<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>My Calendar Demo</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link type="text/css" rel="stylesheet" href="/项目名/calc/calendar-system.css" />
		<!-- 导入calendar.js类, 注意路径问题 -->
		<script type="text/javascript" src="/项目名/calc/calendar.js"></script>
		<!-- 导入calendar-en.js类, 注意路径问题 -->
		<script type="text/javascript" src="/项目名/calc/calendar-en.js"></script>


		<script language="JavaScript">
			var calendar = null;
		
			 function calSelected(cal, date) {
				cal.sel.value = date;
				
				var updateFlexElements = document.getElementsByName('updateF' + cal.sel.name.substring(1));
				if ((typeof updateFlexElements != "undefined") && (updateFlexElements.length > 0)) {
					var elem = updateFlexElements[0];
					if (elem.type == "checkbox") {
						elem.checked = true;
					}
				}
				cal.callCloseHandler();
			}
			
			 function calCloseHandler(cal) {
				cal.hide();
			 } // calCloseHandler
			
			 function calShow(id) {
				var el = document.getElementById(id);
				if (calendar != null) {
					calendar.hide();		// hide the existing calendar
					calendar.parseDate(el.value); // set it to a new date
				} else {
					var cal = new Calendar(false, null, calSelected, calCloseHandler);
					var now = new Date();
					calendar = cal;
					calendar.setDateFormat('y-mm-dd');
					calendar.setRange(now.getFullYear() - 10, now.getFullYear() + 10);
					calendar.weekNumbers = false;
					calendar.create();
				}
				calendar.sel = el;
				calendar.showAtElement(el);
				return false;
			} // calShow
	</script>
	</head>

	<body>
		今天的日期是：<input name="myCal" onclick="calShow(this.name);" onfocus="calShow(this.name);" readonly="true">
		<!-- 设置为只读 -->
	</body>
</html>
