package com.tdd.parallel.service.tcCompose.standard;

import com.tdd.parallel.core.config.ServiceTemplateStandardCfg;
import com.tdd.parallel.entity.PersonStandard;
import com.tdd.parallel.service.IService;
import testsconfig.annotations.MergedService;
import testsconfig.tcCompose.TcComposeConfig;
import testsconfig.utils.TestDbUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import reactor.blockhound.BlockingOperationError;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static testsconfig.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("ServTemplStd")
@Import({ServiceTemplateStandardCfg.class})
@MergedService
public class ServTemplStd {

  //STATIC: one service for ALL tests -> SUPER FASTER
  //NON-STATIC: one service for EACH test
  @Container
  private final DockerComposeContainer<?> compose = new TcComposeConfig().getTcCompose();

  final private String enabledTest = "true";
  final private int repet = 1;

  private final TestDbUtils<PersonStandard> utils = new TestDbUtils<>();

  @Autowired
  private IService<PersonStandard> servTemplStandard;


  @BeforeAll
  public static void beforeAll(TestInfo testInfo) {
    globalBeforeAll();
    globalTestMessage(testInfo.getDisplayName(),"class-start");
  }


  @AfterAll
  public static void afterAll(TestInfo testInfo) {
    globalAfterAll();
    globalTestMessage(testInfo.getDisplayName(),"class-end");
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
    utils.personStandard_save_check(servTemplStandard);
  }


  @Test
  @DisplayName("FindAll")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findAll() {
    utils.personStandard_save_check(servTemplStandard);

    StepVerifier.create(
         servTemplStandard.findAll()
                          .log())
                .expectSubscription()
                .expectNextCount(1L)
                .verifyComplete();
  }


  @Test
  @DisplayName("FindById")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findById() {
    PersonStandard localPerson = utils.personStandard_save_check(servTemplStandard);

    StepVerifier
         .create(servTemplStandard.findById(localPerson.getId())
                                  .log())
         .expectSubscription()
         .expectNextMatches(item -> localPerson.getId()
                                               .equals(item.getId()))
         .verifyComplete();
  }


  @Test
  @DisplayName("DeleteById")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void deleteById() {
    PersonStandard localPerson = utils.personStandard_save_check(servTemplStandard);

    StepVerifier
         .create(servTemplStandard.deleteById(localPerson.getId()))
         .expectSubscription()
         .verifyComplete();

    StepVerifier
         .create(servTemplStandard.findById(localPerson.getId()))
         .expectSubscription()
         .expectNextCount(0L)
         .verifyComplete();
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

