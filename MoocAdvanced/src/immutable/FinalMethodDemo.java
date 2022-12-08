package immutable;

public class FinalMethodDemo {
    public void drink() {

    }

    public final void eat() {

    }

    public static void sleep() {

    }
}

class SubClass extends FinalMethodDemo {
    @Override
    public void drink() {

    }

    // 不允许
    // public void eat() {
    //
    // }

    // 只属于子类，不是继承的
    public static void sleep() {

    }
}
