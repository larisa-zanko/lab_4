import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

class Contact implements Comparable<Contact> {
    private String name; // Наименование
    private String mobilePhone; // Мобильный телефон
    private String workPhone; // Рабочий телефон
    private String homePhone; // Домашний телефон
    private String email; // Адрес электронной почты
    private String webPage; // Адрес веб-страницы
    private String address; // Адрес

    public Contact(String name, String mobilePhone, String workPhone,
                   String homePhone, String email, String webPage, String address) {
        this.name = name;
        this.mobilePhone = mobilePhone;
        this.workPhone = workPhone;
        this.homePhone = homePhone;
        this.email = email;
        this.webPage = webPage;
        this.address = address;
    }

    @Override
    public int compareTo(Contact other) {
        return this.name.compareTo(other.name); // Сравнение по имени
    }

    @Override
    public String toString() {
        return String.join("|", name, mobilePhone, workPhone, homePhone, email, webPage, address);
    }

    public static Contact fromString(String str) {
        String[] fields = str.split("\\|");
        return new Contact(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6]);
    }
}

public class ContactM {
    private List<Contact> contacts;

    public ContactM() {
        contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void loadContactsFromFile(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Contact contact = Contact.fromString(line);
                addContact(contact);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e.getMessage());
        }
    }

    public void saveSortedContactsToFile(String filePath) {
        Collections.sort(contacts);
        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (Contact contact : contacts) {
                writer.println(contact);
            }
            System.out.println("Контакты успешно сохранены в файл: " + filePath);
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось сохранить файл: " + e.getMessage());
        }
    }

    public void printSortedContacts() {
        Collections.sort(contacts);
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    public static void main(String[] args) {
        ContactM manager = new ContactM();

        // Загрузка контактов из файла
        manager.loadContactsFromFile("contacts.txt");

        // Печать отсортированных контактов
        System.out.println("Сортированные контакты:");
        manager.printSortedContacts();

        // Сохранение отсортированных контактов в файл
        manager.saveSortedContactsToFile("sorted_contacts.txt");

        // Проверка существования файла
        File file = new File("sorted_contacts.txt");
        if (file.exists()) {
            System.out.println("Файл сохранен по пути: " + file.getAbsolutePath());
        } else {
            System.out.println("Файл не найден после попытки сохранения.");
        }
    }
}