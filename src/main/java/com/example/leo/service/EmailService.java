package com.example.leo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.leo.entity.Order;
import com.example.leo.entity.User;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private Environment environment;

    public boolean testEmail(){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(environment.getProperty("to.email"));
            simpleMailMessage.setSubject("Testing email");
            simpleMailMessage.setText("Dear folk, is this testing email sent by Ecommerce application !");
            simpleMailMessage.setFrom(environment.getProperty("from.email"));

            javaMailSender.send(simpleMailMessage);

            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean registration(String appUrl, User user){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(user.getUsername());//should be email address
            simpleMailMessage.setSubject("Auto parts - Registration");

            String loginUrl = appUrl+"/login";

            simpleMailMessage.setText(
                    String.format("Dear %s, Thank you for registration with Ecommerce Application !." +
                                    "\n please login %s"
                    , user.getFullName(), loginUrl));
            simpleMailMessage.setFrom(environment.getProperty("from.email"));

            javaMailSender.send(simpleMailMessage);

            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public boolean orderCreation(String appUrl, Order order){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(order.getUser().getUsername());//should be email address
            simpleMailMessage.setSubject("Auto parts - Order Creation");

            String viewOrdersUrl = appUrl+"/order-history";


            simpleMailMessage.setText(
                    String.format("Dear %s, Thank you for made order with Ecommerce Application !"+
                                    "\n please view your order details %s"
                            , order.getUser().getFullName(), viewOrdersUrl));
            simpleMailMessage.setFrom(environment.getProperty("from.email"));

            javaMailSender.send(simpleMailMessage);

            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

}
