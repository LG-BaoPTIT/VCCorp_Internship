package org.example.directory;

import java.io.File;

public class DirectoryLister {
    public static void main(String[] args) {
        String directoryPath = ".";
        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        System.out.println(file.getName());
                    }
                }
            } else {
                System.out.println("Thư mục rỗng hoặc không thể truy cập.");
            }
        } else {
            System.out.println(directoryPath + " không phải là thư mục.");
        }
    }
}
