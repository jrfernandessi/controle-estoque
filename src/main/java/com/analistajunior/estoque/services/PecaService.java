package com.analistajunior.estoque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analistajunior.estoque.domain.Peca;
import com.analistajunior.estoque.repositories.PecaRepository;
import com.analistajunior.estoque.services.exceptions.DataIntegrityException;
import com.analistajunior.estoque.services.exceptions.ObjectNotFoundException;


@Service
public class PecaService {

	@Autowired
	private PecaRepository repository;
	
	public Peca estocar(int quantidade, Long id) {
		Peca peca = repository.findOne(id);
		if(peca == null) {
			throw new ObjectNotFoundException("Peça não encontrada");
		}
		peca.setQuantidade(peca.getQuantidade()+quantidade);
		return repository.save(peca);
	}
	
	public Peca retirar(int quantidade, Long id) {
		Peca peca = repository.findOne(id);
		if(peca == null) {
			throw new ObjectNotFoundException("Peça não encontrada");
		}
		if(quantidade>peca.getQuantidade()) {
			throw new DataIntegrityException("Quantidade maior que a em estoque");
		}
		peca.setQuantidade(peca.getQuantidade() - quantidade);
		return repository.save(peca);
	}
	
}
