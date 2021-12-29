package visAndSyncProblems;


/**
 * Volatile usage example.
 * It's hard to reproduce but flag value can be changed only cached value in thread1.
 * Thread2 will use flag value from another cache, related with thread2.
 * That's "Visibility problem".
 */
public class VisibilityProblem {
    // volatile - fix potential issue.
    private volatile boolean flag = true;

    public static void main(String[] args) {
        VisibilityProblem obj = new VisibilityProblem();
        new Thread(obj::printHelloWorldWhileTrue).start();
        new Thread(obj::changeFlagVal).start();
    }

    private void printHelloWorldWhileTrue() {
        while (flag) {
            System.out.println("Hello world");
        }
    }

    private void changeFlagVal() {
        this.flag = false;
    }

}

