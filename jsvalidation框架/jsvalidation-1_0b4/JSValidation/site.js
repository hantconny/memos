var VERSION = "1.0b4";
var UPDATE_TIME = "2004-8-17";

function prt(str) {
	document.write(str);
}
function writeTop() {
	prt("<table class=\"HeaderTable\" width=\"760\" cellpadding=\"4\" align=\"center\">");
	prt("<tr>");
    prt("<td width=\"100%\" height=\"36\">JavaScript Validation Framework</td> ");
	prt("<td class=\"VersionText\" valign=\"bottom\">"+VERSION+"</td>");
	prt("</tr>");
	prt("</table>");
}


function writeNav() {
	var str = "";
	str+="::导航::";
	str+="<ul>";
	str+="<li><a href=\"index.html\">首页</a></li>";
	str+="<li><a href=\"download.html\">下载</a></li>";
	str+="<li><a href=\"userguide.html\">用户手册</a></li>";
	str+="<li><a href=\"devguide.html\">开发指南</a></li>";
	str+="<li><a href=\"demo.html\">演示</a></li>";
	str+="<li><a href=\"faq.html\">FAQ</a></li>";
	str+="<li><a href=\"contributors.html\">贡献者</a></li>";
	str+="</ul>";

	prt(str);
}