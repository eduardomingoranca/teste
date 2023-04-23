package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenAPI {
    @ApiOperation("Lista os pedidos")
    Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable);


    @ApiOperation("Busca um pedido por um codigo")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do pedido invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Pedido nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    PedidoModel buscar(@ApiParam(value = "Codigo de um pedido", required = true) String codigo);

    @ApiOperation("Cadastra um pedido")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Pedido cadastrado"))
    PedidoModel adicionar(@ApiParam(name = "corpo", value = "Representacao de um novo pedido", required = true)
                          PedidoInput pedidoInput);


}
