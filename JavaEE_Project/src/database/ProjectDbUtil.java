// jndiName = javaee_project
// General User Table Name = users
// Connection Pool Name = JavaProjectPool
// Use student = demo for jdbc, feel free to add more tables into own database.
// Put any Database related files into "database" package.
// Comment at the top of your own table's CRUD to indicate starting point.
// Mark "To-Do" for your own CRUD so that you don't have to scroll

package database;

import Deliveries.DeliveryQueries;
import Deliveries.DeliveryScheduleOptions;
import Deliveries.DeliverySearchFilters;
import Deliveries.ProfileView;
import Donation.Donation;
import Donation.Donators;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDbUtil {

	private static ProjectDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "jdbc/javaee_project";

	public static ProjectDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new ProjectDbUtil();
		}

		return instance;
	}

	private ProjectDbUtil() throws Exception {
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		
		return theDataSource;
	}


	// Edit Codes Below according to DataBase Query Needs, ignore top









	// TODO : USER CRUD

	public List<User> getUsers() throws Exception {

		List<User> Users = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();

			String sql = "select * from users order by last_name";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {

				// retrieve data from result set row
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				String password = myRs.getString("password");
				String dateOfBirth = myRs.getString("DOB");
				String gender = myRs.getString("gender");
				int phoneNumber = myRs.getInt("phoneNumber");
				String profilePic = myRs.getString("profilePic");
				Boolean adminStatus = myRs.getBoolean("adminStatus");
				Boolean newRequestAlert = myRs.getBoolean("newRequestAlert");
				Boolean requestChangesAlert = myRs.getBoolean("requestChangesAlert");
				int completedDeliveries = myRs.getInt("completedDeliveries");

				// create new student object
				User tempUser = new User(id, firstName, lastName, email, password, dateOfBirth, gender, phoneNumber, profilePic, adminStatus, newRequestAlert, requestChangesAlert, completedDeliveries);

				// add it to the list of students
				Users.add(tempUser);
			}

			return Users;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}

	public void addUser(User theUser) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "insert into users (first_name, last_name, email, password, DOB, gender) values (?, ?, ?, ?, ?, ?)";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theUser.getFirstName());
			myStmt.setString(2, theUser.getLastName());
			myStmt.setString(3, theUser.getEmail());
			myStmt.setString(4, theUser.getPassword());
			myStmt.setString(5, theUser.getDateOfBirth());
			myStmt.setString(6, theUser.getGender());

			myStmt.execute();
		} finally {
			close(myConn, myStmt);
		}

	}

	public User getUser(int userId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();

			String sql = "select * from users where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, userId);

			myRs = myStmt.executeQuery();

			User theUser = null;

			// retrieve data from result set row
			if (myRs.next()) {
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				String password = myRs.getString("password");
				String dateOfBirth = myRs.getString("DOB");
				String gender = myRs.getString("gender");
				int phoneNumber = myRs.getInt("phoneNumber");
				String profilePic = myRs.getString("profilePic");
				Boolean adminStatus = myRs.getBoolean("adminStatus");
				Boolean newRequestAlert = myRs.getBoolean("newRequestAlert");
				Boolean requestChangesAlert = myRs.getBoolean("requestChangesAlert");
				int completedDeliveries = myRs.getInt("completedDeliveries");

				theUser = new User(id, firstName, lastName, email, password, dateOfBirth, gender, phoneNumber, profilePic, adminStatus, newRequestAlert, requestChangesAlert, completedDeliveries);
			} else {
				throw new Exception("Could not find student id: " + userId);
			}

			return theUser;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}

	public void updateUser(User theUser) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update users "
					+ " set first_name=?, last_name=?, email=?, password=?, DOB=?, gender=?, phoneNumber=?, adminStatus=?, newRequestAlert=?, requestChangesAlert=?"
					+ " where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theUser.getFirstName());
			myStmt.setString(2, theUser.getLastName());
			myStmt.setString(3, theUser.getEmail());
			myStmt.setString(4, theUser.getPassword());
			myStmt.setString(5, theUser.getDateOfBirth());
			myStmt.setString(6, theUser.getGender());
			myStmt.setInt(7, theUser.getPhoneNumber());
			myStmt.setBoolean(8, theUser.getAdminStatus());
			myStmt.setBoolean(9, theUser.getNewRequestAlert());
			myStmt.setBoolean(10, theUser.getRequestChangesAlert());
			myStmt.setInt(11, theUser.getId());

			myStmt.execute();
		} finally {
			close(myConn, myStmt);
		}

	}

	public void deleteUser(int userId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete from users where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, userId);

			myStmt.execute();
		} finally {
			close(myConn, myStmt);
		}
	}

	private Connection getConnection() throws Exception {

		Connection theConn = dataSource.getConnection();

		return theConn;
	}

	private void close(Connection theConn, Statement theStmt) {
		close(theConn, theStmt, null);
	}

	private void close(Connection theConn, Statement theStmt, ResultSet theRs) {

		try {
			if (theRs != null) {
				theRs.close();
			}

			if (theStmt != null) {
				theStmt.close();
			}

			if (theConn != null) {
				theConn.close();
			}

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public void loginUser(User object) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();
			String sql = "select * from javaee_project.users where email=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, object.getEmail());
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				object.setId(myRs.getInt("id"));
				object.setFirstName(myRs.getString("first_name"));
				object.setLastName(myRs.getString("last_name"));
				object.setPassword(myRs.getString("password"));
				object.setDateOfBirth(myRs.getString("DOB"));
				object.setGender(myRs.getString("gender"));
				object.setPhoneNumber(myRs.getInt("phoneNumber"));
				object.setProfilePic(myRs.getString("profilePic"));
				object.setNewRequestAlert(myRs.getBoolean("newRequestAlert"));
				object.setRequestChangesAlert(myRs.getBoolean("requestChangesAlert"));
				object.setCompletedDeliveries(myRs.getInt("completedDeliveries"));
				if (myRs.getInt("adminStatus") == 1) {
					object.setAdminStatus(true);
				} else {
					object.setAdminStatus(false);
				}
			} else {
				object.setId(-1);
				object.setFirstName(null);
				object.setLastName(null);
				object.setPassword(null);
				object.setDateOfBirth(null);
				object.setGender(null);
				object.setPhoneNumber(0);
				object.setProfilePic(null);
				object.setAdminStatus(null);
				object.setRequestChangesAlert(null);
				object.setNewRequestAlert(null);
				object.setCompletedDeliveries(-1);
			}
		} finally {
			close(myConn, myStmt, myRs);
		}
	}






	// TODO : Delivery CRUD

	public void confirmDelivererDeliverySchedule(int scheduleId, int userId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update user_delivery_schedules "
					+ " set delivererItemConfirmation = 1" +
					" where id=? and deliverer_id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, scheduleId);
			myStmt.setInt(2, userId);

			myStmt.execute();
		} finally {
			close(myConn, myStmt);
		}
	}

	public void confirmRecipientDeliverySchedule(int scheduleId, int userId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update user_delivery_schedules "
					+ " set recipientItemConfirmation = 1" +
					" where id=? and recipient_id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, scheduleId);
			myStmt.setInt(2, userId);

			myStmt.execute();
		} finally {
			close(myConn, myStmt);
		}
	}

	public boolean checkExistingRequest(int deliveryId, int userId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();

			String sql = "select * from user_delivery_schedules where deliverer_id=? and delivery_id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, userId);
			myStmt.setInt(2, deliveryId);

			myRs = myStmt.executeQuery();

			if (myRs.next()) {
				return true;
			} else {
				return false;
			}
		} finally {
			close(myConn, myStmt, myRs);
		}
	}

	public List<ProfileView> getUserRatings(int userId) throws Exception {
		List<ProfileView> ratings = new ArrayList<>();

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();

			String sql = "select * from user_ratings where deliverer_id=? order by id";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1, userId);

			myRs = myStmt.executeQuery();

			// process result set
			while (myRs.next()) {

				// retrieve data from result set row
				int id = myRs.getInt("id");
				int delivererId = myRs.getInt("deliverer_id");
				int recipientId = myRs.getInt("recipient_id");
				Integer friendlinessRating = myRs.getInt("friendlinessRating");
				Integer conditionRating = myRs.getInt("conditionRating");
				Integer punctualityRating = myRs.getInt("punctualityRating");

				// create new student object
				ProfileView tempRating = new ProfileView(id, delivererId, recipientId, friendlinessRating, conditionRating, punctualityRating);

				// add it to the list of students
				ratings.add(tempRating);
			}

			return ratings;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public User getProfiledUser(int userId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();

			String sql = "select * from users where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, userId);

			myRs = myStmt.executeQuery();

			User theUser = null;

			// retrieve data from result set row
			if (myRs.next()) {
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				String password = myRs.getString("password");
				String dateOfBirth = myRs.getString("DOB");
				String gender = myRs.getString("gender");
				int phoneNumber = myRs.getInt("phoneNumber");
				String profilePic = myRs.getString("profilePic");
				Boolean adminStatus = myRs.getBoolean("adminStatus");
				Boolean newRequestAlert = myRs.getBoolean("newRequestAlert");
				Boolean requestChangesAlert = myRs.getBoolean("requestChangesAlert");
				int completedDeliveries = myRs.getInt("completedDeliveries");

				theUser = new User(id, firstName, lastName, email, password, dateOfBirth, gender, phoneNumber, profilePic, adminStatus, newRequestAlert, requestChangesAlert, completedDeliveries);
			} else {
				throw new Exception("Could not find student id: " + userId);
			}

			return theUser;

		} finally {
			close(myConn, myStmt, myRs);
		}
	}

	public User loadUserForAlert(int userId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();

			String sql = "select * from users where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, userId);

			myRs = myStmt.executeQuery();

			User theUser = null;

			// retrieve data from result set row
			if (myRs.next()) {
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				String password = myRs.getString("password");
				String dateOfBirth = myRs.getString("DOB");
				String gender = myRs.getString("gender");
				int phoneNumber = myRs.getInt("phoneNumber");
				String profilePic = myRs.getString("profilePic");
				Boolean adminStatus = myRs.getBoolean("adminStatus");
				Boolean newRequestAlert = myRs.getBoolean("newRequestAlert");
				Boolean requestChangesAlert = myRs.getBoolean("requestChangesAlert");
				int completedDeliveries = myRs.getInt("completedDeliveries");

				theUser = new User(id, firstName, lastName, email, password, dateOfBirth, gender, phoneNumber, profilePic, adminStatus, newRequestAlert, requestChangesAlert, completedDeliveries);
			} else {
				throw new Exception("Could not find student id: " + userId);
			}

			return theUser;

		} finally {
			close(myConn, myStmt, myRs);
		}
	}

	public void removeDeliveryRequest(int deliveryId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete from delivery_requests "
					+ " where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, deliveryId);

			myStmt.execute();
		} finally {
			close(myConn, myStmt);
		}
	}

	public void removeDeliverySchedule(DeliveryScheduleOptions delivery) throws Exception {
		System.out.println(delivery.getId());
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete from user_delivery_schedules "
					+ " where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, delivery.getId());

			myStmt.execute();
		} finally {
			close(myConn, myStmt);
			removeDeliveryRequest(delivery.getDeliveryId());
		}
	}
	public void addRating(Integer fRating, Integer cRating, Integer pRating, DeliveryScheduleOptions delivery) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "insert into user_ratings (deliverer_id, recipient_id, friendlinessRating, conditionRating, punctualityRating) values (?, ?, ?, ?, ?)";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, delivery.getDelivererId());
			myStmt.setInt(2, delivery.getRecipientId());
			myStmt.setInt(3, fRating);
			myStmt.setInt(4, cRating);
			myStmt.setInt(5, pRating);

			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public void deliveryRequestToAccepted(DeliveryScheduleOptions delivery) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update delivery_requests "
					+ " set accepted = 1" +
					" where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, delivery.getDeliveryId());

			myStmt.execute();
		} finally {
			close(myConn, myStmt);
		}
	}

	public void pushScheduleToAccepted(DeliveryScheduleOptions delivery) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update user_delivery_schedules "
					+ " set deliveryStatus = 1"
					+ " where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, delivery.getId());

			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public void addCompletedDelivery(int delivererId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update users "
					+ " set completedDeliveries = completedDeliveries + 1"
					+ " where id=?";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1, delivererId);

			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public void pushScheduleToDeliveredCheck() throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update user_delivery_schedules "
					+ " set deliveryStatus = 2"
					+ " where delivererItemConfirmation = 1 and recipientItemConfirmation = 1";

			myStmt = myConn.prepareStatement(sql);

			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}

    public List<DeliveryScheduleOptions> getUserSchedules() throws Exception {
	    List<DeliveryScheduleOptions> deliverySchedules = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            String sql = "select * from user_delivery_schedules order by id";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            // process result set
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("id");
                int deliveryId = myRs.getInt("delivery_id");
				int recipientId = myRs.getInt("recipient_id");
				int delivererId = myRs.getInt("deliverer_id");
                String delivererName = myRs.getString("deliverer_name");
                String recipientName = myRs.getString("recipient_name");
                long deliveryDateTime = myRs.getLong("delivery_datetime");
                String additionalNotes = myRs.getString("additional_notes");
                int status = myRs.getInt("deliveryStatus");
                Boolean delivererItemConfirmation = myRs.getBoolean("delivererItemConfirmation");
				Boolean recipientItemConfirmation = myRs.getBoolean("recipientItemConfirmation");

                // create new student object
                DeliveryScheduleOptions tempRequests = new DeliveryScheduleOptions(id, deliveryId, delivererId, recipientId, deliveryDateTime, delivererName, recipientName, additionalNotes, status, delivererItemConfirmation, recipientItemConfirmation);

                // add it to the list of students
                deliverySchedules.add(tempRequests);
            }

            return deliverySchedules;
        }
        finally {
            close (myConn, myStmt, myRs);
        }
    }

	public List<DeliveryQueries> getRequests(User user) throws Exception {

		List<DeliveryQueries> requests = new ArrayList<>();

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();

			String sql = "select * from delivery_requests WHERE user_id<>? AND accepted=0 order by id";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1, user.getId());

			myRs = myStmt.executeQuery();

			// process result set
			while (myRs.next()) {

				// retrieve data from result set row
				int id = myRs.getInt("id");
				int user_id = myRs.getInt("user_id");
				String name = myRs.getString("name");
				String itemName = myRs.getString("itemName");
				String itemType = myRs.getString("itemType");
				String itemDesc = myRs.getString("itemDesc");
				Boolean quickDelivery = myRs.getBoolean("quickDelivery");
				String location = myRs.getString("location");
				String address = myRs.getString("address");
				Boolean accepted = myRs.getBoolean("accepted");
				String itemPicPath = myRs.getString("itemPicPath");

				// create new student object
				DeliveryQueries tempRequests = new DeliveryQueries(id, user_id, name, itemName, itemType, itemDesc, quickDelivery, location, address, accepted, itemPicPath);

				// add it to the list of students
				requests.add(tempRequests);
			}

			return requests;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void addRequest(DeliveryQueries theRequest) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "insert into delivery_requests (user_id, location, name, itemType, quickDelivery, itemDesc) values (?, ?, ?, ?, ?, ?)";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, theRequest.getUser_id());
			myStmt.setString(2, theRequest.getLocation());
			myStmt.setString(3, theRequest.getName());
			myStmt.setString(4, theRequest.getItemType());
			myStmt.setBoolean(5, theRequest.isQuickDelivery());
			myStmt.setString(6, theRequest.getItemDesc());

			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public List<DeliveryQueries> getSearchedRequests(DeliverySearchFilters searchFilters, User user) throws Exception {

		List<DeliveryQueries> requests = new ArrayList<>();

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();

			String sql = "select * from delivery_requests where location LIKE ? AND itemType LIKE ? AND quickDelivery=? AND user_id<>? AND accepted=0" +
					" AND (itemDesc LIKE ? OR name LIKE ?) order by id";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setString(1, searchFilters.getLocationSearch());
			myStmt.setString(2, searchFilters.getItemTypeSearch());
			myStmt.setBoolean(3, searchFilters.isQuickDeliverySearch());
			myStmt.setInt(4, user.getId());
			myStmt.setString(5, "%" + searchFilters.getKeywordSearch() + "%");
			myStmt.setString(6, "%" + searchFilters.getKeywordSearch() + "%");
			myRs = myStmt.executeQuery();

			// process result set
			while (myRs.next()) {

				// retrieve data from result set row
				int id = myRs.getInt("id");
				int user_id = myRs.getInt("user_id");
				String name = myRs.getString("name");
				String itemName = myRs.getString("itemName");
				String itemType = myRs.getString("itemType");
				String itemDesc = myRs.getString("itemDesc");
				Boolean quickDelivery = myRs.getBoolean("quickDelivery");
				String location = myRs.getString("location");
				String address = myRs.getString("address");
				Boolean accepted = myRs.getBoolean("accepted");
				String itemPicPath = myRs.getString("itemPicPath");

				// create new student object
				DeliveryQueries tempRequests = new DeliveryQueries(id, user_id, name, itemName, itemType, itemDesc, quickDelivery, location, address, accepted, itemPicPath);

				// add it to the list of students
				requests.add(tempRequests);
			}

			System.out.println(requests);

			return requests;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public DeliveryQueries getRequest(int requestId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();

			String sql = "select * from delivery_requests where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, requestId);

			myRs = myStmt.executeQuery();

			DeliveryQueries theRequest = null;

			// retrieve data from result set row
			if (myRs.next()) {
				int id = myRs.getInt("id");
				int user_id = myRs.getInt("user_id");
				String name = myRs.getString("name");
				String itemName = myRs.getString("itemName");
				String itemType = myRs.getString("itemType");
				String itemDesc = myRs.getString("itemDesc");
				Boolean quickDelivery = myRs.getBoolean("quickDelivery");
				String location = myRs.getString("location");
				String address = myRs.getString("address");
				Boolean accepted = myRs.getBoolean("accepted");
				String itemPicPath = myRs.getString("itemPicPath");

				// create new student object
				theRequest = new DeliveryQueries(id, user_id, name, itemName, itemType, itemDesc, quickDelivery, location, address, accepted, itemPicPath);
			}
			else {
				throw new Exception("Could not find student id: " + requestId);
			}

			return theRequest;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void updateRequest(DeliveryQueries theRequest) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update delivery_requests "
					+ " set location=?, name=?, itemType=?, quickDelivery=?, itemDesc=?"
					+ " where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theRequest.getLocation());
			myStmt.setString(2, theRequest.getName());
			myStmt.setString(3, theRequest.getItemType());
			myStmt.setBoolean(4, theRequest.isQuickDelivery());
			myStmt.setString(5, theRequest.getItemDesc());
			myStmt.setInt(4, theRequest.getId());

			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}

	}

	public void deleteRequest(int requestId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete from delivery_requests where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, requestId);

			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public void addSchedule(DeliveryQueries selectedRequest, User user, long time, String comments) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "insert into user_delivery_schedules (delivery_id, deliverer_id, recipient_id, deliverer_name, recipient_name, delivery_datetime, additional_notes) values (?, ?, ?, ?, ?, ?, ?)";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, selectedRequest.getId());
			myStmt.setInt(2, user.getId());
			myStmt.setInt(3, selectedRequest.getUser_id());
			myStmt.setString(4, user.getFirstName() + " " + user.getLastName());
			myStmt.setString(5, selectedRequest.getName());
			myStmt.setLong(6, time);
			myStmt.setString(7, comments);

			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public void updateSchedule(int scheduleId, long time, String comments, int status) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update user_delivery_schedules "
					+ " set delivery_datetime=?, additional_notes=?, deliveryStatus=?"
					+ " where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setLong(1, time);
			myStmt.setString(2, comments);
			if (status == 1) {
				myStmt.setInt(3, 0);
			} else {
				myStmt.setInt(3, status);
			}
			myStmt.setInt(4, scheduleId);

			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public void deleteSchedule(int scheduleId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete from user_delivery_schedules where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, scheduleId);

			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public void resetDeliveryRequest(int deliveryId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update delivery_requests "
					+ " set accepted = 0"
					+ " where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, deliveryId);

			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}

	}



	// TODO : Donations CRUD
	//Donations CRUD
	public ArrayList<Donation> getAllDonations() throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		ArrayList<Donation> results = new ArrayList<Donation>();

		try {
			myConn = dataSource.getConnection();
			String sql = "select * from javaee_project.donationlist";
			myStmt = myConn.prepareStatement(sql);
			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				int id = myRs.getInt("id");
				String name = myRs.getString("event_name");
				String description = myRs.getString("description");
				String type = myRs.getString("event_type");
				int goal = myRs.getInt("goal");
				Date date_start = myRs.getDate("date_start");
				Date date_end = myRs.getDate("date_end");
				int noOfDonors = myRs.getInt("noOfDonors");
				int current_Amount = myRs.getInt("current_amount");
				String filename = myRs.getString("event_image");
				Donation tempDonation = new Donation(id, name, description, type, goal, date_start, date_end, noOfDonors, current_Amount, filename);
				results.add(tempDonation);
			}
		} finally {
			close(myConn, myStmt, myRs);
		}

		return results;
	}

	public ArrayList<Donation> getDonationsWithType(String event_type) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		ArrayList<Donation> results = new ArrayList<Donation>();

		try {
			myConn = dataSource.getConnection();
			String sql = "select * from javaee_project.donationlist where event_type=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, event_type);
			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				int id = myRs.getInt("id");
				String name = myRs.getString("event_name");
				String description = myRs.getString("description");
				String type = myRs.getString("event_type");
				int goal = myRs.getInt("goal");
				Date date_start = myRs.getDate("date_start");
				Date date_end = myRs.getDate("date_end");
				int noOfDonors = myRs.getInt("noOfDonors");
				int current_Amount = myRs.getInt("current_amount");
				String filename = myRs.getString("event_image");

				Donation tempDonation = new Donation(id, name, description, type, goal, date_start, date_end, noOfDonors, current_Amount, filename);

				results.add(tempDonation);
			}
		} finally {
			close(myConn, myStmt, myRs);
		}

		return results;
	}

	public void addDonationEvent(Donation donation_object) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {

			myConn = dataSource.getConnection();
			String sql = "insert into donationlist (event_name, description, event_type, goal, date_start, date_end, noOfDonors, current_amount, event_image) values (?,?,?,?,?,?,?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, donation_object.getName());
			myStmt.setString(2, donation_object.getDescription());
			myStmt.setString(3, donation_object.getType());
			myStmt.setInt(4, donation_object.getGoal());
			Date date_start = new java.sql.Date(donation_object.getDate_start().getTime());
			myStmt.setDate(5, date_start);
			Date date_end = new java.sql.Date(donation_object.getDate_end().getTime());
			myStmt.setDate(6, date_start);
			myStmt.setInt(7, 0);
			myStmt.setInt(8, 0);
			myStmt.setString(9, donation_object.getFilename());
			myStmt.execute();
		} finally {
			close(myConn, myStmt);
		}

	}

	public void deleteDonationEvent(int id) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();
			String sql = "delete from javaee_project.donationlist where id=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, id);
			myStmt.execute();
		} finally {
			close(myConn, myStmt);
		}
	}

	public Donation getDonation(int event_id) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();
			String sql = "select * from javaee_project.donationlist where id=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, event_id);
			myRs = myStmt.executeQuery();
			Donation event = null;

			if (myRs.next()) {
				int id = myRs.getInt("id");
				String event_name = myRs.getString("event_name");
				String description = myRs.getString("description");
				String event_type = myRs.getString("event_type");
				int goal = myRs.getInt("goal");
				Date date_start = myRs.getDate("date_start");
				Date date_end = myRs.getDate("date_end");
				int noOfDonors = myRs.getInt("noOfDonors");
				int current_amount = myRs.getInt("current_amount");
				String filename = myRs.getString("event_image");

				event = new Donation(id,event_name,description,event_type,goal,date_start,date_end,noOfDonors,current_amount, filename);
			} else {
				throw new Exception("Could not find event : " + event_id);
			}
			return event;
		} finally {
			close(myConn,myStmt,myRs);
		}
	}

	public void updateEvent(Donation obj) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update javaee_project.donationlist set event_name=?, description=?,event_type=?, goal=?, date_start=?, date_end=?, event_image=? where id=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, obj.getName());
			myStmt.setString(2, obj.getDescription());
			myStmt.setString(3, obj.getType());
			myStmt.setInt(4, obj.getGoal());
			Date date_start = new java.sql.Date(obj.getDate_start().getTime());
			myStmt.setDate(5, date_start);
			Date date_end = new java.sql.Date(obj.getDate_end().getTime());
			myStmt.setDate(6, date_end);
			myStmt.setString(7, obj.getFilename());
			myStmt.setInt(8, obj.getId());

			myStmt.execute();
		} finally {
			close(myConn,myStmt);
		}

	}

	public ArrayList<Donators> getDonators(int eventid) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        ArrayList<Donators> results = new ArrayList<Donators>();

        try {
            myConn = getConnection();
            String sql = "select * from javaee_project.donation_records where event_id=? order by donation_amount DESC";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, eventid);
            myRs = myStmt.executeQuery();

            while(myRs.next()) {
                int transaction_id = myRs.getInt("transaction_id");
                int event_id = myRs.getInt("event_id");
                String name = myRs.getString("first_name") + " " + myRs.getString("last_name");
                if (name.equals("null null") || name.equals(" ")) {
                    name = "Anonymous";
                }
                int donation_amount = myRs.getInt("donation_amount");
                Date date_donated = myRs.getDate("date_donated");
                Donators tempDonator = new Donators(transaction_id, event_id, name, donation_amount, date_donated);
                results.add(tempDonator);
            }
        } finally {
            close(myConn,myStmt,myRs);
        }

        return results;
    }

    public void addDonationTransaction(int event_id, String first_name, String last_name, int donation_amount, Date date_donated) throws Exception {
	    Connection myConn = null;
	    PreparedStatement myStmt =null;

	    try {
	        myConn = getConnection();
	        String mysql = "insert into javaee_project.donation_records(event_id, first_name, last_name, donation_amount, date_donated) values (?, ?, ?, ?, ?)";
	        myStmt = myConn.prepareStatement(mysql);
	        myStmt.setInt(1, event_id);
	        myStmt.setString(2, first_name);
	        myStmt.setString(3, last_name);
	        myStmt.setInt(4, donation_amount);
	        myStmt.setDate(5, date_donated);
	        myStmt.execute();
        } finally {
	        close(myConn,myStmt);
        }
    }

    public void addDonationMoney(int event_id, int money) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();
            String mysql = "update javaee_project.donationlist set current_amount = current_amount+? where id=?";
            myStmt = myConn.prepareStatement(mysql);
            myStmt.setInt(1, money);
            myStmt.setInt(2, event_id);
            myStmt.execute();
            String mysql2 = "update javaee_project.donationlist set noOfDonors = noOfDonors+1 where id=?";
            myStmt = myConn.prepareStatement(mysql2);
            myStmt.setInt(1, event_id);
            myStmt.execute();
        } finally {
            close(myConn, myStmt);
        }
    }
}
