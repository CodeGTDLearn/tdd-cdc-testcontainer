package com.tdd.parallel.service.tcContainer.nestedTestClasses;

import com.tdd.parallel.entity.PersonStandard;
import com.tdd.parallel.service.IService;
import com.tdd.parallel.service.ServiceCrudRepo;
import com.tdd.testsconfig.tcContainer.annotations.TcContainer;
import com.tdd.testsconfig.globalAnnotations.GlobalConfig;
import com.tdd.testsconfig.globalAnnotations.MongoDbConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import reactor.test.StepVerifier;

import java.util.Stack;

import static com.tdd.databuilder.PersonBuilder.personWithIdAndName;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static org.junit.jupiter.api.parallel.ExecutionMode.SAME_THREAD;

@DisplayName("NestedTestClassLab")
@Import({ServiceCrudRepo.class})
@TcContainer
@MongoDbConfig
@GlobalConfig
public class NestedTestClassLab {

  final private String enabledTest = "true";

  @Autowired
  IService serviceCrudRepo;


  @Test
  @DisplayName("is instantiated with new Stack()")
  void isInstantiatedWithNew() {
    new Stack<>();
  }


  @MongoDbConfig
  @Nested
  @DisplayName("SameThread")
  @Execution(SAME_THREAD)
  class SameThread {
    @Test
    @DisplayName("findAll1")
    @EnabledIf(expression = enabledTest, loadContext = true)
    public void test1findAll() {

      PersonStandard localPerson = generatePerson_savePerson_testThisSaving();

      StepVerifier.create(serviceCrudRepo.findAll()
                                         .log())
                  .thenConsumeWhile(person -> {
//                    System.out.println(person.getName());
                    Assertions.assertEquals((person.getId()),localPerson.getId());
                    return true;
                  })
                  .verifyComplete();
    }


    @Test
    @DisplayName("findAll2")
    @EnabledIf(expression = enabledTest, loadContext = true)
    public void test2findAll() {

      PersonStandard localPerson = generatePerson_savePerson_testThisSaving();

      StepVerifier.create(serviceCrudRepo.findAll()
                                         .log())
                  .thenConsumeWhile(person -> {
//                    System.out.println(person.getName());
                    Assertions.assertEquals((person.getId()),localPerson.getId());
                    return true;
                  })
                  .verifyComplete();
    }
  }

  @MongoDbConfig
  @Nested
  @DisplayName("Concurrent")
  @Execution(CONCURRENT)
  class Concurrent {


    @Test
    @DisplayName("findAll3")
    @EnabledIf(expression = enabledTest, loadContext = true)
    public void findAll3() {

      PersonStandard localPerson = generatePerson_savePerson_testThisSaving();

      StepVerifier.create(serviceCrudRepo.findAll()
                                         .log())
                  .thenConsumeWhile(person -> {
//                    System.out.println(person.getName());
                    Assertions.assertEquals((person.getId()),localPerson.getId());
                    return true;
                  })
                  .verifyComplete();
    }
  }


  private PersonStandard getPerson() {
    return personWithIdAndName().create();
  }


  private PersonStandard generatePerson_savePerson_testThisSaving() {
    PersonStandard localPerson = getPerson();

    StepVerifier
         .create(serviceCrudRepo.save(localPerson))
         .expectSubscription()
         .expectNext(localPerson)
         .verifyComplete();

    return localPerson;
  }
}

