package com.techgeeknext.service;

import com.techgeeknext.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
 
public interface IProductService
{
	Flux<Product> findAll();
	
	Mono<Product> findById(Long id);
 
    Mono<Product> create(Product p);
 
    Mono<Product> update(Product p);
 
    Mono<Boolean> delete(Long id);
}