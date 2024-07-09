package org.example.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class test {
    public static void main(String[] args) throws ClassNotFoundException {
        String filename = "binaryfile.dat";
        Gson gson = new Gson();
        try(FileInputStream fileInputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ) {
            Person person = (Person) objectInputStream.readObject();
            String jsPerson = gson.toJson(person);
            System.out.println(jsPerson);

            Person person1 = gson.fromJson(jsPerson,Person.class);
            System.out.println(person1);

            String jsString = "{\"name\":\"Luu Gia Baor\",\"age\":22}";

            JsonObject jsonObject = JsonParser.parseString(jsString).getAsJsonObject();

            String name = jsonObject.get("name").getAsString();
            int age = jsonObject.get("age").getAsInt();

            System.out.println(name);
            System.out.println(age);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
