import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
class Data {
    private static final String FILE_NAME = "contacts.ser";

    public void serializeContacts(ArrayList<PhoneContact> contacts) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(contacts);
        }
    }

    public ArrayList<PhoneContact> deserializeContacts() throws IOException, ClassNotFoundException {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (ArrayList<PhoneContact>) ois.readObject();
        }
    }
}
