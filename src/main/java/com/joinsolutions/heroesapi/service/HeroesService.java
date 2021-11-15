package com.joinsolutions.heroesapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.joinsolutions.heroesapi.document.Heroes;
import com.joinsolutions.heroesapi.repository.HeroesRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HeroesService {

	@Autowired
	private HeroesRepository heroesRepository;

	public Flux<Heroes> findAll() {
		return Flux.fromIterable(this.heroesRepository.findAll());
	}

	public Mono<Heroes> findById(String id) throws EmptyResultDataAccessException {
		return Mono.justOrEmpty(this.heroesRepository.findById(id))
				.switchIfEmpty(Mono.error(new EmptyResultDataAccessException(1)));
	}

	public Mono<Heroes> save(Heroes heroes) {
		return Mono.justOrEmpty(this.heroesRepository.save(heroes));
	}

	public Mono<Boolean> deleteById(String id) {
		this.heroesRepository.deleteById(id);
		return Mono.just(true);
	}
}
