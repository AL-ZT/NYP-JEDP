package listings;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListingDbUtil {

    private static ListingDbUtil instance;
    private DataSource dataSource;
    private String jndiName = "jdbc/javaee_project";

    public static ListingDbUtil getInstance() throws Exception {
        if (instance == null) {
            instance = new ListingDbUtil();
        }
        return instance;
    }

    private ListingDbUtil() throws Exception {
        dataSource = getDataSource();
    }

    private DataSource getDataSource() throws NamingException {
        Context context = new InitialContext();
        DataSource theDataSource = (DataSource) context.lookup(jndiName);   //lookup connection pool
        return theDataSource;
    }

    public List<Listing> getListings() throws Exception {
        List<Listing> listings = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();
            String sql = "select * from listings";
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery(sql);

            while(myRs.next()){
                int itemId = myRs.getInt("id");
                String itemName = myRs.getString("itemName");
                String itemGrp = myRs.getString("itemGrp");
                String itemPrice = myRs.getString("itemPrice");
                Boolean expDeliv = myRs.getBoolean("expDeliv");
                String itemDesc = myRs.getString("itemDesc");

                Listing tempListing = new Listing(itemId, itemName, itemGrp, itemPrice, expDeliv, itemDesc);     //creating new temp object

                listings.add(tempListing);
            }
            return listings;
        }
        finally{
            close(myConn, myStmt, myRs);
        }

    }

    public Listing getListing(int listingId) throws Exception{
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try{
            myConn = getConnection();
            String sql = "select * from listings where id=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, listingId);
            myRs = myStmt.executeQuery();
            Listing theListing = null;

            if(myRs.next()){
                int id = myRs.getInt("id");
                String itemName = myRs.getString("itemName");
                String itemGrp = myRs.getString("itemGrp");
                String itemPrice = myRs.getString("itemPrice");
                Boolean expDeliv = myRs.getBoolean("expDeliv");
                String itemDesc = myRs.getString("itemDesc");

                theListing = new Listing(id, itemName, itemGrp, itemPrice, expDeliv, itemDesc);
            }
            else{
                throw new Exception("Could not find listing id: "+listingId);
            }
            System.out.println(theListing   );
            return theListing;
        }
        finally {
            close(myConn, myStmt, myRs);
        }
    }

    public void addListing(Listing theListing) throws Exception{
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try{
            myConn = getConnection();
            String sql = "insert into listings (itemName, itemGrp, itemPrice, expDeliv, itemDesc) values (?, ?, ?, ?, ?)";
            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1, theListing.getItemName());
            myStmt.setString(2, theListing.getItemGrp());
            myStmt.setString(3, theListing.getItemPrice());
            myStmt.setBoolean(4, theListing.getExpDeliv());
            myStmt.setString(5, theListing.getItemDesc());

            myStmt.execute();
        }finally {
            close(myConn, myStmt);
        }
    }

    public void updateListing(Listing theListing) throws Exception{
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try{
            myConn = getConnection();
            String sql = "update listings " + " set itemName=?, itemGrp=?, itemPrice=?, expDeliv=?, itemDesc=?" + " where id=?";
            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1, theListing.getItemName());
            myStmt.setString(2, theListing.getItemGrp());
            myStmt.setString(3, theListing.getItemPrice());
            myStmt.setBoolean(4, theListing.getExpDeliv());
            myStmt.setString(5, theListing.getItemDesc());
            myStmt.setInt(6, theListing.getItemID());

            myStmt.execute();
        }
        finally {
            close(myConn, myStmt);
        }
    }

    public void deleteListing(int listingId)throws Exception{
        Connection myConn = null;
        PreparedStatement myStmt = null;
        try{
            myConn = getConnection();
            String sql = "delete from listings where id=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, listingId);
            myStmt.execute();
        }finally {
            close(myConn, myStmt);
        }
    }

    //get connection
    private Connection getConnection() throws Exception {
        Connection theConn = dataSource.getConnection();
        return theConn;
    }
    //close connection
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

}