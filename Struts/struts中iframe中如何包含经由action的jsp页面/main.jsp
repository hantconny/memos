<%@ page language="java" pageEncoding="UTF-8"%>
<html>
	<head>
		<style>
		table,td {
			border-style: none;
			font-size: 13;
		}
		</style>
	</head>
	<body>
		<table width=100% height=100%>
			<tr height=25%>
				<td colspan=2></td>
			</tr>
			<tr height=75%>
				<td width=25% align="center" valign="middle">
					<ul>
						<li>
							欢迎您,${login.name }[
							<a href="login.html" target="_parent">注销</a>]
						</li>
						<li>
							<!-- target对应下面的iframe的name -->
							<!-- href对应要请求的action -->
							<!-- struts-config.xml中配置forward时使用转发 -->
							<!-- 转发到main.jsp页面 -->
							<a href="/bbs/user.do?method=init" target="main">用户管理</a>
						</li>
						<li>
							<a href="#">板块管理</a>
						</li>
						<li>
							<a href="#">帖子管理</a>
						</li>
						<li>
							<a href="#">回帖管理</a>
						</li>
					</ul>
				</td>
				<td width=75% align="center" valign="middle">
					<iframe name="main" frameborder=0 width=100% height=100% src=/bbs/html/welcome.html></iframe>
				</td>
			</tr>
		</table>
	</body>
</html>