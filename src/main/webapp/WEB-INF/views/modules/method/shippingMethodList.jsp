<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>配送方式管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/method/shippingMethod/">配送方式列表</a></li>
		<shiro:hasPermission name="method:shippingMethod:save"><li><a href="${ctx}/method/shippingMethod/form">配送方式添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shippingMethod" action="${ctx}/method/shippingMethod/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>首重量</th>
				<th>续重量</th>
				<th>排序</th>
				<shiro:hasPermission name="method:shippingMethod:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shippingMethod">
			<tr>
				<td><a href="${ctx}/method/shippingMethod/form?id=${shippingMethod.id}">
					${shippingMethod.name}
				</a></td>
				<td>
					${shippingMethod.firstweight}
				</td>
				<td>
					${shippingMethod.continueweight}
				</td>
				<td>
					${shippingMethod.orders}
				</td>
				<shiro:hasPermission name="method:shippingMethod:edit"><td>
    				<a href="${ctx}/method/shippingMethod/updateFormView?id=${shippingMethod.id}">修改</a>
					<a href="${ctx}/method/shippingMethod/delete?id=${shippingMethod.id}" onclick="return confirmx('确认要删除该配送方式吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>