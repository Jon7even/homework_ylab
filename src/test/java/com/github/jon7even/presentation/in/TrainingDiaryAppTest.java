package com.github.jon7even.presentation.in;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TrainingDiaryAppTest {
    @Test
    void contextLoads() {
    }

    @Test
    void testMan() {
        Assertions.assertDoesNotThrow(TrainingDiaryApp::new);
    }
}