package com.tdd.testconfig.annotation;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import io.restassured.module.webtestclient.specification.WebTestClientRequestSpecBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.Extension;
import reactor.blockhound.BlockHound;

import static com.tdd.testconfig.annotation.CustomTestcontainerConfigClass.containerHeader;

@Slf4j
public class CustomTestsConfigClass implements Extension {

  final static Long MAX_TIMEOUT = 15000L;
  final static ContentType JSON_CONTENT_TYPE = ContentType.JSON;


  @BeforeAll
  public static void beforeAll() {
    BlockHound.install(
         builder -> builder
              .allowBlockingCallsInside("java.io.PrintStream",
                                        "write"
                                       ));

    //DEFINE CONFIG-GLOBAL PARA OS REQUESTS DOS TESTES
    RestAssuredWebTestClient.requestSpecification =
         new WebTestClientRequestSpecBuilder()
              .setContentType(JSON_CONTENT_TYPE)
              .build();


    //DEFINE CONFIG-GLOBAL PARA OS RESPONSE DOS TESTES
    RestAssuredWebTestClient.responseSpecification =
         new ResponseSpecBuilder()
              .expectResponseTime(
                   Matchers.lessThanOrEqualTo(MAX_TIMEOUT))
              .build();

    containerHeader("BEFORE-ALL");
  }


  @AfterAll
  public static void afterAll() {
    RestAssuredWebTestClient.reset();
    containerHeader("AFTER-ALL");
  }


  public static void testHeader(String title,String label,String subTitle) {
    if (subTitle.contains("repetition"))
      subTitle = "Error: Provide TestInfo testInfo.getTestMethod().toString()";
    if (subTitle.contains("()]")) {
      subTitle = subTitle.replace("()]","");
      subTitle = subTitle.substring(subTitle.lastIndexOf(".") + 1);
      subTitle = subTitle.substring(0,1)
                         .toUpperCase() + subTitle.substring(1);
    }

    System.out.printf(
         "\n\n>=====================< %s >=====================<\n" +
              " -->  %s %s\n" +
              ">=====================< %s >=====================<\n\n%n",
         title,label,subTitle,title
                     );
  }
}




