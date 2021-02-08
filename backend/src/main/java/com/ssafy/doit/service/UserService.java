package com.ssafy.doit.service;

import com.ssafy.doit.model.request.RequestChangePw;
import com.ssafy.doit.model.response.ResponseUser;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private S3Service s3Service;

    public Long currentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        Long userPk = userRepository.findByEmail(userDetails.getUsername()).get().getId();

        return  userPk;
    }

    // 회원 상세 정보
    @Transactional
    public ResponseUser detailUser(Long userPk){
        User getUser = userRepository.findById(userPk).get();
        ResponseUser user = new ResponseUser(getUser);
        return user;
    }

    // 회원정보 수정
    public void updateUser(Long userPk, User userReq, MultipartFile file){
        System.out.println("ㅅㅄㅂ");
        Optional<User> user = userRepository.findById(userPk);

        user.ifPresent(selectUser->{
            String imgPath = null;


                try {
                    System.out.println("tryㅅㅄㅂ");
                    imgPath = s3Service.upload(userReq.getImage(),file);
                } catch (IOException e) {
                    System.out.println("catchㅅㅄㅂ");
                    e.printStackTrace();
                }
                selectUser.setImage(imgPath);


            selectUser.setNickname(userReq.getNickname());
            userRepository.save(selectUser);
        });
    }


}
