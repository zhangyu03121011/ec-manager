<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抽奖活动信息管理</title>
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
		<li class="active"><a href="${ctx}/prize/prizeActivity/">抽奖活动列表</a></li>
		<shiro:hasPermission name="prize:prizeActivity:edit"><li><a href="${ctx}/prize/prizeActivity/form">抽奖活动添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="prizeActivity" action="${ctx}/prize/prizeActivity/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="width:100px;">抽奖活动名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>抽奖活动名称</th>
				<th>积分数</th>
				<th>限制次数</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>活动开始时间</th>
				<th>活动结束时间</th>
				<shiro:hasPermission name="prize:prizeActivity:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="prizeActivity">
			<tr>
				<td><a href="${ctx}/prize/prizeActivity/form?id=${prizeActivity.id}">
					${prizeActivity.name}
				</a></td>
				<td>${prizeActivity.point}</td>
				<td>${prizeActivity.limits}</td>
				<th>${prizeActivity.limits}</th>
				<td>
					<fmt:formatDate value="${prizeActivity.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${prizeActivity.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${prizeActivity.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="prize:prizeActivity:edit"><td>
					<a href="${ctx}/prize/prizeItemActivity/form?prizeId=${prizeActivity.id}">新增活动项</a></li>
    				<a href="${ctx}/prize/prizeActivity/form?id=${prizeActivity.id}">修改</a>
					<a href="${ctx}/prize/prizeActivity/delete?id=${prizeActivity.id}" onclick="return confirmx('确认要删除该抽奖活动信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>