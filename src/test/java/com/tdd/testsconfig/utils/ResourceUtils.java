package com.tdd.testsconfig.utils;

import com.tdd.parallel.entity.standard.PersonStandard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.tdd.databuilder.PersonBuilder.personWithIdAndName;

public class ResourceUtils {



  public PersonStandard generatePerson_savePerson_checkSaving() {
    PersonStandard localPerson = personWithIdAndName().create();

    StepVerifier
         .create(serviceCrudRepo.deleteAll()
                                .log())
         .expectSubscription()
         .expectNextCount(0L)
         .verifyComplete();

    StepVerifier
         .create(serviceCrudRepo.save(localPerson))
         .expectSubscription()
         .expectNext(localPerson)
         .verifyComplete();

    return localPerson;
  }


  public void StepVerifierCountPersonInDb(Flux<PersonStandard> flux,Long totalElements) {
    StepVerifier
         .create(flux)
         .expectSubscription()
         .expectNextCount(totalElements)
         .verifyComplete();
  }


  public void StepVerifierFindPerson(Mono<PersonStandard> flux,Long totalElements) {
    StepVerifier
         .create(flux)
         .expectSubscription()
         .expectNextCount(totalElements)
         .verifyComplete();
  }
}
