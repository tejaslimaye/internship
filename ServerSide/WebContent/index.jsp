<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
  <jsp:include page="head.jsp"/>
  <body onload="fetchDetails();"  class="app header-fixed sidebar-fixed aside-menu-fixed sidebar-lg-show">
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
            <div class="row">
              <div class="col-sm-6 col-lg-3">
                <div class="card text-white bg-primary">
                  <div class="card-body pb-0">
                   
                    <div class="text-value" id="test_runs">wait... </div>
                    <div>Live Test Runs</div>
                  </div>
                  <div class="chart-wrapper mt-3 mx-3" style="height:70px;">
                    <canvas class="chart" id="card-chart1" height="70"></canvas>
                  </div>
                </div>
              </div>
                <!-- /.col-->
              <div class="col-sm-6 col-lg-3">
                <div class="card text-white bg-danger">
                  <div class="card-body pb-0">
                  
                    <div class="text-value" id="device_fails"> wait...</div>
                    <div>Most Failures by Devices</div>
                  </div>
                  <div class="chart-wrapper mt-3 mx-3" style="height:70px;">
                    <canvas class="chart" id="card-chart2" height="70"></canvas>
                  </div>
                </div>
              </div>
              <!-- /.col-->
              <div class="col-sm-6 col-lg-3">
                <div class="card text-white bg-danger">
                  <div class="card-body pb-0">
                  
                    <div class="text-value" id="feature_fails"> wait...</div>
                    <div>Most Failures by Features</div>
                  </div>
                  <div class="chart-wrapper mt-3 mx-3" style="height:70px;">
                    <canvas class="chart" id="card-chart4" height="70"></canvas>
                  </div>
                </div>
              </div>
              <!-- /.col-->
            
            </div>
            <!-- /.row-->
            <div class="card">
              <div class="card-body">
                <div class="row">
                  <div class="col-sm-5">
                    <h4 class="card-title mb-0">Test Execution History (Last 7 days)</h4>
                    <div class="small text-muted"><%=new java.util.Date()%></div>
                  </div>
                  <!-- /.col-->
                  <!-- /.col-->
                </div>
                <!-- /.row-->
                <div class="chart-wrapper" style="height:270px;margin-top:40px;">
                  <canvas class="chart" id="main-chart" height="270"></canvas>
                </div>
              </div>
            </div>
            <!-- /.card-->
            <!-- /.row-->
          </div>
        </div>
      </main><jsp:include page="aside.jsp"></jsp:include> 
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
    <!-- CoreUI and necessary plugins-->
   </body>
</html>