package com.mts.msavaliadorcredito.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mts.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import com.mts.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import com.mts.msavaliadorcredito.domain.model.Cartao;
import com.mts.msavaliadorcredito.domain.model.CartaoAprovado;
import com.mts.msavaliadorcredito.domain.model.CartaoCliente;
import com.mts.msavaliadorcredito.domain.model.DadosCliente;
import com.mts.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import com.mts.msavaliadorcredito.domain.model.SituacaoCliente;
import com.mts.msavaliadorcredito.infra.clients.CartoesResourceClient;
import com.mts.msavaliadorcredito.infra.clients.ClienteResourceClient;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
	
	private final ClienteResourceClient clienteClient;
	private final CartoesResourceClient cartoesClient;
	
	public SituacaoCliente obterSituacaoCliente(String cpf) 
			throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException{
		try {
		ResponseEntity<DadosCliente> dadosClienteResponse = clienteClient.dadosCliente(cpf);
		ResponseEntity<List<CartaoCliente>> cartaoResponse = cartoesClient.obterCartoesPorCliente(cpf);
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
	
	public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) 
			throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
		try {
		ResponseEntity<DadosCliente> dadosClienteResponse = clienteClient.dadosCliente(cpf);
		ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.obterCartoesRendaAte(renda);
	 
		List<Cartao> cartoes = cartoesResponse.getBody();
		var listaCartoesAprovados = cartoes.stream().map(cartao -> {
		 
			DadosCliente dadosCliente = dadosClienteResponse.getBody();
		 
			BigDecimal limiteBasico = cartao.getLimiteBasico();
			BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
				 
			var fator = idadeBD.divide(BigDecimal.valueOf(10));
			BigDecimal limiteAprovado = fator.multiply(limiteBasico);
			
			CartaoAprovado aprovado = new CartaoAprovado();
			aprovado.setCartao(cartao.getNome());
			aprovado.setBandeira(cartao.getBandeira());
			aprovado.setLimiteAprovado(limiteAprovado);
			
			return aprovado;
		}).collect(Collectors.toList());
		
		return new RetornoAvaliacaoCliente(listaCartoesAprovados);
	 
		}catch (FeignException.FeignClientException e) {
			int status = e.status();
			if(HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
