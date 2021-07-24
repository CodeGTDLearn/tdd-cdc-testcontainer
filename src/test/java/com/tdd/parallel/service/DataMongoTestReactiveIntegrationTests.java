package com.tdd.parallel.service;

import com.tdd.parallel.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static com.tdd.databuilder.PersonBuilder.personWithIdAndName;

//https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-test-autoconfigure/src/test/java/org/springframework/boot/test/autoconfigure/data/mongo/DataMongoTestReactiveIntegrationTests.java
@DataMongoTest
@Testcontainers(disabledWithoutDocker = true)
class DataMongoTestReactiveIntegrationTests {

  final static String MONGO_VERSION = "mongo:4.4.2";
  final static String MONGO_URI_PROPERTY = "spring.data.mongodb.uri";

  @Container
  public static final MongoDBContainer container =
       new MongoDBContainer(DockerImageName.parse(MONGO_VERSION));

  @Autowired
  private ReactiveMongoTemplate mongoTemplate;
//  private MongoTemplate mongoTemplate;


  @DynamicPropertySource
  static void mongoProperties(DynamicPropertyRegistry registry) {
    registry.add(MONGO_URI_PROPERTY,container::getReplicaSetUrl);
  }


  @Test
  void testRepository() {
    Person person1 = personWithIdAndName().create();
    person1.setName("Look, new @DataMongoTest!");
//    MongoTemplate
//    String name = this.mongoTemplate.save(person1).getName();
//    assertThat(name).isNotNull();

//    ReactiveMongoTemplate
    Person person = this.mongoTemplate.save(person1).block(Duration.ofSeconds(30));
    assertThat(person).isNotNull();
    assertThat(this.mongoTemplate.collectionExists("person"));
  }
}