package com.joinsolutions.heroesapi.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.joinsolutions.heroesapi.document.Heroes;
import com.joinsolutions.heroesapi.service.HeroesService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController()
@RequestMapping("/api/v1/heroes")
public class HeroesController {

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HeroesController.class);

  @Autowired
  private HeroesService heroesService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Heroes> create(@RequestBody Heroes hero) {
    log.info("creating a new Hero");
    return this.heroesService.save(hero);
  }

  @GetMapping("")
  public Flux<Heroes> getAll() {
    log.info("requesting all Heroes");
    return this.heroesService.findAll();
  }

  @GetMapping("/{id}")
  public Mono<Heroes> findById(@PathVariable String id) throws EmptyResultDataAccessException {
    log.info("requesting Hero with id {}", id);
    return this.heroesService.findById(id);
  }

  @PutMapping("/{id}")
  public Mono<Heroes> updateById(@PathVariable String id, @RequestBody Heroes hero) {
    hero.setId(id);
    log.info("updating Hero with id {}", id);
    return this.heroesService.save(hero);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable String id) throws EmptyResultDataAccessException {
    log.info("deleting a Hero with id {}", id);
    this.heroesService.deleteById(id);
  }

  @ExceptionHandler(EmptyResultDataAccessException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Hero not found!")
  public void handle(EmptyResultDataAccessException ex) { }

}
