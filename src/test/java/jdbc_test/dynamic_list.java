package jdbc_test;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dynamic_list {

    String dbUrl = "jdbc:oracle:thin:@54.91.11.180:1521:XE";
    String dbUsername = "hr";
    String dbPassword = "hr";

    @Test
    public void test1() throws SQLException {

        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");

        //in order to get column names we need resultSetMetaData
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        //list of maps to keep all information
        List<Map<String,Object>> queryData = new ArrayList<>();

        //number of columns
        int colCount = resultSetMetaData.getColumnCount();

        //loop through each row
        while (resultSet.next()){

            Map<String,Object> row = new HashMap<>();

            //some code to fill the dynamically
            for (int i = 1; i <= colCount ; i++) {
                row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
            }


            //add ready map row to the list
            queryData.add(row);

        }

        //print each row inside the list
        for (Map<String, Object> row : queryData) {
            System.out.println(row.toString());
        }

        //close connection
        resultSet.close();
        statement.close();
        connection.close();


    }

}