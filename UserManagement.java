import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class UserManagement {

    static Scanner scanner = new Scanner(System.in);

    static public void signIn() {
        System.out.print("Enter a Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter a Password: ");
        String password = scanner.nextLine();

        for (Person person : PersonDB.persons) {
            if (username.equals(person.userName) && password.equals(person.passWord)){
                if (!person.isBanned)
                    PersonDB.personSignedIn = person;
                else
                    System.out.println("You Are Banned!!!");
                return;
            }
        }
        if (PersonDB.personSignedIn == null) {System.out.println("User Not Found !");}
    }


    static public void signUp(String type){
        System.out.print("Enter your First Name: ");
        String first_name = scanner.nextLine();
        System.out.print("Enter your Last Name: ");
        String last_name = scanner.nextLine();


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birth_date = null;
        while (true) {
            try {
                System.out.print("Enter your Birth Date (format: yyyy-MM-dd): ");
                String dateInput = scanner.nextLine();
                if (dateInput.equals(""))
                    break;
                birth_date = dateFormat.parse(dateInput); // Parse the string input to a Date object
                break;
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
            }
        }

        int genderO = 10;
        SexEnum gender = null;
        while (genderO > 3 || genderO < 1) {
            System.out.print("1.Male\n2.Female\n3.Others\nChoose your Gender: ");
            String tmp = scanner.nextLine();
            if (tmp.equals("")) break;
            genderO = Integer.parseInt(tmp);
            switch (genderO) {
                case 1: {
                    gender = SexEnum.MALE;
                    break;
                }
                case 2: {
                    gender = SexEnum.FEMALE;
                    break;
                }
                case 3: {
                    gender = SexEnum.OTHERS;
                    break;
                }
            }
        }

        System.out.print("Enter your Nationality: ");
        String nationality = scanner.nextLine();
        System.out.print("Enter your Email: ");
        String email = scanner.nextLine();

        String username =  "";
        boolean dublicate = true;
        while(dublicate){
            while (true){
                System.out.print("Enter a Username: ");
                username = scanner.nextLine();
                if (username.equals("")) {
                    System.out.println("Username can not be empty!");
                }
                else break;
            }
            for (Person person : PersonDB.persons){
                if (person.getUserName().equals(username)){
                    System.out.println("Username already exists!");
                    dublicate = true;
                    break;
                }
                else dublicate = false;
            }
        }

        String password = "";
        while (true){
            System.out.print("Enter a Password: ");
             password = scanner.nextLine();
            if (password.equals("")) {
                System.out.println("Password can not be empty!");
            }
            else break;
        }


        if (type.equals("M"))
            PersonDB.persons.add(new Member(first_name,last_name, birth_date, gender, nationality, email, username, password));
        else if (type.equals("A"))
            PersonDB.persons.add(new Admin(first_name,last_name, birth_date, gender, nationality, email, username, password));
        else if (type.equals("E"))
            PersonDB.persons.add(new Editor(first_name,last_name, birth_date, gender, nationality, email, username, password));
    }





    static public void adminUserManagment(){
        System.out.print("Enter the name of the member: ");
        String searchMember = scanner.nextLine();
        ArrayList<Member> results = PersonDB.searchMember(searchMember);
        PersonDB.printSearch(results);

        if (results.isEmpty()){
            System.out.print("Nothing Found!");
            return;
        }

        System.out.print("Which one do you want: ");
        int option = Integer.parseInt(scanner.nextLine()) - 1;
        if (option >= 0 && option < results.size()){
            System.out.println("Do you want to Delete or Ban this user? 1.Delete   2.Ban   3.Go Back");
            int option2 = Integer.parseInt(scanner.nextLine());
            switch (option2){
                case 1 ->{
                    PersonDB.persons.remove(results.get(option));
                }
                case 2 ->{
                    results.get(option).isBanned = true;
                }
                case 3 -> {
                    System.out.println("Redirecting ....");
                }
            }
        }
        else
            System.out.println("Invalid Option!");
    }

}
