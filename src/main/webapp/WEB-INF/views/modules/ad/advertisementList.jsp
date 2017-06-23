<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>广告管理管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/ad/advertisement/">广告管理列表</a></li>
		<shiro:hasPermission name="ad:advertisement:save"><li><a href="${ctx}/ad/advertisement/form">广告管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="advertisement" action="${ctx}/ad/advertisement/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>排序</th>
				<th>开始日期</th>
				<th>结束日期</th>
				<shiro:hasPermission name="ad:advertisement:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="advertisement">
			<tr>
				<td><%-- <a href="${ctx}/ad/advertisement/form?id=${advertisement.id}">
					
					</a> --%>${advertisement.title}
				</td>
				<td>
					${advertisement.orders}
				</td>
				<td>
					<fmt:formatDate value="${advertisement.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${advertisement.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="ad:advertisement:edit"><td>
    				<a href="${ctx}/ad/advertisement/updateFormView?id=${advertisement.id}">修改</a>
					<a href="javascript:void(0)" onclick="confirmDel('${advertisement.endDate}','${ctx}/ad/advertisement/delete?id=${advertisement.id}')">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		
		function confirmDel(endDate,href) {
			var currentDate = new Date().getTime();
			//判断结束时间是否大于当前时间
			if(currentDate > new Date(endDate).getTime()) {
				confirmx('确认要删除该广告管理吗？',href);
			} else {
				alertx("有效期内不能删除");
			}
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</body>
</html>