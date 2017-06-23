<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内购商品设置</title>
	<!-- 基础插件，样式页面的引入 -->
	<meta name="decorator" content="default"/>
	<!-- jqgrid -->
	<%-- <link href="${ctxStatic}/bootstrap/2.3.1/css_default/bootstrap.min.css" rel="stylesheet" type="text/css"/> --%>
	<link href="${ctxStatic}/jqGrid/4.6/css/ui.jqgrid.css" rel="stylesheet" type="text/css"/> 
	<%-- <script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script> --%>
	<script src="${ctxStatic}/jqGrid/4.6/i18n/grid.locale-cn.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jqGrid/4.6/js/jquery.jqGrid.js" type="text/javascript"></script>
	
	<!--jbox-->
   <%--  <link id="skin" rel="stylesheet" href="${ctxStatic}/jquery-jbox/2.3/Skins/Default/jbox.css" />
    <script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/i18n/jquery.jBox-zh-CN.js"></script> --%>
	
	<script type="text/javascript">
		//上一次选择的rowId
		var lastSel="";
		//字段校验，默认为true
		var isValid=true;
		
		$(document).ready(function() {
			 var grid = $("#editList").jqGrid({
				 	datastr:'${productList}',
				 	userdata:'${specialId}',
				    datatype:'jsonstring',
					jsonReader: { // 自定义json数据格式
						root: "list",
						repeatitems : false
					},
					colNames:['专题Id','商品Id','商品名称','商品编号', '成本价', '活动数量','市场价','活动价','限购数量','活动累计销量','操作','库存'],
					colModel:[
						{name:'specialId',index:'specialId', hidden : true, formatter:specialIdFormatter},
						{name:'productId',index:'productId', hidden : true},
						{name:'productName',index:'productName', width:200, frozen : true},
						{name:'productNo',index:'productNo', width:100, frozen : true},
						{name:'cost',index:'cost', width:80, align:"right", formatter: 'number'},
						/* {name:'activtyCount',index:'activtyCount', width:80, align:"right", formatter: 'integer', editable: true, editrules:{custom:true, custom_func:activtyCountCheck}}, */
						{name:'activtyCount',index:'activtyCount', width:80, align:"right", formatter: 'integer', editable: true, editoptions:{ dataInit:function (element) { filterEnterKey(element);}}},
						{name:'marketPrice',index:'marketPrice', width:80, align:"right", formatter: 'number'},
						{name:'activtyPrice',index:'activtyPrice', width:80, align:"right", formatter: 'number', editable: true, editoptions:{dataInit:function (element) { filterEnterKey(element);}}},
						{name:'limitCount',index:'limitCount', width:80, align:"right", formatter: 'integer', editable: true, editoptions:{dataInit:function (element) { filterEnterKey(element);}}},
						{name:'activtySalesCount',index:'activtySalesCount', width:80, align:"right", formatter: 'integer'},
						{name:'btns',index:'total', width:100,formatter: delFormatter},
						{name:'stock',index:'stock', hidden : true},
					],
					beforeSelectRow:function(id){;
						//行选中之前校验上一行的数据是否正确
						if(id && id != lastSel){
							if(lastSel != ""){
								//保存上一行
								$('#editList').jqGrid('saveRow',lastSel,false,'clientArray');
								//校验上一行数据
								var latRowData = $("#editList").jqGrid('getRowData', lastSel);
								var activtyCount = parseInt(latRowData.activtyCount);
								
								var limitCount = parseInt(latRowData.limitCount);
								
								if('NaN'==activtyCount || 'undefined'==activtyCount){
									$.jBox.tip("活动数量不是数字");
									$("#editList").jqGrid('setCell',lastSel, 'activtyCount', 0);
									$("#editList").jqGrid('setSelection',lastSel);
									$('#editList').jqGrid('editRow',lastSel,{keys : true});
									return false;
								}
								
								var stock = parseInt(latRowData.stock);
								if(activtyCount< 1 || activtyCount >= stock){
									$.jBox.tip("活动数量请输入1-"+stock+"之内的数字");
									$("#editList").jqGrid('setSelection',lastSel);
									$('#editList').jqGrid('editRow',lastSel,{keys : true});
									return false;
								}
								
								var activtyPrice = parseFloat(latRowData.activtyPrice);
								var cost = parseFloat(latRowData.cost);
								if('NaN'==activtyPrice || 'undefined'==activtyPrice){
									$.jBox.tip("活动价格不是数字");
									$("#editList").jqGrid('setCell',lastSel, 'activtyCount', 0);
									$("#editList").jqGrid('setSelection',lastSel);
									$('#editList').jqGrid('editRow',lastSel,{keys : true});
									return false;
								}
								
								if(activtyPrice <= cost){
									$.jBox.tip("活动价格应大于成本价["+ cost +"]");
									$("#editList").jqGrid('setSelection',lastSel);
									$('#editList').jqGrid('editRow',lastSel,{keys : true});
									return false;
								}
								
								if('NaN'==limitCount || 'undefined'==limitCount){
									$.jBox.tip("限购数量不是数字");
									$("#editList").jqGrid('setCell',lastSel, 'activtyCount', 0);
									$("#editList").jqGrid('setSelection',lastSel);
									$('#editList').jqGrid('editRow',lastSel,{keys : true});
									return false;
								}
								
								if(limitCount< 1 || limitCount>activtyCount){
									$.jBox.tip("活动数量请输入1-"+activtyCount+"之内的数字");
									$("#editList").jqGrid('setSelection',lastSel);
									$('#editList').jqGrid('editRow',lastSel,{keys : true});
									return false;
								}
							}
							lastSel=id;
							//编辑当前行
							$('#editList').jqGrid('editRow',id,{keys : true});
						}
						
						return true;
					},
					
					gridComplete : function() {
						$(window).resize();
					}
				});
			    
				$(window).resize(function(){
					grid.jqGrid('setGridWidth',$(window).width());
					grid.jqGrid('setGridHeight',$(window).height()*3/5);
				}).resize(); 
				
				/**
				 *保存内购商品
				 */
				$("#saveBtn").click(function(){
					$("#saveBtn").attr('disabled',"true");
					if(lastSel!==""){
						$('#editList').jqGrid('saveRow',lastSel,false,'clientArray');
					}
					
					var rowIds = $("#editList").jqGrid('getDataIDs'); //获取本地数据
					var rowArray = new Array();
					if(rowIds){ 
						for(var i = 0, j = rowIds.length; i < j; i++) {
							var curIndex = rowIds[i]; 
							var curRowData = $("#editList").jqGrid('getRowData', curIndex);
							rowArray.push(curRowData);
							
							var activtyCount = parseInt(curRowData.activtyCount);
							
							if('NaN'==activtyCount || 'undefined'==activtyCount){
								$.jBox.tip("活动数量不是数字");
								$("#editList").jqGrid('setCell',curIndex, 'activtyCount', 0);
								$("#editList").jqGrid('setSelection',curIndex);
								$('#editList').jqGrid('editRow',curIndex,{keys : true});
								return false;
							}
							
							var stock = parseInt(curRowData.stock);
							if(activtyCount < 1 || activtyCount >= stock){
								$.jBox.tip("活动数量请输入1-"+stock+"之内的数字");
								$("#editList").jqGrid('setSelection',curIndex);
								$('#editList').jqGrid('editRow',curIndex, {keys : true});
								return false;
							}
							
							var activtyPrice = parseFloat(curRowData.activtyPrice);
							var cost = parseFloat(curRowData.cost);
							if('NaN'==activtyPrice || 'undefined'==activtyPrice){
								$.jBox.tip("活动价格不是数字");
								$("#editList").jqGrid('setCell',curIndex, 'activtyCount', 0);
								$("#editList").jqGrid('setSelection',curIndex);
								$('#editList').jqGrid('editRow',curIndex,{keys : true});
								return false;
							}
							
							if(activtyPrice <= cost){
								$.jBox.tip("活动价格应大于成本价["+ cost +"]");
								$("#editList").jqGrid('setSelection',curIndex);
								$('#editList').jqGrid('editRow',curIndex,{keys : true});
								return false;
							}
							
							var limitCount = parseInt(curRowData.limitCount);
							if('NaN'==limitCount || 'undefined'==limitCount){
								$.jBox.tip("限购数量不是数字");
								$("#editList").jqGrid('setCell',curIndex, 'activtyCount', 0);
								$("#editList").jqGrid('setSelection',curIndex);
								$('#editList').jqGrid('editRow',curIndex,{keys : true});
								return false;
							}
							
							if(limitCount< 1 || limitCount>activtyCount){
								$.jBox.tip("活动数量请输入1-"+activtyCount+"之内的数字");
								$("#editList").jqGrid('setSelection',curIndex);
								$('#editList').jqGrid('editRow',curIndex,{keys : true});
								return false;
							}
						}
					}
					
					var postJson = {"productList":JSON.stringify(rowArray)};
					console.log(postJson);
					
					$.ajax({
						cache:false,
						type:"post",
						url:"${ctx}/product/inAppPurchase/batchEditProduct",
						contentType:'application/json;charset=utf-8',
						data:JSON.stringify(postJson),
						dataType :'json',
						success:function(data){
							if(data.status){
								$.jBox.tip("保存成功");
								var jumpUrl = '${ctx}/product/inAppPurchase/updateSelectProduct?specialId=${specialId}&updateFlag=${updateFlag}';
								//跳转到内购商品查看页面
								window.location.href=jumpUrl;
							}else{
								//$.jBox.tip("保存失败");
								$.jBox.tip(data.msg);
								$("#saveBtn").removeAttr('disabled');
							}
							
						},
						error:function(){
							$.jBox.tip("保存失败");
							$("#saveBtn").removeAttr('disabled');
						}
					});
					
				});
		});
		
		/**
		 *从userdata中读取数据
		 */
		function specialIdFormatter(cellvalue, options, rowObject){
			var specialId = $("#editList").getGridParam('userdata');
			return specialId;
		}
		
		/**
		 * 删除列格式化
		 */
		function delFormatter(cellvalue, options, rowObject){
			var rowId = options.rowId;
			return "<a href='#' data='"+rowId+"' onclick='delRecordDetail(this)'>删除</a>";
			
		}
		
		/**
		 *通过rowId删除行
		 */
		function delRecordDetail(obj){
			var rowId = $(obj).attr('data');
			jQuery('#editList').jqGrid('delRowData',rowId);
		}
		
		/**
		 *活动数量的校验
		 */
		function activtyCountCheck(cellvalue, options, rowObject) {
			if (cellvalue < 0 || cellvalue > 20) {
				isValid = false;
				return [false,"Please enter value between 0 and 20"];
			}else {
				return [true,""];
			}
		}
		
		/**
		 *拦截回车按钮
		 */
		function filterEnterKey(element){
                 $(element).keydown(function (event) {
                     if (event.keyCode == 13) { 
                    	 console.log('do nothing when put enter'); 
                    	 return false;
                    }
                 });
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/product/inAppPurchase/">内购信息列表</a></li>
		<li class="active"><a href="${ctx}/product/inAppPurchase/form">内购信息添加</a></li>
	</ul><br/>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/product/inAppPurchase/formView?id=${product.specialId}">内购基本信息</a></li>
		<li><a href="${ctx}/product/inAppPurchase/productList?specialId=${product.specialId}">内购商品添加</a></li>
		<li class="active"><a href="javascript:volid(0);">内购商品设置</a></li>
	</ul>
	
	<table id="editList">
	</table>
	
	<div class="form-actions">
		<shiro:hasPermission name="product:special:view">
			<input id="saveBtn" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
		</shiro:hasPermission>
		<input id="cancelBtn" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</body>
</html>