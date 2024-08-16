package com.johncnstn.servicetemplate.unit;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public abstract class AbstractUnitTest {

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
}
