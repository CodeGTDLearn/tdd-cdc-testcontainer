package com.tdd.testsconfig.annotation;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

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
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
public @interface TestsGlobalAnn {
}
