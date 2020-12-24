package com.company.lab8;

import com.company.lab6.analyze.ClassScanner;
import com.company.lab6.annotations.Entity;
import com.company.lab6.annotations.Relation;
import com.company.lab6.annotations.RelationType;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static com.company.lab7.Lab6Main.getFields;
import static com.company.lab7.Lab6Main.getTables;


public class EntityManagerFactory {
    /**
     * HashMap for keeping database structure:
     * <p>
     * key — table name,
     * <p>
     * value — HashSet of table columns name
     */
    private final HashMap<String, HashSet<String>> tables = new HashMap<>();
    /**
     * Connection to database
     */
    private Connection connection;
    /**
     * Properties of database connection
     */
    private Properties properties;

    /**
     * EntityManager Factory default constructor
     *
     * @param properties properties of database connection
     */
    public EntityManagerFactory(Properties properties) {
        this.properties = properties;
    }

    public HashMap<String, HashSet<String>> getTablesScheme() {
        return tables;
    }

    /**
     * Method for creating {@linkplain EntityManager}
     *
     * @return new {@linkplain EntityManager} instance
     */
    public IEntityManager<Long> createEM() throws Exception {
        if (isDbValid()) {
            IEntityManager<Long> entityManager = new EntityManager(connection);
            entityManager.setTables(tables);
            return entityManager;
        } else throw new Exception("The database is not correct");
    }

    /**
     * Method to connect to database server
     */
    public void connect() {
        if (connection == null) {
            try {
                Class.forName(properties.getProperty("driver"));
                connection = DriverManager.getConnection(
                        properties.getProperty("url"),
                        properties.getProperty("user"),
                        properties.getProperty("password"));
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method for checking the database correctness.
     *
     * @return true if database is correct, else — false
     */
    public boolean isDbValid() throws IOException, URISyntaxException {
        connect();
        analyzeDB();
        List<Class<?>> classes = new ClassScanner().get(URI.create("com/company/lab8/entities"))
                .stream()
                .filter(aClass -> aClass.isAnnotationPresent(Entity.class))
                .collect(Collectors.toList());
        if (classes.size() != tables.size()) {
            return false;
        } else {
            for (Class<?> entity : classes) {
                for (Field field : entity.getDeclaredFields()) {
                    String fieldName = field.getName().toLowerCase();
                    if (field.isAnnotationPresent(Relation.class) && field.getAnnotation(Relation.class).type().equals(RelationType.ONE_TO_MANY)) {
                        Class<?> fieldClass = field.getType();
                        if (Collection.class.isAssignableFrom(fieldClass)) {
                            Type fieldGenericType = field.getGenericType();
                            if (fieldGenericType instanceof ParameterizedType) {
                                ParameterizedType paramType = (ParameterizedType) fieldGenericType;
                                String genericClassName = ((Class<?>) paramType.getActualTypeArguments()[0]).getSimpleName().toLowerCase();
                                if (!tables.get(genericClassName).contains(entity.getSimpleName().toLowerCase() + "_id")) {
                                    System.out.println(tables.get(genericClassName) + " " + entity.getSimpleName().toLowerCase() + "_id");
                                    return false;
                                }
                            }
                        }
                    } else if (field.isAnnotationPresent(Relation.class) && field.getAnnotation(Relation.class).type().equals(RelationType.MANY_TO_ONE)) {
                        if (!tables.get(entity.getSimpleName().toLowerCase()).contains(fieldName + "_id")) {
                            System.out.println(tables.get(entity.getSimpleName().toLowerCase()) + " " + fieldName + "_id");
                            return false;
                        }
                    } else if (!tables.get(entity.getSimpleName().toLowerCase()).contains(fieldName)) {
                        System.out.println(tables.get(entity.getSimpleName().toLowerCase()) + " " + fieldName);
                        return false;
                    }
                }
            }
            return true;
        }
    }

    /**
     * Method for analyzing database, creates connection to DB and fills {@linkplain EntityManagerFactory#tables}
     */
    private void analyzeDB() {
        connect();
        List<String> tablesList = getTables(connection);
        for (String table : tablesList) {
            List<String> fields = null;
            try {
                fields = getFields(connection, table);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            assert fields != null;
            HashSet<String> columns = new HashSet<>(fields);
            tables.put(table, columns);
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Connection getConnection() {
        connect();
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
