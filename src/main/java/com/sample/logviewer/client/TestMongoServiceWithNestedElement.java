/**
 * 
 */
package com.sample.logviewer.client;

import java.util.ArrayList;
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
 *
 */
@Repository
public class TestMongoServiceWithNestedElement {
	@Autowired
	MongoOperations mongoOperations;

	public void run() {
		testGroupBy();

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
	
	private void testGroupBy() {
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
		projectedFields.put("phone_model", "$build.model");
		projectedFields.put("android_version", "$android_version");
		projectedFields.put("device_id", "$device_id");
		
		projectedFields.put("user_crash_date","$user_crash_date");
		
		//projectedFields.put("model", "$build.$model");
		
		BasicDBObject projectOp2 = new BasicDBObject("$project", new BasicDBObject(projectedFields));
		//System.out.println(projectOp.toString());
		
		BasicDBObject groupOp2 = new BasicDBObject("$group", 
				new BasicDBObject("_id", new BasicDBObject("exception_string", "$exception_string")
						.append("phone_model", "$phone_model")
						.append("android_version", "$android_version")
						.append("device_id", "$device_id")) 
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
			
			if(exceptionName.contains("Device")) {
				
				String user_crash_date  = (String) ((DBObject)dbObject.get("_id")).get("user_crash_date");
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
			}
		}
		
		System.out.println("Final Log :");
		System.out.println(logDataInfo.toString());
	}
}
