package com.se300.ledger.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import java.util.Date;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CanaryControllerTest {

    @InjectMocks
    private CanaryController canaryController;

    @Mock
    private Model model;

    @Test
    public void testSayHello() {
        // Arrange
        String expectedViewName = "example";

        // Act
        String actualViewName = canaryController.sayHello(model);

        // Assert
        assertEquals(expectedViewName, actualViewName);
        verify(model).addAttribute(eq("date"), any(Date.class));
    }

    @Test
    public void testHandlePostRequest() {
        // Arrange
        String expectedViewName = "example";

        // Act
        String actualViewName = canaryController.handlePostRequest(model);

        // Assert
        assertEquals(expectedViewName, actualViewName);
        verify(model).addAttribute(eq("date"), any(Date.class));
    }
}
