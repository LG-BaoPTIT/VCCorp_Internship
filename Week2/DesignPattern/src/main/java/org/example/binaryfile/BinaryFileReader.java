package org.example.binaryfile;


import org.example.Person;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class BinaryFileReader {
    public static void main(String[] args) {
        String filename = "binaryfile.dat";

        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            Person person = (Person) objectIn.readObject();
            System.out.println("Đối tượng đọc từ tệp binary: " + person);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
