var userScript = (function(){
var chartData = [];
var chartData2 = [];
var charts = [];

/* Add default config to make charts responsive */
var options = {
    responsive: true,
    maintainAspectRatio: true
};

var prepareChartContext = function (canvasNode,chartdata) {
	 /* body... */
	 var ctx = canvasNode[0].getContext("2d");
	 ctx.canvas.width = "600";
	 ctx.canvas.height = "300";
	 var data = {
    labels: chartdata.lables,
    
    datasets: [
        {
            label: chartdata.datasets[0].name,
            fillColor: "rgba(209,17,65,0.1)",
            strokeColor: "rgba(209,17,65,1)",
            pointColor: "rgba(209,17,65,1)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba(220,220,220,1)",
            data: chartdata.datasets[0].value
        },
        {
            label: chartdata.datasets[1].name,
            fillColor: "rgba(0,174,219,0.1)",
            strokeColor: "rgba(0,174,219,1)",
            pointColor: "rgba(0,174,219,1)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba(220,220,220,1)",
            data: chartdata.datasets[1].value
        },
        {
            label: chartdata.datasets[2].name,
            fillColor: "rgb(255,255,255,0)",
            strokeColor: "rgba(0,100,219,1)",
            pointColor: "rgba(0,100,219,1)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba(220,220,220,1)",
            data: chartdata.datasets[2].value
        },
        ]
	 
     }
     return {
     	ctx : ctx,
     	data : data
     }
};


var createCanvasNode = function () {
	 /* body... */ 
	 var $div = $("<div>", {class: "col-md-8"});
	 var $span = $("<span>", {class: "appName"});
	 $($div).append($span);
	 var $canvas = $("<canvas>",{class: "chartClass"});
	 return {
	 	canvasNodeHandler : $($div).append($canvas),
	 	canvasHandler : $canvas
	 }
};

var createContainerNode = function () {
	 /* body... */ 
	 var $div = $("<div>", {class: "container"});
	 return $div;
};


var getChartData = function (percentageEV, startHour, requiredMW) {
	
	var endpoint="/viewWinterCharts?percentageEV="+percentageEV+"&startHour="+startHour+"&requiredMW="+requiredMW;
	 $.getJSON(endpoint)	 
	 .done(function(data){
	 	var parentDiv = $("#parentDiv");
	 	/* little housekeeping */
	 	$(parentDiv).empty();
	 	chartData = [];
		charts = [];
	 	var containerNode;
	 	var canvasNode;
	 	for (var i =0; i<data.length; i++) {
	 		if (i%3 == 0) {
	 			containerNode = createContainerNode();
	 			canvasNode = createCanvasNode();
	 			chartData[i] = prepareChartContext(canvasNode.canvasHandler,data[i]);
	 			$($(canvasNode.canvasNodeHandler).find('span')).append(document.createTextNode(data[i].appName));
	 			$(containerNode).append(canvasNode.canvasNodeHandler);
	 			$(parentDiv).append(containerNode);
	 		} else{
	 			canvasNode = createCanvasNode();
	 			chartData[i] = prepareChartContext(canvasNode.canvasHandler,data[i]);
	 			$($(canvasNode.canvasNodeHandler).find('span')).append(document.createTextNode(data[i].appName));	 	 
	 			$(containerNode).append(canvasNode.canvasNodeHandler);
	 		};
	 	 }
	 	 
	 	 $.each(chartData,function (index,value) {
	 	 	 /* body... */ 
	 	 	 charts[index] = new Chart(value.ctx).Line(value.data,options);
	 	 });

	 })
	.fail(function( jqxhr, textStatus, error ) {
    	var err = textStatus + ", " + error;
    	console.log( "Request Failed: " + err );
	});
	 
	 var endpoint="/viewSummerCharts?percentageEV="+percentageEV+"&startHour="+startHour+"&requiredMW="+requiredMW;
	 $.getJSON(endpoint)	 
	 .done(function(data){
	 	var parentDiv = $("#parentDiv2");
	 	/* little housekeeping */
	 	$(parentDiv).empty();
	 	chartData2 = [];
		charts = [];
	 	var containerNode;
	 	var canvasNode;
	 	for (var i =0; i<data.length; i++) {
	 		if (i%3 == 0) {
	 			containerNode = createContainerNode();
	 			canvasNode = createCanvasNode();
	 			chartData2[i] = prepareChartContext(canvasNode.canvasHandler,data[i]);
	 			$($(canvasNode.canvasNodeHandler).find('span')).append(document.createTextNode(data[i].appName));
	 			$(containerNode).append(canvasNode.canvasNodeHandler);
	 			$(parentDiv).append(containerNode);
	 		} else{
	 			canvasNode = createCanvasNode();
	 			chartData2[i] = prepareChartContext(canvasNode.canvasHandler,data[i]);
	 			$($(canvasNode.canvasNodeHandler).find('span')).append(document.createTextNode(data[i].appName));	 	 
	 			$(containerNode).append(canvasNode.canvasNodeHandler);
	 		};
	 	 }
	 	 
	 	 $.each(chartData,function (index,value) {
	 	 	 /* body... */ 
	 	 	 charts[index] = new Chart(value.ctx).Line(value.data,options);
	 	 });

	 })
	.fail(function( jqxhr, textStatus, error ) {
    	var err = textStatus + ", " + error;
    	console.log( "Request Failed: " + err );
	});
};

$("#winter").click(function () {
	$("#parentDiv").css('display','block');
	$("#parentDiv2").css('display','none');
	 /* body... */ 
	$.each(chartData,function (index,value) {
	 	 	 /* destroy previous chart */
	 	 	 if(charts[index] !== undefined || charts[index] !== null){
	 	 	 	charts[index].destroy();
	 	 	 }	 	 	
	 	 	charts[index] =  new Chart(value.ctx).Line(value.data,options);
	 	 });	 
});

$("#summer").click(function () {
	$("#parentDiv").css('display','none');
	$("#parentDiv2").css('display','block');
	 /* body... */ 
	$.each(chartData2,function (index,value) {
	 	 	 /* destroy previous chart */ 
	 	 	if(charts[index] !== undefined || charts[index] !== null){
	 	 	 	charts[index].destroy();
	 	 	 }
	 	 	charts[index] =  new Chart(value.ctx).Line(value.data,options);
	 	 });	 
});

$("#refreshCharts").click(getChartData);

return {
	getChartData : getChartData
	}
})(this);
