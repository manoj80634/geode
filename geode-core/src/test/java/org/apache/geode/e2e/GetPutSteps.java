package org.apache.geode.e2e;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import com.spotify.docker.client.exceptions.DockerException;
import org.apache.geode.cache.CacheClosedException;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.cache.execute.Execution;
import org.apache.geode.cache.execute.FunctionService;
import org.apache.geode.cache.execute.ResultCollector;
import org.apache.geode.e2e.container.DockerCluster;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class GetPutSteps {

  private DockerCluster cluster;

  @BeforeStories
  public void teardownContainers() throws DockerException, InterruptedException {
    // Stop and delete all current containers
    DockerCluster.scorch();
  }

  @BeforeScenario
  public void beforeScenario() throws IOException {
    cluster = new DockerCluster("test-cluster");
  }

  @AfterScenario
  public void afterScenario() throws Exception {
    cluster.stop();
  }

  @Given("cluster is started with $locators locator{s|} and $servers servers")
  public void startCluster(int locatorCount, int serverCount) throws Exception {
    cluster.setLocatorCount(locatorCount);
    cluster.setServerCount(serverCount);
    cluster.start();
  }

  @Given("region $name is created as $type")
  public void createRegion(String name, String type) throws Exception {
    cluster.gfshCommand(String.format("create region --name=%s --type=%s", name, type));
  }

  @Given(value = "region $name is created as $type with redundancy $redundancy", priority = 1)
  public void createRegionWithRedundancy(String name, String type, Integer redundancy) throws Exception {
    cluster.gfshCommand(String.format("create region --name=%s --type=%s --redundancy=%d", name, type, redundancy));
  }

  @Given("server $idx is killed")
  public void killServer(int idx) throws Exception {
    cluster.killServer(idx);
  }

  @When("I put $count entries into region $name")
  public void when(int count, String name) throws Exception {
    ClientCache cache = getClientCache();
    Region region = cache.createClientRegionFactory(ClientRegionShortcut.PROXY).create(name);
    for (int i = 0; i < count; i++) {
      region.put("key_" + i, "value_" + i);
    }
  }

  @Then("I can get $count entries from region $name")
  public void then(int count, String name) throws Exception {
    ClientCache cache = getClientCache();
    Region region = cache.getRegion(name);

    assertEquals(count, region.keySetOnServer().size());
    for (int i = 0; i < count; i++) {
      assertEquals("value_" + i, region.get("key_" + i));
    }
  }

  @Given("class{es|} $fnName {is|are} deployed")
  public void deployClasses(String fnClasses) throws Exception {
    for (String fnClass : fnClasses.split(",")) {
      String jar = cluster.injectScratchFile(Utils.getJarForClassName(fnClass));
      cluster.gfshCommand("deploy --jar=" + jar);
    }
  }

  @When("I call function with id $fnId on region $regionName with argument $arg it returns $returns")
  public void testRegionBucketSizeWithFunction(String fnId, String regionName, String arg, int returns) {
    ClientCache cache = getClientCache();
    Region region = cache.getRegion(regionName);
    Execution exe = FunctionService.onServers(region.getRegionService());
    ResultCollector rs = exe.withArgs(regionName).execute(fnId);
    List<Integer> results = (List<Integer>) rs.getResult();

    assertEquals(returns, results.stream().mapToInt(i -> i.intValue()).sum());
  }

  private ClientCache getClientCache() {
    ClientCache cache;
    try {
      cache = ClientCacheFactory.getAnyInstance();
      return cache;
    } catch (CacheClosedException ignored) {
      // ignored
    }

    cache = new ClientCacheFactory().
      set("log-level", "warn").
      addPoolLocator("localhost", cluster.getLocatorPort()).
      create();

    return cache;
  }

}

