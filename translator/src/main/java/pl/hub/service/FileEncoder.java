package pl.hub.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@PropertySource("classpath:message.properties")
public class FileEncoder {
    @Value("${fileSource}")
    private static String fileName = "data.csv";
    private static int key = new Random().nextInt(10);

    public static void justReturn() throws IOException {
        Files.readAllLines(Paths.get(fileName));
    }

    public static void encodeFile(boolean value) throws IOException {
        List<String> phrases = Files.readAllLines(Paths.get(fileName));
        List<String> newPhrases = new ArrayList<>();
        for (int i = 0; i < phrases.size(); i++) {
            String phrase = phrases.get(i);
            phrase = cesar(phrase, value);
            newPhrases.add(i, phrase);
        }
        saveToFile(newPhrases);
    }

    private static void saveToFile(List<String> newPhrases) {
        try (PrintWriter printWriter = new PrintWriter(fileName)) {
            newPhrases.forEach(printWriter::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String cesar(String phrase, boolean value) {
        if (value) {
            return phrase.chars()
                    .map(s -> s + key)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        } else {
            return phrase.chars()
                    .map(s -> s - key)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        }
    }
}
