package com.mts.mscartoes.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mts.mscartoes.application.dto.CartaoDTO;
import com.mts.mscartoes.application.dto.CartoesPorClienteDTO;
import com.mts.mscartoes.domain.Cartao;
import com.mts.mscartoes.domain.ClienteCartao;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesController {
	
	private final CartaoService cartaoService;
	private final ClienteCartaoService clienteCartaoService;
	
	@GetMapping
	public String status() {
		return "ok";
	}

	@PostMapping
	public ResponseEntity salvar(@RequestBody CartaoDTO dto) {
		Cartao cartao = dto.toModel();
		cartaoService.save(cartao);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(params = "renda")
	public ResponseEntity<List<Cartao>> obterCartoesRendaAte(@RequestParam("renda") Long renda){
		List<Cartao> list = cartaoService.getCartaoRendaMenorIgual(renda);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity<List<CartoesPorClienteDTO>> obterCartoesPorCliente(
			@RequestParam("cpf") String cpf){
			List<ClienteCartao> lista = clienteCartaoService.listaCartoesPorCpf(cpf);
			List<CartoesPorClienteDTO> resultlist = lista.stream()
					.map(CartoesPorClienteDTO::fromModel)
					.collect(Collectors.toList());
			return ResponseEntity.ok(resultlist);
	}	
}
