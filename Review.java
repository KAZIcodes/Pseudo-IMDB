import java.util.ArrayList;
import java.util.Scanner;

public class Review {
    String reviewText;
    int stars;  //for rating
    boolean isSpoiler;
    long likes;
    long dislikes;
    boolean editorInappropriateFlag;
    Movie movieReviewed;
    ArrayList<Person> peopleLiked =  new ArrayList<>();
    ArrayList<Person> peopleDisliked =  new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);


    public Review(String reviewText, int stars, boolean isSpoiler, Movie movie) {
        this.reviewText = reviewText;
        this.stars = stars;
        this.isSpoiler = isSpoiler;
        this.movieReviewed = movie;
        this.likes = this.dislikes = 0;
        this.editorInappropriateFlag = false;
    }

    public Review(Review toCopy) {
        this.reviewText = toCopy.reviewText;
        this.stars = toCopy.stars;
        this.isSpoiler = toCopy.isSpoiler;
        this.likes = toCopy.likes;
        this.dislikes = toCopy.dislikes;
        this.editorInappropriateFlag = toCopy.editorInappropriateFlag;
        this.movieReviewed = toCopy.movieReviewed;
    }

    public void printReview(){
        if (isSpoiler)
            System.out.println("SPOILER ALERT!!!\n" + "Review: " + this.reviewText + "\nUser Rate: " + this.stars + "\nLikes: " + this.likes + "\nDislikes: " + this.dislikes);
        else
            System.out.println("Review: " + this.reviewText + "\nUser Rate: " + this.stars + "\nLikes: " + this.likes + "\nDislikes: " + this.dislikes);
    }

    public void incLikes() {
        if (!this.peopleLiked.contains(PersonDB.personSignedIn))
            this.likes++;
        else System.out.println("You have already liked this");
    }

    public void incDislikes() {
        if (!this.peopleDisliked.contains(PersonDB.personSignedIn))
            this.dislikes++;
        else System.out.println("You have already liked this");
    }



    static public void modifyReviws(){
        System.out.print("Enter the title of the movie which reviews you want to modify: ");
        String searchTitle = scanner.nextLine();
        ArrayList<Movie> results = MovieDB.searchMovie(searchTitle);
        if (results.isEmpty()){
            System.out.println("Nothing Found!");
        } else {
            int counter = 0;
            for (Movie movie : results) {
                System.out.println(++counter + ". " + movie.getTitle());
            }
            System.out.print("Choose one: ");
            int option = Integer.parseInt(scanner.nextLine());
            if (option <= counter && option > 0){
                if (results.get(option-1).getReviews().isEmpty())
                    System.out.println("This movie has no reviews!");
                else
                    results.get(option-1).viewReviews();
            }
        }
    }

    public void modifyThisReview(){
        System.out.println("1.Like    2.Dislike   3.Set Spoiler Alert   4.Delete");

        int option2 = Integer.parseInt(scanner.nextLine());
        if (option2 == 1)
            this.incLikes();
        else if (option2 == 2)
            this.incDislikes();
        else if (option2 == 3)
            this.isSpoiler = true;
        else if (option2 == 4) {
            MovieDB.removeReview(this);
        }
    }


    public void writeEdit() {
        Review copyReview = new Review(this);
        System.out.println("1.Set Spoiler Alert   2.Flag As Inappropriate Content   3.Go Back...");
        int option = Integer.parseInt(scanner.nextLine());
        if (option == 3) {
            System.out.println("Redirecting...");
            return;
        } else if (option == 1) {
            copyReview.isSpoiler = true;
        } else if (option == 2) {
            copyReview.editorInappropriateFlag = true;
        }
        else
            System.out.println("Invalid Option!");
        System.out.print("Enter edit details: ");
        String details = scanner.nextLine();
        Edit newEdit = new Edit(copyReview, this, details);
        ((Editor)PersonDB.personSignedIn).edits.add(newEdit);
        Admin.editSuggestions.add(newEdit);
        System.out.println("Edit sent to admins successfully!");
    }

    static public void applyEdit(Edit editToApply, boolean delete){
        if (delete){
            ((Review)editToApply.objToEdit).movieReviewed.getReviews().remove((Review) editToApply.objToEdit);
        }
        else {
            int indexToReplace = ((Review)editToApply.objToEdit).movieReviewed.getReviews().indexOf((Review) editToApply.originalObj);
            ((Review)editToApply.objToEdit).movieReviewed.getReviews().set(indexToReplace, (Review) editToApply.objToEdit);
        }
        editToApply.isApproved = true;
        Admin.editSuggestions.remove(editToApply);
        System.out.println("Edits Applied!");
    }
}
