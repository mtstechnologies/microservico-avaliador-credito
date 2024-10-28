package com.mts.msclientes.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mts.msclientes.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	//query em tempo de runtime
	Optional<Cliente> findByCpf(String cpf);
}