package com.tdd.testsconfig;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import io.restassured.module.webtestclient.specification.WebTestClientRequestSpecBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MongoDBContainer;

import static org.hamcrest.Matchers.lessThanOrEqualTo;

@Slf4j
public class TestsGlobalMethods {

  final static Long MAX_TIMEOUT = 15000L;
  final static ContentType JSON_CONTENT_TYPE = ContentType.JSON;


  @BeforeAll
  public static void globalBeforeAll() {
    BlockhoundManage.blockhoundInstallSimple();

    //DEFINE CONFIG-GLOBAL PARA OS REQUESTS DOS TESTES
    RestAssuredWebTestClient.requestSpecification =
         new WebTestClientRequestSpecBuilder()
              .setContentType(JSON_CONTENT_TYPE)
              .build();


    //DEFINE CONFIG-GLOBAL PARA OS RESPONSE DOS TESTES
    RestAssuredWebTestClient.responseSpecification =
         new ResponseSpecBuilder()
              .expectResponseTime(lessThanOrEqualTo(MAX_TIMEOUT))
              .build();


  }


  @AfterAll
  public static void globalAfterAll() {
    RestAssuredWebTestClient.reset();
  }


  public static void globalTestMessage(String subTitle,String testType) {


    if (subTitle.contains("repetition"))
      subTitle = "Error: Provide TestInfo testInfo.getTestMethod().toString()";

    if (subTitle.contains("()]")) {
      subTitle = subTitle.replace("()]","");
      subTitle = subTitle.substring(subTitle.lastIndexOf(".") + 1);
      subTitle = subTitle.substring(0,1)
                         .toUpperCase() + subTitle.substring(1);
    }

    String title = "";

    switch (testType.toLowerCase()) {
      case "class-start":
        title = "STARTING TEST-CLASS....";
        break;

      case "class-end":
        title = "...END TEST-CLASS";
        break;

      case "method-start":
        title = "STARTING TEST-METHOD...";
        break;

      case "method-end":
        title = "...END TEST-METHOD";
        break;
    }

    System.out.printf(
         "%n%n" +
              "╔════════════════════════════════════════════════════════════════════╗%n" +
              "║                         %s                                         ║%n" +
              "║ --> Name: %s %38s%n" +
              "╚════════════════════════════════════════════════════════════════════╝%n%n%n",
         title,subTitle,"║"
                     );
  }


  public static void globalContainerMessage(MongoDBContainer container,String typeTestMessage) {
    switch (typeTestMessage.toLowerCase()) {
      case "container-start":
        System.out.printf(
             "%n%n" +
                  "╔═══════════════════════════════════════════════════════════════════════╗%n" +
                  "║                           STARTING TEST-CONTAINER...                  ║%n" +
                  "║ --> Name: %s\n" +
                  "║ --> Url: %s\n" +
                  "║ --> Running: %s\n" +
                  "╚═══════════════════════════════════════════════════════════════════════╝%n%n",
             container.getContainerName(),
             container.getReplicaSetUrl(),
             container.isRunning()
                         );
        break;

      case "container-end":
        System.out.printf(
             "%n%n" +
                  "╔═══════════════════════════════════════════════════════════════════════╗%n" +
                  "║                          ...END TEST-CONTAINER                        ║%n" +
                  "║ --> Name: %s\n" +
                  "║ --> Url: %s\n" +
                  "║ --> Running: %s\n" +
                  "╚═══════════════════════════════════════════════════════════════════════╝%n%n",
             container.getContainerName(),
             container.getReplicaSetUrl(),
             container.isRunning()
                         );
        break;
    }
  }
}




