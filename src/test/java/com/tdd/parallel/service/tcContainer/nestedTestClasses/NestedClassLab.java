package com.tdd.parallel.service.tcContainer.nestedTestClasses;

import com.tdd.parallel.entity.PersonStandard;
import com.tdd.parallel.service.IService;
import com.tdd.parallel.service.standard.ServCrudStandard;
import testsconfig.annotations.GlobalConfig;
import testsconfig.annotations.MongoDbConfig;
import testsconfig.tcContainer.annotations.TcContainer;
import testsconfig.utils.TestDbUtils;
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

import static testsconfig.databuilder.PersonStandardBuilder.personWithIdAndNameStandard;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static org.junit.jupiter.api.parallel.ExecutionMode.SAME_THREAD;

@DisplayName("NestedClassLab")
@Import({ServCrudStandard.class})
@TcContainer
@MongoDbConfig
@GlobalConfig
public class NestedClassLab {

  final private String enabledTest = "true";

  private final TestDbUtils<PersonStandard> utils = new TestDbUtils<>();

  @Autowired
  IService<PersonStandard> serviceCrudRepo;


  @Test
  @DisplayName("is instantiated with new Stack()")
  void isInstantiatedWithNew() {
    new Stack<>();
  }


  private PersonStandard getPerson() {
    return personWithIdAndNameStandard().create();
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

      PersonStandard localPerson = utils.personStandard_save_check(serviceCrudRepo);

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

      PersonStandard localPerson = utils.personStandard_save_check(serviceCrudRepo);

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

      PersonStandard localPerson = utils.personStandard_save_check(serviceCrudRepo);

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
}

