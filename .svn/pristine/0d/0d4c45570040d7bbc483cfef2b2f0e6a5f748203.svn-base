function showPetitionInfo(petitionNo,regionCode,currPetitionNo){
	if(petitionInfoWinObj){
		petitionInfoWinObj.panel("options").petitionNo=petitionNo;
		petitionInfoWinObj.panel("options").regionCode=regionCode;
		petitionInfoWinObj.panel("options").currPetitionNo=currPetitionNo;
		petitionInfoWin.init();
	}else{
		petitionInfoWinObj = $.f("div","assmble").window({
	 		  width:825, 
	 		  height:600,
	 		  modal:true,
	 		  title : "信访件信息",
	 		  maximizable:false,
	 		  minimizable:false,
	 		  collapsible:false,
	 		  closed:true,
	 		  petitionNo:petitionNo,
	 		  regionCode:regionCode,
	 		  currPetitionNo:currPetitionNo,
	 		  onOpen:function(){
	 			 petitionInfoWin.init();
	 		  },
	 		  onClose : function(){
		 		  petitionInfoWinObj.panel("options").petitionNo="";
				  petitionInfoWinObj.panel("options").regionCode="";
				  petitionInfoWinObj.panel("options").currPetitionNo="";
	 		  }
		});
	}
	petitionInfoWinObj.window("open");
}

function gridColumn(){
	return [{
		field : "currPetitionNo",
		title : "信访编号",
		width : 115,
		align : "center",
		formatter : function(value,row,index){
			var petitionNo;
			if(value!=null)
				petitionNo = value.substring(0,6)+'-'+value.substring(value.length-6);
			else
				petitionNo = '';
			return "<a style=\"text-decoration:none;font-size: 12px;\" class=\"rowaStyle\"  href='javascript:showPetitionInfo(\""+row.petitionNo+"\",\""+row.regionCode+"\",\""+value+"\")'>"+petitionNo+"</a>";
		}
	},{
		field : "petitionPrtNo",
		title : "条形码",
		width : 100,
		align : "center",
		hidden : true
	},{
		field : "petitionUrgeCode",
		title : "紧急程度",
		width : 100,
		align : "center",
		hidden : true,
		formatter : function(value,row,index){
			if(value=='1'){
				return "是";
			}else{
				return "否";
			}
		}
	},{
		field : "petitionDate",
		title : "信访日期",
		width : 90,
		align : "center",
		hidden : false,
		formatter : function(value,row,index){
			if(null!=value){
				return value.substring(0,10);
			}
			return '';
		}
	},{
		field : "petitionTypeName",
		title : "信访方式",
		width :  100,
		align : "center",
		hidden : true
	},{
		field : "petitionSourceName",
		title : "信访来源",
		width :  100,
		align : "center",
		hidden : true
	},{
		field : "petitionClassName",
		title : "信访类别",
		width :  100,
		align : "center",
		hidden : true
	},{
		field : "accusedName",
		title : "被反映人姓名",
		width :  110,
		align : "center"
	},{
		field : "accusedSexName",
		title : "被反映人性别",
		width :  100,
		align : "center",
		hidden : true
	},{
		field : "otherAccuseds",
		title : "其他被反映人",
		width :  100,
		align : "center",
		hidden : true
	},{
		field : "accusedWorkUnit",
		title : "被反映人单位",
		align : "center",
		width :  110
	},{
		field : "accusedWorkTypeName",
		title : "被反映人单位性质",
		width :  100,
		align : "center",
		hidden : true
	},{
		field : "accusedPositionName",
		title : "被反映人职务",
		align : "center",
		width :  110
	},{
		field : "accusedClassName",
		title : "被反映人职级",
		align : "center",
		width :  110
	},{
		field : "accusedRegionName",
		title : "被反映人所属区域",
		width :  100,
		align : "center",
		hidden : true
	},{
		field : "issueRegionName",
		title : "问题属地",
		width :  100,
		align : "center",
		hidden : true
	},{
		field : "issueTypeName",
		title : "问题类别",
		align : "center",
		width :  110
	},{
		field : "keyWordContent",
		title : "关键字",
		width :  100,
		align : "center",
		hidden : true
	},{
		field : "issueContent",
		title : "问题描述",
		align : "center",
		width :  130
	},{
		field : "instruction",
		title : "批示内容",
		align : "center",
		width :  100,
		hidden : true
	},{
		field : "accuserName",
		title : "反映人姓名",
		align : "center",
		width :  100,
		hidden : true
	},{
		field : "accuserRegionName",
		title : "反映人所属区域",
		align : "center",
		width :  100,
		hidden : true
	},{
		field : "accuserWorkUnit",
		title : "反映人单位",
		align : "center",
		width :  100,
		hidden : true
	},{
		field : "personNum",
		title : "信访人数",
		align : "center",
		width :  60,
		hidden : true
	},{
		field : "groupFlag",
		title : "集体访",
		width : 100,
		align : "center",
		hidden : true,
		formatter : function(value,row,index){
			if(value=='1'){
				return "是";
			}else if(value=='0'){
				return "否";
			}
		}
	},{
		field : "isrepeat",
		title : "是否重复件",
		width : 100,
		align : "center",
		hidden : true,
		formatter : function(value,row,index){
			if(value=='1'){
				return "是";
			}else{
				return "否";
			}
		}
	},{
		field : "isSecretaryFlag",
		title : "是否集体研判",
		width : 100,
		align : "center",
		hidden : true,
		formatter : function(value,row,index){
			if(value=='0'||value==""){
				return "否";
			}else{
				return "是";
			}
		}
	},{
		field : "isSecretaryFlagName",
		title : "集体研判意见",
		width : 100,
		align : "center",
		hidden : true,
		formatter : function(value,row,index){
			if(value=='1'){
				return "初核";
			}else if(value=='2'){
				return "谈话函询";
			}else if(value=='3'){
				return "转办";
			}else if(value=='4'){
				return "暂存";
			}else if(value=='5'){
				return "了结";
			}
		}
	},{
		field : "creatorName",
		title : "录入人",
		width : 100,
		align : "center",
		hidden : true
	},{
		field : "dealTypeName",
		title : "办理方式",
		width : 100,
		align : "center",
		hidden : true
	},{
		field : "createDate",
		title : "办理日期",
		width : 100,
		align : "center",
		hidden : true,
		formatter : function(value,row,index){
			if(null!=value&&"null"!=value){
				return value.substring(0,10);
			}else{
				return "";
			}
		}
	},{
		field : "surveyDept",
		title : "调查部门",
		width : 100,
		align : "center",
		hidden : true
	},{
		field : "endTypeName",
		title : "了结方式",
		width : 100,
		align : "center",
		hidden : true
	},{
		field : "verifyCode",
		title : "初步核实",
		width : 100,
		align : "center",
		hidden : true
	},{
		field : "verifyName",
		title : "谈话函询",
		width : 100,
		align : "center",
		hidden : true
	},{
		field : "fourPatternName",
		title : "四种形态",
		width : 100,
		align : "center",
		hidden : true
	},{
		field : "dealPeopleNum",
		title : "了结处理人数",
		width : 100,
		align : "center",
		hidden : true
	},{
		field : "realityName",
		title : "属实程度",
		width : 100,
		align : "center",
		hidden : true
	},{
		field : "beforeEndNotion",
		title : "拟结意见",
		width : 100,
		align : "center",
		hidden : true
	},{
		field : "regionNname",
		title : "办理机构",
		width : 100,
		align : "center",
		hidden : true
	},{
		field : "currDeptName",
		title : "承办部门",
		align : "center",
		width :  100,
		hidden : true
	},{
		field : "defaultDealerName",
		title : "承办人",
		width : 100,
		align : "center",
		hidden : true
	},{
		field : "transAssignSendDate",
		title : "转交办日期",
		width : 100,
		align : "center",
		hidden : true,
		formatter : function(value,row,index){
			if(null!=value&&"null"!=value){
				return value.substring(0,10);
			}else{
				return "";
			}
		}
	},{
		field : "transAssignToRegionName",
		title : "转交办机构",
		align : "center",
		width :  100,
		hidden : true
	},{
		field : "transSendDate",
		title : "转办日期",
		width : 100,
		align : "center",
		hidden : true,
		formatter : function(value,row,index){
			if(null!=value&&"null"!=value){
				return value.substring(0,10);
			}else{
				return "";
			}
		}
	},{
		field : "transToRegionName",
		title : "转办单位",
		width : 100,
		align : "center",
		hidden : true
	},{
		field : "assignSendDate",
		title : "交办日期",
		width : 100,
		align : "center",
		hidden : true
	},{
		field : "assignToRegionName",
		title : "交办单位",
		width : 100,
		align : "center",
		hidden : true
	},{
		field : "archiveDate",
		title : "归档日期",
		width : 100,
		align : "center",
		hidden : true,
		formatter : function(value,row,index){
			if(null!=value&&"null"!=value){
				return value.substring(0,10);
			}else{
				return "";
			}
		}
	},{
		field : "surveyContent",
		title : "调查结果",
		align : "center",
		width :  100,
		hidden : true
	},{
		field : "activityName",
		title : "流转状态",
		align : "center",
		width :  100,
		hidden : true
	},{
		field : "remark",
		title : "备注",
		align : "center",
		hidden : true,
		width :  100
	}];
}