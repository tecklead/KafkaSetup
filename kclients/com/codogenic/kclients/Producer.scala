package com.codogenic.kclients

import org.apache.kafka.clients.producer._

import java.util.Properties

object Producer {

  def main(args: Array[String]): Unit = {
    writeToKafka("abc")
  }

  def writeToKafka(topic: String): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9091")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](props)
    println("inside producer")

    val message = "message"

    for (i <- 1 to 5) {
      val msg = s"$message + $i"
      println(s"sendig msg $msg")
      val record = new ProducerRecord[String, String](topic, msg)
      producer.send(record)
    }

    //val record = new ProducerRecord[String, User](topic, "User2", userObject)
    //producer.send(record)
    //println(" prducer send complete")
    println("producer complete")
    producer.close()
  }
}


