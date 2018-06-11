<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String path = request.getContextPath();
    String basePath =
            request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
   <%@include file="/include/head.jsp"%>
   
</head>
<body>
	<shiro:guest>
    欢迎游客访问，<a href="${pageContext.request.contextPath}/login/login.jsp">点击登录</a>
		<br />
	</shiro:guest>
	<shiro:user>
    欢迎[<shiro:principal />]登录，
<shiro:hasRole name="admin">  
    用户[<shiro:principal />]拥有角色admin<br />
		</shiro:hasRole>

		<a href="${pageContext.request.contextPath}/logout">点击退出</a>
		<br />
	</shiro:user>

	<table class="table table-bordered">
		<caption> 用户[<shiro:principal/>]角色权限状况</caption>
		<thead>
			<tr>
				<th>admin角色</th>
				<th>sys:role:list</th>
				<th>sys:role:save</th>
				<th>sys:role:update</th>
				<th>sys:role:delete</th>
				<th>sys:role:other</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
					<shiro:hasRole name="admin">  
    					拥有角色admin<br />
					</shiro:hasRole>
				</td>
				<td>
					<shiro:hasPermission name="sys:role:list">  
					   拥有权限sys:role:list<br/>  
					</shiro:hasPermission>   
					<a href="shiroTest/org_list" target="_blank">测试</a>
					<a onclick="testAjax('org_list')">ajax</a>
				</td>
				<td>
					<shiro:hasPermission name="sys:role:save">  
					   拥有权限sys:role:save<br/>  
					</shiro:hasPermission> 
					<a href="shiroTest/org_add" target="_blank">测试</a>  
					<a onclick="testAjax('org_add')">ajax</a>
				</td>
				<td>
					<shiro:hasPermission name="sys:role:update">  
					   拥有权限sys:role:update<br/>  
					</shiro:hasPermission>   
					<a href="shiroTest/org_update" target="_blank">测试</a>
					<a onclick="testAjax('org_update')">ajax</a>
				</td>
				<td>
					<shiro:hasPermission name="sys:role:delete">  
					   拥有权限sys:role:delete<br/>  
					</shiro:hasPermission>   
					<a href="shiroTest/org_delete" target="_blank">测试</a>
					<a onclick="testAjax('org_delete')">ajax</a>
				</td>
				<td>
					<shiro:hasPermission name="sys:role:other">  
					   拥有权限sys:role:other<br/>  
					</shiro:hasPermission>   
					<a href="shiroTest/org_other" target="_blank">测试</a>
					<a onclick="testAjax('org_other')">ajax</a>
				</td>
				
			</tr>
		</tbody>

	</table>
</body>
<script type="text/javascript">

function testAjax(url){
	var url = 'shiroTest/'+url;
	ajax(url,'get','',function(r){
		console.log(r);
		toast('操作成功',r.msg,'success');
	},function(xhr, status, error){
		console.error(error);
		toast('操作失败',error,'error');
	});
}

</script>
</html>