<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
  <head>
	<SCRIPT type="text/javascript">
		function add() {
			var leftSel = document.getElementById("leftSel");
			var left_opts = leftSel.options;
			var rightSel = document.getElementById("rightSel");
			
			for(var i = 0; i < left_opts.length; i++){
				var tmp = left_opts[i];
				if(tmp.selected == true){
					rightSel.options[rightSel.options.length] = new Option(tmp.text, tmp.value);
					leftSel.options.remove(i);
					i = i - 1;
				}
			}
		}
		
		function remove() {
			var leftSel = document.getElementById("leftSel");
			var rightSel = document.getElementById("rightSel");
			var right_opts = rightSel.options;
			
			for(var i = 0; i < right_opts.length; i++){
				var tmp = right_opts[i];
				if(tmp.selected == true){
					leftSel.options[leftSel.options.length] = new Option(tmp.text, tmp.value);
					rightSel.options.remove(i);
					i = i - 1;
				}
			}
		}
		
		
	</SCRIPT>
  </head>
  <body>
  	<form action="">
  		<table>
  			<tr>
  				<td>
  					<SELECT id="leftSel" multiple="multiple" size="8" style="width: 150px">
			  			<option value="1">one</option>
			  			<option value="2">two</option>
			  			<option value="3">three</option>
			  			<option value="4">four</option>
			  			<option value="5">five</option>
			  			<option value="6">six</option>
			  			<option value="7">seven</option>
			  			<option value="8">eight</option>
			  			<option value="9">nine</option>
			  			<option value="10">ten</option>
		  			</SELECT>
  				</td>
  				<td>
  					<button name="addBtn" onclick="add()">
			  			&gt;&gt;
			  		</button>
			  		<br/>
  					<button name="removeBtn" onclick="remove()">
			  			&lt;&lt;
			  		</button>
  				</td>
  				<td>
  					<SELECT id="rightSel" multiple="multiple" size="8" style="width: 150px">
  					</SELECT>
  				</td>
  			</tr>
  		</table>
  	</form>
  </body>
</html>
