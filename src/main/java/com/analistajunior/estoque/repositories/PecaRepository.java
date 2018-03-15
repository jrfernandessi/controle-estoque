package com.analistajunior.estoque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.analistajunior.estoque.domain.Peca;

public interface PecaRepository extends JpaRepository<Peca, Long>{
	public Peca findByDescricao(String descricao);
}
