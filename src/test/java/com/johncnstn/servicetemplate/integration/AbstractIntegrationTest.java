package com.johncnstn.servicetemplate.integration;

import com.johncnstn.servicetemplate.initializer.PostgresInitializer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {PostgresInitializer.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {}
