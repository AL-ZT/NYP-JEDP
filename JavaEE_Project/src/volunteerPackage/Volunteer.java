package volunteerPackage;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import java.io.ByteArrayInputStream;

@ManagedBean
public class Volunteer {

    private int id;
    private String event_name;
    private String event_target;
    private String event_date;
    private String group_category;
    private String event_type;
    private UploadedFile IMGphoto;
    private StreamedContent actualImage;
    public Volunteer() {
    }

    public Volunteer(int id, String event_name, String event_target, String event_date, String group_category, String event_type) {
        this.id = id;
        this.event_name = event_name;
        this.event_target = event_target;
        this.event_date = event_date;
        this.group_category = group_category;
        this.event_type = event_type;
    }

    public Volunteer(int id, String event_name, String event_target, String event_date, String group_category, String event_type, UploadedFile IMGphoto) {
        this.id = id;
        this.event_name = event_name;
        this.event_target = event_target;
        this.event_date = event_date;
        this.group_category = group_category;
        this.event_type = event_type;
        this.IMGphoto = IMGphoto;
    }

    public Volunteer(int id, String event_name, String event_target, String event_date, String group_category, String event_type, ByteArrayInputStream imageByteArray ) {
        this.id = id;
        this.event_name = event_name;
        this.event_target = event_target;
        this.event_date = event_date;
        this.group_category = group_category;
        this.event_type = event_type;
        this.actualImage = new DefaultStreamedContent(imageByteArray);
    }

    public Volunteer(int id, String event_name, String event_target, String event_date, String group_category, String event_type, StreamedContent ActualImage) {
        this.id = id;
        this.event_name = event_name;
        this.event_target = event_target;
        this.event_date = event_date;
        this.group_category = group_category;
        this.event_type = event_type;
        this.actualImage = ActualImage;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_target() {
        return event_target;
    }

    public void setEvent_target(String event_target) {
        this.event_target = event_target;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getGroup_category() {
        return group_category;
    }

    public void setGroup_category(String group_category) {
        this.group_category = group_category;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public UploadedFile getIMGphoto() {
        return IMGphoto;
    }

    public void setIMGphoto(UploadedFile IMGphoto) {
        this.IMGphoto = IMGphoto;
    }


    public StreamedContent getActualImage() {
        return actualImage;
    }

    public void setActualImage(StreamedContent actualImage) {
        this.actualImage = actualImage;
    }

    @Override
    public String toString() {
        return "Volunteer [id=" + id + ", event_name=" + event_name + ", event_target="
                + event_target + ", event_date=" + event_date + ", group_category=" +group_category + ", event_type=" +event_type + "]";
    }

}