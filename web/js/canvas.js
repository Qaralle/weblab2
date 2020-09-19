function getSign(x) {
	if(x<0) return -1;
	else return 1;
}

function drawCoordinateLine(context, x0, y0, x1, y1){
	const LENGTH = 6;

	context.fillStyle="black";

	context.beginPath();
	context.lineWidth = 2;
	context.moveTo(x0, y0);
	context.lineTo(x1, y1);
	context.stroke();

	context.beginPath();
	context.moveTo(x1, y1)
	context.lineTo(x1+(getSign(x0-x1)*LENGTH), y1+(getSign(x0-x1)*LENGTH));
	context.lineTo(x1-LENGTH,y1+LENGTH);
	context.fill();
}

function drawCoordinateLines(context) {

	drawCoordinateLine(context, 0, context.canvas.height/2, context.canvas.width, context.canvas.height/2);
	context.fillText('X',context.canvas.width-10,context.canvas.height/2+15)
	drawCoordinateLine(context, context.canvas.width/2, context.canvas.height, context.canvas.width/2, 0);
	context.fillText('Y',context.canvas.width/2+15,10 )

}


function drawShapes(context, color, val) {

	let x = context.canvas.width/2;
	let y = context.canvas.height/2;

	let step = (val*context.canvas.width/2)/7;

	context.fillStyle = color;

	context.beginPath();
	context.moveTo(x, y);
	context.arc(x, y, step, -Math.PI, -Math.PI/2, false);
	context.fill();

	context.beginPath();
	context.moveTo(x, y);
	context.lineTo(x, y-2*step);
	context.lineTo(x+step, y);
	context.fill();


	context.beginPath();
	context.moveTo(x, y);
	context.lineTo(x-2*step, y);
	context.lineTo(x-2*step, y+2*step);
	context.lineTo(x, y+2*step);
	context.fill();
}

function drawSystem(context, val) {
	let vals = [val, val/2, -val/2, -val];
	let x = context.canvas.width/2;
	let y = context.canvas.height/2;
	let step = val*x/7;

	context.fillStyle = "black";

	let count=0;
	for (let i = -2; i <=2 ; i++) {
		if (i !==0){
			context.fillText(vals[count], x+6, y+step*i+5);
			context.beginPath();
			context.moveTo(x-4, y+step*i);
			context.lineTo(x+4, y+step*i);
			context.stroke();
			count++;
		}
	}
	count=3;
	for (let i = -2; i <=2 ; i++) {
		if (i !==0){
			context.fillText(vals[count], x+step*i-5, y-10)
			context.beginPath()
			context.moveTo(x+step*i, y+4)
			context.lineTo(x+step*i, y-4)
			context.stroke()
			count--;
		}
	}

	drawCoordinateLines(context)

}


function makeAResultArray(jqueryArray) {
	let checked = []
	let counter = 0;
	for (let i = jqueryArray.length - 1; i >= 0; i--) {
		if(jqueryArray[i].checked){

			checked[counter]=jqueryArray[i].value
			counter++
		}
	}
	return checked
}
//TODO: rewrite with two canvases
function draw(r_vals) {


	if(!r_vals)
		r_vals=makeAResultArray($("input[ type='checkbox']"))



	console.log(r_vals)
	let colors=["#1f01ff", "#FF9900", "#DDFF59", "#3DAEFFFF", "#9999FF"];
	let context = $('#canvas')[0].getContext('2d');

	context.strokeStyle = "black";
	context.fillStyle = "black";


	context.clearRect(0, 0, context.canvas.width, context.canvas.height);
	drawCoordinateLines(context)

	for (let i = 0; i < r_vals.length ; i++) {

		drawShapes(context, colors[i], r_vals[i]);

	}

	for (let i = 0; i < r_vals.length ; i++) {

		drawSystem(context, r_vals[i])

	}

	if(!localStorage.getItem("R"))
		drawPoints()


}

function drawResult(x, y, alpha) {
	let context =  $('#canvas')[0].getContext('2d');

	let step = context.canvas.width/7;

	context.strokeStyle = `rgba(215,0,0,${alpha})`;
	context.fillStyle = `rgba(215,0,0,${alpha})`;

	context.beginPath(context.canvas.width/2);
	context.moveTo(context.canvas.width/2 + x*step, context.canvas.height/2 - y*step);
	context.arc(context.canvas.width/2 + x*step, context.canvas.height/2 - y*step,4,0,2*Math.PI);
	context.fill();
}

function setCheck(array) {
	if(localStorage.getItem("R")) {
		for (let value of array) {
			for (let item of $("input[type='checkbox']")) {
				if (item.value === value) {
					item.checked = true;
				}
			}
		}
	}
}


function drawPoints() {

	if(localStorage.getItem("R")) {
		setCheck(JSON.parse(localStorage.getItem("R")))
		localStorage.clear()
	}

	let tbody_tr=$("tbody tr")

	if (tbody_tr.length>0) {
		let i_old;
		let iter = 1;
		if (tbody_tr.length>5){
			i_old = tbody_tr.length -5
		}else i_old=0

		for (let i = tbody_tr.length-1; i >= i_old ; i--) {
			drawResult(parseFloat(tbody_tr[i].cells[0].textContent), parseFloat(tbody_tr[i].cells[1].textContent),5/(5*iter))
			iter++
		}


	}
}

function clickPoint(event){

	if(makeAResultArray($("input[type='checkbox']")).length !== 0) {

		let context =  $('#canvas')[0].getContext('2d');
		let step = context.canvas.width/7;
		let x = (event.offsetX - context.canvas.width/2)/step
		let y = (context.canvas.height/2 - event.offsetY)/step
		if ((x>=-3 && x<=5) &&
			(y>=-5 && y<=3)){
			$("#input_x").append(`<option selected value="${x.toFixed(5).toString()}">${x.toFixed(5).toString()}</option>`);
			$("#input_y")[0].value=y.toFixed(10).toString()


			$("#submit_button")[0].disabled=false
			$("#submit_button")[0].click()
		} else
			alert("Координаты ограничены вариантом Л/Р")

	}else alert("Введите R")

}

$("#canvas").on("click",event=> {
		console.log("clicked: ")
		console.log(event)
		clickPoint(event)
	}
);
$(window).on("load",()=> {
		draw(JSON.parse(localStorage.getItem("R")))
		drawPoints()
	}
);
$(window).resize(draw());
$("input[type='checkbox']").on("change", ()=> draw());
