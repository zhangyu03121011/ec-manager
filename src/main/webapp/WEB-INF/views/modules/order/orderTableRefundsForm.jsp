<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>退款单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/order/orderTableRefunds/">退款单列表</a></li>
		<li class="active"><a href="${ctx}/order/orderTableRefunds/form?id=${orderTableRefunds.id}">退款单<shiro:hasPermission name="order:orderTableRefunds:edit">${not empty orderTableRefunds.id?'查看':'查看'}</shiro:hasPermission><shiro:lacksPermission name="order:orderTableRefunds:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="orderTableRefunds" action="${ctx}/order/orderTableRefunds/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">退款单编号：</label>
			<div class="controls">
				<%-- <form:input path="refundNo" htmlEscape="false" maxlength="32" class="input-xlarge "/> --%>
			${orderTableRefunds.refundNo}
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">退款账号：</label>
			<div class="controls">
				<%-- <form:input path="account" htmlEscape="false" maxlength="32" class="input-xlarge "/> --%>
			${orderTableRefunds.account}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退款金额：</label>
			<div class="controls">
				<%-- <form:input path="amount" htmlEscape="false" class="input-xlarge "/> --%>
			${orderTableRefunds.amount}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退款银行：</label>
			<div class="controls">
				<%-- <form:input path="bank" htmlEscape="false" maxlength="32" class="input-xlarge "/> --%>
			${orderTableRefunds.bank}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">方式：</label>
			<div class="controls">
				<%-- <form:input path="method" htmlEscape="false" maxlength="32" class="input-xlarge "/> --%>
			${orderTableRefunds.methodName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作员：</label>
			<div class="controls">
				<%-- <form:input path="operator" htmlEscape="false" maxlength="32" class="input-xlarge "/> --%>
			${orderTableRefunds.operator}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收款人：</label>
			<div class="controls">
				<%-- <form:input path="payee" htmlEscape="false" maxlength="32" class="input-xlarge "/> --%>
			${orderTableRefunds.payee}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付方式：</label>
			<div class="controls">
				<%-- <form:select path="paymentMethod" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_Payment')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select> --%>
			${fns:getDictLabel(orderTableRefunds.paymentMethod, 'order_Payment', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<%-- <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/> --%>
			${orderTableRefunds.remarks}
			</div>
		</div>
		<div class="form-actions">
			<%-- <shiro:hasPermission name="order:orderTableRefunds:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission> --%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>