package org.example.textfile;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TextFileWriter {
    public static void main(String[] args) {
        String filename = "textfile.txt";

        try (FileWriter fileWriter = new FileWriter(filename);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println("Luu Gia Bao - B20DCCN087");
            printWriter.println("1 2 3 5 - anh co danh roi nhip nap khong");
            System.out.println("Ghi nội dung vào tệp text thành công.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

