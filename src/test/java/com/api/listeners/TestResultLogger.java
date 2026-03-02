package com.api.listeners;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * JUnit 5 TestWatcher extension for logging test execution results.
 * <p>
 * This extension automatically logs the status of each test method execution
 * including PASSED, FAILED, ABORTED, and DISABLED states with timestamps.
 * It provides detailed information about test failures including exception
 * messages and causes.
 * </p>
 * <p>
 * Usage: Add {@code @ExtendWith(TestResultLogger.class)} to test classes
 * or configure globally in junit-platform.properties.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 2.0
 */
public class TestResultLogger implements TestWatcher {
    
    private static final Logger logger = LoggerFactory.getLogger(TestResultLogger.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Called when a test successfully completes.
     * <p>
     * Logs a PASSED status with timestamp and test display name.
     * </p>
     *
     * @param context the extension context for the test
     */
    @Override
    public void testSuccessful(ExtensionContext context) {
        String timestamp = LocalDateTime.now().format(formatter);
        String testName = context.getDisplayName();
        logger.info("{} - [PASSED]: {}", timestamp, testName);
    }
    
    /**
     * Called when a test fails due to an assertion error or exception.
     * <p>
     * Logs a FAILED status with timestamp, test display name, and failure cause.
     * </p>
     *
     * @param context the extension context for the test
     * @param cause the throwable that caused the test to fail
     */
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String timestamp = LocalDateTime.now().format(formatter);
        String testName = context.getDisplayName();
        String errorMessage = cause.getMessage() != null ? cause.getMessage() : cause.getClass().getSimpleName();
        logger.error("{} - [FAILED]: {} - Reason: {}", timestamp, testName, errorMessage);
    }
    
    /**
     * Called when a test is aborted before completion.
     * <p>
     * Logs an ABORTED status with timestamp, test display name, and abort cause.
     * </p>
     *
     * @param context the extension context for the test
     * @param cause the throwable that caused the test to be aborted
     */
    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        String timestamp = LocalDateTime.now().format(formatter);
        String testName = context.getDisplayName();
        String errorMessage = cause.getMessage() != null ? cause.getMessage() : "Test aborted";
        logger.warn("{} - [ABORTED]: {} - Reason: {}", timestamp, testName, errorMessage);
    }
    
    /**
     * Called when a test is disabled and not executed.
     * <p>
     * Logs a DISABLED status with timestamp, test display name, and optional reason.
     * </p>
     *
     * @param context the extension context for the test
     * @param reason optional reason why the test was disabled
     */
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        String timestamp = LocalDateTime.now().format(formatter);
        String testName = context.getDisplayName();
        String disabledReason = reason.orElse("No reason provided");
        logger.info("{} - [DISABLED]: {} - Reason: {}", timestamp, testName, disabledReason);
    }
}
