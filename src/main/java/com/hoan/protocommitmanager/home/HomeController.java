package com.hoan.protocommitmanager.home;

import com.hoan.protocommitmanager.home.domain.GitUserDTO;
import com.hoan.protocommitmanager.home.service.GitRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@RequestMapping({"", "/"})
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final GitRequestService gitRequestService;

    @GetMapping({"", "/", "/home"})
    public String homePage() {
        return "home/home";
    }

    @PostMapping("/register")
    public ResponseEntity registerExample(@ModelAttribute GitUserDTO gitUserDTO) {
        HashMap<String,Object> response = new HashMap<>();

        try {
            gitRequestService.registerReservationGitCommit(gitUserDTO);
            response.put("isSuccess", true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("isSuccess", false);
        }

        return ResponseEntity.ok(response);
    }

}
