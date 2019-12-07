package com.invillia.acme.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.event.RecursoCriado;
import com.invillia.acme.filter.ItemFilter;
import com.invillia.acme.model.Item;
import com.invillia.acme.service.ItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@RequestMapping("/item")
@Api(value = "API Rest ItemResource")
public class ItemResource {
	
	@Autowired
	private ItemService itemService;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	/**
	 * Pesquisar Item
	 */
	@GetMapping
	@ApiOperation(value = "Retorna uma Lista de Item")
	public Page<Item> search(ItemFilter itemFilter, Pageable pageable) {
		return itemService.filter(itemFilter, pageable);
	}
	
	/**
	 * Buscar pelo codigo o Item
	 */
	@GetMapping("/{code}")
	@ApiOperation(value = "Retorna um Item pelo Codigo")
	public ResponseEntity<Item> buscarPeloCodigo(@PathVariable Long code) {
		Item item = itemService.buscarItemPeloCodigo(code);
		return item != null ? ResponseEntity.ok(item) : ResponseEntity.notFound().build();
	}
	
	/**
	 * Criar Item
	 */
	@PostMapping
	@ApiOperation(value = "criar um Item")
	public ResponseEntity<Item> criar(@Valid @RequestBody Item item, HttpServletResponse response) {
		Item itemSalvo = itemService.save(item);
		publisher.publishEvent(new RecursoCriado(this, response, itemSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(itemSalvo);
	}
	
	/**
	 * Atualizar Item
	 */
	@PutMapping("/{code}")
	@ApiOperation(value = "Atualizar um Item")
	public ResponseEntity<Item> atualizar(@PathVariable Long code, @Valid @RequestBody Item item) {
		Item itemSalva = itemService.atualizar(code, item);
		return ResponseEntity.ok(itemSalva);
	}
	

	/**
	 * Deletar Item
	 */
	@DeleteMapping("/{code}")
	@ApiOperation(value = "Deletar um Item")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long code) {
		itemService.delete(code);
	}

}
