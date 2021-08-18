package com.example.leo.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.leo.Exception.UserNotFoundException;
import com.example.leo.entity.User;
import com.example.leo.global.Utility;
import com.example.leo.service.UserService;

import net.bytebuddy.utility.RandomString;

@Controller
public class ForgotPasswordController {
@Autowired
private UserService userService;
@Autowired
private JavaMailSender mailSender;//ENVIO GMAIL

	@GetMapping("/forgot_password")
	public String showForgotPasswordForm(Model model) {
		model.addAttribute("pageTitle", "Forgot Password");
		return "/auth/forgot_password_form";
		
	}
	
	@PostMapping("/forgot_password")
	public String processForgotPasswordForm( HttpServletRequest request ,Model model) {
		String email = request.getParameter("email");//name="email"
		 String token = RandomString.make(45);
		 
	try {
		userService.updateResetPasswordToken(token, email);//usamos utility tmb

		String resetPasswordLink = Utility.getSiteUrl(request)+ "/reset_password?token=" + token;
		sendEmail(email , resetPasswordLink);
		model.addAttribute("message", "hemos enviado un link para el cambio de password a su email .revisalo ");
			} catch (UserNotFoundException ex) {
		model.addAttribute("error", ex.getMessage());
		} catch (UnsupportedEncodingException |MessagingException e) {
				
			model.addAttribute("error", "Error mientras se envia el email.");
			} 
	model.addAttribute("pageTitle", "Forgot Password");
		return "/auth/forgot_password_form";
}
	
	private void sendEmail(String email ,String resetPasswordLink) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
	
		helper.setFrom("contact@leo.com", "Soporte Leo");
		 helper.setTo(email);
	     
		    String subject = "Aqui esta el link para que resetear el password";
		     
		    String content = "<p>Hola,</p>"
		            + "<p>Has solicitado cambiar la contraseña.</p>"
		            + "<p>Clic debajo para cambiar tu contraseña:</p>"
		            + "<p><a href=\"" + resetPasswordLink + "\">Cambiar contraseña</a></p>"
		            + "<br>"
		            + "<p>Ignora este email si ya recordaste tu password, "
		            + "o no hayas solicitado cambiarla.</p>";
		     
		    helper.setSubject(subject);
		     
		    helper.setText(content, true);
		     
		    mailSender.send(message);//llama al remittete
	}

	@GetMapping("/reset_password")
	public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
	    User user = userService.get(token);
	    model.addAttribute("token", token);
	     
	    if (user == null) {
	    	 model.addAttribute("title", "Reset your password");
	        model.addAttribute("message", "Invalid Token");
	        return "message";
	    }
	     model.addAttribute("token", token);//atributo escondido hidden
	     model.addAttribute("pageTitle", "Reset your password");
	    return "/auth/reset_password_form";
	}

	@PostMapping("/reset_password")
	public String processResetPassword(HttpServletRequest request, Model model) {
	    String token = request.getParameter("token");
	    String password = request.getParameter("password");
	     
	    User user = userService.get(token);
	   
	     
	    if (user == null) {//verificamos si el usario es valido o no
	    	model.addAttribute("title", "Reset your password");
	    	model.addAttribute("message", "Invalid Token");
	        
	    } else {           
	        userService.updatePassword(user, password);//se encripta igualmente la contraseña
	         
	        model.addAttribute("message", "You have successfully changed your password.");
	    }
	     
	    return "/auth/message";
	}
}

