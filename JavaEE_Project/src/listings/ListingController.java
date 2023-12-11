package listings;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
@SessionScoped
public class ListingController {
    private List<Listing> listings;
    private ListingDbUtil listingDbUtil;
    private Logger logger = Logger.getLogger(getClass().getName());

    public ListingController()throws Exception{
        listings = new ArrayList<>();

        listingDbUtil = ListingDbUtil.getInstance();
    }

    public void loadListings(){     //get all listings
        logger.info("Loading listings");
        listings.clear();

        try{
            listings = listingDbUtil.getListings();      //get listing from db

        }catch(Exception exc){
            logger.log(Level.SEVERE, "Error loading listings", exc);
            addErrorMessage(exc);
        }
    }

    public String loadListing(int listingId){        //get SINGLE listing (for update)
        logger.info("Loading student: " +listingId);

        try{
            Listing theListing = listingDbUtil.getListing(listingId);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> requestMap = externalContext.getRequestMap();
            requestMap.put("listing", theListing);      //put obj in post request?
        }
        catch (Exception exc){
            logger.log(Level.SEVERE, "Error loading listing id:pr" +listingId, exc);
            addErrorMessage(exc);
            return null;
        }
        return "update_listing.xhtml";
    }

    public String addListing(Listing theListing){
        logger.info("Adding listing: " +theListing);

        try{
            //add listing to db
            listingDbUtil.addListing(theListing);
        }
        catch(Exception exc){
            logger.log(Level.SEVERE, "Error adding listing", exc);
            //add error msg at jsf page
            addErrorMessage(exc);
            return null;
        }
        return "user_view_listing?faces-redirect=true";
    }

    public String updateListing(Listing theListing){
        logger.info("Updating listing: "+theListing);

        try{
            listingDbUtil.updateListing(theListing);
        }catch (Exception exc){
            logger.log(Level.SEVERE, "Error updating listing: "+theListing, exc);
            addErrorMessage(exc);
            return null;
        }
        return "user_view_listing?faces-redirect=true";
    }

    public String deleteListing(int listingId){
        logger.info("Deleting listing id: "+listingId);

        try{
            listingDbUtil.deleteListing(listingId);
        }catch(Exception exc){
            logger.log(Level.SEVERE, "Error deleting listing: "+listingId, exc);
            addErrorMessage(exc);
            return null;
        }
        return "user_view_listing";
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

    private void addErrorMessage(Exception exc) {
        FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}