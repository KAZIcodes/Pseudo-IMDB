import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Member extends Person {

    ArrayList<Member> followings = new ArrayList<Member>();
    ArrayList<Actor> actorFollowing = new ArrayList<>();
    ArrayList<Writer> writerFollowing = new ArrayList<>();
    ArrayList<Director> directorFollowing = new ArrayList<>();
    ArrayList<Review> reviews = new ArrayList<Review>();
    ArrayList<CustomList> customLists = new ArrayList<>();
    ArrayList<Report> reports = new ArrayList<>();
    ArrayList<Post> posts = new ArrayList<>();

    Scanner scanner = new Scanner(System.in);

    public Member(String firstName, String lastName, Date birthData, SexEnum sex, String nationality, String email, String userName, String passWord) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthData = birthData;
        this.sex = sex;
        this.nationality = nationality;
        this.email = email;
        this.userName = userName;
        this.passWord = passWord;
        this.isBanned = false;
    }

    public void view() {
        System.out.println("Username: " + this.userName + "\n" + "Name: " + this.getName() + "\n" + "Nationality: " + this.nationality + "\n" + "Gender: " + this.sex + "-----------------------------------");

        int option = 10;
        while (option != 9) {
            if (PersonDB.personSignedIn instanceof Admin){
                System.out.print("\n1.View Followings\n2.View Following Actors\n3.View Following Directors\n4.View Following Writers\n5.View Member's Custom Lists\n6.Follow This Member\n7.Ban This Member\n8.Delete This Member\n9.Go Back\nChoose an option: ");
                option = Integer.parseInt(scanner.nextLine());
                if (option > 9 || option < 1) {
                    System.out.println("Invalid Option !");
                    continue;
                }
            } else {
                System.out.print("\n1.View Followings\n2.View Following Actors\n3.View Following Directors\n4.View Following Writers\n5.View Member's Custom Lists\n6.Follow This Member\n7.Go Back\nChoose an option: ");
                option = Integer.parseInt(scanner.nextLine());
                if (option == 7){
                    System.out.println("Redirecting...");
                    break;
                }
                if (option > 7 || option < 1) {
                    System.out.println("Invalid Option !");
                    continue;
                }
            }

            switch (option) {
                case 1 -> {
                    viewFollowings();
                }
                case 2 -> {
                    viewFollowingActors();
                }
                case 3 -> {
                    viewFollowingDirectors();
                }
                case 4 -> {
                    viewFollowingsWriters();
                }
                case 5 -> {
                    viewMemberLists();
                }
                case 6 -> {
                    if (((Member)PersonDB.personSignedIn).getFollowings().contains(this))
                        System.out.println("You are already following this member!");
                    else {
                        ((Member) PersonDB.personSignedIn).getFollowings().add(this);
                        System.out.println("Add to your followings successfully !");
                    }
                }
                case 7 -> {
                    this.isBanned = true;
                }
                case 8 -> {
                    PersonDB.persons.remove(this);
                }
                case 9 -> {
                    System.out.println("Redirecting to home page ...");
                }
            }
        }
    }


    public void createCustomList(){
        System.out.print("Enter your new list name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your details for your new list: ");
        String detail = scanner.nextLine();
        CustomList list = new CustomList(name, detail);

        while (true) {
            System.out.print("Which movie do you want to add ?(if you wanna exit searching type 'exit') Enter title: ");
            String searchQuery = scanner.nextLine();
            if (searchQuery.equalsIgnoreCase("exit"))
                break;

            ArrayList<Movie> results = MovieDB.searchMovie(searchQuery);
            int counter = 1;
            for (Movie movie : results) {
                System.out.println(counter++ + ". " + movie.getTitle());
            }
            if (counter > 1){
                System.out.print("Choose one to add: ");
                int option = Integer.parseInt(scanner.nextLine());
                if (option < counter-1 && option > 0) {
                    list.movies.add(results.get(option - 1));
                    System.out.print("Added successfully!");
                }
            }
            else
                System.out.println("Nothing Found!");
        }
        ((Member)PersonDB.personSignedIn).getCustomLists().add(list);
    }

    public void printMemberLists(){
        if (customLists.isEmpty()) {System.out.println("Lists are empty!"); return;}

        int counter = 1;
        for (CustomList list : customLists){
            System.out.println(counter++ + ". " + list.getName() + "\n" + "Details: " + list.getDetail() + "\n---------------------");
        }
        System.out.println(counter + ". Go Back...");
    }

    public void viewMemberLists(){
        if (customLists.isEmpty()) {System.out.println("No Custom List!"); return;}

        int counter = 1;
        for (CustomList list : customLists){
            System.out.println(counter++ + ". " + list.getName() + "\n" + "Details: " + list.getDetail() + "\n---------------------");
        }
        System.out.println(counter + ". Go back");
        System.out.println("Choose an option: ");
        int option = Integer.parseInt(scanner.nextLine());
        if (counter == option)
            return;
        else {
            option--;
            if (option >= 0 && option < customLists.size()) {
                customLists.get(option).view();
            } else {
                System.out.println("Invalid Option !");
            }
        }
    }
    public void viewFollowings(){
        if (followings.isEmpty()) {System.out.println("No Following!"); return;}

        int counter = 1;
        for (Member member: followings){
            System.out.println(counter++ + ". " + member.getName());
        }
        System.out.println(counter + ". Go back");
        System.out.println("Choose an option: ");
        int option = Integer.parseInt(scanner.nextLine());
        if (counter == option)
            return;
        else {
            option--;
            if (option >= 0 && option < followings.size()) {
                followings.get(option).view();
            } else {
                System.out.println("Invalid Option !");
            }
        }

    }

    private void viewFollowingActors(){
        if (actorFollowing.isEmpty()) {System.out.println("No Following!"); return;}

        int counter = 1;
        for (Actor actor: actorFollowing){
            System.out.println(counter++ + ". " + actor.getName());
        }
        System.out.println(counter + ". Go back");
        System.out.println("Choose an option: ");
        int option = Integer.parseInt(scanner.nextLine());
        if (counter == option)
            return;
        else {
            option--;
            if (option >= 0 && option < actorFollowing.size()) {
                actorFollowing.get(option).view(false);
            } else {
                System.out.println("Invalid Option !");
            }
        }
    }

    private void viewFollowingDirectors(){
        if (directorFollowing.isEmpty()) {System.out.println("No Following!"); return;}

        int counter = 1;
        for (Director director: directorFollowing){
            System.out.println(counter++ + ". " + director.getName());
        }
        System.out.println(counter + ". Go back");
        System.out.println("Choose an option: ");
        int option = Integer.parseInt(scanner.nextLine());
        if (counter == option)
            return;
        else {
            option--;
            if (option >= 0 && option < directorFollowing.size()) {
                directorFollowing.get(option).view(false);
            } else {
                System.out.println("Invalid Option !");
            }
        }
    }

    private void viewFollowingsWriters(){
        if (writerFollowing.isEmpty()) {System.out.println("No Following!"); return;}

        int counter = 1;
        for (Writer writer: writerFollowing){
            System.out.println(counter++ + ". " + writer.getName());
        }
        System.out.println(counter + ". Go back");
        System.out.println("Choose an option: ");
        int option = Integer.parseInt(scanner.nextLine());
        if (counter == option)
            return;
        else {
            option--;
            if (option >= 0 && option < writerFollowing.size()) {
                writerFollowing.get(option).view(false);
            } else {
                System.out.println("Invalid Option !");
            }
        }
    }


    public void updateProfile(){
        System.out.print("Enter your  new info for the parts you wanna change and for the ones that you don't want to change just leave it empty...\n-----------------------------------------------------------");
        System.out.print("Enter your First Name: ");
        String first_name = scanner.nextLine();
        if (!first_name.equals(""))
            this.firstName = first_name;
        System.out.print("Enter your Last Name: ");
        String last_name = scanner.nextLine();
        if (!last_name.equals(""))
            this.lastName = last_name;


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birth_date;
        while (true) {
            try {
                System.out.print("Enter your Birth Date (format: yyyy-MM-dd): ");
                String dateInput = scanner.nextLine();
                if (!dateInput.equals("")) {
                    birth_date = dateFormat.parse(dateInput); // Parse the string input to a Date object
                    this.birthData = birth_date;
                    break;
                }
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
            }
        }

        int genderO = 10;
        SexEnum gender = null;
        while (genderO > 4 || genderO < 1) {
            System.out.print("1.Male\n2.Female\n3.Others\n4.Don't change\nChoose your Gender: ");
            genderO = Integer.parseInt(scanner.nextLine());
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
                case 4: {
                    break;
                }
            }
        }

        System.out.print("Enter your Nationality: ");
        String nationality = scanner.nextLine();
        if (!nationality.equals(""))
            this.nationality = nationality;
        System.out.print("Enter your Email: ");
        String email = scanner.nextLine();
        if (!email.equals(""))
            this.email = email;
        System.out.print("Enter a Username: ");
        String username = scanner.nextLine();
        if (!username.equals(""))
            this.userName = username;
        System.out.print("Enter a Password: ");
        String password = scanner.nextLine();
        if (!password.equals(""))
            this.passWord = password;
    }



    public void recomEngine(){
        if (MovieDB.genres.isEmpty() || MovieDB.movies.isEmpty() || this.getCustomLists().isEmpty() || this.getDirectorFollowing().isEmpty() || this.getActorFollowing().isEmpty()) {
            System.out.println("Not Enough Data for Recom Engine To Work!!!");
            return;
        }

        ArrayList<Movie> recoms = new ArrayList<Movie>();
        //based on top ratings
        int count = 0;
        for (Movie movie : MovieDB.movies){
            if (count++ == 3)                 //adding e movies based on rating
                break;
            if(movie.getRating() > 8.5)
                recoms.add(movie);
        }

        //based on genre
        for (CustomList list : this.customLists){
            if(!list.movies.isEmpty()){
                for (Movie movie : list.movies){
                    GenreEnum temp_genre = movie.getGenre().getGenre();
                    for (Genre genre : MovieDB.genres){
                        if (genre.genre == temp_genre){
                            genre.temp_count++;
                        }
                    }
                }
            }
        }
        //finding the favorite genre this member watches
        int max = 0;
        Genre res = null;
        for (Genre genre : MovieDB.genres) {
            if (genre.temp_count > max) {
                res = genre;
            }
            genre.temp_count = 0; // back to 0 for next time this function is called
        }
        //recomin from max genre
        count = 0;
        for (Movie movie : res.getMovies()) {
            if (count++ == 3)                  //adds 3 movis for each genre
                break;
            recoms.add(movie);
        }

        //based on director/actor follow
        count = 0;
        for (Director director : this.directorFollowing){
            for (Movie movie : MovieDB.movies){
                if (movie.director == director){               //adds 3 movis for each director
                    if (count++ == 3)
                        break;
                    recoms.add(movie);
                }
            }
            count = 0;
        }

        for (Actor actor : this.actorFollowing){
            for (Movie movie : MovieDB.movies){
                for (ActorRole actorRole : movie.getCast()){
                    if (actorRole.getActor() == actor){
                        if (count++ == 2)                   //add two movies for each actor
                            break;
                        recoms.add(movie);
                    }
                }
            }
            count = 0;
        }


        //printing recoms for the member
        for (Movie movie : recoms){
            System.out.println(++count + ". " + movie.getTitle());
        }
        System.out.println(++count + "Go Back");
        System.out.println("Choose one to view: ");
        int option = Integer.parseInt(scanner.nextLine());
        if (option < count-1 && option >= 0){
            recoms.get(option).view(false);
        }
    }

    public void viewReportStatus(){
        if (this.reports.isEmpty()){
            System.out.println("You have no reports!");
            return;
        }

        int counter = 1;
        for (Report report : this.reports){
            if (report.isApproved){
                System.out.println(counter++ + ". " + report.reportText + "\nStatus: Approved");
            }
            else if (report.isRejected){
                System.out.println(counter++ + ". " + report.reportText + "\nStatus: Rejected");
            }
            else
                System.out.println(counter++ + ". " + report.reportText + "\nStatus: Not yet seen by an Admin!");
        }
    }

    public void viewActorDirectorWriter(){
        System.out.println("Which following list do you wanna view? ");
        System.out.print("1.Actor     2.Director     3.Writer     4.Go Back\nChoose an option: ");
        int option = Integer.parseInt(scanner.nextLine());
        switch (option) {
            case 1 -> {
                this.viewFollowingActors();
            }
            case 2 -> {
                this.viewFollowingDirectors();
            }
            case 3 -> {
                this.viewFollowingsWriters();
            }
            case 4 -> {
                System.out.println("Redirecting ...");
            }
        }
    }

    public ArrayList<Member> getFollowings() {
        return followings;
    }

    public ArrayList<Actor> getActorFollowing() {
        return actorFollowing;
    }

    public ArrayList<Writer> getWriterFollowing() {
        return writerFollowing;
    }

    public ArrayList<Director> getDirectorFollowing() {
        return directorFollowing;
    }

    public ArrayList<CustomList> getCustomLists() {
        return customLists;
    }
    public ArrayList<Review> getReviews() {
        return reviews;
    }
}
