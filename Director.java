import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Director extends Writer {

    public Director(String firstName, String lastName, Date birthData, SexEnum sex, String nationality, String email, String userName, String passWord, String bio, ArrayList<Media> posters,  ArrayList<Movie> moviesDirected) {
        super(firstName, lastName, birthData, sex, nationality, email, userName, passWord, bio, posters, moviesDirected);
    }

    public Director(Director toCopy) {
        super(toCopy);
    }

    public void view(boolean editMode) {
        System.out.printf("Name: %s\nBirth Date: %s\nGender: %s\nNationality: %s\nBIO: %s\n", firstName + " " + lastName, birthData, this.sex.name() ,nationality, bio);
        if (((Member) PersonDB.personSignedIn).getDirectorFollowing().contains(this)) {
            System.out.println("You Are Following This Director!\n1.Go Back");
            int option = Integer.parseInt(scanner.nextLine());
        }
        else {
            if (PersonDB.personSignedIn instanceof Editor) {
                System.out.print("1.Follow\n2.View Movies From This Director\n3.Write Edit\n4.Go Back\nChoose: ");
                int option = Integer.parseInt(scanner.nextLine());
                if (option > 4 || option < 1) {
                    System.out.println("Invalid Option !");
                } else if (option == 1) {
                    ((Member) PersonDB.personSignedIn).getDirectorFollowing().add(this);
                }else if (option == 2) {
                    this.viewMovies();
                }
                else if (option == 3) {
                    writeEdit();
                } else {
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
                    System.out.print("1.Follow\n2.View Movies From This Director\n3.Go Back\nChoose: ");
                    int option = Integer.parseInt(scanner.nextLine());
                    if (option > 3 || option < 1){
                        System.out.println("Invalid Option !");
                    }
                    else if (option == 1) {
                        ((Member)PersonDB.personSignedIn).getDirectorFollowing().add(this);
                    }
                    else if (option == 2) {
                        this.viewMovies();
                    }
                }
            }
        }
    }



    static public Director makeDirector(Director directorToEdit){
        System.out.print("Enter First Name: ");
        String first_nameTemp = scanner.nextLine();
        String first_name = "";
        if (first_nameTemp.equals("")){
            if (directorToEdit != null)
                first_name = directorToEdit.firstName;

        }
        else {
            first_name = first_nameTemp;
        }

        System.out.print("Enter Last Name: ");
        String last_nameTemp = scanner.nextLine();
        String last_name = "";
        if (last_nameTemp.equals("")){
            if (directorToEdit != null)
                last_name = directorToEdit.lastName;

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
                    if (directorToEdit != null){
                        birth_date = directorToEdit.birthData;
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
                if (directorToEdit != null){
                    gender = directorToEdit.sex;
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
            if (directorToEdit != null)
                nationality = directorToEdit.nationality;
        }
        else {
            nationality = Ntemp;
        }

        System.out.print("Enter BIO: ");
        String bio = "";
        String bioTemp = scanner.nextLine();
        if (bioTemp.equals("")){
            if (directorToEdit != null)
                bio = directorToEdit.bio;
        }
        else {
            bio = bioTemp;
        }

        String email = "";
        String username = "";
        String password = "";
        if (directorToEdit != null){
            email = directorToEdit.email;
            username = directorToEdit.userName;
            password = directorToEdit.passWord;
        }

        //adding media
        System.out.println("How many posters do you wanna add for this director: ");
        ArrayList<Media> posters = new ArrayList<>();
        String tmp = scanner.nextLine();
        if (tmp.equals("")){
            if (directorToEdit != null){
                posters = directorToEdit.posters;
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
        System.out.println("How many movies do you wanna add for this director: ");
        ArrayList<Movie> moviesWrote = new ArrayList<>();
        String tmp1 = scanner.nextLine();
        if (tmp1.equals("")){
            if (directorToEdit != null){
                moviesWrote = directorToEdit.moviesWrote;
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

        if (directorToEdit != null){
            directorToEdit.firstName = first_name;
            directorToEdit.lastName = last_name;
            directorToEdit.birthData = birth_date;
            directorToEdit.sex = gender;
            directorToEdit.nationality = nationality;
            directorToEdit.email = email;
            directorToEdit.userName = username;
            directorToEdit.passWord = password;
            directorToEdit.isBanned = false;
            directorToEdit.bio = bio;
            directorToEdit.posters = posters;
            directorToEdit.moviesWrote = moviesWrote;

            return directorToEdit;
        }
        else {
            Director newDirector = new Director(first_name,last_name, birth_date, gender, nationality, email, username, password, bio, posters, moviesWrote);
            MovieDB.directors.add(newDirector);
            return newDirector;
        }
    }


    public void writeEdit(){
        Director copyDirector = new Director(this);
        copyDirector = makeDirector(copyDirector);
        System.out.print("Enter details about your edit: ");
        String details = scanner.nextLine();
        Edit newEdit = new Edit(copyDirector, this, details);
        Admin.editSuggestions.add(newEdit);
        ((Editor)PersonDB.personSignedIn).edits.add(newEdit);
        System.out.println("Edit sent to admins successfully!");
    }

    static public void applyEdit(Edit editToApply){
        int indexToReplace = MovieDB.directors.indexOf((Director) editToApply.originalObj);
        MovieDB.directors.set(indexToReplace, (Director) editToApply.objToEdit);
        editToApply.isApproved = true;
        Admin.editSuggestions.remove(editToApply);
        System.out.println("Edits Applied Successfully!");
    }
}


