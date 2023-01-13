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
<title>Reviews</title>
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
          <a class="nav-link" href="findusers">Search User</a>
        </li>
      </ul>
      <!-- Left links -->
    </div>
    <!-- Collapsible wrapper -->
  </div>
  <!-- Container wrapper -->
</nav>
<h1 style="text-align:center;">Create Host</h1>
	<form action="reviewcreate" method="post" class="container" style="width:50vw; margin-top:2vw;">
		<div class="form-outline mb-4">
			
			<input id="userId" name="userId" class="form-control" value="">
			<label for="userId" class="form-label">UserId</label>
		</div>
		<div class="form-outline mb-4">
			
			<input id="username" name="username" class="form-control" value="">
			<label for="username" class="form-label">User Name</label>
		</div>
		<div class="form-outline mb-4">
			
			<input id="created" name="created" class="form-control" value="">
			<label for="created" class="form-label">Created (yyyy-mm-dd)</label>
		</div>
		<div class="form-outline mb-4">
			
			<input id="content" name="content" class="form-control" value="">
			<label for="content" class="form-label">Content</label>
		</div>
		<div class="form-outline mb-4">
			
			<input id="listingId" name="listingId" class="form-control" value="${fn:escapeXml(param.listingId)}" readonly>
			<label for="listingId" class="form-label">ListingId</label>
		</div>
		<div class="col">
			 <button type="submit" class="btn btn-primary btn-block">Submit</button>
			 <br/><br/><br/>
			 <span id="successMessage"><b>${messages.success}</b></span>
		</div>
	</form>
</body>
<script
  type="text/javascript"
  src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/4.3.0/mdb.min.js"
></script>
</html>
