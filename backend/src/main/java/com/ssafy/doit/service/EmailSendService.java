package com.ssafy.doit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
public class EmailSendService {
    @Autowired
    private JavaMailSenderImpl mailSender;
    private int size;

    //인증키 생성
    private String getKey(int size) {
        this.size = size;
        return getAuthCode();
    }

    //인증코드 난수 발생
    private String getAuthCode() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;

        while(buffer.length() < size) {
            num = random.nextInt(10);
            buffer.append(num);
        }
        return buffer.toString();
    }

    //회원가입 인증메일 보내기
    public String sendSignupMail(String email) {
        //6자리 난수 인증번호 생성
        String authKey = getKey(6);
        //인증메일 보내기
        try {
            MailUtil sendMail = new MailUtil(mailSender);
            sendMail.setSubject("회원가입 이메일 인증");
            sendMail.setText(new StringBuffer().append("<h1>[이메일 인증]</h1>")
                    .append("<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>")
                    .append("<a href='http://localhost:3000/user/signupEmail?email=") // *** 나중에 뷰로 보내기
                    .append(email)
                    .append("&authKey=")
                    .append(authKey)
                    .append("' target='_blenk'>회원가입 이메일 인증 확인</a>")
                    .toString());
            sendMail.setFrom("buhee1029@gmail.com", "관리자");
            sendMail.setTo(email);
            sendMail.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authKey;
    }

    // 비번변경 인증메일 보내기
    public String sendChangePwMail(String email) {
        //6자리 난수 인증번호 생성
        String authKey = getKey(6);
        //인증메일 보내기
        try {
            MailUtil sendMail = new MailUtil(mailSender);
            sendMail.setSubject("비밀번호 변경 링크");
            sendMail.setText(new StringBuffer().append("<h1>[비밀번호 변경을 위한 이메일 인증]</h1>")
                    .append("<p>아래 링크를 클릭하시면 비밀번호 변경페이지로 이동됩니다.</p>")
                    .append("<a href='http://localhost:8080/") // 비밀번호 변경 페이지로 이동
                    .append(email)
                    .append("&authKey=")
                    .append(authKey)
                    .append("' target='_blenk'>비밀번호 변경 이메일 인증 확인</a>")
                    .toString());
            sendMail.setFrom("buhee1029@gmail.com", "관리자");
            sendMail.setTo(email);
            sendMail.send();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return authKey;
    }
}

