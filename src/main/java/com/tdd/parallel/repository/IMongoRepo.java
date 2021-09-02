package com.tdd.parallel.repository;

import com.tdd.parallel.entity.PersonStandard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository("iMongoRepo")
public interface IMongoRepo extends ReactiveMongoRepository<PersonStandard, String> {

}