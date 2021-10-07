package com.tdd.parallel.service.tcContainer.annotation;

import com.tdd.parallel.core.config.ServiceTemplateStandardCfg;
import com.tdd.parallel.entity.PersonStandard;
import com.tdd.parallel.service.IService;
import testsconfig.annotations.MergedTcContainer;
import testsconfig.utils.TestDbUtils;
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

import static testsconfig.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


// KEEP: Anotacao de TcContainer
// - sera inocua, se imports staticos, da classe de extensao dessa anotacao, forem feitos
// - a classe de extensao da anotacao, tem static-automatic-initialization, por isso seu importe
// ja a inicia independente da anotacao
@DisplayName("ServTemplAnnot")
@Import({ServiceTemplateStandardCfg.class})
@MergedTcContainer
public class ServTemplAnnot {

  final private String enabledTest = "true";
  final private int repet = 1;

  private final TestDbUtils<PersonStandard> utils = new TestDbUtils<>();

  @Autowired
  private IService<PersonStandard> serviceTemplateRepo;


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
    utils.personStandard_save_check(serviceTemplateRepo);
  }


  @Test
  @DisplayName("FindAll")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findAll() {
    PersonStandard localPerson = utils.personStandard_save_check(serviceTemplateRepo);

    StepVerifier.create(serviceTemplateRepo.findAll()
                                           .log())
                .thenConsumeWhile(person -> {
                  Assertions.assertEquals((person.getId()),localPerson.getId());
                  return true;
                })
                .verifyComplete();
  }


  @Test
  @DisplayName("FindById")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findById() {
    PersonStandard localPerson = utils.personStandard_save_check(serviceTemplateRepo);

    StepVerifier
         .create(serviceTemplateRepo.findById(localPerson.getId())
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
    PersonStandard localPerson = utils.personStandard_save_check(serviceTemplateRepo);

    StepVerifier
         .create(serviceTemplateRepo.deleteById(localPerson.getId()))
         .expectSubscription()
         .verifyComplete();

    StepVerifier
         .create(serviceTemplateRepo.findById(localPerson.getId()))
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

