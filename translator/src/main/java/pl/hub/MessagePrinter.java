package pl.hub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.hub.annotationsandconfigs.CaseFormat;
import pl.hub.decorator.FileDecarator;

@Component
public class MessagePrinter {

    private FileDecarator fileDecarator;

    @Autowired
    @CaseFormat(type= CaseFormat.CaseType.BASIC)
    public MessagePrinter(FileDecarator fileDecarator) {
        this.fileDecarator = fileDecarator;
    }

    public void println(String string) {
        String formattedText = fileDecarator.format(string);
        System.out.println(formattedText);
    }
}
