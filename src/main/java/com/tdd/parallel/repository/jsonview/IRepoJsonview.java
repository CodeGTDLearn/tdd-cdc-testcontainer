package com.tdd.parallel.repository.jsonview;

import com.tdd.parallel.entity.PersonJsonview;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository("iRepoJsonview")
public interface IRepoJsonview extends ReactiveMongoRepository<PersonJsonview, String> {

}