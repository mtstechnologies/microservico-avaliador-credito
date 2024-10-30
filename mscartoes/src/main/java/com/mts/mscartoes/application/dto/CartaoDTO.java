package com.mts.mscartoes.application.dto;

import java.math.BigDecimal;

import com.mts.mscartoes.domain.BandeiraCartao;
import com.mts.mscartoes.domain.Cartao;

import lombok.Data;

@Data
public class CartaoDTO {
	
	private String nome;
	private BandeiraCartao bandeira;
	private BigDecimal renda;
	private BigDecimal limite;
	
	public Cartao toModel() {
		return new Cartao(nome, bandeira, renda, limite);
	}
}
