package com.tdd.testconfig.annotation;

import com.tdd.parallel.core.MongoTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
//------CONFLITO: SpringRunner X WebFluxTest---------------------------
//@RunWith(SpringRunner.class)
//@WebFluxTest
//----------------------------------------------
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource("classpath:application-test.properties")
//@CustomTestcontainerConfig
@MongoTest
@ActiveProfiles("test")
@ExtendWith(CustomTestsConfigClass.class)
public @interface CustomTestsConfig {
}
