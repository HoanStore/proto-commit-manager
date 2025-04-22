package com.hoan.protocommitmanager.home.service;

import com.hoan.protocommitmanager.common.domain.FileDetailVO;
import com.hoan.protocommitmanager.common.domain.FileVO;
import com.hoan.protocommitmanager.common.enums.FileMgmtDetailType;
import com.hoan.protocommitmanager.common.enums.MgmtTypes;
import com.hoan.protocommitmanager.common.service.CommonService;
import com.hoan.protocommitmanager.home.config.GitLocalPathProperties;
import com.hoan.protocommitmanager.home.domain.GitUserDTO;
import com.hoan.protocommitmanager.home.util.GitCleaner;
import com.hoan.protocommitmanager.home.util.GitCredentialApplier;
import com.hoan.protocommitmanager.home.util.GitInit;
import com.hoan.protocommitmanager.home.util.GitPatchApplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@RequiredArgsConstructor
@Service
public class GitRequestServiceImpl implements GitRequestService {

    private final GitLocalPathProperties gitLocalPathProperties;

    private final GitInit gitInit;
    private final GitCleaner gitCleaner;

    private final GitCredentialApplier gitCredentialApplier;
    private final GitPatchApplier gitPatchApplier;
    private final CommonService commonService;

    @Override
    public boolean isGitRepo(String localPath) {
        File gitDir = new File(localPath, ".git");
        return gitDir.exists() && gitDir.isDirectory();
    }

    @Override
    public void gitClean(String localPath) {
        gitCleaner.cleanGitRepository(localPath);
    }

    @Override
    public void gitInit(GitUserDTO gitUserDTO, String localPath) {
        gitInit.init(gitUserDTO, localPath);
    }


    @Override
    public void registerReservationGitCommit(GitUserDTO gitUserDTO) {
        String attflId = commonService.registerFiles(gitUserDTO.getFileLists(), FileVO.builder().mgmtType(MgmtTypes.EXAMPLES.getValue())
                .fileMgmtDetailType(FileMgmtDetailType.COMMON_IMAGE)
                .attflIstc("")
                .build());

        System.out.println("attflId = " + attflId);

        FileDetailVO fileDetailVO = commonService.selectFileNmAndPath(attflId, 1L);

        System.out.println("fileDetailVO = " + fileDetailVO);

        gitUserDTO.setPatchFilePath(fileDetailVO.getFileSavePath()+"/"+fileDetailVO.getSaveFileNm());
        String localPath = gitLocalPathProperties.getPath();
        // 정상적으로 pull 해 오는지까지만 확인
        gitInit.init(gitUserDTO, localPath);

        gitCredentialApplier.configureGitCredentials(gitUserDTO.getName(), gitUserDTO.getPassword());
        gitPatchApplier.patchApply(gitUserDTO,localPath);

    }
}
