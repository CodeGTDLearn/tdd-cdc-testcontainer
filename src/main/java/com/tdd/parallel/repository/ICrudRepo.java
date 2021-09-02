package com.tdd.parallel.repository;

import com.tdd.parallel.entity.PersonStandard;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository("iCrudRepo")
public interface ICrudRepo extends ReactiveCrudRepository<PersonStandard, String> {

}