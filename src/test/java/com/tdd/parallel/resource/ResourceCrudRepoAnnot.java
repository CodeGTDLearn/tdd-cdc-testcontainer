package com.tdd.parallel.resource;

import com.tdd.parallel.entity.Person;
import com.tdd.parallel.service.IService;
import com.tdd.parallel.service.ServiceCrudRepo;
import com.tdd.testsconfig.annotation.TestcontainerAnn;
import com.tdd.testsconfig.annotation.TestsGlobalConfigAnn;
import com.tdd.testsconfig.annotation.TestsResourceConfigAnn;
import io.restassured.http.ContentType;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.blockhound.BlockingOperationError;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.tdd.databuilder.PersonBuilder.personWithIdAndName;
import static com.tdd.parallel.core.Routes.REQ_MAP;
import static com.tdd.testsconfig.TestsGlobalMethods.*;
import static com.tdd.testsconfig.annotation.TestcontainerConfigAnn.getTestcontainer;
import static com.tdd.testsconfig.annotation.TestcontainerConfigAnn.restartTestcontainer;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@DisplayName("ResourceCrudRepoAnnot")
@Import({ServiceCrudRepo.class})
@TestsResourceConfigAnn
@TestcontainerAnn
public class ResourceCrudRepoAnnot {

  final private String enabledTest = "false";
  final private int repet = 1;
  final ContentType CONT_ANY = ContentType.ANY;
  final ContentType CONT_JSON = ContentType.JSON;

  // WEB-TEST-CLIENT(non-blocking client)'
  // SHOULD BE USED WITH 'TEST-CONTAINERS'
  // BECAUSE THERE IS NO 'REAL-SERVER' CREATED VIA DOCKER-COMPOSE
  @Autowired
  WebTestClient mockedWebClient;

  @Autowired
  private IService serviceCrudRepo;


  @BeforeAll
  public static void beforeAll(TestInfo testInfo) {
    globalBeforeAll();
    globalTestMessage(testInfo.getTestClass()
                              .toString(),"class-start");
    globalContainerMessage(getTestcontainer(),"container-start");
  }


  @AfterAll
  public static void afterAll(TestInfo testInfo) {
    globalAfterAll();
    globalTestMessage(testInfo.getTestClass()
                              .toString(),"class-end");
    globalContainerMessage(getTestcontainer(),"container-end");
    restartTestcontainer();
  }


  @BeforeEach
  public void setUp(TestInfo testInfo) {
    globalTestMessage(testInfo.getTestMethod()
                              .toString(),"method-start");

    RestAssuredWebTestClient.reset();
    //REAL-SERVER INJECTED IN WEB-TEST-CLIENT(non-blocking client)'
    //SHOULD BE USED WHEN 'DOCKER-COMPOSE' UP A REAL-WEB-SERVER
    //BECAUSE THERE IS 'REAL-SERVER' CREATED VIA DOCKER-COMPOSE
    // realWebClient = WebTestClient.bindToServer()
    //                      .baseUrl("http://localhost:8080/customer")
    //                      .build();
  }


  @AfterEach
  void tearDown(TestInfo testInfo) {
    globalTestMessage(testInfo.getTestMethod()
                              .toString(),"method-end");
  }


  @Test
  @DisplayName("Container")
  @EnabledIf(expression = "true", loadContext = true)
  public void checkContainer() {
    assertTrue(getTestcontainer()
                    .isRunning());
  }


  @Test
  @DisplayName("FindAll")
  @EnabledIf(expression = "true", loadContext = true)
  void findAll() {
    Person localPerson = generatePerson_savePerson_testThisSaving();

    RestAssuredWebTestClient
         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .when()
         .get(REQ_MAP)

         .then()
         .statusCode(OK.value())
         .log()
         .headers()
         .and()
         .log()

         .body()
         .body("size()",is(1))
         .body("id",hasItem(localPerson.getId()))
    ;
  }


  //  @Test
  //  @DisplayName("Save")
  //  @EnabledIf(expression = enabledTest, loadContext = true)
  //  public void save() {
  //  }
  //
  //
  //  @Test
  //  @DisplayName("FindById")
  //  @EnabledIf(expression = enabledTest, loadContext = true)
  //  public void findById() {
  //  }
  //
  //
  //  @Test
  //  @DisplayName("DeleteAll")
  //  @EnabledIf(expression = enabledTest, loadContext = true)
  //  public void deleteAll() {
  //  }
  //
  //
  //  @Test
  //  @DisplayName("DeleteById")
  //  @EnabledIf(expression = enabledTest, loadContext = true)
  //  public void deleteById() {
  //  }


  @Test
  @DisplayName("BHWorks")
  @EnabledIf(expression = "true", loadContext = true)
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
         .create(serviceCrudRepo.deleteAll()
                                .log())
         .expectSubscription()
         .expectNextCount(0L)
         .verifyComplete();

    StepVerifier
         .create(serviceCrudRepo.save(localPerson))
         .expectSubscription()
         .expectNext(localPerson)
         .verifyComplete();

    return localPerson;
  }
}
