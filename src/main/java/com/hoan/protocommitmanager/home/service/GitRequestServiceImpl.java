package com.hoan.protocommitmanager.home.service;

import com.hoan.protocommitmanager.common.domain.FileDetailVO;
import com.hoan.protocommitmanager.common.domain.FileVO;
import com.hoan.protocommitmanager.common.enums.FileMgmtDetailType;
import com.hoan.protocommitmanager.common.enums.MgmtTypes;
import com.hoan.protocommitmanager.common.service.CommonService;
import com.hoan.protocommitmanager.home.domain.GitUserDTO;
import com.hoan.protocommitmanager.home.util.GitInit;
import com.hoan.protocommitmanager.home.util.GitPatchApplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GitRequestServiceImpl implements GitRequestService {

    private final GitInit gitInit;
    private final GitPatchApplier gitPatchApplier;
    private final CommonService commonService;

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
        String localPath = "/Users/keunwan/hoan_workspace/commit-manager-store/localBranch"; // 저장할 로컬 경로
        // 정상적으로 pull 해 오는지까지만 확인
        gitInit.init(gitUserDTO, localPath);
        gitPatchApplier.patchApply(gitUserDTO,localPath);

    }
}
