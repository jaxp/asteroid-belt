package com.pallas.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/11/27 13:59
 * @desc:
 */
public class FileUtils {

    private static final String BASE_DIRECTORY = "files";

    public static final String getRoot() {
        return Paths.get("").toAbsolutePath().toString().concat(File.separator);
    }

    public static final String getPath(String... directorys) {
        StringBuilder path = new StringBuilder(BASE_DIRECTORY);
        if (Objects.nonNull(directorys)) {
            for (String directory : directorys) {
                path.append(File.separator).append(directory);
            }
        }
        Calendar calendar = Calendar.getInstance();
        path.append(File.separator)
            .append(calendar.get(Calendar.YEAR))
            .append(String.format("%02d", (calendar.get(Calendar.MONTH) + 1)))
            .append(String.format("%02d", (calendar.get(Calendar.DAY_OF_MONTH) + 1)));
        return path.toString();
    }

    public static final String createPath(String... directorys) throws IOException {
        String path = getPath(directorys);
        createDirectory(path);
        return path;
    }

    public static final String writeFile(String fileName, byte[] content, String... directorys) throws IOException {
        String filePath = createFile(fileName, directorys);
        Path path = Paths.get(getRoot() + filePath);
        Files.write(path, content);
        return filePath;
    }

    public static final String createFile(String fileName, String... directorys) throws IOException {
        String path = getPath(directorys);
        createDirectory(path);
        String filePath = new StringBuilder(path).append(File.separator).append(fileName).toString();
        Path fileStorePath = Paths.get(getRoot() + filePath);
        if (!fileStorePath.toFile().exists()) {
            Files.createFile(fileStorePath);
        }
        return filePath;
    }

    public static final void createDirectory(String path) throws IOException {
        Path directoryPath = Paths.get(getRoot() + path);
        if (!directoryPath.toFile().exists()) {
            Files.createDirectories(directoryPath);
        }
    }

}
