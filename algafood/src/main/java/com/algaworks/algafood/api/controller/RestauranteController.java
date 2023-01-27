package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.util.ReflectionUtils.*;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @GetMapping
    public ResponseEntity<List<Restaurante>> listar() {
        return status(OK).body(restauranteRepository.todas());
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteRepository.porID(restauranteId);

        if (restaurante == null) return status(NOT_FOUND).build();

        return status(OK).body(restaurante);
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = cadastroRestaurante.salvar(restaurante);

            return status(CREATED).body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable("restauranteId") Long id,
                                       @RequestBody Restaurante restaurante) {
        try {
            Restaurante restauranteAtual = restauranteRepository.porID(id);

            if (restauranteAtual == null) return status(NOT_FOUND).build();

            copyProperties(restaurante, restauranteAtual, "id");
            cadastroRestaurante.salvar(restauranteAtual);

            return status(OK).body(restauranteAtual);
        } catch (EntidadeNaoEncontradaException e) {
            return status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable("restauranteId") Long id,
                                              @RequestBody Map<String, Object> campos) {
        Restaurante restauranteAtual = restauranteRepository.porID(id);

        if (restauranteAtual == null) return status(NOT_FOUND).build();

        merge(campos, restauranteAtual);

        return atualizar(id, restauranteAtual);
    }

    // mesclar os valores do map campos para dentro do restaurante atual
    private static void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
        // ObjectMapper -> class responsavel por converter objetos java em json e json em objetos java
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            // obtendo os campos de restaurante e informado no nome da propriedade informada
            Field field = findField(Restaurante.class, nomePropriedade);
            // acessando um atributo/metodo private
            assert field != null;
            field.setAccessible(true);

            // obtendo o valor da propriedade convertido
            Object novoValor = getField(field, restauranteOrigem);

            // alterando o valor da variavel de instancia informada pelo valor da propriedade
            setField(field, restauranteDestino, novoValor);
        });
    }

}