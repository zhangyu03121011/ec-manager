<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抽奖活动信息管理</title>
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
		
		function checkTime(){
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var startDate = new Date(startTime).getTime();
			var endDate = new Date(endTime).getTime();
			if(startDate>endDate){
				return false;
			}else{
				return true;
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/prize/prizeActivity/">抽奖活动信息列表</a></li>
		<li class="active"><a href="${ctx}/prize/prizeActivity/form?id=${prizeActivity.id}">抽奖活动信息<shiro:hasPermission name="prize:prizeActivity:edit">${not empty prizeActivity.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="prize:prizeActivity:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="prizeActivity" action="${ctx}/prize/prizeActivity/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">抽奖活动名称：<font style="color: red;">*</font></label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">积分数：<font style="color: red;">*</font></label>
			<div class="controls">
				<form:input path="point" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">限制次数：<font style="color: red;">*</font></label>
			<div class="controls">
				<form:input path="limits" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动开始日期：<font style="color: red;">*</font></label>
			<div class="controls">
				<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input Wdate  required"
					value="<fmt:formatDate value="${prizeActivity.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动结束日期：<font style="color: red;">*</font></label>
			<div class="controls">
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input Wdate  required"
					value="<fmt:formatDate value="${prizeActivity.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="prize:prizeActivity:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>