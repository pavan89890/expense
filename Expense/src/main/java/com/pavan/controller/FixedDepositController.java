package com.pavan.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.pavan.dto.FixedDepositDTO;
import com.pavan.model.FixedDepositBO;
import com.pavan.respository.FixedDepositRepository;

@Controller
@SessionAttributes(names = "currentUser")
public class FixedDepositController {

	@Autowired
	private FixedDepositRepository fixedDepositsRepository;

	@GetMapping("/admin/fds")
	public ModelAndView fds() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("fd",
		             new FixedDepositBO());
		FixedDepositDTO dto = new FixedDepositDTO();
		List<FixedDepositBO> fds = new ArrayList<FixedDepositBO>();

		int count=0;
		float totDepAmount=0;
		float totMatAmount=0;
		
		
		for (FixedDepositBO fd : fixedDepositsRepository.findAll()) {
			fd.setRemaining(calculateAge(fd.getMaturedOn()));
			totDepAmount+=fd.getDepAmount();
			totMatAmount=fd.getMatAmount();
			fds.add(fd);
			count++;
		}
		dto.setFds(fds);
		dto.setFdsCount(count);
		dto.setTotDepAmount(totDepAmount);
		dto.setTotMatAmount(totMatAmount);
		
		mv.addObject("dto",
		             dto);
		mv.setViewName("fixedDeposit");
		return mv;
	}
	
	private static String calculateAge(Date birthDate)
	   {
	      int years = 0;
	      int months = 0;
	      int days = 0;
	 
	      //create calendar object for current day
	      long currentTime = System.currentTimeMillis();
	      Calendar now = Calendar.getInstance();
	      now.setTimeInMillis(birthDate.getTime());
	      
	      Calendar birthDay = Calendar.getInstance();
	      birthDay.setTimeInMillis(currentTime);
	 
	      //Get difference between years
	      years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
	      int currMonth = now.get(Calendar.MONTH) + 1;
	      int birthMonth = birthDay.get(Calendar.MONTH) + 1;
	 
	      //Get difference between months
	      months = currMonth - birthMonth;
	 
	      //if month difference is in negative then reduce years by one
	      //and calculate the number of months.
	      if (months < 0)
	      {
	         years--;
	         months = 12 - birthMonth + currMonth;
	         if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
	            months--;
	      } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
	      {
	         years--;
	         months = 11;
	      }
	 
	      //Calculate the days
	      if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
	         days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
	      else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
	      {
	         int today = now.get(Calendar.DAY_OF_MONTH);
	         now.add(Calendar.MONTH, -1);
	         days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
	      }
	      else
	      {
	         days = 0;
	         if (months == 12)
	         {
	            years++;
	            months = 0;
	         }
	      }
	      //Create new Age object
	      //
	      return  years+" y, "+months+" m, "+days+" d";
	   }

	@PostMapping(value = "/admin/saveFixedDeposit")
	@ResponseBody
	public String saveFixedDeposit(FixedDepositBO fixedDeposit) {
		String message = "";
		try {
			Float matAmount = null;
			matAmount = fixedDeposit.getDepAmount() + (fixedDeposit.getDepAmount() * fixedDeposit.getRoi()) / 100;
			fixedDeposit.setMatAmount(matAmount);

			Date maturedOn = null;
			Calendar c = Calendar.getInstance();
			c.setTime(fixedDeposit.getDepositedOn());
			c.add(Calendar.MONTH,
			      fixedDeposit.getPeriod());
			maturedOn = c.getTime();
			fixedDeposit.setMaturedOn(maturedOn);

			fixedDepositsRepository.save(fixedDeposit);
			message = "SUCCESS-Fixed deposit saved successfully.";
		} catch (Exception e) {
			message = "ERROR-" + e.getMessage();
		}
		return message;
	}

	@GetMapping("/admin/updateFixedDeposit")
	public String updateFixedDeposit(@RequestParam(value = "u") FixedDepositBO u, Model m) {
		m.addAttribute("fixedDeposit",
		               u == null ? new FixedDepositBO() : u);
		return "fixedDepositPopup";
	}

	@GetMapping("/admin/deleteFixedDeposit")
	public String deleteFixedDeposit(@RequestParam(value = "u") FixedDepositBO u) {
		String message = "";
		try {
			fixedDepositsRepository.delete(u);
			return "redirect:/admin/fds";
		} catch (Exception e) {
			message = "ERROR-" + e.getMessage();
			return message;
		}
	}

}
