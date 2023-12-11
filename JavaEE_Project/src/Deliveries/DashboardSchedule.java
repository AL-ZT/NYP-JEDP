package Deliveries;

import database.ProjectDbUtil;
import database.User;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
@ViewScoped
public class DashboardSchedule implements Serializable {
    private ScheduleModel eventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    private Logger logger = Logger.getLogger(getClass().getName());
    private List<DeliveryScheduleOptions> deliverySchedules;
    private List<DeliveryQueries> ReceivedScheduledRequestDetails;
    private List<DeliveryQueries> SentScheduledRequestDetails;
    private DeliveryQueries selectedRequestDetail;
    private ProjectDbUtil projectDbUtil;

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
    }

    public DashboardSchedule() throws Exception {
        deliverySchedules = new ArrayList<>();
        ReceivedScheduledRequestDetails = new ArrayList<>();
        SentScheduledRequestDetails = new ArrayList<>();

        projectDbUtil = ProjectDbUtil.getInstance();
    }

    public void loadDeliverySchedules(User user) {
        logger.info("Loading Schedules");

        deliverySchedules.clear();
        ReceivedScheduledRequestDetails.clear();
        SentScheduledRequestDetails.clear();

        try {
            deliverySchedules = projectDbUtil.getUserSchedules();

            for (int i = 0; i < deliverySchedules.size(); i++) {
                if (deliverySchedules.get(i).getDelivererId() == user.getId()) {
                    this.SentScheduledRequestDetails.add(projectDbUtil.getRequest(deliverySchedules.get(i).getDeliveryId()));
                } else if (deliverySchedules.get(i).getRecipientId() == user.getId()) {
                    this.ReceivedScheduledRequestDetails.add(projectDbUtil.getRequest(deliverySchedules.get(i).getDeliveryId()));
                }
            }

            for (int i = 0; i < deliverySchedules.size(); i++) {
                if (deliverySchedules.get(i).getDeliveryStatus() == 1) {
                    addToEventModel(deliverySchedules.get(i), user);
                }
            }

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error loading Request", exc);

            // add error message for JSF page
            addErrorMessage(exc);
        }
    }

    public DeliveryQueries getSelectedRequestDetail() {
        return selectedRequestDetail;
    }

    public void setSelectedRequestDetail(DeliveryQueries selectedRequestDetail) {
        this.selectedRequestDetail = selectedRequestDetail;
    }

    public List<DeliveryQueries> getReceivedScheduledRequestDetails() {
        return ReceivedScheduledRequestDetails;
    }

    public void setReceivedScheduledRequestDetails(List<DeliveryQueries> receivedScheduledRequestDetails) {
        ReceivedScheduledRequestDetails = receivedScheduledRequestDetails;
    }

    public List<DeliveryQueries> getSentScheduledRequestDetails() {
        return SentScheduledRequestDetails;
    }

    public void setSentScheduledRequestDetails(List<DeliveryQueries> sentScheduledRequestDetails) {
        SentScheduledRequestDetails = sentScheduledRequestDetails;
    }

    public String acceptRequest(DeliveryScheduleOptions schedule) throws Exception {
        projectDbUtil.pushScheduleToAccepted(schedule);
        projectDbUtil.deliveryRequestToAccepted(schedule);

        return "userDashboard?faces-redirect=true";
    }

    public void onclickPreview(DeliveryScheduleOptions schedule, DeliveryQueries request) {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> requestMap = ec.getRequestMap();
        requestMap.put("deliveryScheduleOptions", schedule);

        this.selectedRequestDetail = request;
    }

    public String onclickEdit(DeliveryScheduleOptions schedule) {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> requestMap = ec.getRequestMap();
        requestMap.put("deliveryScheduleOptions", schedule);

        return "edit_Delivery_Schedule.xhtml";
    }

    private void addErrorMessage(Exception exc) {
        FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private void addToEventModel(DeliveryScheduleOptions deliverySchedule, User user) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(deliverySchedule.getTimeStamp());

        Calendar anotherCalendar = (Calendar) calendar.clone();
        anotherCalendar.set(Calendar.HOUR_OF_DAY, anotherCalendar.get(Calendar.HOUR_OF_DAY) + 1);
        if (deliverySchedule.getDelivererId() == user.getId()) {
            for (int i = 0; i < this.SentScheduledRequestDetails.size(); i++) {
                if (deliverySchedule.getDeliveryId() == this.SentScheduledRequestDetails.get(i).getId()) {
                    eventModel.addEvent(new DefaultScheduleEvent("Sending : " + this.SentScheduledRequestDetails.get(i).getItemName(), calendar.getTime(), anotherCalendar.getTime()));
                }
            }
        } else {
            for (int i = 0; i < this.ReceivedScheduledRequestDetails.size(); i++) {
                if (deliverySchedule.getDeliveryId() == this.ReceivedScheduledRequestDetails.get(i).getId()) {
                    eventModel.addEvent(new DefaultScheduleEvent("Receiving : " + this.ReceivedScheduledRequestDetails.get(i).getItemName(), calendar.getTime(), anotherCalendar.getTime()));
                }
            }
        }
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public List<DeliveryScheduleOptions> getDeliverySchedules() {
        return deliverySchedules;
    }

    public void setDeliverySchedules(List<DeliveryScheduleOptions> deliverySchedules) {
        this.deliverySchedules = deliverySchedules;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public void addEvent() {
        if(event.getId() == null)
            eventModel.addEvent(event);
        else
            eventModel.updateEvent(event);

        event = new DefaultScheduleEvent();
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
