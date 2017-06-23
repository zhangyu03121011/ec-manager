<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>推广记录管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
// 			var datalistw = ${fns:toJson(page)};
			var data = ${fns:toJson(list)};
			
			var arr = new Array();
			var datalist = ${fns:toJson(list)};
			for(var e=0;e<datalist.length;e++){
				var parentId = datalist[e].parentId;
				var rootId= datalist[e].parentId;
				var flag = true;
				for(var f=0;f<datalist.length;f++){
					if(flag&&rootId==datalist[f].memberId){
						flag = false;
					}
				}
				if(flag){
					arr.push(rootId);
				}
// 				if($.inArray(rootId,arr)==-1 ){
// 					arr.push(rootId);
// 				}
				//清空点击数与关注数
// 				datalist[e].clickCount = 0;
// 				datalist[e].linkCount = 0;
			}
			//打印根节点，根节点的parentid一定为0
			for(var c=0;c<arr.length;c++){
				var pp = arr[c];
				var tempArr = pp.split('-');
				var prodId = tempArr[1];
				var memberName='',parentName='',productName='',productCode='',clickCount=0,linkCount=0;
				for(var n=0;n<data.length;n++){
					if(pp==data[n].parentId){
						memberName = data[n].parentName;
// 						if(data[n].clickCount==0&&data[n].linkCount==0){
// 							break end;
// 						}
						clickCount = data[n].clickCount;
						linkCount = data[n].linkCount;
					}
					if(pp==data[n].memberId){
						parentName = data[n].parentName;
					}
					if(prodId==data[n].productId){
						productName = data[n].productName;
						productCode = data[n].productCode;
					}
				}
				var rowData = {"memberId":arr[c],"memMemberName":memberName,"parentName":parentName,"productName":productName,"productCode":productCode,"parentId":'0'};
				var memberStatus = {"clickCount":clickCount,"linkCount":linkCount};
				$("#treeTableList").append(addTr(rowData,memberStatus,'0'));
				addRow("#treeTableList", datalist,pp);
			} 
			$("#treeTable").treeTable({initialState : "collapsed"});
		});
			
		/**
		 *增加tr
		 */
		function addTr(row,memberStatus,pid){
			var memberId = row.memberId.split("-");
			var parentId = row.parentId.split("-");
			if(parentId=='0'){
				parentId = ' ';
			}
			var tr = '<tr id="' +row.memberId + '" pId="'+ pid +'">'
			    + '<td>' + row.memMemberName + '</td>'
				+	'<td>'+row.productName + '</td>'
				+	'<td>'+row.productCode + '</td>'
				+ 	'<td>'+row.parentName+'</td>'
				+ 	'<td>'+memberStatus.clickCount+'</td>'
				+ 	'<td>'+memberStatus.linkCount+'</td>'
				+	'</tr>';
			return tr;
		}
		
		function addRow(list, data, pid){
			var clickCount=0,linkCount=0;
			for (var i=0; i<data.length; i++){
				var row = data[i];
				var p =row.parentId;
				if(pid == p){
					for(var a=0; a<data.length; a++){
						if(row.memberId==data[a].parentId){
// 							if(data[a].memberStatusName=='点击'){
// 								row.clickCount += 1;
// 			 				}else if(data[a].memberStatusName=='已关注'){
// 			 					row.linkCount += 1;
// 			 				}
							clickCount = data[a].clickCount;
							linkCount = data[a].linkCount;
						}
					}
					var memberStatus = {"clickCount":clickCount ,"linkCount":linkCount};
					$(list).append(addTr(row,memberStatus,p));
					addRow(list, data, row.memberId);
				}
			}
		}
	
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/mem/recommenderLog/">推广记录列表</a></li>
<%-- 		<shiro:hasPermission name="mem:recommenderLog:save"><li><a href="${ctx}/mem/recommenderLog/form">推广记录添加</a></li></shiro:hasPermission> --%>
	</ul>
		<form:form id="searchForm" modelAttribute="recommenderLog" action="${ctx}/mem/recommenderLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="memMemberName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>申请时间：</label>
				<input name="createTime"  type="text" maxlength="20"  readonly="readonly" style="width:135px;" class="input-medium Wdate"
				value="<fmt:formatDate value="${recommenderLog.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>   
				<input name="updateTime"  type="text" maxlength="20" readonly="readonly" style="width:135px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${recommenderLog.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>被推广人</th>
				<th>产品名称</th>
				<th>产品编号</th>
				<th>推荐人</th>
				<th><a href="${ctx}/mem/recommenderLog?sortId=2">点击数</a></th>
				<th><a href="${ctx}/mem/recommenderLog?sortId=1">已关注数</a></th>
			</tr>
		</thead>
		<tbody id="treeTableList">
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>