package immutable;

/**
 * 测试final能否被修改
 */
public class TestFinal {
    public static void main(String[] args) {
        final Person person = new Person();
        person.bag = "book";
        // person = new Person();
        // person.age = 19;
    }
}
