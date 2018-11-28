package com.pavan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.pavan.model.ChitBO;
import com.pavan.respository.ChitRepository;

@Controller
@SessionAttributes(names = "currentUser")
public class ChitController {

	@Autowired
	private ChitRepository chitRepository;

	@GetMapping("/admin/chits")
	public ModelAndView chits() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("chit",
		             new ChitBO());
		mv.addObject("chits",
		             chitRepository.findAll());
		mv.addObject("totalActual",
		             chitRepository.getTotalActualAmount());
		mv.addObject("totalPaid",
		             chitRepository.getTotalPaidAmount());
		mv.setViewName("chit");
		return mv;
	}

	@PostMapping(value = "/admin/saveChit")
	@ResponseBody
	public String saveChit(ChitBO chit) {
		String message = "";
		try {
			chit.setProfit(chit.getActualAmount() - chit.getPaidAmount());
			chitRepository.save(chit);
			message = "SUCCESS-Chit saved successfully.";
		} catch (Exception e) {
			message = "ERROR-" + e.getMessage();
		}
		return message;
	}

	@GetMapping("/admin/updateChit")
	public String updateChit(@RequestParam(value = "u") ChitBO u, Model m) {
		m.addAttribute("chit",
		               u == null ? new ChitBO() : u);
		return "chitPopup";
	}

	@GetMapping("/admin/deleteChit")
	public String deleteChit(@RequestParam(value = "u") ChitBO u) {
		String message = "";
		try {
			chitRepository.delete(u);
			return "redirect:/admin/chits";
		} catch (Exception e) {
			message = "ERROR-" + e.getMessage();
			return message;
		}
	}

}
