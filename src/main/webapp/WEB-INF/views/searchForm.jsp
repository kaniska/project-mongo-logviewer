<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>

<h3>Acra Exception Report</h3>

<c:url var="Url" value="/entities" />
<form:form modelAttribute="searchCriteria" action="${Url}" method="get"
	cssClass="inline" name="form1">
	<span class="errors span-18"> <form:errors path="*" />
	</span>
	<fieldset>

		<%-- <div class="span-8">
			<label for="versionNumber">Target Version:</label>
			<form:select id="versionNumber" path="versionNumber">
				<form:options items="${versionNumberList}"/>
			</form:select>
		</div> --%>
		
					<div class="span-8">
						<label for="startDate">From date:</label>
						<form:input path="startDate"/>
						<script type="text/javascript">
							Spring.addDecoration(new Spring.ElementDecoration({
								elementId : "startDate",
								widgetType : "dijit.form.DateTextBox",
								widgetAttrs : { datePattern : "MM-dd-yyyy", required : true }}));  
						</script>						
					</div>
					<div class="span-8">
					    <label for="startDate">To date:</label>					
						<form:input path="endDate"/>
						<script type="text/javascript">
							Spring.addDecoration(new Spring.ElementDecoration({
								elementId : "endDate",
								widgetType : "dijit.form.DateTextBox",
								widgetAttrs : { datePattern : "MM-dd-yyyy", required : true }}));  
						</script>
					</div>
					
			<div class="span-8">
			<label for="fieldName">Field Name:</label>
			<form:select id="fieldName" path="fieldName">
				<form:option label="Exception" value="exception_string" />
				<form:option label="Device-Id" value="device_id" />
				<form:option label="Phone Model" value="phone_model" />
				<form:option label="Android-Version" value="android_version" />
			</form:select>
			</div>
			
			<div class="span-8">
			<label for="searchFilter">Search Text:</label>
			<form:input id="searchFilter" path="searchFilter" />
			<script type="text/javascript">
				Spring.addDecoration(new Spring.ElementDecoration({
					elementId : "searchFilter",
					widgetType : "dijit.form.ValidationTextBox",
					widgetAttrs : { promptMessage : "Search Exceptions  by name." }}));
			</script>

		<%--  <div class="span-8">
				<label for="pageSize">Maximum Records:</label>
				<form:select id="pageSize" path="pageSize">
					<form:option label="50" value="50" />
					<form:option label="100" value="100" />
					<form:option label="200" value="200" />
					<form:option label="500" value="500" />
				</form:select>
		</div>  --%>
		
		<!-- <div class="span-4">
			<button type="submit" >Search</button>
		</div> -->
		
		<!-- 	<button type="submit" name="search"  onClick="javascript:loadImage()">Search</button>
		-->
		 <input class="input" type="submit" name="search" value="Search"
		 onclick="javascript:loadSubmit()">
		 </div>
		
		<p style="visibility:hidden;" id="progress"/>
		<img id="progress_image" style="padding-left:5px;padding-top:5px;" 
		src="/dcba_doctor/resources/images/loading.gif" alt=""> <b>Search in progress</b><p> 
		<span id="loading"></span>
		
	</fieldset>
</form:form>
		
		
		
<script type="text/javascript">
 function validateTheForm(f) {
	 try {
		var fromDate=document.getElementById('startDate').value;
		var toDate=document.getElementById('endDate').value;
		if ( fromDate.length == 0 || toDate.length == 0 ) {
            //required fields.
            alert("Dates are required fields.");
            return false;

        }else {
            if (Date.parse(fromDate) > Date.parse(toDate)) {
                alert("Invalid Date Range!");
                return false;            
            } else {
                return true;
            }
        }
    }  catch (e) {
        return false;
    }
	return false;
} 

function loadImage() {
	document.getElementById('loading').innerHTML = "Refreshing the UI...<img src=\"/dcba_doctor/resources/images/loading.gif\"/>";location=document.form1.search;
	return true;
}

function loadSubmit() {

    ProgressImage = document.getElementById('progress_image');
    document.getElementById("progress").style.visibility = "visible";
    setTimeout("ProgressImage.src = ProgressImage.src",100);
    return true;

    } 
</script>
