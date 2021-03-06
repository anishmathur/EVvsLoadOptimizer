<!DOCTYPE html>
<html lang='en'>
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  
  <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
  
  <link rel="stylesheet" href="css/rSlider.min.css"/>
    <script src="js/rSlider.min.js"></script>

  
</head>
<body>

<style>
body {
    background-image: url("image01.jpg");
    background-repeat: no-repeat;
    background-size: 100% 100%;
}

a:hover:after {
  background: #47c9af;
}
</style>
	
	<script type="text/javascript">
window.onload = function () {

var slider1 = new rSlider({
                    target: '#slider1',
                    values: [10, 20, 30, 40, 50],
                    range: false,
                    set: [30],
                    tooltip: false,
                    onChange: function (vals) {
                        console.log(vals);
                    }
                });

				
				var slider2 = new rSlider({
                    target: '#slider2',
                    values: {min: 0, max: 24},
                    step: 3,
                    range: true,
                    set: [0, 3],
                    scale: true,
                    labels: false,
                    onChange: function (vals) {
                        console.log(vals);
                    }
                });
				
				var sliderSelect1 = document.getElementById("slider1");
		var output = document.getElementById("demo");
		output.innerHTML = slider.value;

		sliderSelect1.oninput = function() {
			output.innerHTML = this.value;
		}
		
		var sliderSelect2 = document.getElementById("slider2");
		var output1 = document.getElementById("demo2");
		var output2 = document.getElementById("demo3");
		output.innerHTML = slider.value;

		sliderSelect2.oninput = function() {
			output1.innerHTML = this.value;
			output2.innerHTML = parseInt(this.value, 10)+3;
		}
}


</script>

<div class="container">
  <div class="page-header" style="margin-top:10px; border-bottom:none;">
    <img src="getpreview.jpg" alt="Hackathon" style="width: 100%; height: 100px; align:center;"/>      
  </div>
  
  <div class="row">
    <div class="col-sm-4" >

				<form action="electricVehicle" method="post" style="padding: 10px; border-radius: 5px; height: 460px; border: 1px solid #337ab7; background-color: #CDDFE9;">

					<div class="form-group" style="width: 40%; float: left;">
						<label for="requiredMW">EV Load(KWH):</label> 
						<input type="text" class="form-control" name="requiredMW" value="${eVehicle.requiredMW}" />
					</div>

					<div class="form-group" style="width: 40%; float: right;">
						<label for="something">No of EV in 2017:</label> <input
							type="text" class="form-control" />
					</div>

					<!--  <p>Percentage of EVs: <input type="text" th:field="*{percentageEV}" /></p>-->
					<div class="form-group">
						<label for="percentageEV">EV Percentage(%): </label>
						<div class="slidecontainer" style="width: 100%;">
							<input type="text" id="slider1" class="slider" name="percentageEV" value="${eVehicle.percentageEV}"/>
						</div>
					</div>

					<div class="form-group">
						<label for="startHour">Hour Range to Charge:</label>
						<div style="width: 100%;margin-top:30px;" class="ui-content">
							<input type="text" id="slider2" class="slider" name="startHour" value="${eVehicle.startHour}" />
						</div>
						<br />
						<br />
					</div>

					<div class="form-group" style="float: right;">
						<p>
							<button type="submit" class="btn btn-default">Submit</button>
							<button type="submit" class="btn btn-default">Reset</button>
						</p>
					</div>

				</form>
			</div>
    	
	<div class="col-sm-8">
		<div class="row">
			<ul class="nav nav-pills nav-justified" style="border:1px solid #B7CEEC;border-radius:5px;background-color:#B7CEEC;">
			<li class="active"><a data-toggle="tab" id="line" onclick="changeGraphType('line');"><strong>Line</strong></a></li>
			<li><a data-toggle="tab" id="bar" onclick="changeGraphType('bar');"><strong>Bar</strong></a></li>
			  	
			</ul>
		</div>
		<div class="row" style="padding:10px; border-radius:5px; height:420px; border:1px solid #CDDFE9; background-color:#CDDFE9;" id="parentDiv">		
		</div>
	</div>
	
	
  </div>
  
  <div class="page-footer" style="height:120px;">
  </div>  
  
  </div>


 

<script>
	
		var slider = document.getElementById("slider1");
		var output = document.getElementById("demo");
		output.innerHTML = slider.value;

		slider.oninput = function() {
			output.innerHTML = this.value;
		}
		
		var slider1 = document.getElementById("slider2");
		var output1 = document.getElementById("demo2");
		output.innerHTML = slider.value;

		slider1.oninput = function() {
			output1.innerHTML = this.value;
			output2.innerHTML = parseInt(this.value, 10)+3;
		}
	</script>
	
	<script type="text/javascript" src="/js/jquery-1.12.0.min.js"></script>
	<script type="text/javascript" src="/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/Chart.min.js"></script>
	<script type="text/javascript" src="/js/getChartData.js"></script>
	<script type="text/javascript">
	userScript.getChartData(1, 2, 3, 4);
	</script>
</body>
</html>
