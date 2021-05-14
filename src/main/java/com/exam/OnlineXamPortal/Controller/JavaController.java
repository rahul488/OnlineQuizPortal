package com.exam.OnlineXamPortal.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.OnlineXamPortal.Dao.JavaDao;
import com.exam.OnlineXamPortal.Entity.JavaQuestion;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class JavaController {
	
	@Autowired
	private JavaDao javaDao;
	
	@PostMapping("/addjava")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addJavaQuestion(@RequestBody JavaQuestion question) {
		javaDao.save(question);
		return new ResponseEntity<String>("Success",HttpStatus.OK);
	}
	@GetMapping("/getjavaQuestion")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<List<JavaQuestion>> getQuestion() {
		List<JavaQuestion> questions=javaDao.findAll();
		return new ResponseEntity<List<JavaQuestion>>(questions,HttpStatus.OK);
	}

}
