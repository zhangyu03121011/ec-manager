<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内购信息管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.sortError{
			background: url("${ctxStatic}/images/unchecked.gif") no-repeat 0 0;
			padding-left: 18px;
			padding-bottom: 2px;
			font-weight: bold;
			color: #ea5200;
			margin-left: 10px;
		}
	</style>
	<script type="text/javascript" src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/kindeditor.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/themes/default/default.css" />
	<script src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/lang/zh_CN.js"></script>
	<script type="text/javascript">
	KindEditor.ready(function(K) {
		var editor = K.editor({
			allowFileManager : false
		});
		K('#image_select_button').click(function() {
			editor.loadPlugin('image', function() {
				editor.plugin.imageDialog({
					imageUrl : K('#showImage').val(),
					clickFn : function(url, title, width, height, border, align) {
						K('#showImage').val(url);
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
					/* loading('正在提交，请稍等...');
					form.submit(); */
					
					var sort = $("#sort").val();
					var specialId = '${product.specialId}';
					console.log('specialId='+ specialId);
					$.ajax({
						type :'post',
						//url :'${ctx}/product/inAppPurchase/getbysort?sort='+sort,
						url :'${ctx}/product/inAppPurchase/checkSort?sortNum='+sort + '&specialId=' + specialId,
						success :function(date){
							if(date.status){
								loading('正在提交，请稍等...');
								form.submit();
							}else{
								$.jBox.tip("该排序已存在");
							}
						}
					});
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
		function getbysort(){
			var sort = $("#sort").val();
			var specialId = '${product.specialId}';
			console.log('specialId='+ specialId);
			$.ajax({
				type :'post',
				//url :'${ctx}/product/inAppPurchase/getbysort?sort='+sort,
				url :'${ctx}/product/inAppPurchase/checkSort?sortNum='+sort + '&specialId=' + specialId,
				success :function(date){
					if(date.status){
						$("#sorterror").removeAttr("class");
						$("#sorterror").text("");
					}else{
						$("#sorterror").text(sort+"排序字段已存在");
						$("#sort").val("");
						$("#sorterror").attr("class", "sortError");
					}
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/product/inAppPurchase/">内购信息列表</a></li>
		<li class="active"><a href="${ctx}/product/inAppPurchase/updateFormView?id=${product.specialId}">内购信息修改</a></li>
	</ul><br/>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/product/inAppPurchase/updateFormView?id=${product.specialId}">内购基本信息</a></li>
		<li><a href="${ctx}/product/inAppPurchase/updateSelectProduct?specialId=${product.specialId}&updateFlag=${product.updateFlag}">内购商品查看</a></li>
		<li><a href="${ctx}/product/inAppPurchase/updateProductList?specialId=${product.specialId}&updateFlag=${product.updateFlag}">内购商品添加</a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="special" action="${ctx}/product/inAppPurchase/update" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">内购名称：</label>
			<div class="controls">
				<form:input path="specialTitle" htmlEscape="false" maxlength="255" class="input  required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推荐内容：</label>
			<div class="controls">
				<form:textarea path="specialArticle" htmlEscape="false" maxlength="255" class="input  required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展示图片：</label>
			<div class="controls">
				<form:input path="showImage" htmlEscape="false" maxlength="255" class="input  required"/>
				<input type="button" id="image_select_button" value="选择图片" />
				<!-- <font color="red">图片大小(330*330)</font> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="32" class="input required number" onblur="getbysort()"/>
				<lable id= "sorterror"></lable>
			</div>
		</div>
		<div class="control-group">
				<label class="control-label">是否可用优惠券：</label>
				<div class="controls">
					<form:select path="isCoupon" class="input-large ">
						<form:option value="" label="请选择" />
						<form:option value="0" label="是" />
						<form:option value="1" label="否" />
					</form:select>
				</div>
		</div>
		<div class="control-group">
				<label class="control-label">促销金额支付主体：</label>
				<div class="controls">
					<form:select path="payCompany" class="input-large ">
						<form:option value="" label="请选择" />
						<form:option value="0" label="供应商" />
						<form:option value="1" label="平台" />
						<form:option value="2" label="供应商和平台共同承担" />
					</form:select>
				</div>
		</div>
		<div class="control-group">
			<label class="control-label">平台占比：</label>
			<div class="controls">
				<form:input path="percent" htmlEscape="false" maxlength="255" class="input"/>%
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"> 开始日期：</label>
			<div class="controls">
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input Wdate  required"
					value="<fmt:formatDate value="${special.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束日期：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input Wdate  required"
					value="<fmt:formatDate value="${special.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="product:special:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/product/inAppPurchase'"/>
		</div>
	</form:form>
</body>
</html>