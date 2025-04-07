package com.hoan.protocommitmanager.home.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Component
public class GitCleaner {

    public void cleanGitRepository(String localPath) {
        Path targetPath = Paths.get(localPath);

        if (!Files.exists(targetPath) || !Files.isDirectory(targetPath)) {
            System.out.println("Target path does not exist or is not a directory: " + localPath);
            return;
        }

        try {
            deleteRecursively(targetPath);
            System.out.println("All contents under " + localPath + " deleted successfully.");
        } catch (IOException e) {
            System.err.println("Failed to delete contents of directory: " + e.getMessage());
        }
    }

    private void deleteRecursively(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (var entries = Files.list(path)) {
                for (Path entry : entries.toList()) {
                    deleteRecursively(entry);
                }
            }
        }
        Files.deleteIfExists(path);
    }
}
