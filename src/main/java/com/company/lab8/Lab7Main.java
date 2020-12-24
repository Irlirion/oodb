package com.company.lab8;

import com.company.lab8.entities.Community;

import java.io.FileReader;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Lab7Main {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileReader("src/main/resources/connection.properties"));
        EntityManagerFactory factory = new EntityManagerFactory(properties);
        System.out.println(factory.isDbValid());

        IEntityManager<Long> entityManager = factory.createEM();
        Community e = new Community("root", "test desc");
        Class<Community> entityClass = Community.class;
        entityManager.persist(e);

        List<Community> collect = entityManager.findAll(entityClass).stream().map(entity -> (Community) entity).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println(entityManager.findAll(entityClass).size());

        e.setId(2L);
        entityManager.merge(e);
        collect = entityManager.findAll(entityClass).stream().map(entity -> (Community) entity).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println(entityManager.findAll(entityClass).size());

        entityManager.remove(e);
        collect = entityManager.findAll(entityClass).stream().map(entity -> (Community) entity).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println(entityManager.findAll(entityClass).size());

        System.out.println((Community) entityManager.find(entityClass, 4L));
        entityManager.refresh(e);
        e.setId(5L);
        System.out.println(e);
    }
}

