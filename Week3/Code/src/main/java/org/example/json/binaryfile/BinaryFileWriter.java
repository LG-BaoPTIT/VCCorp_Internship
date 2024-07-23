package org.example.json.binaryfile;

import org.example.json.Person;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

//daemon thread, ddoongf thoi va song song
public class BinaryFileWriter {
    public static void main(String[] args) {
//        String filename = "binaryfile.dat";
//
//        Person person = new Person("Luu Gia Baor", 22);
//
//        try (FileOutputStream fileOut = new FileOutputStream(filename);
//             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
//            objectOut.writeObject(person);
//            System.out.println("Ghi đối tượng vào tệp binary thành công.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        numbers.parallelStream()
                .forEach(number -> System.out.println("Thread: " + Thread.currentThread().getName() + " - Number: " + number));

    }
}
