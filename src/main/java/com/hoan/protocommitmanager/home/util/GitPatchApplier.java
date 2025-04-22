package com.hoan.protocommitmanager.home.util;

import com.hoan.protocommitmanager.home.domain.GitUserDTO;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

@Component
public class GitPatchApplier {

    public void patchApply(GitUserDTO gitUserDTO, String paramLocalPath) {
        String USERNAME = gitUserDTO.getName();
        String TOKEN = gitUserDTO.getPassword();  // PAT 사용
        String USEREMAIL = gitUserDTO.getEmail();
        String localPath = paramLocalPath;
        String patchFile = gitUserDTO.getPatchFilePath();

        try {
            // 환경 변수 설정: 사용자 이름과 PAT 자동 입력
            System.setProperty("GIT_ASKPASS", "echo");
            System.setProperty("GIT_USERNAME", USERNAME);
            System.setProperty("GIT_PASSWORD", TOKEN);  // PAT를 환경 변수로 설정

            // 1️⃣ Git 사용자 정보 설정
            runCommandInDirectory(localPath, "git", "config", "--local", "user.name", USERNAME);
            runCommandInDirectory(localPath, "git", "config", "--local", "user.email", USEREMAIL);

            // 2️⃣ 원격 저장소 URL 설정 (username:token 방식 사용하지 않음)
            String remoteUrl = "https://github.com/HoanStore/proto-commit-manager.git";
            runCommandInDirectory(localPath, "git", "remote", "set-url", "origin", remoteUrl);

            // 3️⃣ Git 패치 파일 적용
            runCommandInDirectory(localPath, "git", "apply", patchFile);

            // 4️⃣ 변경 사항 스테이징 (git add .)
            runCommandInDirectory(localPath, "git", "add", ".");

            // 5️⃣ 변경 사항 커밋
            runCommandInDirectory(localPath, "git", "commit", "-am", "예약된 커밋");

            // 6️⃣ 변경 사항 푸시
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


    /**
     * PAT을 받을 떄,
     * 임시적으로 해당 정보랑 계정 이름으로
     * 계정 정보를 구성해야 함!
     */
    public void test() {
        // GitHub 리포지토리 URL과 사용자 정보
        String repoUrl = "https://github.com/HoanStore/proto-commit-manager.git";
        String username = "조근완";  // GitHub 사용자 이름
        String token = "your_personal_access_token";  // GitHub Personal Access Token

        // 인증 제공자 생성
        UsernamePasswordCredentialsProvider credentialsProvider =
                new UsernamePasswordCredentialsProvider(username, token);

        try {
            // Git 클론 실행
            Git.cloneRepository()
                    .setURI(repoUrl)
                    .setCredentialsProvider(credentialsProvider)
                    .setDirectory(new java.io.File("/path/to/clone/repo"))
                    .call();

            System.out.println("Repository cloned successfully!");

        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }
}


