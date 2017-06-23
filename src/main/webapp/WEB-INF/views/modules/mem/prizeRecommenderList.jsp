<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>中奖记录管理</title>
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
		<li class="active"><a href="${ctx}/mem/prizeRecommender/">中奖记录列表</a></li>
<%-- 		<shiro:hasPermission name="mem:prizeRecommender:save"><li><a href="${ctx}/mem/prizeRecommender/form">中奖记录添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="prizeRecommender" action="${ctx}/mem/prizeRecommender/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员名称：</label>
				<form:input path="memMemberName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>有效时间：</label>
				<input name="createTime"  type="text" maxlength="20"  readonly="readonly" style="width:135px;" class="input-medium Wdate"
				value="<fmt:formatDate value="${prizeRecommender.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>   
				<input name="updateTime"  type="text" maxlength="20" readonly="readonly" style="width:135px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${prizeRecommender.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<td>会员</td>
				<td>奖品名称</td>
				<td>是否虚拟</td>
				<td>奖品状态</td>
				<td>生效时间</td>
				<td>结束时间</td>
<%-- 				<shiro:hasPermission name="mem:prizeRecommender:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="prizeRecommender">
			<tr>
				<td>${prizeRecommender.member.username }</td>
				<td>${prizeRecommender.prizeActivity.name }</td>
				<td>${prizeRecommender.prizeItemActivity.isVirtual }</td>
				<td>${prizeRecommender.prizeStatus }</td>
				<td><fmt:formatDate value="${prizeRecommender.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${prizeRecommender.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
<%-- 				<shiro:hasPermission name="mem:prizeRecommender:edit"><td> --%>
<%--     				<a href="${ctx}/mem/prizeRecommender/updateFormView?id=${prizeRecommender.id}">修改</a> --%>
<%-- 					<a href="${ctx}/mem/prizeRecommender/delete?id=${prizeRecommender.id}" onclick="return confirmx('确认要删除该中奖记录吗？', this.href)">删除</a> --%>
<%-- 				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>