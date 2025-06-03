package annotation.java;

public class DeprecatedClass {

    public void call1() {
        System.out.println("Deprecated class call1");
    }

    @Deprecated
    public void call2() {
        System.out.println("Deprecated class call2");
    }

    @Deprecated(since = "2,4", forRemoval = true)
    public void call3() {
        System.out.println("Deprecated class call3");
    }
}
