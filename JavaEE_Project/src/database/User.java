package database;

import org.primefaces.event.SelectEvent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.text.SimpleDateFormat;

@ManagedBean
@SessionScoped
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String dateOfBirth;
    private String gender;
    private int phoneNumber;
    private String profilePic;
    private Boolean adminStatus = false;
    private String errorMessage = null;
    private Boolean newRequestAlert;
    private Boolean requestChangesAlert;
    private int completedDeliveries;

    private ProjectDbUtil projectDbUtil;

    public User() throws Exception {
        projectDbUtil = ProjectDbUtil.getInstance();
    }

    public User(int id, String firstName, String lastName, String email, String password, String dateOfBirth, String gender, int phoneNumber, String profilePic, Boolean adminStatus, Boolean newRequestAlert, Boolean requestChangesAlert, int completedDeliveries) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.profilePic = profilePic;
        this.adminStatus = adminStatus;
        this.newRequestAlert = newRequestAlert;
        this.requestChangesAlert = requestChangesAlert;
        this.completedDeliveries = completedDeliveries;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", profilePic='" + profilePic + '\'' +
                ", adminStatus=" + adminStatus +
                ", newRequestAlert=" + newRequestAlert +
                ", requestChangesAlert=" + requestChangesAlert +
                ", completedDeliveries=" + completedDeliveries +
                '}';
    }

    public int getCompletedDeliveries() {
        return completedDeliveries;
    }

    public void setCompletedDeliveries(int completedDeliveries) {
        this.completedDeliveries = completedDeliveries;
    }

    public Boolean getNewRequestAlert() {
        return newRequestAlert;
    }

    public void setNewRequestAlert(Boolean newRequestAlert) {
        this.newRequestAlert = newRequestAlert;
    }

    public Boolean getRequestChangesAlert() {
        return requestChangesAlert;
    }

    public void setRequestChangesAlert(Boolean requestChangesAlert) {
        this.requestChangesAlert = requestChangesAlert;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ProjectDbUtil getProjectDbUtil() {
        return projectDbUtil;
    }

    public void setProjectDbUtil(ProjectDbUtil projectDbUtil) {
        this.projectDbUtil = projectDbUtil;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(Boolean adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String registerUser(User theUser) {
        try {
            System.out.println(theUser);
            projectDbUtil.addUser(theUser);
        } catch (Exception exc) {
            return "delivery?faces-redirect=true";
        }

        return "index?faces-redirect=true";
    }

    public String authenticate(User object) throws Exception {
        String user_input_email = object.getEmail();
        String user_input_password = object.getPassword();
        try {
            projectDbUtil.loginUser(object);
            if (user_input_email.equals(object.getEmail()) && user_input_password.equals(object.getPassword())) {
                this.setErrorMessage(null);
                return "index?faces-redirect=true";
            } else {
                System.out.println("Not Correct Credentials");
                object.setErrorMessage("Wrong email or password");
                object.setId(0);
                object.setFirstName(null);
                object.setLastName(null);
                object.setPassword(null);
                object.setDateOfBirth(null);
                object.setGender(null);
                object.setPhoneNumber(0);
                object.setProfilePic(null);
                object.setAdminStatus(false);
                object.setEmail(null);
                object.setNewRequestAlert(null);
                object.setRequestChangesAlert(null);
                object.setCompletedDeliveries(-1);
                return "login?faces-redirect=true";
            }
        } catch (Exception exc) {
            System.out.println("Exception");
            object.setErrorMessage("Wrong email or password");
            return "login?faces-redirect=true";
        }
    }

    public String logoutUser() {

        this.id = -1;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.password = null;
        this.dateOfBirth = null;
        this.gender = null;
        this.phoneNumber = 0;
        this.profilePic = null;
        this.adminStatus = null;
        this.newRequestAlert = null;
        this.requestChangesAlert = null;
        this.completedDeliveries = -1;

        return "index?faces-redirect=true";
    }

    private void addErrorMessage(Exception exc) {
        FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

}