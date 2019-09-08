package jdbctests;

import Utility.DBUtility;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class PreparedStatmentExamples {

    @Test
    public void preparedStatmentExamples() {
        Connection connection = null;
        List<String> countries = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtility.getConnection();
            preparedStatement = connection.prepareStatement("select * from countries Where region_id = ?");
            preparedStatement.setInt(1, 2);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                countries.add(resultSet.getString("country_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        assertEquals(8, countries.size());
        System.out.println(countries.toString());
    }

    @Test
    public void preparedStatmentScroll() {
        String query = "select * from countries where region_id in (?,?)";
        // String query = "select * from countries where region_id in (2,4)";
        Connection connection = null;
        Map<String, String> countries = new TreeMap<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtility.getConnection();
            preparedStatement = connection.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
            preparedStatement.setInt(1, 2);
            preparedStatement.setInt(2, 4);
            resultSet = preparedStatement.executeQuery();

            int rows = resultSet.getRow();
            resultSet.last();
            resultSet.beforeFirst();

            while (resultSet.next()) {
                countries.put( resultSet.getString("country_id"), resultSet.getString("country_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        assertEquals(14, countries.size());
        System.out.println(countries.toString());
        assertEquals("Canada", countries.get("CA"));
        for (String s : countries.keySet()) {
            System.out.println(s+" || "+ countries.get(s));
        }
    }
    @Test
    public void preparedStatmentExamples1() throws SQLException {

        String query = "select * from countries where region_id in (?,?)";
        Connection connection = DBUtility.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
        preparedStatement.setInt(1, 2);
        preparedStatement.setInt(2, 4);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.afterLast();
        Map<String, String> countries = new TreeMap<>();

        while(resultSet.previous()){
            countries.put( resultSet.getString("country_id"), resultSet.getString("country_name"));
        }

        for (String s : countries.keySet()) {
            System.out.println(s+"||"+countries.get(s));


        }
        assertEquals(14, countries.size());


        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

}
