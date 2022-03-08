import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {
        PowerOfTwo powerOfTwo = new PowerOfTwo();
        System.out.println(powerOfTwo.next());
        System.out.println(powerOfTwo.next());
        System.out.println(powerOfTwo.next());
        System.out.println(powerOfTwo.next());
        System.out.println(powerOfTwo.next());
        System.out.println(powerOfTwo.next());
        System.out.println(powerOfTwo.next());
        System.out.println(powerOfTwo.next());
        System.out.println(powerOfTwo.next());
        System.out.println(powerOfTwo.next());
        System.out.println(powerOfTwo.next());
        System.out.println(powerOfTwo.next());
        System.out.println(powerOfTwo.next());
        System.out.println(powerOfTwo.next());
        System.out.println(powerOfTwo.next());
    }

}

class PowerOfTwo {
    private AtomicReference<BigInteger> current = new AtomicReference<>(null);

    BigInteger next() {
        BigInteger recent, next;
        do {
            recent = current.get();
            next = (recent == null) ? BigInteger.valueOf(1) : recent.shiftLeft(1);
        } while (!current.compareAndSet(recent, next));
        return next;
    }
}