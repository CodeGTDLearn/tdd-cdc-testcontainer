package com.tdd.parallel.service.inheritance;

import com.tdd.parallel.core.config.arquive.ServiceTemplateRepoCfg;
import com.tdd.parallel.entity.Person;
import com.tdd.parallel.service.IService;
import com.tdd.testsconfig.inheritance.TestscontainerConfigInhe;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.tdd.databuilder.PersonBuilder.personWithIdAndName;
import static com.tdd.testsconfig.TestsGlobalMethods.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@DisplayName("ServiceTemplateRepoInhe")
@Import({ServiceTemplateRepoCfg.class})
public class ServiceTemplateRepoInhe extends TestscontainerConfigInhe {

  final private String enabledTest = "true";
  final private int repet = 1;
  private List<Person> personList;
  private Mono<Person> personMono;

  @Autowired
  private IService serviceTemplateRepo;


  @BeforeAll
  public static void beforeAll() {
    globalBeforeAll();
    globalTestMessage(ServiceCrudRepoInhe.class.getSimpleName(),"class-start");
  }


  @AfterAll
  public static void afterAll() {
    globalAfterAll();
    globalTestMessage(ServiceCrudRepoInhe.class.getSimpleName(),"class-end");
  }


  @BeforeEach
  public void setUp(TestInfo testInfo) {
    globalTestMessage(testInfo.getTestMethod()
                              .toString(),"method-start");
    Person person1 = personWithIdAndName().create();
    personList = Collections.singletonList(person1);
    personMono = Mono.just(person1);
    StepVerifier
         .create(serviceTemplateRepo.save(person1)
                                    .log())
         .expectNext(person1)
         .verifyComplete();
  }


  @AfterEach
  void tearDown(TestInfo testInfo) {
    StepVerifier
         .create(serviceTemplateRepo.deleteAll()
                                    .log())
         .expectSubscription()
         .expectNextCount(0L)
         .verifyComplete();

    globalTestMessage(testInfo.getTestMethod()
                              .toString(),"method-end");
  }


  @RepeatedTest(repet)
  @DisplayName("Save")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void save() {
    StepVerifier
         .create(personMono)
         .expectSubscription()
         .expectNextCount(1L)
         .verifyComplete();
  }


  @Test
  @DisplayName("FindAll")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findAll() {
    StepVerifier.create(personMono)
                .expectNextSequence(personList)
                .verifyComplete();

    StepVerifier
         .create(serviceTemplateRepo.findAll()
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
         .create(personMono.log())
         .expectSubscription()
         .expectNextMatches(person -> personList.get(0)
                                                .getName()
                                                .equals(person.getName()))
         .verifyComplete();
  }


  @Test
  @DisplayName("DeleteAll")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void deleteAll() {
    StepVerifier.create(serviceTemplateRepo.deleteAll())
                .verifyComplete();

    StepVerifier
         .create(serviceTemplateRepo.findAll()
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
         .create(serviceTemplateRepo.deleteById(personList.get(0)
                                                          .getId()))
         .expectSubscription()
         .verifyComplete();

    Mono<Person> personMono = serviceTemplateRepo.findById(personList.get(0)
                                                                     .getId());

    StepVerifier
         .create(personMono)
         .expectSubscription()
         .expectNextCount(0L)
         .verifyComplete();
  }


  //  @Test
  //  @DisplayName("Container")
  //  @EnabledIf(expression = enabledTest, loadContext = true)
  //  public void checkContainer() {
  //    assertTrue(TestcontainerConfigClass.getContainerAnn()
  //                                       .isRunning());
  //  }


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
}

