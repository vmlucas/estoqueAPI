package com.home.mongocloud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.home.mongocloud.repositories.*;
import com.home.mongocloud.models.*;
import com.home.mongocloud.dtos.*;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class EstoqueController {   
    
  EstoqueRepository estoqueRepository;

  @Autowired
  MongoTemplate mongoTemplate;

  public EstoqueController(EstoqueRepository estoqueRepository) {
    this.estoqueRepository = estoqueRepository;
  }
  
  /**
   * find all type of products we have ( Medicamento, Insulina, Material...)
   * @return json output for the string list   
   */
  @GetMapping("/buscarTipos")
  public ResponseEntity<List<String>> buscarTipos() {
    List<String> lista = mongoTemplate.findDistinct("tipo", Estoque.class, String.class);
    return ResponseEntity.ok(lista);
  }

  /**
   * find the products names from a specific type "tipo" 
   * @param tipo - type of product
   * @return json output for the string list   
   */
  @GetMapping("/buscarNomeMedicamentos")
  public ResponseEntity<List<String>> buscarNomeMedicamentos(@RequestParam(value = "tipo" ) String tipo) {
    Criteria criteria = Criteria.where("Tipo").is(tipo);
    Query query = new Query();
    query.addCriteria(criteria);

    List<String> lista = mongoTemplate.findDistinct(query,"nome", Estoque.class, String.class);
    return ResponseEntity.ok(lista);
  }

  /**
   * find a list for all product based on the "nome" input
   * @param nome - product name
   * @return json output for the Estoque list
   */
  @GetMapping("/buscarMedicamentos")
  public ResponseEntity<List<Estoque>> buscarMedicamentos(@RequestParam(value = "nome" ) String nome) {
      List<Estoque> lista = estoqueRepository.buscarMedicamento(nome);
      return ResponseEntity.ok(lista);
    
  }

  /**
   * find a list for all product based on the input "nome" and dates. 
   * If input "nome" is "todos", it returns all products based on the range of dates.
   * 
   * @param nome - product name to filter the list
   * @param start - initial date
   * @param end - end date
   * @return json output for the Estoque list
   */
  @GetMapping("/buscarMedicamentosAnoMes")
  public ResponseEntity<List<Estoque>> buscarMedicamentosAnoMes(@RequestParam(value = "nome",defaultValue = "todos" ) String nome,
                                                                @RequestParam("start") @DateTimeFormat(iso = ISO.DATE) Date start,
                                                                @RequestParam("end") @DateTimeFormat(iso = ISO.DATE) Date end) {
      if(nome.equals("todos")){                                                            
        List<Estoque> lista = estoqueRepository.buscarMedicamentoAnoMes(start, end);
        return ResponseEntity.ok(lista);
      }
      else{
        List<Estoque> lista = estoqueRepository.buscarMedicamentoAnoMes(start, end).stream()
                              .filter(estoque -> estoque.getNome().startsWith(nome)).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
      }

    
  }

  

  
  
}