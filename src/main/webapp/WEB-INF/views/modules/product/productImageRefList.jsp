<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品图片管理</title>
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
		<li class="active"><a href="${ctx}/product/productImageRef/">商品图片列表</a></li>
		<shiro:hasPermission name="product:productImageRef:edit"><li><a href="${ctx}/product/productImageRef/form">商品图片添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="productImageRef" action="${ctx}/product/productImageRef/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商品编号：</label>
				<form:input path="productNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>商品主键ID：</label>
				<form:input path="productId" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<shiro:hasPermission name="product:productImageRef:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="productImageRef">
			<tr>
				<td><a href="${ctx}/product/productImageRef/form?id=${productImageRef.id}">
					${productImageRef.title}
				</a></td>
				<shiro:hasPermission name="product:productImageRef:edit"><td>
    				<a href="${ctx}/product/productImageRef/form?id=${productImageRef.id}">修改</a>
					<a href="${ctx}/product/productImageRef/delete?id=${productImageRef.id}" onclick="return confirmx('确认要删除该商品图片吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>