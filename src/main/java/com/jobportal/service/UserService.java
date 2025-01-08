package com.jobportal.service;

import com.jobportal.entity.JobSeekerProfile;
import com.jobportal.entity.RecruiterProfile;
import com.jobportal.entity.User;
import com.jobportal.repository.JobSeekerProfileRepository;
import com.jobportal.repository.RecruiterProfileRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RecruiterProfileRepository recruiterProfileRepository,
                       JobSeekerProfileRepository jobSeekerProfileRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createNewUser(User user){
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userSaved = userRepository.save(user);

        int userType = user.getUserTypeId().getUserTypeId();
        if(userType == 1){
            recruiterProfileRepository.save(new RecruiterProfile(userSaved));
        }else{
            jobSeekerProfileRepository.save(new JobSeekerProfile(userSaved));
        }


        return userSaved;
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }
}
