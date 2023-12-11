package database;

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
public class UserController {

    private List<User> Users;
    private ProjectDbUtil projectDbUtil;
    private Logger logger = Logger.getLogger(getClass().getName());

    public UserController() throws Exception {
        Users = new ArrayList<>();

        projectDbUtil = ProjectDbUtil.getInstance();
    }

    public List<User> getUsers() {
        return Users;
    }

    public void loadUsers() {

        logger.info("Loading Users");

        Users.clear();

        try {

            // get all students from database
            Users = projectDbUtil.getUsers();

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error loading Users", exc);

            // add error message for JSF page
            addErrorMessage(exc);
        }
    }

    public String loadUser(int userId) {

        logger.info("loading student: " + userId);

        try {
            // get user from database
            User theStudent = projectDbUtil.getUser(userId);

            // put in the request attribute ... so we can use it on the form page
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

            Map<String, Object> requestMap = externalContext.getRequestMap();
            requestMap.put("student", theStudent);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error loading user id:pr" + userId, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "update-student-form.xhtml";
    }

    public String deleteUser(int userId) {

        logger.info("Deleting student id: " + userId);

        try {

            // delete the student from the database
            projectDbUtil.deleteUser(userId);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error deleting student id: " + userId, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "list-students";
    }

    public String updateUser(User theUser) {

        logger.info("updating student: " + theUser);

        try {

            // update student in the database
            projectDbUtil.updateUser(theUser);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error updating student: " + theUser, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "index?faces-redirect=true";
    }

    private void addErrorMessage(Exception exc) {
        FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
