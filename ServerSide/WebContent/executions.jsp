<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html lang="en">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<jsp:include page="headexecutions.jsp" />




<body onload="fetchExecutionAnalysis();"
	class="app header-fixed sidebar-fixed aside-menu-fixed sidebar-lg-show ">
	<jsp:include page="header.jsp" />
	<div class="app-body">
		<jsp:include page="sidebar.jsp"></jsp:include>

		<main class="main"> <!-- Breadcrumb-->
		<ol class="breadcrumb">
			<li class="breadcrumb-item">Home</li>
		</ol>
		<div class="container-fluid">
			<div class="animated fadeIn">
				<div class="card">
					<div class="card-header">
						<i class="icon-note"></i>Execution Result
					</div>
					<table id='grid'></table>
    				<div id='pager'></div>
					
					
				</div>
			</div>
		</div>
		</main>

		<jsp:include page="aside.jsp"></jsp:include>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>