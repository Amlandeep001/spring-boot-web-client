package com.techgeeknext.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techgeeknext.model.Product;
import com.techgeeknext.service.ProductService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/products")
public class ProductWebController 
{
	@Autowired
	private ProductService productService;

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(HttpStatus.OK)
	//using Flux for collection of employees
	public Flux<Product> findAll() {
		return productService.findAll();
	}

	@GetMapping(value = "/{id}")
	//using Mono for single employee
	public Mono<Product> findById(@PathVariable("id") Long id) {
		return productService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	//using Mono for single employee
	public Mono<Product> create(@RequestBody Product p) {
		return productService.create(p);
	}

	@PutMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	//using Mono for single employee
	public Mono<Product> update(@RequestBody Product p, @PathVariable("id") Long id) {
		p.setId(id);
		Mono<Product> prd = productService.update(p);
		if(prd != null) {
			log.info("Response :: {}", prd.toString());
			return prd;
		}
		else {
			throw new RuntimeException("Got null response back as ID does not exist in DB");
		}
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	// using Mono<Void> when no response content is expected
	public Mono<Boolean> delete(@PathVariable("id") Long id) {
		return productService.delete(id);
	}
}
