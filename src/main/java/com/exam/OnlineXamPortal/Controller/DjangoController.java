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

import com.exam.OnlineXamPortal.Dao.DjangoDao;
import com.exam.OnlineXamPortal.Entity.DjangoQuestion;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DjangoController {
	
	@Autowired
	private DjangoDao djangoDao;
	

	@PostMapping("/adddjango")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addJavaQuestion(@RequestBody DjangoQuestion question) {
		djangoDao.save(question);
		return new ResponseEntity<String>("Success",HttpStatus.OK);
	}
	@GetMapping("/getdjangoQuestion")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<List<DjangoQuestion>> getQuestion() {
		List<DjangoQuestion> questions=djangoDao.findAll();
		return new ResponseEntity<List<DjangoQuestion>>(questions,HttpStatus.OK);
	}

}
