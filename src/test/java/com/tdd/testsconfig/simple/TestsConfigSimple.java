package com.tdd.testsconfig.simple;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import io.restassured.module.webtestclient.specification.WebTestClientRequestSpecBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import reactor.blockhound.BlockHound;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

//------CONFLITO: SpringRunner X WebFluxTest---------------------------
//@RunWith(SpringRunner.class)
//@WebFluxTest
//----------------------------------------------
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@Slf4j
@ActiveProfiles("test")
//@TestPropertySource("classpath:application-test.properties")
public class TestsConfigSimple extends TcSimple {

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

    containerHeaderTcSimple("BEFORE-ALL");
  }


  @AfterAll
  public static void afterAll() {
    RestAssuredWebTestClient.reset();
    containerHeaderTcSimple("AFTER-ALL");
  }


  public static void testSimpleHeader(String title,String label,String subTitle) {
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




