package Deliveries;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@SessionScoped
public class DeliverySearchFilters implements Serializable {

    private String locationSearch = "";
    private String itemTypeSearch = "";
    private boolean quickDeliverySearch;
    private String[] regions;
    private String[] itemTypes;
    private String keywordSearch = "";

    public DeliverySearchFilters() {
        regions = new String[4];
        regions[0] = "Yishun";
        regions[1] = "Sembawang";
        regions[2] = "Woodlands";
        regions[3] = "Hougang";

        itemTypes = new String[4];
        itemTypes[0] = "Medicine";
        itemTypes[1] = "Food";
        itemTypes[2] = "Necessities";
        itemTypes[3] = "Others";
    }

    public void addMessage() {
        String summary = quickDeliverySearch ? "Checked" : "Unchecked";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

    public List<String> getRegions() {
        return Arrays.asList(regions);
    }

    public void setRegions(String[] regions) {
        this.regions = regions;
    }

    public List<String> getItemTypes() {
        return Arrays.asList(itemTypes);
    }

    public void setItemTypes(String[] itemTypes) {
        this.itemTypes = itemTypes;
    }

    public String getLocationSearch() {
        return locationSearch;
    }

    public void setLocationSearch(String locationSearch) {
        this.locationSearch = locationSearch;
    }

    public String getItemTypeSearch() {
        return itemTypeSearch;
    }

    public void setItemTypeSearch(String itemTypeSearch) {
        this.itemTypeSearch = itemTypeSearch;
    }

    public boolean isQuickDeliverySearch() {
        return quickDeliverySearch;
    }

    public void setQuickDeliverySearch(boolean quickDeliverySearch) {
        this.quickDeliverySearch = quickDeliverySearch;
    }

    public String getKeywordSearch() {
        return keywordSearch;
    }

    public void setKeywordSearch(String keywordSearch) {
        this.keywordSearch = keywordSearch;
    }

    @Override
    public String toString() {
        return "DeliverySearchFilters{" +
                "locationSearch='" + locationSearch + '\'' +
                ", itemTypeSearch='" + itemTypeSearch + '\'' +
                ", quickDeliverySearch=" + quickDeliverySearch +
                ", keywordSearch='" + keywordSearch + '\'' +
                '}';
    }
}