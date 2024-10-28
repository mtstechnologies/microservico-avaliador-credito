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
	
	public Optional<Cliente> obterPorCpf(String cpf){
		return repository.findByCpf(cpf);
	}
	/*
	 @Transactional
	    public ClienteDTO salvarCliente(ClienteDTO clienteDTO) {
	        Cliente cliente = new Cliente();
	        cliente.setCpf(clienteDTO.getCpf());
	        cliente.setNome(clienteDTO.getNome());
	        cliente.setIdade(clienteDTO.getIdade());
	        
	        cliente = repository.save(cliente);
	        
	        clienteDTO.setCpf(cliente.getCpf());
	        return clienteDTO;
	    }
	
	public Optional<ClienteDTO> obterClientePorCpf(String cpf){
		return repository.findByCpf(cpf)
				
        .map(cliente -> {
	            ClienteDTO dto = new ClienteDTO();
	            dto.setCpf(cliente.getCpf());
	            dto.setNome(cliente.getNome());
	            dto.setIdade(cliente.getIdade());
            return dto;
         });
         */
}
