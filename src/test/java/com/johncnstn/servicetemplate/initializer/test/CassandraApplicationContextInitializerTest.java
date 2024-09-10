package com.johncnstn.servicetemplate.initializer.test;

import static org.assertj.core.api.Assertions.assertThat;

import com.johncnstn.servicetemplate.initializer.CassandraInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = CassandraInitializer.class)
public class CassandraApplicationContextInitializerTest {

    @Autowired private CassandraAdminTemplate cassandraAdminTemplate;

    @Test
    void cassandraContainerShouldBeRunning() {
        // Assert that CassandraAdminTemplate is properly configured
        assertThat(cassandraAdminTemplate).isNotNull();
    }
}
