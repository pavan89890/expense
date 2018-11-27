package com.pavan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.pavan.model.UserBO;
import com.pavan.respository.RoleRepository;
import com.pavan.respository.UserRepository;

@Controller
@SessionAttributes(names = "currentUser")
public class WelcomeController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@GetMapping("/")
	public ModelAndView dashboard() {

		ModelAndView mv = new ModelAndView();
		mv.addObject("total",
		             0);
		mv.addObject("current",
		             0);
		mv.addObject("left",
		             0);
		mv.addObject("cancelled",
		             0);
		mv.setViewName("dashboard");
		return mv;
	}

	@GetMapping("/admin/users")
	public ModelAndView employees(Model m) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("user",
		             new UserBO());
		mv.addObject("users",
		             userRepository.findAll());
		mv.addObject("roles",
		             roleRepository.findAll());
		mv.setViewName("users");
		return mv;
	}

	@PostMapping(value = "/admin/saveUser")
	@ResponseBody
	public String saveUser(UserBO user) {
		String message = "";
		user.setEnabled(true);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()
		                                                        .trim()));
		try {
			userRepository.save(user);
			message = "SUCCESS-User saved successfully.";
		} catch (Exception e) {
			message = "ERROR-" + e.getMessage();
		}
		return message;
	}

	@GetMapping("/admin/updateUser")
	public String udpateUser(@RequestParam(value = "u") UserBO u, Model m) {
		m.addAttribute("user",
		               u == null ? new UserBO() : u);
		m.addAttribute("roles",
		               roleRepository.findAll());
		m.addAttribute("users",
		               userRepository.findAll());
		return "userPopup";
	}

	@GetMapping("/admin/deleteUser")
	public String deleteUser(@RequestParam(value = "u") UserBO u) {
		String message = "";
		try {
			userRepository.delete(u);
			return "redirect:/admin/users";
		} catch (Exception e) {
			message = "ERROR-" + e.getMessage();
			return message;
		}
	}

	@GetMapping("/login")
	public String loginPage(Model model, String error, String logout) {
		if (error != null) {
			model.addAttribute("errorMsg",
			                   "Please Enter Correct Username Or Password");
		}
		if (logout != null) {
			model.addAttribute("logoutMsg",
			                   "You have been successfully logged out");
		}
		return "login";
	}

	@GetMapping("/403")
	public String accessDeniedPage() {
		return "accessDeniedPage";
	}
}
