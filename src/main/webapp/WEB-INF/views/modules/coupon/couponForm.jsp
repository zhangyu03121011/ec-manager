<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>优惠券管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/kindeditor.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/themes/default/default.css" />
	<script src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/lang/zh_CN.js"></script>
	
	<script type="text/javascript">
	var tables = ["base_info_tab","coupon_introduction_tab"];
	KindEditor.ready(function(K) {
		K.create('textarea[name="introduction"]', {
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
	});
		$(document).ready(function() {
			//$("#special_div").hide(); 
			//$("#product_div").hide();
			
			$("#couponRange").change(function(){
				var couponRange=$("#couponRange").val();
				if(couponRange=='1'){
					$("#product_div").hide();
					$("#special_div").show(); 
				}else if(couponRange=='2'){
					$("#special_div").hide(); 
					$("#product_div").show();
				}else if(couponRange=='3'){
					$("#special_div").hide(); 
					$("#product_div").hide();
				}
			});
			
			//点击保存按钮的单击事件
			$("#btnSubmit").click(function(){
				if(!checkTime()){
					return;
				} 
				
				var hidden_id=$("#id").val();
				if(!hidden_id){
					if(!checkNum()){
						return;
					}
				}
				
				//判断专题指定范围
			    var couponRange=$("#couponRange").val();
				if(!couponRange){
					$("#start_error").text("");
					$("#end_error").text("");
					alertx("指定范围不能为空");
					return;
				}
				if(couponRange=="1"){
					var specialId=$("#specialId").val();
					if(!specialId){
						$("#special_id_error").text("所选专题不能为空");
						return;
					}
				}else if(couponRange=="2"){
					var productNo=$("#productId").val();
					if(!productNo){
						$("#start_error").text("");
						$("#end_error").text("");
						$("#special_id_error").text("");
						$("#product_no_error").text("所选商品不能为空");
						return;
					}
				} 
				
				//判断该专题或者该商品下所需消费金额是否重复
                if(!checkCoupon()){
                	alertx("同等类型的优惠劵已经存在");
                	return;
                }				
				
				var id=$("#id").val();
				if(id){
					$("#inputForm").attr("action","${ctx}/coupon/coupon/update");
				}
				$("#inputForm").submit(); 
			});
			
			
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
			//判断起始日期和结束日期的值
			var flag=true;
			var star_date=$("#beginDate").val();
			var end_date=$("#endDate").val();
			if(!star_date){
				$("#start_error").text("起始日期不能为空");
				flag=false;
			}else if(!end_date){
				$("#start_error").text("");
				$("#end_error").text("结束日期不能为空");
				flag=false;
			}else{
				$("#start_error").text("");
				$("#end_error").text("");
				var beginTime = star_date;
				var endTime = end_date;
			    var beginTimes=beginTime.substring(0,10).split('-');  
				var endTimes=endTime.substring(0,10).split('-');  
				beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);  
				endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);
				var disparityTime =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;  
				if(disparityTime<0){  
					flag=false;
					alertx("结束时间不能小于开始时间");
				} 
			}
			return flag;
		}
		//判断赠送数量和待生成数量是否合法
		function checkNum(){
			var flag=true;
			var sum=$("#sum").val();
			var presentSum=$("#presentSum").val();
			sum=parseInt(sum);
			presentSum=parseInt(presentSum);
			if(presentSum>sum){
				flag=false;
				alertx("赠送数量不能小于待生成数量");
			}
			return flag;
		}
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
		//切换菜单
		$(function(){
			$("#inputForm ul > li").click(function(){
		        $(this).addClass("active").siblings().removeClass("active");
				var table_id = $(this).attr("id").replace("li","tab");
				$("#"+table_id).attr("style","display:table;width:100%");
				for(var i = 0 ; i < tables.length ; i++){
					if(tables[i] == table_id){
						continue;
					}
					$("#"+tables[i]).attr("style","display:none");
				}
			});
		});
		// 是否允许积分兑换
		$("#isExchange").live("click",function() {
			if ($(this).prop("checked")) {
				$("#isExchange").val("1");
				$("#point").prop("disabled", false);
			} else {
				$("#isExchange").val("0");
				$("#point").val("").prop("disabled", true);
			}
		});
		// 是否启用
		$("#isEnabled").live("click",function() {
			if ($(this).prop("checked")) {
				$("#isEnabled").val("1");
			} else {
				$("#isEnabled").val("0");
			}
		});
			//验证积分
			function isPoint(obj){
				   reg=/^(([0-9]*[1-9][0-9]*)|0)$/;
				   if(!reg.test(obj)){
					   $("#pointNotnull").html("只允许输入零或正整数");
				   }else{
					   $("#pointNotnull").html("");
				   }
				}
	        
			//判断该专题或者该商品下所需消费金额是否重复
			function checkCoupon(){
				var flag=true;
				$.ajax({
                    type: "POST",
                    url: "${ctx}/coupon/coupon/checkCoupon",
                    data:$('#inputForm').serialize(),
                    dataType: "json",
                    async: false,
                    success: function(result){
            	        if(!result.status){
            	        	flag=false;
            	        }
                    }
				});
				return flag;
			}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/coupon/coupon/">优惠券列表</a></li>
		<li class="active"><a href="${ctx}/coupon/coupon/form?id=${coupon.id}">优惠券<shiro:hasPermission name="coupon:coupon:edit">${not empty coupon.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="coupon:coupon:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="coupon" action="${ctx}/coupon/coupon/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<ul class="nav nav-tabs" id="tab">
			<li class="active" id="base_info_li"><a>基本信息</a></li>
			<li id="coupon_introduction_li"><a>介绍</a></li>
		</ul>
		
		<div id="base_info_tab">	
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">前缀：</label>
			<div class="controls">
				<form:input path="prefix" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用起始日期：</label>
			<div class="controls">
				<input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="text Wdate"
					value="<fmt:formatDate value="${coupon.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span style="color:red;" id="start_error"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用结束日期：</label>
			<div class="controls">
				<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="text Wdate"
					value="<fmt:formatDate value="${coupon.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span style="color:red;" id="end_error"></span>
			</div>
		</div>
	<%-- 	<div class="control-group">
			<label class="control-label">最大商品价格：</label>
			<div class="controls">
				<form:input path="maximumPrice" htmlEscape="false" class="text"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最大商品数量：</label>
			<div class="controls">
				<form:input path="maximumQuantity" htmlEscape="false" class="text"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最小商品价格：</label>
			<div class="controls">
				<form:input path="minimumPrice" htmlEscape="false" class="text"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最小商品数量：</label>
			<div class="controls">
				<form:input path="minimumQuantity" htmlEscape="false" maxlength="11" class="text"/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">所需消费金额：</label>
			<div class="controls">
				<form:input path="needConsumeBalance" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">面额：</label>
			<div class="controls">
				<form:input path="facePrice" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">优惠券来源：</label>
			<div class="controls">
				<form:input path="couponSource" htmlEscape="false" maxlength="32" class="text"/>
			</div>
		</div> --%>
		<c:if test="${not empty coupon.id }">
		    <div class="control-group">
			    <label class="control-label">总数量数量：</label>
			    <div class="controls">
				    <form:input path="totalSum" readonly="true" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
			    </div>
			</div>  
			<div class="control-group">
			    <label class="control-label">未使用数量：</label>
			    <div class="controls">
				    <form:input path="noUserSum" readonly="true" htmlEscape="false" maxlength="11"  class="input-xlarge required"/>
			    </div>
		    </div>       
		</c:if>
		<div class="control-group">
			<label class="control-label">待生成数量：</label>
			<div class="controls">
				<form:input path="sum" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">赠送数量：</label>
			<div class="controls">
				<form:input path="presentSum" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优惠劵类型：</label>
			<div class="controls">
				<select id="presentType" name="presentType" class="input-xlarge required">
				    <option value="" <c:if test="${coupon.presentType =='' }">selected="selected"</c:if> >请选择</option>
					<option value="1"  <c:if test="${coupon.presentType =='1'}">selected="selected"</c:if>>购买满减</option>
				</select>
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">指定范围：</label>
			<div class="controls">
				<select id="couponRange" name="couponRange" class="input-xlarge required">
				    <option value="" <c:if test="${coupon.couponRange =='' }">selected="selected"</c:if>>请选择</option>
				    <option value="1" <c:if test="${coupon.couponRange =='1' }">selected="selected"</c:if>>指定专题</option>
					<option value="2" <c:if test="${coupon.couponRange =='2' }">selected="selected"</c:if>>指定商品</option>
					<option value="3" <c:if test="${coupon.couponRange =='3' }">selected="selected"</c:if>>全场通用</option>
				</select>
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:choose>
		    <c:when test="${coupon.couponRange =='1' }">
		        <div class="control-group" id="special_div">
		    </c:when>
		    <c:otherwise>
		        <div class="control-group" id="special_div" style="display:none;">
		    </c:otherwise>
		</c:choose>
			<label class="control-label">适用专题：</label>
			<div class="controls">
				<sys:treeselect id="special" name="specialId"
						value="${coupon.specialId}"
						labelName="specialName"
						labelValue="${coupon.specialName}" 
						module="coupon"
						title="适用专题"
						url="/product/special/treeData" cssClass="input-small required" />
						<span class="help-inline"><font color="red">*</font> </span>
						<span style="color:red;" id="special_id_error"></span>
			</div>
		</div>
		<c:choose>
		    <c:when test="${coupon.couponRange =='2' }">
		        <div class="control-group" id="product_div">
		    </c:when>
		    <c:otherwise>
		        <div class="control-group" id="product_div" style="display:none;">
		    </c:otherwise>
		</c:choose>
			<label class="control-label">适用商品：</label>
			<div class="controls">
				<sys:treeselect id="product" name="productId"
						value="${coupon.productId}"
						labelName="productName"
						labelValue="${coupon.productName}" title="适用商品"
						url="/product/product/treeData" cssClass="input-small required" />
			   <span style="color:red;" id="product_no_error"></span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">价格运算表达式：</label>
			<div class="controls">
				<form:input path="priceExpression" htmlEscape="false" maxlength="255" class="text"/>
			</div>
		</div> --%>
		
		<!-- <div class="control-group">
			<label class="control-label">设置：</label>
				<div class="controls">
					<input type="checkbox" id="isExchange" name="isExchange" value="0" />是否允许积分兑换
				</div>
		</div>
		<div class="control-group">
			<label class="control-label"><font color="red">*</font>积分兑换数：</label>
				<div class="controls">
					<input type="text" id="point" name="point" class="text"  maxlength="9" disabled="disabled" onblur="isPoint(this.value)"/>
					<font color="#ffb042"><span id="pointNotnull"></span></font>
				</div>
		</div> -->
		</div>
			<!-- 商品信息 -->
		<table id="coupon_introduction_tab" style="display:none;width:100%;">
		<tr class="input">
				<td>
					<textarea id="editor" name="introduction" class="editor" style="width: 100%;"></textarea>
				</td>
			</tr>
		</table>
		<div class="form-actions">
			<shiro:hasPermission name="coupon:coupon:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>