package Donation;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Date;

@ManagedBean
@SessionScoped
public class Donators {

    private int transaction_id;
    private int event_id;
    private String name;
    private int donation_amount;
    private Date date_donated;

    public Donators() {
    }

    public Donators(int transaction_id, int event_id, String name, int donation_amount, Date date_donated) {
        this.transaction_id = transaction_id;
        this.event_id = event_id;
        this.name = name;
        this.donation_amount = donation_amount;
        this.date_donated = date_donated;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDonation_amount() {
        return donation_amount;
    }

    public void setDonation_amount(int donation_amount) {
        this.donation_amount = donation_amount;
    }

    public Date getDate_donated() {
        return date_donated;
    }

    public void setDate_donated(Date date_donated) {
        this.date_donated = date_donated;
    }
}
