package volunteerPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class VolunteerController {

    private List<Volunteer> volunteers;
    private VolunteerDbUtil volunteerDbUtil;
    private Logger logger = Logger.getLogger(getClass().getName());

    private String theSearchName;
    public VolunteerController() throws Exception {
        volunteers = new ArrayList<>();

        volunteerDbUtil = volunteerDbUtil.getInstance();
    }

    public List<Volunteer> getVolunteers() {
        return volunteers;
    }

    public void loadVolunteers() {

        logger.info("Loading volunteers");

        volunteers.clear();

        try {
            // get all students from database
            if (theSearchName != null && theSearchName.trim().length() > 0) {
                // search for students by name
                volunteers = volunteerDbUtil.searchVolunteers(theSearchName);
            }
            else {
                volunteers = volunteerDbUtil.getVolunteers();
            }

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error loading volunteers", exc);
            // add error message for JSF page
            addErrorMessage(exc);
        }
    }

    public String addVolunteer(Volunteer theVolunteer) {

        logger.info("Adding volunteer: " + theVolunteer);
        try {
            // add student to the database
            volunteerDbUtil.addVolunteer(theVolunteer);
        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error adding volunteer", exc);
            // add error message for JSF page
            addErrorMessage(exc);
            return null;
        }
        return "display_volunteerWork.xhtml";
    }

    public String loadVolunteer(int volunteerId) {

        logger.info("loading volunteer: " + volunteerId);

        try {
            // get student from database
            Volunteer theVolunteer = volunteerDbUtil.getVolunteer(volunteerId);

            // put in the request attribute ... so we can use it on the form page
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

            Map<String, Object> requestMap = externalContext.getRequestMap();
            requestMap.put("volunteer", theVolunteer);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error loading volunteer id:pr" + volunteerId, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "update_volunteerWork.xhtml";
    }

    public String updateVolunteer(Volunteer theVolunteer) {

        logger.info("updating volunteer: " + theVolunteer);

        try {

            // update student in the database
            volunteerDbUtil.updateVolunteer(theVolunteer);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error updating volunteer: " + theVolunteer, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "display_volunteerWork.xhtml";
    }

    public String deleteVolunteer(int volunteerId) {

        logger.info("Deleting volunteer id: " + volunteerId);

        try {

            // delete the student from the database
            volunteerDbUtil.deleteVolunteer(volunteerId);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error deleting volunteer id: " + volunteerId, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "display_volunteerWork.xhtml";
    }

    private void addErrorMessage(Exception exc) {
        FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getTheSearchName() {
        return theSearchName;
    }

    public void setTheSearchName(String theSearchName) {
        this.theSearchName = theSearchName;
    }

}