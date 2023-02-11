package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class CadastroRestauranteService {
    public static final String MSG_COZINHA_NAO_ENCONTRADA = "Nao existe cadastro de cozinha com codigo %d";

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaID = restaurante.getCozinha().getId();
//         caso nao exista uma cozinha lanca exception
        Cozinha cozinha = cozinhaRepository.findById(cozinhaID)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        format(MSG_COZINHA_NAO_ENCONTRADA, cozinhaID)));

        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    public Optional<Restaurante> buscar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId);
    }

    public Restaurante buscarOuFalhar(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        format(MSG_COZINHA_NAO_ENCONTRADA, id)));
    }

}
