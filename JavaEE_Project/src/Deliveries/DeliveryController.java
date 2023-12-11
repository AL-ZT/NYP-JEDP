package Deliveries;

import database.ProjectDbUtil;
import database.User;
import org.primefaces.event.SelectEvent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
@SessionScoped
public class DeliveryController implements Serializable {

    private List<DeliveryQueries> requests;
    private DeliveryQueries selectedRequest;
    private List<String> pageSize;
    private int selectedPage = 1;
    private boolean preserveRequestData = false;
    private List<DeliveryQueries> subRequests;
    private List<DeliveryQueries> filteredRequests;
    private ProjectDbUtil projectDbUtil;
    private DeliveryScheduleOptions scheduleOptions;
    private boolean searchButton = false;
    private Logger logger = Logger.getLogger(getClass().getName());

    public DeliveryController() throws Exception {
        requests = new ArrayList<>();
        pageSize = new ArrayList<>();

        projectDbUtil = ProjectDbUtil.getInstance();
    }

    public boolean isSearchButton() {
        return searchButton;
    }

    public void setSearchButton(boolean searchButton) {
        this.searchButton = searchButton;
    }

    public String clickedOnSearch() {

        setSearchButton(true);
        selectedPage = 1;

        return "delivery?faces-redirect=true";
    }

    public String incrementPage() {
        selectedPage++;
        this.preserveRequestData = true;

        return "delivery?faces-redirect=true";
    }

    public String decrementPage() {
        selectedPage--;
        this.preserveRequestData = true;

        return "delivery?faces-redirect=true";
    }

    public void previewRequest(DeliveryQueries request) {
        this.selectedRequest = request;
        this.preserveRequestData = true;
    }

    public String selectRequest(DeliveryQueries request) {
        this.selectedRequest = request;

        return "add_Delivery_Schedule?faces-redirect=true";
    }

    public String clearSearch() {
        selectedPage = 1;

        return "delivery?faces=redirect=true";
    }

    public boolean isPreserveRequestData() {
        return preserveRequestData;
    }

    public void setPreserveRequestData(boolean preserveRequestData) {
        this.preserveRequestData = preserveRequestData;
    }

    public List<DeliveryQueries> getSubRequests() {
        return subRequests;
    }

    public void setSubRequests(List<DeliveryQueries> subRequests) {
        this.subRequests = subRequests;
    }

    public DeliveryQueries getSelectedRequest() {
        return selectedRequest;
    }

    public DeliveryScheduleOptions getScheduleOptions() {
        return scheduleOptions;
    }

    public List<String> getPageSize() {
        return pageSize;
    }

    public void setPageSize(List<String> pageSize) {
        this.pageSize = pageSize;
    }

    public int getSelectedPage() {
        return selectedPage;
    }

    public void setSelectedPage(int selectedPage) {
        this.selectedPage = selectedPage;
    }

    public void setScheduleOptions(DeliveryScheduleOptions scheduleOptions) {
        this.scheduleOptions = scheduleOptions;
    }

    public void setSelectedRequest(DeliveryQueries selectedRequest) {
        this.selectedRequest = selectedRequest;
    }

    public List<DeliveryQueries> getRequests() {
        return requests;
    }

    public List<DeliveryQueries> getFilteredRequests() {
        return filteredRequests;
    }

    public void setFilteredRequests(List<DeliveryQueries> filteredRequests) {
        this.filteredRequests = filteredRequests;
    }

    public void setRequests(List<DeliveryQueries> requests) {
        this.requests = requests;
    }


    public void loadRequests(DeliverySearchFilters filters, User user) {

        logger.info("Loading Request");

        try {
            if (!preserveRequestData) {
                requests.clear();
                pageSize.clear();
                this.selectedPage = 1;

                if (this.searchButton) {
                    if (filters.getLocationSearch() == null) {
                        filters.setLocationSearch("%%");
                    }
                    if (filters.getItemTypeSearch() == null) {
                        filters.setItemTypeSearch("%%");
                    }
                    requests = projectDbUtil.getSearchedRequests(filters, user);
                } else {
                    requests = projectDbUtil.getRequests(user);
                }

                if (requests.size() / (double) 5 > 1) {
                    if (requests.size() % 5 == 0) {
                        for (int i = 1; i <= (requests.size() / 5); i++) {
                            pageSize.add(Integer.toString(i));
                        }
                    } else {
                        for (int i = 1; i <= ((requests.size() / 5) + 1); i++) {
                            pageSize.add(Integer.toString(i));
                        }
                    }
                    subRequests = requests.subList(0, 5);
                } else {
                    pageSize.add("1");
                    subRequests = requests.subList(0, requests.size());
                }
            } else {
                if (selectedPage != pageSize.size()) {
                    subRequests = requests.subList((selectedPage * 5) - 5, (selectedPage * 5));
                } else {
                    subRequests = requests.subList((selectedPage * 5) - 5, requests.size());
                }
            }

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error loading Request", exc);

            // add error message for JSF page
            addErrorMessage(exc);
        } finally {
            this.searchButton = false;
            this.preserveRequestData = false;
        }
    }

    public String loadRequest(int requestId) {

        logger.info("loading request: " + requestId);

        try {
            // get user from database
            DeliveryQueries theRequest = projectDbUtil.getRequest(requestId);

            // put in the request attribute ... so we can use it on the form page
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

            Map<String, Object> requestMap = externalContext.getRequestMap();
            requestMap.put("request", theRequest);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error loading request id:pr" + requestId, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "update-student-form.xhtml";
    }

    public String deleteRequest(int requestId) {

        logger.info("Deleting request id: " + requestId);

        try {

            // delete the student from the database
            projectDbUtil.deleteRequest(requestId);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error deleting request id: " + requestId, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "list-students";
    }

    public String updateRequest(DeliveryQueries theRequest) {

        logger.info("updating request: " + theRequest);

        try {

            // update student in the database
            projectDbUtil.updateRequest(theRequest);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error updating request: " + theRequest, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "list-students?faces-redirect=true";
    }

    private void addErrorMessage(Exception exc) {
        FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onRowSelect(SelectEvent event) {
        this.selectedRequest = ((DeliveryQueries) event.getObject());
    }

    public String changePage(String pageNo) {
        selectedPage = Integer.parseInt(pageNo);
        this.preserveRequestData = true;

        return "delivery?faces-redirect=true";
    }

    public int getIntegerPageSize() {
        return this.pageSize.size();
    }
}
