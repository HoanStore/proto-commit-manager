package com.hoan.protocommitmanager.home.util;

import com.hoan.protocommitmanager.home.domain.GitUserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GitInitTest {


    @Autowired
    private GitInit gitInit;

    @Autowired
    private GitPatchApplier gitPatchApplier;

//    @DisplayName("최초로 수행됨 : 원격 URL에서 clone하거나 이미 존재한다면 pull을 수행합니다.")
//    @Test
//    public void gitInitTest() {
//        GitUserDTO requestDTO = GitUserDTO.builder().url("https://github.com/HoanStore/proto-commit-manager.git").build();
//        gitInit.init(requestDTO);
//    }
//
//    @DisplayName("patch 파일을 바탕으로 commit을 로컬 브랜치에 반영하고, 원격 브랜치에 push까지 진행합니다. ")
//    @Test
//    public void gitPatchApplierTest() {
//        GitUserDTO gitUserDTO = GitUserDTO.builder().name("hoan").password("12345").email("test@naver.com").build();
//        gitPatchApplier.patchApply(gitUserDTO);
//    }


}
