package jdbctests;

import java.sql.*;

public class Main {
    public Main() {
    }

    public static void main(String[] args) throws SQLException {
        String oracledbURL = "jdbc:oracle:thin:@3.84.120.216:1521:xe";
        String oracledbUsername = "hr";
        String oracledbpassword = "hr";


        Connection connection = DriverManager.getConnection(oracledbURL, oracledbUsername, oracledbpassword);
        Statement statement = connection.createStatement();
        // Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM regions");
        resultSet.next();

        System.out.println(resultSet.getString("REGION_ID"));

        resultSet.close();
        statement.close();
        connection.close();

    }
}
