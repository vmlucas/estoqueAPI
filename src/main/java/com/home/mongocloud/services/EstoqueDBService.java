package com.home.mongocloud.services;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import com.home.mongocloud.models.Estoque;
import com.home.mongocloud.models.MedicamentoCEAF;
import com.home.mongocloud.repositories.EstoqueRepository;
import com.home.mongocloud.repositories.MedicamentosCEAFRepository;


@Service
public class EstoqueDBService {

    @Value("${authAPIKey}")
    private String authAPIKey; 
    EstoqueRepository estoqueRepository;
    MedicamentosCEAFRepository ceafRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    public EstoqueDBService(EstoqueRepository estoqueRepository,MedicamentosCEAFRepository ceafRepository ) {
        this.estoqueRepository = estoqueRepository;
        this.ceafRepository = ceafRepository;
    }

    public List<String> buscarTipos() {
        List<String> lista = mongoTemplate.findDistinct("tipo", Estoque.class, String.class);
        return lista;
    }

    public List<String> buscarNomeMedicamentos(String tipo) {
       Criteria criteria = Criteria.where("Tipo").is(tipo);
       Query query = new Query();
       query.addCriteria(criteria);
       
       List<String> lista = mongoTemplate.findDistinct(query,"nome", Estoque.class, String.class);
       return lista;
    }

    public List<Estoque> buscarMedicamentosCEAF(@RequestParam(value = "nome" ) String nome) {
      
        Date maxDate = estoqueRepository.buscarMaxData();
        System.out.println(maxDate);
        System.out.println(nome);
        
        List<Estoque> results = estoqueRepository.buscarMedicamentosData(nome,maxDate);
        System.out.println(results);
        for( Estoque e:results){
          List<MedicamentoCEAF> medLista = this.ceafRepository.buscaMedicamentos(nome);
          if(medLista.size()>0)
          {
            e.setMedicamentosCEAF(medLista);
          }        
        }

      return results;
    
    }

    public List<Estoque> buscarMedicamentos( String nome) {
        List<Estoque> lista = estoqueRepository.buscarMedicamento(nome);
        return lista;      
    }

    public List<Estoque> buscarMedicamentosAnoMes(String nome, Date start, Date end) {
      
      if(nome.equals("todos")){                                                            
        List<Estoque> lista = estoqueRepository.buscarMedicamentoAnoMes(start, end);
        return lista;
      }
      else{
        List<Estoque> lista = estoqueRepository.buscarMedicamentoAnoMes(start, end).stream()
                              .filter(estoque -> estoque.getNome().startsWith(nome)).collect(Collectors.toList());
         
        return lista;
      }
    }

    public List<Estoque> loadData(String key) throws Exception{

        if( key.equals(authAPIKey)) 
        {          
          EstoqueDataLoader eloader = new EstoqueDataLoader();                              
          try{
            List<Estoque> list = eloader.loadList();
            List<Estoque> estoquesCreated = estoqueRepository.saveAll(list);    
            return estoquesCreated;
          }
          catch(Exception e){
            e.printStackTrace();
            List<Estoque> estoquesCreated = new ArrayList<Estoque>();
            return estoquesCreated;
          }
        }
        else{
            throw new Exception("Chave Inválida");    
        }
    }

    public List<MedicamentoCEAF> loadMedicamentosCEAF() {

      MedicamentoCEAFDataLoader eloader = new MedicamentoCEAFDataLoader();
      
      try{
        List<MedicamentoCEAF> list = eloader.fetchMedicamentoCEAFDAta();
        List<MedicamentoCEAF> medsCeafCreated = ceafRepository.saveAll(list);    
        return medsCeafCreated;
      }
      catch(Exception e){
        e.printStackTrace();
        List<MedicamentoCEAF> estoquesCreated = new ArrayList<MedicamentoCEAF>();
        return estoquesCreated;
      }
    }
}

