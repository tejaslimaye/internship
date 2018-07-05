var execSummaryDevices;
var execSummaryFeatures;
var execSummaryRunning;


var cardChart1;
var cardChart2;
var cardChart4;


function fetchDetails(){
	fetchLiveTests();
	fetchFailuresByFeatures();
	fetchFailuresByDevices();
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