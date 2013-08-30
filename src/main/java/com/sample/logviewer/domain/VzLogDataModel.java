package com.sample.logviewer.domain;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
public class VzLogDataModel implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private ObjectId _id;
	@Indexed
	private String exception;
	private String android_version;
	private String stack_trace;
	private String report_id;	
	private String app_version_code;	
	//private Collection<Deal> deals = new ArrayList<Deal>();
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getAndroid_version() {
		return android_version;
	}
	public void setAndroid_version(String android_version) {
		this.android_version = android_version;
	}
	public String getStack_trace() {
		return stack_trace;
	}
	public void setStack_trace(String stack_trace) {
		this.stack_trace = stack_trace;
	}
	public String getReport_id() {
		return report_id;
	}
	public void setReport_id(String report_id) {
		this.report_id = report_id;
	}
	public String getApp_version_code() {
		return app_version_code;
	}
	public void setApp_version_code(String app_version_code) {
		this.app_version_code = app_version_code;
	}
	
	
}
