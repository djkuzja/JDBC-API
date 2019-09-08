package jdbctests;


import Utility.DBUtility;
import org.junit.Test;

import java.sql.*;
import java.util.Map;
import java.util.TreeMap;

public class MetaData {
    @Test
    public void  databaseMetadata(){
        Connection connection = null;
        Map<String, String> countries = new TreeMap<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtility.getConnection();
            preparedStatement = connection.prepareStatement("select * from countries where region_id in (2,4)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
            resultSet = preparedStatement.executeQuery();
            DatabaseMetaData dbmd = connection.getMetaData();

            System.out.println("Database type: "+dbmd.getDatabaseProductName());
            System.out.println("Database version: "+dbmd.getDatabaseProductVersion());
            System.out.println("User: "+dbmd.getURL());
            System.out.println("Get schema: "+ dbmd.getSchemas());

            ResultSetMetaData rsMD = resultSet.getMetaData();
            System.out.println("ColumnCount: "+rsMD.getColumnCount());
            System.out.println("ColumnName1: "+rsMD.getColumnName(1));
            System.out.println("ColumnName2: "+rsMD.getColumnName(2));
            System.out.println("ColumnName3: "+rsMD.getColumnName(3));
            System.out.println(rsMD.getColumnType(3));
            int rows = resultSet.getRow();
            resultSet.last();
            resultSet.beforeFirst();
            while (resultSet.next()) {
                countries.put( resultSet.getString("country_id"), resultSet.getString("country_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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
        }
}
