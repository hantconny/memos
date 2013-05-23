function selAll(param) {
	for ( var i = 0; i < document.getElementsByName("idArr").length; i++) {
		document.getElementsByName("idArr")[i].checked = param;
	}
}

function batchDelete() {
	if (confirm('确认删除吗？')) {
		document.getElementById('batchForm').submit();
	}
}

function multi_sel_add() {
	var leftSel = document.getElementById("leftSel");
	var left_opts = leftSel.options;
	var rightSel = document.getElementById("rightSel");

	for(var i = 0; i < left_opts.length; i++) {
		var tmp = left_opts[i];
		if (tmp.selected == true) {
			rightSel.options[rightSel.options.length] = new Option(tmp.text, tmp.value);
			leftSel.options.remove(i);
			i = i - 1;
		}
	}
	
	for(var j = 0; j < rightSel.options.length; j++) {
		var tmp = rightSel.options[j];
		tmp.selected = "selected";
	}
}

function multi_sel_remove() {
	var leftSel = document.getElementById("leftSel");
	var rightSel = document.getElementById("rightSel");
	var right_opts = rightSel.options;

	for ( var i = 0; i < right_opts.length; i++) {
		var tmp = right_opts[i];
		if (tmp.selected == true) {
			leftSel.options[leftSel.options.length] = new Option(tmp.text, tmp.value);
			rightSel.options.remove(i);
			i = i - 1;
		}
	}
	
	for (var j = 0; j < right_opts.length; j++) {
		var tmp = right_opts[j];
		tmp.selected = "selected";
	}
}