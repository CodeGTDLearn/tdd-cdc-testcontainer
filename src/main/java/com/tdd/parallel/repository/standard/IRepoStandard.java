package com.tdd.parallel.repository.standard;

import com.tdd.parallel.entity.standard.PersonStandard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository("iRepoStandard")
public interface IRepoStandard extends ReactiveMongoRepository<PersonStandard, String> {

}