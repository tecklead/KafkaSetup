package com.codogenic.kexample

import com.logicovercode.docker.cluster.ClusterBuilderDefinitions
import com.logicovercode.docker.kafka.ClusterNodeKafkaExtension
import com.logicovercode.wdocker.{ContainerDefinition, DockerFactory, DockerHostAndClientReResolver, DockerJavaExecutor, DockerKit, DockerNetwork}
import com.logicovercode.wdocker.OsFunctions.currentOsOption
import com.logicovercode.wdocker.api.{DockerContext, DockerProcessFunctions, DockerSystem}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

object SpinKafkaClusterNative extends ClusterNodeKafkaExtension with ClusterBuilderDefinitions with DockerKit{

  val hnet = DockerNetwork("hnet", Option("192.168.33.0/16"))
  val container = ServiceBuilder(hnet).addMaster().asKafkaCluster().masterNode.kafkaNodeDefinition(None)

  val _@(lHost, lDockerClient) = DockerHostAndClientReResolver.hostAndClient()

  override val StartContainersTimeout = 1.minute
  override val PullImagesTimeout = 1.minute
  override implicit val dockerFactory: DockerFactory = {
    () => new DockerJavaExecutor(lHost, lDockerClient)
  }
  override def containerDefinitions: List[ContainerDefinition] = List(container)

  def main(args: Array[String]): Unit = {

    println("start of main")

    println("starting service >" + container + "<")

    DockerSystem.tryNetworkConnectivity(hnet)(DockerContext.dockerClient)
    DockerSystem.pullDockerImage(Option("logicovercode"), "sbt-kafka-node", "0.0.3", 1.minute.toSeconds)(lDockerClient)
    startAllOrFail()

    println("service started >" + container + "<")

    val processId = DockerProcessFunctions.pid()
    DockerProcessFunctions.killDockerManager(processId, currentOsOption)

    println("end of main")
  }
}
