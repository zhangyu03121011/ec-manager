<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#reset").click(function(){
			    $("#name").val("");
			    $("#starDate").val("");  
			    $("#endDate").val("");
			});
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
		<li class="active"><a href="${ctx}/mem/memMember/purchaseList">会员购买统计列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="memMember" action="${ctx}/mem/memMember/purchaseList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户名：</label>
				<form:input path="name" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>			
			<li><label>下单时间：</label>
				<input name="starDate" id="starDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${memMember.starDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>—
				<input name="endDate" id="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${memMember.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns">
			    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			    <input id="reset" class="btn btn-primary" type="button" value="重置"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户名</th>
				<th>用户类型</th>
				<th>手机号</th>
				<th>会员等级</th>
				<th>有效下单数</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="memMember">
			<tr>
				<td>
					${memMember.name}
				</td>
				<td>				
					<c:if test="${memMember.userType == '1'}">c1消费者</c:if>
					<c:if test="${memMember.userType == '2'}">分销商</c:if>
					<c:if test="${memMember.userType == '3'}">c2消费者</c:if>
				</td>
				<td>
					${memMember.phone}
				</td>
				<td>
					${memMember.memRank.name}
				</td>
				<td>
				    ${memMember.count}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>