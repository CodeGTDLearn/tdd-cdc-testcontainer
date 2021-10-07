package testsconfig.utils;

import com.tdd.parallel.entity.PersonJsonview;
import com.tdd.parallel.entity.PersonStandard;
import com.tdd.parallel.service.IService;
import testsconfig.databuilder.PersonJsonviewBuilder;
import testsconfig.databuilder.PersonStandardBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


public class TestDbUtils<E> {

  public void countPersonInDb(Flux<E> flux,Long totalItems) {
    StepVerifier
         .create(flux)
         .expectSubscription()
         .expectNextCount(totalItems)
         .verifyComplete();
  }


  public void findPersonInDb(Mono<E> flux,Long totalItems) {
    StepVerifier
         .create(flux)
         .expectSubscription()
         .expectNextCount(totalItems)
         .verifyComplete();
  }


  public PersonStandard personStandard_save_check(IService<PersonStandard> service) {
    PersonStandard localPerson = PersonStandardBuilder.personWithIdAndNameStandard().create();

    StepVerifier
         .create(service.deleteAll()
                        .log())
         .expectSubscription()
         .expectNextCount(0L)
         .verifyComplete();

    StepVerifier
         .create(service.save(localPerson))
         .expectSubscription()
         .expectNext(localPerson)
         .verifyComplete();

    return localPerson;
  }


  public PersonJsonview personJsonview_save_check(IService<PersonJsonview> service) {
    PersonJsonview localPerson = PersonJsonviewBuilder.personWithIdAndNameJsonview().create();

    StepVerifier
         .create(service.deleteAll()
                        .log())
         .expectSubscription()
         .expectNextCount(0L)
         .verifyComplete();

    StepVerifier
         .create(service.save(localPerson))
         .expectSubscription()
         .expectNext(localPerson)
         .verifyComplete();

    return localPerson;
  }
}
