package com.tdd.parallel.core;

import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

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
@Target(ElementType.TYPE)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public @interface MongoTest {
}
