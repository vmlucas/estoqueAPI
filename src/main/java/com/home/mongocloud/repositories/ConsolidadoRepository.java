package com.home.mongocloud.repositories;

import com.home.mongocloud.models.*;
import java.util.*;
import java.time.ZonedDateTime;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ConsolidadoRepository extends MongoRepository<Consolidado, String>{

    /**
     * Return a list of object with Date and Total that is the product of valor and Qtd for each product
     * 
     * @return list of Consolidado
     */
    @Aggregation(pipeline = {
        "{ $group:{'_id': '$Data', 'Total' : { $sum : { $multiply:['$Valor', '$qtd'] } }    } }",
        "{ $project:{'Data' : '$_id', 'Total' : '$Total','_id':0} }",
        "{'$sort': { 'Data': 1 } }"
    })
    List<Consolidado> consolidadoValorAno();    


    /**
     * Return a list of object with Date and Total that is the product of valor and Qtd for each product, delimited with a range of date
     * 
     * @param start - initial date
     * @param end  - final date
     * 
     * @return list of Consolidado
     */
    @Aggregation(pipeline = {
        "{ $match:{'Data' : { $gte: ?0, $lte: ?1 }}}",
        "{ $group:{'_id': '$Data', 'Total' : { $sum : { $multiply:['$Valor', '$qtd'] } }    } }",
        "{ $project:{'Data' : '$_id', 'Total' : '$Total','_id':0} }",
        "{'$sort': { 'Data': 1 } }"
    })
    List<Consolidado> consolidadoValor(Date start, Date end);    


    /**
     * Return a list of object with Date and Total that is the product of valor and Qtd for each product, based on "tipo" filter.
     * 
     * @param tipo - string that filter the type of product (Medicamento, Insulina...)
     * 
     * @return list of Consolidado
     */
    @Aggregation(pipeline = {
        "{'$match': {'Tipo': ?0 }}",
        "{ $group:{'_id': '$Data', 'Total' : { $sum : { $multiply:['$Valor', '$qtd'] } }    } }",
        "{ $project:{'Data' : '$_id', 'Total' : '$Total','_id':0} }",
        "{'$sort': { 'Data': 1 } }"
    })
    List<Consolidado> consolidadoValorTipoAno(String tipo);   


    /**
     * Return a list of object with Date and Total that is the product of valor and Qtd for each product, based on "nome" filter.
     * 
     * @param nome - string that filter the name of product
     * 
     * @return list of Consolidado
     */
    @Aggregation(pipeline = {
        "{'$match': {'Nome': ?0 }}",
        "{ $group:{'_id': '$Data', 'Total' : { $sum : { $multiply:['$Valor', '$qtd'] } }    } }",
        "{ $project:{'Data' : '$_id', 'Total' : '$Total','_id':0} }",
        "{'$sort': { 'Data': 1 } }"
    })
    List<Consolidado> consolidadoValorMedAno(String nome);   

}
