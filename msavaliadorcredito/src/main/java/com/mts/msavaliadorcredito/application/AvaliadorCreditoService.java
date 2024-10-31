package com.mts.msavaliadorcredito.application;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mts.msavaliadorcredito.domain.model.CartaoCliente;
import com.mts.msavaliadorcredito.domain.model.DadosCliente;
import com.mts.msavaliadorcredito.domain.model.SituacaoCliente;
import com.mts.msavaliadorcredito.infra.clients.CartoesResourceClient;
import com.mts.msavaliadorcredito.infra.clients.ClienteResourceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
	
	private final ClienteResourceClient clienteClient;
	private final CartoesResourceClient cartaoClient;
	
	public SituacaoCliente obterSituacaoCliente(String cpf) {
		//obterDadosCliente - MSCLIENTES
		//obtercartoes dp cliente - MSCARTOES
		
		ResponseEntity<DadosCliente> dadosClienteResponse = clienteClient.dadosCliente(cpf);
		ResponseEntity<List<CartaoCliente>> cartaoResponse = cartaoClient.obterCartoesPorCliente(cpf);
		return SituacaoCliente
				.builder()
				.cliente(dadosClienteResponse.getBody())
				.cartoes(cartaoResponse.getBody())
				.build();
	}	
}
