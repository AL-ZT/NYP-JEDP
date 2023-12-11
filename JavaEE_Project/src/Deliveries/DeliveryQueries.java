package Deliveries;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class DeliveryQueries {

    private int id;
    private int user_id;
    private String name;
    private String itemName;
    private String itemType;
    private String itemDesc;
    private boolean quickDelivery;
    private String location;
    private String address;
    private boolean accepted;
    private String itemPicPath;

    public DeliveryQueries() {
    }

    public DeliveryQueries(int id, int user_id, String name, String itemName, String itemType, String itemDesc, boolean quickDelivery, String location, String address, boolean accepted, String itemPicPath) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemDesc = itemDesc;
        this.quickDelivery = quickDelivery;
        this.location = location;
        this.address = address;
        this.accepted = accepted;
        this.itemPicPath = itemPicPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public boolean isQuickDelivery() {
        return quickDelivery;
    }

    public void setQuickDelivery(boolean quickDelivery) {
        this.quickDelivery = quickDelivery;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getItemPicPath() {
        return itemPicPath;
    }

    public void setItemPicPath(String itemPicPath) {
        this.itemPicPath = itemPicPath;
    }

    @Override
    public String toString() {
        return "DeliveryQueries{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", name='" + name + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemType='" + itemType + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                ", quickDelivery=" + quickDelivery +
                ", location='" + location + '\'' +
                ", address='" + address + '\'' +
                ", accepted=" + accepted +
                ", itemPicPath='" + itemPicPath + '\'' +
                '}';
    }
}
