val hdfsService = ServiceBuilder()
  .addMaster()
  .addWorker()
  .addWorker()
  .asHdfsCluster()
  .configurableAttributes(2, 1)

val build = SBuild("com.codogenic", "kafka-cluster", "0.0.1")
  .sourceDirectories("src", "kclients")
  .dependencies(docker_core(), docker_definitions(), "org.apache.kafka" % "kafka-clients" % "3.1.0")
  .testSourceDirectories("spec")
  .testDependencies( scalatest() )
  .services(hdfsService)
  .scalaVersions("2.13.7")


val hdfsClusterProject = (project in file("."))
  .settings( build.settings )
  .enablePlugins(FluentStyleSbt, PackPlugin)
