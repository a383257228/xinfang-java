$.SUI("systemCode");

systemCode.init = function(){
	$.f("div","systemCodeMain").f("ul","systemCodeTree").tree({
		url:sessionlostName+"/systemcode/system-code-info!getSystemCodeList.action",
		animate:true,
		onBeforeLoad:function(node, param){
			if(!node){
				param.node=-1;
			}else {
				delete param["id"]
				param.node=node.id;
			}
		},
		loadFilter: function(data){
			$(data).each(function(index,element){
				if(!element.leaf){
					element.state = "closed";
				}
			});
			return data;
	    },
		onClick:function(node){
			var filterTxt = "";
			var filterValue = "";
			if(node.type == "node"){
				if(node.leaf){
					filterTxt = "id";
					filterValue = node.id;
				}else{
					filterTxt = "codeType;code";
					filterValue = node.codeType+";"+node.code+"%";
				}
			}else if(node.type == "code"){
				filterTxt = "codeType";
				filterValue = node.codeType;
			}
			
			//处理删除、修改、新增按钮样式
			$.f("div","systemCodeMain").f("a","add").attr("class", "button append");
			$.f("div","systemCodeMain").f("a","update").attr("class", "button invalid");
        	$.f("div","systemCodeMain").f("a","delete").attr("class", "button invalid");
			var queryParams = {
				filterTxt : filterTxt,
				filterValue : filterValue
			};
			$.f("div","systemCodeMain").f("table","systemCodeNodeGrid").datagridSinosoft("setUrl","/jubao/systemcode/system-code-node-info!pagedQuery.action");
			$.f("div","systemCodeMain").f("table","systemCodeNodeGrid").datagridSinosoft("load",queryParams);
		},
		onLoadSuccess: function(data){
			var params={
				operationFuncCode : "getSystemCodeList",
				operationFuncName : "查询代码类别列表",
				operationBtnName : "查询",
				operationDesc : "查询代码类别列表",
				operationTypeCode : OperationType.QUERY,
				operationTypeName : OperationType.QUERY_NAME,
				enableDataLog :true
			}
			saveOperaLog(params);
		}
	});
	//创建右侧列表
	$.f("div","systemCodeMain").f("table","systemCodeNodeGrid").datagridSinosoft({
		toolbar:"div[idFlag=systemCodeNodeGridBar]",
		body:{
			border:false,
			ctrlSelect : true,
			singleSelect : false,
			frozenColumns:[[{
            	field : "id",
            	hidden : true
            }]],
            columns:[[{
            	field : "name",
        		title : "代码名称",
        		width : 50,
        		halign:"center",
        		align : "center"
            },{
            	field : "code",
        		title : "代码编码",
        		width : 50,
        		halign:"center",
        		align : "center"
            },{
            	field : "codeTypeName",
        		title : "类别名称",
        		width : 50,
        		halign:"center",
        		align : "center",
        		formatter : function(value, row, index){
        			return row.systemcode.codeTypeName
        		}
            },{
            	field : "codeProperty",
        		title : "代码属性",
        		width : 50,
        		halign:"center",
        		align : "center",
        		formatter : function(val, row, index){
        			if (val == 'AllUnify') {
		                return '全局统一';
		            } else if (val == 'FromUpper') {
		                return '接收上级';
		            } else if (val == 'DownwardUnify') {
		                return '向下统一';
		            } else if (val == 'SelfUse') {
		                return '本级自用';
		            }
        		}
            }]],
            onBeforeLoad : function(parm){
				parm.start=(parm.page-1)*parm.rows;
				parm.limit=parm.rows;
				parm.dir = "ASC";
			},
			loadFilter:function(data){
				data.rows =  data.result;
				return data;
			},
			onLoadSuccess: function (data) {
				var params={
					operationFuncCode : "pagedQuery",
					operationFuncName : "查询代码类型列表",
					operationBtnName : "查询",
					operationDesc : "查询代码类型列表",
					operationTypeCode : OperationType.QUERY,
					operationTypeName : OperationType.QUERY_NAME,
					enableDataLog :true
				}
				saveOperaLog(params);
				$(this).datagrid("doCellTip", { "max-width": "400px", "delay": 500 });
            },
            onSelect:function(index, row){
          	  	$.f("div","systemCodeMain").f("a","add").attr("class", "button invalid");
            	$.f("div","systemCodeMain").f("a","update").attr("class","button lightgreen2");
            	$.f("div","systemCodeMain").f("a","delete").attr("class","button write");
            }
		}	
	});
}
//新增
systemCode.add = function(){
	if($.f("div","systemCodeMain").f("a","add").attr("class")=="button append"){
		var node = $.f("div","systemCodeMain").f("ul","systemCodeTree").tree("getSelected");
		var systemCodeId = "";
		var parentId = "";
		if(node.type=="code"){
			systemCodeId = node.id;
			codeType = node.codeType;
		}else if(node.type=="node"){
			systemCodeId = node.systemCodeId;
			parentId = node.id;
		}
		systemCode.openWin("", node.codeType, systemCodeId, parentId);
	}
}
//修改
systemCode.update = function(){
	var row = $("#systemCodeMain").f("table","systemCodeNodeGrid").datagridSinosoft("getSelected");
	if(row){
		systemCodeNodeAddWin.id = row.id;
		systemCodeNodeAddWin.codeType = row.codeType;
		systemCode.openWin(row.id,row.codeType,row.systemcode.id);
	}
}
//打开新增窗口
systemCode.openWin = function(id, codeType, systemCodeId, parentId){
	if(systemCodeNodeAddWinObj){
		systemCodeNodeAddWinObj.panel("options").defaultId = id;
		systemCodeNodeAddWinObj.panel("options").codeType = codeType;
		systemCodeNodeAddWinObj.panel("options").parentId = parentId;
		systemCodeNodeAddWinObj.panel("options").systemCodeId = systemCodeId;
		systemCodeNodeAddWinObj.window("setTitle","修改代码");
	}else{
		systemCodeNodeAddWinObj = $.f("div","systemCodeNodeAddWin").window({
			title:"增加代码",
			defaultId : id,
			codeType : codeType,
			parentId : parentId,
			systemCodeId : systemCodeId,
			height:190,
			width:600,
			collapsible:false,
			minimizable:false,
			maximizable:false,
			closed:true,
			draggable:false,
			resizable:false,
			modal:true,
			onOpen:function(){
				var id = systemCodeNodeAddWinObj.panel("options").defaultId;
				if(id){
					systemCodeNodeAddWinObj.form("loads","/jubao/systemcode/system-code-node-info!loadData.action?id="+id,function(data){
						var params={
							operationFuncCode : "loadData",
							operationFuncName : "代码类型修改load回显数据",
							operationBtnName : "查询",
							operationDesc : "load回显类型为"+codeType+"的数据",
							operationTypeCode : OperationType.QUERY,
							operationTypeName : OperationType.QUERY_NAME,
							enableDataLog :true
						}
						saveOperaLog(params);
						
						systemCodeNodeAddWinObj.setChecked("available", data.available);
						if (data.codeProperty == 'AllUnify') {
							$.f("div","systemCodeNodeAddWin").f("input","codeProperty").combobox("setText","全局统一");
			            } else if (data.codeProperty == 'FromUpper') {
			            	$.f("div","systemCodeNodeAddWin").f("input","codeProperty").combobox("setText","接收上级");
			            } else if (data.codeProperty == 'DownwardUnify') {
			            	$.f("div","systemCodeNodeAddWin").f("input","codeProperty").combobox("setText","向下统一");
			            } else if (data.codeProperty == 'SelfUse') {
			            	$.f("div","systemCodeNodeAddWin").f("input","codeProperty").combobox("setText","本级自用");
			            }
						return data;
					});
				}else{
					var node = $("#systemCodeMain").f("ul","systemCodeTree").tree("getSelected");
					$.ajax(sessionlostName+"/systemcode/system-code-info!getSystemCodeList.action",{
						data:{
							node : node.id
						},
						success:function(data, textStatus, jqXHR){
							var result = $.toJSON(data);
							var childNodeCodes = [];
							for(var i=0;i<result.length;i++){
								childNodeCodes.push(result[i].code);
							}			
							var max = parseInt(childNodeCodes[0]);
					        for(var i=1; i<childNodeCodes.length; i++) {
					            if(max < parseInt(childNodeCodes[i])) {
					                max = parseInt(childNodeCodes[i]);
					            }
					        }							
							$.f("div","systemCodeNodeAddWin").f("input","code").textbox("setValue",max+1);
						}
					});
					$.f("div","systemCodeNodeAddWin").f("input","codeProperty").combobox("setText","本级自用");
				}
			},
			onClose:function(){
				systemCodeNodeAddWinObj.f("form","systemCodeNodeForm").form("reset");
				systemCodeNodeAddWinObj.panel("options").defaultId = "";
				systemCodeNodeAddWinObj.panel("options").codeType = "";
				systemCodeNodeAddWinObj.panel("options").systemCodeId = "";
				systemCodeNodeAddWin.id=null;
				systemCodeNodeAddWinObj.panel("options").parentId = "";
			}
		});
	}
	systemCodeNodeAddWinObj.window("open");
}
//保存
systemCode.submit = function(){
	systemCodeNodeAddWinObj.f("form","systemCodeNodeForm").form("submit",{
	    url:sessionlostName+"/systemcode/system-code-node-info!save.action",   
	    onSubmit: function(param){
    		param.systemCodeId = systemCodeNodeAddWinObj.panel("options").systemCodeId;
	    	param.parentId = systemCodeNodeAddWinObj.panel("options").parentId;
	    	param.codeType = systemCodeNodeAddWinObj.panel("options").codeType;
	    	if(systemCodeNodeAddWin.id){
	    		param.id = systemCodeNodeAddWin.id;	
	    		param.codeType = systemCodeNodeAddWin.codeType;
	    	}
	    },
	    success:function(data){
	    	if(systemCodeNodeAddWinObj.panel("options").defaultId){
	    		var params={
					operationFuncCode : "save",
					operationFuncName : "修改代码类型信息",
					operationBtnName : "保存信息",
					operationResultCode : "1",
					operationResultName : "成功",
					operationDesc : "修改代码类型信息",
					operationTypeCode : OperationType.MODIFY,
					operationTypeName : OperationType.MODIFY_NAME,
					operationDataDesc : "修改代码类型信息",
					operationDataValue : "代码编码："+systemCodeNodeAddWinObj.f("form","systemCodeNodeForm").f("input","code").textbox("getValue")+",代码名称："+systemCodeNodeAddWinObj.f("form","systemCodeNodeForm").f("input","name").textbox("getValue"),		
					enableDataLog :true
				}
				saveOpeationRecord(params);
	    	}else{
	    		var params={
					operationFuncCode : "save",
					operationFuncName : "新增代码类型信息",
					operationBtnName : "保存信息",
					operationResultCode : "1",
					operationResultName : "成功",
					operationDesc : "新增代码类型信息",
					operationTypeCode : OperationType.ADD,
					operationTypeName : OperationType.ADD_NAME,
					operationDataDesc : "新增代码类型信息",
					operationDataValue : "代码编码："+systemCodeNodeAddWinObj.f("form","systemCodeNodeForm").f("input","code").textbox("getValue")+",代码名称："+systemCodeNodeAddWinObj.f("form","systemCodeNodeForm").f("input","name").textbox("getValue"),		
					enableDataLog :true
				}
				saveOpeationRecord(params);
	    	}
	    	systemCodeNodeAddWinObj.f("form","systemCodeNodeForm").form("reset");
	    	systemCodeNodeAddWinObj.window("close");
	    	dialog.showMiniDialog("self-style", '<i class="icon-success"></i> 代码设置成功！', 2000);
			//刷新树节点
			var node = $("#systemCodeMain").f("ul","systemCodeTree").tree("getSelected");
			$("#systemCodeMain").f("ul","systemCodeTree").tree("reload",node.target);
			$("#systemCodeMain").f("table","systemCodeNodeGrid").datagridSinosoft("reload");
	    }
	});
},
//重置
systemCode.reset = function(){
	if(systemCodeNodeAddWin.id){
		systemCodeNodeAddWinObj.form("loads",sessionlostName+"/systemcode/system-code-node-info!loadData.action?id="+systemCodeNodeAddWin.id,function(data){
			systemCodeNodeAddWinObj.setChecked("available", data.available);
			if (data.codeProperty == 'AllUnify') {
				$.f("div","systemCodeNodeAddWin").f("input","codeProperty").combobox("setText","全局统一");
            } else if (data.codeProperty == 'FromUpper') {
            	$.f("div","systemCodeNodeAddWin").f("input","codeProperty").combobox("setText","接收上级");
            } else if (data.codeProperty == 'DownwardUnify') {
            	$.f("div","systemCodeNodeAddWin").f("input","codeProperty").combobox("setText","向下统一");
            } else if (data.codeProperty == 'SelfUse') {
            	$.f("div","systemCodeNodeAddWin").f("input","codeProperty").combobox("setText","本级自用");
            }
			return data;
		});
	}else{
		$.f("form","systemCodeNodeForm").form("reset");
		systemCodeNodeAddWinObj.setChecked("available","Y");
		$.f("div","systemCodeNodeAddWin").f("input","codeProperty").combobox("setText","本级自用");
	}
}
//删除
systemCode.del =　function(){
	var rows = $("#systemCodeMain").f("table","systemCodeNodeGrid").datagridSinosoft("getSelections");
	if(rows && rows.length>0){
		//开启对话框
		dialog.showNormalDialog("提示", '确定要删除当前角色吗？', {
            ok: {
                name: "确认",
                click: function () {
                    dialog.removeDialog();
                    var idArr=[];
    				$(rows).each(function(index,node){
    					idArr.push(node.id);
    				});
    				var ids = idArr.join(",");
    				$.ajax({
    					url:sessionlostName+"/systemcode/system-code-node-info!dropNodes.action",
    					data:{
    						"ids" : ids
    					},
    					success:function(data, textStatus, jqXHR){
    						var params={
								operationFuncCode : "dropNodes",
								operationFuncName : "删除代码类型",
								operationBtnName : "删除",
								operationResultCode : "1",
								operationResultName : "成功",
								operationDesc : "删除代码类型",
								operationTypeCode : OperationType.DELETE,
								operationTypeName : OperationType.DELETE_NAME,
								operationDataDesc : "删除代码类型",
								operationDataValue : "删除id为:"+ids+"的代码类型",   			
								enableDataLog :true
							}
							saveOpeationRecord(params);
    						
    						
    						//刷新树节点
    						var node = $("#systemCodeMain").f("ul","systemCodeTree").tree("getSelected");
    						$.f("div","systemCodeMain").f("ul","systemCodeTree").tree("reload",node.target);
    						//刷新列表
    						$("#systemCodeMain").f("table","systemCodeNodeGrid").datagridSinosoft("reload");
    					}
    				});
                }
            },
            cancel: {
                name: "取消",
                click: function () {
                    dialog.removeDialog();
                }
            }
        });
	}
}