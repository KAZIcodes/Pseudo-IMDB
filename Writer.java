import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Writer extends Person {
    String bio;
    ArrayList<Media> posters = new ArrayList<Media>();
    static Scanner scanner = new Scanner(System.in);
    ArrayList<Movie> moviesWrote = new ArrayList<Movie>();



    public Writer(String firstName, String lastName, Date birthData, SexEnum sex, String nationality, String email, String userName, String passWord, String bio, ArrayList<Media> posters, ArrayList<Movie> moviesWrote) {
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
        if (moviesWrote != null)
            this.moviesWrote = moviesWrote;
    }

    public Writer(Writer toCopy) {
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
        this.moviesWrote = toCopy.moviesWrote;
    }
    
    public void view(boolean editMode){
        System.out.printf("Name: %s\nBirth Date: %s\nGender: %s\nNationality: %s\nBIO: %s\n", firstName + " " + lastName, birthData, this.sex.name(), nationality, bio);
        if (((Member)PersonDB.personSignedIn).getWriterFollowing().contains(this)){
            System.out.println("You Are Following This Writer!\n1.Go Back");
            int option = Integer.parseInt(scanner.nextLine());
        }
        else {
            if (PersonDB.personSignedIn instanceof Editor){
                System.out.print("1.Follow\n2.View movies form this Writer\n3.Write Edit\n4.Go Back\nChoose: ");
                int option = Integer.parseInt(scanner.nextLine());
                if (option > 4 || option < 1){
                    System.out.println("Invalid Option !");
                }
                else if (option == 1) {
                    ((Member)PersonDB.personSignedIn).getWriterFollowing().add(this);
                }
                else if (option == 2) {
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
                    System.out.print("1.Follow\n2.View movies from this Wirter\n3.Go Back\nChoose: ");
                    int option = Integer.parseInt(scanner.nextLine());
                    if (option > 3 || option < 1){
                        System.out.println("Invalid Option !");
                    }
                    else if (option == 1) {
                        ((Member)PersonDB.personSignedIn).getWriterFollowing().add(this);
                    }
                    else if (option == 2){
                        this.viewMovies();
                    }
                }
            }
        }
    }

    public void viewMovies(){
        if (this.moviesWrote == null || this.moviesWrote.isEmpty()){
            System.out.println("This Writer/Director has no movie!");
            return;
        }

        int counter = 0;
        for(Movie movie : this.moviesWrote){
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
                    this.moviesWrote.get(option-1).view(false);
                    break;
                } else System.out.println("Invalid option!");
            } catch (NumberFormatException e) {
                System.out.println("Enter a number!");
            }
        }
    }


    static public Writer makeWriter(Writer writerToEdit){
        System.out.print("Enter First Name: ");
        String first_nameTemp = scanner.nextLine();
        String first_name = "";
        if (first_nameTemp.equals("")){
            if (writerToEdit != null)
                first_name = writerToEdit.firstName;

        }
        else {
            first_name = first_nameTemp;
        }

        System.out.print("Enter Last Name: ");
        String last_nameTemp = scanner.nextLine();
        String last_name = "";
        if (last_nameTemp.equals("")){
            if (writerToEdit != null)
                last_name = writerToEdit.lastName;

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
                    if (writerToEdit != null){
                        birth_date = writerToEdit.birthData;
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
                if (writerToEdit != null){
                    gender = writerToEdit.sex;
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
            if (writerToEdit != null)
                nationality = writerToEdit.nationality;
        }
        else {
            nationality = Ntemp;
        }

        System.out.print("Enter BIO: ");
        String bio = "";
        String bioTemp = scanner.nextLine();
        if (bioTemp.equals("")){
            if (writerToEdit != null)
                bio = writerToEdit.bio;
        }
        else {
            bio = bioTemp;
        }

        String email = "";
        String username = "";
        String password = "";
        if (writerToEdit != null){
            email = writerToEdit.email;
            username = writerToEdit.userName;
            password = writerToEdit.passWord;
        }

        //adding media
        System.out.println("How many posters do you wanna add for this writer: ");
        ArrayList<Media> posters = new ArrayList<>();
        String tmp = scanner.nextLine();
        if (tmp.equals("")){
            if (writerToEdit != null){
                posters = writerToEdit.posters;
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
        System.out.println("How many movies do you wanna add for this writer: ");
        ArrayList<Movie> moviesWrote = new ArrayList<>();
        String tmp1 = scanner.nextLine();
        if (tmp1.equals("")){
            if (writerToEdit != null){
                moviesWrote = writerToEdit.moviesWrote;
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
                moviesWrote.add((Movie) res.get(option-1));
            }
            else
            {
                System.out.println("Invalid option; Search Again!");
                i--;
            }
        }


        if (writerToEdit != null){
            writerToEdit.firstName = first_name;
            writerToEdit.lastName = last_name;
            writerToEdit.birthData = birth_date;
            writerToEdit.sex = gender;
            writerToEdit.nationality = nationality;
            writerToEdit.email = email;
            writerToEdit.userName = username;
            writerToEdit.passWord = password;
            writerToEdit.isBanned = false;
            writerToEdit.bio = bio;
            writerToEdit.posters = posters;
            writerToEdit.moviesWrote = moviesWrote;

            return writerToEdit;
        }
        else {
            Writer newWriter = new Writer(first_name,last_name, birth_date, gender, nationality, email, username, password, bio, posters, moviesWrote);
            MovieDB.writers.add(newWriter);
            return newWriter;
        }
    }


    public void writeEdit(){
        Writer copyWriter = new Writer(this);
        copyWriter = makeWriter(copyWriter);
        System.out.print("Enter details about your edit: ");
        String details = scanner.nextLine();
        Edit newEdit = new Edit(copyWriter, this, details);
        Admin.editSuggestions.add(newEdit);
        ((Editor)PersonDB.personSignedIn).edits.add(newEdit);
        System.out.println("Edit sent to admins successfully!");
    }

    static public void applyEdit(Edit editToApply){
        int indexToReplace = MovieDB.writers.indexOf((Writer) editToApply.originalObj);
        MovieDB.writers.set(indexToReplace, (Writer) editToApply.objToEdit);
        editToApply.isApproved = true;
        Admin.editSuggestions.remove(editToApply);
        System.out.println("Edits Applied Successfully!");
    }
}
