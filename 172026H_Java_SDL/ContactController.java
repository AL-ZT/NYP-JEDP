package itp212.contacts;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import javax.ejb.SessionContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@SessionScoped
public class ContactController implements Serializable {

	private List<Contact> contactList;
	private List<Contact> filteredContacts;
	private Contact selectedContact;
	private boolean rowSelected = false;
	List<String> addresses = new ArrayList<>();


	public ContactController(){
		this.selectedContact = new Contact();
		addresses.add("Jurong East");
		addresses.add("Bukit Batok");
		addresses.add("Clementi");
		addresses.add("Yishun");
		addresses.add("Hougang");
		addresses.add("Woodlands");
		addresses.add("Sembawang");
		addresses.add("Toa Payoh");
		addresses.add("Bishan");
		addresses.add("Serangoon");
		addresses.add("Pasir Ris");
		addresses.add("Bedok");
		addresses.add("Ang Mo Kio");
	}


	public List<Contact> getFilteredContacts() {
		return filteredContacts;
	}

	public void setFilteredContacts(List<Contact> filteredContacts) {
		this.filteredContacts = filteredContacts;
	}

	public List<Contact> getContactList() {
		return contactList;
	}

	public void setContactList(List<Contact> contactList) {
		this.contactList = contactList;
	}


	public Contact getSelectedContact() {
		return selectedContact;
	}

	public void setSelectedContact(Contact selectedContact) {
		this.selectedContact = selectedContact;
		System.out.println("============ setSelectedContact " + this.selectedContact);
	}


	public void onRowSelect(SelectEvent event){
		this.rowSelected = true;
		this.selectedContact = ((Contact) event.getObject());
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = ec.getSessionMap();
		sessionMap.put("contact", this.selectedContact);
	}

	public void saveContact(){
		this.displayConfirmation("New Contact Added");
	}


	private void displayConfirmation(String message){
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("appMessage", new FacesMessage("Success",  message) );
	}

	public void deleteContact(){
		this.displayConfirmation("Contact Deleted");
	}

	public void clearContact(){
		this.rowSelected = false;
		this.selectedContact = null;
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contact", new Contact());
		RequestContext.getCurrentInstance().reset("contactDetailForm");
	}


	public void loadContacts(){
		if (this.contactList == null){
			this.contactList = new ArrayList<Contact>();
			contactList.add(new Contact(this.getRandomId(), "Jacelyn", "Lian", "91234567", this.getRandomAddress(), "jacelyn@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Sandra", "Tan",  "92234567", this.getRandomAddress(), "sandra@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Sean", "Teo","69934834", this.getRandomAddress(), "sean@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Han Boon", "Goh",  "78182374", this.getRandomAddress(), "hanboon@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Cheng Ying", "Guo", "63984923", this.getRandomAddress(), "chengying@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Kuan Wei", "Ng", "91757384", this.getRandomAddress(), "kuanwei@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Kyle", "Solis", "85736498", this.getRandomAddress(), "kyle@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Jin Cheng", "Choo", "923487342", this.getRandomAddress(), "jincheng@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Jing", "Li",  "99989891", this.getRandomAddress(), "jingli@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Jeffrey", "Ng", "69817383", this.getRandomAddress(), "jeffrey@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Zhi Jie", "Ng", "812549572", this.getRandomAddress(), "zhijie@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Vienna", "Chong", "666598932", this.getRandomAddress(), "vienna@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Ivan", "Lai", "815047342", this.getRandomAddress(), "ivan@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Chong-Yu", "Leong", "95732958", this.getRandomAddress(), "chongyu@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Yee Ann", "Tan", "91234567", this.getRandomAddress(), "yeeann@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Zheng Hui", "Toh", "61604743", this.getRandomAddress(), "zhenghui@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Syafiq", "Akhbar", "87345918", this.getRandomAddress(), "syafiq@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Zheng Xuan", "Chia", "78929743", this.getRandomAddress(), "zhengxuan@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Kyi", "Myat", "91234567", this.getRandomAddress(), "kyi@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Wen Qiang", "Teo", "601923934", this.getRandomAddress(), "wenqiang@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Arno", "Gan", "108523934", this.getRandomAddress(), "arno@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Jing Le", "Chuang", "7274938", this.getRandomAddress(), "jingle@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Lik Wee", "Low", "89387474", this.getRandomAddress(), "likwee@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Quang", "Pham","1231231", this.getRandomAddress(), "quang@gmail.com", this.getRandomPostal()));
			contactList.add(new Contact(this.getRandomId(), "Xing Yu", "Lim", "32879823", this.getRandomAddress(), "xingyu@gmail.com", this.getRandomPostal()));
		}
	}

	private String getRandomId() {
		return UUID.randomUUID().toString().substring(0, 8);
	}

	private String getRandomPostal() {
		Random rand = new Random();
		return String.valueOf(rand.nextInt(900000) + 100000);
	}

	private String getRandomAddress() {
		return addresses.get(new Random().nextInt(13));
	}
}
