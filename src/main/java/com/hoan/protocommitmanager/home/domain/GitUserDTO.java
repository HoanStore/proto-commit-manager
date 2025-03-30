package com.hoan.protocommitmanager.home.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitUserDTO {
    private String name;
    private String email;
    private String password;
    private String url;
    private String message;
    private String branch;
    private String patchFilePath;

    private List<MultipartFile> fileLists;
}
