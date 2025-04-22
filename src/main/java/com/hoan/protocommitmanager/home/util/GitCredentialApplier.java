package com.hoan.protocommitmanager.home.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

@Component
public class GitCredentialApplier {

    public void configureGitCredentials(String username, String token) {
        try {
            // 1️⃣ ~/.git-credentials 경로 구성
            Path credentialsPath = Paths.get(System.getProperty("user.home"), ".git-credentials");

            // 2️⃣ 자격증명 문자열 생성
            String credentialsEntry = "https://" + username + ":" + token + "@github.com\n";

            // 3️⃣ 기존 내용과 중복 방지 후 작성
            if (!Files.exists(credentialsPath) || !Files.readAllLines(credentialsPath).contains(credentialsEntry.trim())) {
                Files.write(credentialsPath, credentialsEntry.getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }

            // 4️⃣ 파일 권한 제한 (Linux/Unix 환경)
            setFilePermission600(credentialsPath);

            // 5️⃣ git config credential.helper store 설정
            runCommand("git", "config", "--global", "credential.helper", "store");

            System.out.println("✅ Git credentials 설정 완료");

        } catch (IOException e) {
            throw new RuntimeException("자격 증명 설정 중 오류 발생", e);
        }
    }

    private void runCommand(String... command) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("명령 실행 중 인터럽트 발생", e);
        }
    }

    private void setFilePermission600(Path path) throws IOException {
        try {
            Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-------");
            Files.setPosixFilePermissions(path, perms);
        } catch (UnsupportedOperationException e) {
            // Windows 등에서는 무시
        }
    }
}
