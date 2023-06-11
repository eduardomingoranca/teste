package com.algaworks.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Setter
@Getter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {
    @Schema(example = "1")
    private Long produtoId;

    @Schema(example = "Porco com molho agridoce")
    private String produtoNome;

    @Schema(example = "2")
    private Long quantidade;

    @Schema(example = "78.90")
    private BigDecimal precoUnitario;

    @Schema(example = "78.90")
    private BigDecimal precoTotal;

    @Schema(example = "Menos picante, por favor")
    private String observacao;

}
