package com.home.mongocloud.models;

import org.springframework.data.mongodb.core.mapping.*;
import lombok.*;
import lombok.experimental.*;
import java.util.*;

@Document(collection = "Estoque")
@Accessors(chain = true)
@NoArgsConstructor
@Data
@Getter
@Setter
public class Estoque {  
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Field(name="Data")
    private Date data;

    @Field(name = "Nome")
    private String nome;

    @Field(name = "Class")
    private String classe;

    @Field(name = "Tipo")
    private String tipo;

    @Field(name = "Valor")
    private double valor;

    @Field(name = "qtd")
    private int qtd;

}
