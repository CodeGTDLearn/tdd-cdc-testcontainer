package com.tdd.parallel.repository.template;

import com.tdd.parallel.entity.Person;
import com.tdd.parallel.repository.IRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


//@AllArgsConstructor
@Repository("templateRepo")
public class TemplateRepo implements IRepo {

  @Autowired
  ReactiveMongoTemplate repo;


  @Override
  public Mono<Person> save(Person person) {
    return repo.save(person);
  }


  @Override
  public Flux<Person> findAll() {
    return repo.findAll(Person.class);
  }


  @Override
  public Mono<Void> deleteById(String id) {
    return findById(id)
         .flatMap(repo::remove)
         .then();
  }


  @Override
  public Mono<Void> deleteAll() {
    return repo
         .remove(new Query(),Person.class)
         .then();
  }


  @Override
  public Mono<Boolean> collectionExists(String collection) {
    return repo.collectionExists(collection);

  }


  @Override
  public Mono<Person> findById(String id) {
    return repo.findById(id,Person.class);
  }


  @Override
  public Flux<Person> saveAll(List<Person> personList) {
    return repo.insertAll(personList);
  }
}
