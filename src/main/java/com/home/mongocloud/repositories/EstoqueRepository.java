package com.home.mongocloud.repositories;

import com.home.mongocloud.models.*;

import java.util.*;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueRepository extends MongoRepository<Estoque, String> {
    

    @Aggregation(pipeline = {
    "{'$group': {'_id':'',Data:{ $max:$Data} }}"
    })
    Date buscarMaxData();
     
    @Aggregation(pipeline = {
    "{'$match': {'Nome': /.*?0.*/}}",
    "{'$match': { 'Data': ?1 } }"
    })
    List<Estoque> buscarMedicamentosData(String nome, Date data);

    /**
     * Return a list of object Estoque
     * 
     * @param nome - string that filter the name of product 
     * 
     * @return list of Estoque
     */
    @Aggregation(pipeline = {
            "{'$match': {'Nome': /.*?0.*/}}",
            "{'$sort': { 'Data': -1 } }"
    })
    List<Estoque> buscarMedicamento(String nome);
    

    /**
     * Return a list of object Estoque, filtered by a range of date.
     * 
     * @param start - initial date
     * @param end  - final date
     * 
     * @return list of Estoque
     */
    @Aggregation(pipeline = {
        "{ $match:{'Data' : { $gte: ?0, $lte: ?1 }}}",
        "{'$sort': { 'Data': -1 } }"
    })
    List<Estoque> buscarMedicamentoAnoMes(Date start, Date end);

    
}

