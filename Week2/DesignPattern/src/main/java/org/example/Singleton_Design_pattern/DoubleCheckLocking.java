package org.example.Singleton_Design_pattern;

public class DoubleCheckLocking {
    private static volatile DoubleCheckLocking INSTANCE;

    private DoubleCheckLocking(){};

    private static DoubleCheckLocking getInstance(){
        if(INSTANCE == null){
            synchronized (DoubleCheckLocking.class){
                if(INSTANCE == null){
                    INSTANCE = new DoubleCheckLocking();
                }
            }
        }
        return INSTANCE;
    }
}
