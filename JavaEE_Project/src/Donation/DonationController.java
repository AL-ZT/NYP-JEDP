package Donation;

import database.ProjectDbUtil;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
@SessionScoped
public class DonationController {
    private List<Donation> donations;
    private List<Donators> donators;
    private int donation_id;
    private String donation_name;
    private ProjectDbUtil donationsDbUtil;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String donation_type;
    private String event_image_name;
    private int donation_amt;
    private Donation edit_donation;
    private Donation view_donation;

    public DonationController() throws Exception {
        donations = new ArrayList<>();
        donationsDbUtil = ProjectDbUtil.getInstance();
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public int getDonation_id() {
        return donation_id;
    }

    public void setDonation_id(int donation_id) {
        this.donation_id = donation_id;
    }

    public String getDonation_name() {
        return donation_name;
    }

    public void setDonation_name(String donation_name) {
        this.donation_name = donation_name;
    }

    public String getDonation_type() {
        return donation_type;
    }

    public void setDonation_type(String donation_type) {
        this.donation_type = donation_type;
    }

    public Donation getEdit_donation() {
        return edit_donation;
    }

    public void setEdit_donation(Donation edit_donation) {
        this.edit_donation = edit_donation;
    }

    public Donation getView_donation() {
        return view_donation;
    }

    public void setView_donation(Donation view_donation) {
        this.view_donation = view_donation;
    }

    public List<Donators> getDonators() {
        return donators;
    }

    public void setDonators(List<Donators> donators) {
        this.donators = donators;
    }

    public int getDonation_amt() {
        return donation_amt;
    }

    public void setDonation_amt(int donation_amt) {
        this.donation_amt = donation_amt;
    }

    public String getEvent_image_name() {
        return event_image_name;
    }

    public void setEvent_image_name(String event_image_name) {
        this.event_image_name = event_image_name;
    }

    public void addErrorMessage(Exception exc) {
        FacesMessage message = new FacesMessage("Error : " + exc.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void loadDonations() {
        try {
            if (donation_type != null && donation_type.trim().length() > 0) {
                donations = donationsDbUtil.getDonationsWithType(donation_type);
            } else {
                donations = donationsDbUtil.getAllDonations();
            }
        } catch (Exception exc) {
            logger.log(Level.SEVERE, "Error retrieving all the donations");
            addErrorMessage(exc);
        } finally {
            donation_type = null;
        }
    }

    public String deleteDonationEvent(int id) throws Exception {
        try {
            donationsDbUtil.deleteDonationEvent(id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting", e);
            addErrorMessage(e);
        }
        return "donation?faces-redirect=true";
    }

    public String loadEvent(int event_id) {
        logger.info("Loading event : " + event_id);

        try {
            edit_donation = donationsDbUtil.getDonation(event_id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error loading event : " + event_id);
            addErrorMessage(e);
            return null;
        }

        return "donation_edit.xhtml?faces-redirect=true";
    }

    public String sortEvents() {
        return "donation?faces-redirect=true";
    }

    public String updateEvent(Donation obj) throws ValidatorException {

        if (obj.getDate_start().after(obj.getDate_end())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("End date cannot be before start date!"));
            return null;
        }

        try {
            if (obj.getEvent_image().getFileName().length() > 0) {
                copyFile(obj.getEvent_image().getFileName(), obj.getEvent_image().getInputstream(), obj);
            } else {
                obj.setFilename("default.jpg");
            }
        } catch (Exception e) {
            System.out.println("IO Exception" + e);
            return null;
        }

        try {
            donationsDbUtil.updateEvent(obj);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating event", e);
            addErrorMessage(e);
            return null;
        }
        return "donation?faces-redirect=true";
    }

    public String addDonationEvent(Donation donation_object) throws ValidatorException {

        if (donation_object.getDate_start().after(donation_object.getDate_end())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("End date cannot be before start date!"));
            return null;
        }

        try {
            if (donation_object.getEvent_image().getFileName().length() > 0) {
                copyFile(donation_object.getEvent_image().getFileName(), donation_object.getEvent_image().getInputstream(), donation_object);
            } else {
                donation_object.setFilename("default.jpg");
            }
        } catch (Exception e) {
            System.out.println("IO Exception" + e);
            return null;
        }


        try {
            donationsDbUtil.addDonationEvent(donation_object);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error inserting into DB", e);
            addErrorMessage(e);
        }

        return "donation?faces-redirect=true";
    }

    public String viewDonationEvent(int id) throws Exception {
        try {
            view_donation = donationsDbUtil.getDonation(id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving from DB", e);
            addErrorMessage(e);
        }

        return "donation_event?faces-redirect=true";
    }

    public void loadDonatorList(int event_id) throws Exception {
        try {
            donators = donationsDbUtil.getDonators(event_id);
            view_donation = donationsDbUtil.getDonation(event_id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving from DB", e);
            addErrorMessage(e);
        }
    }

    public void addDonationAmount(String first_name, String last_name) throws Exception {
        try {
            int event_id = view_donation.getId();
            int donation_amount = donation_amt;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            Date date_donated = format.parse(format.format(new Date()));
            java.sql.Date sqlDate = new java.sql.Date(date_donated.getTime());
            System.out.println(event_id);
            System.out.println(first_name);
            System.out.println(last_name);
            System.out.println(donation_amount);
            System.out.println(sqlDate);
            donationsDbUtil.addDonationTransaction(event_id, first_name, last_name, donation_amount, sqlDate);
            donationsDbUtil.addDonationMoney(event_id, donation_amount);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error inserting to DB", e);
            addErrorMessage(e);
        }
    }

    //Uploading File
    public void copyFile(String fileName, InputStream in, Donation donation_object) {
        try {

            // write the inputStream to a FileOutputStream
            String username = System.getProperty("user.name");
            String newfilename = generateRandomHex() + fileName;
            if (!newfilename.contains(".jpg")) {
                String[] temp = newfilename.split("\\.");
                String filename = temp[0];
                newfilename = filename + ".jpg";
            }
            donation_object.setFilename(newfilename);
            OutputStream out = new FileOutputStream(new File("C:\\Users\\Chow Jia Jun\\IdeaProjects\\JavaEE_Project\\out\\artifacts\\JavaEE_Project_war_exploded\\resources\\images\\" + newfilename));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String generateRandomHex() {
        Random random = new Random();
        String new_string = "";

        for (int i = 0; i < 8; i++) {
            int val = random.nextInt();
            String hex = new String();
            hex = Integer.toHexString(val);
            new_string = new_string += hex;
        }

        return new_string;
    }
}