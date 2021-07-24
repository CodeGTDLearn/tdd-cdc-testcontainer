package com.tdd.parallel.repository.mongo;

import com.tdd.parallel.entity.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository("mongoRepository")
public interface IMongoRepository extends ReactiveMongoRepository<Person, String> {

}