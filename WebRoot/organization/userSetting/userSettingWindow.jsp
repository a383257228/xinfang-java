<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
  <head>
  </head>
 <body>
	<div  idFlag="userSettingWindow" style=" display:none;background-color: #f5f8fd">
		<form method="post" idFlag="userWinForm"> 
				<table class="tablebox" style="width:90%;height:350px;margin: auto;border: 0px;padding: 5px;">
					<tr>
						<td>用户登录名<font style="color:red">*</font>:</td>
						<td >
							<input type="hidden" name="id"></input>
							<input  required="true"   class="sinosoft-textbox" par="width:200" name="userEname" idFlag="userEname"  prompt="最多输入32个字符" par="validType:'customChinaValidator[16]'"></input>
						</td>
						<td>用户姓名<font style="color:red">*</font>:</td>
						<td>
							<input required="true"  class="sinosoft-textbox"  par="width:220" name="userCname" idFlag="userCname"  prompt="最多输入32个字符" par="validType:'customChinaValidator[16]'"></input>
						</td>
					</tr>
					<tr>
						<td>登录密码<font style="color:red"  >*</font>:</td>
						<td>
							<input required="true"  class="sinosoft-textbox" par="width:200,type:'password',validType:'ispassword[6]'" name="userPwd" idFlag="userPwd" prompt="********"></input>
						</td>
						<td>确认密码<font style="color:red">*</font>:</td>
						<td>
							<input required="true"  class="sinosoft-textbox"  par="width:220,type:'password',validType:'ispassword[6]'" name="userPwd2"  idFlag="userPwd2" prompt="********"></input>
						</td>
					</tr>
					<tr>
						<td>是否有效:</td>
						<td>
							<span class="sinosoft-checkedbox" par="width:200,labelText:'',group:'state' " idFlag="invalidFlag"></span>
						</td>
						<td>所属部门:</td>
						<td>
							<input type="hidden" name="organPersonRelationOid"></input>
							<input type="hidden" name="organizationInfoOid"/>
							<input class="sinosoft-combotree" name="orgName" idFlag="orgName" 
							 par="width:220,
							 	  url:'/jubao//petition/petition-organ-info!getOrganColumnsTree.action',
								  queryParams:{
									  invalidFlag:1,
									  node:-1,
									  purpose:'',
									  codeType:'',
									  organPersonRelationOrganizationOid:''
								  },
								  loadFilter:userSettingGrid.classCodeLoad,
								  onBeforeLoad:userSettingGrid.beforeLoad
							 "/>
						</td>
					</tr>
					<tr>
						<td>电话:</td>
						<td> 
							<input class="sinosoft-textbox" par="width:200,validType:'telephone'"    name="telephone" idFlag="telephone" ></input>
						</td>
						<td>排序编码<font style="color:red">*</font>:</td>
						<td> 
							<input class="sinosoft-textbox" par="width:220,required:true"    name="defaultDealerNameOrder" idFlag="defaultDealerNameOrder" ></input>
						</td>
					</tr>
					<tr>
						<td>用户类型<font style="color:red">*</font>:</td>
						<td>
							<input idFLag="leaderFlag" class="sinosoft-combobox" 
							par="width:200,valueField: 'label',
								valueField: 'label',
								textField: 'value',
								panelHeight:'auto',
								required:true,
								editable:false,
								data: [{
									label: '1',
									value: '领导'
									
								},{
									label: '2',
									value: '秘书'
								},{
									label: '3',
									value: '一般用户'
								},{
									label: '4',
									value: '业务管理员'
								},{
									label: '5',
									value: '委领导'
								},{
									label: '6',
									value: '系统管理员'
								},{
									label: '7',
									value: '安全管理员'
								},{
									label: '8',
									value: '安全审计员'
								}]" name="leaderFlag" />
						</td>
					</tr>
					<tr>
						<td  colspan="4">
							<div style="text-align: center;margin-top: 2px;height:47px;" >
									<a class="button deepblue" style="margin-left: 20px;width:53px;" onclick="userSettingGrid.saveUser()">保存</a>
									<a class="button write" style="width:53px;" onclick="userSettingGrid.resetWin()">重置</a>
							</div>
						</td>
					</tr>
				</table>
			</form>
	</div>
  </body>
  <script type="text/javascript">
  	var userSettingWinObj;
  	$.SUI("userSettingGrid");
  	userSettingGrid.classCodeLoad = function(data){
  		$(data).each(function(index){
			if(!data[index].leaf){
				data[index].state="closed";
				data[index].leaf="true"
			}else{
				delete data[index].state;
				data[index].leaf="true"
			}
		});
		return data;
	}
  	userSettingGrid.beforeLoad=function(node,par){
		if(node){
			par.node=node.id;
			par.purpose='2';
		}
		par.invalidFlag="1";
	}
  	$.f("div","userSettingWindow").f("input","orgName").combotree({
	    onSelect : function(row){
	    	if(row.orgOrDept != "dept"){
	    		dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;只能选择部门信息！', 3000);
	    		return;
	    	}
	    	$.f("div","userSettingWindow").findName("input","organizationInfoOid").val(row.id);
	    } 
	}); 
  	$.f("div","userSettingWindow").f("input","leaderFlag").combobox({
	    onSelect : function(row){
	    	if(row.label=="6"||row.label=="7"||row.label=="8"){
	    		if($.f("div","userSettingWindow").f("input","userPwd").textbox("getValue").length<10){
	    			dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;用户类型为系统管理员、安全管理员或安全审计员时，密码长度不能小于十位！', 3000);
	    			return;
	    		}
	    	}
	    } 
	}); 
  </script>
</html>
