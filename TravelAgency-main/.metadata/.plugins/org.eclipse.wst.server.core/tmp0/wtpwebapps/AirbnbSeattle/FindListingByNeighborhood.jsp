<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
  href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/4.3.0/mdb.min.css"
  rel="stylesheet"
  />

<link
  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
  rel="stylesheet"
/>
<title>Find a listing</title>
</head>
<body>
	<form action="FindListingByNeighborhood" method="post" class="container" style="width:50vw; margin-top:2vw;">
		<h1 style="text-align:center;">Search for a AirbnbListing by Neighborhood</h1>	
		<div class="form-outline mb-4">
    		<input id="neighborhood" class="form-control" name="neighborhood" value="${fn:escapeXml(param.neighborhood)}" />
    		<label class="form-label" for="zipcode">Neighborhood</label>
 		</div>
		<div class="col">
			<button type="submit" class="btn btn-primary btn-block">Submit</button>
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</div>
	</form>
	<div style=" margin-left:1vw; margin-right:1vw; margin-top:2vw;">
	<c:if test="${listings.size() != 0 || listings == null}">
        <table border="1" class="table align-middle mb-0 bg-white" >
        	<thead class="bg-light">
	            <tr>
	                <th>Listing Id</th>
	                <th>Price</th>
	
	            </tr>
	        </thead>
	        <tbody>
	        	 <c:forEach items="${listings}" var="listing" >
                <tr>
                    <td><c:out value="${listing.getListingId()}" /></td>
                    <td><c:out value="${listing.getPrice()}" /></td>

                </tr>
            </c:forEach>
	        </tbody>
       </table>
       </c:if>
       </div>
</body>
<script
  type="text/javascript"
  src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/4.3.0/mdb.min.js"
></script>
</html>
