package com.svcsolicitarcredito.domain.entity;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedJson;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Data
@DynamoDBDocument
public class ParecerOrigemPedido implements Serializable {
    // Atributos da classe
    @Column(name = "parecer")
    private String parecer; // o parecer da origem sobre o pedido de crédito
    @Column(name = "justificativa")
    private String justificativa; // a justificativa da origem sobre o parecer
    @Column(name = "data_parecer")
    private String dataParecer; // a data do parecer da origem
    @Column(name ="usuario_parecer")
    private String usuarioParecer; // o usuário que emitiu o parecer da origem

    public String getParecer() {
        return parecer;
    }

    public void setParecer(String parecer) {
        this.parecer = parecer;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public String getDataParecer() {
        return dataParecer;
    }

    public void setDataParecer(String dataParecer) {
        this.dataParecer = dataParecer;
    }

    public String getUsuarioParecer() {
        return usuarioParecer;
    }

    public void setUsuarioParecer(String usuarioParecer) {
        this.usuarioParecer = usuarioParecer;
    }
}
