package it.pkg.config;

import ch.qos.logback.classic.*;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.*;
import ch.qos.logback.core.status.NopStatusListener;
import ch.qos.logback.core.util.FileSize;
import ch.qos.logback.core.util.StatusPrinter;
import net.logstash.logback.encoder.LogstashEncoder;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

//@Configuration
public class LogbackConfiguration {

    private final Environment environment;

    public LogbackConfiguration(Environment environment) {
        this.environment = environment;
        configureLogback();
    }

    private void configureLogback() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.getStatusManager().add(new NopStatusListener());
        context.reset();

        // Common properties
        String logPath = environment.getProperty("log.path");
        String appName = environment.getProperty("app.name");
        String projectVersion = environment.getProperty("app.version");
        // ... Fetch other properties ...

        // CONSOLE_APPENDER
        ConsoleAppender<ILoggingEvent> consoleAppender = buildConsoleAppender(context, appName, projectVersion);

        // NDJSON_FILE_APPENDER
        RollingFileAppender<ILoggingEvent> ndJsonFileAppender = buildNdJsonFileAppender(context, logPath, appName, projectVersion);

        // NDJSON_CONSOLE_APPENDER
        ConsoleAppender<ILoggingEvent> ndJsonConsoleAppender = buildNdJsonConsoleAppender(context, appName, projectVersion);

        // Assign appenders based on profiles
        if (environment.acceptsProfiles("dev", "local")) {
            configureDevelopmentProfile(context, consoleAppender, ndJsonFileAppender);
        } else if (environment.acceptsProfiles("prod")) {
            configureProductionProfile(context, ndJsonFileAppender, ndJsonConsoleAppender);
        }

        // Ensure any errors or warnings in configuration are reported
        StatusPrinter.printInCaseOfErrorsOrWarnings(context);
    }

    private ConsoleAppender<ILoggingEvent> buildConsoleAppender(LoggerContext context, String appName, String projectVersion) {
        ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<>();
        appender.setContext(context);
        appender.setName("CONSOLE_APPENDER");

        ThresholdFilter filter = new ThresholdFilter();
        filter.setLevel("TRACE");
        filter.start();
        appender.addFilter(filter);

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%blue([%d{yyyy-MM-dd HH:mm:ss:SSS}]) %highlight(%green([" + appName + "])) %highlight(%green([" + projectVersion + "])) %yellow([%clr(%-5p)]) %magenta(%thread) %gray(---) %cyan(%logger{0}) %red(:) %blue(%m) %n %highlight(%magenta([%marker])) %n");
        encoder.start();
        appender.setEncoder(encoder);

        appender.start();

        return appender;
    }

    private RollingFileAppender<ILoggingEvent> buildNdJsonFileAppender(LoggerContext context, String logPath, String appName, String projectVersion) {
        RollingFileAppender<ILoggingEvent> appender = new RollingFileAppender<>();
        appender.setContext(context);
        appender.setName("NDJSON_FILE_APPENDER");
        appender.setFile(logPath + "/" + appName + ".log");

        FixedWindowRollingPolicy rollingPolicy = new FixedWindowRollingPolicy();
        rollingPolicy.setContext(context);
        rollingPolicy.setFileNamePattern(logPath + "/" + appName + ".%i.log");
        rollingPolicy.setMinIndex(1);
        rollingPolicy.setMaxIndex(10);
        rollingPolicy.setParent(appender);
        rollingPolicy.start();
        appender.setRollingPolicy(rollingPolicy);

        SizeBasedTriggeringPolicy<ILoggingEvent> triggeringPolicy = new SizeBasedTriggeringPolicy<>();

        triggeringPolicy.setMaxFileSize(FileSize.valueOf("100MB"));
        triggeringPolicy.start();
        appender.setTriggeringPolicy(triggeringPolicy);

        LogstashEncoder encoder = new LogstashEncoder();
        encoder.setContext(context);
        //encoder.getIncludeMdcKeyNames().add("OPERATION_ID");
        encoder.setCustomFields("{\"app_name\": \"" + appName + "\", \"project_version\": \"" + projectVersion + "\"}");
        encoder.start();
        appender.setEncoder(encoder);

        appender.start();

        return appender;
    }

    private ConsoleAppender<ILoggingEvent> buildNdJsonConsoleAppender(LoggerContext context, String appName, String projectVersion) {
        ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<>();
        appender.setContext(context);
        appender.setName("NDJSON_CONSOLE_APPENDER");

        LogstashEncoder encoder = new LogstashEncoder();
        encoder.setContext(context);
        //encoder.getIncludeMdcKeyNames().add("OPERATION_ID");
        encoder.setCustomFields("{\"app_name\": \"" + appName + "\", \"project_version\": \"" + projectVersion + "\"}");
        encoder.start();
        appender.setEncoder(encoder);

        appender.start();

        return appender;
    }

    private void configureDevelopmentProfile(LoggerContext context, ConsoleAppender<ILoggingEvent> consoleAppender, RollingFileAppender<ILoggingEvent> fileAppender) {
        String rootLogLevel = environment.getProperty("log.level.root", "INFO");

        Logger rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.toLevel(rootLogLevel));
        rootLogger.addAppender(consoleAppender);
        rootLogger.addAppender(fileAppender);

        String appLogLevel = environment.getProperty("log.level.application", "TRACE");
        Logger appLogger = context.getLogger("archetype.it");
        appLogger.setLevel(Level.toLevel(appLogLevel));
        appLogger.setAdditive(false);
        appLogger.addAppender(consoleAppender);
        appLogger.addAppender(fileAppender);
    }

    private void configureProductionProfile(LoggerContext context, RollingFileAppender<ILoggingEvent> fileAppender, ConsoleAppender<ILoggingEvent> ndJsonConsoleAppender) {
        String rootLogLevel = environment.getProperty("log.level.root", "INFO");

        Logger rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.toLevel(rootLogLevel));
        rootLogger.addAppender(fileAppender);
        rootLogger.addAppender(ndJsonConsoleAppender);

        String appLogLevel = environment.getProperty("log.level.application", "TRACE");
        Logger appLogger = context.getLogger("archetype.it");
        appLogger.setLevel(Level.toLevel(appLogLevel));
        appLogger.setAdditive(false);
        appLogger.addAppender(fileAppender);
        appLogger.addAppender(ndJsonConsoleAppender);
    }
}
