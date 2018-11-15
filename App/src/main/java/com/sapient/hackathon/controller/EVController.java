package com.sapient.hackathon.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.hackathon.model.ChartsResponse;
import com.sapient.hackathon.model.ElectricVehicle;
import com.sapient.hackathon.service.ElectricVehicleService;
import com.sapient.hackathon.service.ForecastWinterService;

@RestController
public class EVController {
	
	@Autowired
	private ForecastWinterService forecastWinterService; 
	
	private ElectricVehicleService electricVehicleService = new ElectricVehicleService();
	

	 	/*@RequestMapping("/viewCharts")
	    public String greetingSubmit(@ModelAttribute ElectricVehicle electricVehicle, HttpSession session) {
    	
	    	//System.out.println("Inside 45-"+((ElectricVehicle)session.getAttribute("eVehicle")).getPercentageEV());
	    	//System.out.println("Inside 21: "+forecastWinterService.getForecastForAwinterDay().size());
	    	//electricVehicle.setRequiredMW(electricVehicleService.addValue(electricVehicle.getRequiredMW()));
	       
	    	List<ChartsResponse> chartsResponse = new ArrayList<ChartsResponse>();
			chartsResponse.add(forecastWinterService.getForecastForAwinterDay());
			chartsResponse.add(forecastWinterService.getForecastForAwinterDay());
	 		//List<JsonDataset> dataSets= forecastWinterService.getForecastForANormalDay();
	 		Map<String, Double> dataSets= forecastWinterService.getForecastForANormalDay();
	 		Gson gsonBuilder = new GsonBuilder().create();
	 		String jsonFromPojo = gsonBuilder.toJson(dataSets);
			//chartsResponse.add(forecastWinterService.getForecastForAwinterDay());
			//chartsResponse.add(forecastWinterService.getForecastForAwinterDay());
			//chartsResponse.add(forecastWinterService.getForecastForAwinterDay());
			
	 		System.out.println(jsonFromPojo);
	    	//return chartsResponse;
			System.out.println("Inside 55");
	    	
	    	return jsonFromPojo;
	    }
*/	 	
	
	@RequestMapping("/viewCharts")
    public List<ChartsResponse> greetingSubmit(@ModelAttribute ElectricVehicle electricVehicle, HttpSession session) {
    	
    	System.out.println("percentageEV="+electricVehicle.getPercentageEV()+"&startHour="+electricVehicle.getStartHour()+"&requiredMW="+electricVehicle.getRequiredMW());
    	
    	System.out.println("Inside 45-"+((ElectricVehicle)session.getAttribute("eVehicle")).getPercentageEV());
    	//System.out.println("Inside 21: "+forecastWinterService.getForecastForAwinterDay().size());
    	//electricVehicle.setRequiredMW(electricVehicleService.addValue(electricVehicle.getRequiredMW()));
       
    	List<ChartsResponse> chartsResponse = new ArrayList<ChartsResponse>();
		chartsResponse.add(forecastWinterService.getForecastForAwinterDay(((ElectricVehicle)session.getAttribute("eVehicle")).getPercentageEV(), ((ElectricVehicle)session.getAttribute("eVehicle")).getStartHour()));
		chartsResponse.add(forecastWinterService.getForecastForAwinterDay(((ElectricVehicle)session.getAttribute("eVehicle")).getPercentageEV(), ((ElectricVehicle)session.getAttribute("eVehicle")).getStartHour()));
		//chartsResponse.add(forecastWinterService.getForecastForAwinterDay());
		//chartsResponse.add(forecastWinterService.getForecastForAwinterDay());
		//chartsResponse.add(forecastWinterService.getForecastForAwinterDay());
		
    	
    	return chartsResponse;
    }

	 	
	 	@RequestMapping("/viewCharts2")
	    public String greetingSubmit2(@ModelAttribute ElectricVehicle electricVehicle, HttpSession session) {
    	
	  	
	    	//return chartsResponse;
			System.out.println("Inside 56");
	    	
	    	return "[[0,18],[1,15],[2,22],[3,14],[4,12],[5,21],[6,13],[7,10],[8,15],[9,15]]";
	    }
	    

}

