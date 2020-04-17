import interfaces.MComparator;
import org.junit.Test;

import static org.junit.Assert.*;
import static interfaces.MComparator.*;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class MComparatorTests {

    static class Student {
        private final String name;
        private final int number;
        private int credits;

        public Student(String name, int number, int credits) {
            this.name = name;
            this.number = number;
            this.credits = credits;
        }

        public int getCredits() { return credits; }

        public String getName() { return name; }

        public int getNumber() { return number; }
    }

    static List<Student> db = List.of(
        new Student("Carlos", 55342, 20),
        new Student("Joao", 56777, 50),
        new Student("Joao", 56778, 50),
        new Student("Pedro", 62112, 65),
        new Student("Maria", 63333, 65)
    );

    private static <T> Optional<T> max(Iterable<T> vals, MComparator<T> cmp) {
        Iterator<T> it = vals.iterator();
        if (!it.hasNext()) return Optional.empty();
        T m = it.next();
        while (it.hasNext()) {
            T curr = it.next();
            if (cmp.compare(curr,m) > 0)
                m = curr;
        }
        return Optional.of(m);
    }

    @Test
    public void maxNameTest() {
        Optional<Student> m = max(db, comparing(Student::getName));
        //Optional<Student> m = max(db, comparing(s -> s.getName()));
        //Optional<Student> m = max(db, (s1, s2) -> s1.name.compareTo(s2.name));
        assertTrue(m.isPresent());
        String expected = "Pedro";
        assertEquals(expected, m.get().name);
    }

    @Test
    public void minNameTest() {
        Optional<Student> m = max(db,
                comparing(Student::getName).reversed()
        );
        //Optional<Student> m = max(db, comparing(s -> s.getName()));
        //Optional<Student> m = max(db, (s1, s2) -> s1.name.compareTo(s2.name));
        assertTrue(m.isPresent());
        String expected = "Carlos";
        assertEquals(expected, m.get().name);
    }


    @Test
    public void maxStudentNameForMaxCreditTest() {
        Optional<Student> m =
            max(db, comparing(Student::getCredits)
                    .thenComparing(Student::getName));
        assertTrue(m.isPresent());
        String expected = "Pedro";
        assertEquals(expected, m.get().name);
    }

}
