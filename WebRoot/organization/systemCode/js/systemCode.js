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
			//处理删除、修改、新增按钮样式
			$.f("div","systemCodeMain").f("a","add").attr("class", "button append");
			$.f("div","systemCodeMain").f("a","update").attr("class", "button invalid");
        	$.f("div","systemCodeMain").f("a","delete").attr("class", "button invalid");
			var filterTxt = "id";
			var filterValue = node.id;
			var queryParams = {
				filterTxt : filterTxt,
				filterValue : filterValue
			};
			$.f("div","systemCodeMain").f("table","systemCodeNodeGrid").datagridSinosoft("setUrl","/jubao/systemcode/system-code-node-info!pagedQuery.action");
			$.f("div","systemCodeMain").f("table","systemCodeNodeGrid").datagridSinosoft("load",queryParams);
		}
	});
	//创建右侧列表
	$.f("div","systemCodeMain").f("table","systemCodeNodeGrid").datagridSinosoft({
		toolbar:"div[idFlag=systemCodeNodeGridBar]",
		body:{
			border:false,
			ctrlSelect : true,
			singleSelect : false,
            columns:[[{
            	field : "id",
            	hidden : true
            },{
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
            	field : "codeType",
        		title : "类别",
        		width : 50,
        		halign:"center",
        		align : "center"
            }]],
            onBeforeLoad : function(parm){
				parm.start=(parm.page-1)*parm.rows;
				parm.limit=parm.rows;
				parm.dir = "ASC";
				parm.sort = "code";
			},
			loadFilter:function(data){
				data.rows =  data.result;
				return data;
			},
			onLoadSuccess: function (data) {
				$(this).datagrid("doCellTip", { "max-width": "400px", "delay": 500 });
            },
            onSelect:function(index, row){
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
		systemCodeNodeAddWin.openWin("", node.codeType, systemCodeId, parentId);
	}
}
systemCode.openWin = function(id, codeType, systemCodeId, parentId){
	if(systemCodeNodeAddWinObj){
		systemCodeNodeAddWinObj.window("setTitle","修改代码");
	}else{
		systemCodeNodeAddWinObj = $.f("div","systemCodeNodeAddWin").window({
			title:"增加代码",
			height:190,
			width:600,
			id : id,
			collapsible:false,
			minimizable:false,
			maximizable:false,
			closed:true,
			draggable:false,
			resizable:false,
			modal:true,
			onOpen:function(){
			},
			onClose:function(){
			}
		});
	}
	systemCodeNodeAddWinObj.window("open");
}