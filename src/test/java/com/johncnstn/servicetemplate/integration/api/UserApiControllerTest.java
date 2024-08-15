package com.johncnstn.servicetemplate.integration.api;

import static com.johncnstn.servicetemplate.util.TestUtils.user;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.johncnstn.servicetemplate.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

public class UserApiControllerTest extends AbstractIntegrationTest {

    @Test
    public void createHappy() throws Exception {
        // GIVEN
        var userToCreate = user();
        // WHEN
        var createdUser = createUser(userToCreate);
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
                });
    }
}
