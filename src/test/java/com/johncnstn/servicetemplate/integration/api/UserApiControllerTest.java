package com.johncnstn.servicetemplate.integration.api;

import static com.johncnstn.servicetemplate.controller.api.UserApi.createUserPath;
import static com.johncnstn.servicetemplate.controller.api.UserApi.deleteUserPath;
import static com.johncnstn.servicetemplate.controller.api.UserApi.getUserPath;
import static com.johncnstn.servicetemplate.controller.api.UserApi.listUsersPath;
import static com.johncnstn.servicetemplate.controller.api.UserApi.updateUserPath;
import static com.johncnstn.servicetemplate.util.PathUtils.generatePath;
import static com.johncnstn.servicetemplate.util.TestUtils.user;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.johncnstn.servicetemplate.integration.AbstractIntegrationTest;
import com.johncnstn.servicetemplate.model.User;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class UserApiControllerTest extends AbstractIntegrationTest {

    @Test
    public void createHappy() throws Exception {
        // GIVEN
        var userToCreate = user();
        // WHEN
        var response =
                createResultActions(userToCreate, createUserPath)
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse();
        var createdUser = objectMapper.readValue(response.getContentAsByteArray(), User.class);
        // THEN
        assertSoftly(
                softly -> {
                    softly.assertThat(createdUser.getId()).isNotNull();
                    softly.assertThat(createdUser.getEmail()).isEqualTo(userToCreate.getEmail());
                    softly.assertThat(createdUser.getFirstName())
                            .isEqualTo(userToCreate.getFirstName());
                    softly.assertThat(createdUser.getLastName())
                            .isEqualTo(userToCreate.getLastName());
                    softly.assertThat(createdUser.getDescription())
                            .isEqualTo(userToCreate.getDescription());
                    softly.assertThat(createdUser.getCreatedAt()).isNotNull();
                    softly.assertThat(createdUser.getUpdatedAt()).isNotNull();
                });
    }

    // region Get

    @Test
    @SuppressWarnings("PMD")
    public void getHappy() throws Exception {
        // GIVEN
        var userToCreate = user();
        var createdUser = createUser(userToCreate);
        // WHEN
        var response =
                getResultActions(createdUser.getId(), getUserPath)
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse();
        var receivedUser = objectMapper.readValue(response.getContentAsByteArray(), User.class);
        // THEN
        assertUser(receivedUser, createdUser);
    }

    @Test
    @SuppressWarnings("PMD")
    public void getWhenNotExist() throws Exception {
        // GIVEN
        var createdUserId = UUID.randomUUID();
        // WHEN
        var resultActions = getResultActions(createdUserId, getUserPath);
        // THEN
        resultActions.andExpect(status().isNotFound());
    }

    // endregion

    @Test
    public void listHappy() throws Exception {
        // GIVEN
        var userToCreate = user();
        var createdUser = createUser(userToCreate);
        // WHEN
        var response =
                mockMvc.perform(get(generatePath(listUsersPath)))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse();
        var receivedUsers =
                objectMapper.readValue(
                        response.getContentAsByteArray(), new TypeReference<List<User>>() {});
        // THEN
        assertSoftly(
                softly -> {
                    softly.assertThat(response.getHeader("Link")).isNotNull();
                    softly.assertThat(Integer.parseInt(response.getHeader("X-Total-Count")))
                            .isEqualTo(receivedUsers.size());
                });
        var user =
                receivedUsers.stream()
                        .filter(u -> createdUser.getId().equals(u.getId()))
                        .findAny()
                        .get();
        assertUser(user, createdUser);
    }

    // region Update

    @Test
    public void updateHappy() throws Exception {
        // GIVEN
        var userToCreate = user();
        var createdUserId = createUser(userToCreate).getId();
        var userToUpdate = user();
        // WHEN
        var response =
                updateResultActions(createdUserId, userToUpdate, updateUserPath)
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse();
        var updatedUser = objectMapper.readValue(response.getContentAsByteArray(), User.class);
        // THEN
        assertSoftly(
                softly -> {
                    softly.assertThat(updatedUser.getEmail()).isEqualTo(userToUpdate.getEmail());
                    softly.assertThat(updatedUser.getFirstName())
                            .isEqualTo(userToUpdate.getFirstName());
                    softly.assertThat(updatedUser.getLastName())
                            .isEqualTo(userToUpdate.getLastName());
                });
    }

    @Test
    public void updateWhenNotExist() throws Exception {
        // GIVEN
        var createdUserId = UUID.randomUUID();
        var userToUpdate = user();
        // WHEN
        var resultActions = updateResultActions(createdUserId, userToUpdate, updateUserPath);
        // THEN
        resultActions.andExpect(status().isNotFound());
    }

    // endregion

    // region Delete

    @Test
    public void deleteHappy() throws Exception {
        // GIVEN
        var userToCreate = user();
        var createdUserId = createUser(userToCreate).getId();
        // WHEN
        var resultActions = mockMvc.perform(delete(generatePath(deleteUserPath, createdUserId)));
        // THEN
        resultActions.andExpect(status().isNoContent());
        assertSoftly(
                softly -> {
                    softly.assertThat(
                                    userRepository
                                            .findOneByIdAndDeletedAtIsNull(createdUserId)
                                            .isPresent())
                            .isFalse();
                    softly.assertThat(userRepository.findById(createdUserId).isPresent()).isTrue();
                });
    }

    @Test
    public void deleteWhenNotExist() throws Exception {
        // GIVEN
        var createdUserId = UUID.randomUUID();
        // WHEN
        var resultActions = mockMvc.perform(delete(generatePath(deleteUserPath, createdUserId)));
        // THEN
        resultActions.andExpect(status().isNoContent());
    }

    // endregion

    private void assertUser(User actual, User expected) {
        assertSoftly(
                softly -> {
                    softly.assertThat(actual.getId()).isEqualTo(expected.getId());
                    softly.assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
                    softly.assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
                    softly.assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
                    softly.assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
                    softly.assertThat(actual.getUpdatedAt()).isEqualTo(expected.getUpdatedAt());
                    softly.assertThat(actual.getCreatedAt()).isEqualTo(expected.getCreatedAt());
                });
    }
}
