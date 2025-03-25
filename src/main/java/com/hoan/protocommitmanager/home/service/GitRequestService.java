package com.hoan.protocommitmanager.home.service;

import com.hoan.protocommitmanager.home.domain.GitUserDTO;

public interface GitRequestService {

    void registerReservationGitCommit(GitUserDTO gitUserDTO);
}
