package com.tdd.parallel.service.annotation;

import com.tdd.parallel.core.config.ServiceCrudRepoCfg;
import com.tdd.parallel.entity.Person;
import com.tdd.parallel.service.IService;
import com.tdd.testsconfig.annotation.TestcontainerConfig;
import com.tdd.testsconfig.annotation.TestsGlobalAnnotations;
import com.tdd.testsconfig.annotation.TestsMongoConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.tdd.databuilder.PersonBuilder.personWithIdAndName;
import static com.tdd.testsconfig.annotation.TestcontainerConfigClass.getTestcontainer;
import static com.tdd.testsconfig.annotation.TestcontainerConfigClass.testcontainerHeader;
import static com.tdd.testsconfig.annotation.TestsGlobalMethods.*;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("ServiceCrudRepoAnnot")
@Import(ServiceCrudRepoCfg.class)
@TestcontainerConfig
@TestsMongoConfig
@TestsGlobalAnnotations
public class ServiceCrudRepoAnnot {

  final private String enabledTest = "true";
  final private int repet = 1;
  private Person person1;

  @Autowired
  private IService serviceCrudRepo;


  @BeforeAll
  public static void beforeAll() {
    globalBeforeAll();
    generalTestMessage("STARTING TEST-CLASS...","Name:",
                       ServiceCrudRepoAnnot.class.getSimpleName()
                      );
  }


  @AfterAll
  public static void afterAll() {
    globalAfterAll();
    generalTestMessage("...ENDING TEST-CLASS","Name:",
                       ServiceCrudRepoAnnot.class.getSimpleName()
                      );
  }


  @BeforeEach
  public void setUp(TestInfo testInfo) {
    generalTestMessage("STARTING TEST","Method-Name:",
                       testInfo.getTestMethod()
                               .toString()
                      );

    testcontainerHeader("STARTING TEST-CONTAINER...",getTestcontainer());

    person1 = personWithIdAndName().create();

    StepVerifier
         .create(serviceCrudRepo.save(person1)
                                .log())
         .expectNext(person1)
         .verifyComplete();
  }


  @AfterEach
  void tearDown(TestInfo testInfo) {
    StepVerifier
         .create(serviceCrudRepo.deleteAll()
                                .log())
         .expectSubscription()
         .expectNextCount(0L)
         .verifyComplete();

    generalTestMessage("ENDING TEST","Method-Name:",
                       testInfo.getTestMethod()
                               .toString()
                      );

  }


  @RepeatedTest(repet)
  @DisplayName("Save")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void save() {
    Person person = personWithIdAndName().create();
    Mono<Person> monoToSave = serviceCrudRepo.save(person);
    StepVerifier
         .create(monoToSave)
         .expectSubscription()
         .expectNext(person)
         .verifyComplete();
  }


  @RepeatedTest(value = repet)
  @DisplayName("SaveAll")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void saveAll() {
    List<Person> list = Arrays.asList(
         personWithIdAndName().create(),
         personWithIdAndName().create()
                                     );
    var flux = serviceCrudRepo.saveAll(list);
    StepVerifier.create(flux.log())
                .expectNextSequence(list)
                .verifyComplete();
  }


  @Test
  @DisplayName("FindAll")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findAll() {
    StepVerifier.create(
         serviceCrudRepo.findAll()
                        .log())
                .expectSubscription()
                .expectNextCount(1L)
                .verifyComplete();
  }


  @Test
  @DisplayName("FindById")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findById() {
    StepVerifier
         .create(serviceCrudRepo.findById(person1.getId())
                                .log())
         .expectSubscription()
         .expectNextMatches(person -> person1.getId()
                                             .equals(person.getId()))
         .verifyComplete();
  }


  @Test
  @DisplayName("DeleteAll")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void deleteAll() {
    StepVerifier
         .create(serviceCrudRepo.findAll()
                                .log())
         .expectSubscription()
         .expectNextCount(1L)
         .verifyComplete();

    StepVerifier.create(serviceCrudRepo.deleteAll())
                .verifyComplete();

    StepVerifier
         .create(serviceCrudRepo.findAll()
                                .log())
         .expectSubscription()
         .expectNextCount(0L)
         .verifyComplete();
  }


  @Test
  @DisplayName("DeleteById")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void deleteById() {
    StepVerifier
         .create(serviceCrudRepo.deleteById(person1.getId()))
         .expectSubscription()
         .verifyComplete();

    Mono<Person> personMono = serviceCrudRepo.findById(person1.getId());

    StepVerifier
         .create(personMono)
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
      fail("should fail");
    } catch (ExecutionException | InterruptedException | TimeoutException e) {
      assertTrue(e.getCause() instanceof BlockingOperationError,"detected");
    }
  }
}

