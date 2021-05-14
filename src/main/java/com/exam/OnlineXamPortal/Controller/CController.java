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

import com.exam.OnlineXamPortal.Dao.CDao;
import com.exam.OnlineXamPortal.Entity.CQuestion;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CController {
	
	@Autowired
	private CDao cDao;

	@PostMapping("/addc")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addJavaQuestion(@RequestBody CQuestion question) {
		cDao.save(question);
		return new ResponseEntity<String>("Success",HttpStatus.OK);
	}
	@GetMapping("/getcQuestion")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<List<CQuestion>> getQuestion() {
		List<CQuestion> questions=cDao.findAll();
		return new ResponseEntity<List<CQuestion>>(questions,HttpStatus.OK);
	}

}
