<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<head>
<link rel="stylesheet" href="<c:url value="/resources/styles/blueprint/displaytagex.css"/>" type="text/css" media="screen, projection" />
</head>
<!-- <h3>Exception Report: </h3> -->
<%--  <p>
	<a id="changeSearchLink" href="/?searchString=${searchCriteria.searchString}&pageSize=${searchCriteria.pageSize}">Change Search</a>
	<script type="text/javascript">
		Spring.addDecoration(new Spring.AjaxEventDecoration({
			elementId: "changeSearchLink",
			event: "onclick",
			popup: true,
			params: {fragments: "searchForm"}		
		}));
	</script>
</p> --%>

<div id="hotelResults">

<display:table name="entityList" id="bizEntity" pagesize="20" requestURI="" varTotals="totals">
<display:setProperty name="paging.banner.placement" value="bottom" />


<c:forEach var="columnLabel" items="${columnLabelList}">
          <td></td>
        <display:column title="${columnLabel}" sortable="true">
				<c:out value="${bizEntity.getNextValue()}"/>        	           
        </display:column>
</c:forEach>
 
<display:footer>
   <%--  <tr>
      <td>Total Entities :</td>
      <td><c:out value="${entityList.size()}" /></td>
    <tr> --%>
  </display:footer>
</display:table>

<%-- </c:if> --%>
</div>	

