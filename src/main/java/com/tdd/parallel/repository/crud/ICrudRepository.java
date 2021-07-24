package com.tdd.parallel.repository.crud;

import com.tdd.parallel.entity.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository("crudRepository")
public interface ICrudRepository extends ReactiveCrudRepository<Person, String> {

}