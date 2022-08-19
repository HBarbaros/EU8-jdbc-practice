package spartanDataBase_withOscar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestConnection {

    // connection string
    String dbURL ="jdbc:oracle:thin:@44.202.119.26:1521:xe";
    String dbUsername = "SP";
    String dbPassword = "SP";
    String query = "select name from spartans";
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    @BeforeEach
    public void connectDB() throws SQLException {
        connection = DriverManager.getConnection(dbURL,dbUsername,dbPassword); //step 1 connect
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY); // 2. for sending query I need statement object
        resultSet = statement.executeQuery(query); // resultSet

    }

    @AfterEach
    public void closeDB() throws SQLException {
        connection.close();
        statement.close();
        resultSet.close();
    }

    @Test
    public void test1() throws SQLException {
        resultSet.next(); // move cursor to next row
        int row = resultSet.getRow();
        System.out.println("row = " + row);

        // go to last row and get rowNumber : for this one we need to configure statement object insensitive
        resultSet.last();
        System.out.println("resultSet.getRow() = " + resultSet.getRow());

    }

    @Test
    public void test2() throws SQLException {

        // resultSet.next(); // if there is next row goes there and returns boolean

        DatabaseMetaData databaseMetaData = connection.getMetaData();
        System.out.println("databaseMetaData = " + databaseMetaData.getDriverName());
        System.out.println("databaseMetaData = " + databaseMetaData.getDatabaseProductVersion());

        ResultSetMetaData rsmd = resultSet.getMetaData(); // more information about
        String columnName = rsmd.getColumnName(1);
        System.out.println("columnName = " + columnName);

        int columnCount = rsmd.getColumnCount();

        List<Map<String, Object>> queryList = new ArrayList<>();
        while (resultSet.next()){ // to handle each row data
            Map<String, Object> eachRow = new HashMap<>();
            for (int i=1; i <= columnCount; i++){ // to handle each column information
                eachRow.put(rsmd.getColumnName(i),resultSet.getObject(i));
            }
            queryList.add(eachRow);
        }

        System.out.println(queryList);

    }

}
