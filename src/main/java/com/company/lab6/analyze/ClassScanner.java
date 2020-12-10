package com.company.lab6.analyze;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ClassScanner {

    private final ClassLoader classLoader;
    private final Predicate<String> classFileName = Pattern.compile(".+\\.class").asMatchPredicate();

    public ClassScanner() {
        this.classLoader = Thread.currentThread().getContextClassLoader();
    }

    public List<Class<?>> get(URI uri) throws URISyntaxException, IOException {
        String uriString = uri.toASCIIString();
        URL url = classLoader.getResource(uriString);

        List<Class<?>> classes = new ArrayList<>();
        String packageClass = uriString.replace('\\', '.').replace('/', '.');

        assert url != null;
        Path mainPath = Path.of(url.toURI());
        Files.walk(mainPath)
                .filter(path -> classFileName.test(path.toString()))
                .map(path -> {
                    String fileName = mainPath.relativize(path).toString().replace('/', '.').replace('\\', '.');
                    String filePathName = fileName.substring(0, fileName.length() - 6);
                    try {
                        return Class.forName(packageClass + "." + filePathName);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .forEach(classes::add);

        return classes;
    }
}
