import java.lang.management.MonitorInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;

public class Admin extends Member {

    static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Report> reports = new ArrayList<>();
    public static ArrayList<Edit> editSuggestions = new ArrayList<>();

    public Admin(String firstName, String lastName, Date birthData, SexEnum sex, String nationality, String email, String userName, String passWord) {
        super(firstName, lastName, birthData, sex, nationality, email, userName, passWord);
    }
    public static ArrayList<Report> getReports() {
        return reports;
    }



    static public void viewEditSuggestions(){
        if (editSuggestions.isEmpty()){
            System.out.println("No Edit Suggestions Available!");
            return;
        }

        int counter = 0;
        for (Edit edit : editSuggestions){
            String type = "Not Defined";
            if (edit.objToEdit instanceof Movie) type = "Movie";
            else if (edit.objToEdit instanceof Review) type = "Review";
            else if (edit.objToEdit instanceof Actor) type = "Actor";
            else if (edit.objToEdit instanceof Director) type = "Director";
            else if (edit.objToEdit instanceof Writer) type = "Writer";
            else if (edit.objToEdit instanceof Post) type = "Post";
            else if (edit.objToEdit instanceof Trivia) type = "Trivia or Goof";
            else if (edit.objToEdit instanceof Quotes) type = "Quote";
            else if (edit.objToEdit instanceof SoundTrack) type = "Sound Track";
            System.out.println(++counter + ".\n" + "Edit Details: " + edit.editDetails + "\nType of the data to edit: " + type + "\n--------------------------");
        }
        System.out.println(++counter + ". Go Back...");
        System.out.println("Which one do you want to see after edit: ");
        int option = Integer.parseInt(scanner.nextLine());
        if (option == counter){
            System.out.println("Going back...");
        }
        else if (option > counter || option < 1){
            System.out.println("Invalid Option!");
        }
        else {
            Edit thisEdit = editSuggestions.get(option-1);
            boolean delete = false;
            thisEdit.viewEdit();
            if ((thisEdit.objToEdit instanceof Review && ((Review)thisEdit.objToEdit).editorInappropriateFlag) || (thisEdit.objToEdit instanceof Post && ((Post)thisEdit.objToEdit).editorInappropriateFlag)) {
                System.out.println("Editor marked this review as inappropriate!");
                 delete = true;
            }
            System.out.print("What do you wanna do? 1.Apply Edit   2.Reject Edit   3.Go Back...\n Choose: ");
            int option2 = Integer.parseInt(scanner.nextLine());
            if (option2 == 3){
                System.out.println("Redirecting...");
            }
            else if (option2 == 1){
                if (thisEdit.objToEdit instanceof Movie) {
                    Movie.applyEdit(thisEdit);
                    System.out.println("DONE!");
                }
                else if (thisEdit.objToEdit instanceof Review) {
                    Review.applyEdit(thisEdit, delete);
                    System.out.println("DONE!");
                }
                else if (thisEdit.objToEdit instanceof Actor) {
                    Actor.applyEdit(thisEdit);
                    System.out.println("DONE!");
                }
                else if (thisEdit.objToEdit instanceof Director) {
                    Director.applyEdit(thisEdit);
                    System.out.println("DONE!");
                }
                else if (thisEdit.objToEdit instanceof Writer) {
                    Writer.applyEdit(thisEdit);
                    System.out.println("DONE!");
                }
                else if (thisEdit.objToEdit instanceof Post) {
                    Post.applyEdit(thisEdit);
                    System.out.println("DONE!");
                }
                else if (thisEdit.objToEdit instanceof Trivia) {
                    Trivia.applyEdit(thisEdit);
                    System.out.println("DONE!");
                }
                else if (thisEdit.objToEdit instanceof Quotes) {
                    Quotes.applyEdit(thisEdit);
                    System.out.println("DONE!");
                }
                else if (thisEdit.objToEdit instanceof SoundTrack) {
                    SoundTrack.applyEdit(thisEdit);
                    System.out.println("DONE!");
                }
            }
            else if (option2 == 2){
                thisEdit.isRejected = true;
                editSuggestions.remove(thisEdit);
                System.out.println("DONE!");
            }
            else {
                System.out.println("Invalid Option!");
            }
        }
    }
}
