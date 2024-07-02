package org.example.Singleton_Design_pattern;

public class ThreadSafe {
    private static volatile ThreadSafe INSTANCE;

    private String name;

    private ThreadSafe(){};

    public static synchronized ThreadSafe getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ThreadSafe();
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
