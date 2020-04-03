package pl.hub.decorator;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import pl.hub.annotationsandconfigs.CaseFormat;

@Component
@CaseFormat(type = CaseFormat.CaseType.UPPERCASE)
public class UpperCaseTextDecarator implements FileDecarator {
    @Override
    public String format(String text) {
        return text.toUpperCase();
    }
}
