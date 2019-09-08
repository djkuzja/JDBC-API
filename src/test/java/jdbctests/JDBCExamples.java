package jdbctests;

import org.junit.Test;

import java.sql.*;

public class JDBCExamples {
    public final String ORACLE_DB_URL = "jdbc:oracle:thin:@3.84.120.216:1521:xe";
    public final String ORACLE_DB_USERNAME = "hr";
    public final String ORACLE_DB_PASSWORD = "hr";

    @Test
    public void readRegionNames() throws SQLException {
        Connection connection = DriverManager.getConnection(ORACLE_DB_URL, ORACLE_DB_USERNAME, ORACLE_DB_PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM regions");
        //loop through region


        while (resultSet.next()) {
            int rID = resultSet.getInt("region_id");
            String rname = resultSet.getString(2);
            // System.out.println(resultSet.getString("region_name"));
            System.out.println(rID + "|" + rname);
        }
        // resultSet = statement.executeQuery()


        resultSet.close();
        statement.close();
        connection.close();
    }

    @Test
    public void countAndNavigations() throws SQLException {
        Connection connection = DriverManager.getConnection(ORACLE_DB_URL, ORACLE_DB_USERNAME, ORACLE_DB_PASSWORD);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM departments");
        resultSet.last(); // move pointer to the last row
        int rowCount = resultSet.getRow();
        System.out.println("Number of Records " + rowCount);
        System.out.println("Number of Departments " + rowCount);

        resultSet.beforeFirst();
        while (resultSet.next()) {
            int depID = resultSet.getInt("department_id");
            String depNAme = resultSet.getString("department_name");
            System.out.println(depID + "|" + depNAme);
        }

        
        //loop through region


        resultSet.close();
        statement.close();
        connection.close();
    }

}
