package org.example.Singleton_Design_pattern;

public class EagerInitialization {
    private static final EagerInitialization INSTANCE = new EagerInitialization();

    private String name;

    private EagerInitialization(){

    }

    public static EagerInitialization getInstance(){
        return INSTANCE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
