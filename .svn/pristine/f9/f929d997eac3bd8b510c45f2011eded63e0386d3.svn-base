$.extend($.fn.validatebox.defaults.rules, {  
	/**
	 * @param {Object} value
	 * @param {Object} param
	 * @return {TypeName} 
	 */
	customChinaValidator:{
		validator: function(value, param){ 
			if(value==""){return true};
			if(value.length<=param[0]){
				 var re = new RegExp(/^[a-zA-Z0-9\u4E00-\u9FA5\n]*$/);
				 return  re.test(value);
			}else{
				return false;
			}
		},
		message:"长度应小于{0}位，</br>不能输入特殊字符"
	},
	pwdUser : { 
    	 validator : function(value,param) { //参数value为当前文本框的值，也就是startDate
    	 	if($.f("div","userSettingWindow").find("input[name=userPwd]").val()){
	    		if(value==$.f("div","userSettingWindow").find("input[name=userPwd]").val()){
	    			return true;
	    		}else{
	    			return false;
	    		}
    	 	}
         },  
         message : '两次密码不一致'  
    	
    },
	/**
	 * 可以输入,，.。;；:：、()（）汉字字母数字空格
	 */
	singleTextValidator:{
		validator: function(value, param){
			if(value==""){return true};
			if(value.length<=param[0]){
				 var re = new RegExp(/^[a-zA-Z0-9\u4E00-\u9FA5\s,，.。;；:：、()（）]*$/);
				 return  re.test(value);
			}else{
				return false;
			}
		},
		message:"长度应小于{0}位，</br>不能输入特殊字符"
	},
	/**
	 * 可以输入汉字字母数字空格
	 */
	singleSaveTextValidator:{
		validator: function(value, param){ 
			if(value==""){return true};
			if(value.length<=param[0]){
				 var re = new RegExp(/^[a-zA-Z0-9\u4E00-\u9FA5\s]*$/);
				 return  re.test(value);
			}else{
				return false;
			}
		},
		message:"此项只能填写</br>不超过{0}个，</br>不能输入特殊字符"
	},
	/**
	 * 验证姓名
	 */
	nameValidator:{
		validator: function(value, param){ 
			if(value==""){return true};
			if(value.length<=param[0]){
				 var re = new RegExp(/^[a-zA-Z\u4E00-\u9FA5]*$/);
				 return  re.test(value);
			}else{
				return false;
			}
		},
		message:"输入的长度应小于{0}位"
	},
	/**
	 * 校验多行文本的
	 */
	customValidator:{
		validator: function(value, param){ 
			if(value==""){return true};
			var forbidChar = new Array( "\\","<",">");
			if(value.length>param[0]){
				return false;
			}else{
				for (var i = 0; i < forbidChar.length; i++) {
					if (value.indexOf(forbidChar[i]) >= 0) {
						return false;
					}
				}
			}
			return true;
		},
		message:"此项只能填写</br>不超过{0}个，</br>不能输入特殊字符"
	},
	/**
	 * 校验问题描述
	 */
	validatorProblemDescription:{
		validator: function(value, param){ 
			if(value==""){return true};
			var forbidChar = new Array( "\\","<",">");
			for (var i = 0; i < forbidChar.length; i++) {
				if (value.indexOf(forbidChar[i]) >= 0) {
					return false;
				}
			}
			return true;
		},
		message:"不能输入特殊字符"
	},
	/**
	 * 输入 多少位以内和多少位之间的 汉字
	 * @param {Object} value
	 * @param {Object} param
	 * @return {TypeName} 
	 */
	minormaxChinaValidator:{
		validator: function(value, param){ 
			if(value==""){return true};
			if($.trim(value).length>=param[0]&&$.trim(value).length<=param[1]){
					 var re = new RegExp(/^[\u4E00-\u9FA5]*$/);
					 return  re.test(value);
			}else{
				return false;
			}
		},
		message:"此项只能填写{0}位到{1}位的汉字"
	},
	/**
	 * 输入 多少位以上的密码
	 * @param {Object} value
	 * @param {Object} param
	 * @return {TypeName} 
	 */
	ispassword:{
		validator: function(value, param){ 
			if(value==""){return true};
			if($.trim(value).length>=param[0]){
				var re = new RegExp("^(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9]*$");
					 return  re.test(value);
			}else{
				return false;
			}
		},
		message:"请输入{0}位</br>及{0}位以上，以字母开头，</br>数字和字母组合的密码"
	},
	/**
	 * 用途：检查输入字符串是否只由英文字母和数字和下划线和汉字组成，且必须以字母或汉字开头 
	 * 输入： s：字符串 返回： 如果通过验证返回true,否则返回false
	 * add by wanglw 
	 * 2010-09-08
	 */ 
    isNumberOr_OrLetterFirst: {
        validator: function(value, param){    
            var re = new RegExp(/^[a-zA-Z][a-zA-Z0-9_]*$/);
					if (re.test(value)) {
						return true;
					} else {
						return false;
					}
        },    
     	message: '只能输入英文字母、数字、下划线_，</br>且以字母开头 例如：aads_73736jehwury_'   
    },
	
	ispasswordten:{
		validator:function(value){    
			var re = new RegExp("^(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9]{10,}$");
				if (re.test(value)) {
					return true;
				} else {
					return false;
				}
		},
		message: "请输入10位以上，</br>以字母开头，数字和字母组合的密码"
	},
	/**
	 * 特殊字符校验的正则表达式---专供煤炭产品定义使用，如果别的名称允许输入“/”
	 * 那么也可以使用这个函数
	 * @param {} value
	 * @return {Boolean}
	 */
	isSpacailCharForSpecialName:{
		validator: function(value){    
			if (value == "")
				return true;
			var forbidChar = new Array(",","，"," ", "\\", "'","￥","‘","’", "@","!","！","%", "\"", "?", "&", "#",
						"^", "》","《","<",">","*","[","]","(",")","（","）","{","}","、","。",".",
						"`","~", "+","=", "$","；",";",":","：","|");
			for (var i = 0; i < forbidChar.length; i++) {
				if (value.indexOf(forbidChar[i]) >= 0) {
					return false;
				}
			}
			return true;
        },   
		message: '输入的内容含有特殊字符'  
	},
	/**
	 * 控制不能输入'"','\''字符，因为拼接树的时候会出现解析错误
	 * @param {} value
	 * @return {Boolean}
	 */
	isQuotationMarks:{
		validator: function(value){    
			if (value == "")
				return true;
			var forbidChar = new Array( "\"", "'");
			for (var i = 0; i < forbidChar.length; i++) {
				if (value.indexOf(forbidChar[i]) >= 0) {
					return false;
				}
			}
			return true;
		},
		message: '输入的内容含有特殊字符英文单引号()或者英文双引号(\")' 
	},
	isSpacailChar:{
		validator:function(value){    
			if (value == "")
			return true;
			var regu = "^`/!@#$%^&amp; ',.*():{}[] <>";
			var re = new RegExp(regu);
			return re.test(value);
		},
		message: "输入的内容含有特殊字符"
	},
	/**
	* 该单元格不能为空或者内容全是空格
	*/
	isNull:{
		validator:function(value){    
			if (value == "")
				return true;
			var regu = "^[ ]+$";
			var re = new RegExp(regu);
			return re.test(value);
		},
		message: "该单元格不能为空或者内容全是空格"
	},
	
	/**
	* 该单元格不能为空或者内容全是空格
	*/
	isNulltwo:{
		validator:function(value){  
			if (value == "")
			return true;
			var regu = "^[ ]+$";
			var re = new RegExp(regu);
			if (re.test(value)) {
				return false;
			} else {
				return true;
			}
		
		},
		message: "该单元格不能为空或者内容全是空格"
	},
	isInteger:{
		validator:function(value){  
			if(value==""){
			return true;
			}
			var regu = '/^[-]{0,1}[0-9]{1,}$/';
			return regu.test(value);
		
		},
		message: "请输入整数\n 例如：-1,2,59"
	},
	isDouble:{
		validator:function(value){  
			alert("ssss")
			if(value==""){
			return true;
			}
			var re = new RegExp(/^-?\d*\.?\d*$/); 
			if(re.test(value)){
				return true;
			}else{
				return false;
			}
		
		},
		message: "请输入整数或小数\n 例如: 1,2,2.25,0.78"
	},
	isDoublefour:{
		validator:function(value){  
			if(value==""){
			return true;
			}
			var re = new RegExp(/^[0-9]+([.]{1}[0-9]{1,4})?$/);
			if(re.test(value)){
				return true;
			}else{
			return false;
			}
		
		},
		message: "只能输入最多含4位小数的数字"
	},
	isDoubletentwo:{
		validator:function(value){  
			if(value==""){
			return true;
			}
			var re = new RegExp(/^[0-9]+([.]{1}[0-9]{1,12})?$/);
			if(re.test(value)){
				return true;
			}else{
				return false;
			}
		
		},
		message: "只能输入最多含12位小数的数字"
	},
	isDoubletwo:{
		validator:function(value){  
			if(value==""){
				return true;
			}
			var re = new RegExp(/^[0-9]+([.]{1}[0-9]{1,2})?$/);
			if(re.test(value)){
				return true;
			}else{
			return false;
			}
		
		},
		message: "只能输入最多含2位小数的数字"
	},
	isDecimal:{
		validator:function(value){  
			if(value==""){
				return true;
			}
			if (isInteger(value))
				return true;
			var re = '/^[-]{0,1}(\d+)[\.]+(\d+)$/';
			if (re.test(value)) {
				if (RegExp.$1 == 0 && RegExp.$2 == 0)
					return false;
				return true;
			} else {
				return false;
			}
		
		},
		message: "请输入小数 \n 例如：-1.344,5.9939"
	},
	isDecimalNotNavigate:{
		validator:function(value){  
			if(value==""){
				return true;
			}
			var regu = '^\\d+(\\.\\d+)?$';
	//		var regu = '^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$';
			var re = new RegExp(regu);
			return re.test(value);
		
		},
		message: "请输入非负数 \n 例如：1.344,5.9939"
	},
	isPort:{
		validator:function(value){  
			if(value==""){
				return true;
			}
				return (value>0 && value < 65536);
		
		},
		message: "端口号只能是大于0小于65536的正整数"
	},
	isEmail:{
		validator:function(value){
			if(value==""){
			return true;
			}
			var myReg = /^[-_A-Za-z0-9]{6,18}@([_A-Za-z0-9]{2,8}\.)+[A-Za-z0-9]{2,3}$/;
			if (myReg.test(value))
				return true;
			return false;
		
		},
		message: "请输入正确的邮件格式\n 例如：ningJing@163.com"
	},
	isMoney:{
		validator:function(value){  
			if(value==""){
			return true;
			}
			var regu = "^[0-9]+[\.][0-9]{0,3}$";
			var re = new RegExp(regu);
			if (re.test(value)) {
				return true;
			} else {
				return false;
			}
		
		},
		message: "只能输入带三位的小数\n 例如：223.777"
	},
	isOracle_KeyWords:{
		validator:function(value){  
			var keywords = "ACCESS ADD ALL ALTER AND ANY AS ASC AUDIT BETWEEN BY CHAR"+
					"CHECK CLUSTER COLUMN COMMENT COMPRESS CONNECT CREATE CURRENT"+
					"DATE DECIMAL DEFAULT DELETE DESC DISTINCT DROP ELSE EXCLUSIVE"+
					"EXISTS FILE FLOAT FOR FROM GRANT GROUP HAVING IDENTIFIED"+
					"IMMEDIATE IN INCREMENT INDEX INITIAL INSERT INTEGER INTERSECT"+
					"INTO IS LEVEL LIKE LOCK LONG MAXEXTENTS MINUS MLSLABEL MODE"+
					"MODIFY NOAUDIT NOCOMPRESS NOT NOWAIT NULL NUMBER OF OFFLINE ON"+
					"ONLINE OPTION OR ORDER PCTFREE PRIOR PRIVILEGES PUBLIC RAW"+
					"RENAME RESOURCE REVOKE ROW ROWID ROWNUM ROWS SELECT SESSION"+
					"SET SHARE SIZE SMALLINT START SUCCESSFUL SYNONYM SYSDATE TABLE"+
					"THEN TO TRIGGER UID UNION UNIQUE UPDATE USER VALIDATE VALUES"+
					"VARCHAR VARCHAR2 VIEW WHENEVER WHERE WITH";
			var ts = keywords.split(" ");
			for(var i=0;i<ts.length;i++ ){
					if(ts[i]==value.toUpperCase()){
						return  false;//已存在该字段
					}else{
					return true;
					}		
				}
			
		},
		message: "该字段一再列表中存在，请换个名再试"
	},
	
	isNumberOr_Letter:{
		validator:function(value){  
			if(value==""){
			return true;
			}
			var regu = "^[0-9a-zA-Z\_]+$";
			var re = new RegExp(regu);
			if (re.test(value)) {
				return true;
			} else {
				return false;
			}
		
		},
		message: "只能输入英文字母、数字和下划线\n 例如：1213aads_73736jehwury"
	},
	isNumberOr_Letters:{
		validator:function(value){  
			if(value != ""){
			var regu = "^[0-9a-zA-Z\_\-]+$";
			var re = new RegExp(regu);
			if (re.test(value)) {
				return true;
			} else {
				return false;
			}
			}else{
					return true;
			}
		
		},
		message: "只能输入英文字母、数字,横线和下划线\n 例如：1213aads_737-36jehwury"
	},
	isNumberOr_Lettertwo:{
		validator:function(value){  
			if(value==""){
			return true;
			}
			var regu = "^[0-9a-zA-Z\_\-]+$";
			var re = new RegExp(regu);
			if (re.test(value)) {
				return true;
			} else {
				return false;
			}
		
		},
		message: "只能输入英文字母、数字,横线和下划线\n 例如：1213aads_737-36jehwury"
	},
	isNumberOrLetter:{
		validator:function(value){  
			if(value==""){
				return true;
			}
			var regu = "^[0-9a-zA-Z]+$";
			var re = new RegExp(regu);
			if (re.test(value)) {
				return true;
			} else {
				return false;
			}
		
		},
		message: "只能输入英文字母和数字 \n 例如：1213aads"
	},
	isNumberOrLetterP:{
		validator:function(value){  
			if(value==""){
				return true;
			}
			var regu = "^[//0-9a-zA-Z]+$";
			var re = new RegExp(regu);
			if (re.test(value)) {
				return true;
			} else {
				return false;
			}
		},
		message: "只能输入英文字母和数字及“/” \n 例如：1213/aads"
	},
	isChinaOrNumbOrLett:{
		validator:function(value){  
			if(value==""){
				return true;
			}
			var regu = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";
			var re = new RegExp(regu);
			if (re.test(value)) {
				return true;
			} else {
				return false;
			}
		
		
		},
		message: "只能输入汉字、字母和数字"
	},
	isChinaOrNumbOrLetttwo:{
		validator:function(value){  
			if(value==""){
				return true;
			}
			var regu = "^[0-9a-zA-Z\u4e00-\u9fa5\/\_]+$";
			var re = new RegExp(regu);
			if (re.test(value)) {
				return true;
			} else {
				return false;
			}
		
		},
		message: "只能输入汉字、字母、数字、/和_"
	},
	isChina:{
		validator:function(value){  
		
		 var regu = "^[\u4E00-\u9FA5]{0,50}$";
		 var re = new RegExp(regu);
		 if(re.test(value)){
			return true;
		 }else{
			return false;
		 }
		
		},
		message: "只能输入汉字并且长度应该小于50"
	},
	isChinaTwentyfive:{
		validator:function(value){  
			 var regu = "^[\u4E00-\u9FA5]{0,25}$";
			 var re = new RegExp(regu);
			 if(re.test(value)){
				return true;
			 }else{
				return false;
			 }
		},
		message: "只能输入汉字并且长度应该小于50"
	},
	isChinaSixteen:{
		validator:function(value){  
			 var regu = "^[\u4E00-\u9FA5]{0,16}$";
			 var re = new RegExp(regu);
			 if(re.test(value)){
				return true;
			 }else{
				return false;
			 }
		},
		message: "只能输入汉字并且长度应该小于16"
	},
	isChinaOrNumb_OrLetttwo:{
		validator:function(value){  
			if(value==""){
				return true;
			}
			var regu = "^[0-9a-zA-Z\u4e00-\u9fa5\_\/\*\-]+$";
			var re = new RegExp(regu);
			if (re.test(value)) {
				return true;
			} else {
				return false;
			}
		},
		message: "只能输入汉字、字母、数字、下划线和横线\n 例如：你好_737-ehwury"
	},
	isChinaOrNumb_OrLettthree:{
		validator:function(value){  
			if(value==""){
				return true;
			}
			var regu = "^[0-9a-zA-Z\u4e00-\u9fa5\-\/\——]+$";
			var re = new RegExp(regu);
			if (re.test(value)) {
				return true;
			} else {
				return false;
			}
		},
		message: "只能输入汉字、字母、数字和中英文横线\n 例如：你好737-ehwury"
	},
	isChinaOrNumb_OrStarLetttwo:{
		validator:function(value){  
			if(value==""){
			return true;
			}
			var regu = "^[\\0-9a-zA-Z\u4e00-\u9fa5\_\(-)*\-]+$";
			var re = new RegExp(regu);
			if (re.test(value)) {
				return true;
			} else {
				return false;
			}
		},
		message: "只能输入汉字、字母、数字、下划线、横线、斜线、反斜线、星号\n 例如：你好_737-eh/wu\ry*js"
	},
	isChinaOrNumb_OrLettOrLinetwo:{
		validator:function(value){  
			if(value==""){
				return true;
			}
			var regu = "^[\\0-9a-zA-Z\u4e00-\u9fa5\_\-]+$";
			var re = new RegExp(regu);
			if (re.test(value)) {
				return true;
			} else {
				return false;
			}
		},
		message: "只能输入汉字、字母、数字、'%'、'_'、'-'、'/'"
	},
	isChinaOrNumb_OrLettOrLinetwoOrBracket:{
		validator:function(value){  
			if(value==""){
			return true;
			}
			var regu = "^[\(\)\\0-9a-zA-Z\u4e00-\u9fa5\_\-]+$";
			var re = new RegExp(regu);
			if (re.test(value)) {
				return true;
			} else {
				return false;
			}
		},
		message: "只能输入汉字、字母、数字、'()'、'_'、'-'、'/'"
	},
	
	age:{
		validator:function(value){  
			if(value==""){
				return true;
			}
			if ('/^\d+$/'.test(value)) {
				var _age = parseInt(value);
				if (_age < 200)
					return true;
			} else
				return false;
		},
		message: "只能输入汉字、字母、数字、'%'、'_'、'-'、'/'"
	},
	postcode:{
		validator:function(value){  
			if(value==""){
			return true;
			}
			return (/^[1-9]\d{5}$/.test(value));
		},
		message: "该输入项目必须是邮政编码格式，例如：226001"
	},
	ip:{
		validator:function(value){  
			if(value==""){
			return true;
			}
			return /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
				.test(value);
		},
		message: "该输入项目必须是IP地址格式，例如：222.192.42.12"
	},
	fax:{
		
		validator:function(value){  
			if(value==""){
				return true;
			}
			return (/(^\d{3}\-\d{7,8}$)|(^\d{4}\-\d{7,8}$)|(^\d{3}\d{7,8}$)|(^\d{4}\d{7,8}$)|(^\d{7,8}$)/
					.test(value));
		},
		message: "该输入值必须是传真格式，例如：0513-89500414,051389500414,89500414"
	},
	telephone:{
		
		validator:function(value){  
			if(value==""){
			return true;
			}
			return (/(^\d{3}\-\d{7,8}$)|(^\d{4}\-\d{7,8}$)|(^\d{3}\d{7,8}$)|(^\d{4}\d{7,8}$)|(^\d{7,8}$)/
				.test(value));
		},
		message: "该输入项目必须是电话号码格式，例如：0513-89500414,051389500414,89500414"
	},
	mobile:{
		 validator: function (value) {
            var reg = /^1[3|4|5|8|9]\d{9}$/;
            return reg.test(value);
        },
        message: '该输入项目必须是手机号码格式，例如：13485135075'
	},
	IDCard : {
		validator : function(value) {
			if (value == "") {
				return true;
			}
			// return /(^[0-9]{17}([0-9]|[Xx])$)|(^[0-9]{17}$)/.test(_v);
			var area = {
				11 : "北京",
				12 : "天津",
				13 : "河北",
				14 : "山西",
				15 : "内蒙古",
				21 : "辽宁",
				22 : "吉林",
				23 : "黑龙江",
				31 : "上海",
				32 : "江苏",
				33 : "浙江",
				34 : "安徽",
				35 : "福建",
				36 : "江西",
				37 : "山东",
				41 : "河南",
				42 : "湖北",
				43 : "湖南",
				44 : "广东",
				45 : "广西",
				46 : "海南",
				50 : "重庆",
				51 : "四川",
				52 : "贵州",
				53 : "云南",
				54 : "西藏",
				61 : "陕西",
				62 : "甘肃",
				63 : "青海",
				64 : "宁夏",
				65 : "新疆",
				71 : "台湾",
				81 : "香港",
				82 : "澳门",
				91 : "国外"
			}
			var Y, JYM;
			var S, M;
			var idcard_array = new Array();
			idcard_array = value.split("");
			// 地区检验
			if (area[parseInt(value.substr(0, 2))] == null) {
				this.IDCardText = "身份证号码地区非法!!,格式例如:32";
				return false;
			}
			// 身份号码位数及格式检验
			switch (value.length) {
				case 15 :
					if ((parseInt(value.substr(6, 2)) + 1900) % 4 == 0
							|| ((parseInt(value.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(value
									.substr(6, 2)) + 1900)
									% 4 == 0)) {
						ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;// 测试出生日期的合法性
					} else {
						ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;// 测试出生日期的合法性
					}
					if (ereg.test(value))
						return true;
					else {
						this.IDCardText = "身份证号码出生日期超出范围,格式例如:19860817";
						return false;
					}
					break;
				case 18 :
					// 18位身份号码检测
					// 出生日期的合法性检查
					// 闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
					// 平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
					if (parseInt(value.substr(6, 4)) % 4 == 0
							|| (parseInt(value.substr(6, 4)) % 100 == 0 && parseInt(value
									.substr(6, 4))
									% 4 == 0)) {
						ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;// 闰年出生日期的合法性正则表达式
					} else {
						ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;// 平年出生日期的合法性正则表达式
					}
					if (ereg.test(value)) {// 测试出生日期的合法性
						// 计算校验位
						S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10]))
								* 7
								+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11]))
								* 9
								+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12]))
								* 10
								+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13]))
								* 5
								+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14]))
								* 8
								+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15]))
								* 4
								+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16]))
								* 2
								+ parseInt(idcard_array[7])
								* 1
								+ parseInt(idcard_array[8])
								* 6
								+ parseInt(idcard_array[9]) * 3;
						Y = S % 11;
						M = "F";
						JYM = "10X98765432";
						M = JYM.substr(Y, 1);// 判断校验位
						// alert(idcard_array[17]);
						if (M == idcard_array[17]) {
							return true; // 检测ID的校验位
						} else {
							this.IDCardText = "身份证号码末位校验位校验出错,请注意x的大小写,格式例如:201X";
							return false;
						}
					} else {
						this.IDCardText = "身份证号码出生日期超出范围,格式例如:19860817";
						return false;
					}
					break;
				default :
					this.IDCardText = "身份证号码位数不对,应该为15位或是18位";
					return false;
					break;
			}
		},
		message : "该输入项目必须是身份证号码格式，例如：32082919860817201x"
	},
	isNumberOrLetterAndSpace:{
		
		validator:function(value){  
				if(value==""){
					return true;
				}
				var regu = "^[0-9a-zA-Z][0-9a-zA-Z ]*$";
				var re = new RegExp(regu);
				if (re.test(value)) {
					return true;
				} else {
					return false;
				}
		},
		message: "只能输入英文字母和数字和空格 \n 例如：12 as 13 ds   "
	},
	isNumber:{
		validator:function(value){  
			if(value==""){
				return true;
			}
			var regu = /^[0-9]*$/;
			var re = new RegExp(regu);
			if (re.test(value)) {
				return true;
			} else {
				return false;
			}
		},
		message: "只能输入数字!"
	},
	isQuotationChineseMarks:{
		
		validator:function(value){  
			var re = new RegExp(/[\u4e00-\u9fa5]|\'|\"/);
			if (re.test(value)) {
				return false;
			} else {
				return true;
			}
		},
		message: "输入的内容含有特殊字符英文单引号(')或者英文双引号(\")或者中文汉字"
	},
	isLessThan:{
		
		validator:function(value){  
			var re = new RegExp(/<|^[ ]+$/);
			if (re.test(value)) {
				return false;
			} else {
				return true;
			}
		},
		message: "输入的内容含有特殊字符英文小于号(<)或者全部为空格"
	},
	isUrlText:{
		
		validator:function(value){  
			var re=new RegExp(/^https?:\/\/(([a-zA-Z0-9_-])+(\.)?)*(:\d+)?(\/((\.)?(\?)?=?&?[a-zA-Z0-9_-](\?)?)*)*$/);  
			return re.test(value);   
		},
		message: "该输入项目必须是url地址格式,例如http://localhost:8002"
	},
	isService:{
		
		validator:function(value){  
			if(value==""){
				return false;
			}
			var re=new RegExp(/^\/(([a-zA-Z0-9_-])+(\.)?)*(:\d+)?(\/((\.)?(\?|\!)?=?&?[a-zA-Z0-9_-](\?|\!)?)*)*$/);  
			return re.test(value);     
		},
		message: "该输入项目必须是服务地址格式,例如/systemcode/system-code!getCode.action"
	},
	isSafe_KeyWords:{
		
		validator:function(value){  
			
		var flag = true;
		
		//oracle sql关键字
		var oracleSqlkeywords = "ACCESS ADD ALL ALTER AND ANY AS ASC AUDIT BETWEEN BY CHAR"+
					"CHECK CLUSTER COLUMN COMMENT COMPRESS CONNECT CREATE CURRENT"+
					"DATE DECIMAL DEFAULT DELETE DESC DISTINCT DROP ELSE EXCLUSIVE"+
					"EXISTS FILE FLOAT FOR FROM GRANT GROUP HAVING IDENTIFIED"+
					"IMMEDIATE IN INCREMENT INDEX INITIAL INSERT INTEGER INTERSECT"+
					"INTO IS LEVEL LIKE LOCK LONG MAXEXTENTS MINUS MLSLABEL MODE"+
					"MODIFY NOAUDIT NOCOMPRESS NOT NOWAIT NULL NUMBER OF OFFLINE ON"+
					"ONLINE OPTION OR ORDER PCTFREE PRIOR PRIVILEGES PUBLIC RAW"+
					"RENAME RESOURCE REVOKE ROW ROWID ROWNUM ROWS SELECT SESSION"+
					"SET SHARE SIZE SMALLINT START SUCCESSFUL SYNONYM SYSDATE TABLE"+
					"THEN TO TRIGGER UID UNION UNIQUE UPDATE USER VALIDATE VALUES"+
					"VARCHAR VARCHAR2 VIEW WHENEVER WHERE WITH";
					
		//防止声明变量,HEX编码式的sql注入
		var otherkeywords = "DECLARE HEX";
		
		//js敏感关键字
		var jsKeywords ="BREAK DELETE FUNCTION RETURN TYPEOF CASE DO IF SWITCH VAR"+
						"CATCH ELSE IN THIS VOID CONTINUE FALSE INSTANCEOF THROW WHILE DEBUGGER FINALLY NEW TRUE WITH DEFAULT FOR NULL TRY "+
						"ABSTRACT DOUBLE GOTO NATIVE STATIC BOOLEAN ENUM IMPLEMENTS PACKAGE SUPER BYTE EXPORT IMPORT PRIVATE SYNCHRONIZED"+
						"CHAR EXTENDS INT PROTECTED THROWS CLASS FINAL INTERFACE PUBLIC TRANSIENT CONST FLOAT LONG SHORT VOLATILE";
						
		//html敏感关键字
		var htmlKeywords ="EVAL IFRAME SCRIPT META LOCATION DIV INNERHTML OUTERHTML DOCUMENT MARQUEE FUNCTION ALERT";
						
		//禁用字符
		var forbidChar = new Array(",", "\\", "'","‘","’","!","！","%", "\"","&", "#",
									"^","<",">","*","[","]","(",")","{","}",".",
									"`","~", "+","=", "$","；",";",":","|","-");
		
		var keywords = oracleSqlkeywords + " " + jsKeywords+" "+ htmlKeywords+" "+otherkeywords;
		var filterArray = keywords.split(" ");
		
		var value = value.toUpperCase();
		//关键字过滤循环
		for (i=0;i<filterArray.length;i++) {
			 if (value.indexOf(filterArray[i])!=-1){
			  flag = false;
			  }
		}
		//敏感字符过滤循环
		for (i=0;i<forbidChar.length;i++) {
			 if (value.indexOf(forbidChar[i])!=-1){
			  flag = false;
			  }
		}
		
		return flag;
		},
		message: "输入的内容含有不安全字符片段，请勿输入js语言，html语言标记，以及sql关键字"
	},
	
	
	
	minDate:{
		  validator : function(value,param) { //参数value为当前文本框的值，也就是endDate
			  var   curDate=new   Date();
			  str = value.replace(/-/g,"/");
			  var date = new Date(str );
			  return  date>curDate
		  },
		  message : '失效日期必须大于今天'  
		
	},
	/**
	 * 结束日期校验
	 * @type 
	 */
    endDate : {  
        validator : function(value,param) { //参数value为当前文本框的值，也就是endDate
        	var startTime;
        	if(param==1){//1表示可编辑列表
        		startTime = $(this).parents("tr").eq(1).find("td[field='startDate']").find("input").eq(0).datetimebox('getValue');
        	}else {
        		startTime = $(param[0]).datetimebox('getValue');
        	}
            if(startTime!=""){
            	if(value.length<7){
            		return;
            	}
	            var start = $.fn.datebox.defaults.parser(startTime);  
	            var end = $.fn.datebox.defaults.parser(value); 
	           	return end>start;
            }
            return true;
        },  
        message : '结束时间要大于开始时间!'  
    },
    /**
	 * 开始日期校验
	 * @type 
	 */
    startDate : {  
        validator : function(value,param) { //参数value为当前文本框的值，也就是startDate
        	var endTime;
        	if(param==1){
        		endTime = $(this).parents("tr").eq(1).find("td[field='endDate']").find("input").eq(0).datetimebox('getValue');
        	}else{
        		endTime = $(param[0]).datetimebox('getValue');
        	}
            if(endTime!=""){
            	if(value.length<7){
            		return;
            	}
	            var end = $.fn.datebox.defaults.parser(endTime);  
	            var start = $.fn.datebox.defaults.parser(value); 
           		return end>start;	
            }
            return true;
        },  
        message : '开始时间要小于结束时间!'  
    },
    /**
     * 巡视系统专用
     */
    pwdxiangshi : { 
    	 validator : function(value,param) { //参数value为当前文本框的值，也就是startDate
    		if(value==$.f("div","userSettingWindow").find("input[name=userPwd]").val()){
    			return true;
    		}else{
    			return false;
    		}
         },  
         message : '两次密码不一致'  
    	
    },
    updatepwd : { 
   	 validator : function(value,param) { //参数value为当前文本框的值，也就是startDate
   		if(value==$.f("div","updatepwdWin").find("input[name=userPwd]").val()){
   			return true;
   		}else{
   			return false;
   		}
        },  
        message : '两次密码不一致'  
   	
   },
   oldpwd : { 
  	 validator : function(value,param) { //参数value为当前文本框的值，也就是startDate
  		var newpwd=$.md5(value)
  		if(localStorage.sessionpwd==newpwd){
  			return true;
  		}else{
  			return false;
  		}
       },  
       message : '原密码输入错误'  
  	
  }
    
  
    
});
