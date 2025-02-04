package com.jobportal.controller;

import com.jobportal.entity.RecruiterProfile;
import com.jobportal.entity.User;
import com.jobportal.repository.UserRepository;
import com.jobportal.service.RecruiterProfileService;
import com.jobportal.service.util.FileUploadUtil;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private UserRepository userRepository;
    private RecruiterProfileService recruiterProfileService;

    public RecruiterProfileController(UserRepository userRepository, RecruiterProfileService recruiterProfileService) {
        this.userRepository = userRepository;
        this.recruiterProfileService = recruiterProfileService;
    }

    @GetMapping("/")
    public String recruiterProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();
            User user = userRepository.findByEmail(currentUsername)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOneRecruiterProfile(user.getUserId());
            if(!recruiterProfile.isEmpty()){
                model.addAttribute("profile", recruiterProfile);
            }
        }
        return "recruiter_profile";
    }

    @PostMapping("/addNew")
    public String addNew(RecruiterProfile recruiterProfile, @Param("image")MultipartFile multipartFile, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();
            User user = userRepository.findByEmail(currentUsername)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
            recruiterProfile.setUserId(user);
            recruiterProfile.setUserAccountId(user.getUserId());
        }
        model.addAttribute("profile", recruiterProfile);
        String fileName = "";
        if(!multipartFile.getOriginalFilename().equals("")){
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            recruiterProfile.setPhotoProfile(fileName);
        }

        RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);
        String uploadDir = "photos/recruiter/"+savedUser.getUserAccountId();
        try{
            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/dashboard/";
    }
}
