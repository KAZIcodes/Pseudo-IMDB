import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MovieDB {
    public static ArrayList<Movie> movies = new ArrayList<>();
    public static ArrayList<Genre> genres = new ArrayList<>();
    public static ArrayList<Actor> actors = new ArrayList<>();
    public static ArrayList<Director> directors = new ArrayList<>();
    public static ArrayList<Writer> writers = new ArrayList<>();

    static Scanner scanner = new Scanner(System.in);



    static public void testMvoieDirectorCast(){
        ArrayList<Writer> writers = new ArrayList<>();
        ArrayList<ActorRole> cast = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date Rdate = null;
        try {
            Rdate = dateFormat.parse("2004-11-04"); // Parse the string input to a Date object
        } catch (ParseException e) {
            System.out.println("Exception happened in Firste Movie's Date");
        }
        Director firstD = new Director("Christopher", "Nolan", Rdate, SexEnum.MALE, "American", "NOLAN@gmail.com", "", "", "TOO BADASS", null, null);
        MovieDB.directors.add(firstD);
        writers.add((Writer)firstD);
        Actor leo = new Actor("Leonardo", "Dicaprio", Rdate, SexEnum.MALE, "American", "leo@gmail.com", "", "", "Bache khoshgel", null, null);
        ActorRole leoRole = new ActorRole(leo, "Mamad");
        Actor jennifer = new Actor("Jennifer", "Lawrence", Rdate, SexEnum.FEMALE, "American", "JL@gmail.com", "", "", "Blonde KIndda", null, null);
        ActorRole JRole = new ActorRole(jennifer, "red girl");
        Actor toby = new Actor("Tobey", "Maguiere", Rdate, SexEnum.MALE, "American", "tobyboy@gmail.com", "", "", "SPIDER MANNNNN", null, null);
        ActorRole tobyRole = new ActorRole(toby, "Peter Parker");
        Actor morgot = new Actor("Morgot", "Robbie", Rdate, SexEnum.FEMALE, "American", "barbie@gmail.com", "", "", "Stared in Barbie", null, null);
        ActorRole Mrole = new ActorRole(morgot, "Barbie");
        actors.add(jennifer);actors.add(toby);actors.add(leo);actors.add(morgot);
        cast.add(Mrole);cast.add(tobyRole);cast.add(JRole);cast.add(leoRole);
        Movie firstMovie = new Movie("Batman begins", "Batman starts being batman...", MovieDB.genres.get(1), null, Rdate, "English", firstD, writers, cast);
        movies.add(firstMovie);

    }



    static public void sortRating(){
        movies.sort(Comparator.comparingDouble(Movie::getRating));
    }
    static public void printChart(){
        sortRating();
        int counter = 0;
        for (Movie movie: movies) {
            System.out.printf("%d.%s : %.1f\n", ++counter, movie.getTitle(), movie.getRating());
            if (counter == 250) //Top 250 IMDB :)
                break;
        }

        System.out.println(++counter + ". Go back");
        System.out.print("Choose an option: ");
        int option = Integer.parseInt(scanner.nextLine());
        if (counter == option)
            return;
        else {
            option--;
            if (option >= 0 && option < counter) {
                movies.get(option).view(false);
            } else {
                System.out.println("Invalid Option !");
            }
        }
    }
    static public ArrayList<Object> search(String searchQuery){


        ArrayList<Object> searchObjects = new ArrayList<>();
        searchObjects.addAll(MovieDB.actors);
        searchObjects.addAll(MovieDB.directors);
        searchObjects.addAll(MovieDB.writers);
        searchObjects.addAll(MovieDB.movies);

        ArrayList<Object> results = new ArrayList<>();

        for (Object obj : searchObjects) {
            String name = getName(obj);
            if (name != null && name.toLowerCase().contains(searchQuery.toLowerCase())) {
                results.add(obj);
            }
        }

        if (results.isEmpty())
            System.out.println("Nothing Found for your Search!");

        return results;
    }

    static public ArrayList<Movie> searchMovie(String searchQuery){

        ArrayList<Movie> results = new ArrayList<>();

        for (Movie movie : movies) {
            String name = movie.getTitle();
            if (name != null && name.toLowerCase().contains(searchQuery.toLowerCase())) {
                results.add(movie);
            }
        }

        return results;
    }

    static public void removeReview(Review searchRev){
        boolean flag = false;
        for (Movie movie : movies) {
            if (flag) break;

            for (Review review : movie.getReviews()){
                if (searchRev.equals(review)){
                    movie.getReviews().remove(searchRev);
                    flag = true;
                    break;
                }
            }
        }
    }

    static private String getName(Object obj) {
        if (obj instanceof Actor) {
            return ((Actor) obj).getName();
        } else if (obj instanceof Writer) {
            return ((Writer) obj).getName();
        } else if (obj instanceof Movie) {
            return ((Movie) obj).getTitle();
        } else if (obj instanceof Director) {
            return ((Director) obj).getName();
        }
        return null;
    }

    static public void printSearch(ArrayList<Object> results){
        int counter = 1;

        for (Object result : results) {
            if (result instanceof Movie) {
                System.out.println(counter + ". Movie: " + ((Movie) result).getTitle());
            }
            else if (result instanceof Actor) {
                System.out.println(counter + ". Actor: " + ((Actor) result).getName());
            }
            else if (result instanceof Director) {
                System.out.println(counter + ". Director: " + ((Director) result).getName());
            }
            else if (result instanceof Writer) {
                System.out.println(counter + ". Writer: " + ((Writer) result).getName());
            }
            counter++;
        }
    }


    static void addEditMovie(){
        System.out.println("What do you wanna do?\n1.Add Movie   2.Edit Movie   3.Delete Movie");
        int option = 10;
        while (option != 2 && option != 1 && option != 3) {
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1 -> {
                    addMovie(null);
                    System.out.println("DONE");
                }
                case 2 -> {
                    System.out.print("Enter the name of the movie: ");
                    String movieName = scanner.nextLine();
                    ArrayList<Object> results = search(movieName);
                    if (!results.isEmpty()){
                        printSearch(results);
                        System.out.print("Which one do you want to edit ? ");
                        int o = Integer.parseInt(scanner.nextLine()) - 1;
                        if (o >= 0 && o < results.size()) {
                            Movie searchedMovie = (Movie) results.get(o);
                            addMovie(searchedMovie);
                            System.out.println("DONE!");
                        } else {
                            System.out.println("Invalid Option !");
                        }
                    }
                }
                case 3 -> {
                    System.out.print("Enter the name of the movie: ");
                    String movieName = scanner.nextLine();
                    ArrayList<Object> results = search(movieName);
                    if (!results.isEmpty()){
                        printSearch(results);
                        System.out.print("Which one do you want to edit ? ");
                        int o = Integer.parseInt(scanner.nextLine()) - 1;
                        if (o >= 0 && o < results.size()) {
                            Movie searchedMovie = (Movie) results.get(o);
                            movies.remove(searchedMovie);
                            System.out.println("DONE!");
                        } else {
                            System.out.println("Invalid Option !");
                        }
                    }
                }
            }
        }
    }


    static public void addMovie(Movie movieToEdit){
        System.out.print("If you want one field to be empty(Unchanged if you are in edit mode) just press Enter\nEnter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Summary: ");
        String summary = scanner.nextLine();
        System.out.print("Enter Genre: ");
        String genreInput = scanner.nextLine();
        Genre resGenre = null;
        for (Genre genre : MovieDB.genres){
            if(genre.getGenre().name().equalsIgnoreCase(genreInput)){
                resGenre = genre;
                break;
            }
        }
        //adding media
        System.out.println("How many media do you wanna add for this movie: ");
        ArrayList<Media> mediaRes = new ArrayList<>();
        String tempN = scanner.nextLine();
        int n = 0;
        if (!tempN.equals(""))
            n = Integer.parseInt(tempN);
        for (int i = 0; i < n; i++){
            System.out.print("Enter media title: ");
            String mediaTitle = scanner.nextLine();
            System.out.print("Enter media details: ");
            String mediaDetail = scanner.nextLine();
            System.out.println("Enter media type(TRAILER, POSTER): ");
            String typeInput = scanner.nextLine();
            MediaType mediaType = null;
            if (typeInput.equalsIgnoreCase("TRAILER")){
                mediaType = MediaType.TRAILER;
            }
            else if (typeInput.equalsIgnoreCase("POSTER")){
                mediaType = MediaType.POSTER;
            }
            System.out.println("Enter path on the server: ");
            String path = scanner.nextLine();
            mediaRes.add(new Media(mediaTitle, mediaDetail, mediaType, path));
        }

        //adding release date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate = null;
        while (true) {
            try {
                System.out.print("Enter your Release Date (format: yyyy-MM-dd): ");
                String dateInput = scanner.nextLine();
                if (dateInput.equals(""))
                    break;
                releaseDate = dateFormat.parse(dateInput); // Parse the string input to a Date object
                break;
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
            }
        }

        System.out.println("Enter language of the movie: ");
        String language = scanner.nextLine();

        //adding director
        Director director = null;
        System.out.println("Do you wanna make a director or set an existing one? 1.Make One  2.Existing One");
        int option = 10;
        while (option != 1 && option != 2){
            String tmp = scanner.nextLine();
            if (tmp.equals(""))
                break;
            option = Integer.parseInt(tmp);
            switch (option){
                case 1 -> {
                    director = Director.makeDirector(null);
                }
                case 2 -> {
                    while (true) {
                        System.out.print("Enter the name of the director: ");
                        String Dname = scanner.nextLine();
                        ArrayList<Object> results = search(Dname);
                        if (!results.isEmpty()) {

                            printSearch(results);
                            System.out.print("Which one do you want? ");
                            int o = Integer.parseInt(scanner.nextLine()) - 1;
                            if (o >= 0 && o < results.size()) {
                                director = (Director) results.get(o);
                                break;
                            } else {
                                System.out.println("Invalid Option;  Search again!");
                            }

                        }
                    }
                }
            }
        }

        //adding writer
        ArrayList<Writer> writers = new ArrayList<>();
        boolean flag = true;
        while (flag){
            System.out.println("Do you wanna make a Writer or set an existing one? 1.Make One  2.Existing One   3.continue(exit loop)");
            String temp = scanner.nextLine();
            if (temp.equals(""))
                break;
            option = Integer.parseInt(temp);
            switch (option){
                case 1 -> {
                    writers.add(Writer.makeWriter(null));
                }
                case 2 -> {
                    while (true) {
                        System.out.print("Enter the name of the writer: ");
                        String Wname = scanner.nextLine();
                        ArrayList<Object> results = search(Wname);
                        if (!results.isEmpty()) {

                            printSearch(results);
                            System.out.print("Which one do you want? ");
                            int o = Integer.parseInt(scanner.nextLine()) - 1;
                            if (o >= 0 && o < results.size()) {
                                writers.add((Writer) results.get(o));
                                break;
                            } else {
                                System.out.println("Invalid Option !");
                            }

                        }
                    }
                }
                case 3 -> {
                    flag = false;
                }
            }
        }


        //adding cast
        ArrayList<ActorRole> actorRoles = new ArrayList<>();
        flag = true;
        while (flag){
            System.out.println("Do you wanna make an Actor or add an existing one? 1.Make One  2.Existing One   3.continue(exit loop)");
            String tmp = scanner.nextLine();
            if (tmp.equals(""))
                break;
            option = Integer.parseInt(tmp);
            switch (option){
                case 1 -> {
                    Actor newActor = Actor.makeActor(null);
                    System.out.print("What is the role of this actor in this movie: ");
                    String role = scanner.nextLine();
                    actorRoles.add(new ActorRole(newActor, role));
                }
                case 2 -> {
                    while (true) {
                        System.out.print("Enter the name of the Actor: ");
                        String Aname = scanner.nextLine();
                        ArrayList<Object> results = search(Aname);
                        if (!results.isEmpty()) {
                            printSearch(results);
                            System.out.print("Which one do you want? ");
                            int o = Integer.parseInt(scanner.nextLine()) - 1;
                            if (o >= 0 && o < results.size()) {
                                System.out.print("What is the role of this actor in this movie: ");
                                String role = scanner.nextLine();
                                actorRoles.add(new ActorRole((Actor) results.get(o), role));
                                break;
                            } else {
                                System.out.println("Invalid Option !");
                            }

                        }
                    }
                }
                case 3 -> {
                    flag = false;
                }
            }
        }


        if (movieToEdit == null){
            Movie newMovie = new Movie(title, summary, resGenre, mediaRes, releaseDate, language, director, writers, actorRoles);
            MovieDB.movies.add(newMovie);
            //adding this new movie to it's director
            if (director != null)
                director.moviesWrote.add(newMovie);
            //adding this new movie to it's writers
            if (!writers.isEmpty()){
                for (Writer writer : writers){
                    writer.moviesWrote.add(newMovie);
                }
            }
            //addin this new movie to it's actors
            if (!actorRoles.isEmpty()){
                for (ActorRole Role : actorRoles){
                    Role.actor.moviesPlayed.add(newMovie);
                }
            }
        }
        else {
            if (!title.equals(""))
                movieToEdit.setTitle(title);
            if (!summary.equals(""))
                movieToEdit.setPlotSummary(summary);
            if (resGenre != null)
                movieToEdit.setGenre(resGenre);
            if (!mediaRes.isEmpty())
                movieToEdit.setMedia(mediaRes);
            if (releaseDate != null)
                movieToEdit.setReleaseDate(releaseDate);
            if (!language.equals(""))
                movieToEdit.setLanguage(language);
            if (director != null) {
                if (movieToEdit.director != null && movieToEdit.director.moviesWrote != null && !movieToEdit.director.moviesWrote.isEmpty())
                    movieToEdit.director.moviesWrote.remove(movieToEdit);
                movieToEdit.setDirector(director);
                if (director.moviesWrote != null)
                    director.moviesWrote.add(movieToEdit);
            }
            if (!writers.isEmpty()) {
                if (!movieToEdit.writers.isEmpty()) {
                    for (Writer writer : movieToEdit.writers) {
                        if (writer.moviesWrote != null && !writer.moviesWrote.isEmpty())
                            writer.moviesWrote.remove(movieToEdit);
                    }
                }
                movieToEdit.setWriters(writers);
                for (Writer writer : writers) {
                    if (writer.moviesWrote != null){
                        writer.moviesWrote.add(movieToEdit);
                    }
                }
            }
            if (!actorRoles.isEmpty()) {
                if (!movieToEdit.cast.isEmpty()) {
                    for (ActorRole role : movieToEdit.cast) {
                        if (role.actor != null && role.actor.moviesPlayed != null && !role.actor.moviesPlayed.isEmpty())
                            role.actor.moviesPlayed.remove(movieToEdit);
                    }
                }
                movieToEdit.setCast(actorRoles);
                for (ActorRole role : actorRoles) {
                    if (role != null && role.actor.moviesPlayed != null){
                        role.actor.moviesPlayed.add(movieToEdit);
                    }
                }
            }
        }
    }


    private static boolean isValidGenre(String input) {
        try {
            GenreEnum.valueOf(input.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
