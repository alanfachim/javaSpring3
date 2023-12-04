package com.svcsolicitarcredito.application.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParecerOrigemPedidoDTO {
    // Atributos da classe
    @JsonProperty(value = "indicador_parecer")
    private String parecer; // o parecer da origem sobre o pedido de crédito
    @JsonProperty(value = "texto_parecer")
    private String justificativa; // a justificativa da origem sobre o parecer
    @JsonProperty(value = "data_hora_parecer")
    private String dataParecer; // a data do parecer da origem
    @JsonProperty(value = "funcional_colaborador")
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
