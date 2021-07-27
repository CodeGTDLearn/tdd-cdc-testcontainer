package com.tdd.parallel.repository;

import com.tdd.parallel.entity.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository("mongoRepo")
public interface IMongoRepo extends ReactiveMongoRepository<Person, String> {

}