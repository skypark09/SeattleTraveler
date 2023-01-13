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
  

<title>Listing Detail</title>
<script>
function initMap() {
	  const myLatLng = { lat: ${listingDetail.getLatitude()} , lng: ${listingDetail.getLongitude()}};
	  const map = new google.maps.Map(document.getElementById("map"), {
	    zoom: 12,
	    center: myLatLng,
	  });

	  new google.maps.Marker({
	    position: myLatLng,
	    map,
	    title: "Hello World!",
	  });
	}

window.initMap = initMap;
</script>
</head>
<body>
<nav class="navbar navbar-expand-lg sticky-top navbar-light bg-light">
  <!-- Container wrapper -->
  <div class="container-fluid">
    <!-- Toggle button -->
    <button
      class="navbar-toggler"
      type="button"
      data-mdb-toggle="collapse"
      data-mdb-target="#navbarSupportedContent"
      aria-controls="navbarSupportedContent"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <i class="fas fa-bars"></i>
    </button>

    <!-- Collapsible wrapper -->
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <!-- Navbar brand -->
      <a class="navbar-brand mt-2 mt-lg-0" href="/AirbnbSeattle">
        Seattle Traveler
      </a>
      <!-- Left links -->
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link" href="findusers">Find User</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="FindAirbnbListings">Find Listing</a>
        </li>
      </ul>
      <!-- Left links -->
    </div>
    <!-- Collapsible wrapper -->
  </div>
  <!-- Container wrapper -->
</nav>
<div class="row" style="padding:1vw;">
	<div class="col-md-6">
		<h4><c:out value="${listingDetail.getName()}" /></h4>
		<img src="<c:out value="${listingDetail.getPicture_URL()}"/>"  class=" img-fluid rounded" style="width:50vw; "/>
	</div>
	<div class="col-md-3">
		<a href="<c:out value="${listingDetail.getListing_url()}"/>">Airbnb URL</a>
		<p>Property Type: <c:out value="${listingDetail.getProperty_Type()}"/></p>
		<p>Room Type: <c:out value="${listingDetail.getRoom_Type()}"/></p>
		<p>Accommodates: <c:out value="${listingDetail.getAccommdates()}"/> People</p>
		<p>Bathrooms: <c:out value="${listingDetail.getBathrooms()}"/></p>
		<p>Bedrooms: <c:out value="${listingDetail.getBedrooms()}"/></p>
		<p>Beds: <c:out value="${listingDetail.getBeds()}"/></p>
		<p>Price: $<c:out value="${listingDetail.getPrice()}"/></p>
		<p>Host Name: <c:out value="${listingDetail.getHost().getHostName()}"/></p>
		<p>Neighborhood Name: <c:out value="${listingDetail.getNeighborhood().getNeighborhoodName()}"/></p>
	</div>
	<div class="col-md-3">
		<div id="map" style="width:100%; height:500px;"></div>
	</div>
	<div class="col-md-12">
		<h5>Property Description</h5>
		<p><c:out value="${listingDetail.getDescription()}"/></p>
		<h5>Neighborhood Overview</h5>
		<p><c:out value="${listingDetail.getNeighborhood_Overview()}"/></p>
	</div>
	<h5>Reviews</h5><a href="reviewcreate?listingId=<c:out value="${listingDetail.getListingId()}"/>" class="btn btn-primary" style="width:150px;"> Create Review</a>
	<div class="col-md-12" style="margin-left:1vw; margin-right:1vw; margin-top:2vw; height:100vh; overflow-y:scroll;">
		<c:if test="${reviews.size() != 0 || reviews == null}">
        <table border="1" class="table align-middle mb-0 bg-white" >
        	<thead class="bg-light">
	            <tr>
	                <th>UserName</th>
	                <th>Content</th>
	                <th>Date</th>
	
	            </tr>
	        </thead>
	        <tbody>
	        	 <c:forEach items="${reviews}" var="review" >
                <tr>
                    <td><c:out value="${review.getUsername()}" /></td>
                    <td><c:out value="${review.getContent()}" /></td>
                    <td><fmt:formatDate value="${review.getCreated()}" pattern="MM-dd-yyyy hh:mm:sa"/></td>
                </tr>
            </c:forEach>
	        </tbody>
       </table>
       </c:if>
	</div>
</div>

<script
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB41DRUbKWJHPxaFjMAwdrzWzbVKartNGg&callback=initMap&v=weekly"
      defer
    ></script>
</body>
<script
  type="text/javascript"
  src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/4.3.0/mdb.min.js"
></script>
</html>