package es.fandango.core.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

public class HighlightConverterText extends ForegroundCompositeConverterBase<ILoggingEvent> {

    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        Level level = event.getLevel();
        switch (level.toInt()) {
            case Level.ERROR_INT:
                return ANSIConstants.BOLD + ANSIConstants.RED_FG; // same as default color scheme
            case Level.WARN_INT:
                return ANSIConstants.BOLD + ANSIConstants.YELLOW_FG;
            case Level.DEBUG_INT:
                switch (event.getLoggerName()) {
                    // Customize colors for INFO
                    case "io.micronaut.http.server.netty.NettyHttpServer":
                        return ANSIConstants.BOLD + ANSIConstants.YELLOW_FG;
                    default:
                        return ANSIConstants.DEFAULT_FG;
                }
            default:
                return ANSIConstants.DEFAULT_FG;
        }
    }

}