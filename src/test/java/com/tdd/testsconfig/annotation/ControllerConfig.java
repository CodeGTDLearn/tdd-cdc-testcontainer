package com.tdd.testsconfig.annotation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

/*------------------------------------------------------------
                         DataMongoTest
  ------------------------------------------------------------
a) AMBOS FUNCIONAM:
 - @DataMongoTest
 - @DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
b) USO ALTERNATIVO (DataMongoTest/SpringBootTest) - CONFLITAM ENTRE-SI:
 - @SpringBootTest(webEnvironment = RANDOM_PORT)
  ------------------------------------------------------------*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@ExtendWith(TestcontainerConfigClass.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public @interface ControllerConfig {
}
