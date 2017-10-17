
	/**
	 * 操作日志前台开关，如果不需要记录操作数据日志，可以设置为false，
	 * @type Boolean
	 */
	 enableDataLog = true;
	
	/**
	 * 是否开启浏览器关闭时对登陆日志的更新，默认为true
	 * @type Boolean
	 */
	 enableLogOnUnload = true;
	
	/**
	 * 错误信息提示字符串
	 * @type String
	 */
	 MESSAGE_TITLE_ERROR = '错误';
	/**
	 * 操作等级
	 * @type 
	 */
	 OperationLevel = {
		NORMAL : 'NORMAL',
		NORMAL_NAME : "一般",
		WARN : "WARN",
		WARN_NAME : "警告",
		SERIOUS : "SERIOUS",
		SERIOUS_NAME : "严重",
		FATAL : "FATAL",
		FATAL_NAME : "致命"
	},
	/**
	 * 操作类型
	 * @type 
	 */
     OperationType = {
		ADD_NAME : "新增",
		ADD : "add",
		MODIFY_NAME : "修改",
		MODIFY : "modify",
		DELETE_NAME : "删除",
		DELETE :"delete",
		QUERY_NAME : "查询",
		QUERY : "query",
		BACK_NAME : "回退",
		BACK : "back",
		EXPORT_NAME : "导出",
		EXPORT : "export",
		SHOW_NAME : "显示",
		SHOW : "show",
		CLOSE_NAME : "关闭",
		CLOSE : "close",
		RESET_NAME : "重置",
		RESET : "reset",
		DOWNLOAD_NAME :"下载",
		DOWNLOAD : "downLoad",
		SUSPEND_NAME : "暂停",
		SUSPEND : "suspend",
		START_NAME : "开始",
		START :	"start",
		SCAN_NAME:"扫描",
		SCAN:"scan",
		PRINT_NAME : "打印",
		PRINT : "print"
	}
	/**
	 * 记录前台操作对应的操作日志
	 * @param {} operationLogJsonObj 操作日志参数对应json对象
	 */
	 //记录主页面菜单导航保存日志方法
	 function saveMenuOperaLog(operationLogJsonObj){
		if(operationLogJsonObj){
			var OPERLEVEL = OperationLevel;
			var OPERTYPE = OperationType;
			operationLogJsonObj.operationLevelCode = OPERLEVEL.NORMAL;
			operationLogJsonObj.operationLevelName = OPERLEVEL.NORMAL_NAME;
			operationLogJsonObj.operationResultCode = '1';
			operationLogJsonObj.operationResultName = '成功';
			operationLogJsonObj.operationTypeCode = OPERTYPE.QUERY;
			operationLogJsonObj.operationTypeName = OPERTYPE.QUERY_NAME;
			operationLogJsonObj.operationBtnName = '-----',
			operationLogJsonObj.countNum = 0
		}
		if(operationLogJsonObj){
			$.ajax({
				url : sessionlostName+'/log/operation-log!save.action',
				data : operationLogJsonObj,
				error : function(res,opts){
					alert('保存功能菜单对应的操作日志有误！');
				}
			});
		}
	 }
	/**
	 * 前台直接保存操作日志
	 * @param {} jsonObj 操作日志json对象
	 * @param {} millis 多少毫秒之后再发生请求保存日志，默认为1秒
	 */
	 //查询参数的日志保存方法
	  function saveOperaLog(jsonObj){
		 var par ={
		    "operationFuncCode": jsonObj.operationFuncCode,//方法英文名称
		    "operationFuncName":jsonObj.operationFuncName,//方法名称
		    "operationBtnName": jsonObj.operationBtnName,//操作按钮名称
		    "operationDesc": jsonObj.operationDesc,//日志表操作描述
		    "operationTypeCode": jsonObj.operationTypeCode,//操作类型code(暂时不用传参--忽略)
		    "operationTypeName": jsonObj.operationTypeName,//操作类型name(暂时不用传参--忽略)
		    "enableDataLog" : jsonObj.enableDataLog //操作日志开关--是否进行保存，默认传true
	    }
		var level={ 
			"operationLevelCode": OperationLevel.NORMAL,//操作等级code
			"operationLevelName": OperationLevel.NORMAL_NAME,//操作等级name
			"operationResultCode": "1",//查询默认传值为成功，则不需要传这两个参数
			"operationResultName": "成功",//查询默认传值为成功，则不需要传这两个参数
			"countNum" : 0
		}
		var params=$.extend(par,level)
		var millis = 1000;
		setTimeout(function(){
			if(jsonObj){
				$.ajax({
					url : '/jubao/log/operation-log!save.action',
					data : params,
					success : function(res,opts){
					
					},
					error:function(){
						alert('保存操作日志有误！');
					}
				});
			}
		},millis)
	}
	  //增删改（需要记录处理数据）日志保存方法
	  function saveOpeationRecord(operationLogJsonObj){
	      var par ={
			    "operationFuncCode": operationLogJsonObj.operationFuncCode,//方法英文名称
			    "operationFuncName":operationLogJsonObj.operationFuncName,//方法名称
			    "operationBtnName": operationLogJsonObj.operationBtnName,//操作按钮名称
			    "operationDesc": operationLogJsonObj.operationDesc,//日志表操作描述
			    "operationResultCode": operationLogJsonObj.operationResultCode,//操作结果code  1--成功、2--失败
			    "operationResultName": operationLogJsonObj.operationResultName,//操作结果name  --成功、失败
			    "operationTypeCode": operationLogJsonObj.operationTypeCode,//操作类型code(暂时不用传参--忽略)
			    "operationTypeName": operationLogJsonObj.operationTypeName,//操作类型name(暂时不用传参--忽略)
			    "operationDataDesc": operationLogJsonObj.operationDataDesc,//操作日志的数据表描述
			    "operationDataValue": operationLogJsonObj.operationDataValue,//保存在操作日志的数据表--操作具体数据
			    "enableDataLog" : operationLogJsonObj.enableDataLog //操作日志开关--是否进行保存，默认传true
		  }
	      var level={ 
    		  "operationLevelCode": OperationLevel.NORMAL,//操作等级code
		      "operationLevelName": OperationLevel.NORMAL_NAME//操作等级name
		  }
	      //合并json参数
	      var params=$.extend(par,level)
		  $.ajax({
				url:"/jubao/organperson/person-info!saveOperationLogRecordInfo.action",
				data:params,
				success:function(data){
				}
			});
	  }	  