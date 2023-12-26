import java.util.ArrayList;
import java.util.Date;

public class Editor extends Member {
    ArrayList<Edit> edits = new ArrayList<Edit>();


    public Editor(String firstName, String lastName, Date birthData, SexEnum sex, String nationality, String email, String userName, String passWord) {
        super(firstName, lastName, birthData, sex, nationality, email, userName, passWord);
    }

    public void viewEditsStatus(){
        if (this.edits.isEmpty()){
            System.out.println("You Have No Edits!");
            return;
        }

        int counter = 0;
        for (Edit edit : this.edits){
            System.out.print(++counter + ":\n");
            edit.viewStatus();
        }
    }
}
