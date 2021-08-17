package com.tdd.testsconfig.inheritance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

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
public class TestscontainerConfigInhe extends TestcontainerInhe {
}




