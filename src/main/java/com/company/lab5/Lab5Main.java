package com.company.lab5;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Lab5Main {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            String dbURL = "jdbc:postgresql://0.0.0.0:5432/testdb";
            connection = DriverManager.getConnection(dbURL, "testusr", "password");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        assert connection != null;
        truncateData(connection);
        addData(connection);
        showData(connection);
        editData(connection);
        System.out.println("\n\t\t======================= Data changed =============================\n");
        showData(connection);
    }

    private static void truncateData(Connection connection) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.execute("truncate \"user\", \"friend\" restart identity;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void showData(Connection connection) {
        PreparedStatement statementUser;
        try {
            statementUser = connection.prepareStatement(
                    "SELECT id,client from \"user\"");
            ResultSet users = statementUser.executeQuery();
            while (users.next()) {
                System.out.println(
                        users.getString(2)
                );
                System.out.print("Friends: ");
                for (String friend :
                        getFriends(connection, users.getInt(1))) {
                    System.out.print(friend + " ");
                }
                System.out.println("\n");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static ArrayList<String> getFriends(Connection connection, int userID) {
        ArrayList<String> result = new ArrayList<>();
        try {
            PreparedStatement statementMovies = connection.prepareStatement(
                    "select u2.client " +
                            "from \"user\" u " +
                            "join friend f on u.id = f.user_id " +
                            "join \"user\" u2 on u2.id = f.friend_id " +
                            "where u.id = ?;");
            statementMovies.setInt(1, userID);
            ResultSet resultSet = statementMovies.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static void addData(Connection connection) {
        addUsers(connection);
        addFriends(connection);
    }

    private static void addUsers(Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO \"user\" (client) values ((?,?,?,?))");

            statement.setString(1, "IVAN");
            statement.setString(2, "IVANOV");
            statement.setString(3, "ivan.ivanov@gmail.com");
            statement.setString(4, "88005553535");
            statement.executeUpdate();

            statement.setString(1, "ANDREY");
            statement.setString(2, "ANDREEV");
            statement.setString(3, "andrey.andreev@gmail.com");
            statement.setString(4, "88001441551");
            statement.executeUpdate();

            statement.setString(1, "MAX");
            statement.setString(2, "MAXIMOV");
            statement.setString(3, "max.maximov@gmail.com");
            statement.setString(4, "88001441552");
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addFriends(Connection connection) {
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO \"friend\" VALUES (?,?)");
            statement.setInt(1, 1);
            statement.setInt(2, 2);
            statement.executeUpdate();

            statement.setInt(1, 2);
            statement.setInt(2, 1);
            statement.executeUpdate();

            statement.setInt(1, 3);
            statement.setInt(2, 1);
            statement.executeUpdate();

            statement.setInt(1, 3);
            statement.setInt(2, 2);
            statement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static void editData(Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE \"user\" SET client = row(?, ?, ?, ?) where \"user\".id=1");
            statement.setString(1, "EGOR");
            statement.setString(2, "EGOROV");
            statement.setString(3, "egor.egorov@gmail.com");
            statement.setString(4, "88009951213");
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
