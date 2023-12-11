package volunteerPackage;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
@ManagedBean
@SessionScoped
public class VolunteerDbUtil implements Serializable {

    private static VolunteerDbUtil instance;
    private DataSource dataSource;
    private String jndiName = "jdbc/javaee_project";
    //private String jndiName = "jdbc/SQLite";

    public static VolunteerDbUtil getInstance() throws Exception {
        if (instance == null) {
            instance = new VolunteerDbUtil();
        }
        return instance;
    }


    private VolunteerDbUtil() throws Exception {
        dataSource = getDataSource();
    }

    private DataSource getDataSource() throws NamingException {
        Context context = new InitialContext();
        DataSource theDataSource = (DataSource) context.lookup(jndiName);
        return theDataSource;
    }

    public List<Volunteer> getVolunteers() throws Exception {

        List<Volunteer> volunteers = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            String sql = "select * from volunteerdb order by event_name";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            // process result set
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("id");
                String event_name = myRs.getString("event_name");
                String event_target = myRs.getString("event_target");
                String event_date = myRs.getString("event_date");
                String group_category = myRs.getString("group_category");
                String event_type = myRs.getString("event_type");

                // create new student object
                Volunteer tempVolunteer = new Volunteer(id, event_name, event_target,
                        event_date, group_category, event_type);

                // add it to the list of students
                volunteers.add(tempVolunteer);
            }

            return volunteers;
        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    public void addVolunteer(Volunteer theVolunteer) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;
        InputStream fin2 = theVolunteer.getIMGphoto().getInputstream();
        System.out.println(fin2);
        try {
            myConn = getConnection();

            String sql = "insert into volunteerdb (event_name, event_target, event_date, group_category, event_type, IMGphoto) values (?, ?, ?, ?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setString(1, theVolunteer.getEvent_name());
            myStmt.setString(2, theVolunteer.getEvent_target());
            myStmt.setString(3, theVolunteer.getEvent_date());
            myStmt.setString(4, theVolunteer.getGroup_category());
            myStmt.setString(5, theVolunteer.getEvent_type());
            myStmt.setBinaryStream(6, fin2, theVolunteer.getIMGphoto().getSize());
            myStmt.execute();
        } finally {
            close(myConn, myStmt);
        }

    }

    public Volunteer getVolunteer(int volunteerId) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            String sql = "select * from volunteerdb where id=?";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, volunteerId);

            myRs = myStmt.executeQuery();

            Volunteer theVolunteer = null;

            // retrieve data from result set row
            if (myRs.next()) {
                int id = myRs.getInt("id");
                String event_name = myRs.getString("event_name");
                String event_target = myRs.getString("event_target");
                String event_date = myRs.getString("event_date");
                String group_category = myRs.getString("group_category");
                String event_type = myRs.getString("event_type");
                ByteArrayInputStream myByteArray = new ByteArrayInputStream(myRs.getBytes("IMGphoto"));

                //StreamedContent ActualImage = new DefaultStreamedContent(new ByteArrayInputStream(myRs.getBytes("IMGphoto")));

                theVolunteer = new Volunteer(id, event_name, event_target,
                        event_date, group_category, event_type, myByteArray);
            } else {
                throw new Exception("Could not find student id: " + volunteerId);
            }

            return theVolunteer;
        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    public void updateVolunteer(Volunteer theVolunteer) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();

            String sql = "update volunteerdb " + " set event_name=?, event_target=?, event_date=?, group_category=?, event_type=?" + " where id=?";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setString(1, theVolunteer.getEvent_name());
            myStmt.setString(2, theVolunteer.getEvent_target());
            myStmt.setString(3, theVolunteer.getEvent_date());
            myStmt.setString(4, theVolunteer.getGroup_category());
            myStmt.setString(5, theVolunteer.getEvent_type());
            myStmt.setInt(6, theVolunteer.getId());

            myStmt.execute();
        } finally {
            close(myConn, myStmt);
        }

    }

    public void deleteVolunteer(int volunteerId) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();

            String sql = "delete from volunteerdb where id=?";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, volunteerId);

            myStmt.execute();
        } finally {
            close(myConn, myStmt);
        }
    }

    public void getSearch(String PASSIN) throws Exception{


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

    public List<Volunteer> searchVolunteers(String theSearchName)  throws Exception {

        List<Volunteer> volunteers = new ArrayList<>();

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int studentId;

        try {

            // get connection to database
            myConn = dataSource.getConnection();

            //
            // only search by name if theSearchName is not empty
            //
            if (theSearchName != null && theSearchName.trim().length() > 0) {

                // create sql to search for students by name
                String sql = "select * from volunteerdb where lower(event_name) like ? or lower(event_date) like ?";

                // create prepared statement
                myStmt = myConn.prepareStatement(sql);

                // set params
                String theSearchNameLike = "%" + theSearchName.toLowerCase() + "%";
                myStmt.setString(1, theSearchNameLike);
                myStmt.setString(2, theSearchNameLike);

            } else {
                // create sql to get all students
                String sql = "select * from volunteerdb order by event_name";

                // create prepared statement
                myStmt = myConn.prepareStatement(sql);
            }

            // execute statement
            myRs = myStmt.executeQuery();

            // retrieve data from result set row
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("id");
                String event_name = myRs.getString("event_name");
                String event_target = myRs.getString("event_target");
                String event_date = myRs.getString("event_date");
                String group_category = myRs.getString("group_category");
                String event_type = myRs.getString("event_type");

                // create new student object
                Volunteer tempVolunteer = new Volunteer(id, event_name, event_target,
                        event_date, group_category, event_type);

                // add it to the list of students
                volunteers.add(tempVolunteer);
            }

            return volunteers;
        }
        finally {
            // clean up JDBC objects
            close(myConn, myStmt, myRs);
        }
    }
}