package com.tdd.parallel.repository.mongo;

import com.tdd.parallel.entity.Person;
import com.tdd.parallel.repository.IRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Repository("mongoRepo")
@AllArgsConstructor
public class MongoRepo implements IRepo {

  private final IMongoRepository repo;


  @Override
  public Mono<Person> save(Person person) {
    return repo.save(person);
  }


  @Override
  public Flux<Person> findAll() {
    return repo.findAll();
  }


  @Override
  public Mono<Void> deleteById(String id) {
    return repo.deleteById(id);
  }


  @Override
  public Mono<Void> deleteAll() {
    return repo.deleteAll();
  }


  @Override
  public Mono<Person> findById(String id) {
    return repo.findById(id);
  }


  @Override
  public Flux<Person> saveAll(List<Person> personList) {
    return repo.saveAll(personList);
  }


  @Override
  public Mono<Boolean> collectionExists(String collection) {
    return null;
  }
}
