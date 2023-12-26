import java.util.ArrayList;
import java.util.Scanner;

public class Comment {
    String text;
    Member writer;
    boolean isSpoiler;
    long likes;
    long dislikes;
    boolean editorInappropriateFlag;
    ArrayList<Comment> replys = new ArrayList<>(); //not used
    ArrayList<Person> peopleLiked =  new ArrayList<>();
    ArrayList<Person> peopleDisliked =  new ArrayList<>();

    static Scanner scanner = new Scanner(System.in);

    public Comment(String text, Member writer, boolean isSpoiler) {
        this.text = text;
        this.writer = writer;
        this.likes = 0;
        this.dislikes = 0;
        this.editorInappropriateFlag = false;
        this.isSpoiler = isSpoiler;
    }



    public void viewComment(){
        if (isSpoiler){
            System.out.println("SPOILER ALERT!");
        }
        System.out.println("Comment: " + this.text + "\nWritten By: " + this.writer.getName() + "\nLikes: " + this.likes + "    Dislikes: " + this.dislikes);
        if (!(PersonDB.personSignedIn instanceof Admin)) {
            System.out.println("1.Like 2.Dislike 3.Report Which one? ");
            int o = 10;
            while(true) {
                try {
                    o = Integer.parseInt(scanner.nextLine());
                    break;
                }
                catch(NumberFormatException e) {
                    System.out.println("Enter a Number!");
                }
            }
            if (o == 1) this.incLikes();
            else if (o == 2) this.incDislikes();
            else if (o == 3){
                System.out.print("Enter report details: ");
                String details = scanner.nextLine();
                Report report = new Report(this, details, ReportEnum.COMMENT);
                Admin.reports.add(report); //send to admins for reviewing
                ((Member) PersonDB.personSignedIn).reports.add(report);
                System.out.println("Reported!");
            }
            else System.out.println("Invalid Option!");
        }
        else {
            System.out.println("1.Like 2.Dislike 3.Modify This Comment   Which one? ");
            int o = 10;
            while(true) {
                try {
                    o = Integer.parseInt(scanner.nextLine());
                    break;
                }
                catch(NumberFormatException e) {
                    System.out.println("Enter a Number!");
                }
            }
            if (o == 1) this.incLikes();
            else if (o == 2) this.incDislikes();
            else if (o == 3){
                this.modifyThisComment();
            }
            else System.out.println("Invalid Option!");
        }
    }


    public void modifyThisComment(){
        System.out.println("1.Like    2.Dislike   3.Set Spoiler Alert   4.Delete");

        int option2 = Integer.parseInt(scanner.nextLine());
        if (option2 == 1)
            this.incLikes();
        else if (option2 == 2)
            this.incDislikes();
        else if (option2 == 3)
            this.isSpoiler = true;
        else if (option2 == 4) {
            for (Post post : Forum.posts){
                if (post.comments.contains(this)){
                    post.comments.remove(this);
                    break;
                }
            }
        }
        else System.out.println("Invalid Option");
    }



    public void incLikes() {
        if (!peopleLiked.contains(PersonDB.personSignedIn)) likes++;
    }

    public void incDislikes() {
        if (peopleDisliked.contains(PersonDB.personSignedIn)) dislikes++;
    }
}
