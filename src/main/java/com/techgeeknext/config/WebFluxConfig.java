package com.techgeeknext.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer
{
	@Bean
	WebClient getWebClient(@Value("${product.service.baseUrl}") String baseUrl)
	{
		HttpClient httpClient = HttpClient.create()
				.wiretap(true) // for logging request and response 
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
				.doOnConnected(conn -> conn
						.addHandlerLast(new ReadTimeoutHandler(10))
						.addHandlerLast(new WriteTimeoutHandler(10)));

		ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

		return WebClient.builder()
				.baseUrl(baseUrl)
				.clientConnector(connector)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}
}