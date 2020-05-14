package methodrefs;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class MethodRefs {
    private static int DEFAULT=123;

    private int val;
    private String[] names;

    public MethodRefs(int v) {
        val = v;
    }

    public MethodRefs() {
        val = DEFAULT;
    }

    public MethodRefs(String[] names) {
        this.names = names;
    }

    private int mapper(String name) {
        return name.length();
    }

    static class MyType {
        public static int m0() { return 0; }
        public int m1() { return 0; }
        public static int m2(MyType mt) { return 1; }
        public static int m3(MyType mt, int i) { return 2; }
        public  Function<Integer,String> m4() {return null; }
    }

    public void methods() {
        // métodos estáticos

        Supplier<Integer> f1 = () -> MyType.m0();
        Supplier<Integer> f1a = MyType::m0;

        Function<MyType, Integer> f2=
                (MyType mt) -> MyType.m2(mt);
        Function<MyType, Integer> f2b = MyType::m2;

        BiFunction<MyType,Integer,Integer> f3a = MyType::m3;


        // metodos de instância
        Function<MyType, Integer> f4 = MyType::m1;


        Function<String, Integer> f5 =
                (String n) -> this.mapper(n);

        Function<String, Integer> f5a = this::mapper;


        // construtores
        Supplier<MethodRefs> f6 = () -> new MethodRefs();
        Supplier<MethodRefs> f6a = MethodRefs::new;

        Function<Integer, MethodRefs> f7=
                (Integer i) -> new MethodRefs(i);
        Function<Integer, MethodRefs> f7a= MethodRefs::new;

        Function<String[], MethodRefs> f8 =
                (String[] n) -> new MethodRefs(n);
        Function<String[], MethodRefs> f8a = MethodRefs::new;

        Function<Integer, MethodRefs[]> f9 =
                (Integer n) -> new MethodRefs[n];
        Function<Integer, MethodRefs[]> f9a =
                MethodRefs[]::new;
    }
}
