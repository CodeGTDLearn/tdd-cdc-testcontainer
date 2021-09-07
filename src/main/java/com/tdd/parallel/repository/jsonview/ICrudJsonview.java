package com.tdd.parallel.repository.jsonview;

import com.tdd.parallel.entity.jsonview.PersonJsonview;
import com.tdd.parallel.entity.standard.PersonStandard;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository("iCrudJsonview")
public interface ICrudJsonview extends ReactiveCrudRepository<PersonJsonview, String> {

}