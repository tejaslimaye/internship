<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html lang="en">
 <jsp:include page="head.jsp"/>
 
 <script>
    
 
</script>
 
  <body onload="fetchTestJobs();" class="app header-fixed sidebar-fixed aside-menu-fixed sidebar-lg-show">
 <jsp:include page="header.jsp"/>
 
    <div class="app-body">
	<jsp:include page="sidebar.jsp"></jsp:include>
     
     <main class="main">
        <!-- Breadcrumb-->
        <ol class="breadcrumb">
          <li class="breadcrumb-item">Home</li>
          </ol>
        <div class="container-fluid">
          <div class="animated fadeIn">
            <div class="card">
              <div class="card-header">
                <i class="icon-screen-notebook"></i>Test Jobs</div>
                
              <div class="card-body">
              	<div id="jsGrid_TestJobs">
              		
              	</div>
              
              </div>
            </div>
           </div>
        </div>
      </main>
    
    <jsp:include page="aside.jsp"></jsp:include> 
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
  </body>
</html>