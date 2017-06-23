<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>退货单管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(
			function() {
				$("#btnExport").click(
						function() {
							$("#searchForm").attr("action",
									"${ctx}/order/orderTableReturns/export")
									.submit();
						});
				$("#btnSubmit").click(
						function() {
							var date_flag=true;
							var applyBginTime=$("#applyBginTime").val();
							var applyEndTime=$("#applyEndTime").val();
							//1.判断开始时间是否大于当前时间
							if(applyBginTime!=""){
								$.ajax({
						             type: "POST",
						             url: "${ctx}/order/orderTableReturns/compareDate?startDate="+applyBginTime,
						             dataType: "json",
						             async: false,
						             success: function(result){
						            	 if(!result.status){
						            		 date_flag=false;
						            		 alertx(result.msg);
						            	 }
						             }
								});
							}
							if((applyEndTime!="") && date_flag){
								//2.判断结束时间是否小于开始时间
								$.ajax({
						             type: "POST",
						             url: "${ctx}/order/orderTableReturns/compareDate?startDate="+applyBginTime+"&endDate="+applyEndTime,
						             dataType: "json",
						             async: false,
						             success: function(result){
						            	 if(!result.status){
						            		 date_flag=false;
						            		 alertx(result.msg);
						            	 }
						             }
								});
								
							}
							if(!date_flag){
								return;	
							}
							
							$("#searchForm").attr("action",
									"${ctx}/order/orderTableReturns/list")
									.submit();
						});
				
				//重置按钮
				$("#btnReset").click(function(){
					$("#orderNo").val("");
					$("#supplierId").val("");
					$("#supplierName").val("");
					$("#applyBginTime").val("");
					$("#applyEndTime").val("");
					$("#searchForm").find('.select2-chosen').each(function(){
						$(this).text("请选择");
					});
					$("#returnType").val("");
					$("#returnStatus").val("");
				});
				
				
			});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/order/orderTableReturns/">退货单列表</a></li>
		<shiro:hasPermission name="order:orderTableReturns:save">
			<li><a href="${ctx}/order/orderTableReturns/form">退货单添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orderTableReturns"
		action="${ctx}/order/orderTableReturns/updateStatus" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>订单编号：</label> <form:input path="orderNo"
					htmlEscape="false" maxlength="32" class="input-medium" id="orderNo"/></li>
		    <li>
		    	<label>供应商名称：</label> 
		    	<sys:treeselect id="supplier" name="supplierId" value="${orderTableReturns.supplierId}" labelName="companyName" labelValue="${orderTableReturns.companyName}" title="供应商"
						url="/supplier/supplier/treeData" module="product" notAllowSelectRoot="false" cssClass="input-small required" /><span class="help-inline"><font color="red">*</font> </span>
<%-- 		    	<form:input path="companyName" htmlEscape="false" maxlength="32" class="input-medium" /></li> --%>
			<li><label>退货类型</label> <form:select path="returnType"
					class="input-small" id="returnType">
					<form:option value="0" label="请选择" />
					<form:option value="1" label="退款" />
					<form:option value="2" label="退货" />
					<form:option value="3" label="换货" />
				</form:select></li>
			<li><label>退货时间：</label> <input id="applyBginTime" name="applyBginTime" type="text"
				readonly="readonly" maxlength="20" class="input-medium Wdate"
				value="<fmt:formatDate value="${orderTableReturns.applyBginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />—<input id="applyEndTime"
				name="applyEndTime" type="text" readonly="readonly" maxlength="20"
				class="input-medium Wdate"
				value="<fmt:formatDate value="${orderTableReturns.applyEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
			</li>
			<li><label>退货状态</label> <form:select path="returnStatus"
					class="input-small" id="returnStatus">
					<form:option value="0" label="请选择" />
					<form:option value="1" label="退货成功" />
					<form:option value="2" label="退货失败" />
					<form:option value="3" label="未处理" />
				</form:select></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="button" value="查询" /></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary"
				type="button" value="导出" /></li>
			<li class="btns"><input id="btnReset" class="btn btn-primary"
				type="button" value="重置" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>退货单编号</th>
				<th>订单编号</th>
				<th>用户名</th>
				<th>手机号</th>
				<th>退货类型</th>
				<th>供应商公司名称</th>
				<th>退货状态</th>
				<th>申请时间</th>
				<shiro:hasPermission name="order:orderTableReturns:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="orderTableReturns">
				<tr>
					<td>${orderTableReturns.returnNo}</td>
					<%-- <td><a
						href="${ctx}/order/orderTable/form?id=${orderTableReturns.orderTableId}">${orderTableReturns.orderNo}</a></td> --%>
					<td>${orderTableReturns.orderNo}</td>
					<td>${orderTableReturns.userName}</td>
					<td>${orderTableReturns.phone}</td>
					<td><c:if test="${orderTableReturns.returnType==1}">退款</c:if>
						<c:if test="${orderTableReturns.returnType==2}">退货</c:if> <c:if
							test="${orderTableReturns.returnType==3}">换货</c:if></td>
					<td>${orderTableReturns.companyName}</td>
					<td><c:if test="${orderTableReturns.returnStatus==1}">已处理</c:if>
						<c:if test="${orderTableReturns.returnStatus==2}">无法退货</c:if> <c:if
							test="${orderTableReturns.returnStatus==3}">未处理</c:if></td>
					<td><fmt:formatDate value="${orderTableReturns.applyTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<shiro:hasPermission name="order:orderTableReturns:edit">
						<td> <a
							href="${ctx}/order/orderTableReturns/form?id=${orderTableReturns.id}&operaTion=detail">退货详情</a>
						
								<%-- <c:if test="${orderTableReturns.returnStatus==1}">
									<a href="javaScript:void(0)" style="color:#888888">退货编辑</a>
								</c:if>	
								<c:if test="${orderTableReturns.returnStatus==2}">
									<a href="javaScript:void(0)" style="color:#888888">退货编辑</a>
								</c:if>		
								<c:if test="${orderTableReturns.returnStatus==3}">
									<a href="${ctx}/order/orderTableReturns/form?id=${orderTableReturns.id}&operaTion=edit">退货编辑</a>
								</c:if>					 --%>
								
								<c:choose>
									<c:when test="${orderTableReturns.editFlag==0}">
										<a href="${ctx}/order/orderTableReturns/form?id=${orderTableReturns.id}&operaTion=edit">退货编辑</a>
									</c:when>
									<c:otherwise>
										<a href="javaScript:void(0)" style="color:#888888">退货编辑</a>
									</c:otherwise>
								</c:choose>
							
							</td>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>