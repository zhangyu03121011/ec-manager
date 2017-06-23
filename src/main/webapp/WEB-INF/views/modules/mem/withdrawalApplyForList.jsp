<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提现管理表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				$("#searchForm").attr("action","${ctx}/mem/withdrawalApplyFor/export").submit();
				$("#searchForm").attr("action","${ctx}/mem/withdrawalApplyFor/");
			});
			
			function reset(){
				$("#endDate").val('');
				$("#startDate").val('');
			}
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		//重置按钮
		function reset(){
			$("#endDate").val('');
			$("#startDate").val('');
			$("#applyUsername").val('');
			$("#applyPhone").val('');
		   /*  $("#searchForm").find("span[class='select2-chosen']").each(function(){
				$(this).text('');
			});  */
		 	$("#applyStatus option[value='']").attr("selected",true); 
			$(".select2-chosen").html("全部");
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/mem/withdrawalApplyFor/">提现列表</a></li>
		<%-- <shiro:hasPermission name="mem:withdrawalApplyFor:save"><li><a href="${ctx}/mem/withdrawalApplyFor/form">提现详情</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="withdrawalApplyFor" action="${ctx}/mem/withdrawalApplyFor/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户名：</label>
				<form:input path="applyUsername" id="applyUsername" htmlEscape="false" maxlength="64" class="input-medium" style="width:90px;"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="applyPhone" id="applyPhone" htmlEscape="false" maxlength="11" class="input-medium" style="width:90px;"/>
			</li>
			<li><label>申请时间：</label>
				<input name="startDate" id="startDate" type="text" maxlength="20"  readonly="readonly" style="width:135px;" class="input-medium Wdate"
				value="<fmt:formatDate value="${withdrawalApplyFor.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>   
				<input name="endDate" id="endDate" type="text" maxlength="20" readonly="readonly" style="width:135px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${withdrawalApplyFor.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>提现状态</label>
				<select name="applyStatus" style="width:100px;" id="applyStatus">
					<option value=""></option>
					<option value="" <c:if test="${withdrawalApplyFor.applyStatus =='' }">selected="selected"</c:if>>全部</option>
					<option value="0" <c:if test="${withdrawalApplyFor.applyStatus =='0' }">selected="selected"</c:if>>未处理</option>
					<option value="1" <c:if test="${withdrawalApplyFor.applyStatus =='1' }">selected="selected"</c:if>>处理失败</option>
					<option value="2" <c:if test="${withdrawalApplyFor.applyStatus =='2' }">selected="selected"</c:if>>处理成功</option>
				</select>
				<%-- <form:select path="applyStatus" name="applyStatus" id ="applyStatus" class="input-medium" style="width:100px;">
					<!-- <option value=""></option> -->
					<option value=""></option>
					<option value="" <c:if test="${withdrawalApplyFor.applyStatus =='' }">selected="selected"</c:if>>全部</option>
					<c:forEach items="${withdrawalapplyStatus}" var="status">
						<option value="${status.key}">${status.value}</option>
					</c:forEach>  
				</form:select> --%>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><div style="width:30px;height:20px" style="font-weight: 200px" id="btnReturn" class="btn btn-primary" onclick="reset()">重置</div></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户名</th>
				<th>手机号</th>
				<th>持卡人姓名</th>
				<th>提现银行卡号</th>
				<th>提现金额(元)</th>
				<th>提现状态</th>
				<th>申请时间</th>
				<shiro:hasPermission name="mem:withdrawalApplyFor:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="withdrawalApplyFor">
			<tr>
				<td><a href="${ctx}/mem/withdrawalApplyFor/form?id=${withdrawalApplyFor.id}">
					${withdrawalApplyFor.applyUsername}
				</a></td>
				<td>				
					${withdrawalApplyFor.applyPhone}
				</td>
				<td>				
					${withdrawalApplyFor.cardPersonName}
				</td>
				<td>				
					${withdrawalApplyFor.cardNo}
				</td>
				<td>				
					${withdrawalApplyFor.applyAmount}
				</td>
				<td>				
					<c:if test="${withdrawalApplyFor.applyStatus == '0'}">未处理</c:if>
					<c:if test="${withdrawalApplyFor.applyStatus == '1'}"><font color="red">处理失败</font></c:if>
					<c:if test="${withdrawalApplyFor.applyStatus == '2'}"><font color="green">处理成功</font></c:if>
				</td>
				<td>				
					<fmt:formatDate value="${withdrawalApplyFor.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="mem:withdrawalApplyFor:edit"><td>
    				<a href="${ctx}/mem/withdrawalApplyFor/form?id=${withdrawalApplyFor.id}">详情</a>
    				<c:if test="${withdrawalApplyFor.applyStatus == '0'}"><a href="${ctx}/mem/withdrawalApplyFor/updateFormView?id=${withdrawalApplyFor.id}">编辑</a></c:if>
					<%-- <a href="${ctx}/mem/withdrawalApplyFor/delete?id=${withdrawalApplyFor.id}" onclick="return confirmx('确认要删除该提现管理表吗？', this.href)">删除</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>