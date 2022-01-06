package singleton;

public class SingletonSample {
    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        System.out.println(instance);
        System.out.println(SingletonEn.INSTANCE.doSometh());
    }
}


class Singleton {
    private static Singleton singleton;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}


enum SingletonEn {
    INSTANCE;

    public String doSometh() {
        return "do something";
    }
}