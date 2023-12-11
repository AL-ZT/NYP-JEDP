package Deliveries;

import database.ProjectDbUtil;
import database.User;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.util.Date;

@ManagedBean
@SessionScoped
public class DeliveryScheduleOptions {

    private int id;
    private int delivererId;
    private int recipientId;
    private int deliveryId;
    private long timeStamp;
    private String delivererName;
    private String recipientName;
    private String additionalComments;
    private int deliveryStatus;
    private Date deliveryDate;
    private int daysLeft;
    private Date deliveryTime;
    private Boolean delivererItemConfirmation;
    private Boolean recipientItemConfirmation;
    private ProjectDbUtil projectDbUtil;
    private Alerts alertInstance;

    public DeliveryScheduleOptions() throws Exception {
        projectDbUtil = ProjectDbUtil.getInstance();
        alertInstance = new Alerts();
    }

    public DeliveryScheduleOptions(int id, int deliveryId, int delivererId, int recipientId, long timeStamp, String delivererName, String recipientName, String additionalComments, int deliveryStatus, Boolean delivererItemConfirmation, Boolean recipientItemConfirmation) {
        this.id = id;
        this.delivererId = delivererId;
        this.recipientId = recipientId;
        this.deliveryId = deliveryId;
        this.timeStamp = timeStamp;
        this.delivererName = delivererName;
        this.recipientName = recipientName;
        this.additionalComments = additionalComments;
        this.deliveryStatus = deliveryStatus;
        this.deliveryDate = new Date(timeStamp);
        this.delivererItemConfirmation = delivererItemConfirmation;
        this.recipientItemConfirmation = recipientItemConfirmation;
        this.daysLeft = Days.daysBetween(new LocalDate(System.currentTimeMillis()), new LocalDate(timeStamp)).getDays();
    }

    public String addDeliverySchedule(DeliveryQueries selectedRequest, User selectedUser) throws Exception {
        this.deliveryDate.setHours(this.deliveryTime.getHours());
        this.deliveryDate.setMinutes(this.deliveryTime.getMinutes());
        if (!projectDbUtil.checkExistingRequest(selectedRequest.getId(), selectedUser.getId())) {
            projectDbUtil.addSchedule(selectedRequest, selectedUser, this.deliveryDate.getTime(), this.additionalComments);
            alertInstance.receivedRequestAlert(selectedRequest);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Schedule Added! Check Dashboard for Schedule.", null);
            FacesContext.getCurrentInstance().addMessage(null, message);

            return "delivery.xhtml";
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "You have already scheduled a request for this user!", null);
            FacesContext.getCurrentInstance().addMessage("addDelivery:submitBtn", message);

            return "add_Delivery_Schedule.xhtml";
        }
    }

    public String editDeliverySchedule(DeliveryScheduleOptions delivery) throws Exception {
        delivery.getDeliveryDate().setHours(this.deliveryTime.getHours());
        delivery.getDeliveryDate().setMinutes(this.deliveryTime.getMinutes());
        projectDbUtil.updateSchedule(delivery.getId(), delivery.getDeliveryDate().getTime(), delivery.getAdditionalComments(), delivery.getDeliveryStatus());
        projectDbUtil.resetDeliveryRequest(delivery.getDeliveryId());
        alertInstance.acceptedRequestModifiedAlert(delivery.getRecipientId(), delivery.getDelivererId());

        return "userDashboard.xhtml";
    }

    public void deleteDeliverySchedule(DeliveryScheduleOptions delivery) throws Exception {
        projectDbUtil.deleteSchedule(delivery.getId());
        projectDbUtil.resetDeliveryRequest(delivery.getDeliveryId());
    }

    public void itemConfirmation() {

    }

    public void dateValidation(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if (value == null) {
            return;
        }

        Date selectedDate = ((Date) value);

        Date currentDate = new Date(System.currentTimeMillis());

        if (selectedDate.before(currentDate)) {

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected cannot be before current date!", null);
            FacesContext.getCurrentInstance().addMessage("addDelivery:date", message);

            throw new ValidatorException(message);
        }
    }

    public Boolean getDelivererItemConfirmation() {
        return delivererItemConfirmation;
    }

    public void setDelivererItemConfirmation(Boolean delivererItemConfirmation) {
        this.delivererItemConfirmation = delivererItemConfirmation;
    }

    public Boolean getRecipientItemConfirmation() {
        return recipientItemConfirmation;
    }

    public void setRecipientItemConfirmation(Boolean recipientItemConfirmation) {
        this.recipientItemConfirmation = recipientItemConfirmation;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    public ProjectDbUtil getProjectDbUtil() {
        return projectDbUtil;
    }

    public void setProjectDbUtil(ProjectDbUtil projectDbUtil) {
        this.projectDbUtil = projectDbUtil;
    }

    public int getDelivererId() {
        return delivererId;
    }

    public void setDelivererId(int delivererId) {
        this.delivererId = delivererId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDelivererName() {
        return delivererName;
    }

    public void setDelivererName(String delivererName) {
        this.delivererName = delivererName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }

    public int getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Alerts getAlertInstance() {
        return alertInstance;
    }

    public void setAlertInstance(Alerts alertInstance) {
        this.alertInstance = alertInstance;
    }

    @Override
    public String toString() {
        return "DeliveryScheduleOptions{" +
                "id=" + id +
                ", deliveryId=" + deliveryId +
                ", timeStamp=" + timeStamp +
                ", delivererName='" + delivererName + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", additionalComments='" + additionalComments + '\'' +
                ", deliveryStatus=" + deliveryStatus +
                ", deliveryDate=" + deliveryDate +
                ", deliveryTime=" + deliveryTime +
                '}';
    }
}
