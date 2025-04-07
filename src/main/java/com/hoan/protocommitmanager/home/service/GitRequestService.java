package com.hoan.protocommitmanager.home.service;

import com.hoan.protocommitmanager.home.domain.GitUserDTO;

public interface GitRequestService {

    boolean isGitRepo(String localPath);

    void gitClean(String localPath);

    void gitInit(GitUserDTO gitUserDTO, String localPath);

    void registerReservationGitCommit(GitUserDTO gitUserDTO);
}
