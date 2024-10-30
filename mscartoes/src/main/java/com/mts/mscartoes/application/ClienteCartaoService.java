package com.mts.mscartoes.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mts.mscartoes.domain.ClienteCartao;
import com.mts.mscartoes.infra.repository.ClienteCartaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {
	
	private final ClienteCartaoRepository repository;

	public List<ClienteCartao> listaCartoesPorCpf(String cpf){
		return repository.findByCpf(cpf);
	}
}
