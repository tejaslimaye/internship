<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html lang="en">
 <jsp:include page="head.jsp"/>
 
 <script>
    
 
</script>
<meta name="viewport" content="width=device-width, initial-scale=1">
 <style>
    label, input { display:block; }
    input.text { margin-bottom:12px; width:50%; padding: .4em; }
    fieldset { padding:0; border:0; width:50%; margin-top:25px; }
    h1 { font-size: 1.2em; margin: .6em 0; }
    div#users-contain { width: 350px; margin: 20px 0; }
    div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
    div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
    .ui-dialog .ui-state-error { padding: .3em; }
    .validateTips { border: 1px solid transparent; padding: 0.3em; }
    .JsGridRow{text-overflow: ellipsis; word-wrap: initial;overflow-x: hidden;}
  </style>

  <body onload="fetchServers();" class="app header-fixed sidebar-fixed aside-menu-fixed sidebar-lg-show">
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
                <i class="icon-screen-desktop"></i> Servers</div>
                
              <div class="card-body">
              	<div id="jsGrid_Servers">
 				<!-- <script>
 				  $( function() {
 					    $( "#detailsDialog" ).dialog();
 					  } );
  </script> -->
<div id="detailsDialog" title="Add Server">
  <p class="validateTips">All form fields are required.</p>
 
  <form>
    <fieldset>
      <label for="gm_port:">gm_port:</label>
      <input type="text" name="gm_port" id="gm_port"  class="text ui-widget-content ui-corner-all">
      <label for="sdk_port">sdk_port:</label>
      <input type="text" name="sdk_port" id="sdk_port"  class="text ui-widget-content ui-corner-all">
      <label for="verify_port">verify_port:</label>
      <input type="text" name="verify_port" id="verify_port"  class="text ui-widget-content ui-corner-all">
 <label for="api_port">api_port:</label>
      <input type="text" name="api_port" id="api_port"  class="text ui-widget-content ui-corner-all">
      <label for="ip_address"> ip_address:</label>
      <input type="text" name="ip_address" id="ip_address"  class="text ui-widget-content ui-corner-all">
 
      <label for="os_version">os_version:</label>
      <input type="text" name="os_version" id="os_version"  class="text ui-widget-content ui-corner-all">
      <label for="console_user">console_user:</label>
      <input type="text" name="console_user" id="console_user"  class="text ui-widget-content ui-corner-all">
  <label for="console_password">console_password:</label>
      <input type="text" name="console_password" id="console_password"  class="text ui-widget-content ui-corner-all">
  <label for="enterprise_id">enterprise_id:</label>
      <input type="text" name="enterprise_id" id="enterprise_id"  class="text ui-widget-content ui-corner-all">
  <label for="enterprise_user">enterprise_user:</label>
      <input type="text" name="enterprise_user" id="enterprise_user"  class="text ui-widget-content ui-corner-all">
  <label for="enterprise_password">enterprise_password:</label>
      <input type="text" name="enterprise_password" id="enterprise_password"  class="text ui-widget-content ui-corner-all">
<label for="server_user">server_user:</label>
      <input type="text" name="server_user" id="server_user"  class="text ui-widget-content ui-corner-all">
  <label for="server_password">server_password:</label>
      <input type="text" name="server_password" id="server_password"  class="text ui-widget-content ui-corner-all">
 <label for="agent_info">agent_info:</label>
      <input type="text" name="agent_info" id="agent_info"  class="text ui-widget-content ui-corner-all">

<button id="showDetailsDialog">Add2</button>
 <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
    </fieldset>
  </form>
</div>
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