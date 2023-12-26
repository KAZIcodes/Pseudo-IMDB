import java.util.Date;

public class Person {
    String firstName;
    String lastName;
    Date birthData;
    SexEnum sex;
    String nationality;
    String email;
    String userName;
    String passWord;
    boolean isBanned;

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getUserName() {
        return userName;
    }
}
