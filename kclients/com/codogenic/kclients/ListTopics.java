package com.codogenic.kclients;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsOptions;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ListTopics {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091");

        AdminClient adminClient = AdminClient.create(properties);

        System.out.println(adminClient.listTopics(new ListTopicsOptions()).names().get());
    }
}
