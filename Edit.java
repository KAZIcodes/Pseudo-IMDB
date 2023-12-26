public class Edit {
    Object objToEdit;
    Object originalObj;
    String editDetails;
    boolean isApproved;
    boolean isRejected;

    public Edit(Object objToEdit, Object originalObj, String editDetails) {
        this.objToEdit = objToEdit;
        this.editDetails = editDetails;
        this.originalObj = originalObj;
        this.isApproved = false;
        this.isRejected = false;
    }


    public void viewEdit(){
        if (this.objToEdit instanceof Movie){
            ((Movie) objToEdit).view(true);
        }
        else if (this.objToEdit instanceof Review){
            ((Review) objToEdit).printReview();
        }
        else if (this.objToEdit instanceof Actor) {
            ((Actor) objToEdit).view(true);
        }
        else if (this.objToEdit instanceof Director) {
            ((Director) objToEdit).view(true);
        }
        else if (this.objToEdit instanceof Writer) {
            ((Writer) objToEdit).view(true);
        }
        else if (this.objToEdit instanceof Post) {
            ((Post) objToEdit).viewPost(true);
        }
        else if (this.objToEdit instanceof Trivia) {
            ((Trivia) objToEdit).viewThisTrivia();
        }
        else if (this.objToEdit instanceof Quotes) {
            ((Quotes) objToEdit).viewThisQuote();
        }
        else if (this.objToEdit instanceof SoundTrack) {
            ((SoundTrack) objToEdit).viewThisST();
        }
    }

    public void viewStatus(){
        String status = "Not Yet Seen By An Admin!";
        if (this.isRejected) status = "Rejected!";
        if (this.isApproved) status = "Approved!";

        if (this.objToEdit instanceof Movie){
            System.out.println("Movie edited: " + ((Movie) objToEdit).getTitle() + "\nStatus: " + status);
        }
        else if (this.objToEdit instanceof Review){
            System.out.println("Text of review edited: " + ((Review) objToEdit).reviewText + "\nStatus: " + status);
        }
        else if (this.objToEdit instanceof Actor) {
            System.out.println("Actor edited: " + ((Actor) objToEdit).getName() + "\nStatus: " + status);
        }
        else if (this.objToEdit instanceof Director) {
            System.out.println("Director edited: " + ((Director) objToEdit).getName() + "\nStatus: " + status);
        }
        else if (this.objToEdit instanceof Writer) {
            System.out.println("Writer edited: " + ((Writer) objToEdit).getName() + "\nStatus: " + status);
        }
        else if (this.objToEdit instanceof Post) {
            System.out.println("Post title edited: " + ((Post) objToEdit).title + "\nStatus: " + status);
        }
    }
}
