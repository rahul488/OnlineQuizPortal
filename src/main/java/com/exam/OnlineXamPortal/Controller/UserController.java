package com.exam.OnlineXamPortal.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.OnlineXamPortal.Dao.TopicsDao;
import com.exam.OnlineXamPortal.Dao.UserDao;
import com.exam.OnlineXamPortal.Entity.AuthRequest;
import com.exam.OnlineXamPortal.Entity.Topics;
import com.exam.OnlineXamPortal.Entity.User;
import com.exam.OnlineXamPortal.Exceptione.Exceptionhandler;
import com.exam.OnlineXamPortal.JwtUtill.JwtUtill;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	@Autowired
	private JwtUtill jwtUtill;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private UserDao userDao;	
	@Autowired
	private TopicsDao topicsDao;
	
	@PostMapping("/authenticate")
	public String generateToken(@RequestBody AuthRequest authRequest ) throws Exception{
		try {
			
				authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUserName(),authRequest.getPassword()));
				
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception("invalid U/P");
		}
		System.out.println(jwtUtill.generateToken(authRequest.getUserName()));
		return jwtUtill.generateToken(authRequest.getUserName());
	}
	@GetMapping("/")
	public String home() {
		return "welcome !!";
	}
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user) throws Exception {
		
		String password=encoder.encode(user.getPassword());
		user.setPassword(password);
		
		
		User user1=userDao.findByUserName(user.getUserName());
		
		if(user1 != null)
			throw new Exceptionhandler();
		user.setRole("ROLE_USER");
		
		userDao.save(user);
		return new ResponseEntity<String>("Success",HttpStatus.OK);
	}
	@GetMapping("/admin/admin-page")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public String adminpage() {
		return "Heyy Admin";
	}
	//using method level security
	@GetMapping("/user/user-page")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> userpage() {
		return new ResponseEntity<String>("Hey user",HttpStatus.OK);
	}
	@GetMapping("/getRole/{userName}")
	public ResponseEntity<String> getRoleofUser(@PathVariable("userName")String userName){
		System.out.println("user name is"+" "+userName);
		User user=userDao.findByUserName(userName);
		System.out.println("User is"+" "+user);
		String role="ADMIN";
		if(user == null || user.getRole().equals("ROLE_USER")) {
			role="USER";
		}
		System.out.println(role);
		return new ResponseEntity<String>(role,HttpStatus.OK);
		
	}
	@PostMapping("/savetopic")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> saveTopic(@RequestBody Topics topics){
		
		topicsDao.save(topics);
		
		return new ResponseEntity<String>("Success",HttpStatus.OK);
	}
	
	@GetMapping("/gettopic")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<List<Topics>> getTopic(){
		
		List<Topics> topics=topicsDao.findAll();
		
		return new ResponseEntity<List<Topics>>(topics,HttpStatus.OK);
	}
	@PostMapping("/registerAdmin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> registerAdmin(@RequestBody User user) throws Exception {
		
		String password=encoder.encode(user.getPassword());
		user.setPassword(password);
		
		
		User user1=userDao.findByUserName(user.getUserName());
		
		if(user1 != null)
			throw new Exceptionhandler();
		user.setRole("ROLE_ADMIN");
		
		userDao.save(user);
		return new ResponseEntity<String>("Success",HttpStatus.OK);
	}
	
}
