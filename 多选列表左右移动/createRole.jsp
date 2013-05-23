<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>创建角色页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/common.css">
	<script type="text/javascript" src="js/basicInfo/goods.js"></script>
	<script type="text/javascript" src="${basePath}js/basicInfo/cz_common.js"></script>
	<STYLE type="text/css">
		#tablediv {
			font-size: 12px;
		}
		
		#content table {
			font-size: 12px;
			width: 100%;
			border-left: 1px solid #009ED2;
			border-top: 1px solid #009ED2;
		}
		
		#content table th {
			border-right: 1px solid #009ED2;
			border-bottom: 1px solid #009ED2;
		}
		
		#content table td {
			border-right: 1px solid #009ED2;
			border-bottom: 1px solid #009ED2;
			text-align: left;
		}

		</STYLE>
  </head>
<body style="overflow-y: auto">
	<DIV>
		<TABLE height="97%" cellSpacing=0 cellPadding=0 width="99%" border=0>
			<TR
				style="BACKGROUND-IMAGE: url(images/common/bg_header.gif); BACKGROUND-REPEAT: repeat-x"
				height=47>
				<TD width=10><SPAN
					style="FLOAT: left; BACKGROUND-IMAGE: url(images/common/main_hl.gif); WIDTH: 15px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 47px"></SPAN>
				</TD>
				<TD><SPAN
					style="FLOAT: left; BACKGROUND-IMAGE: url(images/common/main_hl2.gif); WIDTH: 15px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 47px"></SPAN><SPAN
					style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; FLOAT: left; BACKGROUND-IMAGE: url(images/common/main_hb.gif); PADDING-BOTTOM: 10px; COLOR: white; PADDING-TOP: 10px; BACKGROUND-REPEAT: repeat-x; HEIGHT: 47px; TEXT-ALIGN: center; 0 px:">
						添加角色 </SPAN> <SPAN
					style="FLOAT: left; BACKGROUND-IMAGE: url(images/common/main_hr.gif); WIDTH: 60px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 47px"></SPAN>
				</TD>
				<TD
					style="BACKGROUND-POSITION: 50% bottom; BACKGROUND-IMAGE: url(images/common/main_rc.gif)"
					width=10></TD>
			</TR>
			<TR>
				<TD style="BACKGROUND-IMAGE: url(images/common/main_ls.gif)">
					&nbsp;</TD>
				<TD style="BACKGROUND-COLOR: white" vAlign="middle" align="center">
					<div>
						<div id="current">
							<span><font color="#566984">&nbsp;&nbsp;&nbsp;&nbsp;当前位置：用户管理&raquo;角色管理&raquo;添加角色</font></span>
						</div>
						<div id="content">


							<!-- new  -->
							<s:form method="post" action="user/user!createGroup.action">
								<table cellspacing="0" id="mytable">
									<caption id="msg">&nbsp;</caption>

									<tr>
										<td style="text-align: right; font: bold; width: 30%">
											请输入角色名称：</td>
										<td style="text-align: left;">
											<s:textfield name="groupSObj.group.groupCode"></s:textfield>&nbsp;&nbsp;*
										</td>
									</tr>

									<tr>
										<td style="text-align: right; font: bold;">角色描述：</td>
										<td style="text-align: left;">
											<s:textarea name="groupSObj.group.groupDesc" cols="30" rows="4"></s:textarea>
										</td>
									</tr>

									<tr>
										<td style="text-align: right; font: bold;">权限选择：</td>
										<td style="text-align: left; padding-bottom: 10px; padding-top: 10px; padding-left: 20px">
											<table cellspacing="0">
									  			<tr>
									  				<td>
									  					<select id="leftSel" multiple="multiple" size="20" style="width: 250px">
									  						<s:iterator status="st" var="functionSObj" value="#request.functionList">
									  							<option value='<s:property value="#functionSObj.function.id.functionId"/>'>
									  								<s:property value="#functionSObj.function.functionCode"/>
									  							</option>
									  						</s:iterator>
									  					</select>
									  				</td>
									  				<td>
									  					<button name="addBtn" onclick="multi_sel_add()">
												  			&gt;&gt;
												  		</button>
												  		<br/>
									  					<button name="removeBtn" onclick="multi_sel_remove()">
												  			&lt;&lt;
												  		</button>
									  				</td>
									  				<td>
									  					<select name="rightSelArr" id="rightSel" multiple="multiple" size="20" style="width: 250px">
									  						
									  					</select>
									  				</td>
									  			</tr>
									  		</table>
										</td>
									</tr>
									
									<tr>
										<td colspan="4" style="text-align: center;">
											<s:submit value="添加"></s:submit>
											&nbsp;
											<input type="reset"	value="清空" />
										</td>
									</tr>


								</table>
							</s:form>

						</div>


						<!-- add new  -->


					</div>
				</TD>
				<TD style="BACKGROUND-IMAGE: url(images/common/main_rs.gif)"></TD>
			</TR>
			<TR
				style="BACKGROUND-IMAGE: url(images/common/main_fs.gif); BACKGROUND-REPEAT: repeat-x"
				height=10>
				<TD style="BACKGROUND-IMAGE: url(images/common/main_lf.gif)"></TD>
				<TD style="BACKGROUND-IMAGE: url(images/common/main_fs.gif)"></TD>
				<TD style="BACKGROUND-IMAGE: url(images/common/main_rf.gif)"></TD>
			</TR>
		</TABLE>


	</DIV>


</body>
 
  
  
</html>
