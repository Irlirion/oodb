package com.company.lab4;

import com.company.lab4.domain.SocialNetwork;
import com.company.lab4.domain.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class Lab2SaveDB {
    public static void saveSocianNetwork(SocialNetwork socialNetwork, Connection connect) throws IOException, SQLException {

        if (socialNetwork != null) {
            Gson gson = new Gson();
            String socialAsJson = gson.toJson(socialNetwork);

            PreparedStatement statement = connect.
                    prepareStatement("insert into test (json, jsonb) values ( cast( ? as json) , cast( ? as json) )");

            statement.setString(1, socialAsJson);
            statement.setString(2, socialAsJson);

            int count = statement.executeUpdate();

            System.out.println(count + " records added!");

            statement.close();

        }
    }

    public static void saveUsersList(List<User> users, Connection connect, String column) throws SQLException {
        connect.setAutoCommit(false);
        if (users != null && users.size() > 0) {
            Gson gson = new Gson();
            PreparedStatement statement = connect.
                    prepareStatement(String.format("insert into test (%s) values (cast( ? as %s))", column, column));

            String userAsJson;
            for (User user :
                    users) {
                userAsJson = gson.toJson(user);

                statement.setString(1, userAsJson);
                statement.addBatch();
            }

            // The returned int array store insert sql affected record counts.
            Instant start = Instant.now();
            int[] successCount = statement.executeBatch();
            Instant finish = Instant.now();
            String elapsedTime = NumberFormat.getNumberInstance().format(Duration.between(start, finish).toMillis());
            System.out.printf("%s milliseconds to save %s\n", elapsedTime, column);

            int successCountLen = successCount.length;

            connect.commit();
            connect.setAutoCommit(true);
            statement.close();

            System.out.println(successCountLen + " records added!\n");
        }
    }
}
