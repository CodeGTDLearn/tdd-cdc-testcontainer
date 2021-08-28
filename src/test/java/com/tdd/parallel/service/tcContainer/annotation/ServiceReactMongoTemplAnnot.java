package com.tdd.parallel.service.tcContainer.annotation;

import com.tdd.parallel.entity.Person;
import com.tdd.parallel.service.IService;
import com.tdd.parallel.service.ServiceReactMongoTempl;
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
import static com.tdd.testsconfig.utils.TestsGlobalMethods.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

// KEEP: Anotacao de TcContainer
// - sera inocua, se imports staticos, da classe de extensao dessa anotacao, forem feitos
// - a classe de extensao da anotacao, tem static-automatic-initialization, por isso seu importe ja a inicia independente da anotacao
@DisplayName("ServiceReactiveTemplAnnot")
@Import({ServiceReactMongoTempl.class})
@MergedAnnotations
public class ServiceReactMongoTemplAnnot {

  final private String enabledTest = "true";
  final private int repet = 1;

  @Autowired
  private IService serviceReactMongoTempl;


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
    generatePerson_savePerson_testThisSaving();
  }


  @Test
  @DisplayName("FindAll")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findAll() {
    Person localPerson = generatePerson_savePerson_testThisSaving();

    StepVerifier.create(serviceReactMongoTempl.findAll()
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


  private Person generatePerson_savePerson_testThisSaving() {
    Person localPerson = personWithIdAndName().create();

    StepVerifier
         .create(serviceReactMongoTempl.save(localPerson))
         .expectSubscription()
         .expectNext(localPerson)
         .verifyComplete();

    return localPerson;
  }
}

