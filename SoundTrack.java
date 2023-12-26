import java.util.Scanner;

public class SoundTrack {
    String text;
    String title;
    String composer;
    String performer;
    String lyrics;
    String CR;
    boolean editorInappropriateFlag;
    Movie STMovie;
    static Scanner scanner = new Scanner(System.in);

    public SoundTrack(String text, Movie movie, String title ,String composer, String performer, String lyrics, String CR) {
        this.text = text;
        this.STMovie = movie;
        this.title = title;
        this.composer = composer;
        this.lyrics = lyrics;
        this.performer = performer;
        this.CR = CR;
        this.editorInappropriateFlag = false;
    }



    static public SoundTrack writeST(Movie movie, SoundTrack toEdit){
        if (toEdit != null){
            System.out.println("If you want a field to stay unchanged leave it empty!");
        }

        String text = "";
        System.out.println("What is the title? ");
        String title = scanner.nextLine();
        if (title.equals("") && toEdit != null) title = toEdit.title;
        System.out.println("Who is the composer? ");
        String composer = scanner.nextLine();
        if (composer.equals("") && toEdit != null) composer = toEdit.composer;
        System.out.println("Who is the performer? ");
        String performer = scanner.nextLine();
        if (performer.equals("") && toEdit != null) performer = toEdit.performer;
        System.out.println("Lyrics by? ");
        String lyrics = scanner.nextLine();
        if (lyrics.equals("") && toEdit != null) lyrics = toEdit.lyrics;
        System.out.println("What is the Copy Right company? ");
        String CR = scanner.nextLine();
        if (CR.equals("") && toEdit != null) CR = toEdit.CR;
        text += "Title: " + title + "\nComposer: " + composer + "\nPerformed by: " + performer + "\nLyrics by: " + lyrics + "\nCopy Right: " + CR;

        return new SoundTrack(text, movie, title, composer, performer, lyrics, CR);
    }

    static public void viewST(Movie movie){
        int count = 0;
        for (SoundTrack st : movie.soundTracks){
            System.out.println(++count + ". " + st.text + "\n---------------------------------");
        }
        System.out.println(++count + "Go Back...");

        if (PersonDB.personSignedIn instanceof Editor){
            System.out.println("Select one to suggest an edit! ");
            int option = Integer.parseInt(scanner.nextLine());
            System.out.println("What is your edit details? ");
            String details = scanner.nextLine();
            SoundTrack edited = writeST(movie ,movie.soundTracks.get(option-1));
            Admin.editSuggestions.add(new Edit(edited, movie.soundTracks.get(option-1), details));
        }
        else {
            if (PersonDB.personSignedIn instanceof Admin){
                System.out.println("Select one to Modify! ");
                int option = Integer.parseInt(scanner.nextLine());
                SoundTrack edited = writeST(movie ,movie.soundTracks.get(option-1));
                SoundTrack.applyEdit(new Edit(edited, movie.soundTracks.get(option-1), ""));
            }
            else {
                String tmp = scanner.nextLine();
            }
        }
    }

    public void viewThisST(){
        System.out.println(this.text + "\n------------------------------------------------");
    }

    static public void applyEdit(Edit edited){
        int index = ((SoundTrack)edited.originalObj).STMovie.soundTracks.indexOf((SoundTrack) edited.originalObj);
        ((SoundTrack)edited.originalObj).STMovie.soundTracks.set(index, (SoundTrack) edited.objToEdit);
        edited.isApproved = true;
    }

}
