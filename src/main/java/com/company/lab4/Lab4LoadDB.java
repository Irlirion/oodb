package com.company.lab4;

import com.company.lab4.domain.SocialNetwork;
import com.company.lab4.domain.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Lab4LoadDB {

    public static SocialNetwork load(Connection connect) throws IOException, SQLException {
        SocialNetwork socialNetwork;
        String pStr = "";

        PreparedStatement statement =
                connect.prepareStatement("select jsonb from test ");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            pStr = resultSet.getString("jsonb");
            System.out.println(pStr);
        }

        statement.close();

        socialNetwork = new Gson().fromJson(pStr, SocialNetwork.class);

        return socialNetwork;
    }

    public static List<User> loadUsersList(Connection connect, String column, String suffix) throws SQLException {
        String pStr = "";
        List<User> userList = new ArrayList<>();
        Gson gson = new Gson();

        PreparedStatement statement =
                connect.prepareStatement(String.format("select %s from test %s", column, suffix));

        Instant start = Instant.now();
        ResultSet resultSet = statement.executeQuery();
        Instant finish = Instant.now();
        String elapsedTime = NumberFormat.getNumberInstance().format(Duration.between(start, finish).toMillis());
        System.out.printf("%s milliseconds to load %s\n", elapsedTime, column);

        while (resultSet.next()) {
            pStr = resultSet.getString(column);
            // System.out.println(pStr);

            userList.add(gson.fromJson(pStr, User.class));
        }

        statement.close();
        System.out.println(userList.size() + " records loaded!\n");

        return userList;

    }

    public static List<User> loadUsersListSorted(Connection connect, String column, String sortName) throws SQLException {
        String suffix = String.format("order by (%s ->> '%s')", column, sortName);
        return loadUsersList(connect, column, suffix);
    }
}
