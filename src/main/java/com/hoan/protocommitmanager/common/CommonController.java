package com.hoan.protocommitmanager.common;


import com.hoan.protocommitmanager.common.domain.FileDetailVO;
import com.hoan.protocommitmanager.common.service.CommonService;
import com.hoan.protocommitmanager.common.util.file.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@Slf4j
@RequestMapping("/cmm")
@Controller
@RequiredArgsConstructor
public class CommonController {

    private final CommonService commonService;
    private final FileUtil fileUtil;


    @GetMapping("/getFile")
    public ResponseEntity getFile(@RequestParam String attflId, @RequestParam Long attflSeq,
                                  @RequestParam(defaultValue = "N") String isThumbnail) throws IOException {
        FileDetailVO fileDetailVO = commonService.selectFileNmAndPath(attflId, attflSeq);
        return fileUtil.getFile(fileDetailVO, isThumbnail);
    }
}
