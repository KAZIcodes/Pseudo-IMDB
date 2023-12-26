import javax.swing.text.html.parser.Parser;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.SimpleTimeZone;

public class Post {
    String title;
    String text;
    Member writer;
    Member repostedBy;
    long likes;
    long dislikes;
    boolean isSpoiler;
    boolean editorInappropriateFlag;
    Date publishDate;
    ArrayList<Comment> comments =  new ArrayList<Comment>();
    ArrayList<Person> peopleLiked =  new ArrayList<>();
    ArrayList<Person> peopleDisliked =  new ArrayList<>();

    static Scanner scanner = new Scanner(System.in);

    public Post(String title, String text, Member writer, boolean isSpoiler) {
        this.title = title;
        this.text = text;
        this.writer = writer;
        this.likes = 0;
        this.dislikes = 0;
        this.editorInappropriateFlag = false;
        this.isSpoiler = isSpoiler;
        this.publishDate = new Date();
        this.repostedBy = null;
    }

    public Post(Post post) {
        this.title = post.title;
        this.text = post.text;
        this.writer = post.writer;
        this.likes = post.likes;
        this.dislikes = post.dislikes;
        this.editorInappropriateFlag = post.editorInappropriateFlag;
        this.isSpoiler = post.isSpoiler;
        this.comments = post.comments;
        this.peopleLiked = post.peopleLiked;
        this.peopleDisliked = post.peopleDisliked;
        this.publishDate = post.publishDate;
        this.repostedBy = post.repostedBy;
    }

    public void viewPost(boolean editMode){
        if (this.repostedBy != null){
            System.out.println("This is a repost by: " + this.repostedBy.getName());
        }
        if (this.isSpoiler){
            System.out.println("SPOILER ALERT!");
        }


        System.out.println("Title: " + this.title + "\nPost Text: " + this.text + "\nWritten By:  " + this.writer.getName() + "\nLikes: " + this.likes + "    Dislikes: " + this.dislikes + "\nPublish Date: " + this.publishDate + "\n-------------------------------");
        if (editMode){//edit observing edit
            System.out.println("1.Go Back...");
            String inp = scanner.nextLine();
            return;
        }

        if (((Member) PersonDB.personSignedIn).posts.contains(this)){
            System.out.print("1.Delete This Post   2.View Comments   3.Go Back...\nChoose One: ");
            int option = 10;
            while(true) {
                try {
                    option = Integer.parseInt(scanner.nextLine());
                    break;
                }
                catch(NumberFormatException e) {
                    System.out.println("Enter a Number!");
                }
            }
            if (option == 3){
                System.out.println("Going Back...");
            }
            else if (option == 1){
                ((Member) PersonDB.personSignedIn).posts.remove(this);
                Forum.posts.remove(this);
                System.out.println("Successfully Deleted!");
            }
            else if (option == 2){
                viewPostComments();
            }
            else System.out.println("Invalid Option!");
            return;
        }
        else if (PersonDB.personSignedIn instanceof Admin){
            System.out.print("1.Like   2.Dislike   3.View Comments   4.Write A Comment   5.Repost   6.Modify This Post   7.Go Back...\nChoose One: ");
        }
        else if (PersonDB.personSignedIn instanceof Editor){
            System.out.print("1.Like   2.Dislike   3.View Comments   4.Write A Comment   5.Repost   6.Suggest An Edit   7.Go Back...\nChoose One: ");
        }
        else {
            System.out.print("1.Like   2.Dislike   3.View Comments   4.Write A Comment   5.Repost   6.Report   7.Go Back...\nChoose One: ");
        }

        int option = 10;
        while(true) {
            try {
                option = Integer.parseInt(scanner.nextLine());
                break;
            }
            catch(NumberFormatException e) {
                System.out.println("Enter a Number!");
            }
        }
        if (option == 7) System.out.println("Going Back...");
        else if (option == 1){
            if (!this.peopleLiked.contains(PersonDB.personSignedIn)) this.likes++;
            else System.out.println("You have already liked this!");
        }
        else if (option == 2){
            if (!this.peopleDisliked.contains(PersonDB.personSignedIn)) this.dislikes++;
            else System.out.println("You have already disliked this!");
        }
        else if (option == 3){
            viewPostComments();
        }
        else if (option == 4){
            //write a comment
            System.out.println("What is your comment?");
            String cm = scanner.nextLine();

            int o = 10;
            boolean spoiler = false;
            while (o != 2 && o != 1) {
                System.out.println("Is spoiler? 1.Yes  2.No ");
                o = Integer.parseInt(scanner.nextLine());
                if (o == 1)
                    spoiler = true;
            }

            this.comments.add(new Comment(cm, (Member) PersonDB.personSignedIn, spoiler));
            System.out.println("Your comment was succcessfully added!");
        }
        else if (option == 5){
            Post copy = new Post(this);
            copy.repostedBy = (Member) PersonDB.personSignedIn;
            ((Member)PersonDB.personSignedIn).posts.add(copy);
            System.out.println("DONE");
        }
        else if (option == 6){
            if (PersonDB.personSignedIn instanceof Admin){
                this.modifyThisPost();
            }
            else if (PersonDB.personSignedIn instanceof Editor){
                Post copy = new Post(this);
                System.out.println("Do you wanna mark as Inappropriate content? 1.Yes  2.No");
                try{
                    int bool = Integer.parseInt(scanner.nextLine());
                    if (bool == 1) copy.editorInappropriateFlag = true;
                    else if (bool == 2) copy.editorInappropriateFlag = false;
                    else System.out.println("Invalid Option!");
                }catch (NumberFormatException e){
                    System.out.println("Enter a number!");
                }
                System.out.println("Leave a field empty if you don't want to modify it: ");
                Forum.writeNewPost(copy);
                System.out.println("Enter your edit details: ");
                String details = scanner.nextLine();
                Edit newEdit = new Edit(copy, this, details);
                Admin.editSuggestions.add(newEdit);
                ((Editor)PersonDB.personSignedIn).edits.add(newEdit);
                System.out.println("Edit successfully sent to admins!");
            }
            else {
                System.out.print("Enter report details: ");
                String details = scanner.nextLine();
                Report report = new Report(this, details, ReportEnum.POST);
                Admin.reports.add(report); //send to admins for reviewing
                ((Member) PersonDB.personSignedIn).reports.add(report);
                System.out.println("Reported!");
            }

        }
        else System.out.println("Invalid Option!");
    }


    private void viewPostComments(){
        if (this.comments.isEmpty()){
            System.out.println("There is no comment on this post!");
            return;
        }

        int counter = 0;
        for (Comment comment : this.comments){
            if (comment.isSpoiler){
                System.out.println("SPOILER ALERT!");
            }
            System.out.println(++counter + ") Comment: " + comment.text + "\nWritten By: " + comment.writer.getName() + "\nLikes: " + comment.likes + "    Dislikes: " + comment.dislikes + "\n------------------------------------");
        }
        System.out.print(++counter + ".Go Back" + "\nChoose one: ");
        int option = 10;
        while(true) {
            try {
                option = Integer.parseInt(scanner.nextLine());
                break;
            }
            catch(NumberFormatException e) {
                System.out.println("Enter a Number!");
            }
        }
        if (option == counter) System.out.println("Going Back...");
        else if (option > counter || option < 1)  System.out.println("Invalid Option!");
        else if (!(PersonDB.personSignedIn instanceof Admin)) {
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
            if (o == 1 && !this.comments.get(option-1).peopleLiked.contains(PersonDB.personSignedIn)) this.comments.get(option-1).likes++;
            else if (o == 2 && !this.comments.get(option-1).peopleDisliked.contains(PersonDB.personSignedIn)) this.comments.get(option-1).dislikes++;
            else if (o == 3){
                System.out.print("Enter report details: ");
                String details = scanner.nextLine();
                Report report = new Report(this.comments.get(option-1), details, ReportEnum.COMMENT);
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
            if (o == 1 && !this.comments.get(option-1).peopleLiked.contains(PersonDB.personSignedIn)) this.comments.get(option-1).likes++;
            else if (o == 2 && !this.comments.get(option-1).peopleDisliked.contains(PersonDB.personSignedIn)) this.comments.get(option-1).dislikes++;
            else if (o == 3){
                this.comments.get(option-1).modifyThisComment();
            }
            else System.out.println("Invalid Option!");
        }

    }


    public void modifyThisPost(){
        System.out.print("1.Like    2.Dislike   3.Set Spoiler Alert   4.Delete Choose: ");

        int option2 = Integer.parseInt(scanner.nextLine());
        if (option2 == 1)
            this.incLikes();
        else if (option2 == 2)
            this.incDislikes();
        else if (option2 == 3)
            this.isSpoiler = true;
        else if (option2 == 4) {
            Forum.posts.remove(this);
            ((Member)PersonDB.personSignedIn).posts.remove(this);
        }
        else System.out.println("Invalid Option");
    }

    static public void applyEdit(Edit edit){
        //for forum
        int index = Forum.posts.indexOf((Post) edit.originalObj);
        Forum.posts.set(index, (Post) edit.objToEdit);
        //for member:
        int index2 = ((Member)((Post) edit.originalObj).writer).posts.indexOf((Post) edit.originalObj);
        ((Member)((Post) edit.originalObj).writer).posts.set(index2, (Post) edit.objToEdit);
        edit.isApproved = true;
    }

    public long getLikes() {
        return likes;
    }

    public void incLikes() {
        if (!peopleLiked.contains(PersonDB.personSignedIn)) likes++;
    }

    public void incDislikes() {
        if (peopleDisliked.contains(PersonDB.personSignedIn)) dislikes++;
    }
}
