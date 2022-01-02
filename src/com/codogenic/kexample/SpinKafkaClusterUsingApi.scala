package com.codogenic.kexample

import com.logicovercode.docker.cluster.ClusterBuilderDefinitions
import com.logicovercode.docker.kafka.ClusterNodeKafkaExtension
import com.logicovercode.wdocker.OsFunctions.currentOsOption
import com.logicovercode.wdocker.api.{DockerContext, DockerProcessFunctions, DockerSystem}
import com.logicovercode.wdocker.{ContainerDefinition, DockerFactory, DockerHostAndClientReResolver, DockerJavaExecutor, DockerKit, DockerNetwork}

import scala.concurrent.duration.DurationInt

object SpinKafkaClusterUsingApi extends ClusterNodeKafkaExtension with ClusterBuilderDefinitions{

  val hnet = DockerNetwork("hnet", Option("192.168.33.0/16"))
  val container = ServiceBuilder(hnet).addMaster().asKafkaCluster().masterNode.kafkaNodeDefinition(None)

  def main(args: Array[String]): Unit = {

    println("start of main")

    println("starting service >" + container + "<")

    DockerSystem.tryNetworkConnectivity(hnet)(DockerContext.dockerClient)
    DockerSystem.pullAndStartContainerDefinition(container, 1.minute, 1.minute)(DockerContext.dockerClient, DockerContext.dockerFactory)

    println("service started >" + container + "<")

    val processId = DockerProcessFunctions.pid()
    DockerProcessFunctions.killDockerManager(processId, currentOsOption)

    println("end of main")
  }
}
