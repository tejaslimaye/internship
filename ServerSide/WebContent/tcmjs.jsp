<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">

<html lang="en">
 <jsp:include page="head.jsp"/>
 <style>
  #feedback { font-size: 1.4em; }
  #selectable .ui-selecting { background: #FECA40; }
  #selectable .ui-selected { background: #F39814; color: white; }
  #selectable { list-style-type: none; margin: 0; padding: 0; width: 15%; }
  #selectable li { margin: 8px; padding: 0.4em; font-size: 1.5em; height: 40px; }
  </style>
 <script>
    
 
</script>
  <script>
  
   $( function() {
     $( "#selectable" ).selectable();
   } );
  </script>
  <body >
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
                <i class="icon-note"></i>Job Mapping</div>
                
              <div class="card-body">
              	<form id="form" name="form">
<h3>Enter Test job!</h3>
<div>testjob_id:<select name="testjob_id" id="dropdown" >
<!--     <option value="volvo">50</option> -->
<!--     <option value="saab">51</option> -->
<!--     <option value="fiat">52</option> -->
<!--     <option value="audi">53</option> -->
  </select><br>
test_case_id:<input type="text" name="test_case_id" id="test_case_id"><br> 
<!-- test_case_id:<ol id="selectable" ><br>  -->


<!--      <label>test_case_id:</label> -->
<!-- <input id="test_case_id" type="text">  -->
<!-- <label>testjob_id :</label> -->
<!-- <input id="testjob_id" type="text"> -->

<!--  <ol id="selectable"> -->
<!--  <li class="ui-widget-content">Item 1</li> 
<li class="ui-widget-content">Item 2</li> 
<li class="ui-widget-content">Item 3</li> 
<li class="ui-widget-content">Item 4</li> 
<li class="ui-widget-content">Item 5</li> 
<li class="ui-widget-content">Item 6</li> 
<li class="ui-widget-content">Item 7</li> -->
</ol> 
<input id="submit" type="button" name="submit" value="submit">
</div>
</form>
<script>
$.ajax({
            type: "POST",
            url: "http://localhost:8080/automation/getALLTestJobDetails.htm",
            success: function(data)
            {
                helpers.buildDropdown(
                    jQuery.parseJSON(data),
                    $('#dropdown'),
                    'Select an option'
                );
            }
        });
</script>

<!-- <script>
 $.ajax({ 
            type: "POST", 
             url: "http://localhost:8080/automation/getTestCase.htm",
            success: function(data)
           { 
                helpers1.buildDropdown(
                    jQuery.parseJSON(data),
                    $('#selectable'), 
                   'Select an option'
                 ); 
           } 
        }); 
</script> -->

<!-- <script>
var helpers1 =
{
    buildDropdown: function(result, selectable, emptyMessage)
    {
        // Remove current options
        selectable.html('');
        // Add the empty option with the empty message
        selectable.append('<option value="">' + emptyMessage + '</option>');
        // Check result isnt empty
        if(result != '')
        {
//             Loop through each of the results and append the option to the dropdown
            $.each(result, function(k, v) {
            	selectable.append('<option value="' + v.test_case_id + '">' + v.test_case_name + '</option>');
            });
        }
    }
}
</script>-->



<script>
var helpers =
{
    buildDropdown: function(result, dropdown, emptyMessage)
    {
        // Remove current options
        dropdown.html('');
        // Add the empty option with the empty message
        dropdown.append('<option value="">' + emptyMessage + '</option>');
        // Check result isnt empty
        if(result != '')
        {
            // Loop through each of the results and append the option to the dropdown
            $.each(result, function(k, v) {
                dropdown.append('<option value="' + v.testjob_id + '">' + v.test_job_description + '</option>');
            });
        }
    }
}
</script>
<script>
    $(document).ready(function(){
        // click on button submit
        $("#submit").on('click', function(){
        	
        	var tt={
                	test_case_id: $("#test_case_id").val(),
                	testjob_id: $("#dropdown").val()
                }
        	
            // send ajax
            $.ajax({
                url: 'http://localhost:8080/automation/addTCMJ.htm', // url where to submit the request
                type : "POST", // type of action POST || GET
                dataType : "json", // data type
                data : JSON.stringify(tt), // post data || get data
                success : function(result) {
                    // you can see the result from the console
                    // tab of the developer tools
                    console.log(result);
                },
                error: function(xhr, resp, text) {
                    console.log(xhr, resp, text);
                }
            })
        });
    });

</script>
              
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