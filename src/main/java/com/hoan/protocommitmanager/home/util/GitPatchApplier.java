package com.hoan.protocommitmanager.home.util;

import com.hoan.protocommitmanager.home.domain.GitUserDTO;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

@Component
public class GitPatchApplier {

    public void patchApply(GitUserDTO gitUserDTO, String paramLocalPath) {

        String USERNAME = gitUserDTO.getName();
        String PASSWORD = gitUserDTO.getPassword();
        String USEREMAIL = gitUserDTO.getEmail();

        String localPath = paramLocalPath; // Git 로컬 저장소 경로
        String patchFile = gitUserDTO.getPatchFilePath();

        try {
            // 1️⃣ Git 사용자 정보 설정
            runCommandInDirectory(localPath, "git", "config", "--global", "user.name", USERNAME);
            runCommandInDirectory(localPath, "git", "config", "--global", "user.password", PASSWORD);
            runCommandInDirectory(localPath, "git", "config", "--global", "user.email", USEREMAIL);

            // 2️⃣ Git 패치 파일 적용
            runCommandInDirectory(localPath, "git", "apply", patchFile);

            // 3️⃣ 변경 사항 스테이징 (git add .)
            runCommandInDirectory(localPath, "git", "add", ".");

            // 4️⃣ 변경 사항 커밋
            runCommandInDirectory(localPath, "git", "commit", "-am", "예약된 커밋");

            // 5️⃣ 변경 사항 푸시
            runCommandInDirectory(localPath, "git", "push", "origin", "main");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Properties properties = loadProperties("/Users/keunwan/hoan_workspace/config.properties"); // 설정 파일 로드
        if (properties == null) {
            System.err.println("Error: Failed to load configuration.");
            return;
        }

        String USERNAME = properties.getProperty("USERNAME");
        String PASSWORD = properties.getProperty("PASSWORD");
        String USEREMAIL = properties.getProperty("USEREMAIL");

        String localPath = "/Users/keunwan/hoan_workspace/commit-store"; // Git 로컬 저장소 경로
        String patchFile = "/Users/keunwan/hoan_workspace/commit-manager/proto-commit-manager/patches/0001-chore-git-Ignore.patch"; // 패치 파일 경로

        try {
            // 1️⃣ Git 사용자 정보 설정
            runCommandInDirectory(localPath, "git", "config", "--global", "user.name", USERNAME);
            runCommandInDirectory(localPath, "git", "config", "--global", "user.password", PASSWORD);
            runCommandInDirectory(localPath, "git", "config", "--global", "user.email", USEREMAIL);

            // 2️⃣ Git 패치 파일 적용
            runCommandInDirectory(localPath, "git", "apply", patchFile);

            // 3️⃣ 변경 사항 스테이징 (git add .)
            runCommandInDirectory(localPath, "git", "add", ".");

            // 4️⃣ 변경 사항 커밋
            runCommandInDirectory(localPath, "git", "commit", "-am", "예약된 커밋");

            // 5️⃣ 변경 사항 푸시
            runCommandInDirectory(localPath, "git", "push", "origin", "main");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 🔹 설정 파일 로드 메서드
    private static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(fileName)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return properties;
    }

    // 🔹 명령 실행 메서드
    private static void runCommandInDirectory(String directory, String... command) throws IOException, InterruptedException {
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

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            System.err.println("Command failed: " + String.join(" ", command) + " (Exit code: " + exitCode + ")");
        }
    }

    // 🔹 프로세스 출력 로그 메서드
    private static void printProcessOutput(Process process) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}


