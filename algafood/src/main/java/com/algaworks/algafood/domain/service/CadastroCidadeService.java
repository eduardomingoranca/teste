package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class CadastroCidadeService {
    public static final String MSG_CIDADE_NAO_ENCONTRADA = "Nao existe um cadastro de cidade com codigo %d";
    public static final String MSG_CIDADE_EM_USO = "Cidade de codigo %d nao pode ser removida, pois esta em uso";

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    public Cidade salvar(Cidade cidade) {
        Long id = cidade.getEstado().getId();
        Optional<Estado> estado = estadoRepository.findById(id);

        if (estado.isEmpty())
            throw new EntidadeNaoEncontradaException(
                    format(MSG_CIDADE_NAO_ENCONTRADA, id));

        cidade.setEstado(estado.get());

        return cidadeRepository.save(cidade);
    }

    public Optional<Cidade> buscar(Long id) {
        return cidadeRepository.findById(id);
    }

    public void excluir(Long id) {
        try {
            cidadeRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    format(MSG_CIDADE_NAO_ENCONTRADA, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    format(MSG_CIDADE_EM_USO, id));
        }
    }

    public Cidade buscarOuFalhar(Long id) {
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        format(MSG_CIDADE_NAO_ENCONTRADA, id)));
    }

}
