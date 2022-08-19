package jdbc_test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class jdbc_examples {

    String dbUrl = "jdbc:oracle:thin:@54.91.11.180:1521:XE";
    String dbUsername = "hr";
    String dbPassword = "hr";

    @Test
    public void test1() throws SQLException {

        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT  * from departments");

        /*
        //move to first row
        resultSet.next();
        System.out.println(resultSet.getString(2));
         */


        //display departments table in 10 - Administration - 200 - 1700 format
        //code for iterating for each row
        while (resultSet.next()){
            System.out.println(resultSet.getInt(1) + " - " + resultSet.getString(2) + " - "
                    + resultSet.getInt(3) + " - " + resultSet.getInt(4));
        }

        resultSet = statement.executeQuery("SELECT  * from regions");

        while (resultSet.next()){
            System.out.println(resultSet.getInt(1) + " - " + resultSet.getString(2));
        }

        //close connection
        resultSet.close();
        statement.close();
        connection.close();

    }
    @DisplayName("ResultSet Methods")
    @Test
    public void test2() throws SQLException {

        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("SELECT  * from departments");

        //how to find how many rows we have for the query
        // move to last row
        resultSet.last();

        //get the row count
        int rowCount = resultSet.getRow();
        System.out.println("rowCount = " + rowCount);

        //to move before first row after we use ".last" method
        resultSet.beforeFirst();

        //print all second column information
        while(resultSet.next()){
            System.out.println(resultSet.getString(2));
        }

        //close connection
        resultSet.close();
        statement.close();
        connection.close();

    }


    @Test
    public void test3() throws SQLException {

        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("SELECT  * from employees");

        //get the database related data inside the DatabaseMetaData object
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        System.out.println("databaseMetaData.getUserName() = " + databaseMetaData.getUserName());
        System.out.println("databaseMetaData.getDatabaseProductName() = " + databaseMetaData.getDatabaseProductName());
        System.out.println("databaseMetaData.getDatabaseProductVersion() = " + databaseMetaData.getDatabaseProductVersion());
        System.out.println("databaseMetaData.getDriverName() = " + databaseMetaData.getDriverName());
        System.out.println("databaseMetaData.getDriverVersion() = " + databaseMetaData.getDriverVersion());

        //get the resultSetMetaData
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        //how many colums we have?
        int colCount = resultSetMetaData.getColumnCount();
        System.out.println("colCount = " + colCount);

        //getting column names
        System.out.println("ColumnName(1) = " + resultSetMetaData.getColumnName(1));
        System.out.println("ColumnName(2) = " + resultSetMetaData.getColumnName(2));

        //resultSetMetaData.getColumnName(i) --> gets column name
        //resultSetMetaData.getColumnCount() --> total number of columns9
        //print all the column names dynamically
        for (int i = 1; i <= colCount; i++) {
            System.out.println(resultSetMetaData.getColumnName(i));
        }

        //close connection
        resultSet.close();
        statement.close();
        connection.close();

    }

}
