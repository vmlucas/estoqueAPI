package com.home.mongocloud.models;

import org.springframework.data.mongodb.core.mapping.*;
import lombok.*;
import lombok.experimental.*;
import java.util.*;

@Document(collection = "MedicamentoCEAF")
@Accessors(chain = true)
@NoArgsConstructor
@Data
@Getter
@Setter
public class MedicamentoCEAF {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Field(name="Nome")
    private String nome;

    @Field(name="Data")
    private Date data;

    @Field(name="DescricaoCid")
    private String descricaoCid; 
    
}
