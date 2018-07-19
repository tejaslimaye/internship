

var execSummaryDevices;
var execSummaryFeatures;
var execSummaryRunning;
var execHistory;
var execServerDetails;
var execFeaturesDetails;
var execTestCasesDetails;
var execTestJobsDetails;
/*var feature_id= [
    { "id": 1,"test_feature_id": "1"},
    { "id": 3,"test_feature_id": "3"}
];
*/
var execFeature;
var cardChart1;
var cardChart2;
var cardChart4;
var mainChart;

function showDetailsDialog (dialogType,server) {

    $("#gm_port").val(server.Gm_port);
    $("#sdk_port").val(server.Sdk_port);
    $("#verify_port").val(server.Verify_port);
    $("#api_port").val(server.Api_port);
    $("#ip_address").val(server.Ip_address);
    $("#os_version").val(server.Os_version);
    $("#console_user").val(server.Console_user);
    $("#console_password").val(server.Console_password);
    $("#enterprise_id").val(server.Enterprise_id);
    $("#enterprise_user").val(server.Enterprise_user);
    $("#enterprise_password").val(server.Enterprise_password);
    $("#server_user").val(server.Server_user);
    $("#server_password").val(server.Server_password);
    $("#agent_info").val(server.Agent_info);

    

    formSubmitHandler = function() {
    	saveServer(server, dialogType === "Add");
    };

    $("#detailsDialog").dialog("option", "title", dialogType + " Server")
            .dialog("open");
};


function fetchDetails(){
	fetchLiveTests();
	fetchFailuresByFeatures();
	fetchFailuresByDevices();
	fetchExecutionHistory();
	
   
}

function fetchFeatures()
{
	$("#jsGrid_Features").jsGrid({
		  width: "100%",
		  height: "auto",

		 inserting: true,
 	     editing: true,
 	     sorting: true,
 	     paging: true,
 	      
 	      autoload:   true,
 	     paging:     true,
 	     pageSize:   10,
 	     pageButtonCount: 5,
 	     pageIndex:  1,

 	     
 	    
	
 		  controller: {
		    loadData: function(filter) {
		    return  $.ajax({
		        url: "http://localhost:8080/automation/getFeatures.htm",
		        dataType: "json",
		        method: "POST",
		        });
		    },
		    insertItem: function (item) {
		    	insertFeature(item);
		    	
		 	   },
		 	  onItemInserted: function(args)
		 	  {
		 		  alert(11);
		 		  location.reload(true);
		 	  }
		  },
	        fields: [
		 	            { name: "feature_id", type: "number", width: 10},
		 	            { name: "feature_name", type: "text", width: 50, validate:"required"},
		 	            { name: "feature_target", type: "text", width: 50, validate:"required"},
		 	            
		 	            { type: "control" }
		 	        ]
	});
}  

function insertFeature(item)
{

     $.ajax({
         type: "POST",
         url: "http://localhost:8080/automation/addFeature.htm",
         data: "{\"feature_name\":\""+item.feature_name + "\",\"feature_target\":\""+item.feature_target +"\"}",
         success: function(response)
         {
        	 if(response.response_code==1)
        		 {
        		 	alert("error while inserting feature : Check server logs");
        		 }
         },
         error: function(response)
         {
        	 
         }
     });
     
}

function fetchTestCases()
{
	$("#jsGrid_TestCases").jsGrid({
		width: "100%",
	        height: "auto",

		 inserting: true,
 	     editing: true,
 	     sorting: true,
 	     paging: true,
 	      
 	     autoload:   true,
 	    paging:     true,
	     pageSize:   10,
	     pageButtonCount: 5,
	     pageIndex:  1,


 	    
 	     
 	    
	
 		  controller: {
		    loadData: function(filter) {
		    return  $.ajax({
		        url: "http://localhost:8080/automation/getTestCase.htm",
		        dataType: "json",
		        method: "POST",
		        });
		    },
		   insertItem: function (item) {
		    	insertTestCase(item);
		    	
		 	   },
		 	  onItemInserted: function(args)
		 	  {
		 		  alert(11);
		 		  location.reload(true);
		 	  }
		  },
	        fields: [
		 	            { name: "testcase_id", type: "number", width: 30},
		 	            { name: "testcase_name", type: "text", width: 100, validate:"required"},
		 	          { name: "created_time", type: "text", width: 50},
		 	            { name: "update_time", type: "text", width: 50},
		 	           { name: "test_feature_id", type: "select",validate:"required",width: 100,items:selectFeature(item),valueField: "item.feature_id", 
		 	               textField: "item.feature_name",insertTemplate: function () {
		 	               
		 	                  var $insertControl = jsGrid.fields.select.prototype.insertTemplate.call(this);

		 	                  
		 	                  $insertControl.change(function () {
		 	                      var selectedValue = $(this).val();

		 	                      
		 	                  });

		 	                  return $insertControl;}},
		 	            { name: "testcase_desc", type: "text", width: 100, validate:"required"},
		 	           { type: "control" }
		 	        ]
	});
	
}
 
function insertTestCase(item)
{

     $.ajax({
         type: "POST",
         url: "http://localhost:8080/automation/updateTestCase.htm",
         data: "{\"testcase_name\":\""+item.testcase_name + "\",\"update_time\":\""+item.update_time+"\",\"test_feature_id\":\""+item.test_feature_id+"\",\"testcase_desc\":\""+item.testcase_desc+"\"}",
         success: function(response)
         {
        	 if(response.response_code==1)
        		 {
        		 	alert("error while inserting TestCase : Check server logs");
        		 }
         },
         error: function(response)
         {
        	 
         }
     });
     
}

function selectFeature(item)
{$.ajax({
	 url: "http://localhost:8080/automation/getFeatures.htm",
     dataType: "json",
     method: "POST",
     data:"{\"feature_id\":\""+item.feature_id + "\",\"feature_name\":\""+item.feature_name + "\"}",
     success:
    function(response){
	          //alert("done: " + response);
	        	execFeature = JSON.parse(response);
     }
     });
}


function fetchServers()
{		$("#jsGrid_Servers").jsGrid({
	width: "100%",
    height: "auto",

    
 
    	
    	editing: true,
    	
    	paging: true,
   
    	autoload:   true,
    	paging:     true,
    	pageSize:   10,
    	pageButtonCount: 5,
    	pageIndex:  1,

    	  /* rowClick: function(args) {
               showDetailsDialog("Edit", args.item);
           },
     
          insertItem: function (item) {
		    	insertServer(item);
		    	$("#jsGrid_Servers").jsGrid("refresh");
		    	
		 	   },
		 	  onItemInserted: function(args)
		 	  {
		 		  alert(11);
		 		  location.reload(true);
		 		 $("#jsGrid_Servers").jsGrid("refresh");
		 	  },*/
    	controller: {
    	loadData: function(filter) {
    	return  $.ajax({
        url: "http://localhost:8080/automation/getServer.htm",
        dataType: "json",
        method: "POST",
        });
    },
    
 	   	
    },
    
	 	       
	 	        fields: [
	 	            { name: "server_id", type: "number", width: 10},
	 	        
	 	            { name: "gm_port", type: "number", width: 50, validate:"required" },
	 	           { name: "sdk_port", type: "number", width: 50, validate:"required" },
	 	            { name: "verify_port", type: "number", width: 50, validate:"required" },
	 	            { name: "api_port", type: "number", width: 50, validate:"required" },
	 	           { name: "ip_address", type: "number", width: 50, validate:"required"},
	 	            { name: "os_version", type: "text", width: 50, validate:"required"},
	 	            { name: "console_user", type: "text", width: 50, validate:"required"},
	 	            { name: "console_password", type: "text", width: 50, validate:"required"},
	 	            { name: "enterprise_id", type: "text", width: 50, validate:"required"},
	 	            { name: "enterprise_user", type: "text", width: 50, validate:"required"},
	 	            { name: "enterprise_password", type: "text", width: 50, validate:"required"},
	 	            { name: "server_user", type: "text", width: 50, validate:"required"},
	 	            { name: "server_password", type: "text", width: 50, validate:"required"},
	 	            { name: "agent_info", type: "text", width: 50, validate:"required"},
	 	            { type: "control", modeSwitchButton: false,
	 	                editButton: false,
	 	                headerTemplate: function() {
	 	                    return $("<button>").attr("type", "button").text("Add")
	 	                            .on("click", function () {
	 	                                alert(2);
	 	                               console.log(detailsDialog);
	 	                                detailsDialog.dialog("open");
	 	                            	   
	 	                              
	 	                              alert(3);
	 	                               /*$("#gm_port").val(server.Gm_port);
	 	                              $("#sdk_port").val(server.Sdk_port);
	 	                              $("#verify_port").val(server.Verify_port);
	 	                              $("#api_port").val(server.Api_port);
	 	                              $("#ip_address").val(server.Ip_address);
	 	                              $("#os_version").val(server.Os_version);
	 	                              $("#console_user").val(server.Console_user);
	 	                              $("#console_password").val(server.Console_password);
	 	                              $("#enterprise_id").val(server.Enterprise_id);
	 	                              $("#enterprise_user").val(server.Enterprise_user);
	 	                              $("#enterprise_password").val(server.Enterprise_password);
	 	                              $("#server_user").val(server.Server_user);
	 	                              $("#server_password").val(server.Server_password);
	 	                              $("#agent_info").val(server.Agent_info);

	 	                              formSubmitHandler = function() {
	 	                              	saveServer(server, dialogType === "Add");
	 	                              };

	 	                              $("#detailsDialog").dialog("option", "title", dialogType + " Server")
	 	                                      .dialog("open");

	 	                            
*
*
*/
	 	                                
	 	                                
	 	                                
	 	                            });}							}	]


				});
var detailsDialog  = $("#detailsDialog").dialog({
    autoOpen: false,
    width:"50%",
    height:400,
    
 /*   position: { 
        my: "center",
        at: "center",
        of: $("#jsGrid_Servers")},*/
   close: function() {
        $("#detailsForm").validate().resetForm();
        $("#detailsForm").find(".error").removeClass("error");
    }

});

$("#detailsForm").validate({
    rules: {
        gm_port: "required",
        sdk_port: "required",
        verify_port: "required",
            api_port:"required",
            ip_address: "required",
            os_version: "required",
           console_user:"required",
            console_password:"required",
           enterprise_id:"required",
            enterprise_user:"required",
           enterprise_password:"required",
            server_user:"required",
          server_password:"required",
            agent_info:"required"
    },
    messages: {
        
        
        gm_port: "Please enter gm_port",
        sdk_port: "Please enter sdk_port",
        verify_port: "Please enter verify_port",
            api_port:"Please enter api_port" ,
            ip_address: "Please enter ip_address",
            os_version: "Please enter os_version ",
           console_user:"Please enter console_user",
            console_password:"Please enter console_password",
           enterprise_id:"Please enter enterprise_id",
            enterprise_user:"Please enter enterprise_user",
           enterprise_password:"Please enter enterprise_password",
            server_user:"Please enter server_user",
          server_password:"Please enter server_password",
            agent_info:"Please enter agent_info"
    }, submitHandler: function() {
        formSubmitHandler();
    }
});
var formSubmitHandler = $.noop;



var saveServer = function(server, isNew) {
    $.extend(server, {
    	Gm_port:  parseInt($("#gm_port").val(),10),
    	   Sdk_port: parseInt($("#sdk_port").val(),10),
    	   Verify_port: parseInt($("#verify_port").val(),10),
    	  Api_port:  parseInt($("#api_port").val(),10),
    	   Ip_address: parseInt($("#ip_address").val(),10),
    	   Os_version: $("#os_version").val(),
    	   Console_user: $("#console_user").val(),
    	   Console_password: $("#console_password").val(),
    	   Enterprise_id: $("#enterprise_id").val(),
    	   Enterprise_user: $("#enterprise_user").val(),
    	   Enterprise_password: $("#enterprise_password").val(),
    	  Server_user: $("#server_user").val(),
    	   Server_password: $("#server_password").val(),
    	   Agent_info: $("#agent_info").val()
    });

}
    $("#detailsDialog").dialog("close");
}




/*function insertServer(item){
 $.ajax({
     type: "POST",
     url: "http://localhost:8080/automation/updateServerDetails.htm",
     data: "{\"gm_port\":\""+item.gm_port+"\",\" sdk_port\":\""+item.sdk_port+"\",\"verify_port\":\""+item.verify_port+"\",\"api_port\":\""+item.api_port+"\",\"ip_address\":\""+item.ip_address + "\",\" os_version\":\""+item.os_version+ "\",\"console_user\":\""+item.console_user+"\",\"console_password\":\""+item.console_password+"\",\"enterprise_id\":\""+item.enterprise_id+"\",\"enterprise_user\":\""+item.enterprise_user+"\",\"enterprise_password\":\""+item.enterprise_password+"\",\"server_user\":\""+item.server_user+"\",\"server_password\":\""+item.server_password+"\",\"agent_info\":\""+item.agent_info+"\"}",
     success: function(response)
     {
    	 if(response.response_code==1)
    		 {
    		 	alert("error while inserting Server : Check server logs");
    		 }
     },
     error: function(response)
     {
    	 
     }
 });
}*/









function fetchTestJobs()
{
	$("#jsGrid_TestJobs").jsGrid({
		width: "100%",
	        height: "auto",

		 inserting: true,
 	     editing: true,
 	     sorting: true,
 	     paging: true,
 	      
 	     autoload:   true,
 	    paging:     true,
	     pageSize:   10,
	     pageButtonCount: 5,
	     pageIndex:  1,


	
 		  controller: {
		    loadData: function() {
		    	var deferred = $.Deferred();
		    return  $.ajax({
		        url: "http://localhost:8080/automation/getALLTestJobDetails.htm",
		        dataType: "json",
		        method: "POST",
		        success: function(data){
                    deferred.resolve(data);
		        }
		        });

            return deferred.promise();
                },
		     
		      
		   
		   insertItem: function (item) {
		    	insertTestJob(item);
		    	$("#jsGrid_TestJobs").jsGrid("refresh");
		    	
		 	   },
		 	  onItemInserted: function(args)
		 	  {
		 		  alert(11);
		 		  location.reload(true);
		 		 $("#jsGrid_TestJobs").jsGrid("refresh");
		 	  }
		  },
	        fields: [
		 	            { name: "testjob_id", type: "number", width: 30},
		 	            { name: "test_job_description", type: "text", width: 100, validate:"required"},
		 	          { name: "created_time", type: "text", width: 100},
		 	            { name: "updated_time", type: "text", width: 100},
		 	          { name: "status", type: "text", width: 100, validate:"required"},
		 	         { name: "server_id", type: "number", width: 30, validate:"required"},
		 	          { name: "lib_id", type: "number", width: 30, validate:"required"},
		 	            { name: "auto_create_on_new_device", type: "number", width: 30, validate:"required"},
		 	           { type: "control" }
		 	        ]
	});
	
}

function insertTestJob(item)
{

     $.ajax({
         type: "POST",
         url: "http://localhost:8080/automation/updateTestJobDetails.htm",
         data: "{\"test_job_description\":\""+item.test_job_description + "\",\"updated_time\":\""+item.updated_time+"\",\"status\":\""+item.status+"\",\"server_id\":\""+item.server_id+"\",\"lib_id\":\""+item.lib_id+"\",\"auto_create_on_new_device\":\""+item.auto_create_on_new_device+"\"}",
         success: function(response)
         {
        	 if(response.response_code==1)
        		 {
        		 	alert("error while inserting TestJob : Check server logs");
        		 }
         },
         error: function(response)
         {
        	 
         }
     });
     
}
function fetchExecutionHistory()
{
	var min = 0, max = 0;

	$.ajax({ url: "http://localhost:8080/automation/getExceutionHistory.htm",
	        context: document.body,
	        method: "POST",
	        success: function(response){
	          //alert("done: " + response);
	        	execHistory = JSON.parse(response);
	        	
	        	var labels=[];
	        	var failedcounts=[]; 
	        	var passedcounts=[]; 
	        	var unableToTestcounts=[]; 
	        	
	        	var failedList = execHistory.failedList;
	        	var passedList = execHistory.passedList;
	        	var unableToTestList = execHistory.unableToTestList;
	        	
	        	failedList.forEach(function(item, index, array) {
	        		  console.log(item, index);
	        		  labels.push(item.execution_status);
	        		  failedcounts.push(item.execution_count);
	        		  if(min==0)
	        		  {
	        			  min = item.execution_count;
	        			  max = item.execution_count;
	        		  }
	        		  
	        		  if(min>=item.execution_count)
	        			  min = item.execution_count;
	        		  if(max<=item.execution_count)
	        			  max = item.execution_count;
	        		  
	        	});
	        	
	        	passedList.forEach(function(item, index, array) {
	        		  console.log(item, index);
	        		  //labels.push(item.execution_status);
	        		  passedcounts.push(item.execution_count);
	        		  
	        		  if(min>=item.execution_count)
	        			  min = item.execution_count;
	        		  if(max<=item.execution_count)
	        			  max = item.execution_count;
	        		  
	        	});
	        	
	
	        	unableToTestList.forEach(function(item, index, array) {
	        		  console.log(item, index);
	        		  //labels.push(item.execution_status);
	        		  unableToTestcounts.push(item.execution_count);
	        		  
	        		  if(min>=item.execution_count)
	        			  min = item.execution_count;
	        		  if(max<=item.execution_count)
	        			  max = item.execution_count;
	        		  
	        	});
	        	
	        	
	
	        	//alert(min + ":" + max);
	        	
	        	mainChart = new Chart($('#main-chart'), {
	        		  type: 'line',
	        		  data: {
	        		    labels: labels,
	        		    datasets: [{
	        		      label: 'Unable to Test Count',
	        		      backgroundColor: hexToRgba(getStyle('--info'), 10),
	        		      borderColor: getStyle('--info'),
	        		      pointHoverBackgroundColor: '#fff',
	        		      borderWidth: 2,
	        		      data: unableToTestcounts
	        		    }, {
	        		      label: 'Passed Test Count',
	        		      backgroundColor: 'transparent',
	        		      borderColor: getStyle('--success'),
	        		      pointHoverBackgroundColor: '#fff',
	        		      borderWidth: 2,
	        		      data: passedcounts
	        		    }, {
	        		      label: 'Failed Test Count',
	        		      backgroundColor: 'transparent',
	        		      borderColor: getStyle('--danger'),
	        		      pointHoverBackgroundColor: '#fff',
	        		      borderWidth: 1,
	        		      borderDash: [8, 5],
	        		      data: failedcounts
	        		    }]
	        		  },
	        		  options: {
	        		    maintainAspectRatio: false,
	        		    legend: {
	        		      display: false
	        		    },
	        		    scales: {
	        		      xAxes: [{
	        		        gridLines: {
	        		          drawOnChartArea: false
	        		        }
	        		      }],
	        		      yAxes: [{
	        		        ticks: {
	        		          beginAtZero: true,
	        		          maxTicksLimit: 5,
	        		          stepSize: Math.ceil(max/ 5),
	        		          max: max
	        		        }
	        		      }]
	        		    },
	        		    elements: {
	        		      point: {
	        		        radius: 0,
	        		        hitRadius: 10,
	        		        hoverRadius: 4,
	        		        hoverBorderWidth: 3
	        		      }
	        		    }
	        		  }
	        		});

	        		                      
	        },
	        error: function(e){
	        	alert("error:" + eval(e));}
	   
	})
}



function fetchFailuresByDevices()
{
	var min = 0; max = 0;

	$.ajax({ url: "http://localhost:8080/automation/getFailuresByDevices.htm",
	        context: document.body,
	        method: "POST",
	        success: function(response){
	          //alert("done: " + response);
	        	execSummaryDevices = JSON.parse(response);
	        	var labels=[];
	        	var counts=[]; 
	        	
	        	execSummaryDevices.execSummaryDetails.forEach(function(item, index, array) {
	        		  console.log(item, index);
	        		  labels.push(item.execution_status);
	        		  counts.push(item.execution_count);
	        		  if(min==0)
	        		  {
	        			  min = item.execution_count;
	        			  max = item.execution_count;
	        		  }
	        		  
	        		  if(min>=item.execution_count)
	        			  min = item.execution_count;
	        		  if(max<=item.execution_count)
	        			  max = item.execution_count;
	        		  
	        	});
	        	
	        	if(min<10) 
	        		min = 0;
	        	if(max<10)
	        	{
	        		max = max + 1;
	        	}
	        	else if (max < 100)
	        	{
	        		max = max + 10;
	        	}
	        	else if(max < 1000)
	        	{
	        		max = max + 100;
	        	}
	        	
	        	//alert(min + ":" + max);
	        	cardChart2 = new Chart($('#card-chart2'), {
	        		  type: 'bar',
	        		  data: {
	        		    labels: labels,
	        		    datasets: [{
	        		      label: 'Top Fails by Device',
	        		      backgroundColor: 'rgba(255,255,255,.2)',
	        		      borderColor: 'rgba(255,255,255,.55)',
	        		      data: counts
	        		    }]
	        		  },
	        		  options: {
	        		    maintainAspectRatio: false,
	        		    legend: {
	        		      display: false
	        		    },
	        		    scales: {
	        		      xAxes: [{
	        		        display: false,
	        		        barPercentage: 0.6
	        		      }],
	        		      yAxes: [{
	        		        display: false,
	        		        ticks: {
		        		          display: false,
		        		          min: min,
		        		          max: max
		        		        }
	        		      }]
	        		    }
	        		  }
	        		}); // eslint-disable-next-line no-unused-vars

	        	
	        	document.getElementById("device_fails").innerHTML = execSummaryDevices.total_Count;
	                      
	        },
	        error: function(e){
	        	alert("error:" + eval(e));}
	   
	})
}


function fetchFailuresByFeatures()
{
	var min = 0; max = 0;

	$.ajax({ url: "http://localhost:8080/automation/getFailuresByFeatures.htm",
	        context: document.body,
	        method: "POST",
	        success: function(response){
	          //alert("done: " + response);
	        	execSummaryFeatures = JSON.parse(response);
	        	var labels=[];
	        	var counts=[]; 
	        	
	        	execSummaryFeatures.execSummaryDetails.forEach(function(item, index, array) {
	        		  console.log(item, index);
	        		  labels.push(item.execution_status);
	        		  counts.push(item.execution_count);
	        		  if(min==0)
	        		  {
	        			  min = item.execution_count;
	        			  max = item.execution_count;
	        		  }
	        		  
	        		  if(min>=item.execution_count)
	        			  min = item.execution_count;
	        		  if(max<=item.execution_count)
	        			  max = item.execution_count;
	        		  
	        	});
	        	
	        	if(max<10)
	        	{
	        		max = max + 1;
	        	}
	        	else if (max < 100)
	        	{
	        		max = max + 10;
	        	}
	        	else if(max < 1000)
	        	{
	        		max = max + 100;
	        	}
	        	
	        	if(min<10) 
	        		min = 0;
	        	
	        	cardChart4 = new Chart($('#card-chart4'), {
	        		  type: 'bar',
	        		  data: {
	        		    labels: labels,
	        		    datasets: [{
	        		      label: 'Top Fails by Feature',
	        		      backgroundColor: 'rgba(255,255,255,.2)',
	        		      borderColor: 'rgba(255,255,255,.55)',
	        		      data: counts
	        		    }]
	        		  },
	        		  options: {
	        		    maintainAspectRatio: false,
	        		    legend: {
	        		      display: false
	        		    },
	        		    scales: {
	        		      xAxes: [{
	        		        display: false,
	        		        barPercentage: 0.6
	        		      }],
	        		      yAxes: [{
		        		        display: false,
		        		        ticks: {
			        		          display: false,
			        		          min: min,
			        		          max: max
			        		        }
		        		       }]
	        		    }
	        		  }
	        		}); // eslint-disable-next-line no-unused-vars

	        	
	        	document.getElementById("feature_fails").innerHTML = execSummaryFeatures.total_Count;
	                      
	        },
	        error: function(e){
	        	alert("error:" + eval(e));}
	   
	})
}

function fetchLiveTests()
{
	var min = 0; max = 0;

	$.ajax({ url: "http://localhost:8080/automation/getExecutionSummary.htm",
	        context: document.body,
	        method: "POST",
	        success: function(response){
	          //alert("done: " + response);
	        	execSummaryRunning = JSON.parse(response);
	        	var labels=[];
	        	var counts=[]; 
	        	
	        	execSummaryRunning.execSummaryDetails.forEach(function(item, index, array) {
	        		  console.log(item, index);
	        		  labels.push(item.execution_status);
	        		  counts.push(item.execution_count);
	        		  if(min==0)
	        		  {
	        			  min = item.execution_count;
	        			  max = item.execution_count;
	        		  }
	        		  
	        		  if(min>=item.execution_count)
	        			  min = item.execution_count;
	        		  if(max<=item.execution_count)
	        			  max = item.execution_count;
	        		  
	        	});
	        	
	        	if(max<10)
	        	{
	        		max = max + 1;
	        	}
	        	else if (max < 100)
	        	{
	        		max = max + 10;
	        	}
	        	else if(max < 1000)
	        	{
	        		max = max + 100;
	        	}
	        	
	        	cardChart1= new Chart($('#card-chart1'), {
	        		  type: 'line',
	        		  data: {
	        		    labels: labels,
	        		    datasets: [{
	        		      label: 'Live Tests',
	        		      backgroundColor: getStyle('--primary'),
	        		      borderColor: 'rgba(255,255,255,.55)',
	        		      data: counts
	        		    }]
	        		  },
	        		  options: {
	        		    maintainAspectRatio: false,
	        		    legend: {
	        		      display: false
	        		    },
	        		    scaleShowValues: true,
	        		    scales: {
	        		      xAxes: [{
	        		        gridLines: {
	        		          color: 'transparent',
	        		          zeroLineColor: 'transparent'
	        		        },
	        		        ticks: {
	        		          fontSize: 2,
	        		          fontColor: 'transparent'
	        		        }
	        		      }],
	        		      yAxes: [{
	        		        display: false,
	        		        ticks: {
	        		          display: false,
	        		          min: min,
	        		          max: max
	        		        }
	        		      }]
	        		    },
	        		    elements: {
	        		      line: {
	        		        borderWidth: 1
	        		      },
	        		      point: {
	        		        radius: 4,
	        		        hitRadius: 8,
	        		        hoverRadius: 6
	        		      }
	        		    }
	        		  }
	        		}); // eslint-disable-next-line no-unused-vars

	        	
	        	document.getElementById("test_runs").innerHTML = execSummaryRunning.total_Count;
	                      
	        },
	        error: function(e){
	        	alert("error:" + eval(e));}
	   
	})
}