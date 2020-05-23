package sample;

import javafx.scene.control.Hyperlink;

import java.time.LocalDate;

public class Conference {
    private String conference_name;
    private String subject;
    private LocalDate submission_date;
    private LocalDate notification_date;
    private LocalDate confirmation_date;

    private LocalDate start_date;
    private LocalDate end_date;
    private String country;
    private String city;
    private Hyperlink conference_Url;

    public Conference() {
        this.conference_name = "";
        this.subject = "";
        this.start_date = null;
        this.end_date = null;
        this.country = "";
        this.city = "";
        this.submission_date = null;
        this.notification_date = null;
        this.confirmation_date = null;
        this.conference_Url = null;
    }
    public Conference(String conference_name, String subject,LocalDate start_date,LocalDate end_date,String country,String city, LocalDate submission_date, LocalDate notification_date, LocalDate confirmation_date) {
        this.conference_name = conference_name;
        this.subject = subject;
        this.start_date = start_date;
        this.end_date = end_date;
        this.country = country;
        this.city = city;
        this. submission_date =  submission_date;
        this. notification_date =  notification_date;
        this.confirmation_date = confirmation_date;

    }

    public Conference(String conference_name, String subject,LocalDate start_date,LocalDate end_date,String country,String city, LocalDate submission_date, LocalDate notification_date, LocalDate confirmation_date, String conference_link) {
        this.conference_name = conference_name;
        this.subject = subject;
        this.start_date = start_date;
        this.end_date = end_date;
        this.country = country;
        this.city = city;
        this. submission_date =  submission_date;
        this. notification_date =  notification_date;
        this.confirmation_date = confirmation_date;
        this.conference_Url = new Hyperlink(conference_link);
    }

    public Hyperlink getConference_Url() {
        return conference_Url;
    }

    public void setConference_Url(String conference_link) {
        this.conference_Url = new Hyperlink(conference_link);
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {

        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {

        this.country = country;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public String getConference_name() {
        return conference_name;
    }

    public void setConference_name(String conference_name) {
        this.conference_name = conference_name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getSubmission_date() {
        return submission_date;
    }

    public void setSubmission_date(LocalDate submission_date) {
        this.submission_date = submission_date;
    }

    public LocalDate getNotification_date() {
        return notification_date;
    }

    public void setNotification_date(LocalDate notification_date) {
        this.notification_date = notification_date;
    }

    public LocalDate getConfirmation_date() {
        return confirmation_date;
    }

    public void setConfirmation_date(LocalDate confirmation_date) {
        this.confirmation_date = confirmation_date;
    }
}
