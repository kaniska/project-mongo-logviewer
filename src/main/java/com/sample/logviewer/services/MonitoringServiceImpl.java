package com.sample.logviewer.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.sample.logviewer.domain.BusinessEntity;
import com.sample.logviewer.domain.SearchCriteria;
import com.sample.logviewer.domain.VzLogDataModel;

/**
 * @author Kaniska_Mandal
 * 
 */
@Service("monitoringService")
public class MonitoringServiceImpl implements MonitoringService,
		ApplicationContextAware {

	@Autowired
	MongoOperations mongoOperations;

	@Autowired
	MongoFactoryBean mongoFactoryBean;
	private Log logger = LogFactory.getLog(getClass());

	/**
	 * @throws Exception
	 */
	public List<BusinessEntity> searchData(SearchCriteria criteria)
			throws Exception {
		List<BusinessEntity> viewDataList = new ArrayList<BusinessEntity>(1);

		if (criteria.getVersionNumber() != null) {

			logger.debug("Retrieving all Business Entities");

			List<VzLogDataModel> list = mongoOperations.findAll(
					VzLogDataModel.class, "logs");
			List<String> logDataInfo = new ArrayList<String>(1);
			for (VzLogDataModel vzLogDataModel : list) {
				logDataInfo.add(vzLogDataModel.getException());
				logDataInfo.add(vzLogDataModel.getReport_id());
				logDataInfo.add(vzLogDataModel.getAndroid_version());

				BusinessEntity businessEntity = new BusinessEntity(logDataInfo);
				viewDataList.add(businessEntity);
			}
		}

		return viewDataList;
	}

	/**
	 * 
	 */
	public List<BusinessEntity> exceptionDetails(SearchCriteria criteria)
			throws Exception {
		List<BusinessEntity> viewDataList = new ArrayList<BusinessEntity>(1);

	//	if (criteria.getVersionNumber() != null) {

			logger.debug("Retrieving all Business Entities");

			// BasicDBObject projectOp = new BasicDBObject("$project", new
			// BasicDBObject("exception_string", "$exception"));
			Map<String, String> projectedFields = new HashMap<String, String>();
			projectedFields.put("exception_string", "$exception");
			projectedFields.put("phone_model", "$phone_model");
			projectedFields.put("android_version", "$android_version");
			projectedFields.put("device_id", "$device_id");
			projectedFields.put("user_crash_date","$user_crash_date");
			BasicDBObject projectOp2 = new BasicDBObject("$project",
					new BasicDBObject(projectedFields));

			/*Map<String, String> matchFields= new HashMap<String, String>();
			matchFields.put("$gte", criteria.getStartDate());
			matchFields.put("$lt", criteria.getEndDate());
			BasicDBObject matchOp = new BasicDBObject("$match",
					new BasicDBObject(matchFields));*/

			
			BasicDBObject groupOp2 = new BasicDBObject("$group",
					new BasicDBObject("_id", new BasicDBObject(
							"exception_string", "$exception_string")
							.append("phone_model", "$phone_model")
							.append("android_version", "$android_version")
							.append("device_id", "$device_id")
							.append("user_crash_date","$user_crash_date"))
							.append("total_exception_count", new BasicDBObject("$sum", 1)));

			DBCollection dbColl = mongoOperations.getCollection("logs");
			AggregationOutput aggrOutput = dbColl.aggregate(projectOp2,
					groupOp2);

			List<String> logDataInfo = null;

			DateFormat searchDateFormat =  new SimpleDateFormat("MM-dd-yyyy");
			DateFormat crashDateFormat =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			
			//
			for (DBObject dbObject : aggrOutput.results()) {

				System.out.println("selectedFieldName >> "+criteria.getFieldName());
				
				logDataInfo = new ArrayList<String>(1);

				String exceptionName = (String) ((DBObject) dbObject.get("_id"))
						.get("exception_string");
				String phoneModel =  (String) ((DBObject)dbObject.get("_id")).get("phone_model");
				String device_id = (String) ((DBObject) dbObject.get("_id"))
						.get("device_id");
				String android_version = (String) ((DBObject) dbObject
						.get("_id")).get("android_version");
				
				String fieldValue = (String) ((DBObject) dbObject
						.get("_id")).get(criteria.getFieldName());
				
				if(!StringUtils.hasText(criteria.getSearchFilter()) ||					
						(fieldValue != null && fieldValue.contains(criteria.getSearchFilter()))) {
					
					   String user_crash_date  = (String) ((DBObject)dbObject.get("_id")).get("user_crash_date");
					   Long crashDate = null;
						if(user_crash_date != null && !user_crash_date.trim().equals("")) {
							 crashDate = crashDateFormat.parse(user_crash_date).getTime();
						} 
					    String startDateStr = criteria.getStartDate();
					    String endDateStr = criteria.getEndDate();
					    Long startDate = searchDateFormat.parse(startDateStr).getTime();
						Long endDate = searchDateFormat.parse(endDateStr).getTime();
				       
						
					   // System.out.println("user_crash_date : "+user_crash_date);
					   // System.out.println("search_from_date : "+criteria.getStartDate());
					   // System.out.println("search_to_date : "+criteria.getEndDate());
					   // System.out.println("<===================================>");
					    
					    if(crashDate != null && crashDate >= startDate && crashDate < endDate) {
												    
							logDataInfo.add(exceptionName);
							logDataInfo.add(phoneModel);
							logDataInfo.add(device_id);
							logDataInfo.add(android_version);
							logDataInfo.add(user_crash_date);
							String totalExceptionCount = String.valueOf(dbObject
									.get("total_exception_count"));
							logDataInfo.add(totalExceptionCount);
							
							BusinessEntity businessEntity = new BusinessEntity(logDataInfo);
							viewDataList.add(businessEntity);
					    }
				
				}

				// System.out.println("Exception name : " +exceptionName);
				// System.out.println("Total Exception count : " +
				// totalExCount);

				// db.logs.distinct('build.device', {exception:
				// 'java.lang.Exception: BitmapManager: not enough
				// memory'}).length
				// BasicDBObject exceptionOp = new BasicDBObject("exception",
				// exceptionName);
				// String buildDeviceCount =
				// String.valueOf(dbColl.distinct("build.device",
				// exceptionOp).size());
				// logDataInfo.add(buildDeviceCount);
				// System.out.println("Total Build Device count : " +
				// buildDeviceCount);

				// db.logs.distinct('build.fingerprint', {exception:
				// 'java.lang.Exception: BitmapManager: not enough
				// memory'}).length
				// String buildfingerprintCount = String.valueOf(
				// dbColl.distinct("build.fingerprint", exceptionOp).size());
				// System.out.println("Total Build Fingerprint count : " +
				// buildfingerprintCount);
				// logDataInfo.add(buildfingerprintCount);

				// String appVersionCodeCount =
				// String.valueOf(dbColl.distinct("app_version_code",
				// exceptionOp).size());
				// System.out.println("Total App Version Code count : " +
				// appVersionCodeCount);
				// logDataInfo.add(appVersionCodeCount);

				
			}

		//}

		return viewDataList;
	}

	/**
	 * columnLabelList.add("device"); columnLabelList.add("device_id");
	 * columnLabelList.add("board"); columnLabelList.add("exception");
	 * columnLabelList.add("user_crash_date");
	 * columnLabelList.add("user_app_start_date");
	 * columnLabelList.add("stack_trace");
	 * 
	 * @param criteria
	 * @return
	 */

	// helpers

	private String getSearchPattern(SearchCriteria criteria) {
		if (StringUtils.hasText(criteria.getSearchFilter())) {
			return "%"
					+ criteria.getSearchFilter().toLowerCase()
							.replace('*', '%') + "%";
		} else {
			return "'%'";
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getListOfVersions() throws Exception {
		// TODO Auto-generated method stub
		List<String> versionList = new ArrayList<String>();
		versionList.add("4.0");
		versionList.add("3.2");
		versionList.add("4.2");

		return versionList;
	}

}
