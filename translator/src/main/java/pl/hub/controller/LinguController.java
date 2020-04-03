package pl.hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.hub.MessagePrinter;
import pl.hub.model.Entry;
import pl.hub.repository.EntryRepository;
import pl.hub.service.FileService;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

@Controller
public class LinguController {
    private static final int UNDEFINED = -1;
    private static final int ADD_ENTRY = 0;
    private static final int TEST = 1;
    private static final int CLOSE_APP = 2;

    private EntryRepository entryRepository;
    private FileService fileService;
    private Scanner scanner;
    private MessagePrinter messagePrinter;

    @Autowired
    public LinguController(EntryRepository entryRepository, FileService fileService, Scanner scanner, MessagePrinter messagePrinter) {
        this.entryRepository = entryRepository;
        this.fileService = fileService;
        this.scanner = scanner;
        this.messagePrinter = messagePrinter;
    }

    public void mainLoop() {
        messagePrinter.println("Witaj w aplikacji LinguApp");
        int option = UNDEFINED;
        while (option != CLOSE_APP) {
            printMenu();
            option = chooseOption();
            executeOption(option);
        }
    }

    private void executeOption(int option) {
        switch (option) {
            case ADD_ENTRY:
                addEntry();
                break;
            case TEST:
                test();
                break;
            case CLOSE_APP:
                close();
                break;
            default:
                messagePrinter.println("Opcja niezdefiniowana");
        }
    }

    private void test() {
        if (entryRepository.isEmpty()) {
            messagePrinter.println("Dodaj przynajmniej jedną frazę do bazy.");
            return;
        }
        final int testSize = Math.min(entryRepository.size(), 10);
        Set<Entry> randomEntries = entryRepository.getRandomEntries(testSize);
        int score = 0;
        for (Entry entry : randomEntries) {
            messagePrinter.println("Podaj tłumaczenie dla :\"" + entry.getOriginal() + "\"");
            String translation = scanner.nextLine();
            if (entry.getTranslation().equalsIgnoreCase(translation)) {
                messagePrinter.println("Odpowiedź poprawna");
                score++;
            } else {
                messagePrinter.println("Odpowiedź niepoprawna - " + entry.getTranslation());
            }
        }
        messagePrinter.println("Twój wynik: " + score + "/" + testSize);
    }

    private void addEntry() {
        messagePrinter.println("Podaj oryginalną frazę");
        String original = scanner.nextLine();
        messagePrinter.println("Podaj tłumaczenie");
        String translation = scanner.nextLine();
        Entry entry = new Entry(original, translation);
        entryRepository.add(entry);
    }

    private void close() {
        try {
            fileService.saveEntries(entryRepository.getAll());
            messagePrinter.println("Zapisano stan aplikacji");
        } catch (IOException e) {
            messagePrinter.println("Nie udało się zapisać zmian");
        }
        messagePrinter.println("Bye Bye!");
    }

    private void printMenu() {
        messagePrinter.println("Wybierz opcję:");
        messagePrinter.println("0 - Dodaj frazę");
        messagePrinter.println("1 - Test");
        messagePrinter.println("2 - Koniec programu");
    }

    private int chooseOption() {
        int option;
        try {
            option = scanner.nextInt();
        } catch (InputMismatchException e) {
            option = UNDEFINED;
        } finally {
            scanner.nextLine();
        }
        if (option > UNDEFINED && option <= CLOSE_APP)
            return option;
        else
            return UNDEFINED;
    }
}
