package org.example.json.binaryfile;

import org.example.json.Person;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class BinaryFileWriter {
    public static void main(String[] args) {
        String filename = "binaryfile.dat";

        Person person = new Person("Luu Gia Baor", 22);

        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(person);
            System.out.println("Ghi đối tượng vào tệp binary thành công.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
