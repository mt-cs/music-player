import java.time.LocalDate;

/**
 * this tester Class contains a simple main method that test all methods in Entity.java
 */
public class EntityTest {
    public static void main(String[] args) {
        Entity e1 = new Entity();
        e1.setName("Marisa");
        System.out.println(e1.getName());
        e1.setDateCreated(LocalDate.now());
        System.out.println(e1.getDateCreated());
        System.out.println(e1.toString());

        Entity e2 = new Entity();
        e2.setName("Lodewyk");
        System.out.println(e2.getName());
        e1.setDateCreated(LocalDate.now());
        System.out.println(e2.getDateCreated());
        System.out.println(e2.toString());

        if (e1.equals(e2)){
            System.out.println("Entities are equal");
        } else {
            System.out.println("Entities are not equal");
        }
    }
}
