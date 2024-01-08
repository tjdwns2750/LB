package com.team2.lb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team2.lb.service.MailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MailController {
	
	private final MailService mailService;

    @GetMapping("mail")
    public String MailPage(){
        return "mail/Main";
    }

    @ResponseBody
    @PostMapping("mail")
    public String MailSend(String mail){
    	log.debug("mail:{}", mail);

       int number = mailService.sendMail(mail);

       String num = "" + number;

       return num;
    }


}
