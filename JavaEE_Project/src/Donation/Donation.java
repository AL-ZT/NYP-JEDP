package Donation;

import org.primefaces.model.UploadedFile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.Date;

@ManagedBean
@RequestScoped
public class Donation {

    private int id;
    private String name;
    private String description;
    private String type;
    private int goal;
    private Date date_start;
    private Date date_end;
    private UploadedFile event_image;
    private int noOfDonors;
    private int current_Amount;
    private String filename;

    public Donation() {

    }

    public Donation(int id, String n, String d, String t, int g, Date s, Date e, int no, int current_Amount, String filename) {
        this.id = id;
        this.name = n;
        this.description = d;
        this.type = t;
        this.goal = g;
        this.date_start = s;
        this.date_end = e;
        this.noOfDonors = no;
        this.current_Amount = current_Amount;
        this.filename = filename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }

    public int getNoOfDonors() {
        return noOfDonors;
    }

    public void setNoOfDonors(int noOfDonors) {
        this.noOfDonors = noOfDonors;
    }

    public int getCurrent_Amount() {
        return current_Amount;
    }

    public void setCurrent_Amount(int current_Amount) {
        this.current_Amount = current_Amount;
    }

    public UploadedFile getEvent_image() {
        return event_image;
    }

    public void setEvent_image(UploadedFile event_image) {
        this.event_image = event_image;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}