<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>配送方式管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/kindeditor.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/themes/default/default.css" />
	<script src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/lang/zh_CN.js"></script>
	
	<script type="text/javascript">
	KindEditor.ready(function(K) {
		K.create('textarea[name="description"]', {
			uploadJson : '${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/jsp/upload_json.jsp',
		                fileManagerJson : '${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/jsp/file_manager_json.jsp',
		                allowFileManager : true,
		                allowImageUpload : true, 
		    width : "100%",
			autoHeightMode : true,
			afterCreate : function() {this.loadPlugin('autoheight');},
			afterBlur : function(){ this.sync(); }  //Kindeditor下获取文本框信息
		});
		var editor = K.editor({
			allowFileManager : true
		});
		K('#file_select_button').click(function() {
			editor.loadPlugin('image', function() {
				editor.plugin.imageDialog({
					imageUrl : K('#icon').val(),
					clickFn : function(url, title, width, height, border, align) {
						K('#icon').val(url);
						editor.hideDialog();
					}
				});
			});
		});
	});
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
		<li><a href="${ctx}/method/shippingMethod/">配送方式列表</a></li>
		<li class="active"><a href="${ctx}/method/shippingMethod/form?id=${shippingMethod.id}">配送方式<shiro:hasPermission name="method:shippingMethod:edit">${not empty shippingMethod.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="method:shippingMethod:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="shippingMethod" action="${ctx}/method/shippingMethod/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<%-- <div class="control-group">
			<label class="control-label"><font color="#ff6d6d" >*</font>名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="32" class="input "/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<select id="name" name="name"  class="input-small ">
					<option value="">请选择</option>
					<option>第三方配送</option>
					<option>平台配送</option>
					<option>其他配送方式</option>
					<%-- <c:forEach items="${deliverycorps}" var="deliveryCorp">
						<c:choose>
							<c:when test="${deliveryCorp.id == shippingMethod.defaultdeliverycorp.id}">
							<option value="${deliveryCorp.id}" selected="selected">
							${deliveryCorp.name}</option>
							</c:when>
							<c:otherwise>
							<option value="${deliveryCorp.id}">
							${deliveryCorp.name}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach> --%>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><font color="#ff6d6d" >*</font>首重量：</label>
			<div class="controls">
				<form:input path="firstweight" htmlEscape="false" maxlength="32" class="input  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><font color="#ff6d6d" >*</font>续重量：</label>
			<div class="controls">
				<form:input path="continueweight" htmlEscape="false" maxlength="32" class="input  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><font color="#ff6d6d" >*</font>首重价格：</label>
			<div class="controls">
				<form:input path="firstprice" htmlEscape="false" class="input  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><font color="#ff6d6d" >*</font>续重价格：</label>
			<div class="controls">
				<form:input path="continueprice" htmlEscape="false" class="input  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图标：</label>
			<div class="controls">
				<form:input path="icon" htmlEscape="false" maxlength="255" class="input "/>
				<input id="file_select_button" type="button" value="选择图片"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="orders" htmlEscape="false" maxlength="64" class="input"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">介绍：</label>
			<div class="controls">
				<textarea id="editor" name="description" class="editor" style="width: 100%;">${shippingMethod.description}</textarea>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="method:shippingMethod:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>