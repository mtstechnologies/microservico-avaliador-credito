package com.mts.msclientes.application;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mts.msclientes.application.dto.ClienteDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {
	
	
	private final ClienteService service;
	
	@GetMapping
	public String status() {
		return "ok";
	}
	
	@PostMapping
	public ResponseEntity salvarCliente(@RequestBody ClienteDTO dto) {
		var cliente = dto.toModel();
		service.salvar(cliente);
		URI headerLocation = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.query("cpf={cpf}")
				.buildAndExpand(cliente.getCpf())
				.toUri();
		return ResponseEntity.created(headerLocation).build();
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity buscarPorCpf(@RequestParam("cpf") String cpf) {
		var cliente = service.obterPorCpf(cpf);
		if(cliente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cliente);
	}
	/*
	 @PostMapping
	    public ResponseEntity<ClienteDTO> criarCliente(@RequestBody ClienteDTO clienteDTO) {
	        ClienteDTO novoCliente = service.salvarCliente(clienteDTO);
	        return ResponseEntity.ok(novoCliente);
	    }
	
	@GetMapping("/{cpf}")
	 public ResponseEntity<ClienteDTO> buscarPorCpf(@PathVariable String cpf){
		Optional<ClienteDTO> cliente = service.obterClientePorCpf(cpf);
		return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

	}
	*/
}