package com.sample.logviewer.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sample.logviewer.domain.BusinessEntity;
import com.sample.logviewer.domain.SearchCriteria;
import com.sample.logviewer.domain.VzLogDataModel;
import com.sample.logviewer.services.MonitoringService;
import com.sample.logviewer.util.DateFormatUtil;

@Controller
public class LogViewerMvcController {

	@Inject
	public MonitoringService monitoringService;

	private Log log = LogFactory.getLog(getClass());

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showForm(Model model) throws Exception {
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setPage(5);
		searchCriteria.setPageSize(50);
		try {
			 searchCriteria.setEndDate(DateFormatUtil.marshal(new Date()));
			 searchCriteria.setStartDate(DateFormatUtil.marshal(new Date(
					 	new Date().getTime() - 604800000)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		searchCriteria.setFieldName("exception_string");
		searchCriteria.setVersionNumber(monitoringService.getListOfVersions().get(0));		
		searchCriteria.setSearchFilter("");
		 // List<BusinessEntity> entityList = monitoringService.findEntityAttributeList(searchCriteria);
		 // List<String> entityAttributeList =monitoringService.findAttributeList(searchCriteria);
		
		model.addAttribute("columnLabelList", getColumnLabels());
		
	List<BusinessEntity> tenantDataList = new ArrayList<BusinessEntity>(1);
		try {
			tenantDataList = monitoringService.exceptionDetails(searchCriteria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("entityList", tenantDataList);
		model.addAttribute("versionNumberList", monitoringService.getListOfVersions());
		
		model.addAttribute("searchCriteria", searchCriteria); // adding in
																// model
		return "home";
	}

	@RequestMapping(value = "/entities", method = RequestMethod.GET)
	public String list(SearchCriteria searchCriteria, Model model) throws Exception {
		
		model.addAttribute("columnLabelList", getColumnLabels());
		
		List<BusinessEntity> exceptionDataList = new ArrayList<BusinessEntity>(1);
		try {
			exceptionDataList = monitoringService.exceptionDetails(searchCriteria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//model.addAttribute("entityList", exceptionDataList);
		model.addAttribute("entityList", exceptionDataList);
		model.addAttribute("versionNumberList", monitoringService.getListOfVersions());
		
		model.addAttribute("searchCriteria", searchCriteria);
		
		return "home"; // users/list
	}
	
	private List<String> getColumnLabels(){
		List<String> columnLabelList = new ArrayList<String>(1);				
		columnLabelList.add("Exception");
		columnLabelList.add("Phone Model");
		columnLabelList.add("Device Id");
		columnLabelList.add("Android Version");
		columnLabelList.add("Crash Date");
		columnLabelList.add("Count");
		//columnLabelList.add("user_crash_date");
		//columnLabelList.add("user_app_start_date");
		//columnLabelList.add("stack_trace");
		return columnLabelList;
	}	
	
	/*private List<String> getColumnLabels(){
		List<String> columnLabelList = new ArrayList<String>(1);				
		columnLabelList.add("device");
		//columnLabelList.add("device_id");
		//columnLabelList.add("board");
		columnLabelList.add("exception");
		columnLabelList.add("total_count");
		//columnLabelList.add("android_version");
		//columnLabelList.add("user_crash_date");
		//columnLabelList.add("user_app_start_date");
		//columnLabelList.add("stack_trace");
		return columnLabelList;
	}*/

}
