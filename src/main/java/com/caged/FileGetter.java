package com.caged;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

class FileGetter {

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
}