import java.util.Scanner;

public class Report {
    String reportText;
    Object objReported;
    ReportEnum type;
    boolean isApproved;
    boolean isRejected;

    static Scanner scanner = new Scanner(System.in);
    public Report(Object objReported, String reportText, ReportEnum type) {
        this.reportText = reportText;
        this.objReported = objReported;
        this.type = type;

        this.isApproved = false;
        this.isRejected = false;
    }


    static public void viewReports(){
        if (Admin.getReports().isEmpty()) {
            System.out.println("There is no report!");
            return;
        }
        int counter = 0;
        for (Report report : Admin.getReports()){
            if (!report.isRejected){
                System.out.println(++counter + ". " + report.reportText + "\nType: " + report.type.name() + "\n---------------------------");
            }
        }
        System.out.print("Choose one: ");
        int option = Integer.parseInt(scanner.nextLine()) - 1;
        if (option < 0 || option >= Admin.getReports().size()){
            System.out.println("Invalid Option!");
        }
        else {
            Report reported = Admin.getReports().get(option);
            if (reported.type == ReportEnum.REVIEW) ((Review) reported.objReported).printReview();
            if (reported.type == ReportEnum.POST)  ((Post) reported.objReported).viewPost(false);
            if (reported.type == ReportEnum.REVIEW) ((Comment) reported.objReported).viewComment();
            boolean flag = true;
            while (flag) {
                if (reported.type == ReportEnum.REVIEW) {           //maybe implement other enums
                    System.out.print("What do you wanna do with this report? 1.Approve   2.Reject   3.Modify it's Review : ");
                    int option2 = Integer.parseInt(scanner.nextLine());
                    switch (option2) {
                        case 1 -> {
                            reported.isApproved = true;
                            flag = false;
                        }
                        case 2 -> {
                            reported.isRejected = true;
                            flag = false;
                        }
                        case 3 -> {
                            System.out.println("Don't forget to Approve or Reject after this to Get Out ...");
                            ((Review) reported.objReported).modifyThisReview();
                        }
                    }
                }
                else if (reported.type == ReportEnum.POST){
                    System.out.print("What do you wanna do with this report? 1.Approve   2.Reject   3.Modify it's Post : ");
                    int option2 = Integer.parseInt(scanner.nextLine());
                    switch (option2) {
                        case 1 -> {
                            reported.isApproved = true;
                            flag = false;
                        }
                        case 2 -> {
                            reported.isRejected = true;
                            flag = false;
                        }
                        case 3 -> {
                            System.out.println("Don't forget to Approve or Reject after this to Get Out ...");
                            ((Post) reported.objReported).modifyThisPost();
                        }
                    }
                }
                else if (reported.type == ReportEnum.COMMENT){
                    System.out.print("What do you wanna do with this report? 1.Approve   2.Reject   3.Modify it's Comment : ");
                    int option2 = Integer.parseInt(scanner.nextLine());
                    switch (option2) {
                        case 1 -> {
                            reported.isApproved = true;
                            flag = false;
                        }
                        case 2 -> {
                            reported.isRejected = true;
                            flag = false;
                        }
                        case 3 -> {
                            System.out.println("Don't forget to Approve or Reject after this to Get Out ...");
                            ((Comment) reported.objReported).modifyThisComment();
                        }
                    }
                }
            }
        }

    }

}
