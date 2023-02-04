package com.algaworks.algafood.infrastructure.repository.spec;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.domain.Specification;

import static java.math.BigDecimal.ZERO;

public class RestauranteSpecs {
    public static Specification<Restaurante> comFreteGratis() {
        return (root, query, builder) ->
                builder.equal(root.get("taxaFrete"), ZERO);
    }

    public static Specification<Restaurante> comNomeSemelhante(String nome) {
        return (root, query, builder) ->
                builder.like(root.get("nome"), "%" + nome + "%");
    }

}