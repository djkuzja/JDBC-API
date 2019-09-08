package jdbctests;

import Utility.ConfigurationReader;
import Utility.DBType;

import Utility.DBUtility;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class jdbcWithUtility {
    @BeforeClass
    public static void setUp() {
        DBUtility.establishConnection(DBType.ORACLE);
    }

    @Test
    public void getEmployeesCount() {
        int employeesCount = DBUtility.getRowsCount("SELECT * FROM EMPLOYEES");
        System.out.println(employeesCount);
        assertEquals(122, employeesCount);
    }

    @Test
    public void testEmployees() {
        String sql = "select first_name, last_name, salary from employees where salary BETWEEN 5000 AND 6000";
        int ecount = DBUtility.getRowsCount(sql);
        List<Map<String, Object>> empData = DBUtility.runSQLQuery(sql);
        System.out.println(empData.toString());

        Map<String, Object> firstEmployee = empData.get(0);
        System.out.println("First employee " + firstEmployee);

        System.out.println("firstName: " + firstEmployee.get("FIRST_NAME"));
        System.out.println("lastName: " + firstEmployee.get("LAST_NAME"));
        System.out.println("job id: " + firstEmployee.get("JOB_ID"));
        System.out.println("salary: " + firstEmployee.get("SALARY"));
    }

    @Test
    public void regionsPojoTest() throws SQLException {
        List<Region> regions = new ArrayList<>();

        Connection connection = DriverManager.getConnection(ConfigurationReader.getProperty("oracledb.url"),
                ConfigurationReader.getProperty("oracledb.user"),
                ConfigurationReader.getProperty("oracledb.password"));
        PreparedStatement preparedStatement = connection.prepareStatement("select * from regions",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int region_id = resultSet.getInt(1);
            String region_name = resultSet.getString(2);
            regions.add(new Region(region_id, region_name));

        }
        System.out.println(regions.toString());
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @AfterClass
    public static void cleanUP() {
        DBUtility.closeConnections();
    }
}
