package com.sample.logviewer.services;

import java.util.List;

import com.sample.logviewer.domain.BusinessEntity;
import com.sample.logviewer.domain.SearchCriteria;

/**
 * A service interface for retrieving hotels and bookings from a backing
 * repository. Also supports the ability to cancel a booking.
 */
public interface MonitoringService {

	
	List<BusinessEntity> searchData(SearchCriteria criteria) throws Exception;

	List<String> getListOfVersions() throws Exception;

	List<BusinessEntity> exceptionDetails(SearchCriteria searchCriteria) throws Exception;

}
