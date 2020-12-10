package com.company.lab6.analyze;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassAnalyzer {

    private final List<Class<?>> classes;
    private final Map<Path, OpenOption[]> paths;
    private boolean printInConsole;

    private ClassAnalyzer() {
        this(new ArrayList<>());
    }

    public ClassAnalyzer(Collection<Class<?>> classes) {
        this.classes = List.copyOf(classes);
        this.paths = new HashMap<>();
        this.printInConsole = true;
    }

    public ClassAnalyzer printInConsole(boolean printInConsole) {
        this.printInConsole = printInConsole;
        return this;
    }

    public ClassAnalyzer file(Path path, OpenOption... openOptions) {
        paths.put(path, openOptions);
        return this;
    }

    public void analyse() throws IOException {
        Set<PrintStream> printStreams = getPrintStreams();
        for (Class<?> searchClass : classes) {
            generalAnalyse(printStreams, searchClass);
            fieldsAnalyse(printStreams, searchClass);
            constructorsAnalyse(printStreams, searchClass);
            methodsAnalyse(printStreams, searchClass);
        }
    }

    private void methodsAnalyse(Set<PrintStream> printStreams, Class<?> searchClass) {
        Method[] methods = searchClass.getDeclaredMethods();
        for (PrintStream printStream : printStreams) {
            printStream.printf("Methods of class: %s%n%n", methods.length > 0 ? "" : "null");
        }

        for (Method method : methods) {
            methodAnalyse(printStreams, method);
        }
    }

    private void methodAnalyse(Set<PrintStream> printStreams, Method method) {
        Annotation[] annotations = method.getDeclaredAnnotations();
        String annotationString = Arrays.toString(annotations);

        for (PrintStream printStream : printStreams) {
            printStream.printf("\t%s %n", method);
            printStream.printf("\tAnnotations of method: %s %n%n", annotationString);
        }
    }

    private void constructorsAnalyse(Set<PrintStream> printStreams, Class<?> searchClass) {
        Constructor<?>[] constructors = searchClass.getDeclaredConstructors();
        for (PrintStream printStream : printStreams) {
            printStream.printf("Constructors of class: %s%n%n", constructors.length > 0 ? "" : "null");
        }

        for (Constructor<?> constructor : constructors) {
            constructorAnalyse(printStreams, constructor);
        }
    }

    private void constructorAnalyse(Set<PrintStream> printStreams, Constructor<?> constructor) {
        Annotation[] annotations = constructor.getDeclaredAnnotations();
        String annotationString = Arrays.toString(annotations);

        for (PrintStream printStream : printStreams) {
            printStream.printf("\t%s %n", constructor);
            printStream.printf("\tAnnotations of constructor: %s %n%n", annotationString);
        }
    }

    private void fieldsAnalyse(Set<PrintStream> printStreams, Class<?> searchClass) {
        Field[] fields = searchClass.getDeclaredFields();
        for (PrintStream printStream : printStreams) {
            printStream.printf("Field of class: %s%n%n", fields.length > 0 ? "" : "null");
        }

        for (Field field : fields) {
            fieldAnalyse(printStreams, field);
        }
    }

    private void fieldAnalyse(Set<PrintStream> printStreams, Field field) {
        Annotation[] annotations = field.getDeclaredAnnotations();
        String modifier = Modifier.toString(field.getModifiers());
        String annotationsString = Arrays.toString(annotations);

        for (PrintStream printStream : printStreams) {
            printStream.printf("\t%s %s %s%n", modifier, field.getType().getTypeName(), field.getName());
            printStream.printf("\tAnnotations of field: %s%n%n", annotationsString);
        }
    }

    private void generalAnalyse(Set<PrintStream> printStreams, Class<?> searchClass) {
        String infoClass = getInfoClass(searchClass);
        String infoAnnotations = getInfoAnnotations(searchClass);
        String infoSuperclass = getInfoSuperclass(searchClass);
        String infoInterfaces = getInfoInterfaces(searchClass);
        String additionalAttributes = getAdditionalAttributes(searchClass);

        for (PrintStream printStream : printStreams) {
            printStream.println("--------------");
            printStream.println("General info");
            printStream.printf("\t%s%n", infoClass);
            printStream.printf("\t%s%n", infoAnnotations);
            printStream.printf("\t%s%n", infoSuperclass);
            printStream.printf("\t%s%n", infoInterfaces);
            printStream.printf("\t%s%n", additionalAttributes);
        }
    }

    private String getAdditionalAttributes(Class<?> searchClass) {
        StringBuilder builder = new StringBuilder("Additional attributes: ");
        if (searchClass.isArray()) {
            builder.append(String.format("%n\t\tIt's array class, component type class - %s", getFullClassName(searchClass.getComponentType())));
        }

        if (searchClass.isAnonymousClass()) {
            builder.append("\n\t\tIt's anonymous class");
        }

        if (searchClass.isEnum()) {
            builder.append("\n\t\tIt's enum class");
        }

        Class<?> nestHost = searchClass.getNestHost();
        if (searchClass.isLocalClass()) {
            builder.append(String.format("%n\t\tIt's local class, located in class %s", getFullClassName(nestHost)));

        }

        if (searchClass.isMemberClass()) {
            builder.append(String.format("%n\t\tIt's member of class %s", getFullClassName(nestHost)));
        }

        if (searchClass.isPrimitive()) {
            builder.append("\n\t\tIt's primitive");
        }

        if (searchClass.isSynthetic()) {
            builder.append("\n\t\tIt's synthetic class (probably, lambda-function)");
        }

        builder.append("\n\t\tInner classes: ");
        Class<?>[] declaredClasses = searchClass.getDeclaredClasses();


        for (Class<?> declareClass : declaredClasses) {
            builder.append(getFullClassName(declareClass)).append(", ");
        }

        if (declaredClasses.length == 0) {
            builder.append("null");
        } else {
            int length = builder.length();
            builder.delete(length - 2, length);
        }


        return builder.toString();
    }

    private String getInfoInterfaces(Class<?> searchClass) {
        String info = "Interfaces: %s";
        Class<?>[] interfaces = searchClass.getInterfaces();
        Type[] genericInterfaces = searchClass.getGenericInterfaces();

        String[] infoInterfaces = getInterfacesMatching(interfaces, genericInterfaces);
        return String.format(info, Arrays.toString(infoInterfaces));
    }

    private String[] getInterfacesMatching(Class<?>[] interfaces, Type[] genericInterfaces) {
        Pattern pattern = Pattern.compile("([\\w.]+)(<.++>)?");

        Map<String, Class<?>> genericInterfacesMap = new HashMap<>();
        for (Class<?> classInterface : interfaces) {
            genericInterfacesMap.put(classInterface.getName(), classInterface);
        }

        String[] strings = new String[genericInterfacesMap.size()];

        for (int i = 0; i < genericInterfaces.length; i++) {
            String typeName = genericInterfaces[i].getTypeName();
            Matcher matcher = pattern.matcher(typeName);
            if (matcher.find()) {
                strings[i] = String.format("%s %s", getModifier(genericInterfacesMap.get(matcher.group(1))), typeName);
            }
        }

        return strings;
    }

    private String getModifier(Class<?> searchClass) {
        return Modifier.toString(searchClass.getModifiers());
    }

    private String getInfoSuperclass(Class<?> searchClass) {
        String info = "Superclass: %s";
        Type genericSuperclass = searchClass.getGenericSuperclass();
        Class<?> superclass = searchClass.getSuperclass();
        if (Objects.nonNull(genericSuperclass)) {
            if (genericSuperclass instanceof Class<?>) {
                Class<?> typeClass = (Class<?>) genericSuperclass;
                return String.format(info, getFullClassName(typeClass));
            } else {
                return String.format(info + " class %s", getModifier(superclass), genericSuperclass.getTypeName());
            }
        } else {
            return String.format(info, getFullClassName(superclass));
        }
    }

    private String getInfoClass(Class<?> searchClass) {
        return String.format("Class: %s", getFullClassName(searchClass));
    }

    private String getFullClassName(Class<?> searchClass) {
        if (searchClass == null) {
            return "null";
        }
        return String.format("%s %s", getModifier(searchClass), searchClass);
    }

    private String getInfoAnnotations(Class<?> searchClass) {
        String info = "Annotations: %s";
        Annotation[] declaredAnnotations = searchClass.getDeclaredAnnotations();

        if (declaredAnnotations.length == 0) {
            return String.format(info, "null");
        } else {
            return String.format(info, Arrays.toString(declaredAnnotations));
        }
    }

    private Set<PrintStream> getPrintStreams() throws IOException {
        Set<PrintStream> printStreams = new HashSet<>(paths.size() + 1);
        for (Map.Entry<Path, OpenOption[]> path : paths.entrySet()) {
            printStreams.add(new PrintStream(Files.newOutputStream(path.getKey(), path.getValue())));
        }

        if (printInConsole) {
            printStreams.add(System.out);
        }

        return printStreams;
    }
}
