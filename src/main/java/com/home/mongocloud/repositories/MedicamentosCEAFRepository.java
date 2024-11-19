package com.home.mongocloud.repositories;

import java.util.*;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.home.mongocloud.models.MedicamentoCEAF;



@Repository
public interface MedicamentosCEAFRepository extends MongoRepository<MedicamentoCEAF, String>{

  
     @Aggregation(pipeline = {
            "{'$match': {'Nome': /.*?0.*/}}",
            "{'$sort': { 'Data': -1 } }"
    })
    List<MedicamentoCEAF> buscaMedicamentos(String nome);
  
	
  
}
