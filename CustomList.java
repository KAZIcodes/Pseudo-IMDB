import java.util.ArrayList;
import java.util.Scanner;

public class CustomList {
    String name;
    String detail;
    ArrayList<Movie> movies = new ArrayList<>();

    Scanner scanner = new Scanner(System.in);

    public CustomList(String name, String detail) {
        this.name = name;
        this.detail = detail;
    }

    public void view(){
        int counter = 1;
        for (Movie movie: movies){
            System.out.println(counter++ + ". " + movie.getTitle());
        }
        System.out.println(counter++ + ". Add this list to my custom lists");
        System.out.println(counter + ". Go back");
        System.out.println("Choose an option: ");
        int option = Integer.parseInt(scanner.nextLine());
        if (counter == option)
            return;
        else if (--counter == option) {
            ((Member) PersonDB.personSignedIn).getCustomLists().add(this);
            System.out.println("Added successfully :)");
        }
        else {
            if (option >= 0 && option < movies.size()) {
                movies.get(option).view(false);
            } else {
                System.out.println("Invalid Option !");
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
