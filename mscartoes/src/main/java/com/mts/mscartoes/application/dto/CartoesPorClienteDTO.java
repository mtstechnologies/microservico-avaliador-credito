package com.mts.mscartoes.application.dto;

import java.math.BigDecimal;

import com.mts.mscartoes.domain.ClienteCartao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartoesPorClienteDTO {

	private String nome;
	private String bandeira;
	private BigDecimal limiteLiberado;
	
	public static CartoesPorClienteDTO fromModel(ClienteCartao model) {
		return new CartoesPorClienteDTO(
				model.getCartao().getNome(),
				model.getCartao().getBandeira().toString(),
				model.getLimite()
		);
	}
}
