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


  public static void globalTestMessage(String title,String label,String subTitle) {
    if (subTitle.contains("repetition"))
      subTitle = "Error: Provide TestInfo testInfo.getTestMethod().toString()";
    if (subTitle.contains("()]")) {
      subTitle = subTitle.replace("()]","");
      subTitle = subTitle.substring(subTitle.lastIndexOf(".") + 1);
      subTitle = subTitle.substring(0,1)
                         .toUpperCase() + subTitle.substring(1);
    }

    System.out.printf(
         "\n\n╔════════════════════════╣ %s ╠════════════════════════╣\n" +
              "║ --> %s %s\n" +
              "╚════════════════════════╣ %s ╠════════════════════════╣\n\n%n",
         title,
         label,subTitle,
         title
                     );
  }


  public static void globalContainerMessage(String title,MongoDBContainer container) {
    System.out.printf(
         "\n\n╔═════════════════════════════╣ %s ╠═════════════════════════════╣\n" +
              "║ --> Name: %s\n" +
              "║ --> Url: %s\n" +
              "║ --> Running: %s\n" +
              "╚═════════════════════════════╣ %s ╠═════════════════════════════╣\n\n",
         title,
         container.getContainerName(),
         container.getReplicaSetUrl(),
         container.isRunning(),
         title
                     );
  }
}




