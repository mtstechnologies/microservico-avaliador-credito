package com.mts.msclientes.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mts.msclientes.domain.Cliente;
import com.mts.msclientes.infra.repository.ClienteRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
	
	//ao definir como final, estou afirmando que sera uma dependencia obrigatoria
	private final ClienteRepository repository;

	@Transactional
	public Cliente salvar(Cliente cliente) {
		return repository.save(cliente);
	}
	
	public Optional<Cliente> obterClientePorCpf(String cpf){
		return repository.findByCpf(cpf);
	}
}
