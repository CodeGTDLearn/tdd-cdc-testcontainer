package com.tdd.parallel.service.annotation;

import com.tdd.parallel.core.config.ServiceMongoRepoCfg;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("ServiceMongoRepoAnnot")
@Import(ServiceMongoRepoCfg.class)
@TestcontainerAnn
@TestsMongoConfigAnn
@TestsGlobalAnn
public class ServiceMongoRepoAnnot {

  final private String enabledTest = "true";
  final private int repet = 1;

  @Autowired
  private IService serviceMongoRepo;


  @BeforeAll
  public static void beforeAll() {
    globalBeforeAll();
    globalTestMessage("STARTING TEST-CLASS","Name:",
                      ServiceMongoRepoAnnot.class.getSimpleName()
                     );
  }


  @AfterAll
  public static void afterAll() {
    globalAfterAll();
    globalTestMessage("...ENDING TEST-CLASS","Name:",
                      ServiceCrudRepoAnnot.class.getSimpleName()
                     );
  }


  @BeforeEach
  public void setUp(TestInfo testInfo) {
    globalTestMessage("STARTING TEST","Method-Name:",
                      testInfo.getTestMethod()
                              .toString()
                     );

    globalContainerMessage("STARTING TEST-CONTAINER...",getTestcontainer());
  }


  @AfterEach
  void tearDown(TestInfo testInfo) {
    StepVerifier
         .create(serviceMongoRepo.deleteAll()
                                 .log())
         .expectSubscription()
         .expectNextCount(0L)
         .verifyComplete();

    globalTestMessage("ENDING TEST","Method-Name:",
                      testInfo.getTestMethod()
                              .toString()
                     );

    globalContainerMessage("...ENDING TEST-CONTAINER...",getTestcontainer());
  }


  @RepeatedTest(repet)
  @DisplayName("Save")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void save() {
    generateAndSavePerson();
  }


  @Test
  @DisplayName("FindAll")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findAll() {
    generateAndSavePerson();

    StepVerifier.create(
         serviceMongoRepo.findAll()
                         .log())
                .expectSubscription()
                .expectNextCount(1L)
                .verifyComplete();
  }


  @Test
  @DisplayName("FindById")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findById() {
    Person localPerson = generateAndSavePerson();

    StepVerifier
         .create(serviceMongoRepo.findById(localPerson.getId())
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
    generateAndSavePerson();

    StepVerifier
         .create(serviceMongoRepo.findAll()
                                 .log())
         .expectSubscription()
         .expectNextCount(1L)
         .verifyComplete();

    StepVerifier.create(serviceMongoRepo.deleteAll())
                .verifyComplete();

    StepVerifier
         .create(serviceMongoRepo.findAll()
                                 .log())
         .expectSubscription()
         .expectNextCount(0L)
         .verifyComplete();
  }


  @Test
  @DisplayName("DeleteById")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void deleteById() {
    Person localPerson = generateAndSavePerson();

    StepVerifier
         .create(serviceMongoRepo.deleteById(localPerson.getId()))
         .expectSubscription()
         .verifyComplete();

    StepVerifier
         .create(serviceMongoRepo.findById(localPerson.getId()))
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


  private Person generateAndSavePerson() {
    Person localPerson = getPerson();

    StepVerifier
         .create(serviceMongoRepo.save(localPerson))
         .expectSubscription()
         .expectNext(localPerson)
         .verifyComplete();

    return localPerson;
  }
}

