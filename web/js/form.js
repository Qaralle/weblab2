function isANumber( n ) {
	const NUMSTR = /^[\-]?\d*\.?\d+(?:[Ee][+\-]?\d+)?$|\.$/;
	return NUMSTR.test(n);
}


function validateValue(inp){

    let val = parseFloat(inp.value.replace(',','.'))

    if (isNaN(val) || inp.value.replace(',','.').split('.').length>2 || !isANumber(inp.value.replace(',','.'))){

        return false
    }

    return val <= 3 && val >= -5

}

function validate() {
	$("#submit_button")[0].disabled = !(validateValue($("#input_y")[0])  && $("input[name='R']:checked").length>0)
}

$("#submit_button").on("click",()=>{

    localStorage.clear()
    let counter=0;
    let checked = []
    for (let i = $("input[name='R']").length - 1; i >= 0; i--) {
        if($("input[name='R']")[i].checked){
            checked[counter]=$("input[name='R']")[i].value
            counter++
        }
    }
    localStorage.setItem("R",JSON.stringify(checked))
})

