<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抽奖活动副表管理</title>
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
		<li class="active"><a href="${ctx}/prize/prizeItemActivity/">抽奖活动副表列表</a></li>
<%-- 		<li><a href="${ctx}/prize/prizeItemActivity/form">抽奖活动副表添加</a></li> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="prizeItemActivity" action="${ctx}/prize/prizeItemActivity/" method="post" class="breadcrumb form-search">
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
				<th>活动名称</th>
				<th>奖品等级</th>
				<th>奖品个数</th>
				<th>奖品</th>
				<th>是否虚拟</th>
				<th>剩余数量</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>领取奖品开始时间</th>
				<th>领取奖品结束时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="prizeItemActivity">
			<tr>
				<td>${prizeItemActivity.name}</td>
				<td>${prizeItemActivity.prizeGrade}</td>
				<td>${prizeItemActivity.awardNumber}</td>
				<td>${prizeItemActivity.awardId}</td>
				<td>
					<c:if test="${prizeItemActivity.isVirtual == '1'}">
						是	
					</c:if>
					<c:if test="${prizeItemActivity.isVirtual == '2'}">
						否	
					</c:if>
				
				</td>
				<td>${prizeItemActivity.number}</td>
				<td><fmt:formatDate value="${prizeItemActivity.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><a href="${ctx}/prize/prizeItemActivity/form?id=${prizeItemActivity.id}">
					<fmt:formatDate value="${prizeItemActivity.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td><fmt:formatDate value="${prizeItemActivity.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${prizeItemActivity.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
    				<a href="${ctx}/prize/prizeItemActivity/form?id=${prizeItemActivity.id}">修改</a>
					<a href="${ctx}/prize/prizeItemActivity/delete?id=${prizeItemActivity.id}" onclick="return confirmx('确认要删除该抽奖活动副表吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>