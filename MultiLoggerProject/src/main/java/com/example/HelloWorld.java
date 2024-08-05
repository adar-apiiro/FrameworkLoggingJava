
package com.example;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import io.sentry.Sentry;
import io.sentry.log4j2.SentryAppender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.Logger;
import org.tinylog.configuration.Configuration;
import org.tinylog.Logger as TinyLogger;

public class HelloWorld {
    // Java Util Logging
    private static final Logger julLogger = Logger.getLogger(HelloWorld.class.getName());

    // Log4j2
    private static final Logger log4jLogger = LogManager.getLogger(HelloWorld.class);

    // Tinylog - No need to create an instance

    public static void main(String[] args) {
        // Configure Java Util Logging
        julLogger.setLevel(Level.INFO);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.INFO);
        julLogger.addHandler(handler);

        // Configure Sentry for Log4j2
        Sentry.init(options -> {
            options.setDsn("your-dsn");
        });

        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        BuiltConfiguration configuration = ConfigurationBuilderFactory.newConfigurationBuilder()
            .add(SentryAppender.newBuilder()
                .setConfiguration(context.getConfiguration())
                .setName("SentryAppender")
                .build())
            .build();
        Configurator.reconfigure(configuration);

        // Tinylog configuration (if needed)
        Configuration.set("writer", "console");

        // Log messages using different loggers
        julLogger.info("Hello from Java Util Logging!");
        log4jLogger.info("Hello from Log4j2!");
        TinyLogger.info("Hello from Tinylog!");

        System.out.println("Hello, World!");
    }
}
