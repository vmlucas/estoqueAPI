package com.home.mongocloud.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.home.mongocloud.repositories.*;
import com.home.mongocloud.models.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
public class ConsolidadoController {

  private ConsolidadoRepository consolidadoRepository;

  public ConsolidadoController(ConsolidadoRepository consolidadoRepository) {
        this.consolidadoRepository = consolidadoRepository;
  }
    

  /**
     * Return a list of object
     * if ano = "todos"
     * Return a list of object with Date and Total that is the product of valor and Qtd for each product
     * else
     * Return a list of object with Date and Total that is the product of valor and Qtd for each product, delimited by the year
     * 
     * @return json output for the list
     */
  @GetMapping("/consolidadoValor")
  public ResponseEntity<List<Consolidado>> consolidadoValor(@RequestParam( value = "ano",defaultValue = "todos" ) String ano) {
      
    if( ano.equals("todos")){
      List<Consolidado> lista = consolidadoRepository.consolidadoValorAno();
      return ResponseEntity.ok(lista);
    }
    else{
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
      Date start = null;
      Date end = null;
      try{
        start = format.parse(ano+"-01-01");
        end = format.parse(ano+"-12-31");
      }
      catch(Exception e){
        System.out.println(e);
      }
        List<Consolidado> lista = consolidadoRepository.consolidadoValor(start, end);
      return ResponseEntity.ok(lista);
    }
  }


  /**
   * Return a list of object with Date and Total that is the product of valor and Qtd for each product, based on "tipo" filter.
   * @param tipo - string that filter the type of product (Medicamento, Insulina...)
   * @return json output for the list
   */
  @GetMapping("/consolidadoTipoValorAno")
  public ResponseEntity<List<Consolidado>> consolidadoTipoValorAno(String tipo) {
      List<Consolidado> lista = consolidadoRepository.consolidadoValorTipoAno(tipo);
      return ResponseEntity.ok(lista);
    
  }

  /**
   * Return a list of object with Date and Total that is the product of valor and Qtd for each product, based on "nome" filter.
   * @param nome - string that filter the name of product
   * @return json output for the list
   */
  @GetMapping("/consolidadoMedValorAno")
  public ResponseEntity<List<Consolidado>> consolidadoMedValorAno(String nome) {
      List<Consolidado> lista = consolidadoRepository.consolidadoValorMedAno(nome);
      return ResponseEntity.ok(lista);
    
  }
}
