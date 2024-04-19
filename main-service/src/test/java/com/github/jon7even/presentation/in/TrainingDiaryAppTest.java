package com.github.jon7even.presentation.in;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TrainingDiaryAppTest {
    @Test
    public void contextLoads() {
    }

    @Test
    public void testMan() {
        Assertions.assertDoesNotThrow(TrainingDiaryApp::new);
    }
}
