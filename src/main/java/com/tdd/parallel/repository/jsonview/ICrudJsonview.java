package com.tdd.parallel.repository.jsonview;

import com.tdd.parallel.entity.PersonJsonview;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository("iCrudJsonview")
public interface ICrudJsonview extends ReactiveCrudRepository<PersonJsonview, String> {

}