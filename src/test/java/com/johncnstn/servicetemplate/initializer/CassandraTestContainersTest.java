//package com.johncnstn.servicetemplate.initializer;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.CassandraContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//@SpringBootTest
//@Testcontainers
//public class CassandraTestContainersTest {
//
//    @Container
//    static CassandraContainer<?> cassandraContainer =
//            new CassandraContainer<>("cassandra:latest")
//                    .withExposedPorts(9042)
//                    .withStartupAttempts(5);
//
//    @DynamicPropertySource
//    static void cassandraProperties(DynamicPropertyRegistry registry) {
//        // Set Spring properties for the Cassandra instance started by Testcontainers
//        registry.add(
//                "spring.data.cassandra.contact-points",
//                () -> cassandraContainer.getHost() + ":" + cassandraContainer.getMappedPort(9042));
//        registry.add("spring.data.cassandra.port", () -> cassandraContainer.getMappedPort(9042));
//        registry.add(
//                "spring.data.cassandra.local-datacenter",
//                () -> "datacenter1"); // adjust the datacenter name if needed
//    }
//
//    @BeforeAll
//    public static void setUp() {
//        // Additional setup if required, the container will start automatically
//    }
//
//    @Test
//    void testCassandraConnection() {
//        // Write your test logic here to verify Cassandra interactions
//    }
//}
