package testsconfig.tcCompose;

import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;

/*
SPEED-UP TESTCONTAINERS
https://callistaenterprise.se/blogg/teknik/2020/10/09/speed-up-your-testcontainers-tests/
https://medium.com/pictet-technologies-blog/speeding-up-your-integration-tests-with
-testcontainers-e54ab655c03d
 */
public class TcComposeConfig {

  final static public int COMPOSE_STARTUP_TIMEOUT = 30;
  final static public int COMPOSE_SERVICE_PORT = 27017;
  final static public String COMPOSE_SERVICE = "db-service";
  final static private String COMPOSE_PATH = "src/test/resources/tc-compose.yml";


  //format 01: using a variable to create the tcContainerCompose
  private final DockerComposeContainer<?> tcCompose =
       new DockerComposeContainer<>(
            new File(COMPOSE_PATH))
            .withExposedService(
                 COMPOSE_SERVICE,
                 COMPOSE_SERVICE_PORT,
                 Wait.forListeningPort()
                     .withStartupTimeout(Duration.ofSeconds(COMPOSE_STARTUP_TIMEOUT))
                               );


  //format 02: using a getter/accessor to create the tcContainerCompose
  public DockerComposeContainer<?> getTcCompose() {
    return tcCompose;
  }
}




