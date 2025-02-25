import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class PhoneBookApp {
    private static final int MAX_CONTACTS = 30;
    private static final Scanner scanner = new Scanner(System.in);
    private static final Data data = new Data();
    private ArrayList<PhoneContact> contacts;

    public PhoneBookApp() throws IOException, ClassNotFoundException {
        contacts = data.deserializeContacts();
        if (contacts == null) {
            contacts = new ArrayList<>();
        }
    }

    public void run() throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("\nPhone Book Menu:");
            System.out.println("1. Add Contact");
            System.out.println("2. Remove Contact");
            System.out.println("3. Edit Contact");
            System.out.println("4. List Contacts");
            System.out.println("5. Exit");

            int choice = getIntegerInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    removeContact();
                    break;
                case 3:
                    editContact();
                    break;
                case 4:
                    listContacts();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addContact() throws IOException {
        if (contacts.size() >= MAX_CONTACTS) {
            System.out.println("Maximum number of contacts reached.");
            return;
        }

        System.out.print("Enter first name: ");
        String firstName = scanner.next();
        System.out.print("Enter last name: ");
        String lastName = scanner.next();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.next();
        System.out.print("Enter email: ");
        String email = scanner.next();

        contacts.add(new PhoneContact(firstName, lastName, phoneNumber, email));
        data.serializeContacts(contacts);
        System.out.println("Contact added successfully.");
    }

    private void removeContact() throws IOException, ClassNotFoundException {
        listContacts();
        if (contacts.isEmpty()) return;

        int index = getIntegerInput("Enter the index of the contact to remove: ");
        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
            data.serializeContacts(contacts);
            System.out.println("Contact removed successfully.");
        } else {
            System.out.println("Invalid index.");
        }
    }


    private void editContact() throws IOException, ClassNotFoundException {
        listContacts();
        if (contacts.isEmpty()) return;

        int index = getIntegerInput("Enter the index of the contact to edit: ");
        if (index >= 0 && index < contacts.size()) {
            PhoneContact contact = contacts.get(index);
            System.out.println("Current details: " + contact);

            System.out.print("Enter new first name (or press Enter to keep current): ");
            String firstName = scanner.nextLine(); if(firstName.isEmpty()) firstName=contact.getFirstName();
            System.out.print("Enter new last name (or press Enter to keep current): ");
            String lastName = scanner.nextLine(); if(lastName.isEmpty()) lastName=contact.getLastName();
            System.out.print("Enter new phone number (or press Enter to keep current): ");
            String phoneNumber = scanner.nextLine(); if(phoneNumber.isEmpty()) phoneNumber=contact.getPhoneNumber();
            System.out.print("Enter new email (or press Enter to keep current): ");
            String email = scanner.nextLine(); if(email.isEmpty()) email=contact.getEmail();

            contact.setFirstName(firstName);
            contact.setLastName(lastName);
            contact.setPhoneNumber(phoneNumber);
            contact.setEmail(email);

            data.serializeContacts(contacts);
            System.out.println("Contact updated successfully.");
        } else {
            System.out.println("Invalid index.");
        }
    }


    private void listContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        for (int i = 0; i < contacts.size(); i++) {
            System.out.println((i) + ". " + contacts.get(i));
        }
    }


    private int getIntegerInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next();
            }
        }
    }
}
