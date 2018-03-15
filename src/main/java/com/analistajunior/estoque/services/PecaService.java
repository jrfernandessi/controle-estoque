package com.analistajunior.estoque.services;

import java.util.List;

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
	
	public Peca salvar(Peca peca) {
		try {
			buscarPorDescricao(peca.getDescricao());
			throw new DataIntegrityException("Peça com descrição já cadastrada");
		}catch (ObjectNotFoundException e) {
			peca.setId(null);
			return repository.save(peca);
		}
		
	}
	
	public Peca buscarPorId(Long id) {
		Peca peca = repository.findOne(id);
		if(peca == null) {
			throw new ObjectNotFoundException("Peça com código inexistente");
		}
		return peca;
	}
	
	public List<Peca> listar(){
		return repository.findAll();
	}
	
	public void deletar(Long id) {
		buscarPorId(id);
		repository.delete(id);
	}
	
	public void atualizarDados(Peca newPeca, Peca peca) {
		newPeca.setDescricao(peca.getDescricao());
		newPeca.setQuantidade(peca.getQuantidade());
	}
	
	public Peca atualizar(Peca peca) {
		Peca newPeca = buscarPorId(peca.getId());
		atualizarDados(newPeca, peca);
		return repository.save(newPeca);
	}
	
	public Peca buscarPorDescricao(String descricao) {
		Peca peca = repository.findByDescricao(descricao);
		if(peca == null) {
			throw new ObjectNotFoundException("Descriação não encontrada");
		}
		return peca;
	}
	
}
