import java.util.ArrayList;
import java.util.Scanner;
import java.util.WeakHashMap;

public class Quotes {
    String text;
    boolean isSpoiler;
    long likes;
    long dislikes;
    boolean editorInappropriateFlag;
    Movie quoteMovie;
    ArrayList<Person> peopleLiked =  new ArrayList<>();
    ArrayList<Person> peopleDisliked =  new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);


    public Quotes(String text, boolean isSpoiler, Movie movie) {
        this.text = text;
        this.isSpoiler = isSpoiler;
        this.quoteMovie = movie;
        this.likes = this.dislikes = 0;
        this.editorInappropriateFlag = false;
    }

    public Quotes(Quotes toCopy) {
        this.text = toCopy.text;
        this.isSpoiler = toCopy.isSpoiler;
        this.likes = toCopy.likes;
        this.dislikes = toCopy.dislikes;
        this.editorInappropriateFlag = toCopy.editorInappropriateFlag;
        this.quoteMovie = toCopy.quoteMovie;
    }

    static public Quotes writeQuote(Movie movie, Quotes toEdit){
        if (toEdit != null){
            System.out.println("If you want a field to stay unchanged leave it empty!");
        }

        System.out.println("If finished type 'exit'!");
        String text = "";
        while (true){
            System.out.println("What is the name of the charchter saying the dialog: ");
            String charc = scanner.nextLine();
            if (charc.equalsIgnoreCase("exit")){
                break;
            }
            if (charc.equalsIgnoreCase("")){
                text = "";
                break;
            }
            System.out.println("What is the dialog: ");
            String dialog = scanner.nextLine();
            if (charc.equalsIgnoreCase("exit")){
                break;
            }
            if (charc.equalsIgnoreCase("")){
                text = "";
                break;
            }
            text += charc + ": " + dialog + "\n";
        }
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

        return new Quotes(text, spoiler, movie);
    }

    static public void viewQuotes(Movie movie){
        int count = 0;
        for (Quotes quotes : movie.quotes){
            if (quotes.isSpoiler) System.out.println("SPOILER ALERT!!!");
            System.out.println(++count + ".\n" + quotes.text + "\nLikes: " + quotes.likes + "   Dislikes: " + quotes.dislikes + "\n------------------------------------------------");
        }
        count++;
        if (PersonDB.personSignedIn != null){
            if (PersonDB.personSignedIn instanceof Editor){
                while (true) {
                    System.out.println(count + ". Go Back\n" + "Which one do you wanna like or dislike or edit? ");
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
                            Quotes edited = writeQuote(movie ,movie.quotes.get(option-1));
                            Admin.editSuggestions.add(new Edit(edited, movie.quotes.get(option-1), details));
                        }
                        else if (o == 2){
                            movie.quotes.get(option-1).incLikes();
                            System.out.println("DONE!");
                        }
                        else if (o == 3){
                            movie.quotes.get(option-1).incDislikes();
                            System.out.println("DONE!");
                        }
                    }
                }
            }
            else {
                if (PersonDB.personSignedIn instanceof Admin){
                    while (true) {
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
                                Quotes toEdit = writeQuote(movie, movie.quotes.get(option-1));
                                Quotes.applyEdit(new Edit(toEdit, movie.quotes.get(option-1), ""));
                                System.out.println("DONE!");
                            }
                            else if (o == 2){
                                movie.quotes.get(option-1).incLikes();
                                System.out.println("DONE!");
                            }
                            else if (o == 3){
                                movie.quotes.get(option-1).incDislikes();
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
                                movie.quotes.get(option-1).incLikes();
                                System.out.println("DONE!");
                            }
                            else if (o == 2){
                                movie.quotes.get(option-1).incDislikes();
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


    public void viewThisQuote(){
        if (this.isSpoiler) System.out.println("SPOILER ALERT!!!");
        System.out.println(this.text + "\nLikes: " + this.likes + "   Dislikes: " + this.dislikes + "\n------------------------------------------------");
    }

    static public void applyEdit(Edit edited){
        int index = ((Quotes)edited.originalObj).quoteMovie.quotes.indexOf((Quotes)edited.originalObj);
        ((Quotes)edited.originalObj).quoteMovie.quotes.set(index, (Quotes) edited.objToEdit);
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

