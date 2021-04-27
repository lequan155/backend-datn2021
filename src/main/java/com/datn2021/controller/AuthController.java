package com.datn2021.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datn2021.model.ERole;
import com.datn2021.model.Role;
import com.datn2021.model.User;
import com.datn2021.payload.request.LoginRequest;
import com.datn2021.payload.request.SignupRequest;
import com.datn2021.payload.response.JwtResponse;
import com.datn2021.payload.response.MessageResponse;
import com.datn2021.repo.RoleRepository;
import com.datn2021.repo.UserRepository;
import com.datn2021.security.jwt.JwtUtils;
import com.datn2021.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
				);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest){
		if(userRepository.existsByUserName(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		if(userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		
		User user = new User(signUpRequest.getUsername(), 
				encoder.encode(signUpRequest.getPassword()), 
				signUpRequest.getFullname(), 
				signUpRequest.getPhoneNo(), 
				signUpRequest.getEmail());
		Set<String> strRoles = signUpRequest.getRole();
		
		Set<Role> roles = new HashSet<>();
		
		if(strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_STAFF)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
			roles.add(userRole);
		}
		else {
			strRoles.forEach(role ->{
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
									.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
					roles.add(adminRole);
					break;

				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_STAFF)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
					roles.add(userRole);
				}
			});
		}
		user.setRole(roles);
		userRepository.save(user);
		
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	@GetMapping("/checkUser")
	public ResponseEntity<?> checkLogin(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth!=null) {
			UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream()
					.map(item -> item.getAuthority())
					.collect(Collectors.toList());
			return ResponseEntity.ok(new JwtResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
		}
		return null;
	}
}

