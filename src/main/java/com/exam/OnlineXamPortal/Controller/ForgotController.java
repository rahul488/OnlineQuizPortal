package com.exam.OnlineXamPortal.Controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.OnlineXamPortal.Dao.UserDao;
import com.exam.OnlineXamPortal.Entity.AuthRequest;
import com.exam.OnlineXamPortal.Entity.User;
import com.exam.OnlineXamPortal.Services.EmailService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class ForgotController {
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private EmailService emailService;
	@Autowired
	private UserDao userDao;
	
	
	Random random=new Random();

	@GetMapping("/sendotp/{email}")
	public ResponseEntity<Integer> forgotPassword(@PathVariable("email")String email){
		
		//generating 4 digit otp
		int otp=random.nextInt(9999);
		
		String subject="Otp from ExamPortal";
		
		String message=""
						+"<div style='border:1px solid;'>"
						+"<h1>Your Otp is</h1>"
						+"<b>"
						+otp
						+"</b>"
						+"</div>";
		String to=email;
		
		boolean flag=this.emailService.sendEmail(message, subject, to);
		
		return new ResponseEntity<Integer>(otp,HttpStatus.OK);
	}
	
	@PostMapping("/change")
	public String changePassword(@RequestBody AuthRequest request) throws Exception{
		
		String userName=request.getUserName();
		
		User user=userDao.findByUserName(userName);
		
		if(user == null)
			throw new Exception("User not found");
		System.out.println("password is"+" "+request.getPassword());
		user.setPassword(encoder.encode(request.getPassword()));
		userDao.save(user);
		return "Success";
			
		
	}
	
}
