<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
  <head>

  </head>
  <body>
	<div  idFlag="organizationSettingWindow" id="organizationSettingWindow" style="display:none;background-color: #f5f8fd">
		<form method="post"> 
			<table class="tablebox" style="width:90%;margin: auto;border: 0px;padding: 5px;">
				<tr>
					<td>
						区域编码<font style="color:red">*</font>：
					</td>
					<td>
						<input type="hidden" name="id"></input>
						<input class="sinosoft-textbox" style="width: 200px"  name="orgCode" par="readonly:true"></input>
					</td>
					<td>
						区域名称<font style="color:red">*</font>：
					</td>
					<td>
						<input class="sinosoft-textbox" style="width: 200px"  name="text" required="true"  prompt="最多输入32个字符" par="validType:'customChinaValidator[32]'"></input>
					</td>
				</tr>
				<tr>
					<td>
						区域简称<font style="color:red">*</font>：
					</td>
					<td>
						<input class="sinosoft-textbox" style="width: 200px"    name="orgShortCname"  required="true"  par="validType:'customChinaValidator[16]'" prompt="最多输入16个字符"></input>
					</td>
					<td>
						区域显示顺序<font style="color:red">*</font>：
					</td>
					<td>
						<input class="sinosoft-textbox" style="width: 200px"  name="organOrder"></input>
					</td>
				</tr>
				<tr>
					<td>
						是否有效：
					</td>
					<td>
						<span class="sinosoft-checkedbox"  par="labelText:'',group:'state' "></span>
					</td>
					<td>
						上级区域名称：
					</td>
					<td>
						<input type="hidden" name="parentId"></input>
						<input type="hidden" name="relation"></input>
						<input type="hidden" name="organizationRelationOid"></input>
						<input class="sinosoft-textbox" style="width: 200px"  par="readonly:true" name="parentOrgCname"></input>
					</td>
				</tr>
				<tr>
					<td>
						<span style="
						    position: relative;
						    top: -34px;
						">
							区域描述：
						</span>
					</td>
					<td colspan="3">
						<div>
						<input class="sinosoft-textbox" name="description" par="multiline:true,validType:'singleTextValidator[65]'"  prompt="最多输入65个字符"  style="width:580px;height: 80px;"></input>
						</div>
					</td>
				</tr>
				<tr>
					<td  colspan="4">
						<div style="text-align: center;margin-top: 5px;" >
								<!-- <a class="button deepblue" style="margin-left: 20px;width:53px;">保存</a> -->
								<a class="button deepblue" style="margin-left: 20px;width:53px;">保存</a>
								<a class="button write" style="width:53px;" >重置</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	   </div>		
	    <script type="text/javascript">
	  	  $.SUI("organizationSettingWindow");
	  	  organizationSettingWindow=$.f("div","organizationSettingWindow");
	  	  organizationSettingWindow.resetObj="";
	  	  
	  	  organizationSettingWindow.oROid = "";
	  	  organizationSettingWindow.oid = "";
	  	  var flag="insert";
	  	  //窗体的表单初始化
	  	  organizationSettingWindow.init=function(){
	  		  //用于后台判断新增还是修改
	  		  $.f("div","organizationSettingWindow").window("setTitle","新增区域信息")  
		  	  $.parser.parse("#organizationSettingWindow"); 
	  	 	  var row=organizationBarGrid.treegridSinosoft("getSelected");
	  		  organizationSettingWindow.find(".sinosoft-checkedbox").checkedBox("setUnCheck");
	  	  
	  		  //判断是新增还是修改
	  		  if(organizationSettingWindow.operation=="add"){
  			       //如果是新增
				   $.ajax({  //获取区域编码
			  			url:sessionlostName+"/petition/petition-organ-info!getMaxOrgCodeAndOrder.action",
			  			type:"POST",
			  			data:{
			  				orgCode:row.orgCode
			  			},
			  			success:function(data){
			  				data=$.toJSON(data);
			  				flag="insert";
			  				//上传id
			  				var obj ={};
			  				obj.orgCode=data.maxOrgCode;
			  				obj.organOrder=data.organOrder;
			  				obj.parentId=row.id;
			  				obj.parentOrgCname=row.text;
			  				obj.id=null;
			  				
			  				organizationSettingWindow.findName("input","organizationRelationOid").val(row.id);
			  				organizationSettingWindow.findName("input","relation").val("1");
			  				
			  				organizationSettingWindow.find("form").form("reset");
			  				organizationSettingWindow.find(".sinosoft-checkedbox").checkedBox("setCheck");
			  				organizationSettingWindow.resetObj=obj;
			  				organizationSettingWindow.find("form").form({
			  					 url:sessionlostName+"/petition/petition-organ-info!saveOrganizationInfo.action",
			  					 parm:data
			  				});
			  				organizationSettingWindow.find("form").form("load",obj);
			  			}
			  	   });
	  		  }else if(organizationSettingWindow.operation=="update"){
		  			$.f("div","organizationSettingWindow").window("setTitle","修改区域信息");
  			  		flag="save";
  			  		
		  			var obj = row;
		  			$.ajax({
						url:sessionlostName+ "/organization/organization-relation-info!loadData.action",
						type:"POST",
						async: false,
						data:{
							id:row.id
						},
						success:function(data){
							var params={
								operationFuncCode : "loadData",
								operationFuncName : "组织设置修改load回显数据",
								operationBtnName : "查询",
								operationDesc : "load回显"+row.text+"的数据",
								operationTypeCode : OperationType.QUERY,
								operationTypeName : OperationType.QUERY_NAME,
								enableDataLog :true
							}
							saveOperaLog(params);
							
							var result = $.toJSON(data).organizationInfo;
							organizationSettingWindow.oROid = $.toJSON(data).id;
			  			  	organizationSettingWindow.oid = result.id;
			  			  	
							obj.description = result.description;
				  			obj.id = result.id;
				  			
				  			organizationSettingWindow.findName("input","organizationRelationOid").val($.toJSON(data).id);
				  			organizationSettingWindow.findName("input","relation").val($.toJSON(data).relation);
						}
					});
		  			
	  				if(row.invalidFlag=="1"){
	  					organizationSettingWindow.find(".sinosoft-checkedbox").checkedBox("setCheck");
	  				}
	  				organizationSettingWindow.find("form").form("reset");
	  				organizationSettingWindow.resetObj=obj;
	  				
	  				organizationSettingWindow.find("form").form({
	  					 url:sessionlostName+"/organization/organization-info!saveOrganizationInfo.action"
	  				});
	  				organizationSettingWindow.find("form").form("load",obj);
	  		   }
	  	 	 	//组织机构保存的路径
	  			organizationSettingWindow.find("form").form({
		 	   	   	 onSubmit: function(par){
		 	   		   	 var validate=organizationSettingWindow.find("form").form("validate");
		 	   	   		 par.flag=flag;
		 	   	   		 
		 	   	   		 if(organizationSettingWindow.find(".sinosoft-checkedbox").checkedBox("isChecked")){
		 	   	   				par.invalidFlag="true";		
		 	   	   		 }else{
		 	   	   		 		par.invalidFlag="false";	
		 	   	   		 }
		 	   	   		 par.orgCname = organizationSettingWindow.find("input[name='text']").val();
			 	   	   	 if(flag=="insert"){
			 	   	  	 	var parm=organizationSettingWindow.find("form").form("options").parm;
			 	   	   		par.maxNoInfoId=parm.maxNoInfoId;
			 	   			par.purpose=1;
			 	   	    }
			 	   	   	 return validate;
		 	   	   	 },    
		 	   	   	 success:function(){  
		 	   	   		 if(organizationSettingWindow.operation=="add"){
			 	   	   		 var params={
			 	   	   			operationFuncCode : "submit",
			 	   	   			operationFuncName : "新增组织区域信息",
			 	   	   			operationBtnName : "保存",
			 	   	   			operationResultCode : "1",
			 	   	   			operationResultName : "成功",
			 	   	   			operationDesc : "新增组织区域信息",
			 	   	   			operationTypeCode : OperationType.ADD,
			 	   	   			operationTypeName : OperationType.ADD_NAME,
			 	   	   			operationDataDesc : "新增组织区域信息",
			 	   	   			operationDataValue : "区域编码："+organizationSettingWindow.find("input[name='orgCode']").val()+",区域名称："+organizationSettingWindow.find("input[name='text']").val(),		
			 	   	   			enableDataLog :true
			 	   	   		 }
			 	   	   		 saveOpeationRecord(params);
		 	   	   		 }else if(organizationSettingWindow.operation=="update"){
		 	   	   		 	 var params={
				 	   	   		operationFuncCode : "submit",
			 	   	   			operationFuncName : "修改组织区域信息",
			 	   	   			operationBtnName : "保存",
			 	   	   			operationResultCode : "1",
			 	   	   			operationResultName : "成功",
			 	   	   			operationDesc : "修改组织区域信息",
			 	   	   			operationTypeCode : OperationType.MODIFY,
			 	   	   			operationTypeName : OperationType.MODIFY_NAME,
			 	   	   			operationDataDesc : "修改组织区域信息",
			 	   	   			operationDataValue : "区域编码："+organizationSettingWindow.find("input[name='orgCode']").val()+",区域名称："+organizationSettingWindow.find("input[name='text']").val(),		
			 	   	   			enableDataLog :true
				 	   	   	 }
				 	   	   	 saveOpeationRecord(params);
		 	   	   		 }
		 	   	   		 
		 	   	   		 
		 	   	     	dialog.showMiniDialog("self-style", '<i class="icon-success"></i> 组织机构保存成功！', 3000);
		 	   	     	if(flag=="insert"){
			 	   	     	if(row.childrensize){
		 	   	     			var parentrow=organizationBarGrid.treegridSinosoft("getParent",row.id);
		 	   	     			organizationBarGrid.treegridSinosoft("reload",parentrow.id);
		 	   	     		}else{
			 	   	     		organizationBarGrid.treegridSinosoft("reload",row.id);
		 	   	     		}
		 	   	     	}else{
			 	   	     	if(row){
		 	   	     			var parentrow=organizationBarGrid.treegridSinosoft("getParent",row.id);
		 	   	     			organizationBarGrid.treegridSinosoft("reload",parentrow.id);
		 	   	     		}else{
		 	   	     			organizationBarGrid.treegridSinosoft("reload",row.id);
		 	   	     		}
		 	   	     	}
		 	   		  $.f("div", "organizationSettingWindow").window("close");
		 	   	   	}
		 	});
  	  }
	  	 /**
	  	  *重置按钮
	  	  *
	  	  */
	  	 organizationSettingWindow.reset=function(){
	  		organizationSettingWindow.find("form").form("reset");//所有置为空
	  		if(organizationSettingWindow.resetObj.invalidFlag=="2"){
				organizationSettingWindow.find(".sinosoft-checkedbox").checkedBox("setUnCheck");
			}else{
				organizationSettingWindow.find(".sinosoft-checkedbox").checkedBox("setCheck");
			}
	  		organizationSettingWindow.find("form").form("load",organizationSettingWindow.resetObj);//加载数据
	  	 }
	   	 $(function(){
	   		 //点击保存按钮
	   		 organizationSettingWindow.find(".deepblue").click(function(){
		   		 if(flag=="update"){
		   			 $.ajax({
						 	url:sessionlostName+ "/organization/organization-relation-info!judgeOrganEditCNameRepeat.action",
							type:"POST",
							async: false,
							data:{
								orgCname : organizationSettingWindow.find("input[name='text']").val(),
								organizationRelationInfoOid : organizationSettingWindow.oROid,
								orgOid : organizationSettingWindow.oid
							},
							success:function(data){
								if(data==true||data=='true'){
									dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;机构下已经存在该中文名的机构！', 3000);
				   					return;
								}else{
									organizationSettingWindow.find("form").form("submit");
								}
							}
					});
	   	   		}else if(flag=="insert"){
	   	   			organizationSettingWindow.find("form").form("submit");
	   	   		}
		     });
		     //点击重置按钮
		     organizationSettingWindow.find(".write").click(function(){
		    	  organizationSettingWindow.reset();
		     })
	   	 })
	    </script>
  </body>
</html>
