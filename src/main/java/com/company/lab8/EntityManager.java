package com.company.lab8;

import com.company.lab6.annotations.Column;
import com.company.lab6.annotations.Id;
import com.company.lab6.annotations.Relation;
import com.company.lab6.annotations.RelationType;
import org.postgresql.util.PSQLException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class EntityManager implements IEntityManager<Long> {
    HashMap<String, Boolean> typeSet = new HashMap<String, Boolean>();
    private Connection connection;
    private HashMap<String, HashSet<String>> tables;

    public EntityManager(Connection connection) {
        this.connection = connection;
        typeSet.put("String", true);
        typeSet.put("Double", false);
        typeSet.put("Integer", false);
        typeSet.put("Long", false);
    }

    public void setTables(HashMap<String, HashSet<String>> tables) {
        this.tables = tables;
    }

    @Override
    public void persist(Entity<Long> entity) {
        StringBuilder sql = new StringBuilder();
        List<String> values = new ArrayList<>();
        List<String> types = new ArrayList<>();

        int p = entity.getClass().getName().lastIndexOf('.');
        int l = entity.getClass().getName().length();
        String tableName = entity.getClass().getName().substring(p + 1, l).toLowerCase();

        sql.append("INSERT INTO ").append(tableName).append(" (");

        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation a : annotations) {
                if (a.annotationType().equals(Column.class)) {
                    try {
                        Method method = entity.getClass().getMethod(
                                "get" + field.getName().substring(0, 1).toUpperCase() +
                                        field.getName().substring(1), null);
                        //System.out.println("Сформировали метод: " + method.getName());

                        String value = null;
                        if (!typeSet.containsKey(field.getType().toString().split("\\.")[field.getType().toString().split("\\.").length - 1])) {
                            try {
                                method.invoke(entity, null);
                                sql.append(field.getName()).append(", ");
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                value = String.valueOf(method.invoke(entity, null));
                                sql.append(field.getName()).append(", ");
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                        //System.out.println(value);
                        values.add(value);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    types.add(field.getType().toString().split("\\.")[field.getType().toString().split("\\.").length - 1]);
                } else if (a.annotationType().equals(Relation.class) && field.getAnnotation(Relation.class).type().equals(RelationType.MANY_TO_ONE)) {
                    sql.append(field.getName()).append("_id, ");
                    types.add(field.getType().toString().split("\\.")[field.getType().toString().split("\\.").length - 1] + "_id");
                    // System.out.println(types);
                    Method method = null;
                    try {
                        method = entity.getClass().getMethod(
                                "get" + field.getName().substring(0, 1).toUpperCase() +
                                        field.getName().substring(1), null);
                        Object ob = method.invoke(entity, null);
                        Method method2 = ob.getClass().getMethod(
                                "getId", null);
                        values.add(String.valueOf(method2.invoke(ob, null)));
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 2) + ") values (");
        for (int i = 0; i < types.size(); i++) {
            try {
                if (typeSet.get(types.get(i))) {
                    sql.append("'").append(values.get(i)).append("', ");
                } else if (!typeSet.get(types.get(i))) {
                    sql.append(values.get(i)).append(", ");
                }
            } catch (Exception e) {
                sql.append(values.get(i)).append(", ");
            }
        }
        //System.out.println(types.toString());
        sql = new StringBuilder(sql.substring(0, sql.length() - 2) + ")");

        try {
            connection.prepareStatement(sql.toString()).execute();
            // System.out.println(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Entity<Long> merge(Entity<Long> entity) {
        String id = null;
        String sql = "";
        List<String> values = new ArrayList<>();
        List<String> types = new ArrayList<>();

        int p = entity.getClass().getName().lastIndexOf('.');
        int l = entity.getClass().getName().length();
        String tableName = entity.getClass().getName().substring(p + 1, l).toLowerCase();

        sql = sql + "UPDATE " + tableName + " SET ";
        try {
            Method method2 = entity.getClass().getMethod(
                    "getId", null);
            id = String.valueOf(method2.invoke(entity, null));
        } catch (Exception e) {
        }

        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation a : annotations) {
                if (a.annotationType().equals(Column.class) || a.annotationType().equals(Id.class)) {
                    try {
                        Method method = entity.getClass().getMethod(
                                "get" + field.getName().substring(0, 1).toUpperCase() +
                                        field.getName().substring(1), null);

                        String value = null;
                        if (!typeSet.containsKey(field.getType().toString().split("\\.")[field.getType().toString().split("\\.").length - 1])) {
                            try {
                                method.invoke(entity, null);
                                sql = sql + field.getName() + "=" + value + ", ";
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                value = String.valueOf(method.invoke(entity, null));
                                if (typeSet.get(field.getType().toString().split("\\.")[field.getType().toString().split("\\.").length - 1])) {
                                    sql = sql + field.getName() + "='" + value + "', ";
                                } else {
                                    sql = sql + field.getName() + "=" + value + ", ";
                                }
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }

                        values.add(value);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    types.add(field.getType().toString().split("\\.")[field.getType().toString().split("\\.").length - 1]);
                } else if (a.annotationType().equals(Relation.class) && field.getAnnotation(Relation.class).type().equals(RelationType.MANY_TO_ONE)) {

                    types.add(field.getType().toString().split("\\.")[field.getType().toString().split("\\.").length - 1] + "_id");
                    // System.out.println(types);
                    Method method = null;
                    try {
                        method = entity.getClass().getMethod(
                                "get" + field.getName().substring(0, 1).toUpperCase() +
                                        field.getName().substring(1), null);
                        Object ob = method.invoke(entity, null);
                        Method method2 = ob.getClass().getMethod(
                                "getId", null);
                        values.add(String.valueOf(method2.invoke(ob, null)));
                        sql = sql + field.getName() + "_id =" + String.valueOf(method2.invoke(ob, null) + ", ");
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        sql = sql.substring(0, sql.length() - 2) + " WHERE id=" + id;

        try {
            connection.prepareStatement(sql).execute();
            // System.out.println(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // System.out.println(sql);
        return null;
    }

    @Override
    public void remove(Entity<Long> entity) {
        Number id = entity.getId();
        String tableName = entity.getClass().getSimpleName().toLowerCase();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM \"" + tableName + "\" WHERE id=?");
            preparedStatement.setLong(1, id.longValue());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Entity<Long>> findAll(Class<?> entityClass) {
        ArrayList<Entity<Long>> result = new ArrayList<>();
        String tableName = entityClass.getSimpleName().toLowerCase();

        StringBuilder builder = new StringBuilder();
        ArrayList<Field> fields = new ArrayList<>();
        for (Field declaredField : entityClass.getDeclaredFields()) {
            if (!declaredField.isAnnotationPresent(Relation.class) || !(declaredField.getAnnotation(Relation.class).type().equals(RelationType.ONE_TO_MANY) &&
                    declaredField.getAnnotation(Relation.class).type().equals(RelationType.MANY_TO_ONE))
            ) {
                String s = declaredField.getName().toLowerCase();
                builder.append(s);
                fields.add(declaredField);
            }
            builder.append(",");
        }
        builder.deleteCharAt(builder.lastIndexOf(","));
        String columns = builder.toString();
        PreparedStatement selectStatement = null;
        try {
            selectStatement = connection.prepareStatement("SELECT " + columns + " FROM \"" + tableName + "\"");
            ResultSet resultSet = selectStatement.executeQuery();

            Constructor<?> constructor = entityClass.getDeclaredConstructor(null);
            while (resultSet.next()) {
                Object entity = constructor.newInstance(null);
                for (int i = 0; i < fields.size(); i++) {
                    String name = fields.get(i).getName();
                    name = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                    Class<?> type = fields.get(i).getType();
                    Method setter = entityClass.getMethod(
                            name,
                            type);
                    try {
                        setter.invoke(entity, resultSet.getObject(i + 1, type));
                    } catch (PSQLException ignored) {

                    }
                }
                result.add((Entity<Long>) entity);
            }
        } catch (SQLException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    @Override
    public Entity<Long> find(Class<?> entityClass, Long id) {
        Object objectOfClass = null;

        try {
            Class<?> clazz = Class.forName(String.valueOf(entityClass).split(" ")[1].trim());
            Constructor con = clazz.getConstructor();
            objectOfClass = con.newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }

        String tableName = entityClass.getSimpleName().toLowerCase();

        String sql = "SELECT * FROM " + tableName + " WHERE id=" + id;
        try {
            ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next()) {
                for (int j = 0; j < resultSetMetaData.getColumnCount(); j++) {
                    String columnName = resultSetMetaData.getColumnName(j + 1);
                    if (columnName.contains("_id")) {
                        String[] line = columnName.split("_");

                        Object columnValue = resultSet.getLong(columnName);
                        // System.out.println(columnValue);
                    } else {
                        Object columnValue = resultSet.getObject(columnName.toLowerCase());

                        String fieldNameForMethod = columnName.substring(0, 1).toUpperCase() +
                                columnName.substring(1);
                        Method method = null;
                        try {
                            if (columnValue.getClass() == String.class) {
                                method = objectOfClass.getClass().getMethod(
                                        "set" + fieldNameForMethod, String.class);
                                method.invoke(objectOfClass, columnValue);
                            } else if (columnValue.getClass() == Double.class) {
                                method = objectOfClass.getClass().getMethod(
                                        "set" + fieldNameForMethod, Double.class);
                                method.invoke(objectOfClass, columnValue);
                            } else if (columnValue.getClass() == Long.class) {
                                method = objectOfClass.getClass().getMethod(
                                        "set" + fieldNameForMethod, Long.class);
                                method.invoke(objectOfClass, columnValue);
                            } else if (columnValue.getClass() == int.class) {
                                method = objectOfClass.getClass().getMethod(
                                        "set" + fieldNameForMethod, Integer.class);
                                method.invoke(objectOfClass, columnValue);
                            }
                        } catch (Exception e) {
                        }

                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // System.out.println(sql);
        return (Entity<Long>) objectOfClass;
    }

    @Override
    public void refresh(Entity<Long> entity) {
        Long id = null;
        try {
            Method method2 = entity.getClass().getMethod(
                    "getId", null);
            id = (Long) method2.invoke(entity, null);
        } catch (Exception e) {
        }
        entity = find(entity.getClass(), id);
    }
}
