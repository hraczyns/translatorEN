package pl.hub.annotationsandconfigs;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,
        ElementType.METHOD,
        ElementType.TYPE,
        ElementType.PARAMETER,
        ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface CaseFormat {
    CaseType type();

    enum CaseType {
        BASIC, UPPERCASE
    }
}
