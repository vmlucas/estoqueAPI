package com.home.mongocloud.services;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.home.mongocloud.models.Estoque;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class EstoqueDataLoader {


    public List<Estoque> loadList() throws Exception{
        List<Estoque> lista = new ArrayList<Estoque>();
        Path path = Paths.get(
           ClassLoader.getSystemResource("estoque.csv").toURI());

        List<String[]> lines = readAllLines(path);
        for( String[] item: lines){
            Estoque estoque = new Estoque();
            //ABEMACICLIBE 50 MG,540,76.12,11/11/2024,MEDICAMENTO
            estoque.setNome(item[0]);
            estoque.setQtd(Integer.parseInt(item[1]));
            estoque.setValor(Double.parseDouble(item[2]));
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(item[3]);
            estoque.setData(date);
            estoque.setTipo(item[4]);
            
            lista.add(estoque);
        }

        return lista;
    }

    private List<String[]> readAllLines(Path filePath) throws Exception {
      CSVParser parser = new CSVParserBuilder()
       .withSeparator(',')
       .withIgnoreQuotations(false)
       .build();

      try (Reader reader = Files.newBufferedReader(filePath)) {
        try (CSVReader csvReader = new CSVReaderBuilder(reader)
                      .withSkipLines(1)
                      .withCSVParser(parser)
                      .build()) {
            return csvReader.readAll();
        }
      }
    }

   

}
