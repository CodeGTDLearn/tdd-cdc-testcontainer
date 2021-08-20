package com.tdd.testsconfig.tcCompose;

import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;

import java.io.File;

/*
SPEED-UP TESTCONTAINERS
https://callistaenterprise.se/blogg/teknik/2020/10/09/speed-up-your-testcontainers-tests/
https://medium.com/pictet-technologies-blog/speeding-up-your-integration-tests-with
-testcontainers-e54ab655c03d
 */
public class TcComposeConfig implements Extension {

  final private String COMPOSE_PATH = "src/test/resources/compose-testcontainers.yml";
  final static public int SERVICE_PORT = 27017;
  final static public String SERVICE = "db";

  //@Container // Nao anotar aqui. Annotacao deve ficar na classe receptora
  public DockerComposeContainer<?> tcCompose =
       new DockerComposeContainer<>(
            new File(COMPOSE_PATH))
            .withExposedService(SERVICE,SERVICE_PORT,Wait.forListeningPort());


  public static void checkTestcontainerComposeService(
       DockerComposeContainer<?> compose,
       String service,
       Integer port) {
    String status =
         "\nHost: " + compose.getServiceHost(service,port) +
              "\nPort: " + compose.getServicePort(service,port) +
              "\nCreated: " + compose.getContainerByServiceName(service + "_1")
                                     .get()
                                     .isCreated() +
              "\nRunning: " + compose.getContainerByServiceName(service + "_1")
                                     .get()
                                     .isRunning();

    System.out.println(
         "------------\n" + "SERVICE: " + service + status + "\n------------");
  }
}




