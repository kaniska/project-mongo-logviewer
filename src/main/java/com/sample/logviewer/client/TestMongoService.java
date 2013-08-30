/**
 * 
 */
package com.sample.logviewer.client;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.sample.logviewer.domain.VzLogDataModel;

/**
 * @author Kaniska_Mandal
 * use BasicDBObjectBuilder
 */
@Repository
public class TestMongoService {
	@Autowired
	MongoOperations mongoOperations;

	public void run() {
		try {
			testGroupBy();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void testMR(){
//		List<VzLogDataModel> results = mongoOperations.findAll(VzLogDataModel.class);
		//Query  q1 = new Query(Criteria.where("exception").regex("/^j/"));
		//List<VzLogDataModel> results = mongoOperations.find(q1,VzLogDataModel.class,"vzlogs")..distinct("exception");
		
		//BasicQuery q2 = new BasicQuery("db.vzlogs.aggregate({$group:{'_id':'exception'}},{$match : {'exception':/^j/}})");
		// db.data.find({'exception': /^j/}).distinct('exception')
		// {exception: /^j/}, { "exception": 1 }
		
		GroupByResults<VzLogDataModel> results = mongoOperations.group("logs", 
                GroupBy.key("exception").initialDocument("{ sum: 1 }").reduceFunction("function(doc, prev) { prev.count += 1 }"), 
                VzLogDataModel.class);
		
		
		for(VzLogDataModel logData : results) {
			System.out.println("Result: " + logData.getAndroid_version() 
					+ " " + logData.getException());
		}
		
	}
	
	private void testGroupBy() throws ParseException {
		//http://stackoverflow.com/questions/11418985/mongodb-aggregation-framework-group-over-multiple-values
		System.out.println("Test Group By");
		
		BasicDBObject projectOp1 = new BasicDBObject("$project", new BasicDBObject("exception_string", "$exception"));
		BasicDBObject groupOp1 = new BasicDBObject("$group", 
				new BasicDBObject("_id", new BasicDBObject("exception_string", "$exception_string")).append("total_exception_count", new BasicDBObject("$sum", 1)) );

		BasicDBObject regexOp = new BasicDBObject("$regex","'*Device*'");
		

		BasicDBObject matchOp = new BasicDBObject("$match", new BasicDBObject("$exception", regexOp));
		
		///////////////
		Map<String,String> projectedFields = new HashMap<String,String>();
		projectedFields.put("exception_string", "$exception");
		projectedFields.put("phone_model", "$phone_model");
		projectedFields.put("android_version", "$android_version");
		projectedFields.put("device_id", "$device_id");
		projectedFields.put("user_crash_date","$user_crash_date");
		BasicDBObject projectOp2 = new BasicDBObject("$project", new BasicDBObject(projectedFields));

		DateFormat searchDateFormat =  new SimpleDateFormat("MM-dd-yyyy");
		DateFormat crashDateFormat =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		Long startDate = searchDateFormat.parse("08-21-2010").getTime();
		Long endDate = searchDateFormat.parse("08-23-2013").getTime();
        // 2013-08-23T20:30:42.000-05:00
		
		/*BasicDBObject matchFields =new BasicDBObject("user_crash_date", new BasicDBObject("$gte", df.parse("08-20-2013").getTime())
							.append("$lte", df.parse("08-23-2013").getTime())); */
//		BasicDBObject matchOp2 = new BasicDBObject("$match",matchFields);
		
		BasicDBObject groupOp2 = new BasicDBObject("$group", 
				new BasicDBObject("_id", new BasicDBObject("exception_string", "$exception_string")
						.append("phone_model", "$phone_model")
						.append("android_version", "$android_version")
						.append("device_id", "$device_id")
						.append("user_crash_date", "$user_crash_date")) 
						.append("total_exception_count", new BasicDBObject("$sum", 1)) );
		
		BasicDBObject unwindOp = new BasicDBObject("$unwind", new BasicDBObject("build","$build"));
	
		DBCollection dbColl = mongoOperations.getCollection("logs");
		AggregationOutput aggrOutput = dbColl.aggregate(projectOp2, groupOp2);
		
		List<String> logDataInfo = null;
		
		//System.out.println("Aggregate Result :");
		//System.out.println(aggrOutput.toString());
		
		logDataInfo = new ArrayList<String>(1);
		
		for (DBObject dbObject : aggrOutput.results()) {
			
			String exceptionName =  (String) ((DBObject)dbObject.get("_id")).get("exception_string");
			
			//if(exceptionName.toUpperCase().contains("verizon".toUpperCase())) {
				
				String user_crash_date  = (String) ((DBObject)dbObject.get("_id")).get("user_crash_date");
				Long crashDate = null;
				if(user_crash_date != null && !user_crash_date.trim().equals("")) {
					 crashDate = crashDateFormat.parse(user_crash_date).getTime();
				}
				
				if(crashDate != null && crashDate >= startDate && crashDate < endDate) {
					
						logDataInfo.add(user_crash_date);
						
						logDataInfo.add(exceptionName);
						
						String phoneModel =  (String) ((DBObject)dbObject.get("_id")).get("phone_model");
						logDataInfo.add(phoneModel);
									
						String android_version =  (String) ((DBObject)dbObject.get("_id")).get("android_version");
						logDataInfo.add(android_version);
						
						String device_id =  (String) ((DBObject)dbObject.get("_id")).get("device_id");
						logDataInfo.add(device_id);
						
						String totalExceptionCount = String.valueOf(dbObject.get("total_exception_count"));
						logDataInfo.add(totalExceptionCount);
						
				//}
				}
		}
		
		System.out.println("Final Log :");
		System.out.println(logDataInfo.toString());
	}
}
