package org.example.Singleton_Design_pattern;

public class LazyInitialization {
    private static LazyInitialization INSTANCE;

    private LazyInitialization(){}
    private String name;

    public static LazyInitialization getINSTANCE(){
        if(INSTANCE == null) {
            INSTANCE = new LazyInitialization();
        }
        return INSTANCE;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
