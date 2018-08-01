<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
<script src="js/select-widget-min.js"></script>

<html lang="en">
 <jsp:include page="head.jsp"/>
 <style>
  #feedback { font-size: 1.4em; }
  #selectable .ui-selecting { background: #FECA40; }
  #selectable .ui-selected { background: #F39814; color: white; }
  #selectable { list-style-type: none; margin: 0; padding: 0; width: 60%; }
  #selectable li { margin: 8px; padding: 0.4em; font-size: 1.5em; height: 40px; }
  </style>
 <script>
    
 
</script>
  <script>
  $(document).ready(function(){
	
	  $("select").selectWidget({
	 
	  change : function (changes) {
	    return changes;
	  },scrollHeight : 250

	  });
	
	  });


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
<div>testjob_id:<select name="testjob_id">
    <option value="50">50</option>
    <option value="51">51</option>
    <option value="52">52</option>
    <option value="53">53</option>
  </select><br>
<!-- test_case_id:<input type="text" name="test_case_id" id="test_case_id"><br> -->
test_case_id:<input type="text" name="test_case_id" id="test_case_id"><br>


<!--      <label>test_case_id:</label> -->
<!-- <input id="test_case_id" type="text">  -->
<!-- <label>testjob_id :</label> -->
<!-- <input id="testjob_id" type="text"> -->





<!--    <ol id="selectable"> -->
<!--   <li class="ui-widget-content">Item 1</li> -->
<!--   <li class="ui-widget-content">Item 2</li> -->
<!--   <li class="ui-widget-content">Item 3</li> -->
<!--   <li class="ui-widget-content">Item 4</li> -->
<!--   <li class="ui-widget-content">Item 5</li> -->
<!--   <li class="ui-widget-content">Item 6</li> -->
<!--   <li class="ui-widget-content">Item 7</li> -->
<!-- </ol> -->
<input id="submit" type="button" name="submit" value="submit">
</div>
</form>
<script>
    $(document).ready(function(){
        // click on button submit
        $("#submit").on('click', function(){
        	
        	var tt={
                	test_case_id: $("#test_case_id").val(),
                	testjob_id: $("#testjob_id").val()
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