public class Media {
    String title;
    String details;
    MediaType type;

    String pathOnServer;

    public Media(String title, String details, MediaType type, String path) {
        this.title = title;
        this.details = details;
        this.type = type;
        this.pathOnServer = path;
    }
}
