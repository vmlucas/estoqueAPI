package com.home.mongocloud.models;

import lombok.*;
import lombok.experimental.*;
import java.util.*;
import org.springframework.data.mongodb.core.mapping.*;

@Document(collection = "Estoque")
@Accessors(chain = true)
@NoArgsConstructor
@Data
@Getter
@Setter
public class Consolidado {

    @Field(name="Data")
    private Date data;

    @Field(name = "Total")
    private double valorTotal;
}
