package com.tdd.parallel.repository.standard;

import com.tdd.parallel.entity.standard.PersonStandard;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository("iCrudStandard")
public interface ICrudStandard extends ReactiveCrudRepository<PersonStandard, String> {

}