package sample;

import java.sql.*;

public class Datasource {

    public static final String CONNECTION_STRING = "jdbc:sqlite:/home/nubaf/IdeaProjects/LibraryManagement/src/sample/lib.db";
    private static Connection connection;

    public static void open() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void disconnect() {
       try {
            connection.close();
       } catch (SQLException e) {

       }
    }
    //Insert,Delete,
    public static void executeQuery(String str) throws SQLException{
        Statement statement=null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(str);
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        finally {
            if (statement!=null) statement.close();
        }
    }
    public static ResultSet dbExecute(String str){
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt=connection.createStatement();
            rs=stmt.executeQuery(str);
            return rs;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
