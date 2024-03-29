package com.tdd.parallel.service.tcCompose.jsonview;

import com.tdd.parallel.core.config.ServiceTemplateJsonviewCfg;
import com.tdd.parallel.entity.PersonJsonview;
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


@DisplayName("ServTemplJview")
@Import({ServiceTemplateJsonviewCfg.class})
@MergedService
public class ServTemplJview {

  //STATIC: one service for ALL tests -> SUPER FASTER
  //NON-STATIC: one service for EACH test
  @Container
  private final DockerComposeContainer<?> compose = new TcComposeConfig().getTcCompose();

  final private String enabledTest = "true";
  final private int repet = 1;

  private final TestDbUtils<PersonJsonview> utils = new TestDbUtils<>();

  @Autowired
  private IService<PersonJsonview> servTemplJsonview;


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
    utils.personJsonview_save_check(servTemplJsonview);
  }


  @Test
  @DisplayName("FindById")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findById() {
    PersonJsonview localPerson = utils.personJsonview_save_check(servTemplJsonview);

    StepVerifier
         .create(servTemplJsonview.findById(localPerson.getId())
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
    PersonJsonview localPerson = utils.personJsonview_save_check(servTemplJsonview);

    StepVerifier
         .create(servTemplJsonview.deleteById(localPerson.getId()))
         .expectSubscription()
         .verifyComplete();

    StepVerifier
         .create(servTemplJsonview.findById(localPerson.getId()))
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


  @Test
  @DisplayName("findAll")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findAll() {
    PersonJsonview localPerson = utils.personJsonview_save_check(servTemplJsonview);

    StepVerifier.create(servTemplJsonview.findAll()
                                         .log())
                .thenConsumeWhile(person -> {
                  //                  System.out.println(person.getName());
                  Assertions.assertEquals((person.getId()),localPerson.getId());
                  return true;
                })
                .verifyComplete();
  }
}

