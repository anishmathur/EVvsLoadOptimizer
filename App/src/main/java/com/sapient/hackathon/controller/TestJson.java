package com.sapient.hackathon.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sapient.hackathon.model.ChartsResponse;
import com.sapient.hackathon.model.ElectricVehicle;
import com.sapient.hackathon.service.ChartsService;
import com.sapient.hackathon.service.ForecastWinterService;


@Controller
public class TestJson {
	
	//@Autowired
	ChartsService chartService = new ChartsService();
	
	@Autowired
	private ForecastWinterService forecastWinterService; 
	
	//@RequestMapping("/viewCharts")
	public List<ChartsResponse> testResonse(){
		List<ChartsResponse> chartsResponse = new ArrayList<>();
		/*chartsResponse.add(chartService.getDummyData1());
		chartsResponse.add(chartService.getDummyData2());*/
		return chartsResponse;
	}
    @RequestMapping("/index")
	public String getIndex(){
		return "index";
	}
    
    @GetMapping("/electricVehicle")
    public String greetingForm(Model model) {
        
        System.out.println("Inside 1");
    	
    	ElectricVehicle eVehicle = new ElectricVehicle();
//    	eVehicle.setPercentageEV(electricVehicle.getPercentageEV());
//    	eVehicle.setRequiredMW(electricVehicle.getRequiredMW());
//    	eVehicle.setStartHour(electricVehicle.getStartHour());
    	
    	eVehicle.setPercentageEV(40);
    	eVehicle.setRequiredMW(2);
    	eVehicle.setStartHour(6+"");
    	
    	model.addAttribute("eVehicle", eVehicle);
        
        return "index";
    }
    
    @PostMapping("/electricVehicle")
    public String greetingSubmit(@ModelAttribute ElectricVehicle electricVehicle, HttpSession session) {
    	System.out.println("Inside 2-"+electricVehicle.getRequiredMW());
    	System.out.println("Inside 2-"+electricVehicle.getPercentageEV());
    	System.out.println("Inside 2-"+electricVehicle.getStartHour());
    	
    	ElectricVehicle eVehicle = new ElectricVehicle();
//    	eVehicle.setPercentageEV(electricVehicle.getPercentageEV());
//    	eVehicle.setRequiredMW(electricVehicle.getRequiredMW());
//    	eVehicle.setStartHour(electricVehicle.getStartHour());
    	
    	eVehicle.setPercentageEV(40);
    	eVehicle.setRequiredMW(2);
    	eVehicle.setStartHour(6+"");
    	
    	session.setAttribute("eVehicle", eVehicle);
    	
    	System.out.println("Inside 50-"+((ElectricVehicle)session.getAttribute("eVehicle")).getPercentageEV());
    	
    	//System.out.println("Inside 21: "+forecastWinterService.getForecastForAwinterDay().size());
    	//electricVehicle.setRequiredMW(electricVehicleService.addValue(electricVehicle.getRequiredMW()));
		return "index";
    }

}
