package Deliveries;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import database.ProjectDbUtil;
import database.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Alerts {

    private String accountSID = "ACf752021e1d086cbbd54e342846a508c0";
    private String authToken = "0bd91b35492032d87722cbdf2ceb958c";
    private User alertedUser;
    private DeliveryScheduleOptions alertedDeliverySchedule;
    private DeliveryQueries alertedDeliveryQuery;
    private ProjectDbUtil projectDbUtil;
    private String customMsg;

    public Alerts() throws Exception {
        projectDbUtil = ProjectDbUtil.getInstance();
    }

    public DeliveryQueries getAlertedDeliveryQuery() {
        return alertedDeliveryQuery;
    }

    public void setAlertedDeliveryQuery(DeliveryQueries alertedDeliveryQuery) {
        this.alertedDeliveryQuery = alertedDeliveryQuery;
    }

    public String getAccountSID() {
        return accountSID;
    }

    public void setAccountSID(String accountSID) {
        this.accountSID = accountSID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public User getAlertedUser() {
        return alertedUser;
    }

    public void setAlertedUser(User alertedUser) {
        this.alertedUser = alertedUser;
    }

    public DeliveryScheduleOptions getAlertedDeliverySchedule() {
        return alertedDeliverySchedule;
    }

    public void setAlertedDeliverySchedule(DeliveryScheduleOptions alertedDeliverySchedule) {
        this.alertedDeliverySchedule = alertedDeliverySchedule;
    }

    public String getCustomMsg() {
        return customMsg;
    }

    public void setCustomMsg(String customMsg) {
        this.customMsg = customMsg;
    }

    public void receivedRequestAlert(DeliveryQueries selectedRequest) throws Exception {
        Twilio.init(accountSID, authToken);
        User tempUser = projectDbUtil.getUser(selectedRequest.getUser_id());
        if (tempUser.getPhoneNumber() != 0) {
            if (tempUser.getNewRequestAlert()) {
                String phoneNum = "+65" + tempUser.getPhoneNumber();
                Message message = Message.creator(new PhoneNumber(phoneNum), new PhoneNumber("+14439799177"),"You have just received a delivery request for this product : " + selectedRequest.getItemName()).create();
                System.out.println(message.getSid());
            }
        }
    }

    public void acceptedRequestModifiedAlert(int recipientId, int delivererId) throws Exception {
        Twilio.init(accountSID, authToken);
        User tempUser = projectDbUtil.getUser(recipientId);
        User secondTempUser = projectDbUtil.getUser(delivererId);
        if (tempUser.getPhoneNumber() != 0) {
            if (tempUser.getRequestChangesAlert()) {
                String phoneNum = "+65" + tempUser.getPhoneNumber();
                Message message = Message.creator(new PhoneNumber(phoneNum), new PhoneNumber("+14439799177"), secondTempUser.getFirstName() + " " + secondTempUser.getLastName() + " modified a delivery request for a product").create();
                System.out.println(message.getSid());
            }
        }
    }

    public void presetOTW(User sender) {
        Twilio.init(accountSID, authToken);
        String phoneNum = "+65" + this.alertedUser.getPhoneNumber();
        Message message = Message.creator(new PhoneNumber(phoneNum), new PhoneNumber("+14439799177"), "From " + sender.getFirstName() + " " + sender.getLastName() + " : I am now on my way to your house with your product").create();
        System.out.println(message.getSid());
    }

    public void presetLate(User sender) {
        Twilio.init(accountSID, authToken);
        String phoneNum = "+65" + this.alertedUser.getPhoneNumber();
        Message message = Message.creator(new PhoneNumber(phoneNum), new PhoneNumber("+14439799177"), "From " + sender.getFirstName() + " " + sender.getLastName() + " : I might run late!").create();
        System.out.println(message.getSid());
    }

    public void presetUnavailable(User sender) {
        Twilio.init(accountSID, authToken);
        String phoneNum = "+65" + this.alertedUser.getPhoneNumber();
        Message message = Message.creator(new PhoneNumber(phoneNum), new PhoneNumber("+14439799177"), "From " + sender.getFirstName() + " " + sender.getLastName() + " : I am currently not around in my house, I'll contact you when I am.").create();
        System.out.println(message.getSid());
    }

    public void presetHurry(User sender) {
        Twilio.init(accountSID, authToken);
        String phoneNum = "+65" + this.alertedUser.getPhoneNumber();
        Message message = Message.creator(new PhoneNumber(phoneNum), new PhoneNumber("+14439799177"), "From " + sender.getFirstName() + " " + sender.getLastName() + " : Please Hurry Up with the Delivery!").create();
        System.out.println(message.getSid());
    }

    public void sendCustomMsg(User sender) {
        Twilio.init(accountSID, authToken);
        String phoneNum = "+65" + this.alertedUser.getPhoneNumber();
        Message message = Message.creator(new PhoneNumber(phoneNum), new PhoneNumber("+14439799177"), "From " + sender.getFirstName() + " " + sender.getLastName() + " : " + customMsg).create();
        System.out.println(message.getSid());
    }

    public void loadUser(DeliveryScheduleOptions delivery, DeliveryQueries deliveryQuery, int userId) throws Exception {
        this.alertedUser = projectDbUtil.loadUserForAlert(userId);
        this.alertedDeliverySchedule = delivery;
        this.alertedDeliveryQuery = deliveryQuery;

        System.out.println(this.alertedDeliveryQuery);
    }

    public String confirmRecipientDeliverySchedule(int scheduleId, int userId) throws Exception {
        projectDbUtil.confirmRecipientDeliverySchedule(scheduleId, userId);
        projectDbUtil.pushScheduleToDeliveredCheck();

        return "userDashboard.xhtml";
    }

    public String confirmDelivererDeliverySchedule(int scheduleId, int userId) throws Exception {
        projectDbUtil.confirmDelivererDeliverySchedule(scheduleId, userId);
        projectDbUtil.pushScheduleToDeliveredCheck();
        projectDbUtil.addCompletedDelivery(userId);

        return "userDashboard.xhtml";
    }
}
