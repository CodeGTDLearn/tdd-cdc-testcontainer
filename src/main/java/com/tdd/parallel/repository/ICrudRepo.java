package com.tdd.parallel.repository;

import com.tdd.parallel.entity.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository("iCrudRepo")
public interface ICrudRepo extends ReactiveCrudRepository<Person, String> {

}