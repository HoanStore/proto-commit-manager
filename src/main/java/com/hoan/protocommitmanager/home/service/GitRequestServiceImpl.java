package com.hoan.protocommitmanager.home.service;

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


    @Override
    public void registerReservationGitCommit(GitUserDTO gitUserDTO) {

    }
}
