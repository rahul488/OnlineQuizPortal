package com.exam.OnlineXamPortal.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.OnlineXamPortal.Dao.AngularDao;
import com.exam.OnlineXamPortal.Entity.AngularQuestion;
import com.exam.OnlineXamPortal.Entity.JavaQuestion;

@RestController
public class Angularcontroll{
	
	@Autowired
	private AngularDao angularDao;
	

	@PostMapping("/addangular")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addJavaQuestion(@RequestBody AngularQuestion question) {
		angularDao.save(question);
		return new ResponseEntity<String>("Success",HttpStatus.OK);
	}
	@GetMapping("/getangularQuestion")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<List<AngularQuestion>> getQuestion() {
		List<AngularQuestion> questions=angularDao.findAll();
		return new ResponseEntity<List<AngularQuestion>>(questions,HttpStatus.OK);
	}
	
	
}
