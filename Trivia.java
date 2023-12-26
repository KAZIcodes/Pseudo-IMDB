import java.security.cert.TrustAnchor;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Trivia {
    String text;
    boolean isSpoiler;
    long likes;
    long dislikes;
    boolean editorInappropriateFlag;
    Movie triviaMovie;
    ArrayList<Person> peopleLiked =  new ArrayList<>();
    ArrayList<Person> peopleDisliked =  new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);


    public Trivia(String text, boolean isSpoiler, Movie movie) {
        this.text = text;
        this.isSpoiler = isSpoiler;
        this.triviaMovie = movie;
        this.likes = this.dislikes = 0;
        this.editorInappropriateFlag = false;
    }

    public Trivia(Trivia toCopy) {
        this.text = toCopy.text;
        this.isSpoiler = toCopy.isSpoiler;
        this.likes = toCopy.likes;
        this.dislikes = toCopy.dislikes;
        this.editorInappropriateFlag = toCopy.editorInappropriateFlag;
        this.triviaMovie = toCopy.triviaMovie;
    }

    static public Trivia writeTrivia(Movie thisMovie, Trivia toEdit){
        if (toEdit != null){
            System.out.println("If you want a field to stay unchanged leave it empty!");
        }

        System.out.println("Enter your text: ");
        String text = scanner.nextLine();
        if (text.equals("") && toEdit != null) text = toEdit.text;
        int option = 10;
        boolean spoiler = false;
        while (option != 2 && option != 1) {
            System.out.println("Is spoiler? 1.Yes  2.No ");
            String tmp = scanner.nextLine();
            if (tmp.equals("") && toEdit != null) {
                spoiler = toEdit.isSpoiler;
                break;
            }
            option = Integer.parseInt(tmp);
            if (option == 1)
                spoiler = true;
        }


        return new Trivia(text, spoiler, thisMovie);
    }

    static public void viewTrivia(Movie movie){
        int count = 0;
        for (Trivia trivia : movie.trivias){
            if (trivia.isSpoiler) System.out.println("SPOILER ALERT!!!");
            System.out.println(++count + ".\n" + trivia.text + "\nLikes: " + trivia.likes + "   Dislikes: " + trivia.dislikes + "\n------------------------------------------------");
        }
        count++;
        if (PersonDB.personSignedIn != null){
            while (true) {
                if (PersonDB.personSignedIn instanceof Editor){
                    System.out.println(count + ". Go Back\n" + "Select one to edit, like or dislike? ");
                    int option = Integer.parseInt(scanner.nextLine());
                    if (option > count || option < 1) {
                        System.out.println("Invalid Option");
                    }
                    else if (option == count){
                        System.out.println("Going Back...");
                        break;
                    }
                    else{
                        System.out.println("1.Suggest An Edit   2.Like   3.Dislike");
                        int o = Integer.parseInt(scanner.nextLine());
                        if (o == 1){
                            System.out.println("What is your edit details? ");
                            String details = scanner.nextLine();
                            Trivia edited = writeTrivia(movie ,movie.trivias.get(option-1));
                            Admin.editSuggestions.add(new Edit(edited, movie.trivias.get(option-1), details));
                        }
                        if (o == 2){
                            movie.trivias.get(option-1).incLikes();
                            System.out.println("DONE!");
                        }
                        else if (o == 3){
                            movie.trivias.get(option-1).incDislikes();
                            System.out.println("DONE!");
                        }
                    }
                }
                else {
                    if (PersonDB.personSignedIn instanceof Admin){
                        System.out.println(count + ". Go Back\n" + "Select one to modify, like or dislike: ");
                        int option = Integer.parseInt(scanner.nextLine());
                        if (option > count || option < 1) {
                            System.out.println("Invalid Option");
                        }
                        else if (option == count){
                            System.out.println("Going Back...");
                            break;
                        }
                        else{
                            System.out.println("1.Modify   2.Like   3.Dislike");
                            int o = Integer.parseInt(scanner.nextLine());
                            if (o == 1){
                                Trivia toEdit = writeTrivia(movie, movie.trivias.get(option-1));
                                Trivia.applyEdit(new Edit(toEdit, movie.trivias.get(option-1), ""));
                                System.out.println("DONE!");
                            }
                            else if (o == 2){
                                movie.trivias.get(option-1).incLikes();
                                System.out.println("DONE!");
                            }
                            else if (o == 3){
                                movie.trivias.get(option-1).incDislikes();
                                System.out.println("DONE!");
                            }
                        }
                    }
                    else {
                        System.out.println(count + ". Go Back\n" + "Which one do you wanna like or dislike? ");
                        int option = Integer.parseInt(scanner.nextLine());
                        if (option > count || option < 1) {
                            System.out.println("Invalid Option");
                        }
                        else if (option == count){
                            System.out.println("Going Back...");
                            break;
                        }
                        else{
                            System.out.println("1.Like   2.Dislike");
                            int o = Integer.parseInt(scanner.nextLine());
                            if (o == 1){
                                movie.trivias.get(option-1).incLikes();
                                System.out.println("DONE!");
                            }
                            else if (o == 2){
                                movie.trivias.get(option-1).incDislikes();
                                System.out.println("DONE!");
                            }
                        }
                    }

                }

            }
        }
        else {
            System.out.println(count + ".Go Back");
            while(true){
                 int option = Integer.parseInt(scanner.nextLine());
                 if (option == count){
                     System.out.println("Going Back...");
                     break;
                 }
            }
        }

    }

    public void viewThisTrivia(){
        if (this.isSpoiler) System.out.println("SPOILER ALERT!!!");
        System.out.println(this.text + "\nLikes: " + this.likes + "   Dislikes: " + this.dislikes + "\n------------------------------------------------");
    }

    static public void viewGoofs(Movie movie){
        int count = 0;
        for (Trivia goof : movie.goofs){
            if (goof.isSpoiler) System.out.println("SPOILER ALERT!!!");
            System.out.println(++count + ".\n" + goof.text + "\nLikes: " + goof.likes + "   Dislikes: " + goof.dislikes + "\n------------------------------------------------");
        }
        count++;
        if (PersonDB.personSignedIn != null){
            if (PersonDB.personSignedIn instanceof Editor){
                while (true) {
                    System.out.println(count + ". Go Back\n" + "Select one to edit, like or dislike! ");
                    int option = Integer.parseInt(scanner.nextLine());
                    if (option > count || option < 1) {
                        System.out.println("Invalid Option");
                    }
                    else if (option == count){
                        System.out.println("Going Back...");
                        break;
                    }
                    else{
                        System.out.println("1.Like   2.Dislike");
                        int o = Integer.parseInt(scanner.nextLine());
                        if (o == 1){
                            System.out.println("What is your edit details? ");
                            String details = scanner.nextLine();
                            Trivia edited = writeTrivia(movie ,movie.trivias.get(option-1));
                            Admin.editSuggestions.add(new Edit(edited, movie.trivias.get(option-1), details));
                        }
                        if (o == 2){
                            movie.goofs.get(option-1).incLikes();
                            System.out.println("DONE!");
                        }
                        else if (o == 3){
                            movie.goofs.get(option-1).incDislikes();
                            System.out.println("DONE!");
                        }
                    }
                }
            }
            else {
                if (PersonDB.personSignedIn instanceof Admin){
                    while (true) {
                        System.out.println(count + ". Go Back\n" + "Select one to Modify, like or dislike: ");
                        int option = Integer.parseInt(scanner.nextLine());
                        if (option > count || option < 1) {
                            System.out.println("Invalid Option");
                        }
                        else if (option == count){
                            System.out.println("Going Back...");
                            break;
                        }
                        else{
                            System.out.println("1.Modify   2.Like   3.Dislike");
                            int o = Integer.parseInt(scanner.nextLine());
                            if (o == 1){
                                Trivia toEdit = writeTrivia(movie, movie.goofs.get(option-1));
                                Trivia.applyEdit(new Edit(toEdit, movie.goofs.get(option-1), ""));
                                System.out.println("DONE!");
                            }
                            else if (o == 2){
                                movie.goofs.get(option-1).incLikes();
                                System.out.println("DONE!");
                            }
                            else if (o == 3){
                                movie.goofs.get(option-1).incDislikes();
                                System.out.println("DONE!");
                            }
                        }
                    }
                }
                else {
                    while (true) {
                        System.out.println(count + ". Go Back\n" + "Which one do you wanna like or dislike? ");
                        int option = Integer.parseInt(scanner.nextLine());
                        if (option > count || option < 1) {
                            System.out.println("Invalid Option");
                        }
                        else if (option == count){
                            System.out.println("Going Back...");
                            break;
                        }
                        else{
                            System.out.println("1.Like   2.Dislike");
                            int o = Integer.parseInt(scanner.nextLine());
                            if (o == 1){
                                movie.goofs.get(option-1).incLikes();
                                System.out.println("DONE!");
                            }
                            else if (o == 2){
                                movie.goofs.get(option-1).incDislikes();
                                System.out.println("DONE!");
                            }
                        }
                    }
                }
            }
        }
        else {
            System.out.println(count + ".Go Back");
            while(true){
                int option = Integer.parseInt(scanner.nextLine());
                if (option == count){
                    System.out.println("Going Back...");
                    break;
                }
            }
        }

    }

    static public void applyEdit(Edit edited){
        int index = ((Trivia)edited.originalObj).triviaMovie.trivias.indexOf((Trivia)edited.originalObj);
        ((Trivia)edited.originalObj).triviaMovie.trivias.set(index, (Trivia) edited.objToEdit);
        edited.isApproved = true;
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

}
