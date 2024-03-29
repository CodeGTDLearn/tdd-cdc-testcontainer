package testsconfig.tcContainer.inheritance;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

///*
//RESTARTED TESTCONTAINERS
//https://www.testcontainers.org/test_framework_integration/junit_5/#restarted-containers
// */
@Testcontainers
public class TestcontainerInhe {

  final static String MONGO_VERSION = "mongo:4.4.2";
  final static String MONGO_URI_PROPERTY = "spring.data.mongodb.uri";

  @Container
  public static final MongoDBContainer containerTcSimple =
       new MongoDBContainer(DockerImageName.parse(MONGO_VERSION));


  @DynamicPropertySource
  static void mongoProperties(DynamicPropertyRegistry registry) {
    registry.add(MONGO_URI_PROPERTY,containerTcSimple::getReplicaSetUrl);
  }
}





