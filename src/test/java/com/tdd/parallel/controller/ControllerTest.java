package com.tdd.parallel.controller;

import com.tdd.parallel.entity.Person;
import com.tdd.parallel.service.IService;
import com.tdd.testconfig.annotation.CustomControllerConfig;
import com.tdd.testconfig.annotation.CustomTestcontainerConfigClass;
import com.tdd.testconfig.simple.TestsConfigSimple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

//@Import(ServiceTemplateRepoCfg.class)
@Slf4j
@DisplayName("Controller")
@CustomControllerConfig
public class ControllerTest {

  final private String enabledTest = "true";
  final private int repet = 1;
  private List<Person> personList;
  private Mono<Person> personMono;

  @Autowired
  private IService serviceTemplateRepo;


  @BeforeAll
  public static void beforeAll() {
    TestsConfigSimple.beforeAll();
    TestsConfigSimple.testHeader("STARTING TEST-CLASS","Name:",
                                 ControllerTest.class.getSimpleName()
                                );
  }


  @AfterAll
  public static void afterAll() {
    TestsConfigSimple.afterAll();
    TestsConfigSimple.testHeader("ENDING TEST-CLASS","Name:",
                                 ControllerTest.class.getSimpleName()
                                );
  }


  @BeforeEach
  public void setUp(TestInfo testInfo) {
    TestsConfigSimple.testHeader("STARTING TEST","Method-Name:",
                                 testInfo.getTestMethod()
                                         .toString()
                                );

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

    TestsConfigSimple.testHeader("ENDING TEST","Method-Name:",
                                 testInfo.getTestMethod()
                                         .toString()
                                );
  }


  //  @Test
  @RepeatedTest(value = repet)
  @DisplayName("SaveAll")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void saveAll() {
  }


  //  @Test
  @RepeatedTest(repet)
  @DisplayName("Save")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void save() {
  }


  @Test
  //  @RepeatedTest(repet)
  @DisplayName("FindAll")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findAll() {
  }


  @Test
  //  @RepeatedTest(repet)
  @DisplayName("FindById")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findById() {
  }


  @Test
  //  @RepeatedTest(repet)
  @DisplayName("DeleteAll")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void deleteAll() {
  }


  @Test
  //  @RepeatedTest(repet)
  @DisplayName("DeleteById")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void deleteById() {
  }


  @Test
  @DisplayName("Container")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void checkContainer() {
    assertTrue(CustomTestcontainerConfigClass.getContainer()
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
}

