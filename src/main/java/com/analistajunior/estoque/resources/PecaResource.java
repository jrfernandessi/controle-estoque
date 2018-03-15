package com.analistajunior.estoque.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.analistajunior.estoque.domain.Peca;
import com.analistajunior.estoque.services.PecaService;

@RestController
@RequestMapping("pecas")
public class PecaResource {
	
	@Autowired
	private PecaService service;

	@GetMapping
	public ResponseEntity<List<Peca>> listar(){
		List<Peca> pecas = service.listar();
		return ResponseEntity.ok().body(pecas);
	}
	
	@PostMapping
	public ResponseEntity<Peca> salvar(@Valid @RequestBody Peca peca){
		Peca obj = service.salvar(peca);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Peca> buscar(@PathVariable Long id){
		Peca peca = service.buscarPorId(id);
		return ResponseEntity.ok(peca);
		
	}
	
	@PutMapping("/estocar/{id}")
	public ResponseEntity<Peca> estocar(@RequestBody int quantidade, @PathVariable Long id){
		service.estocar(quantidade, id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/retirar/{id}")
	public ResponseEntity<Peca> retirar(@RequestBody int quantidade, @PathVariable Long id){
		service.retirar(quantidade, id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Peca> deletar(@PathVariable Long id){
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Peca> update(@RequestBody Peca peca, @PathVariable Long id) {
		peca.setId(id);
		service.atualizar(peca);
		return ResponseEntity.noContent().build();
	}
}
