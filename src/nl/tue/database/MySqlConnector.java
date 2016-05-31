package nl.tue.database;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class MySqlConnector {
    // init database constants
    private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/dbt";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String MAX_POOL = "250";

    // init connection object
    private Connection connection;
    // init properties object
    private Properties properties;

    // create properties
    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("MaxPooledStatements", MAX_POOL);
        }
        return properties;
    }

    // connect database
    public Connection connect() {
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, getProperties());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    // disconnect database
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static ArrayList<Table1> GetAll() {
        ArrayList<Table1> results = null;
        MySqlConnector mySqlConnector = new MySqlConnector();
        String sql = "SELECT * FROM `e`";
        try {

            PreparedStatement statement = mySqlConnector.connect().prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            results = new ArrayList<Table1>();
            while (rs.next()) {

                long id = rs.getLong("id");
                long source = rs.getLong("source");
                long label = rs.getLong("label");
                long target = rs.getLong("target");
                results.add(new Table1(id, source, label, target));

            }

            //  System.out.println(statement.execute());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySqlConnector.disconnect();
        }
        return results;
    }
    public static int GetPathQueryTwoCount(int one, int two) {
        int counter = 0;
        ArrayList<Table1> results = null;
        MySqlConnector mySqlConnector = new MySqlConnector();
        String sql = "SELECT A.source, B.target, COUNT(*)\n" +
                "FROM dbt.e as A \n" +
                "JOIN dbt.e as B \n" +
                "ON A.target=B.source \n" +
                "WHERE A.label=? AND B.label=?\n" +
                "GROUP BY A.source, B.target";
        try {

            PreparedStatement statement = mySqlConnector.connect().prepareStatement(sql);
            statement.setInt(1,one);
            statement.setInt(2,two);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                counter++;

            }

            //  System.out.println(statement.execute());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySqlConnector.disconnect();
        }
        return counter;
    }
    public static int GetPathQueryThreeCount(int one, int two, int three) {
        int counter = 0;
        ArrayList<Table1> results = null;
        MySqlConnector mySqlConnector = new MySqlConnector();
        String sql = "SELECT A.source, Z.target, COUNT(*)\n" +
                "FROM dbt.e as A\n" +
                "JOIN dbt.e as B\n" +
                "ON A.target=B.source\n" +
                "JOIN dbt.e as Z\n" +
                "ON B.target=Z.source\n" +
                "WHERE A.label= ? AND B.label= ? AND Z.label=? \n" +
                "GROUP BY A.source, Z.target";
        try {

            PreparedStatement statement = mySqlConnector.connect().prepareStatement(sql);
            statement.setInt(1,one);
            statement.setInt(2,two);
            statement.setInt(3,three);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

               counter++;

            }

            //  System.out.println(statement.execute());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySqlConnector.disconnect();
        }
        return counter;
    }
    public static int GetPathQueryFourCount(int one, int two, int three, int four) {
        int counter = 0;
        ArrayList<Table1> results = null;
        MySqlConnector mySqlConnector = new MySqlConnector();
        String sql = "SELECT A.source, F.target, COUNT(*)\n" +
                "FROM dbt.e as A\n" +
                "JOIN dbt.e as B\n" +
                "ON A.target=B.source\n" +
                "JOIN dbt.e as Z\n" +
                "ON B.target=Z.source\n" +
                "JOIN dbt.e as F\n" +
                "ON Z.target=F.source\n" +
                "WHERE A.label=? AND B.label=? AND Z.label=? AND F.label = ?\n" +
                "GROUP BY A.source, F.target";
        try {

            PreparedStatement statement = mySqlConnector.connect().prepareStatement(sql);
            statement.setInt(1,one);
            statement.setInt(2,two);
            statement.setInt(3,three);
            statement.setInt(4,four);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                counter++;

            }

            //  System.out.println(statement.execute());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySqlConnector.disconnect();
        }
        return counter;
    }
    public static int GetPathQueryFiveCount(int one, int two, int three, int four, int five) {
        int counter = 0;
        ArrayList<Table1> results = null;
        MySqlConnector mySqlConnector = new MySqlConnector();
        String sql = "SELECT A.source, K.target, COUNT(*) \n" +
                "FROM dbt.e as A \n" +
                "JOIN dbt.e as B\n" +
                "ON A.target=B.source\n" +
                "JOIN dbt.e as Z \n" +
                "ON B.target=Z.source\n" +
                "JOIN dbt.e as F \n" +
                "ON Z.target=F.source \n" +
                "JOIN dbt.e as K\n" +
                "on F.target=K.source\n" +
                "WHERE A.label=? AND B.label=? AND Z.label=? AND F.label = ? AND K.label = ?\n" +
                "GROUP BY A.source, K.target";
        try {

            PreparedStatement statement = mySqlConnector.connect().prepareStatement(sql);
            statement.setInt(1,one);
            statement.setInt(2,two);
            statement.setInt(3,three);
            statement.setInt(4,four);
            statement.setInt(5,five);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                counter++;

            }

            //  System.out.println(statement.execute());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySqlConnector.disconnect();
        }
        return counter;
    }
}
