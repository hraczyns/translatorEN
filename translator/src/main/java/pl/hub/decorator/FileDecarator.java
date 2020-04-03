package pl.hub.decorator;

import org.springframework.stereotype.Component;

@FunctionalInterface
@Component
public interface FileDecarator {
    String format(String text);
}
