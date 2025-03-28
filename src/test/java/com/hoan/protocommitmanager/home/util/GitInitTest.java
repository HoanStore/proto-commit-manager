package com.hoan.protocommitmanager.home.util;

import com.hoan.protocommitmanager.home.domain.GitUserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GitInitTest {


    @Autowired
    private GitInit gitInit;


// const testCase1 = {
//                name: '조근완',
//                email: 'kwjo@mqnic.com',
//                password: 'password123',
//                url: 'https://github.com/HoanStore/proto-commit-manager.git',
//                message: 'Initial commit',
//                branch: 'main'
//            };
    @Test
    public void gitInitTest() {
        GitUserDTO requestDTO = GitUserDTO.builder().url("https://github.com/HoanStore/proto-commit-manager.git").build();
        gitInit.init(requestDTO);
    }
}
