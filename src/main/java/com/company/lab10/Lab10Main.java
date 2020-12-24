package com.company.lab10;

import com.company.lab10.domain.Community;
import com.company.lab10.domain.SocialNetwork;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Lab10Main {

    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("lab9");
        EntityManager entityManager = emf.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        SocialNetwork socialNetwork = new SocialNetwork();
        socialNetwork.setName("VK");

        entityManager.persist(socialNetwork);

        Community community = new Community();
        community.setName("First");
        community.setDescription("Second");
        community.setSocialNetwork(socialNetwork);

        entityManager.persist(community);

        transaction.commit();

        entityManager.close();
        emf.close();
    }
}