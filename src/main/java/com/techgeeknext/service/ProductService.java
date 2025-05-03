package com.techgeeknext.service;

import java.time.Duration;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.techgeeknext.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService implements IProductService
{
	private final WebClient webClient;

	public ProductService(WebClient webClient)
	{
		this.webClient = webClient;
	}

	public Flux<Product> findAll()

	{
		return webClient.get()
				.uri("/products")
				.retrieve()
				.bodyToFlux(Product.class)
				.timeout(Duration.ofMillis(10_000));
	}

	public Mono<Product> create(Product prd)
	{
		return webClient.post()
				.uri("/products")
				.body(Mono.just(prd), Product.class)
				.retrieve()
				.bodyToMono(Product.class)
				.timeout(Duration.ofMillis(10_000));
	}

	public Mono<Product> findById(Long id)
	{
		return webClient.get()
				.uri("/products/" + id)
				.retrieve()
				.bodyToMono(Product.class);
	}

	public Mono<Product> update(Product p)
	{
		return webClient.put()
				.uri("/update/" + p.getId())
				.body(Mono.just(p), Product.class)
				.retrieve()
				.bodyToMono(Product.class);
	}

	public Mono<Boolean> delete(Long id)
	{
		return webClient.delete()
				.uri("/delete/" + id)
				.retrieve()
				.bodyToMono(Boolean.class);
	}

}