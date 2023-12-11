package listings;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Listing {
    private int itemID;
    private String itemName;
    private String itemGrp;
    private String itemPrice;       //convert to double/decimal?
    private Boolean expDeliv;
    private String itemDesc;

    public Listing(){

    }

    public Listing(int itemID, String itemName, String itemGrp, String itemPrice, Boolean expDeliv, String itemDesc) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemGrp = itemGrp;
        this.itemPrice = itemPrice;
        this.expDeliv = expDeliv;
        this.itemDesc = itemDesc;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemGrp() {
        return itemGrp;
    }

    public void setItemGrp(String itemGrp) {
        this.itemGrp = itemGrp;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Boolean getExpDeliv() {
        return expDeliv;
    }

    public void setExpDeliv(Boolean expDeliv) {
        this.expDeliv = expDeliv;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    @Override
    public String toString() {
        return "Listing{" +
                "itemID=" + itemID +
                ", itemName='" + itemName + '\'' +
                ", itemGrp='" + itemGrp + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                ", expDeliv=" + expDeliv +
                ", itemDesc='" + itemDesc + '\'' +
                '}';
    }
}