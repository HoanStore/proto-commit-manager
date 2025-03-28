package com.hoan.protocommitmanager.home.util;

import com.hoan.protocommitmanager.home.domain.GitUserDTO;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class GitInit {

    public void init(GitUserDTO gitUserDTO) {
        String localPath = "/Users/keunwan/hoan_workspace/commit-store"; // 저장할 로컬 경로
        String repoUrl = gitUserDTO.getUrl();

        File repoDir = new File(localPath);
        File gitDir = new File(localPath + "/.git");

        try {
            if (!repoDir.exists() || repoDir.list().length == 0) {
                // localPath가 없거나 비어있으면 git clone 실행
                System.out.println("No existing files. Cloning repository...");
                runCommand("git", "clone", repoUrl, localPath);
            } else if (gitDir.exists()) {
                // .git 폴더가 있으면 git pull 실행
                System.out.println("Existing Git repository found. Pulling updates...");
                runCommandInDirectory(localPath, "git", "pull");
            } else {
                // localPath에 파일이 있지만 Git 저장소가 아닌 경우 경고
                System.err.println("Error: The directory exists but is not a Git repository.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void runCommand(String... command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        printProcessOutput(process);

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            System.err.println("Command failed: " + String.join(" ", command) + " (Exit code: " + exitCode + ")");
        }
    }

    private void runCommandInDirectory(String directory, String... command) throws IOException, InterruptedException {
        File dir = new File(directory);
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("Error: Specified directory does not exist - " + directory);
            return;
        }
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(dir);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        printProcessOutput(process);
    }

    private void printProcessOutput(Process process) throws IOException, InterruptedException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        process.waitFor();
    }
}
