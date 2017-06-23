<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品属性管理</title>
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
		<li class="active"><a href="${ctx}/product/propertyGroup/">属性列表</a></li>
		<shiro:hasPermission name="product:propertyGroup:save"><li><a href="${ctx}/product/propertyGroup/form">添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="propertyGroup" action="${ctx}/product/propertyGroup/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input type="hidden" name="productCategoryName"  />
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>商品分类：</label>
				<sys:treeselect id="productCategory" name="productCategoryId" value="${product.productCategoryId}" labelName="productCategoryName"
					labelValue="${product.productCategory.name}" title="商品分类" url="/product/productCategory/treeData" notAllowSelectParent="true" module="product" allowClear="true"   cssClass="input-small" />
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
				<th>商品分类</th>
				<th>排序</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="product:propertyGroup:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="propertyGroup">
			<tr>
				<td><a href="${ctx}/product/propertyGroup/form?id=${propertyGroup.id}">
					${propertyGroup.name}
				</a></td>
				<td>
					${propertyGroup.productCategoryName}
				</td>
				<td>
					${propertyGroup.orders}
				</td>
				<td>
					<fmt:formatDate value="${propertyGroup.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${propertyGroup.remarks}
				</td>
				<shiro:hasPermission name="product:propertyGroup:edit"><td>
    				<a href="${ctx}/product/propertyGroup/updateFormView?id=${propertyGroup.id}">修改</a>
					<a href="${ctx}/product/propertyGroup/delete?id=${propertyGroup.id}" onclick="return confirmx('确认要删除该商品属性吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>