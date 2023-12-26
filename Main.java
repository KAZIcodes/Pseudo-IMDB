import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static boolean quit = false;
    public static void main(String[] args) {

        //these function provide samples for testing the program :
        PersonDB.defaultAdminEditor(); // for creating default admin/editor
        Genre.createGenres(); //initialize Genres for app to work
        MovieDB.testMvoieDirectorCast(); // test Movie

        while (!quit) {

            //Person is not Signed in
            if (PersonDB.personSignedIn == null) {
                boolean isSignedIn = false;
                int option = 10;
                while (option != 5) {
                    if (isSignedIn) {
                        break;
                    }
                    System.out.print("\n1.Sign Up\n2.Sign In\n3.Search Movies/Actor/Director/Writer\n4.Show Highest Rated Chart\n5.exit\nChoose an option: ");
                    String input = scanner.nextLine();
                    try {
                        option = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Enter a number!");
                    }

                    if (option > 5 || option < 1) {
                        System.out.println("Invalid Option !");
                        continue;
                    }
                    switch (option) {
                        case 1 -> {
                            UserManagement.signUp("M");
                            System.out.println("User Signed Up Successfully !");
                        }
                        case 2 -> {
                            UserManagement.signIn();
                            isSignedIn = true;
                        }
                        case 3 -> {
                            movieDBSearch();
                        }
                        case 4 -> {
                            MovieDB.printChart();
                        }
                        case 5 -> {
                            quit = true;
                            System.out.println("Bye ;)");
                        }
                    }
                }
            } else if (PersonDB.personSignedIn instanceof Member) {
                boolean isSignedIn = true;
                int option = 18;
                while (true) {
                    if (!isSignedIn) {
                        PersonDB.personSignedIn = null;
                        break;
                    }
                    System.out.print("\n1.Search Movie/Actor/Director/Writer\n2.Search Members\n3.View My Custom Lists\n4.Create Custom List\n5.View My Following Actor/Director/Writer\n6.View My Followings\n7.Update Profile\n8.Show Highest Rated Chart\n9.See Movie Recommendations\n10.View Report Status\n11.Enter Forum\n");
                    if (!(PersonDB.personSignedIn instanceof Admin)){
                        if (PersonDB.personSignedIn instanceof Editor){
                            System.out.print("12.View Edits Status\n13.Sign Out\nChoose an option: ");
                            String input = scanner.nextLine();
                            try {
                                option = Integer.parseInt(input);
                            } catch (NumberFormatException e) {
                                System.out.println("Enter a number!");
                            }

                            if (option == 13) {
                                System.out.println("Redirecting to main page...");
                                isSignedIn = false;
                                continue;
                            }
                            else if (option == 12){
                                ((Editor) PersonDB.personSignedIn).viewEditsStatus();
                                continue;
                            }
                            else if (option > 13 || option < 1) {
                                System.out.println("Invalid Option !");
                                continue;
                            }
                        }
                        else {
                            System.out.print("12.Sign Out\nChoose an option: ");
                            String input = scanner.nextLine();
                            try {
                                option = Integer.parseInt(input);
                            } catch (NumberFormatException e) {
                                System.out.println("Enter a number!");
                            }

                            if (option == 12) {
                                System.out.println("Redirecting to main page...");
                                isSignedIn = false;
                                continue;
                            }
                            else if (option > 12 || option < 1) {
                                System.out.println("Invalid Option !");
                                continue;
                            }
                        }
                    }
                    else {
                        System.out.print("12.Add/Edit Movie(Make Actor/Writer/Director included)\n13.User Management For Admins\n14.Modify/Delete Review\n15.View Reports\n16.View Edit Suggestions\n17.Create New Admin\n18.Create New Editor\n19.Sign Out\nChoose an option: ");
                        String input = scanner.nextLine();
                        try {
                            option = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.out.println("Enter a number!");
                        }

                        if (option == 19) {
                            System.out.println("Redirecting to main page...");
                            isSignedIn = false;
                            continue;
                        }
                        else if (option > 19 || option < 1) {
                            System.out.println("Invalid Option !");
                            continue;
                        }
                    }


                    switch (option) {
                        case 1 -> {
                            movieDBSearch();
                        }
                        case 2 -> {
                            personDBSearch();
                        }
                        case 3 -> {
                            ((Member)PersonDB.personSignedIn).viewMemberLists();
                        }
                        case 4 -> {
                            ((Member)PersonDB.personSignedIn).createCustomList();
                        }
                        case 5 -> {
                            ((Member)PersonDB.personSignedIn).viewActorDirectorWriter();
                        }
                        case 6 -> {
                            ((Member)PersonDB.personSignedIn).viewFollowings();
                        }
                        case 7 -> {
                            ((Member)PersonDB.personSignedIn).updateProfile();
                        }
                        case 8 -> {
                            MovieDB.printChart();
                        }
                        case 9 -> {
                            ((Member)PersonDB.personSignedIn).recomEngine();
                        }
                        case 10 -> {
                            ((Member)PersonDB.personSignedIn).viewReportStatus();
                        }
                        case 11 -> {
                            Forum.viewForum();
                        }
                        case 12 -> {
                            MovieDB.addEditMovie();
                        }
                        case 13 -> {
                            UserManagement.adminUserManagment();
                        }
                        case 14 -> {
                            Review.modifyReviws();
                        }
                        case 15 -> {
                            Report.viewReports();
                        }
                        case 16 -> {
                            Admin.viewEditSuggestions();
                        }
                        case 17 -> {
                            UserManagement.signUp("A");
                            System.out.println("New admin created succesfully!");
                        }
                        case 18 -> {
                            UserManagement.signUp("E");
                            System.out.println("New editor created succesfully!");
                        }
                    }
                }
            }

        }
    }


    static private void movieDBSearch(){
        System.out.print("Enter your search query: ");
        String searchQuery = scanner.nextLine();
        ArrayList<Object> results = MovieDB.search(searchQuery);
        if (!results.isEmpty()){
            MovieDB.printSearch(results);
            System.out.print("Which one do you want to see ? ");
            int o = Integer.parseInt(scanner.nextLine()) - 1;
            if (o >= 0 && o < results.size()) {
                view(results.get(o));
            } else {
                System.out.println("Invalid Option !");
            }
        }

    }

    static private void personDBSearch(){
        System.out.print("Enter Member name: ");
        String searchQuery = scanner.nextLine();
        ArrayList<Member> results = PersonDB.searchMember(searchQuery);
        if (results.isEmpty()) {System.out.println("Nothing Found!");}
        else {
            PersonDB.printSearch(results);
            System.out.print("Which one do you want to see ? ");
            int o = scanner.nextInt() - 1;
            if (o >= 0 && o < results.size()) {
                results.get(o).view();
            } else {
                System.out.println("Invalid Option !");
            }
        }
    }
    static public void view(Object result){

            if (result instanceof Movie) {
                ((Movie) result).view(false);
            } else if (result instanceof Actor) {
                ((Actor) result).view(false);
            } else if (result instanceof Director) {
                ((Director) result).view(false);
            }
            else if (result instanceof Writer) {
                ((Writer) result).view(false);
            }
    }
}

