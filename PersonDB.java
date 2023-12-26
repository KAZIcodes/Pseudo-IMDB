import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class PersonDB {

    public static ArrayList<Person> persons = new ArrayList<>();

    static Person personSignedIn = null;



    static public void defaultAdminEditor() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birth_date = null;
        try {
            birth_date = dateFormat.parse("1382-10-14"); // Parse the string input to a Date object
        } catch (ParseException e) {
            System.out.println("Exception happened in Default Admin Date");
        }
        Admin defaultAdmin = new Admin("Admin", "mahi", birth_date, SexEnum.MALE, "Persian", "boos@gmail.com", "admin", "admin");
        persons.add(defaultAdmin);
        Editor defaultEditor = new Editor("Amirali", "Kazi", birth_date, SexEnum.MALE, "Persian", "boos@gmail.com", "e", "e");
        persons.add(defaultEditor);
    }

    static public ArrayList<Member> searchMember(String searchQuery){

        ArrayList<Member> results = new ArrayList<>();

        for (Person obj : persons) {
            if (obj instanceof Member){
                String name = obj.getName();
                String username = obj.getUserName();
                if (name != null && name.toLowerCase().contains(searchQuery.toLowerCase()) || username != null && username.toLowerCase().contains(searchQuery.toLowerCase())) {
                    results.add((Member) obj);
                }
            }
        }

        return results;
    }

    static public void printSearch(ArrayList<Member> results) {
        int count = 1;
        for (Member member : results){
            System.out.println(count++ + ". " + member.getName());
        }
    }
}
