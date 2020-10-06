package com.company.lab2;

import com.company.lab2.domain.Community;
import com.company.lab2.domain.SocialNetwork;
import com.company.lab2.domain.User;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class Lab2Main {

    public static void main(String[] args) {
        try {
            User user1 = new User("Ilya", "Vasilyev");
            User user2 = new User("Nikita", "Gun");
            User user3 = new User("Dima", "Bum");
            User user4 = new User("Vova", "Lon");
            User user5 = new User("Anton", "Non");

            Community community1 = new Community("programming", "This community from programmers to programmers");
            community1.addUser(user1);
            community1.addUser(user2);
            community1.addUser(user3);
            community1.addUser(user4);
            community1.addUser(user5);

            community1.addAdmin(user1);

            SocialNetwork socialNetwork = new SocialNetwork("vk");
            socialNetwork.addCommunity(community1);
            socialNetwork.setUsers(community1.getUsers());

            Lab2SaveDB.saveSocianNetwork(socialNetwork);

            SocialNetwork socialNetwork1 = Lab2LoadDB.load();

            System.out.println(socialNetwork1);

            List<User> userList = socialNetwork1.getCommunities().get(0).getUsers();
            userList.sort(Comparator.comparing(User::getFirstName));
            userList.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
