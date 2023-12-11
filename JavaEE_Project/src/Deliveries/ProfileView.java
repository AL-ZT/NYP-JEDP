package Deliveries;

import database.ProjectDbUtil;
import database.User;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean
@SessionScoped
public class ProfileView {
    private User profiledUser;
    private List<ProfileView> userRatings;

    private Integer friendlinessRating;
    private Integer conditionRating;
    private Integer punctualityRating;

    private int ratingId;
    private int delivererId;
    private int recipientId;
    private Integer friendlinessRatingReadonly;
    private Integer conditionRatingReadonly;
    private Integer punctualityRatingReadonly;

    private Integer ratingsReceived;

    private ProjectDbUtil projectDbUtil;

    public ProfileView() throws Exception {
        projectDbUtil = ProjectDbUtil.getInstance();
    }

    public ProfileView(int ratingId, int delivererId, int recipientId, Integer friendlinessRatingReadonly, Integer conditionRatingReadonly, Integer punctualityRatingReadonly) {
        this.ratingId = ratingId;
        this.delivererId = delivererId;
        this.recipientId = recipientId;
        this.friendlinessRatingReadonly = friendlinessRatingReadonly;
        this.conditionRatingReadonly = conditionRatingReadonly;
        this.punctualityRatingReadonly = punctualityRatingReadonly;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public User getProfiledUser() {
        return profiledUser;
    }

    public void setProfiledUser(User profiledUser) {
        this.profiledUser = profiledUser;
    }

    public List<ProfileView> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(List<ProfileView> userRatings) {
        this.userRatings = userRatings;
    }

    public Integer getFriendlinessRating() {
        return friendlinessRating;
    }

    public void setFriendlinessRating(Integer friendlinessRating) {
        this.friendlinessRating = friendlinessRating;
    }

    public Integer getConditionRating() {
        return conditionRating;
    }

    public void setConditionRating(Integer conditionRating) {
        this.conditionRating = conditionRating;
    }

    public Integer getPunctualityRating() {
        return punctualityRating;
    }

    public void setPunctualityRating(Integer punctualityRating) {
        this.punctualityRating = punctualityRating;
    }

    public Integer getFriendlinessRatingReadonly() {
        return friendlinessRatingReadonly;
    }

    public void setFriendlinessRatingReadonly(Integer friendlinessRatingReadonly) {
        this.friendlinessRatingReadonly = friendlinessRatingReadonly;
    }

    public Integer getConditionRatingReadonly() {
        return conditionRatingReadonly;
    }

    public void setConditionRatingReadonly(Integer conditionRatingReadonly) {
        this.conditionRatingReadonly = conditionRatingReadonly;
    }

    public Integer getPunctualityRatingReadonly() {
        return punctualityRatingReadonly;
    }

    public void setPunctualityRatingReadonly(Integer punctualityRatingReadonly) {
        this.punctualityRatingReadonly = punctualityRatingReadonly;
    }

    public Integer getRatingsReceived() {
        return ratingsReceived;
    }

    public void setRatingsReceived(Integer ratingsReceived) {
        this.ratingsReceived = ratingsReceived;
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

    public void loadUserRating(int userId) throws Exception {
        if (this.userRatings != null) {
            this.userRatings.clear();
        }

        this.profiledUser = projectDbUtil.getProfiledUser(userId);
        this.userRatings = projectDbUtil.getUserRatings(userId);

        this.friendlinessRatingReadonly = 0;
        this.conditionRatingReadonly = 0;
        this.punctualityRatingReadonly = 0;

        int i = 0;
        while (i < this.userRatings.size()) {
            this.friendlinessRatingReadonly += this.userRatings.get(i).getFriendlinessRatingReadonly();
            this.conditionRatingReadonly += this.userRatings.get(i).getConditionRatingReadonly();
            this.punctualityRatingReadonly += this.userRatings.get(i).getPunctualityRatingReadonly();
            i++;
        }

        if (i >= 1) {
            this.friendlinessRatingReadonly /= i;
            this.conditionRatingReadonly /= i;
            this.punctualityRatingReadonly /= i;
        }

        this.ratingsReceived = i;
    }

    public void sendRating(DeliveryScheduleOptions delivery) throws Exception {
        int requestId = delivery.getDeliveryId();
        projectDbUtil.addRating(this.friendlinessRating, this.conditionRating, this.punctualityRating, delivery);
        projectDbUtil.removeDeliverySchedule(delivery);
        FacesMessage message = new FacesMessage("Rating Submitted! Deliverer's Page Updated.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public String toString() {
        return "ProfileView{" +
                "ratingId=" + ratingId +
                ", delivererId=" + delivererId +
                ", recipientId=" + recipientId +
                ", friendlinessRatingReadonly=" + friendlinessRatingReadonly +
                ", conditionRatingReadonly=" + conditionRatingReadonly +
                ", punctualityRatingReadonly=" + punctualityRatingReadonly +
                '}';
    }
}
