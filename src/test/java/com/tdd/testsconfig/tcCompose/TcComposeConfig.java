package com.tdd.testsconfig.tcCompose;

import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;

/*
SPEED-UP TESTCONTAINERS
https://callistaenterprise.se/blogg/teknik/2020/10/09/speed-up-your-testcontainers-tests/
https://medium.com/pictet-technologies-blog/speeding-up-your-integration-tests-with
-testcontainers-e54ab655c03d
 */
public class TcComposeConfig implements Extension {

  final static public int SERVICE_PORT = 27017;
  final static public String SERVICE = "db-service";
  final private String COMPOSE_PATH = "src/test/resources/compose-tcContainers.yml";


  //@Container // Nao anotar aqui. Annotacao deve ficar na classe receptora
  public DockerComposeContainer<?> tcCompose =
       new DockerComposeContainer<>(
            new File(COMPOSE_PATH))
            .withExposedService(SERVICE,SERVICE_PORT,Wait.forListeningPort());


}




