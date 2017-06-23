<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员管理</title>
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
		<li><a href="${ctx}/mem/memMember/">会员列表</a></li>
		<li class="active"><a href="${ctx}/mem/memMember/form?id=${memMember.id}">会员<shiro:hasPermission name="mem:memMember:edit">${not empty memMember.id?'详情':'添加'}</shiro:hasPermission><shiro:lacksPermission name="mem:memMember:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="memMember" action="${ctx}/mem/memMember/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">会员名：</label>
			<div class="controls">
				<%-- <form:input path="username" htmlEscape="false" rows="4" maxlength="255" class="input-xlarge "/> --%>
				${memMember.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">头像：</label>
			<div class="controls">
				<%-- <form:input path="username" htmlEscape="false" rows="4" maxlength="255" class="input-xlarge "/> --%>
			    <img src="${memMember.avatar}" style="width:200px;height:120px;">
			</div>
		</div>
		<c:if test="${memMember.userType == '2'}">
		    <div class="control-group">
			    <label class="control-label">真实姓名：</label>
			    <div class="controls">
				    <%-- <form:input path="username" htmlEscape="false" rows="4" maxlength="255" class="input-xlarge "/> --%>
				    ${memMember.username}
			    </div>
		    </div>
		</c:if>
		<div class="control-group">
			<label class="control-label">用户类型：</label>
			<div class="controls">
				<c:if test="${memMember.userType == '1'}">c1消费者</c:if>
				<c:if test="${memMember.userType == '2'}">分销商</c:if>
				<c:if test="${memMember.userType == '3'}">c2消费者</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会员贡献值：</label>
			<div class="controls">
				${memMember.sumamount}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会员等级：</label>
			<div class="controls">
				${memMember.memRank.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号码：</label>
			<div class="controls">
				${memMember.phone}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				${memMember.idCard}
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">是否锁定：</label>
			<div class="controls">
				<c:if test="${memMember.isLocked == '0'}">否</c:if>
				<c:if test="${memMember.isLocked == '1'}">是</c:if>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">审核状态：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${ memMember.approveFlag=='0'}">
						待审核
					</c:when>
					<c:when test="${ memMember.approveFlag=='1'}">
						<font color="green">审核成功</font>
					</c:when>
					<c:when test="${memMember.approveFlag=='2'}">
						<font color="red">审核失败</font>
					</c:when>
					<c:otherwise>
						未认证
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<textarea htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "  disabled="disabled">${memMember.remarks}</textarea>
			</div>
		</div>
		<div class="form-actions">
			<%-- <shiro:hasPermission name="mem:memMember:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission> --%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>