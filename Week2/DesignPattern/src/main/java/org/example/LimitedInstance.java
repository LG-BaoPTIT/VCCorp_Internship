package org.example;
public class LimitedInstance {
    private static final int MAX_INSTANCES = 3;
    private static int instanceCount = 0;
    private static final LimitedInstance[] instances = new LimitedInstance[MAX_INSTANCES];
    private String name;
    private LimitedInstance() {}


    public static synchronized LimitedInstance getInstance() {
        if (instanceCount < MAX_INSTANCES) {
            instances[instanceCount] = new LimitedInstance();
            instanceCount++;
        }
        return instances[(instanceCount - 1) % MAX_INSTANCES];
    }

    public static synchronized int getInstanceCount() {
        return instanceCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        LimitedInstance instance1 = LimitedInstance.getInstance();
        System.out.println("Instance Count: " + LimitedInstance.getInstanceCount());
        instance1.setName("name1");
        System.out.println("Instance name: " + instance1.getName());

        LimitedInstance instance2 = LimitedInstance.getInstance();
        System.out.println("Instance Count: " + LimitedInstance.getInstanceCount());
        instance2.setName("name2");
        System.out.println("Instance name: " + instance2.getName());

        LimitedInstance instance3 = LimitedInstance.getInstance();
        System.out.println("Instance Count: " + LimitedInstance.getInstanceCount());
        instance3.setName("name3");
        System.out.println("Instance name: " + instance3.getName());

        LimitedInstance instance4 = LimitedInstance.getInstance();
        System.out.println("Instance Count: " + LimitedInstance.getInstanceCount());
        instance4.setName("name4");
        System.out.println("Instance name: " + instance4.getName());


        System.out.println("Instance 1: " + instance1);
        System.out.println("Instance 2: " + instance2);
        System.out.println("Instance 3: " + instance3);
        System.out.println("Instance 4: " + instance4);
    }
}
