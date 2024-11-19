package com.home.mongocloud.dtos;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.home.mongocloud.models.MedicamentoCEAF;


public class MedicamentoCEAFDataLoader {

    private RestTemplate restTemplate;
    private HttpEntity<Void> requestEntity;

    public MedicamentoCEAFDataLoader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_PDF, MediaType.APPLICATION_OCTET_STREAM));
        this.requestEntity = new HttpEntity<>(headers); 
        restTemplate = new RestTemplate();
      }
    
    public List<MedicamentoCEAF> fetchMedicamentoCEAFDAta()throws Exception{
        List<MedicamentoCEAF> lista = new ArrayList<MedicamentoCEAF>();
        //System.out.println("Antes URL");
        //URL pdfUrl = new URL("https://www.saude.rj.gov.br/comum/code/MostrarArquivo.php?C=NzA5NDA%2C");
        URI uri = new URI("https://www.saude.rj.gov.br/comum/code/MostrarArquivo.php?C=NzA5NDA%2C");
        
        
        ResponseEntity<byte[]> result =
                 restTemplate.exchange(uri, HttpMethod.GET, requestEntity, byte[].class);
        byte[] pdfBytes = result.getBody();
        //InputStream inputStream = pdfUrl.openStream();
        
        PDDocument document = PDDocument.load(pdfBytes);
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        String nome = "";
        boolean inputDesc = false;
        boolean inputNome = true;
        String[] lines = text.split("\n");
        for(int i=0; i < lines.length;i++){
            String line = lines[i];
            
            if( !line.trim().equals("")&& inputNome){
                if( !line.startsWith("CID")){
                    nome = line;
                }
            }
            if( line.trim().equals("")&&inputDesc){
                inputDesc = false;
                inputNome = true;                
            }
            if( !line.trim().equals("")&&inputDesc){
                MedicamentoCEAF med = new MedicamentoCEAF();
                med.setNome(nome);
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = formatter.parse("15/11/2024");
                med.setData(date);
                med.setDescricaoCid(line);
                lista.add(med);
            }
            
            if( line.startsWith("CID")){
                inputDesc = true;
                inputNome = false;
            }
            
        }
        return lista;        
    }
}
