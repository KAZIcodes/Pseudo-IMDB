import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.SimpleTimeZone;

public class Actor extends Person{
    String bio;
    ArrayList<Media> posters = new ArrayList<Media>();
    ArrayList<Movie> moviesPlayed = new ArrayList<Movie>();

    static Scanner scanner = new Scanner(System.in);




    public Actor(String firstName, String lastName, Date birthData, SexEnum sex, String nationality, String email, String userName, String passWord, String bio, ArrayList<Media> posters, ArrayList<Movie> moviesPlayed) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthData = birthData;
        this.sex = sex;
        this.nationality = nationality;
        this.email = email;
        this.userName = userName;
        this.passWord = passWord;
        this.isBanned = false;
        this.bio = bio;
        if (posters != null)
            this.posters = posters;
        if (moviesPlayed != null)
            this.moviesPlayed = moviesPlayed;
    }
    public Actor(Actor toCopy) {
        this.firstName = toCopy.firstName;
        this.lastName = toCopy.lastName;
        this.birthData = toCopy.birthData;
        this.sex = toCopy.sex;
        this.nationality = toCopy.nationality;
        this.email = toCopy.email;
        this.userName = toCopy.userName;
        this.passWord = toCopy.passWord;
        this.isBanned = toCopy.isBanned;
        this.bio = toCopy.bio;
        this.posters = toCopy.posters;
        this.moviesPlayed = toCopy.moviesPlayed;
    }

    public void view(boolean editMode){
        System.out.printf("Name: %s\nBirth Date: %s\nSex: %s\nNationality: %s\nBIO: %s\n", firstName + " " + lastName, birthData, this.sex.name(),nationality, bio);
        if (((Member)PersonDB.personSignedIn).getActorFollowing().contains(this)){
            System.out.println("You Are Following This Actor!\n1.Go Back");
            int option = Integer.parseInt(scanner.nextLine());
        }
        else {
            if (PersonDB.personSignedIn instanceof Editor){
                System.out.print("1.Follow\n2.View Movies Played\n3.Write Edit\n4.Go Back\nChoose: ");
                int option = Integer.parseInt(scanner.nextLine());
                if (option > 4 || option < 1){
                    System.out.println("Invalid Option !");
                }
                else if (option == 1) {
                    ((Member)PersonDB.personSignedIn).getActorFollowing().add(this);
                }
                else if (option == 2){
                    this.viewMovies();
                }
                else if (option == 3) {
                    writeEdit();
                }
                else {
                    System.out.println("Redirecting...");
                }
            }
            else {
                if (editMode){
                    System.out.print("1.Go Back\nChoose: ");
                    int option = Integer.parseInt(scanner.nextLine());
                    if (option == 1){
                        System.out.println("Redirecting!");
                    }
                    else {
                        System.out.println("Invalid Option !");
                    }
                }
                else {
                    System.out.print("1.Follow\n2.View Movies Played\n3.Go Back\nChoose: ");
                    int option = Integer.parseInt(scanner.nextLine());
                    if (option > 3 || option < 1){
                        System.out.println("Invalid Option !");
                    }
                    else if (option == 1) {
                        ((Member)PersonDB.personSignedIn).getActorFollowing().add(this);
                    }
                    else if (option == 2){
                        this.viewMovies();
                    }
                    else System.out.println("Going Back...!");
                }
            }
        }
    }


    public void viewMovies(){
        if ( this.moviesPlayed == null || this.moviesPlayed.isEmpty()){
            System.out.println("This Actor hasn't played in any movie!");
            return;
        }

        int counter = 0;
        for(Movie movie : this.moviesPlayed){
            System.out.println(++counter + ". " + movie.getTitle());
        }
        System.out.print(++counter + ".Go Back...\nChoose One: ");
        while(true) {
            try {
                int option = Integer.parseInt(scanner.nextLine());
                if (option == counter){
                    System.out.println("Going Back...");
                    break;
                }
                else if (option > 0 && option < counter) {
                    this.moviesPlayed.get(option-1).view(false);
                    break;
                } else System.out.println("Invalid option!");
            } catch (NumberFormatException e) {
                System.out.println("Enter a number!");
            }
        }
    }

    static public Actor makeActor(Actor actorToEdit){
        System.out.print("Enter First Name: ");
        String first_nameTemp = scanner.nextLine();
        String first_name = "";
        if (first_nameTemp.equals("")){
            if (actorToEdit != null)
                first_name = actorToEdit.firstName;

        }
        else {
            first_name = first_nameTemp;
        }

        System.out.print("Enter Last Name: ");
        String last_nameTemp = scanner.nextLine();
        String last_name = "";
        if (last_nameTemp.equals("")){
            if (actorToEdit != null)
                last_name = actorToEdit.lastName;

        }
        else {
            last_name = last_nameTemp;
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birth_date = null;
        while (true) {
            try {
                System.out.print("Enter your Birth Date (format: yyyy-MM-dd): ");
                String dateInput = scanner.nextLine();
                if (dateInput.equals("")){
                    if (actorToEdit != null){
                        birth_date = actorToEdit.birthData;
                        break;
                    }
                    break;
                }
                birth_date = dateFormat.parse(dateInput); // Parse the string input to a Date object
                break;
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
            }
        }

        int genderO = 10;
        SexEnum gender = null;
        while (genderO > 3 || genderO < 1) {
            System.out.print("1.Male\n2.Female\n3.Others\nChoose your Gender: ");
            String inp = scanner.nextLine();
            if (inp.equals("")){
                if (actorToEdit != null){
                    gender = actorToEdit.sex;
                    break;
                }
                break;
            }
            genderO = Integer.parseInt(inp);
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
            }
        }

        System.out.print("Enter Nationality: ");
        String nationality = "";
        String Ntemp = scanner.nextLine();
        if (Ntemp.equals("")){
            if (actorToEdit != null)
                nationality = actorToEdit.nationality;
        }
        else {
            nationality = Ntemp;
        }
        System.out.print("Enter BIO: ");
        String bio = "";
        String bioTemp = scanner.nextLine();
        if (bioTemp.equals("")){
            if (actorToEdit != null)
                bio = actorToEdit.bio;
        }
        else {
            bio = bioTemp;
        }

        String email = "";
        String username = "";
        String password = "";
        if (actorToEdit != null){
            email = actorToEdit.email;
            username = actorToEdit.userName;
            password = actorToEdit.passWord;
        }

        //adding media
        System.out.println("How many posters do you wanna add for this actor: ");
        ArrayList<Media> posters = new ArrayList<>();
        String tmp = scanner.nextLine();
        if (tmp.equals("")){
            if (actorToEdit != null){
                posters = actorToEdit.posters;
            }
            tmp = "0";
        }
        int n = Integer.parseInt(tmp);
        for (int i = 0; i < n; i++){
            System.out.print("Enter media title: ");
            String mediaTitle = scanner.nextLine();
            System.out.print("Enter media details: ");
            String mediaDetail = scanner.nextLine();
            MediaType mediaType = MediaType.POSTER;
            System.out.println("Enter path on the server: ");
            String path = scanner.nextLine();
            posters.add(new Media(mediaTitle, mediaDetail, mediaType, path));
        }

        //adding movies
        System.out.println("How many movies do you wanna add for this actor: ");
        ArrayList<Movie> moviesPlayed = new ArrayList<>();
        String tmp1 = scanner.nextLine();
        if (tmp1.equals("")){
            if (actorToEdit != null){
                moviesPlayed = actorToEdit.moviesPlayed;
            }
            tmp1 = "0";
        }
        n = Integer.parseInt(tmp1);
        for (int i = 0; i < n; i++){
            System.out.print("Enter Movie title: ");
            String movieTitle = scanner.nextLine();
            ArrayList<Object> res = MovieDB.search(movieTitle);
            MovieDB.printSearch(res);
            System.out.print("Choose One: ");
            int option = Integer.parseInt(scanner.nextLine());
            if (option > 0 && option <= res.size()){
                moviesPlayed.add((Movie) res.get(option-1));
            }
            else
            {
                System.out.println("Invalid option; Search Again!");
                i--;
            }
        }

        if (actorToEdit != null){
            actorToEdit.firstName = first_name;
            actorToEdit.lastName = last_name;
            actorToEdit.birthData = birth_date;
            actorToEdit.sex = gender;
            actorToEdit.nationality = nationality;
            actorToEdit.email = email;
            actorToEdit.userName = username;
            actorToEdit.passWord = password;
            actorToEdit.isBanned = false;
            actorToEdit.bio = bio;
            actorToEdit.posters = posters;
            actorToEdit.moviesPlayed = moviesPlayed;

            return actorToEdit;
        }
        else {
            Actor newActor = new Actor(first_name,last_name, birth_date, gender, nationality, email, username, password, bio, posters, moviesPlayed);
            MovieDB.actors.add(newActor);
            return newActor;
        }
    }


    public void writeEdit(){
        Actor copyActor = new Actor(this);
        makeActor(copyActor);
        System.out.print("Enter details about your edit: ");
        String details = scanner.nextLine();
        Edit newEdit = new Edit(copyActor, this, details);
        Admin.editSuggestions.add(newEdit);
        ((Editor)PersonDB.personSignedIn).edits.add(newEdit);
        System.out.println("Edit sent to admins successfully!");
    }

    static public void applyEdit(Edit editToApply){
        int indexToReplace = MovieDB.actors.indexOf((Actor) editToApply.originalObj);
        MovieDB.actors.set(indexToReplace, (Actor) editToApply.objToEdit);
        editToApply.isApproved = true;
        Admin.editSuggestions.remove(editToApply);
        System.out.println("Edits Applied Successfully!");
    }
}
