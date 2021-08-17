package com.tdd.parallel.service.nestedTestClasses;

import com.tdd.parallel.entity.Person;
import com.tdd.parallel.service.IService;
import com.tdd.parallel.service.ServiceCrudRepo;
import com.tdd.testsconfig.annotation.TestcontainerAnn;
import com.tdd.testsconfig.annotation.TestsGlobalConfigAnn;
import com.tdd.testsconfig.annotation.TestsMongoConfigAnn;
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
@TestcontainerAnn
@TestsMongoConfigAnn
@TestsGlobalConfigAnn
public class NestedTestClassLab {

  final private String enabledTest = "true";

  @Autowired
  IService serviceCrudRepo;


  @Test
  @DisplayName("is instantiated with new Stack()")
  void isInstantiatedWithNew() {
    new Stack<>();
  }


  @TestsMongoConfigAnn
  @Nested
  @DisplayName("SameThread")
  @Execution(SAME_THREAD)
  class SameThread {
    @Test
    @DisplayName("findAll1")
    @EnabledIf(expression = enabledTest, loadContext = true)
    public void test1findAll() {

      Person localPerson = generatePerson_savePerson_testThisSaving();

      StepVerifier.create(serviceCrudRepo.findAll()
                                         .log())
                  .thenConsumeWhile(person -> {
                    System.out.println(person.getName());
                    Assertions.assertEquals((person.getId()),localPerson.getId());
                    return true;
                  })
                  .verifyComplete();
    }


    @Test
    @DisplayName("findAll2")
    @EnabledIf(expression = enabledTest, loadContext = true)
    public void test2findAll() {

      Person localPerson = generatePerson_savePerson_testThisSaving();

      StepVerifier.create(serviceCrudRepo.findAll()
                                         .log())
                  .thenConsumeWhile(person -> {
                    System.out.println(person.getName());
                    Assertions.assertEquals((person.getId()),localPerson.getId());
                    return true;
                  })
                  .verifyComplete();
    }
  }

  @TestsMongoConfigAnn
  @Nested
  @DisplayName("Concurrent")
  @Execution(CONCURRENT)
  class Concurrent {


    @Test
    @DisplayName("findAll3")
    @EnabledIf(expression = enabledTest, loadContext = true)
    public void findAll3() {

      Person localPerson = generatePerson_savePerson_testThisSaving();

      StepVerifier.create(serviceCrudRepo.findAll()
                                         .log())
                  .thenConsumeWhile(person -> {
                    System.out.println(person.getName());
                    Assertions.assertEquals((person.getId()),localPerson.getId());
                    return true;
                  })
                  .verifyComplete();
    }
  }


  private Person getPerson() {
    return personWithIdAndName().create();
  }


  private Person generatePerson_savePerson_testThisSaving() {
    Person localPerson = getPerson();

    StepVerifier
         .create(serviceCrudRepo.save(localPerson))
         .expectSubscription()
         .expectNext(localPerson)
         .verifyComplete();

    return localPerson;
  }
}

