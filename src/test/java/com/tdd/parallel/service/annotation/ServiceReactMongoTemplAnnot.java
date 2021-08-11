package com.tdd.parallel.service.annotation;

import com.tdd.parallel.core.config.ServiceReactMongoTemplCfg;
import com.tdd.parallel.entity.Person;
import com.tdd.parallel.service.IService;
import com.tdd.testsconfig.annotation.TestcontainerAnn;
import com.tdd.testsconfig.annotation.TestsGlobalAnn;
import com.tdd.testsconfig.annotation.TestsMongoConfigAnn;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import reactor.blockhound.BlockingOperationError;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.tdd.databuilder.PersonBuilder.personWithIdAndName;
import static com.tdd.testsconfig.TestsGlobalMethods.*;
import static com.tdd.testsconfig.annotation.TestcontainerConfigAnn.getTestcontainer;
import static com.tdd.testsconfig.annotation.TestcontainerConfigAnn.restartTestcontainer;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("ServiceReactiveTemplAnnot")
@Import(ServiceReactMongoTemplCfg.class)
@TestcontainerAnn
@TestsMongoConfigAnn
@TestsGlobalAnn
public class ServiceReactMongoTemplAnnot {

  final private String enabledTest = "true";
  final private int repet = 1;

  @Autowired
  private IService serviceReactMongoTempl;


  @BeforeAll
  public static void beforeAll() {
    globalBeforeAll();
    globalTestMessage(ServiceReactMongoTemplAnnot.class.getSimpleName(),"class-start");
    globalContainerMessage(getTestcontainer(),"container-start");
  }


  @AfterAll
  public static void afterAll() {
    globalAfterAll();
    globalTestMessage(ServiceReactMongoTemplAnnot.class.getSimpleName(),"class-end");
    globalContainerMessage(getTestcontainer(),"container-end");
    restartTestcontainer();
  }


  @BeforeEach
  public void setUp(TestInfo testInfo) {
    globalTestMessage(testInfo.getTestMethod()
                              .toString(),"method-start");
  }


  @AfterEach
  void tearDown(TestInfo testInfo) {
    globalTestMessage(testInfo.getTestMethod()
                              .toString(),"method-end");
  }


  @RepeatedTest(repet)
  @DisplayName("Save")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void save() {
    generatePerson_savePerson_testThisSaving();
  }


  @Test
  @DisplayName("FindAll")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findAll() {
    generatePerson_savePerson_testThisSaving();

    StepVerifier.create(
         serviceReactMongoTempl.findAll()
                               .log())
                .expectSubscription()
                .expectNextCount(1L)
                .verifyComplete();
  }


  @Test
  @DisplayName("FindById")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findById() {
    Person localPerson = generatePerson_savePerson_testThisSaving();

    StepVerifier
         .create(serviceReactMongoTempl.findById(localPerson.getId())
                                       .log())
         .expectSubscription()
         .expectNextMatches(item -> localPerson.getId()
                                               .equals(item.getId()))
         .verifyComplete();
  }


  @Test
  @DisplayName("DeleteAll")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void deleteAll() {
    generatePerson_savePerson_testThisSaving();

    StepVerifier.create(serviceReactMongoTempl.deleteAll())
                .verifyComplete();

    StepVerifier
         .create(serviceReactMongoTempl.findAll()
                                       .log())
         .expectSubscription()
         .expectNextCount(0L)
         .verifyComplete();
  }


  @Test
  @DisplayName("DeleteById")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void deleteById() {
    Person localPerson = generatePerson_savePerson_testThisSaving();

    StepVerifier
         .create(serviceReactMongoTempl.deleteById(localPerson.getId()))
         .expectSubscription()
         .verifyComplete();

    StepVerifier
         .create(serviceReactMongoTempl.findById(localPerson.getId()))
         .expectSubscription()
         .expectNextCount(0L)
         .verifyComplete();
  }


  @Test
  @DisplayName("Container")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void checkContainer() {
    assertTrue(getTestcontainer()
                    .isRunning());
  }


  @Test
  @DisplayName("BHWorks")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void bHWorks() {
    try {
      FutureTask<?> task = new FutureTask<>(() -> {
        Thread.sleep(0);
        return "";
      });

      Schedulers.parallel()
                .schedule(task);

      task.get(10,TimeUnit.SECONDS);
      Assertions.fail("should fail");
    } catch (ExecutionException | InterruptedException | TimeoutException e) {
      assertTrue(e.getCause() instanceof BlockingOperationError,"detected");
    }
  }


  private Person getPerson() {
    return personWithIdAndName().create();
  }


  private Person generatePerson_savePerson_testThisSaving() {
    Person localPerson = getPerson();

    StepVerifier
         .create(serviceReactMongoTempl.save(localPerson))
         .expectSubscription()
         .expectNext(localPerson)
         .verifyComplete();

    return localPerson;
  }
}

