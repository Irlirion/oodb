package com.company.lab4;

import com.company.lab4.domain.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lab2Main {
    private static final String[] firstnames = new String[]{"Ilya", "Nikita", "Dima", "Vova", "Anton", "Zhenya", "Sasha", "Kosty"};
    private static final String[] lastnames = new String[]{"Vasilyev", "Gun", "Bum", "Lon", "Non", "Pam", "Can", "Put", "Get", "Van"};

    public static int getRandom(int length) {
        return new Random().nextInt(length);
    }

    public static List<User> generateUsers(int count) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            users.add(new User(firstnames[getRandom(firstnames.length)], lastnames[getRandom(lastnames.length)]));
        }
        for (User user :
                users) {
            for (int i = 0; i < count / 10; i++) {
                user.addFriend(new User(firstnames[getRandom(firstnames.length)], lastnames[getRandom(lastnames.length)]));
            }
        }
        return users;
    }

    public static void main(String[] args) {
        try {
            JDBCPostgreSQLConnection app = new JDBCPostgreSQLConnection();
            Connection connect = app.connect();

            System.out.println("Generating users");
            List<User> users = generateUsers(1000);

            clearTable(connect);
            System.out.println("Save users to table:");
            Lab2SaveDB.saveUsersList(users, connect, "json");
            Lab2SaveDB.saveUsersList(users, connect, "jsonb");

            System.out.println("Load users from table:");
            Lab2LoadDB.loadUsersList(connect, "json", "");
            Lab2LoadDB.loadUsersList(connect, "jsonb", "");

            System.out.println("Load sorted users:");
            Lab2LoadDB.loadUsersListSorted(connect, "json", "firstName");
            Lab2LoadDB.loadUsersListSorted(connect, "jsonb", "firstName");

            System.out.println("Find users by firstName:");
            Lab2LoadDB.loadUsersList(connect, "json", "where (json->>'firstName') = 'Anton'");
            Lab2LoadDB.loadUsersList(connect, "jsonb", "where (jsonb->>'firstName') = 'Anton'");

            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void clearTable(Connection connect) throws SQLException {
        Statement statement = connect.createStatement();
        statement.execute("truncate table test RESTART IDENTITY");
        statement.close();
        System.out.println("Table truncated");
    }

}
