package com.home.mongocloud.dtos;

import lombok.Getter;
import lombok.Setter;
import com.home.mongocloud.models.*;
import java.util.*; 

@Setter
@Getter
public class CreateEstoqueDto {

    private Date data;
    private String nome;
    private String classe;
    private String tipo;
    private double valor;
    private int qtd;
    
    public Estoque toEstoque() {
        return new Estoque().setNome(nome).setData(data).setClasse(classe).setTipo(tipo).setValor(valor).setQtd(qtd);
    }
}

