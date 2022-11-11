package com.caged;

import java.io.InputStream;
import java.net.URL;

public class FileGetter {

    public InputStream fileGetter(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    public InputStream yamlGetter(String fileName) {

            InputStream input = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream(fileName);
        if (input == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return input;
        }
    }

    public URL imageGetter(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();
        URL image = classLoader.getResource(fileName);

        // the stream holding the file content
        if (image == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return image;
        }
    }
}