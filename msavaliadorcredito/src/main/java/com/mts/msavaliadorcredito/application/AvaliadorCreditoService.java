package com.mts.msavaliadorcredito.application;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mts.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import com.mts.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import com.mts.msavaliadorcredito.domain.model.CartaoCliente;
import com.mts.msavaliadorcredito.domain.model.DadosCliente;
import com.mts.msavaliadorcredito.domain.model.SituacaoCliente;
import com.mts.msavaliadorcredito.infra.clients.CartoesResourceClient;
import com.mts.msavaliadorcredito.infra.clients.ClienteResourceClient;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
	
	private final ClienteResourceClient clienteClient;
	private final CartoesResourceClient cartaoClient;
	
	public SituacaoCliente obterSituacaoCliente(String cpf) 
			throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException{
		try {
		ResponseEntity<DadosCliente> dadosClienteResponse = clienteClient.dadosCliente(cpf);
		ResponseEntity<List<CartaoCliente>> cartaoResponse = cartaoClient.obterCartoesPorCliente(cpf);
		return SituacaoCliente
				.builder()
				.cliente(dadosClienteResponse.getBody())
				.cartoes(cartaoResponse.getBody())
				.build();
		}catch (FeignException.FeignClientException e) {
			int status = e.status();
			if(HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
		}
	}	
}
