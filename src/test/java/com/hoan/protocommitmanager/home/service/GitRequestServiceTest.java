package com.hoan.protocommitmanager.home.service;

import com.hoan.protocommitmanager.home.domain.GitUserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GitRequestServiceTest {

    @Autowired
    private GitRequestService gitRequestService;

    private final static String TEST_LOCAL_PATH = "/Users/keunwan/hoan_workspace/commit-manager-store/localBranch";
    private final static String TEST_URL = "https://github.com/HoanStore/proto-commit-manager.git";




//    @Test
//    public void gitInit() {
//        boolean gitRepo = gitRequestService.isGitRepo(TEST_LOCAL_PATH);
//        System.out.println("gitRepo = " + gitRepo);
//
//        GitUserDTO gitUserDTO = GitUserDTO.builder().url(TEST_URL).build();
//        gitRequestService.gitInit(gitUserDTO, TEST_LOCAL_PATH);
//
//        gitRepo = gitRequestService.isGitRepo(TEST_LOCAL_PATH);
//        System.out.println("gitRepo = " + gitRepo);
//    }

    @Test
    public void gitCleaner() {

        // given
        GitUserDTO gitUserDTO = GitUserDTO.builder().url(TEST_URL).build();
        gitRequestService.gitInit(gitUserDTO, TEST_LOCAL_PATH);

        boolean gitRepo = gitRequestService.isGitRepo(TEST_LOCAL_PATH);
        Assertions.assertTrue(gitRepo);

        // when
        gitRequestService.gitClean(TEST_LOCAL_PATH);

        // then
        gitRepo = gitRequestService.isGitRepo(TEST_LOCAL_PATH);
        Assertions.assertFalse(gitRepo);
    }

}
