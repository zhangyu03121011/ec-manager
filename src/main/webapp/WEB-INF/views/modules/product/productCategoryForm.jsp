<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>商品分类管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript"
	src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/kindeditor.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/themes/default/default.css" />
<script src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script type="text/javascript">
	var productCategoryId = '';
	$(document).ready(
			
			
			
			function() {
				$("#name").focus();
				$("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
										loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									}
								});
				KindEditor.ready(function(K) {
					var editor = K.editor({
						allowFileManager : true
					});
					K('#image_path').click(
							function() {
								editor.loadPlugin('image', function() {
									editor.plugin.imageDialog({
										imageUrl : K('#imagePath').val(),
										clickFn : function(url, title, width,
												height, border, align) {
											K('#imagePath').val(url);
											editor.hideDialog();
										}
									});
								});
							});
				});
				$("#propertyGroupsList").delegate("input[name='propertyGroupIds']","click",function(){
					var $this = $(this);
					if($this.is(':checked')){
						$this.parent().next().show();
					}else{
						$this.parent().next().hide();
					}
				});
				//分类变化
				setInterval('txChange()',200);
			});
	function txChange(){
		var value = $("#parentId").val();
		if(productCategoryId!=value){
			alterPropertyGroup(value);
			productCategoryId = value;
		}
	}
	
	$("#btnSubmit").live("click", function() {
		var flag = true;
		if($("#name").val()==""){
			$("#errorMsg").html("商品名称不能为空");
			flag = false;		 
		}else if($("#orders").val()==""){
			$("#errorMsg").html("商品排序不能为空");
			flag = false;
		}else if($("#parentName").val()==""){
			$("#errorMsg").html("商品分类不能为空");
			flag = false;
		}else{
			$("#errorMsg").html("");
		}
		return flag;
	});
	function alterPropertyGroup(categoryId){
		 $.ajax({
				url:"${ctx}/product/productCategory/queryPropertyGroups?categoryId="+categoryId+"&hasDetail=true",
				type:"GET",
				dataType:"json",
				cache:false,
				success:function(data){
					var $div = $("#propertyGroupsList").empty();
					if(data==null||data.length==0){
						$div.parent().hide();
						return;
					}
					$div.parent().show();
					var html = "";
					$.each(data,function(index,e){
						html = html+"<li><div><label> <input type=\"checkbox\" name=\"propertyGroupIds\" value=\""+e.id+"\" />"+
								e.name+"</label><div style=\"margin-left:25px;display:none\">";
						$.each(e.productPropertyList,function(index,property){
							html = html+"<label style=\"margin-left:5px;\"><input type=\"checkbox\" name=\"propertyId_group_"+e.id+"\" value=\""+property.id+"\"/>"+
									property.name+"</label>";
						});
						html = html+"</div><div></li>";
					});
					$div.html(html);
				}
			});
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/product/productCategory/">商品分类列表</a></li>
		<li class="active"><a
			href="${ctx}/product/productCategory/form?id=${productCategory.id}&parent.id=${productCategoryparent.id}">商品分类<shiro:hasPermission
					name="product:productCategory:edit">${not empty productCategory.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="product:productCategory:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="productCategory"
		action="${ctx}/product/productCategory/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<span id="errorMsg" style="color:red;"></span>
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="32"
					class="input-xlarge " />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="orders" htmlEscape="false" maxlength="11"
					class="input-xlarge  digits " />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="display:none;">
			<label class="control-label">层级：</label>
			<div class="controls">
				<form:input path="grade" htmlEscape="false" maxlength="11"
					class="input-xlarge  digits" />
			</div>
		</div>
		<c:if test="${productCategory!=null and productCategory.parent.parentIds=='0,' }">
			<div class="control-group">
			<label class="control-label">分类图片：</label>
			<div class="controls">
				<form:input path="imagePath" htmlEscape="false" maxlength="256"
					class="input-xlarge " />
				<input type="button" id="image_path" value="选择文件" />
			</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">页面描述：</label>
			<div class="controls">
				<form:input path="seoDescription" htmlEscape="false" maxlength="32"
					class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">页面关健字：</label>
			<div class="controls">
				<form:input path="seoKeywords" htmlEscape="false" maxlength="32"
					class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">页面标题：</label>
			<div class="controls">
				<form:input path="seoTitle" htmlEscape="false" maxlength="32"
					class="input-xlarge " />
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">商户号：</label>
			<div class="controls">
				<form:input path="merchantNo" htmlEscape="false" maxlength="32"
					class="input-xlarge " />
			</div>
		</div> --%>
<%-- 		<c:if test="${productCategory.parent.id!=null}"> --%>
<!-- 			<div class="control-group"> -->
<!-- 				<label class="control-label">上级分类：</label> -->
<!-- 				<div class="controls"> -->
<%-- 					<sys:treeselect id="parent" name="parent.id" --%>
<%-- 						value="${productCategory.parent.id}" labelName="parent.name" --%>
<%-- 						labelValue="${productCategory.parent.name}" title="上级分类" --%>
<%-- 						url="/product/productCategory/treeData" --%>
<%-- 						extId="${productCategory.id}" cssClass=" input-small required" allowClear="true" disabled="disabled" /> --%>
<!-- 					<span class="help-inline"><font color="red">*</font> </span> -->
<!-- 				</div> -->
<!-- 			</div> -->
<%-- 		</c:if> --%>
		<c:choose>
			<c:when  test="${productCategory.parent.id!=null}">
				<div class="control-group">
				<label class="control-label">上级分类：</label>
				<div class="controls">
					<sys:treeselect id="parent" name="parent.id"
						value="${productCategory.parent.id}" labelName="parent.name"
						labelValue="${productCategory.parent.name}" title="上级分类"
						url="/product/productCategory/treeData"
						extId="${productCategory.id}" cssClass=" input-small required" allowClear="true" disabled="disabled" />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			</c:when>
			<c:otherwise>
				<form:hidden path="parent"  />
			</c:otherwise>
		</c:choose>
		<c:choose>
		<c:when test="${not empty propertyGroups }">
			<div class="control-group">
		</c:when>
		<c:otherwise>
			<div class="control-group" style="display:none;">
		</c:otherwise>
		</c:choose>
			<label class="control-label">上级分类属性组：</label>
			<div class="controls" id="propertyGroupsList">
				<c:forEach items="${propertyGroups}" var="propertyGroup">
					<li>
						<div>
						<label>
							<input type="checkbox" name="propertyGroupIds" value="${propertyGroup.id}" />${propertyGroup.name}
						</label>
						<div style="margin-left:25px;">
							<c:forEach items="${propertyGroup.productPropertyList }" var="productProperty">
								<label style="margin-left:5px;"><input type="checkbox" name="propertyId_group_${propertyGroup.id}"/>${productProperty.name }</label>
							</c:forEach>
						</div>
						</div>
					</li>
				</c:forEach>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4"
					maxlength="255" class="input-xxlarge " />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="product:productCategory:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="保 存" />&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>