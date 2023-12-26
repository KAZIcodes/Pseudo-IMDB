import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Forum {
    static ArrayList<Post> posts = new ArrayList<Post>();

    static Scanner scanner = new Scanner(System.in);


    static public void viewForum(){
        while (true){
            System.out.println("1.Write A Post\n2.View Top 10 Popular Posts\n3.View All Posts\n4.View My Posts\n5.Go Back...");
            int option = 10;
            while(true) {
                try {
                    option = Integer.parseInt(scanner.nextLine());
                    break;
                }
                catch(NumberFormatException e) {
                    System.out.println("Enter a Number!");
                }
            }
            if (option == 5){
                System.out.println("Going Back...");
                break;
            }
            else if (option > 5 || option < 1){
                System.out.println("Invalid Option!");
            }
            else {
                switch (option){
                    case 1 -> {
                        writeNewPost(null);
                    }
                    case 2 -> {
                        viewTopPosts();
                    }
                    case 3 -> {
                        viewAllPosts();
                    }
                    case 4 -> {
                        viewMemberPosts();
                    }
                }
            }
        }
    }

    static public void writeNewPost(Post toEdit){
        System.out.println("What is post title: ");
        String title = scanner.nextLine();
        System.out.println("Write post text: ");
        String text = scanner.nextLine();

        int option = 10;
        boolean spoiler = false;
        String inp = "";
        while (option != 2 && option != 1) {
            System.out.println("Is spoiler? 1.Yes  2.No ");
            inp = scanner.nextLine();
            option = Integer.parseInt(inp);
            if (option == 1)
                spoiler = true;
        }

        if (toEdit != null){
            if (!title.equals("")){
                toEdit.title = title;
            }
            if (!text.equals("")){
                toEdit.text = text;
            }
            if (!inp.equals("")){
                toEdit.isSpoiler = spoiler;
            }
        }
        else {
            Post post = new Post(title, text, (Member) PersonDB.personSignedIn, spoiler);
            posts.add(post);
            ((Member) PersonDB.personSignedIn).posts.add(post);
        }

    }


    static public void viewTopPosts(){
        if (posts.isEmpty()) {
            System.out.println("There is no post!");
            return;
        }

        posts.sort(Comparator.comparingLong(Post::getLikes)); //firsting sorting

        ArrayList<Post> topTen = new ArrayList<>();
        int j = 1;
        for (int i = posts.size()-1; i > -1; i--, j++){
            System.out.println(j + ". " + posts.get(i).title);
            topTen.add(posts.get(i));
        }
        System.out.println(j + ".Go Back...");
        int option = 10;
        while(true) {
            try {
                option = Integer.parseInt(scanner.nextLine());
                break;
            }
            catch(NumberFormatException e) {
                System.out.println("Enter a Number!");
            }
        }
        if (option == j){
            System.out.println("Going Back...");
        }
        else if (option > j || option < 1){
            System.out.println("Invalid Option!");
        }
        else {
            topTen.get(option-1).viewPost(false);
        }
    }

    static public void viewAllPosts(){
        if (posts.isEmpty()) {
            System.out.println("There is no post!");
            return;
        }

        int counter = 0;
        for (Post post : posts){
            System.out.println(++counter + ". " + post.title);
        }
        System.out.println(++counter + ".Go Back...");
        int option = 10;
        while(true) {
            try {
                option = Integer.parseInt(scanner.nextLine());
                break;
            }
            catch(NumberFormatException e) {
                System.out.println("Enter a Number!");
            }
        }
        if (option == counter){
            System.out.println("Going Back...");
        }
        else if (option > counter || option < 1){
            System.out.println("Invalid Option!");
        }
        else {
            posts.get(option-1).viewPost(false);
        }
    }

    static private void viewMemberPosts(){
        int counter = 0;
        for (Post post : ((Member) PersonDB.personSignedIn).posts){
            System.out.println(++counter + ". " + post.title);
        }
        System.out.println(++counter + ".Go Back...");
        int option = 10;
        while(true) {
            try {
                option = Integer.parseInt(scanner.nextLine());
                break;
            }
            catch(NumberFormatException e) {
                System.out.println("Enter a Number!");
            }
        }
        if (option == counter){
            System.out.println("Going Back...");
        }
        else if (option > counter || option < 1){
            System.out.println("Invalid Option!");
        }
        else {
            ((Member) PersonDB.personSignedIn).posts.get(option-1).viewPost(false);
        }
    }
}
