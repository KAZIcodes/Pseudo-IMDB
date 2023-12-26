import java.util.ArrayList;

public class Genre {
    GenreEnum genre;
    ArrayList<Movie> movies = new ArrayList<>();


    public ArrayList<Movie> getMovies() {
        return movies;
    }

    int temp_count = 0;
    public Genre(GenreEnum genre) {
        this.genre = genre;
    }

    public GenreEnum getGenre() {
        return genre;
    }

    static public void createGenres(){
        for (GenreEnum Genum : GenreEnum.values()){
            MovieDB.genres.add(new Genre(Genum));
        }
    }
}
