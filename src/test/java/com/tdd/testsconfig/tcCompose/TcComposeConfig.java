package com.tdd.testsconfig.tcCompose;

import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;

import static com.tdd.testsconfig.utils.TestsGlobalMethods.globalComposeServiceContainerMessage;

/*
SPEED-UP TESTCONTAINERS
https://callistaenterprise.se/blogg/teknik/2020/10/09/speed-up-your-testcontainers-tests/
https://medium.com/pictet-technologies-blog/speeding-up-your-integration-tests-with
-testcontainers-e54ab655c03d
 */
//public class TcComposeConfig implements Extension {
public class TcComposeConfig {

  final static public int COMPOSE_SERVICE_PORT = 27017;
  final static public String COMPOSE_SERVICE = "db-service";
  final static private String COMPOSE_PATH = "src/test/resources/compose-tcContainers.yml";


  //format 01: using a variable to create the tcContainerCompose
  private final DockerComposeContainer<?> tcCompose =
       new DockerComposeContainer<>(
            new File(COMPOSE_PATH))
            .withExposedService(
                 COMPOSE_SERVICE,
                 COMPOSE_SERVICE_PORT,
                 Wait.forListeningPort()
                               );
//       .withOptions("sslEnabled(true)");


  //format 02: using a getter/accessor to create the tcContainerCompose
  public DockerComposeContainer<?> getTcCompose() {
    tcCompose.start();

    globalComposeServiceContainerMessage(
         tcCompose,
         TcComposeConfig.COMPOSE_SERVICE,
         TcComposeConfig.COMPOSE_SERVICE_PORT
                                        );
    return tcCompose;
  }
}




