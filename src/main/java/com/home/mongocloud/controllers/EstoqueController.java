package com.home.mongocloud.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.home.mongocloud.models.*;
import com.home.mongocloud.services.*;
import java.util.*;


@RestController
public class EstoqueController {   
    
  EstoqueDBService service;

  public EstoqueController(EstoqueDBService service ) {
    this.service = service;
  }
  
  /**
   * find all type of products we have ( Medicamento, Insulina, Material...)
   * @return json output for the string list   
   */
  @GetMapping("/buscarTipos")
  public ResponseEntity<List<String>> buscarTipos() {
    List<String> lista = service.buscarTipos();
    return ResponseEntity.ok(lista);
  }

  
  /**
   * find the products names from a specific type "tipo" 
   * @param tipo - type of product
   * @return json output for the string list   
   */
  @GetMapping("/buscarNomeMedicamentos")
  public ResponseEntity<List<String>> buscarNomeMedicamentos(@RequestParam(value = "tipo" ) String tipo) {
    
      List<String> lista = service.buscarNomeMedicamentos(tipo);
      return ResponseEntity.ok(lista);
  }


  @GetMapping("/buscarMedicamentosCEAF")
  public ResponseEntity<List<Estoque>> buscarMedicamentosCEAF(@RequestParam(value = "nome" ) String nome) {
      
    List<Estoque> lista = service.buscarMedicamentosCEAF(nome);
    return ResponseEntity.ok(lista);
    
  }

  /**
   * find a list for all product based on the "nome" input
   * @param nome - product name
   * @return json output for the Estoque list
   */
  @GetMapping("/buscarMedicamentos")
  public ResponseEntity<List<Estoque>> buscarMedicamentos(@RequestParam(value = "nome" ) String nome) {
      List<Estoque> lista = service.buscarMedicamentos(nome);
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

        List<Estoque> lista = service.buscarMedicamentosAnoMes(nome,start, end);
        return ResponseEntity.ok(lista);
    
  }

  @GetMapping("/loadData")
  public ResponseEntity<List<Estoque>> loadEstoqueData(@RequestParam(value = "key", defaultValue = "sem chave") String key) {
    List<Estoque> estoquesCreated = null;
    try{
      estoquesCreated = service.loadData(key);
      if( estoquesCreated.size() > 0){
        return new ResponseEntity<>(estoquesCreated, HttpStatus.CREATED);
      }
      else{
        return new ResponseEntity<>(estoquesCreated, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    catch(Exception ex){
      estoquesCreated = new ArrayList<Estoque>();
      Estoque e = new Estoque();
      e.setNome(ex.getMessage());
      estoquesCreated.add(e);

      return new ResponseEntity<>(estoquesCreated, HttpStatus.FORBIDDEN);
    }  
    
    
    
       

  }

  @GetMapping("/loadMedicamentosCeaf")
  public ResponseEntity<List<MedicamentoCEAF>> loadMedicamentosCeaf() {

       List<MedicamentoCEAF> medsCeafCreated = service.loadMedicamentosCEAF();
    
       if( medsCeafCreated.size() > 0){
        return new ResponseEntity<>(medsCeafCreated, HttpStatus.CREATED);
       }
       else{
        return new ResponseEntity<>(medsCeafCreated, HttpStatus.INTERNAL_SERVER_ERROR);
       }

  }
  

  
  
}