import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Movie {
    String title;
    double rating;
    long ratingAmount;
    String  plotSummary;
    Genre genre;
    ArrayList<Media> media = new ArrayList<Media>(); //Paths of the poster/trailer files on the server for example
    Date releaseDate;
    String language;
    Director director;
    ArrayList<Writer> writers = new ArrayList<Writer>();
    ArrayList<ActorRole> cast = new ArrayList<ActorRole>();
    ArrayList<Review> reviews = new ArrayList<Review>();
    ArrayList<Trivia> trivias = new ArrayList<Trivia>();
    ArrayList<Trivia> goofs = new ArrayList<Trivia>(); //goofs is kind of a trivia
    ArrayList<Quotes> quotes = new ArrayList<Quotes>();
    ArrayList<SoundTrack> soundTracks = new ArrayList<SoundTrack>();
    static Scanner scanner = new Scanner(System.in);

    public String getTitle() {
        return title;
    }



    public Movie(String title, String plotSummary, Genre genre, ArrayList<Media> media, Date releaseDate, String language, Director director, ArrayList<Writer> writers, ArrayList<ActorRole> cast) {
        this.title = title;
        this.plotSummary = plotSummary;
        this.genre = genre;
        this.media = media;
        this.releaseDate = releaseDate;
        this.language = language;
        this.director = director;
        this.writers = writers;
        this.cast = cast;

        this.rating = 0;
        this.ratingAmount = 0;
        if (genre != null)
            genre.movies.add(this); //know that you should pass a genre from static genres in movie db to it
    }

    public Movie(Movie original) {
        this.title = original.title;
        this.rating = original.rating;
        this.ratingAmount = original.ratingAmount;
        this.plotSummary = original.plotSummary;
        this.genre = original.genre;
        this.media = original.media;
        this.releaseDate = new Date(original.releaseDate.getTime()); // Create a new Date object
        this.language = original.language;
        this.director = original.director;
        this.writers = new ArrayList<>(original.writers); // Copy the ArrayList
        this.cast = new ArrayList<>(original.cast); // Copy the ArrayList
        this.reviews = new ArrayList<>(original.reviews); // Copy the ArrayList
        this.trivias = original.trivias;
        this.goofs = original.goofs;
        this.quotes = original.quotes;
        this.soundTracks = original.soundTracks;
    }

    public void view(boolean editMode){
        System.out.println("Title: " + this.title + "\nRating: " + this.rating + "\nAmount of people rated: " + this.ratingAmount
                + "\nPlot Summary: " + this.plotSummary + "\nDirector: " + this.director.getName()+ "\nGenre: " + this.genre.getGenre().name() + "\nLanguage: " + this.language + "\nRelease Date: " + this.releaseDate
        + "\n-----------------------------------------");
        int option = 10;
        while(true){
            if (PersonDB.personSignedIn instanceof Editor){
                System.out.println("1.View Director\n2.View Cast\n3.View Writers\n4.View Reviews\n5.Add To Custom List\n6.Write a Review/Rate\n7.Trivias\n8.Goofs\n9.Quotes\n10.Sound Tracks\n11.Suggest Edit To This Movie\n12.Go Back");
                option = Integer.parseInt(scanner.nextLine());
                if (option > 12 || option < 1){
                    System.out.println("Invalid Option!");
                    continue;
                }
                else if (option == 12){
                    System.out.println("Redirecting...");
                    break;
                }
            }
            else {
                System.out.println("1.View Director\n2.View Cast\n3.View Writers\n4.View Reviews\n5.Add To Custom List(Not available if observing a Edit suggested movie)\n6.Write a Review/Rate\n7.Trivias\n8.Goofs\n9.Quotes\n10.Sound Tracks\n11.Go Back");
                option = Integer.parseInt(scanner.nextLine());
                if (option > 11 || option < 1){
                    System.out.println("Invalid Option!");
                    continue;
                }
                else if (option == 11){
                    System.out.println("Redirecting...");
                    break;
                }
                else if (option == 5 && editMode){
                    System.out.println("Option Not available in observing edit suggested movie!");
                    continue;
                }
            }

            switch (option){
                case 1 -> {
                    this.director.view(false);
                }
                case 2 -> {
                    viewCast();
                }
                case 3 -> {
                    viewWriters();
                }
                case 4 -> {
                    viewReviews();
                }
                case 5 -> {
                    addToCustomList();
                }
                case 6 -> {
                    writeReview();
                }
                case 7 -> {
                    doubleOptions("trivia");
                }
                case 8 -> {
                    doubleOptions("goofs");
                }
                case 9 -> {
                    doubleOptions("quotes");
                }
                case 10 -> {
                    doubleOptions("ST");
                }
                case 11 -> {
                    writeEdit(this);
                }
            }
        }
    }

    private void doubleOptions(String type){
        if (type.equals("trivia")){
            System.out.println("Which one do you want? 1.Write a trivia   2.See all trivias");
            int option = Integer.parseInt(scanner.nextLine());
            if (option == 1 && PersonDB.personSignedIn != null){
                this.trivias.add(Trivia.writeTrivia(this, null));
            }
            else if (option == 2){
                Trivia.viewTrivia(this);
            }
            else System.out.println("Invalid Option or You must signin!");
        }
        else if (type.equals("quotes")){
            System.out.println("Which one do you want? 1.Write a quote   2.See all quotes");
            int option = Integer.parseInt(scanner.nextLine());
            if (option == 1 && PersonDB.personSignedIn != null){
                this.quotes.add(Quotes.writeQuote(this, null));
            }
            else if (option == 2){
                Quotes.viewQuotes(this);
            }
            else System.out.println("Invalid Option or You must signin!");
        }
        else if (type.equals("goofs")){
            System.out.println("Which one do you want? 1.Write a goof   2.See all goofs");
            int option = Integer.parseInt(scanner.nextLine());
            if (option == 1 && PersonDB.personSignedIn != null){
                this.goofs.add(Trivia.writeTrivia(this, null));
            }
            else if (option == 2){
                Trivia.viewGoofs(this);
            }
            else System.out.println("Invalid Option or You must signin!");
        }
        else if (type.equals("ST")){
            System.out.println("Which one do you want? 1.Write a Sound Track   2.See all Sound Tracks");
            int option = Integer.parseInt(scanner.nextLine());
            if (option == 1 && PersonDB.personSignedIn != null){
                this.soundTracks.add(SoundTrack.writeST(this, null));
            }
            else if (option == 2){
                SoundTrack.viewST(this);
            }
            else System.out.println("Invalid Option or You must signin!");
        }
    }

    private void writeReview(){
        System.out.println("Write review text: ");
        String Rtext = scanner.nextLine();
        System.out.println("Enter your rate out of 10: ");
        String tempInp = scanner.nextLine();
        while (tempInp.contains(".")){
            System.out.println("Only Integer Rating!");
            tempInp = scanner.nextLine();
        }
        int star = Integer.parseInt(tempInp);
        int option = 10;
        boolean spoiler = false;
        while (option != 2 && option != 1) {
            System.out.println("Is spoiler? 1.Yes  2.No ");
            option = Integer.parseInt(scanner.nextLine());
            if (option == 1)
                spoiler = true;
        }

        Review review = new Review(Rtext, star, spoiler, this);
        this.reviews.add(review);
        ((Member)PersonDB.personSignedIn).getReviews().add(review);
        updateRating(star);
    }

    private void addToCustomList(){
        System.out.println("Which custom list to add?");
        ((Member)PersonDB.personSignedIn).printMemberLists();
        System.out.println("Choose an option: ");
        int option = Integer.parseInt(scanner.nextLine());
        option--;
        if (option >= 0 && option < ((Member)PersonDB.personSignedIn).customLists.size()) {
            ((Member)PersonDB.personSignedIn).customLists.get(option).movies.add(this);
        } else {
            System.out.println("Invalid Option !");
        }
    }

    public void viewReviews(){
        if (this.reviews.isEmpty()){
            System.out.println("There is no Review for this movie!");
            return;
        }

        int counter = 1;
        for (Review review : this.reviews){
            System.out.println(counter++ + ")");
            review.printReview();
            System.out.println("----------------------------------");
        }
        System.out.println(counter + ". Go Back");
        if (PersonDB.personSignedIn instanceof Admin){
            System.out.print("Choose the one you want to like or dislike or Delete or set Spoiler Alert:");
        }
        else if (PersonDB.personSignedIn instanceof Editor){
            System.out.print("Choose the one you want to like or dislike or suggest edit for:");
        }
        else {
            System.out.print("Choose the one you want to like or dislike or report ...");
        }

        int option = Integer.parseInt(scanner.nextLine());
        if (counter == option) {return;}
        if (option < counter && option >= 1){
            if (PersonDB.personSignedIn instanceof Admin){
                System.out.println("1.Like    2.Dislike   3.Set Spoiler Alert   4.Delete");
            }
            else if (PersonDB.personSignedIn instanceof Editor){
                System.out.println("1.Like    2.Dislike   3.Suggest Edit");
            }
            else {
                System.out.println("1.Like    2.Dislike    3.Report");
            }

            int option2 = Integer.parseInt(scanner.nextLine());
            if (option2 == 1) {
                this.reviews.get(option - 1).incLikes();
                System.out.println("DONE");
            }
            else if (option2 == 2) {
                this.reviews.get(option - 1).incDislikes();
                System.out.println("DONE");
            }
            else if (PersonDB.personSignedIn instanceof Admin){
                if (option2 == 3){
                    this.reviews.get(option-1).isSpoiler = true;
                    System.out.println("DONE");
                }
                else if (option2 == 4){
                    this.getReviews().remove(this.reviews.get(option-1));
                    System.out.println("DONE");
                }
                else System.out.println("Invalid Option!");
            }
            else if (PersonDB.personSignedIn instanceof Editor){
                if (option2 == 3){
                    this.reviews.get(option-1).writeEdit();
                    System.out.println("DONE");
                }
                else
                    System.out.println("Invalid Option!");
            }
            else {
                if (option2 == 3) {
                    System.out.print("Enter report details: ");
                    String details = scanner.nextLine();
                    Report report = new Report(this.reviews.get(option - 1), details, ReportEnum.REVIEW);
                    Admin.reports.add(report); //send to admins for reviewing
                    ((Member) PersonDB.personSignedIn).reports.add(report);
                    System.out.println("Reported!");
                }
                else
                    System.out.println("Invalid Option!");
            }

        }
    }


    private void viewCast(){
        int counter = 1;
        for (ActorRole actor : this.cast){
            System.out.println(counter++ + ". " + actor.getActorRole());
        }
        System.out.println(counter + ". Go Back");
        System.out.println("Choose one to view...");
        int option = Integer.parseInt(scanner.nextLine());
        if (counter == option) {return;}
        if (option < counter && option >= 1){
            this.cast.get(option-1).actor.view(false);
        }
    }
    private void viewWriters(){
        int counter = 1;
        for (Writer writer : this.writers){
            System.out.println(counter++ + ". " + writer.getName());
        }
        System.out.println(counter + ". Go Back");
        System.out.println("Choose one to view...");
        int option = Integer.parseInt(scanner.nextLine());
        if (counter == option) {return;}
        if (option < counter && option >= 1){
            this.writers.get(option-1).view(false);
        }
    }

    static public void writeEdit(Movie movieToEdit){
        Movie copyMovie = new Movie(movieToEdit);
        MovieDB.addMovie(copyMovie);
        System.out.print("Enter details about your edit: ");
        String details = scanner.nextLine();
        Edit newEdit = new Edit(copyMovie, movieToEdit, details);
        Admin.editSuggestions.add(newEdit);
        ((Editor)PersonDB.personSignedIn).edits.add(newEdit);
        System.out.println("Edit sent to admins successfully!");
    }

    static public void applyEdit(Edit editToApply){
        int indexToReplace = MovieDB.movies.indexOf((Movie) editToApply.originalObj);
        MovieDB.movies.set(indexToReplace, (Movie) editToApply.objToEdit);
        editToApply.isApproved = true;
        Admin.editSuggestions.remove(editToApply);
        System.out.println("Edits Applied Successfully!");
    }

    public double getRating(){
        return this.rating;
    }
    public Genre getGenre() {
        return genre;
    }

    public ArrayList<ActorRole> getCast() {
        return cast;
    }

    public void updateRating(int rate){
        //this.ratingAmount is getting updated too ;)
        this.rating = (double)(this.rating * this.ratingAmount + rate) / ++this.ratingAmount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPlotSummary(String plotSummary) {
        this.plotSummary = plotSummary;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setMedia(ArrayList<Media> media) {
        this.media = media;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public void setWriters(ArrayList<Writer> writers) {
        this.writers = writers;
    }

    public void setCast(ArrayList<ActorRole> cast) {
        this.cast = cast;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }
}
