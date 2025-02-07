package com.Charge_Flex.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Charge_Flex.dto.AuthenticationRequest;
import com.Charge_Flex.dto.AuthenticationResponse;
import com.Charge_Flex.dto.RecordDto;
import com.Charge_Flex.dto.UserDto;
import com.Charge_Flex.entity.Records;
import com.Charge_Flex.entity.Users;
import com.Charge_Flex.repository.RecordRepository;
import com.Charge_Flex.repository.UserRepository;
import com.Charge_Flex.service.mail.EmailSenderService;
import com.Charge_Flex.services.auth.AuthServicesImpl;
import com.Charge_Flex.services.jwt.UserService;
import com.Charge_Flex.utils.JWTUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	AuthServicesImpl authService;
	
	@Autowired
	EmailSenderService emailSenderService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	JWTUtil jwtUtil;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RecordRepository recordRepository;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signupCustomer(@RequestBody UserDto signupRequest){
		
		if(authService.hasCustomerWithEmail(signupRequest.getEmail()))
			return new ResponseEntity<>("Customer already exists with this email !",HttpStatus.NOT_ACCEPTABLE);
		UserDto createdCustomerDto = authService.createCustomer(signupRequest);
		
		if(createdCustomerDto == null) return new ResponseEntity<>("Customer not created",HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(createdCustomerDto,HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
	    String email = authenticationRequest.getEmail();
	    String password = authenticationRequest.getPassword();
	    
	    
	    Optional<Users> optionalUser = userRepository.findFirstByEmail(email);
	    
	    if (optionalUser.isPresent() && passwordMatches(optionalUser.get().getPassword(), password)) {
	    	final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
	        String jwt = jwtUtil.generateToken(userDetails);

	        AuthenticationResponse authenticationResponse = new AuthenticationResponse(
	                jwt,
	                optionalUser.get().getId(),
	                optionalUser.get().getUserRole(),
	                optionalUser.get().getName(),
	                optionalUser.get().getPhone()
	        );

	        return ResponseEntity.ok(authenticationResponse);
	        
	    } else {
	        return new ResponseEntity("Invalid username or password !",HttpStatus.BAD_REQUEST);
	    }
	}

	private boolean passwordMatches(String string, String password) {

		boolean flag=false;
	    if(string.equals(password)) {
	    	flag=true;
	    };
	    
	    return flag;
	}

	@PostMapping("/saveRecord")
	public void saveRecord(@RequestBody RecordDto saveRequest){
		
		authService.createRecord(saveRequest);
		
		String name = saveRequest.getName();
		String plan = saveRequest.getPlan();
		String validity = saveRequest.getValidity();
		
		String toEmail = saveRequest.getEmail();
        String subject = "Recharge Successful";
        String body = "Dear "+name+ ", \nYour mobile prepaid recharge of ₹"+ plan + " was successful and is valid uptil "+validity+ ". \n\nThanks for using our services! \n\nRegards, \nTeam FlexiPay.";
        
        // Sending email using EmailSenderService
        emailSenderService.sendEmail(toEmail, subject, body);
		
	}
	
	@GetMapping("/getRecords")
    public List<Records> getAllRecords() {
        return recordRepository.findAll();
        
    }
//
//	@GetMapping("/createTransaction/{amount}")
//	public TransactionDetails createTransaction(@PathVariable(name="") Double amount) {
//		return authService.createTransaction(amount);
//	}
}
