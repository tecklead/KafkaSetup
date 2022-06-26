package com.codogenic.kclients;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Scanner;

public class KafkaPublisher {
    final static String kafkaIp = "localhost:9091";

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaIp);
        props.put("auto.create.topics.enable", "true");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Message: ");
        String input = scanner.nextLine();

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props);
        kafkaProducer.send(new ProducerRecord<>("skt", input));
        kafkaProducer.flush();
        System.out.println("Message sent: " + input);
    }
}
