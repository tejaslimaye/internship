var execSummaryDevices;
var execSummaryFeatures;
var execSummaryRunning;
var execHistory;
var execServerDetails;
var execFeaturesDetails;

var cardChart1;
var cardChart2;
var cardChart4;
var mainChart;

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



function fetchServers()
{
	$.ajax({ url: "http://localhost:8080/automation/getServer.htm",
	        context: document.body,
	        method: "POST",
	        success: function(response){
	        	execServerDetails = JSON.parse(response);
	        	
	       	 $("#jsGrid").jsGrid({
	 	        width: "100%",
	 	        height: "400px",
	 	 
	 	        inserting: true,
	 	        editing: true,
	 	        sorting: true,
	 	        paging: true,
	 	 
	 	        data: execServerDetails.server_details,
	 	 
	 	        fields: [
	 	            { name: "server_id", type: "number", width: 10},
	 	            { name: "ip_address", type: "text", width: 50, validate:"required"},
	 	            { name: "os_version", type: "text", width: 50, validate:"required"},
	 	            { name: "gm_port", type: "number", width: 50, validate:"required" },
	 	            { name: "verify_port", type: "number", width: 50, validate:"required" },
	 	            { name: "api_port", type: "number", width: 50, validate:"required" },
	 	            { name: "console_user", type: "text", width: 50, validate:"required"},
	 	            { name: "console_password", type: "text", width: 50, validate:"required"},
	 	            { name: "enterprise_id", type: "text", width: 50, validate:"required"},
	 	            { name: "enterprise_user", type: "text", width: 50, validate:"required"},
	 	            { name: "enterprise_password", type: "text", width: 50, validate:"required"},
	 	            { name: "server_user", type: "text", width: 50, validate:"required"},
	 	            { name: "server_password", type: "text", width: 50, validate:"required"},
	 	            { name: "agentInfo", type: "text", width: 50, validate:"required"},
	 	            { type: "control" }
	 	        ]
	 	    });

	        },
	        error: function(e){
	        	alert("error:" + eval(e));}
	   
	})

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