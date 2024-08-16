package com.johncnstn.servicetemplate.integration;

import static com.johncnstn.servicetemplate.controller.api.UserApi.createUserPath;
import static com.johncnstn.servicetemplate.util.PathUtils.generatePath;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johncnstn.servicetemplate.initializer.PostgresInitializer;
import com.johncnstn.servicetemplate.model.User;
import com.johncnstn.servicetemplate.repository.UserRepository;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {PostgresInitializer.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {
    @Autowired protected MockMvc mockMvc;

    @Autowired protected ObjectMapper objectMapper;

    @Autowired protected UserRepository userRepository;

    @AfterEach
    public void clearDatabase() {
        userRepository.deleteAll();
    }

    public User createUser(User userToCreate) throws Exception {
        var response = createEntity(userToCreate, createUserPath);
        return objectMapper.readValue(response.getContentAsByteArray(), User.class);
    }

    @NotNull protected MockHttpServletResponse createEntity(Object entityToCreate, String path)
            throws Exception {

        return mockMvc.perform(
                        post(generatePath(path))
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(entityToCreate)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();
    }

    @NotNull protected ResultActions createResultActions(Object entityToCreate, String path)
            throws Exception {
        return mockMvc.perform(
                post(generatePath(path))
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(entityToCreate)));
    }

    @NotNull protected ResultActions getResultActions(UUID createdEntityId, String path) throws Exception {
        return mockMvc.perform(get(generatePath(path, createdEntityId)));
    }

    @NotNull protected ResultActions updateResultActions(
            UUID createdEntityId, Object entityToUpdate, String path) throws Exception {

        return mockMvc.perform(
                put(generatePath(path, createdEntityId))
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(entityToUpdate)));
    }
}
