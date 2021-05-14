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

import com.exam.OnlineXamPortal.Dao.PythonDao;
import com.exam.OnlineXamPortal.Entity.PythonQuestion;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PythonController {
	
	@Autowired
	private PythonDao pythonDao;

	@PostMapping("/addpython")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addJavaQuestion(@RequestBody PythonQuestion question) {
		pythonDao.save(question);
		return new ResponseEntity<String>("Success",HttpStatus.OK);
	}
	@GetMapping("/getpythonQuestion")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<List<PythonQuestion>> getQuestion() {
		List<PythonQuestion> questions=pythonDao.findAll();
		return new ResponseEntity<List<PythonQuestion>>(questions,HttpStatus.OK);
	}
	
	
}
