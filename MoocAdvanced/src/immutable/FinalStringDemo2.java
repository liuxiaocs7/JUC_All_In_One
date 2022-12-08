package immutable;

public class FinalStringDemo2 {
    public static void main(String[] args) {
        String a = "wukong2";
        // 编译器无法确定final对象的值，编译器不会进行优化，运行时确定的
        final String b = getDashixiong();
        String c = b + 2;
        System.out.println(a == c);
    }

    private static String getDashixiong() {
        return "wukong";
    }
}
