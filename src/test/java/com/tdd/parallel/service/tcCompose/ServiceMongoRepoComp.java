package com.tdd.parallel.service.tcCompose;

import com.tdd.parallel.entity.Person;
import com.tdd.parallel.service.IService;
import com.tdd.parallel.service.ServiceMongoRepo;
import com.tdd.testsconfig.globalAnnotations.GlobalConfig;
import com.tdd.testsconfig.globalAnnotations.MongoDbConfig;
import com.tdd.testsconfig.tcCompose.TcCompose;
import com.tdd.testsconfig.tcCompose.TcComposeConfig;
import com.tdd.testsconfig.tcContainer.annotations.TcContainer;
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

import static com.tdd.databuilder.PersonBuilder.personWithIdAndName;
import static com.tdd.testsconfig.tcContainer.annotations.TcContainerConfig.getTcContainerCustom;
import static com.tdd.testsconfig.tcContainer.annotations.TcContainerConfig.restartTestcontainer;
import static com.tdd.testsconfig.utils.TestsGlobalMethods.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


@DisplayName("ServiceMongoRepoAnnot")
@Import({ServiceMongoRepo.class})
@MongoDbConfig
@GlobalConfig
@TcCompose
public class ServiceMongoRepoComp {

  @Container
  private static final DockerComposeContainer<?> compose = new TcComposeConfig().tcCompose;

  final private String enabledTest = "true";
  final private int repet = 1;

  @Autowired
  private IService serviceMongoRepo;


  @BeforeAll
  public static void beforeAll(TestInfo testInfo) {
    globalBeforeAll();
    globalTestMessage(testInfo.getDisplayName(),"class-start");
    globalContainerMessage(getTcContainerCustom(),"container-start");
    globalComposeServiceContainerMessage(
         compose,
         TcComposeConfig.SERVICE,
         TcComposeConfig.SERVICE_PORT
                                        );
  }


  @AfterAll
  public static void afterAll(TestInfo testInfo) {
    globalAfterAll();
    globalTestMessage(testInfo.getDisplayName(),"class-end");
    globalContainerMessage(getTcContainerCustom(),"container-end");
    restartTestcontainer();
  }


  @BeforeEach
  public void setUp(TestInfo testInfo) {
    globalTestMessage(testInfo.getTestMethod()
                              .toString(),"method-start");


    globalContainerMessage(getTcContainerCustom(),"container-state");
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

    StepVerifier.create(serviceMongoRepo.findAll()
                                        .log())
                .thenConsumeWhile(person -> {
//                  System.out.println(person.getName());
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
    generatePerson_savePerson_testThisSaving();

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
    Person localPerson = generatePerson_savePerson_testThisSaving();

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
  @DisplayName("Check Service")
  @EnabledIf(expression = enabledTest, loadContext = true)
  void checkServices() {
    globalComposeServiceContainerMessage(
         compose,
         TcComposeConfig.SERVICE,
         TcComposeConfig.SERVICE_PORT
                                        );
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


  private Person generatePerson_savePerson_testThisSaving() {
    Person localPerson = personWithIdAndName().create();

    StepVerifier
         .create(serviceMongoRepo.save(localPerson))
         .expectSubscription()
         .expectNext(localPerson)
         .verifyComplete();

    return localPerson;
  }
}
