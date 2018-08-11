var baseURL=window.location.protocol + "://"+window.location.hostname+":"+window.location.port;
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
var feature;
var feat;
var execFeature;
var cardChart1;
var cardChart2;
var cardChart4;
var mainChart;
var servers;
var libraries;
var testjob;
var testcase;
function fetchDetails(){
	fetchLiveTests();
	fetchFailuresByFeatures();
	fetchFailuresByDevices();
	fetchExecutionHistory();
	
   
}
function fetchServers()
{		$("#jsGrid_Servers").jsGrid({
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
        url:  "/automation/getServer.htm",
        dataType: "json",
        method: "POST",
        });
    },
    	insertItem: function (item) {
    		insertServer(item);
    	
    	},
    	onItemInserted: function(args)
    	{
    		alert(11);
    		location.reload(true);
    	}
    	
     },
  	        fields: [
	 	            { name: "server_id", type: "number", width: 10},
	 	        
//	 	            { name: "gm_port", type: "number", width: 50 },
//	 	           { name: "sdk_port", type: "number", width: 50},
//	 	            { name: "verify_port", type: "number", width: 50},
//	 	            { name: "api_port", type: "number", width: 50},
	 	           { name: "ip_address", type: "number", width: 50, validate:"required"},
	 	            { name: "os_version", type: "text", width: 50, validate:"required"},
//	 	            { name: "console_user", type: "text", width: 50},
//	 	            { name: "console_password", type: "text", width: 50},
//	 	            { name: "enterprise_id", type: "text", width: 50},
//	 	            { name: "enterprise_user", type: "text", width: 50},
	 	            { name: "enterprise_password", type: "text", width: 50, validate:"required"},
//	 	            { name: "server_user", type: "text", width: 50},
//	 	            { name: "server_password", type: "text", width: 50},
	 	            { name: "agent_info", type: "text", width: 50, validate:"required", css:"JsGridRow"},
	 	           { type: "control", width: 10}
	 	            ]


				});


}
function insertServer(item){
 $.ajax({
     type: "POST",
     url: "/automation/updateServerDetails.htm",
     data: "{\"ip_address\":\""+item.ip_address + "\",\"os_version\":\""+item.os_version+ "\",\"enterprise_password\":\""+item.enterprise_password+"\",\"agent_info\":\""+item.agent_info+"\"}",
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
}


var executionsData;


function fetchExecutionAnalysis()
{
	 $.ajax({
	        url: "/automation/getExecutionAnalysis.htm",
	        dataType: "json",
	        method: "POST",
	        success: function(response)
	        {
	       	 if(response.response_code==1)
	       	 {
	       		 	alert("error while loading Executions: Check server logs");
	       		 	return;
	       	 }
	       	executionsData = response.executions;
	       	loadExecutionAnalysis();
	       	
	        },
	        error: function(response)
	        {
	       	 	alert(response);
	        }
	        
	 });
    	
	 
}

function loadExecutionAnalysis()
{
	
	alert(executionsData[0].server_id);
	alert(jQuery("#grid")); 
	$("#grid").jqGrid('jqPivot',
			 executionsData,
			 // pivot options
			 {

			   xDimension : [
			    {dataName: 'device_os', label : 'Server-Device-Library', width:200}
				
			               ],
			   yDimension : [
			   
							  {dataName: 'execution_status',label:'Status', width:50}
			               ],
			   aggregates : [
			     {member : 'count', aggregator : 'sum', width:50, label:'Total', width:50}
			    ],
			   rowTotals: true,
			   colTotals : true,
			   footerrow : true,
			 },
			 // grid options
			 {
			   regional : 'en',
			   width: 1200,
			   height:200,
			   rowNum : 50,
			   pager: "#pager",
			   caption: "Test Execution Results by Server and Library Versions"
			 });
	
}


function fetchExecutions(){
	
	$("#jsGrid_Executions").jsGrid({
		  width: "100%",
		  height: "auto",

		
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
		        url: "/automation/getExecutionDetails.htm",
		        dataType: "json",
		        method: "POST",
		        });
		    },},
		    	        fields: [
	        	 		{ name: "execution_id", type: "number", width: 10 },
		 	            { name: "execution_result", type: "text", width: 50, validate:"required"},
		 	            { name: "start_time", type: "text", width: 50, validate:"required"},
		 	            { name: "end_time", type: "text", width: 50, validate:"required"}
		 	        ]
		  
		  });
	
}
function fetchLibraries(){
	$("#jsGrid_Libraries").jsGrid({
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
		        url: "/automation/getLibraryDetails.htm",
		        dataType: "json",
		        method: "POST",
		        });
		    },
		    insertItem: function (item) {
		    	insertLibrary(item);
		    	
		 	   },
		 	  onItemInserted: function(args)
		 	  {
		 		  location.reload(true);
		 	  }
		  },
	        fields: [
	        	 		{ name: "lib_id", type: "number", width: 10 },
		 	            { name: "lib_name", type: "text", width: 50, validate:"required"},
		 	            { name: "lib_type", type: "text", width: 50, validate:"required"},
		 	            { name: "lib_version", type: "text", width: 50, validate:"required"},
		 	            
		 	            { type: "control" }
		 	        ]
	});
	
}
function insertLibrary(item){

    $.ajax({
        type: "POST",
        url: "/automation/addLibrary.htm",
        data: "{\"lib_name\":\""+item.lib_name + "\",\"lib_type\":\""+item.lib_type+"\",\"lib_version\":\""+item.lib_version+"\"}",
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
		        url: "/automation/getFeatures.htm",
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
         url: "/automation/addFeature.htm",
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
$.ajax({
method: "POST",
url: "/automation/getFeatures.htm",
dataType: "json"
}).done(function(response) {
//
////    	var data = loadFeatures(response.value);
////        createGrid(data);
	feature = response;
	//feature[0].feature_name;
    //feature_name = JSONObject["feature_name"];
	//var featureList = [];
//	for(var i=0;i<feature.length;i++){
//		featureList.push(feature[i].feature_name)
//	}
	
	
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
		        url: "/automation/getTestCase.htm",
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
		 	            { name: "test_case_id", type: "number", width: 30},
		 	            { name: "testcase_name", type: "text", width: 130, validate:"required"},
		 	          //{ name: "created_time", type: "text", width: 50},
		 	          //{ name: "update_time", type: "text", width: 50},
		 	          //{ name: "test_feature_id", type:"number", /*"select", items: feature_id, valueField: "id", textField: "id",*/width: 50},
		 	        //  { name: "feature_name",type:"select", items:featureList, valueField: "text", textField: "text",validate:"required", width: 100},
		 	            { name: "feature_name",type:"select", items:feature, valueField: "feature_name", textField: "feature_name", validate:"required", width: 100}, 
		 	            { name: "testcase_desc", type: "text", width: 100, validate:"required"},
		 	            { name: "response_code", type: "number", width: 75, validate:"required"},
		 	            { name: "error_code", type: "number", width: 65, validate:"required"},
		 	            { name: "error_message", type: "text", width: 100/*, validate:"required"*/},
		 	            { type: "control" }
		 	        ]
	});
	});
	
}
 
//function loads(data){
//    var names = [{Item: ""}];
//    for(var i =0; i< data.length; i++){
//        var name = data[i].Name;
//        names.push({ Item: name});
//    }
//    return names;
//}

function insertTestCase(item)
{

     $.ajax({
         type: "POST",
         url: "/automation/updateTestCase.htm",
         data: "{\"testcase_name\":\""+item.testcase_name + "\",\"update_time\":\""+item.update_time+"\",\"feature_name\":\""+item.feature_name+"\",\"testcase_desc\":\""+item.testcase_desc+"\",\"response_code\":\""+item.response_code+"\",\"error_code\":\""+item.error_code+"\",\"error_message\":\""+item.error_message+"\"}",
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








//function loadLibraries(){
//	$.ajax({
//		method: "POST",
//		url: "/automation/getLibraryDetails.htm",
//		dataType: "json"
//        }).done(function(response) {
//        	Libraries = response;
//        	loadServer();
//        });
//}

//function loadServer(){
//	$.ajax({
//		method: "POST",
//		url: "/automation/getServer.htm",
//		dataType: "json"
//        }).done(function(response) {
//        	servers = response;
//        	fetchTestJobs();
//        });
//}

function fetchTestJobs()
{
	//loadServer();	
	$.ajax({
	method: "POST",
	url: "/automation/getServer.htm",
	dataType: "json"
    }).done(function(response) {
    	servers = response;
    	
	$.ajax({
	method: "POST",
	url: "/automation/getLibraryDetails.htm",
	dataType: "json"
    }).done(function(response) {
    	libraries = response;
    	
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
		        url: "/automation/getALLTestJobDetails.htm",
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
//		 	          { name: "created_time", type: "text", width: 100},
//		 	            { name: "updated_time", type: "text", width: 100},
		 	          { name: "status", type: "text", width: 100, validate:"required"},
		 	          
		 	         { name: "server_id", type: "select", items: servers, valueField: "server_id", textField: "server_id",width: 50, validate:"required"},
		 	          { name: "lib_id", type: "select", items: libraries, valueField: "lib_id", textField: "lib_id",width: 50, validate:"required"},
		 	            { name: "auto_create_on_new_device", type: "number", width: 30, validate:"required"},
		 	           { type: "control" }
		 	        ]
	});
    });
    });
}

function insertTestJob(item)
{

     $.ajax({
         type: "POST",
         url: "/automation/updateTestJobDetails.htm",
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

function fetchMappingJobs(){
	$("#jsGrid_MappingJobs").jsGrid({
		  width: "100%",
		  height: "auto",

//		 inserting: true,
//	     editing: true,
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
		        url: "/automation/GetTCMJ.htm",
		        dataType: "json",
		        method: "POST",
		        });
		    },},
		   
	        fields: [
		 	            { name: "testcase_id", type: "number",  width: 50, validate:"required"},
		 	            { name: "testjob_id", type: "number", width: 50, validate:"required"}

		 	        ]
	});

		    

	
}

function insertMappingJob(item)
{

     $.ajax({
         type: "POST",
         url: "/automation/addTCMJ.htm",
         data: "{\"test_case_id\":\""+item.test_case_id+ "\",\"testjob_id\":\""+item.testjob_id+"\"}",
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

function fetchExecutionHistory()
{
	var min = 0, max = 0;

	$.ajax({ url: "/automation/getExceutionHistory.htm",
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

	$.ajax({ url: "/automation/getFailuresByDevices.htm",
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

	$.ajax({ url: "/automation/getFailuresByFeatures.htm",
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

	$.ajax({ url: "/automation/getExecutionSummary.htm",
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