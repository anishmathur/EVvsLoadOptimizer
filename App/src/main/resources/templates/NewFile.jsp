<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.JsonObject"%>
 
<%
Gson gsonObj = new Gson();
Map<Object,Object> map = null;
List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
 
map = new HashMap<Object,Object>(); map.put("label", "FY07"); map.put("y", 188); list.add(map);
map = new HashMap<Object,Object>(); map.put("label", "FY08"); map.put("y", 213); list.add(map);
map = new HashMap<Object,Object>(); map.put("label", "FY09"); map.put("y", 213); list.add(map);
map = new HashMap<Object,Object>(); map.put("label", "FY10"); map.put("y", 219); list.add(map);
map = new HashMap<Object,Object>(); map.put("label", "FY11"); map.put("y", 207); list.add(map);
map = new HashMap<Object,Object>(); map.put("label", "FY12"); map.put("y", 167); list.add(map);
map = new HashMap<Object,Object>(); map.put("label", "FY13"); map.put("y", 136); list.add(map);
map = new HashMap<Object,Object>(); map.put("label", "FY14"); map.put("y", 152); list.add(map);
map = new HashMap<Object,Object>(); map.put("label", "FY15"); map.put("y", 129); list.add(map);
map = new HashMap<Object,Object>(); map.put("label", "FY16"); map.put("y", 155); list.add(map);
 
String dataPoints = gsonObj.toJson(list);
%>
 
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
window.onload = function() { 
 
var chart = new CanvasJS.Chart("chartContainer", {
	theme: "light2",
	title: {
		text: "Iron Ore Production in India"
	},
	axisX: {
		title: "Fiscal Year"
	},
	axisY: {
		title: "Production ( in million tonnes )"
	},
	data: [{
		type: "line",
		yValueFormatString: "#,##0mn tonnes",
		dataPoints : <%out.print(dataPoints);%>
	}]
});
chart.render();
 
}
</script>
</head>
<body>
<div id="chartContainer" style="height: 370px; width: 100%;"></div>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</body>
</html> 