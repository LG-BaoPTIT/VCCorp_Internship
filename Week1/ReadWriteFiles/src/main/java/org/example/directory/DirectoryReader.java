package org.example.directory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DirectoryReader {

    public static void listFilesAndReadContent(String directoryPath) {
        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            listFiles(directory);
        } else {
            System.out.println(directoryPath + " không phải là thư mục.");
        }
    }

    private static void listFiles(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println("File: " + file.getName());
                    readContent(file);
                    System.out.println("---------------------");
                }
                else if (file.isDirectory()) {
                    listFiles(file);
                }
            }
        } else {
            System.out.println("Thư mục rỗng hoặc không thể truy cập.");
        }
    }

    private static void readContent(File file) {
        try {
            Files.lines(Paths.get(file.getAbsolutePath()))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String directoryPath = "./ReadWriteFiles/src";

        listFilesAndReadContent(directoryPath);
    }
}
