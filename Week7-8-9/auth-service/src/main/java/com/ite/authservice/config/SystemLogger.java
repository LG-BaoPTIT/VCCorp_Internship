package com.ite.authservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SystemLogger {

    private static final Logger logger = LoggerFactory.getLogger(SystemLogger.class);

    public void log(String thread, String processId, String processName, String step, String stepValue) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
        String formattedTime = dateFormat.format(new Date());

        String logMessage = String.format("%s - %s - %s - %s - %s - %s",
                formattedTime, thread, processId, processName, step, stepValue);

        logger.info(logMessage);
    }
    public void log(String thread, String processName, String step, String stepValue) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
        String formattedTime = dateFormat.format(new Date());

        String logMessage = String.format("%s - %s - %s - %s - %s",
                formattedTime, thread, processName, step, stepValue);

        logger.info(logMessage);
    }
    public void logError(String thread, String processName, String step, String stepValue) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
        String formattedTime = dateFormat.format(new Date());

        // Format message
        String logMessage = String.format("%s - %s - %s - %s - %s",
                formattedTime, thread, processName, step, stepValue);

        // Ghi log
        logger.error(logMessage);
    }
}
