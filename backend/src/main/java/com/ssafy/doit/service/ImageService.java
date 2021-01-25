package com.ssafy.doit.service;

import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final UserRepository userRepository;

    @Transactional
    public void imgUpdate(String email, String image) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 회원 없습니다. email =" + email));

        user.setImage(image);

        save_u(user);
    }

    public void save_u(User user) {
        userRepository.save(user);
    }

}
