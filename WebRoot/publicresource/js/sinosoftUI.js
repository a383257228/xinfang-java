
$.showmodel=function(){
	$("#modeldivs").remove();
	$("body").append("<div id='modeldivs' style='width: 100%;height: 100%;position: absolute;left: 0;top: 0;z-index: 99999999999999;text-align: center;vertical-align: middle;'><img style='vertical-align:middle;margin-top: 25%;width:50px' src='/jubao/publicresourceEasyUi/css/images/loading-1.gif'></div>")
}

$.closemodel=function(){
	$("#modeldivs").remove();
}

/**
 * 获取当前的时间
 * @param {} fmt 时间的格式
 * @return {}
 */
function currentTime(fmt){
	Date.prototype.Format = function (fmt) { //author: meizz 
		    var o = {
		        "M+": this.getMonth() + 1, //月份 
		        "d+": this.getDate(), //日 
		        "H+": this.getHours(), //小时 
		        "m+": this.getMinutes(), //分 
		        "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时 
		        "s+": this.getSeconds(), //秒 
		        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		        "S": this.getMilliseconds() //毫秒 
		    };
		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		    for (var k in o)
		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		    return fmt;
		}
		var time = new Date().Format(fmt);
		return time;
	 
}
/**
 * 获取上月今日当前的时间
 * @param {} fmt 时间的格式
 * @return {}
 */
function prevMonthTodayCurrentTime(fmt){
	Date.prototype.Format = function (fmt) { //author: meizz 
		    var o = {
		        "M+": this.getMonth() , //月份 
		        "d+": this.getDate(), //日 
		        "H+": this.getHours(), //小时 
		        "m+": this.getMinutes(), //分 
		        "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时 
		        "s+": this.getSeconds(), //秒 
		        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		        "S": this.getMilliseconds() //毫秒 
		    };
		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		    for (var k in o)
		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		    return fmt;
		}
		var time = new Date().Format(fmt);
		return time;
}
/**
 * 合并列表相同列项
 * @param {} tableFlag 列表对象 colList 列表项组
 * @return {}
 */
function mergeCellsByField(gridFlag,colList){
	         var ColArray = colList.split(";");
	         var tTable = gridFlag;
	         var TableRowCnts = tTable.datagrid("getRows").length;  
	         var tmpA;  
	         var PerTxt = "";  
	         for (j=0;j<=ColArray.length-1 ;j++){
	             PerTxt="";  
	             tmpA=1;  
	             for (i=0;i<=TableRowCnts ;i++ ){
	            	 var tempStr="";
	                 if (i==TableRowCnts){  
	                	 tempStr="";  
	                 }else{  
	                	 for(var tempi=0;tempi<=j;tempi++){
	                         tempStr = tempStr+tTable.datagrid("getRows")[i][ColArray[tempi]];
	                     }          
	                 }  
	                 if (PerTxt==tempStr){  
	                     tmpA+=1;  
	                 }else{  
	                     tTable.datagrid('mergeCells',{  
	                         index:i-tmpA,  
	                         field:ColArray[j],  
	                         rowspan:tmpA,  
	                         colspan:null  
	                     });  
	                     tmpA=1;  
	                 }  
	                 PerTxt=tempStr;  
	             }  
	         }  
	     }  

$.fn.tree.methods.getDept=function(jq,obj){
	var c=$(obj).parents("ul");
	var i=0;
	for(var ss=0;ss<c.length;ss++){
			i++;
		var classString =c.eq(ss).attr("class")
		if(typeof classString=="undefined"){
			classString="";
		}
		 if(classString.indexOf("tree")>-1){
		 		break;
		 }
	}
	return i
}
$.fn.combotree.methods.getDept=function(jq,obj){
	var c=$(obj).parents("ul");
	var i=0;
	for(var ss=0;ss<c.length;ss++){
			i++;
		var classString =c.eq(ss).attr("class")
		if(typeof classString=="undefined"){
			classString="";
		}
		 if(classString.indexOf("tree")>-1){
		 		break;
		 }
	}
	return i
}
$.fn.setdisabled=function(bool){
	if(bool){
		$(this).addClass("gray");
	}else{
		$(this).removeClass("gray");
	}
}

/*$.fn.setdisabledEvent=function(bool,clickName){
	if(bool){
		$(this).addClass("gray");
		var click=$(this).data("click");
		if(clickName){
			$(this).data("click",clickName);
			$(this).data("click",clickName)
		}else{
			$(this).data("click",click);
		}
		$(this).unbind("click");
	}else{
		$(this).unbind("click")
		$(this).removeClass("gray");
		var click=$(this).data("click");
		if(clickName){
			$(this).click(clickName)
			$(this).data("click",clickName)
		}else{
			$(this).click(click);
		}
	}
}*/
/**
 * 禁止使用按钮的点击事件（只有点击事件）
 * bool   取值 true(禁用) false（启用）
 * clickName 执行时是事件方法
 * 例如 ： 要禁止使用 id 为 button的按钮    $("#button").setdisabledEvent(true,function(){alert("xxxx")})
 *         启用 id 为 button的按钮    $("#button").setdisabledEvent(false)
 *         如果启用的按钮的时候给加上了 第二个参数 就会把禁用的时候传的参数给覆盖掉
 * 此方法会霸占Click事件，其他地方加的Click事件一律无用
 * 
 */
$.fn.setdisabledEvent=function(bool,clickName){
	var c=$(this).data("click")
	if(bool){
		$(this).addClass("gray");
		if(c=="null"||$.type(c)=="undefined"){
			$(this).data("click",$(this).attr("onclick"));
		}
		$(this).attr("onclick","null");
	}else{
		$(this).removeClass("gray");
		$(this).attr("onclick",c);
	} 
}
$.fn.isdisabled=function(){
	var bool=true;
	if(!$(this).hasClass("gray")){
		bool=false;
	}
	return bool;
}
/**
 * 命名空间  模块化编程开始
 * 此方法会在前段创建一个对象或者多个对象
 * 例如  $.sinosoftUI('com') 会创建一个名为 com 的对象
 * 		 $.sinosoftUI('com.portal') 会创建一个名为 com 的对象并且会在com里面创建一个portal的对象
 *       使用方法和ExtJS的   Ext.ns 一样
 * @param {Object} className   创建的对象名
 */
$.sinosoftUI=$.SUI=function(className){
	var arr=className.split(".")
	var temp=null;
	for(var i=0;i<arr.length;i++){
		if(i==0){
			if(!window[arr[i]]){
				window[arr[i]]={};
			}
			temp=window[arr[i]];
		}else{
			if(!temp[arr[i]]){
				temp[arr[i]]={};
			}
			temp=temp[arr[i]];
		}
	}
}
/**
 * 控制台输出  不兼容ie9
 * 没什么可以看的  用$.log('输出类容') 调用
 * @param {Object} str  输出的内容
 */
$.log=function(str){
	console.log(str)
}
/**
 * 在当前页面寻找JQuery对象 
 * name值可以重复 找到的是一个对象数组
 * 注意在保证找到的内容只有1个对象的情况下可以直接使用
 * 如果有多个对象的话 可以和 $.findName("div","demo").findObjByIndex(0) 联合使用
 * @param {Object} tag 标签类型  div  span  table 之类的
 * @param {Object} name  name值
 * @return {TypeName} 
 */
$.findName=function(tag,name,attr){
	if(typeof attr =="undefined"){
		attr="name";
	}
	var tagName=tag+"["+attr+"='"+name+"']";
	return $(tagName);
};
$.f=$.findFlag=function(tag,name,attr){
	if(typeof attr =="undefined"){
		attr="idFlag";
	}
	var tagName=tag+"["+attr+"='"+name+"']";
	return $(tagName);
};
/**
 * copy一个对象  和这个被copy 完全没有了关系。类似于克隆，被克隆出来的对象是死是活和源对象没任何关系
 */
$.copyObj=$.apply=function(data){
	return $.extend({},data);
}
$.toJSON=function(data){
	try {
		if(typeof data =="string"){
			data=	$.parseJSON(data);
		}
	} catch (e) {
		try {
			data=eval("("+data+")");
		} catch (e) {
			data="你的字符串不是标准的JSON字符串";
		}
	}
	return data;
}


/**
 * 在当前页面寻找JQuery对象 
 * name值可以重复 找到的是一个对象数组
 * 注意在保证找到的内容只有1个对象的情况下可以直接使用
 * 如果有多个对象的话 可以和 $.findName("div","demo").findObjByIndex(0) 联合使用
 * @param {Object} tag 标签类型  div  span  table 之类的
 * @param {Object} name  name值
 * @return {TypeName} 
 */
$.fn.findName=function(tag,name,attr){
	if(typeof attr =="undefined"){
		attr="name";
	}
	var tagName=tag+"["+attr+"='"+name+"']";
	return this.find(tagName);
};



$.fn.getLabelText=function(groupname){
		return $(this).find("input[name='"+groupname+"']:checked").parent().next().html();
}
$.fn.setChecked=function(groupname,value){
	if(value==null||typeof value=="undefined"||$.trim(value)==""){
		$(this).setRadioBoxNull(groupname);
	}
	try{
		$(this).find("input[name='"+groupname+"'][value='"+value+"']").parent().parent().redioBox("setCheck");
		}catch(e){
	}
		
}
$.fn.getLabelVal=function(groupname){
	return $(this).find("input[name='"+groupname+"']:checked").val();
}
$.fn.setRadioBoxNull=function(groupname){
	if(typeof groupname =="undefined"){
		var radios=$(this).find("input[name='"+groupname+"'][type='radio']")
		for (var i = 0; i < radios.length; i++) {
			try {
				$(radios[i]).parent().parent().redioBox("setUnCheck");
			} catch (e) {
			
			}
		}
	}else{
		try {
		var radios=	$(this).find("input[name='"+groupname+"']").parent().parent();
			$(radios).each(function(){
				$(this).redioBox("setUnCheck");
			})
		} catch (e) {
			
		}
	}
}



$.fn.f=$.fn.findFlag=function(tag,name,attr){
	if(typeof attr =="undefined"){
		attr="idFlag";
	}
	var tagName=tag+"["+attr+"='"+name+"']";
	return this.find(tagName);
};
$.fn.findtabopt=function(){
	return  $(this).tabs("getSelected").panel("options")
};
$.fn.findtabtoPanel=function(){
	var tab=$(this).tabs("getSelected");
	var index = $(this).tabs('getTabIndex',tab);
	return $(".tabs-panels").children(".panel").eq(index);

	
};
/**
 * @param {Object} index 索引 从0 开始
 * @return {TypeName} 
 */
$.fn.findObjByIndex=function(index){
	return this.eq(index);
}



/**
*创建一个复选框对象
*/
function checkedBoxcreate(target){
	var opts = $.data(target, 'checkedBox').options;
	$(target).empty();
	if(opts.checked){
		$(target).append("<span  group=\""+opts.group+"\" style=\"cursor:pointer\"  class=\"tree-checkbox tree-checkbox1\"><input name=\""+opts.group+"\" checked=\"checked\" value=\""+opts.value+"\" type=\"checkbox\" style=\"display:none\"></input></span>")
	}else{
		$(target).append("<span   group=\""+opts.group+"\" style=\"cursor:pointer\"   class=\"tree-checkbox tree-checkbox0\"><input name=\""+opts.group+"\"  value=\""+opts.value+"\"  type=\"checkbox\"    style=\"display:none\"></input></span>")
	}
	$(target).append("<span class=\"tree-title\" style=\"cursor:default;"+opts.fontStyle+"\">"+opts.labelText+"</span>")
	
	$(target).unbind(); //移除所有
	$(target).find(".tree-checkbox").click(function(){
			if($(this).hasClass("tree-checkbox1")){//取消选中
				checkboxseleckunchecked(this,opts)
			}else{//选中
				checkboxseleckchecked(this,opts)
			}
		
	})
	$(target).width(opts.width)
	$(target).height(opts.height)
}
/**
*复选框选中方法
*obj 需要条件状态的框
*opts 状态参数 
*/
function checkboxseleckchecked(obj,opts){
	if(opts.enable){
		var	f=opts.onBeforeCheck($(obj).parent())
		if(typeof f=="undefined"){
			f=true;
		}
		if(f){
			$(obj).removeClass("tree-checkbox0")
			$(obj).addClass("tree-checkbox1")
			$(obj).find("input").attr("checked", true);
		}
		
		opts.onAfterCheck($(obj).parent())	
	}else{
		$($(obj).parent()).find(".tree-title").css("color","red")
	}
}
/**
*复选框取消选中方法
*obj 需要条件状态的框
*opts 状态参数 
*/
function  checkboxseleckunchecked(obj,opts){
	if(opts.enable){
		var f=opts.onBeforeunCheck($(obj).parent())
		if(typeof f=="undefined"){
			f=true;
		}
		if(f){
			$(obj).removeClass("tree-checkbox1")
			$(obj).addClass("tree-checkbox0")
			$(obj).find("input").attr("checked", false);
		}
		opts.onAfterunCheck($(obj).parent())
	}else{
		$($(obj).parent()).find(".tree-title").css("color","red")
	}
}
/**
*复选框
*options 初始化
*param 参数 
*/
$.fn.checkedBox=function(options,param){
	if (typeof options == 'string'){
		var method = $.fn.checkedBox.methods[options];
		if (method){
			return method(this, param);
		}else{
			console.log("没有此方法")
		}
	}
	options = options || {};
    return this.each(function () {
    	//给这个对象挂载一个checkedBox的对象，
        var state = $.data(this, 'checkedBox');
        if (state) {
            $.extend(state.options, options);
            checkedBoxcreate(this);
        }
        else {
        	$.data(this, 'checkedBox', {
					options: $.extend({}, $.fn.checkedBox.defaults, $.fn.checkedBox.parseOptions(this), options)
				});
          //  $.data(this, 'checkedBox', { options: $.extend({}, $.fn.checkedBox.defaults, options) });
            checkedBoxcreate(this);
        }
    })	
}
$.fn.checkedBox.parseOptions= function(target){
	if(typeof $(target).attr("par")=="undefined"){
		return new Object()
	}else{
		var t ="{"+$(target).attr("par")+"}"
		return eval("("+t+")");
	}	
	};

/**
*复选框的方法
*options 初始化
*param 参数 
*/
 $.fn.checkedBox.methods = {
	setCheck:function(jq){//设置选中方法
		var stat= $.data(jq[0], 'checkedBox').options;
		checkboxseleckchecked($(jq).find(".tree-checkbox"),stat)
	},
	setUnCheck:function(jq){//取消选中状态
		/*var stat= $.data(jq).options;
		checkboxseleckunchecked($(jq[0]).find(".tree-checkbox"),stat)*/
		var stat= $.data(jq[0], 'checkedBox').options;
		checkboxseleckunchecked($(jq[0]).find(".tree-checkbox"),stat)
	},
	options: function(jq){//设置获取参数
			return $.data(jq[0], 'checkedBox').options;
		},
	disableChecked:function(jq){//禁止使用
		$.data(jq[0], 'checkedBox').options.enable=false;
		$(jq[0]).find(".tree-title").css("color","red")
		
	},
	enableChecked:function(jq){//启用
		 $.data(jq[0], 'checkedBox').options.enable=true;
		 $(jq[0]).find(".tree-title").css("color","black")
	},
	isChecked:function(jq){//状态
		return $(jq[0]).find(".tree-checkbox").hasClass("tree-checkbox1");
	},
	isEnable:function(jq){//是否启用
		return  $.data(jq[0], 'checkedBox').options.enable;
	},
	setLabelText:function(jq,param){//设置label
		 $.data(jq[0], 'checkedBox').options.labelTex=param;
		 $(jq[0]).find(".tree-title").html(param);
	},
	setValue:function(jq,param){//设置设置值
		$(jq[0]).find("input").attr("value",param);
	}
 }
 /**
*复选框的默认值
*/
$.fn.checkedBox.defaults={
	width:"auto",
	height:22,
	group:"checkedstring",//分组的
	checked:false,
	value:"",//后台接收的值
	labelText:"复选框",//文本
	fontStyle:"",//字的样式
	enable:true,//是否启用
	onBeforeCheck:function(obj){//选中之前触发
		
		
	},
	onAfterCheck:function(obj){//选中之后触发
		
		
	},
	onBeforeunCheck:function(){//取消之前触发
		
	
	},
	onAfterunCheck:function(){//取消之后触发
		
		
	}
}



 /**
 *单选框
 *options 初始化
 *param 参数 
 */
 $.fn.redioBox=function(options,param){
 	if (typeof options == 'string'){
 		var method = $.fn.redioBox.methods[options];
 		if (method){
 			return method(this, param);
 		}else{
 			console.log("没有此方法")
 		}
 	}
 	options = options || {};
     return this.each(function () {
     	//给这个对象挂载一个checkedBox的对象，
         var state = $.data(this, 'redioBox');
         if (state) {
             $.extend(state.options, options);
             redioBoxcreate(this);
         }
         else {
         	$.data(this, 'redioBox', {
 					options: $.extend({}, $.fn.redioBox.defaults, $.fn.redioBox.parseOptions(this), options)
 				});
           //  $.data(this, 'checkedBox', { options: $.extend({}, $.fn.checkedBox.defaults, options) });
             redioBoxcreate(this);
         }
     })	
 }
 $.fn.redioBox.methods = {
 	setCheck:function(jq){//设置选中方法
 		var stat= $.data(jq[0], 'redioBox').options;
 		redioboxseleckchecked($(jq).find(".tree-checkbox"),stat)
 	},
 	setUnCheck:function(jq){//取消选中状态
 		var stat= $.data(jq[0],'redioBox').options;
 		redioboxseleckunchecked($(jq[0]).find(".tree-checkbox"),stat)
 	},
 	options: function(jq){//设置获取参数
 			return $.data(jq[0], 'redioBox').options;
 		},
 	disableChecked:function(jq){//禁止使用
 		$.data(jq[0], 'redioBox').options.enable=false;
 		$(jq[0]).find(".tree-title").css("color","#aaa")
 		
 	},
 	enableChecked:function(jq){//启用
 		 $.data(jq[0], 'redioBox').options.enable=true;
 		 $(jq[0]).find(".tree-title").css("color","black")
 	},
 	isChecked:function(jq){//状态
 		return $(jq[0]).find(".tree-checkbox").hasClass("rediocomchecked");
 	},
 	isEnable:function(jq){//是否启用
 		return  $.data(jq[0], 'redioBox').options.enable;
 	},
 	setLabelText:function(jq,param){//设置label
 		 $.data(jq[0], 'redioBox').options.labelTex=param;
 		 $(jq[0]).find(".tree-title").html(param);
 	},
 	setValue:function(jq,param){//设置设置值
 		$(jq[0]).find("input").attr("value",param);
 	},
 	getlabelText:function(jq,param){//获取lable
 		return  $.data(jq[0], 'redioBox').options.labelText;
 	},
 	getlabelVal:function(jq,param){//获取value
 		return $.data(jq[0], 'redioBox').options.value;
 	},
 	getCheckedLabelText:function(jq){//如果当前这个选中的话，可以返回他的lable值
 		var groupname=$(jq[0]).find("span").first().attr("group")
 		var arr=$("input[name='"+groupname+"']:checked")
 		var label="";
 		$(arr).each(function(){
 			label+=","+$(this).parent().next().html()
 		})
 		if(label.length==0){
 			return null;
 		}else{
 			return label.substring(1);
 		}
 	},
 	getCheckedLabelVal:function(jq){//如果当前这个选中的话，可以返回他的lable值
 		var groupname=$(jq[0]).find("span").first().attr("group")
 		var arr=$("input[name='"+groupname+"']:checked")
 		var label="";
 		$(arr).each(function(){
 			label+=","+$(this).val();
 		})
 		if(label.length==0){
 			return null;
 		}else{
 			return label.substring(1);
 		}
 	}
 }
 $.fn.redioBox.parseOptions= function(target){
 	if(typeof $(target).attr("par")=="undefined"){
 		return new Object()
 	}else{
 		var t ="{"+$(target).attr("par")+"}"
 		return eval("("+t+")");
 	}	
 	};
  /**
 *单选的默认值
 */
 $.fn.redioBox.defaults={
 	width:"auto",
 	height:22,
 	group:"rediostring",//分组的
 	checked:false,
 	value:"",//后台接收的值
 	labelText:"单选框",//文本
 	fontStyle:"",//字的样式
 	enable:true,//是否启用
 	labelName:"undefined",
 	canel:false,//是否可以取消选用  false=不可以取消 true=可以取消
 	onBeforeCheck:function(obj){//选中之前触发
 		
 		
 	},
 	onAfterCheck:function(obj){//选中之后触发
 		
 		
 	},
 	onBeforeunCheck:function(){//取消之前触发
 		
 	
 	},
 	onAfterunCheck:function(){//取消之后触发
 		
 		
 	}
 }

 /**
 *创建一个单选框对象
 */
 function redioBoxcreate(target){
 	
 	var opts = $.data(target, 'redioBox').options;
 	$(target).empty();
 	if(opts.checked){
 		$(target).append("<span  group=\""+opts.group+"\" style=\"cursor:pointer\"  class=\"tree-checkbox rediocomchecked\"><input name=\""+opts.group+"\" checked=\"checked\" value=\""+opts.value+"\" type=\"radio\" style=\"display:none\"     ></input></span>")
 	}else{
 		$(target).append("<span   group=\""+opts.group+"\" style=\"cursor:pointer\"   class=\"tree-checkbox rediocom\"><input name=\""+opts.group+"\"  value=\""+opts.value+"\"  type=\"radio\"  style=\"display:none\" ></input></span>")
 	}
 	$(target).append("<span class=\"tree-title\" style=\"cursor:default;"+opts.fontStyle+"\">"+opts.labelText+"</span>")
 	$(target).unbind(); //移除所有
 	$(target).find(".tree-checkbox").click(function(){
 			if($(this).hasClass("rediocomchecked")){//取消选中
 				redioboxseleckunchecked(this,opts)
 			}else{//选中
 				redioboxseleckchecked(this,opts)
 			}
 	})
 	$(target).width(opts.width)
 	$(target).height(opts.height)
 }
 /**
  * 单选框选中
  * @param {Object} obj
  * @param {Object} opts
  * @memberOf {TypeName} 
  */
 function redioboxseleckchecked(obj,opts){
 	if(opts.enable){
 		var	f=opts.onBeforeCheck($(obj).parent())
 		if(typeof f=="undefined"){
 			f=true;
 		}
 		if(f){
 			var group=$(obj).attr("group");
 			if(typeof group=="undefined"){
 					group='rediostring';
 			}
 			$("span[group='"+group+"']").each(function(){
 				$(this).removeClass("rediocomchecked");
 				$(this).addClass("rediocom");
 				$(this).find("input").prop("checked", false);
 				$(this).find("input").next().remove();
 				//$(this).find("input").removeAttr("checked");
 			})
 			$(obj).removeClass("rediocom")
 			$(obj).addClass("rediocomchecked")
 			$(obj).find("input").prop("checked", true);
 			var name=$(obj).find("input").eq(0).attr("name");
 			if(opts.labelName!="undefined"){
 				$(obj).find("input").after("<input type=hidden name="+opts.labelName+"  value="+$(obj).next().html()+"></input>")
 			}else{
 				$(obj).find("input").after("<input type=hidden name=label"+name+"  value="+$(obj).next().html()+"></input>")
 			}
 			
 			
 		}
 		
 		opts.onAfterCheck($(obj).parent())	
 	}else{
 		$($(obj).parent()).find(".tree-title").css("color","#aaa")
 	}	
 }
 /**
  * 单选取消选中
  * @param {Object} obj
  * @param {Object} opts
  */
 function redioboxseleckunchecked(obj,opts){
 	if(opts.enable){
 		var f=opts.onBeforeunCheck($(obj).parent())
 		if(typeof f=="undefined"){
 				f=true;
 		}
 		if(f){
 			if($(obj).find("input").prop("checked")==false&&opts.canel==false){
 				$(obj).removeClass("rediocomchecked")
 				$(obj).addClass("rediocom")
 				$(obj).find("input").prop("checked", false);
 				$(obj).find("input").next().remove();
 			}
 			if(opts.canel==true){
 				$(obj).removeClass("rediocomchecked")
 				$(obj).addClass("rediocom")
 				$(obj).find("input").prop("checked", false);
 				$(obj).find("input").next().remove();
 			}
 			//$(obj).find("input").removeAttr("checked");
 		}
 		opts.onAfterunCheck($(obj).parent())
 	}else{
 		$($(obj).parent()).find(".tree-title").css("color","#aaa")
 	}
 }







/**
 * 声明普通表格对象
 * @param {Object} options
 * @param {Object} param
 * @memberOf {TypeName} 
 * @return {TypeName} 
 */
$.fn.datagridSinosoft=function(options,param){
	if (typeof options == 'string'){
			var method = $.fn.datagridSinosoft.methods[options];
			if (method){
				return method(this, param);
			}else{
				console.log("没有此方法")
			}
	}
	options = options || {};
    return this.each(function () {
       	//给这个对象挂载一个checkedBox的对象，
        var state = $.data(this, 'datagridSinosoft');
        if (state) {
            $.extend(state.options, options);
            datagridSinosoftCreate(this);
        }
        else {
        	$.data(this, 'datagridSinosoft', {
					options: $.extend({}, $.fn.datagridSinosoft.defaults, $.fn.datagridSinosoft.parseOptions(this), options)
				});
            datagridSinosoftCreate(this)
        }
    })		
};
/**
 * 读取参数
 * @param {Object} target
 * @return {TypeName} 
 */
$.fn.datagridSinosoft.parseOptions=function(target){
		if(typeof $(target).attr("par")=="undefined"){
		return new Object()
		}else{
			var t ="{"+$(target).attr("par")+"}"
			return eval("("+t+")");
		}	
}	

/**
 *  创建一个表格
 * @param {Object} options
 * @param {Object} param
 * @memberOf {TypeName} 
 * @return {TypeName} 
 */
function datagridSinosoftCreate(tager){
	var object =$.extend(true,{},$.fn.datagridSinosoft.defaults,$.data(tager, 'datagridSinosoft').options);
	object.body.toolbar=object.toolbar;
	object.pagebar.pageSize=object.body.pageSize;
	object.pagebar.pageList=object.body.pageList;
	$(tager).datagrid(object.body);
	$(tager).datagrid("getPager").pagination(object.pagebar)
}
/**
 * 这里所有datagridSinosoft的方法
 * @param {Object} jq
 * @return {TypeName} 
 */
$.fn.datagridSinosoft.methods={
	setOpt:function(jq,obj){
		$.extend(true,$(jq[0]).datagridSinosoft("options"),obj)
	},
	setUrl:function(jq,obj){
		$(jq[0]).datagridSinosoft("gridoptions").url=obj;
	},
	doCellTip:function(jq,param){
		return $(jq[0]).datagrid("doCellTip",param);
	},
	options:function(jq){//获取当前组件的参数
			return $.data(jq[0], 'datagridSinosoft').options;
	},
	gridoptions:function(jq){//获取grid的参数
			return $(jq[0]).datagrid("options");
	},
	pageoptions:function(jq){//获取page工具條的参数
			return $(jq[0]).datagrid("getPager").pagination("options");;
	},
	getPager:function(jq){//获取分页提示条的对象
		return $(jq[0]).datagrid("getPager");
	},
	getPanel:function(jq){//返回面板对象。
		return $(jq[0]).datagrid("getPanel");
	},
	getColumnFields:function(jq,param){//返回列字段。如果设置了frozen属性为true，将返回固定列的字段名。
		return $(jq[0]).datagrid("getColumnFields",param);
	},
	getColumnOption:function(jq,param){//返回指定列属性。
		return $(jq[0]).datagrid("getColumnOption",param);
	},
	resize:function(jq,param){//做调整和布局。
		return $(jq[0]).datagrid("resize",param);
	},
	load:function(jq,param){//加载和显示第一页的所有行。如果指定了'param'，它将取代'queryParams'属性。通常可以通过传递一些参数执行一次查询，通过调用这个方法从服务器加载新数据。
		return $(jq[0]).datagrid("load",param);
	},
	
	reload:function(jq,param){//重载行。等同于'load'方法，但是它将保持在当前页。
		return $(jq[0]).datagrid("reload",param);
	},
	reloadFooter:function(jq,param){//重载页脚行。
		return $(jq[0]).datagrid("reloadFooter",param);
	},
	loading:function(jq,param){//隐藏载入状态。
		return $(jq[0]).datagrid("loading");
	},
	loaded:function(jq,param){//使列自动展开/收缩到合适的DataGrid宽度。
		return $(jq[0]).datagrid("loaded");
	},
	fitColumns:function(jq,field){//固定列大小。如果'field'参数未配置，所有列大小将都是固定的
		return $(jq[0]).datagrid("fitColumns",field);
	},
	fixColumnSize:function(jq,index){//固定指定列高度。如果'index'参数未配置，所有行高度都是固定的。
		return $(jq[0]).datagrid("fixColumnSize",index);
	},
	fixRowHeight:function(jq,index){//固定指定列高度。如果'index'参数未配置，所有行高度都是固定的。
		return $(jq[0]).datagrid("fixRowHeight",index);
	},
	freezeRow:function(jq,param){//冻结指定行，当DataGrid表格向下滚动的时候始终保持被冻结的行显示在顶部。
		return $(jq[0]).datagrid("freezeRow",param);
	},
	autoSizeColumn:function(jq,param){//自动调整列宽度以适应内容。
		return $(jq[0]).datagrid("autoSizeColumn",param);
	},
	loadData:function(jq,param){//加载本地数据，旧的行将被移除。
		return $(jq[0]).datagrid("loadData",param);
	},
	getData:function(jq){//返回加载完毕后的数据。
		return $(jq[0]).datagrid("getData");
	},
	getRows:function(jq){//返回当前页的所有行。
		return $(jq[0]).datagrid("getRows");
	},
	getFooterRows:function(jq){//返回页脚行。
		return $(jq[0]).datagrid("getFooterRows");
	},
	getRowIndex:function(jq,row){//返回指定行的索引号，该行的参数可以是一行记录或一个ID字段值。
		return $(jq[0]).datagrid("getRowIndex",row);
	},
	getChecked:function(jq){//在复选框呗选中的时候返回所有行
		return $(jq[0]).datagrid("getChecked");
	},
	getSelected:function(jq){//返回第一个被选中的行或如果没有选中的行则返回null。
		return $(jq[0]).datagrid("getSelected");
	},
	getSelections:function(jq){//返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
		return $(jq[0]).datagrid("getSelections");
	},
	clearSelections:function(jq){//清除所有选择的行。
		return $(jq[0]).datagrid("clearSelections");
	},
	clearChecked:function(jq){//清除所有勾选的行
		return $(jq[0]).datagrid("clearChecked");
	},
	scrollTo:function(jq,index){//滚动到指定的行。
		return $(jq[0]).datagrid("scrollTo",index);
	},
	highlightRow:function(jq,index){//高亮一行
		return $(jq[0]).datagrid("highlightRow",index);
	},
	selectAll:function(jq){//选择当前页中所有的行。
		return $(jq[0]).datagrid("selectAll");
	},
	unselectAll:function(jq){//取消选择所有当前页中所有的行。
		return $(jq[0]).datagrid("unselectAll");
	},
	selectRow:function(jq,index){//选择一行，行索引从0开始。
		return $(jq[0]).datagrid("selectRow",index);
	},
	selectRecord:function(jq,idValue){//通过ID值参数选择一行。
		return $(jq[0]).datagrid("selectRecord",idValue);
	},
	unselectRow:function(jq,index){//取消选择一行。
		return $(jq[0]).datagrid("unselectRow",index);
	},
	checkAll:function(jq){//勾选当前页中的所有行
		return $(jq[0]).datagrid("checkAll");
	},
	uncheckAll:function(jq){//取消勾选当前页中的所有行
		return $(jq[0]).datagrid("uncheckAll");
	},
	checkRow:function(jq,index){//勾选一行，行索引从0开始
		return $(jq[0]).datagrid("checkRow",index);
	},
	uncheckRow:function(jq,index){//取消勾选一行，行索引从0开始。
		return $(jq[0]).datagrid("uncheckRow",index);
	},
	beginEdit:function(jq,index){//开始编辑行。
		return $(jq[0]).datagrid("beginEdit",index);
	},
	endEdit:function(jq,index){//结束编辑行。
		return $(jq[0]).datagrid("endEdit");
	},
	cancelEdit:function(jq,index){//取消编辑行。
		return $(jq[0]).datagrid("cancelEdit",index);
	},
	getEditors:function(jq,index){//获取指定行的编辑器。每个编辑器都有以下属性：actions：编辑器可以执行的动作，同编辑器定义。target：目标编辑器的jQuery对象。field：字段名称。type：编辑器类型，比如：'text','combobox','datebox'等。
		return $(jq[0]).datagrid("getEditors",index);
	},
	getEditor:function(jq,options){//获取指定编辑器，options包含2个属性：index：行索引。field：字段名称。 
		return $(jq[0]).datagrid("getEditor",options);
	},
	refreshRow:function(jq,index){//刷新行。
		return $(jq[0]).datagrid("refreshRow",index);
	},
	validateRow:function(jq,index){//验证指定的行，当验证有效的时候返回true。
		return $(jq[0]).datagrid("validateRow",index);
	},
	updateRow:function(jq,param){//更新指定行，参数包含下列属性：index：执行更新操作的行索引。row：更新行的新数据。
		return $(jq[0]).datagrid("updateRow",param);
	},
	appendRow:function(jq,row){//追加一个新行。新行将被添加到最后的位置。 
		return $(jq[0]).datagrid("appendRow",row);
	},
	insertRow:function(jq,param){//插入一个新行，参数包括一下属性：index：要插入的行索引，如果该索引值未定义，则追加新行。row：行数据。
		return $(jq[0]).datagrid("insertRow",param);
	},
	deleteRow:function(jq,index){//删除行。
		return $(jq[0]).datagrid("deleteRow",index);
	},
	getChanges:function(jq,type){//从上一次的提交获取改变的所有行。类型参数指明用哪些类型改变的行，可以使用的值有：inserted,deleted,updated等。当类型参数未配置的时候返回所有改变的行
		return $(jq[0]).datagrid("getChanges",type);
	},
	acceptChanges:function(jq){//提交所有从加载或者上一次调用acceptChanges函数后更改的数据。
		return $(jq[0]).datagrid("acceptChanges");
	},
	
	rejectChanges:function(jq){//回滚所有从创建或者上一次调用acceptChanges函数后更改的数据。
		return $(jq[0]).datagrid("rejectChanges");
	},
	mergeCells:function(jq,options){//合并单元格，options包含以下属性：index：行索引。field：字段名称。rowspan：合并的行数。colspan：合并的列数
		return $(jq[0]).datagrid("mergeCells",options);
	},
	showColumn:function(jq,field){//显示指定的列。
		return $(jq[0]).datagrid("showColumn",field);
	},
	hideColumn:function(jq,field){//隐藏指定的列。
		return $(jq[0]).datagrid("hideColumn",field);
	},
	sort:function(jq,param){//排序datagrid表格
		return $(jq[0]).datagrid("sort",param);
	},
	loading:function(jq){//提醒分页控件正在加载中。
		return $(jq[0]).datagrid("getPager").pagination("loading");
	},
	loaded:function(jq){//提醒分页控件加载完成。
		return$(jq[0]).datagrid("getPager").pagination("loaded");
	},
	refresh:function(jq,options){//刷新并显示分页栏信息
		return $(jq[0]).datagrid("getPager").pagination("refresh",options);
	},
	select:function(jq,page){//选择一个新页面，页面索引从1开始。
		return $(jq[0]).datagrid("getPager").pagination("select",page);
	}
	
}
/**
 * 表格默认值
 * @param {Object} options
 * @param {Object} param
 * @memberOf {TypeName} 
 * @return {TypeName} 
 */
$.fn.datagridSinosoft.defaults={
		toolbar:[],
		body:{
				fitColumns:true,
				fit:true,
				height: 'auto', 
				striped:true,
				pageSize:20,
				pageList:[10,20,30,50],
				rownumbers:true,
				singleSelect:true,
				pagination:true,
			    loadMsg:"加载中......",
				url:'',
				method:'post'
		},
		pagebar:{
			pageSize:20,
			pageList:[10,20,30,50],
			showPageList:true,
			beforePageText:"第",
			afterPageText:"页,共{pages}页",
			displayMsg:"当前记录 {from}-{to} 总共 {total} 项"
		}
	}





/**
 * 声明可编辑表格对象
 * @param {Object} options
 * @param {Object} param
 * @memberOf {TypeName} 
 * @return {TypeName} 
 */
$.fn.edatagridSinosoft=function(options,param){
	if (typeof options == 'string'){
			var method = $.fn.edatagridSinosoft.methods[options];
			if (method){
				return method(this, param);
			}else{
				console.log("没有此方法")
			}
	}
	options = options || {};
    return this.each(function () {
       	//给这个对象挂载一个checkedBox的对象，
        var state = $.data(this, 'edatagridSinosoft');
        if (state) {
            $.extend(state.options, options);
            edatagridSinosoftCreate(this);
        }
        else {
        	$.data(this, 'edatagridSinosoft', {
					options: $.extend({}, $.fn.edatagridSinosoft.defaults, $.fn.edatagridSinosoft.parseOptions(this), options)
				});
            edatagridSinosoftCreate(this)
        }
    })		
};


/**
 * 读取参数
 * @param {Object} target
 * @return {TypeName} 
 */
$.fn.edatagridSinosoft.parseOptions=function(target){
	if(typeof $(target).attr("par")=="undefined"){
		return new Object()
	}else{
		var t ="{"+$(target).attr("par")+"}"
		return eval("("+t+")");
	}	
}
function edatagridSinosoftCreate(tager){
	var egrid=$(tager);
	var editIndex = undefined;
	var endEditing=function (){//结束编辑
		if (editIndex == undefined){
				var arrrow=egrid.edatagridSinosoft("getRows");
				egrid.datagrid('endEdit', arrrow.length-1);
				return true
		}
		//验证指定的行，当验证有效的时候返回true。
		if (egrid.datagrid('validateRow', editIndex)){
			//结束编辑行。
			egrid.datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	$.fn.edatagridSinosoft.defaults["body"].onClickCell=function(index, field, value){//用户点击单元格的时候触发
			$(egrid).datagrid('selectRow', index);
		if (editIndex != index){
				endEditing()
			}
	}
	$.fn.edatagridSinosoft.defaults["body"].onDblClickCell=function(index, field, value){//用户双击单元格的时候触发
						if (editIndex != index){
								if (endEditing()){
									egrid.datagrid('selectRow', index).datagrid('beginEdit', index);
									var ed = egrid.datagrid('getEditor', {index:index,field:field});
									if (ed){
										($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
									}
									editIndex = index;
								} else {
									setTimeout(function(){
										egrid.datagrid('selectRow', editIndex);
									},0);
								}
							}
	}
	
	
	var object =$.extend(true,{},$.fn.edatagridSinosoft.defaults,$.data(tager, 'edatagridSinosoft').options);
	object.body.toolbar=object.toolbar;
	object.pagebar.pageSize=object.body.pageSize;
	object.pagebar.pageList=object.body.pageList;
	$(tager).datagrid(object.body);
	$(tager).datagrid("getPager").pagination(object.pagebar);
	$(tager).parent().find(".datagrid-body").click(function(){
			endEditing()
	})
	
	
}
$.fn.edatagridSinosoft.defaults={
		toolbar:[],
		body:{
				fitColumns:true,//撑满列
				fit:true,//撑满布局
				height: 'auto', //自动
				striped:true,
				pageSize:20,
				pageList:[10,20,30,50],
				rownumbers:true,
				singleSelect:true,
				pagination:true,
			    loadMsg:"加载中......",
				url:'',
				method:'post'
		},
		pagebar:{
			pageSize:20,
			pageList:[10,20,30,50],
			showPageList:true,
			beforePageText:"第",
			afterPageText:"页,共{pages}页",
			displayMsg:"当前记录 {from}-{to} 总共 {total} 项"
		}
}
/**
 * 这里所有datagridSinosoft的方法
 * @param {Object} jq
 * @return {TypeName} 
 */
$.fn.edatagridSinosoft.methods={
	setOpt:function(jq,obj){
		$.extend(true,$(jq[0]).edatagridSinosoft("options"),obj)
	},
	options:function(jq){//获取当前组件的参数
			return $.data(jq[0], 'edatagridSinosoft').options;
	},
	gridoptions:function(jq){//获取grid的参数
			return $(jq[0]).datagrid("options");
	},
	pageoptions:function(jq){//获取page工具條的参数
			return $(jq[0]).datagrid("getPager").pagination("options");;
	},
	getPager:function(jq){//获取分页提示条的对象
		return $(jq[0]).datagrid("getPager");
	},
	getPanel:function(jq){//返回面板对象。
		return $(jq[0]).datagrid("getPanel");
	},
	getColumnFields:function(jq,param){//返回列字段。如果设置了frozen属性为true，将返回固定列的字段名。
		return $(jq[0]).datagrid("getColumnFields",param);
	},
	getColumnOption:function(jq,param){//返回指定列属性。
		return $(jq[0]).datagrid("getColumnOption",param);
	},
	resize:function(jq,param){//做调整和布局。
		return $(jq[0]).datagrid("resize",param);
	},
	load:function(jq,param){//加载和显示第一页的所有行。如果指定了'param'，它将取代'queryParams'属性。通常可以通过传递一些参数执行一次查询，通过调用这个方法从服务器加载新数据。
		return $(jq[0]).datagrid("load",param);
	},
	
	reload:function(jq,param){//重载行。等同于'load'方法，但是它将保持在当前页。
		return $(jq[0]).datagrid("reload",param);
	},
	reloadFooter:function(jq,param){//重载页脚行。
		return $(jq[0]).datagrid("reloadFooter",param);
	},
	loading:function(jq,param){//隐藏载入状态。
		return $(jq[0]).datagrid("loading");
	},
	loaded:function(jq,param){//使列自动展开/收缩到合适的DataGrid宽度。
		return $(jq[0]).datagrid("loaded");
	},
	fitColumns:function(jq,field){//固定列大小。如果'field'参数未配置，所有列大小将都是固定的
		return $(jq[0]).datagrid("fitColumns",field);
	},
	fixColumnSize:function(jq,index){//固定指定列高度。如果'index'参数未配置，所有行高度都是固定的。
		return $(jq[0]).datagrid("fixColumnSize",index);
	},
	fixRowHeight:function(jq,index){//固定指定列高度。如果'index'参数未配置，所有行高度都是固定的。
		return $(jq[0]).datagrid("fixRowHeight",index);
	},
	freezeRow:function(jq,param){//冻结指定行，当DataGrid表格向下滚动的时候始终保持被冻结的行显示在顶部。
		return $(jq[0]).datagrid("freezeRow",param);
	},
	autoSizeColumn:function(jq,param){//自动调整列宽度以适应内容。
		return $(jq[0]).datagrid("autoSizeColumn",param);
	},
	loadData:function(jq,param){//加载本地数据，旧的行将被移除。
		return $(jq[0]).datagrid("loadData",param);
	},
	getData:function(jq){//返回加载完毕后的数据。
		return $(jq[0]).datagrid("getData");
	},
	getRows:function(jq){//返回当前页的所有行。
		return $(jq[0]).datagrid("getRows");
	},
	getFooterRows:function(jq){//返回页脚行。
		return $(jq[0]).datagrid("getFooterRows");
	},
	getRowIndex:function(jq,row){//返回指定行的索引号，该行的参数可以是一行记录或一个ID字段值。
		return $(jq[0]).datagrid("getRowIndex",row);
	},
	getChecked:function(jq){//在复选框呗选中的时候返回所有行
		return $(jq[0]).datagrid("getChecked");
	},
	getSelected:function(jq){//返回第一个被选中的行或如果没有选中的行则返回null。
		return $(jq[0]).datagrid("getSelected");
	},
	getSelections:function(jq){//返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
		return $(jq[0]).datagrid("getSelections");
	},
	clearSelections:function(jq){//清除所有选择的行。
		return $(jq[0]).datagrid("clearSelections");
	},
	clearChecked:function(jq){//清除所有勾选的行
		return $(jq[0]).datagrid("clearChecked");
	},
	scrollTo:function(jq,index){//滚动到指定的行。
		return $(jq[0]).datagrid("scrollTo",index);
	},
	highlightRow:function(jq,index){//高亮一行
		return $(jq[0]).datagrid("highlightRow",index);
	},
	selectAll:function(jq){//选择当前页中所有的行。
		return $(jq[0]).datagrid("selectAll");
	},
	unselectAll:function(jq){//取消选择所有当前页中所有的行。
		return $(jq[0]).datagrid("unselectAll");
	},
	selectRow:function(jq,index){//选择一行，行索引从0开始。
		return $(jq[0]).datagrid("selectRow",index);
	},
	selectRecord:function(jq,idValue){//通过ID值参数选择一行。
		return $(jq[0]).datagrid("selectRecord",idValue);
	},
	unselectRow:function(jq,index){//取消选择一行。
		return $(jq[0]).datagrid("unselectRow",index);
	},
	checkAll:function(jq){//勾选当前页中的所有行
		return $(jq[0]).datagrid("checkAll");
	},
	uncheckAll:function(jq){//取消勾选当前页中的所有行
		return $(jq[0]).datagrid("uncheckAll");
	},
	checkRow:function(jq,index){//勾选一行，行索引从0开始
		return $(jq[0]).datagrid("checkRow",index);
	},
	uncheckRow:function(jq,index){//取消勾选一行，行索引从0开始。
		return $(jq[0]).datagrid("uncheckRow",index);
	},
	beginEdit:function(jq,index){//开始编辑行。
		return $(jq[0]).datagrid("beginEdit",index);
	},
	endEdit:function(jq,index){//结束编辑行。
		return $(jq[0]).datagrid("endEdit");
	},
	cancelEdit:function(jq,index){//取消编辑行。
		return $(jq[0]).datagrid("cancelEdit",index);
	},
	getEditors:function(jq,index){//获取指定行的编辑器。每个编辑器都有以下属性：actions：编辑器可以执行的动作，同编辑器定义。target：目标编辑器的jQuery对象。field：字段名称。type：编辑器类型，比如：'text','combobox','datebox'等。
		return $(jq[0]).datagrid("getEditors",index);
	},
	getEditor:function(jq,options){//获取指定编辑器，options包含2个属性：index：行索引。field：字段名称。 
		return $(jq[0]).datagrid("getEditor",options);
	},
	refreshRow:function(jq,index){//刷新行。
		return $(jq[0]).datagrid("refreshRow",index);
	},
	validateRow:function(jq,index){//验证指定的行，当验证有效的时候返回true。
		return $(jq[0]).datagrid("validateRow",index);
	},
	updateRow:function(jq,param){//更新指定行，参数包含下列属性：index：执行更新操作的行索引。row：更新行的新数据。
		return $(jq[0]).datagrid("updateRow",param);
	},
	appendRow:function(jq,row){//追加一个新行。新行将被添加到最后的位置。 
		return $(jq[0]).datagrid("appendRow",row);
	},
	insertRow:function(jq,param){//插入一个新行，参数包括一下属性：index：要插入的行索引，如果该索引值未定义，则追加新行。row：行数据。
		return $(jq[0]).datagrid("insertRow",param);
	},
	deleteRow:function(jq,index){//删除行。
		return $(jq[0]).datagrid("deleteRow",index);
	},
	getChanges:function(jq,type){//从上一次的提交获取改变的所有行。类型参数指明用哪些类型改变的行，可以使用的值有：inserted,deleted,updated等。当类型参数未配置的时候返回所有改变的行
		return $(jq[0]).datagrid("getChanges",type);
	},
	acceptChanges:function(jq){//提交所有从加载或者上一次调用acceptChanges函数后更改的数据。
		return $(jq[0]).datagrid("acceptChanges");
	},
	
	rejectChanges:function(jq){//回滚所有从创建或者上一次调用acceptChanges函数后更改的数据。
		return $(jq[0]).datagrid("rejectChanges");
	},
	mergeCells:function(jq,options){//合并单元格，options包含以下属性：index：行索引。field：字段名称。rowspan：合并的行数。colspan：合并的列数
		return $(jq[0]).datagrid("mergeCells",options);
	},
	showColumn:function(jq,field){//显示指定的列。
		return $(jq[0]).datagrid("showColumn",field);
	},
	hideColumn:function(jq,field){//隐藏指定的列。
		return $(jq[0]).datagrid("hideColumn",field);
	},
	sort:function(jq,param){//排序datagrid表格
		return $(jq[0]).datagrid("sort",param);
	},
	loading:function(jq){//提醒分页控件正在加载中。
		return $(jq[0]).datagrid("getPager").pagination("loading");
	},
	loaded:function(jq){//提醒分页控件加载完成。
		return$(jq[0]).datagrid("getPager").pagination("loaded");
	},
	refresh:function(jq,options){//刷新并显示分页栏信息
		return $(jq[0]).datagrid("getPager").pagination("refresh",options);
	},
	select:function(jq,page){//选择一个新页面，页面索引从1开始。
		return $(jq[0]).datagrid("getPager").pagination("select",page);
	}
	
}




$.fn.treegridSinosoft=function(options,param){
	if (typeof options == 'string'){
			var method = $.fn.treegridSinosoft.methods[options];
			if (method){
				return method(this, param);
			}else{
				console.log("没有此方法")
			}
	}
	options = options || {};
    return this.each(function () {
       	//给这个对象挂载一个checkedBox的对象，
        var state = $.data(this, 'treegridSinosoft');
        if (state) {
            $.extend(state.options, options);
            treegridSinosoftCreate(this);
        }
        else {
        	$.data(this, 'treegridSinosoft', {
					options: $.extend({}, $.fn.treegridSinosoft.defaults, $.fn.treegridSinosoft.parseOptions(this), options)
				});
            treegridSinosoftCreate(this)
        }
    })
}
/**
 * 读取参数
 * @param {Object} target
 * @return {TypeName} 
 */
$.fn.treegridSinosoft.parseOptions=function(target){
		if(typeof $(target).attr("par")=="undefined"){
		return new Object()
	}else{
		var t ="{"+$(target).attr("par")+"}"
		return eval("("+t+")");
	}	
}

function treegridSinosoftCreate(tager){
	var object =$.extend(true,{},$.fn.treegridSinosoft.defaults,$.data(tager, 'treegridSinosoft').options);
	object.body.toolbar=object.toolbar;
	object.pagebar.pageSize=object.body.pageSize;
	object.pagebar.pageList=object.body.pageList;
	$(tager).treegrid(object.body);
	$(tager).treegrid("getPager").pagination(object.pagebar)
}
$.fn.treegridSinosoft.defaults={
	toolbar:[],
	body:{
		toolbar:[],
		url:'/sinosoftUI/html/datagrid/data/treegrid_data.json',    
		idField:'id', //第一列的id
		treeField:'name', //第一列的显示值
		fit:true,  //让grid撑满父容器
		fitColumns:true,//让grid撑满父容器
		pagination:false,
		loadMsg:"加载中......",
		method:'post'
		},
	pagebar:{
		pageSize:10,
		pageList:[10,20,30,40,50],
		showPageList:true,
		beforePageText:"第",
		afterPageText:"页,共{pages}页",
		displayMsg:"当前记录 {from}-{to} 总共 {total} 项"
	}
	
}
$.fn.treegridSinosoft.methods={
	options:function(jq){//获取当前组件的参数
			return $.data(jq[0], 'treegridSinosoft').options;
	},
	gridoptions:function(jq){//获取treegrid的参数
			return $(jq[0]).treegrid("options");
	},
	pageoptions:function(jq){//获取page工具條的参数
			return $(jq[0]).treegrid("getPager").pagination("options");;
	},
	/*设置树形表格大小，options包含2个属性：
		width：树形表格的新宽度。
		height：树形表格的新高度。*/
	resize:function(jq,options){
		return $(jq[0]).treegrid("resize",options);
	},
	//修正指定的行高
	fixRowHeight:function(jq,id){
		return $(jq[0]).treegrid("fixRowHeight",id);
		
	},	
	/*
	代码示例：
	
	 读取并发送请求参数
	$('#tg').treegrid('load', {
		q: 'abc',
		name: 'name1'
	});*/
	load:function(jq,param){
		return $(jq[0]).treegrid("load",param);
	},
	/*读取树形表格数据。*/
	loadData:function(jq,data){
		return $(jq[0]).treegrid("loadData",data);
		
	},
	/*重新加载树形表格数据。如果'id'属性有值，将重新载入指定树形行，否则重新载入所有行。 
	代码示例：
	
	$('#tt').treegrid('reload', 2);	// 重新载入值为2的行
	$('#tt').treegrid('reload');	// 重新载入所有行*/
	reload:function(jq,id){
		return $(jq[0]).treegrid("reload",id);
	},
	//重新载入页脚数据。
	reloadFooter:function(jq,footer){
		return $(jq[0]).treegrid("reloadFooter",footer);
	},
	//获取载入数据。
	getData:function(jq){
		return $(jq[0]).treegrid("getData");
	},
	//获取页脚数据。
	getFooterRows:function(jq){
		return $(jq[0]).treegrid("getFooterRows");
	},
	//获取根节点，返回节点对象。
	getRoot:function(jq){
		return $(jq[0]).treegrid("getRoot");
		
	},
	//获取所有根节点，返回节点数组。
	getRoots:function(jq){
		
		return $(jq[0]).treegrid("getRoots");
	},
	//获取父节点。
	getParent:function(jq,id){
		return $(jq[0]).treegrid("getParent",id);
	},
	//获取子节点。
	getChildren:function(jq,id){
		return $(jq[0]).treegrid("getChildren",id);
	},
	//获取选择的节点并返回它，如果没有节点被选中则返回null。
	getSelected:function(jq){
		return $(jq[0]).treegrid("getSelected");
	},
	//获取所有选择的节点。
	getSelections:function(jq){
		return $(jq[0]).treegrid("getSelected");
		
	},
	//获取指定节点等级
	getLevel:function(jq,id){
		return $(jq[0]).treegrid("getLevel",id);
	},
	//查找指定节点并返回节点数据。
	find:function(jq,id){
		return $(jq[0]).treegrid("find",id);

	},
	//选择一个节点。
	select:function(jq,id){
		return $(jq[0]).treegrid("select",id);
		
	},
	//反选一个节点。
	unselect:function(jq,id){
		return $(jq[0]).treegrid("unselect",id);
	},
	//选择所有节点。
	selectAll:function(jq){
		return $(jq[0]).treegrid("selectAll");
	},
	//反选所有节点。
	unselectAll:function(jq){
		return $(jq[0]).treegrid("unselectAll");
	},
	//折叠一个节点。
	collapse:function(jq,id){
		return $(jq[0]).treegrid("collapse",id);
	},
	//展开一个节点。
	expand:function(jq,id){
		return $(jq[0]).treegrid("expand",id);
	},
	//折叠所有节点。
	collapseAll:function(jq,id){
		return $(jq[0]).treegrid("collapseAll",id);
	},
	//展开所有节点。
	expandAll:function(jq,id){
		return $(jq[0]).treegrid("expandAll",id);
	},
	//打开从根节点到指定节点之间的所有节点。
	expandTo:function(jq,id){
		return $(jq[0]).treegrid("expandTo",id);
		
	},
	//节点展开/折叠状态触发器。
	toggle:function(jq,id){
		return $(jq[0]).treegrid("toggle",id);
	},
	/*追加节点到一个父节点，'param'参数包含如下属性：
	parent：父节点ID，如果未指定则追加到根节点。
	data：数组，节点数据。
	代码示例： 
	// 追加若干节点到选中节点的后面var node = $('#tt').treegrid('getSelected');
	$('#tt').treegrid('append',{
		parent: node.id,  // the node has a 'id' value that defined through 'idField' property
		data: [{
			id: '073',
			name: 'name73'
		}]
	});
	*/
	append:function(jq,param){
		return $(jq[0]).treegrid("append",param);
	},
/*	插入一个新节点到指定节点。'param'参数包含一下参数：
	before：插入指定节点ID值之前。
	after：插入指定节点ID值之后。
	data：新节点数据。 
	代码示例：
	
	// 插入一个新节点到选择的节点之前
	var node = $('#tt').treegrid('getSelected');
	if (node){
		$('#tt').treegrid('insert', {
			before: node.id,
			data: {
				id: 38,
				name: 'name38'
			}
		});
	}*/
	

	insert:function(jq,param){
		return $(jq[0]).treegrid("insert",param);	
	},
	//除一个节点和他的所有子节点。
	remove:function(jq,id){
		return $(jq[0]).treegrid("remove",id);	
	},
	//弹出并返回节点数据以及它的子节点之后删除
	pop:function(jq,id){
		return $(jq[0]).treegrid("pop",id);	
	},

	//刷新指定节点。
	refresh:function(jq,id){
		return $(jq[0]).treegrid("refresh",id);	
	},
	/*	更新指定节点。'param'参数包含以下属性：
	id：要更新的节点的ID。
	row：新的行数据。 
	代码示例：
	
	$('#tt').treegrid('update',{
		id: 2,
		row: {
			name: '新名称',
			iconCls: 'icon-save'
		}
	});*/
	update:function(jq,param){
		return $(jq[0]).treegrid("update",param);	
	},
	//开始编辑一个节点。
	beginEdit:function(jq,id){
		return $(jq[0]).treegrid("beginEdit",id);	
	},
	//结束编辑一个节点。
	endEdit:function(jq,id){
		return $(jq[0]).treegrid("endEdit",id);	
	},
	//取消编辑一个节点。
	cancelEdit:function(jq,id){
		return $(jq[0]).treegrid("cancelEdit",id);	
	},
	/*获取指定行编辑器。每个编辑器都包含以下属性：
	actions：编辑器执行的动作。
	target：目标编辑器的jQuery对象。
	field：字段名称。
	type：编辑器类型。*/
	getEditors:function(jq,id){
		return $(jq[0]).treegrid("getEditors",id);	
	},
	/*获取指定编辑器，'param'参数包含2个属性：
	id：行节点ID。
	field：字段名称。*/
	getEditor:function(jq,param){
		return $(jq[0]).treegrid("getEditor",param);	
	},
	loading:function(jq){//提醒分页控件正在加载中。
		return $(jq[0]).treegrid("getPager").pagination("loading");
	},
	loaded:function(jq){//提醒分页控件加载完成。
		return$(jq[0]).treegrid("getPager").pagination("loaded");
	},
	pagerefresh:function(jq,options){//刷新并显示分页栏信息
		return $(jq[0]).treegrid("getPager").pagination("refresh",options);
	},
	pageselect:function(jq,page){//选择一个新页面，页面索引从1开始。
		return $(jq[0]).treegrid("getPager").pagination("select",page);
	}
	
}





var detailview = $.extend({}, $.fn.datagrid.defaults.view, {
	render: function(target, container, frozen){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		if (frozen){
			if (!(opts.rownumbers || (opts.frozenColumns && opts.frozenColumns.length))){
				return;
			}
		}
		var rows = state.data.rows;
		var fields = $(target).datagrid('getColumnFields', frozen);
		var table = [];
		table.push('<table class="datagrid-btable" cellspacing="0" cellpadding="0" border="0"><tbody>');
		for(var i=0; i<rows.length; i++) {
			// get the class and style attributes for this row
			var css = opts.rowStyler ? opts.rowStyler.call(target, i, rows[i]) : '';
			var classValue = '';
			var styleValue = '';
			if (typeof css == 'string'){
				styleValue = css;
			} else if (css){
				classValue = css['class'] || '';
				styleValue = css['style'] || '';
			}
			
			var cls = 'class="datagrid-row ' + (i % 2 && opts.striped ? 'datagrid-row-alt ' : ' ') + classValue + '"';
			var style = styleValue ? 'style="' + styleValue + '"' : '';
			var rowId = state.rowIdPrefix + '-' + (frozen?1:2) + '-' + i;
			table.push('<tr id="' + rowId + '" datagrid-row-index="' + i + '" ' + cls + ' ' + style + '>');
			table.push(this.renderRow.call(this, target, fields, frozen, i, rows[i]));
			table.push('</tr>');
			
			table.push('<tr style="display:none;">');
			if (frozen){
				table.push('<td colspan=' + (fields.length+(opts.rownumbers?1:0)) + ' style="border-right:0">');
			} else {
				table.push('<td colspan=' + (fields.length) + '>');
			}

			table.push('<div class="datagrid-row-detail">');
			if (frozen){
				table.push('&nbsp;');
			} else {
				table.push(opts.detailFormatter.call(target, i, rows[i]));
			}
			table.push('</div>');

			table.push('</td>');
			table.push('</tr>');
			
		}
		table.push('</tbody></table>');
		
		$(container).html(table.join(''));
	},
	
	renderRow: function(target, fields, frozen, rowIndex, rowData){
		var opts = $.data(target, 'datagrid').options;
		
		var cc = [];
		if (frozen && opts.rownumbers){
			var rownumber = rowIndex + 1;
			if (opts.pagination){
				rownumber += (opts.pageNumber-1)*opts.pageSize;
			}
			cc.push('<td class="datagrid-td-rownumber"><div class="datagrid-cell-rownumber">'+rownumber+'</div></td>');
		}
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			var col = $(target).datagrid('getColumnOption', field);
			if (col){
				var value = rowData[field];	// the field value
				var css = col.styler ? (col.styler(value, rowData, rowIndex)||'') : '';
				var classValue = '';
				var styleValue = '';
				if (typeof css == 'string'){
					styleValue = css;
				} else if (cc){
					classValue = css['class'] || '';
					styleValue = css['style'] || '';
				}
				var cls = classValue ? 'class="' + classValue + '"' : '';
				var style = col.hidden ? 'style="display:none;' + styleValue + '"' : (styleValue ? 'style="' + styleValue + '"' : '');
				
				cc.push('<td field="' + field + '" ' + cls + ' ' + style + '>');
				
				if (col.checkbox){
					style = '';
				} else if (col.expander){
					style = "text-align:center;height:16px;";
				} else {
					style = styleValue;
					if (col.align){style += ';text-align:' + col.align + ';'}
					if (!opts.nowrap){
						style += ';white-space:normal;height:auto;';
					} else if (opts.autoRowHeight){
						style += ';height:auto;';
					}
				}
				
				cc.push('<div style="' + style + '" ');
				if (col.checkbox){
					cc.push('class="datagrid-cell-check ');
				} else {
					cc.push('class="datagrid-cell ' + col.cellClass);
				}
				cc.push('">');
				
				if (col.checkbox){
					cc.push('<input type="checkbox" name="' + field + '" value="' + (value!=undefined ? value : '') + '">');
				} else if (col.expander) {
					//cc.push('<div style="text-align:center;width:16px;height:16px;">');
					cc.push('<span class="datagrid-row-expander datagrid-row-expand" style="display:inline-block;width:16px;height:16px;cursor:pointer;" />');
					//cc.push('</div>');
				} else if (col.formatter){
					cc.push(col.formatter(value, rowData, rowIndex));
				} else {
					cc.push(value);
				}
				
				cc.push('</div>');
				cc.push('</td>');
			}
		}
		return cc.join('');
	},
	
	insertRow: function(target, index, row){
		var opts = $.data(target, 'datagrid').options;
		var dc = $.data(target, 'datagrid').dc;
		var panel = $(target).datagrid('getPanel');
		var view1 = dc.view1;
		var view2 = dc.view2;
		
		var isAppend = false;
		var rowLength = $(target).datagrid('getRows').length;
		if (rowLength == 0){
			$(target).datagrid('loadData',{total:1,rows:[row]});
			return;
		}
		
		if (index == undefined || index == null || index >= rowLength) {
			index = rowLength;
			isAppend = true;
			this.canUpdateDetail = false;
		}
		
		$.fn.datagrid.defaults.view.insertRow.call(this, target, index, row);
		
		_insert(true);
		_insert(false);
		
		this.canUpdateDetail = true;
		
		function _insert(frozen){
			var v = frozen ? view1 : view2;
			var tr = v.find('tr[datagrid-row-index='+index+']');
			
			if (isAppend){
				var newDetail = tr.next().clone();
				tr.insertAfter(tr.next());
			} else {
				var newDetail = tr.next().next().clone();
			}
			newDetail.insertAfter(tr);
			newDetail.hide();
			if (!frozen){
				newDetail.find('div.datagrid-row-detail').html(opts.detailFormatter.call(target, index, row));
			}
		}
	},
	
	deleteRow: function(target, index){
		var opts = $.data(target, 'datagrid').options;
		var dc = $.data(target, 'datagrid').dc;
		var tr = opts.finder.getTr(target, index);
		tr.next().remove();
		$.fn.datagrid.defaults.view.deleteRow.call(this, target, index);
		dc.body2.triggerHandler('scroll');
	},
	
	updateRow: function(target, rowIndex, row){
		var dc = $.data(target, 'datagrid').dc;
		var opts = $.data(target, 'datagrid').options;
		var cls = $(target).datagrid('getExpander', rowIndex).attr('class');
		$.fn.datagrid.defaults.view.updateRow.call(this, target, rowIndex, row);
		$(target).datagrid('getExpander', rowIndex).attr('class',cls);
		
		// update the detail content
		if (this.canUpdateDetail){
			var row = $(target).datagrid('getRows')[rowIndex];
			var detail = $(target).datagrid('getRowDetail', rowIndex);
			detail.html(opts.detailFormatter.call(target, rowIndex, row));
		}
	},
	
	bindEvents: function(target){
		var state = $.data(target, 'datagrid');

		if (state.ss.bindDetailEvents){return;}
		state.ss.bindDetailEvents = true;

		var dc = state.dc;
		var opts = state.options;
		var body = dc.body1.add(dc.body2);
		var clickHandler = ($.data(body[0],'events')||$._data(body[0],'events')).click[0].handler;
		body.unbind('click').bind('click', function(e){
			var tt = $(e.target);
			var tr = tt.closest('tr.datagrid-row');
			if (!tr.length){return}
			if (tt.hasClass('datagrid-row-expander')){
				var rowIndex = parseInt(tr.attr('datagrid-row-index'));
				if (tt.hasClass('datagrid-row-expand')){
					$(target).datagrid('expandRow', rowIndex);
				
				} else {
					$(target).datagrid('collapseRow', rowIndex);
				}
				$(target).datagrid('fixRowHeight');
				
			} else {
				clickHandler(e);
			}
			e.stopPropagation();
		});
	},
	
	onBeforeRender: function(target){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var dc = state.dc;
		var t = $(target);
		var hasExpander = false;
		var fields = t.datagrid('getColumnFields',true).concat(t.datagrid('getColumnFields'));
		for(var i=0; i<fields.length; i++){
			var col = t.datagrid('getColumnOption', fields[i]);
			if (col.expander){
				hasExpander = true;
				break;
			}
		}
		if (!hasExpander){
			if (opts.frozenColumns && opts.frozenColumns.length){
				opts.frozenColumns[0].splice(0,0,{field:'_expander',expander:true,width:24,resizable:false,fixed:true});
			} else {
				opts.frozenColumns = [[{field:'_expander',expander:true,width:24,resizable:false,fixed:true}]];
			}
			
			var t = dc.view1.children('div.datagrid-header').find('table');
			var td = $('<td rowspan="'+opts.frozenColumns.length+'"><div class="datagrid-header-expander" style="width:24px;"></div></td>');
			if ($('tr',t).length == 0){
				td.wrap('<tr></tr>').parent().appendTo($('tbody',t));
			} else if (opts.rownumbers){
				td.insertAfter(t.find('td:has(div.datagrid-header-rownumber)'));
			} else {
				td.prependTo(t.find('tr:first'));
			}
		}
	},
	
	onAfterRender: function(target){
		var that = this;
		var state = $.data(target, 'datagrid');
		var dc = state.dc;
		var opts = state.options;
		var panel = $(target).datagrid('getPanel');
		
		$.fn.datagrid.defaults.view.onAfterRender.call(this, target);
		
		if (!state.onResizeColumn){
			state.onResizeColumn = opts.onResizeColumn;
		}
		if (!state.onResize){
			state.onResize = opts.onResize;
		}
		function resizeDetails(){
			var ht = dc.header2.find('table');
			var fr = ht.find('tr.datagrid-filter-row').hide();
			var ww = ht.width()-1;
			var details = dc.body2.find('>table.datagrid-btable>tbody>tr>td>div.datagrid-row-detail:visible')._outerWidth(ww);
			// var details = dc.body2.find('div.datagrid-row-detail:visible')._outerWidth(ww);
			details.find('.easyui-fluid').trigger('_resize');
			fr.show();
		}
		
		opts.onResizeColumn = function(field, width){
			if (!opts.fitColumns){
				resizeDetails();				
			}
			var rowCount = $(target).datagrid('getRows').length;
			for(var i=0; i<rowCount; i++){
				$(target).datagrid('fixDetailRowHeight', i);
			}
			
			// call the old event code
			state.onResizeColumn.call(target, field, width);
		};
		opts.onResize = function(width, height){
			if (opts.fitColumns){
				resizeDetails();				
			}
			state.onResize.call(panel, width, height);
		};
		
		this.canUpdateDetail = true;	// define if to update the detail content when 'updateRow' method is called;
		
		var footer = dc.footer1.add(dc.footer2);
		footer.find('span.datagrid-row-expander').css('visibility', 'hidden');
		$(target).datagrid('resize');

		this.bindEvents(target);
		var detail = dc.body1.add(dc.body2).find('div.datagrid-row-detail');
		detail.unbind().bind('mouseover mouseout click dblclick contextmenu scroll', function(e){
			e.stopPropagation();
		});
	}
});
$.extend($.fn.datagrid.methods, {
	fixDetailRowHeight: function(jq, index){
		return jq.each(function(){
			var opts = $.data(this, 'datagrid').options;
			if (!(opts.rownumbers || (opts.frozenColumns && opts.frozenColumns.length))){
				return;
			}
			var dc = $.data(this, 'datagrid').dc;
			var tr1 = opts.finder.getTr(this, index, 'body', 1).next();
			var tr2 = opts.finder.getTr(this, index, 'body', 2).next();
			// fix the detail row height
			if (tr2.is(':visible')){
				tr1.css('height', '');
				tr2.css('height', '');
				var height = Math.max(tr1.height(), tr2.height());
				tr1.css('height', height);
				tr2.css('height', height);
			}
			dc.body2.triggerHandler('scroll');
		});
	},
	getExpander: function(jq, index){	// get row expander object
		var opts = $.data(jq[0], 'datagrid').options;
		return opts.finder.getTr(jq[0], index).find('span.datagrid-row-expander');
	},
	// get row detail container
	getRowDetail: function(jq, index){
		var opts = $.data(jq[0], 'datagrid').options;
		var tr = opts.finder.getTr(jq[0], index, 'body', 2);
		// return tr.next().find('div.datagrid-row-detail');
		return tr.next().find('>td>div.datagrid-row-detail');
	},
	expandRow: function(jq, index){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var dc = $.data(this, 'datagrid').dc;
			var expander = $(this).datagrid('getExpander', index);
			var heitr=null;
			if (expander.hasClass('datagrid-row-expand')){
				expander.removeClass('datagrid-row-expand').addClass('datagrid-row-collapse');
				var tr1 =heitr= opts.finder.getTr(this, index, 'body', 1).next();
				var tr2 = opts.finder.getTr(this, index, 'body', 2).next();
				tr1.show();
				tr2.show();
				if (opts.onExpandRow){
					var row = $(this).datagrid('getRows')[index];
					opts.onExpandRow.call(this, index, row,tr1,tr2);
				}
			}
			var a=this;
			var cc=setInterval(function(){
					$(a).datagrid('fixDetailRowHeight', index);
			},1);
			setTimeout(function(){
				clearInterval(cc);
			},5000)
	
			
		
		});
	},
	collapseRow: function(jq, index){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var dc = $.data(this, 'datagrid').dc;
			var expander = $(this).datagrid('getExpander', index);
			if (expander.hasClass('datagrid-row-collapse')){
				expander.removeClass('datagrid-row-collapse').addClass('datagrid-row-expand');
				var tr1 = opts.finder.getTr(this, index, 'body', 1).next();
				var tr2 = opts.finder.getTr(this, index, 'body', 2).next();
				tr1.hide();
				tr2.hide();
				dc.body2.triggerHandler('scroll');
				if (opts.onCollapseRow){
					var row = $(this).datagrid('getRows')[index];
					opts.onCollapseRow.call(this, index, row);
				}
			}
		});
	}
});

$.extend($.fn.datagrid.methods, {
	subgrid: function(jq, conf){
		return jq.each(function(){
			createGrid(this, conf);

			function createGrid(target, conf, prow){
				var queryParams = $.extend({}, conf.options.queryParams||{});
				// queryParams[conf.options.foreignField] = prow ? prow[conf.options.foreignField] : undefined;
				if (prow){
					var fk = conf.options.foreignField;
					if ($.isFunction(fk)){
						$.extend(queryParams, fk.call(conf, prow));
					} else {
						queryParams[fk] = prow[fk];
					}
				}

				$(target).datagrid($.extend({}, conf.options, {
					subgrid: conf.subgrid,
					view: (conf.subgrid ? detailview : undefined),
					queryParams: queryParams,
					detailFormatter: function(index, row){
						return '<div><table class="datagrid-subgrid"></table></div>';
					},
					onExpandRow: function(index, row){
						var opts = $(this).datagrid('options');
						var rd = $(this).datagrid('getRowDetail', index);
						var dg = getSubGrid(rd);
						if (!dg.data('datagrid')){
							createGrid(dg[0], opts.subgrid, row);
						}
						rd.find('.easyui-fluid').trigger('_resize');
						setHeight(this, index);
						if (conf.options.onExpandRow){
							conf.options.onExpandRow.call(this, index, row);
						}
					},
					onCollapseRow: function(index, row){
						setHeight(this, index);
						if (conf.options.onCollapseRow){
							conf.options.onCollapseRow.call(this, index, row);
						}
					},
					onResize: function(){
						var dg = $(this).children('div.datagrid-view').children('table')
						setParentHeight(this);
					},
					onResizeColumn: function(field, width){
						setParentHeight(this);
						if (conf.options.onResizeColumn){
							conf.options.onResizeColumn.call(this, field, width);
						}
					},
					onLoadSuccess: function(data){
						setParentHeight(this);
						if (conf.options.onLoadSuccess){
							conf.options.onLoadSuccess.call(this, data);
						}
					}
				}));
			}
			function getSubGrid(rowDetail){
				var div = $(rowDetail).children('div');
				if (div.children('div.datagrid').length){
					return div.find('>div.datagrid>div.panel-body>div.datagrid-view>table.datagrid-subgrid');
				} else {
					return div.find('>table.datagrid-subgrid');
				}
			}
			function setParentHeight(target){
				var tr = $(target).closest('div.datagrid-row-detail').closest('tr').prev();
				if (tr.length){
					var index = parseInt(tr.attr('datagrid-row-index'));
					var dg = tr.closest('div.datagrid-view').children('table');
					setHeight(dg[0], index);
				}
			}
			function setHeight(target, index){
				$(target).datagrid('fixDetailRowHeight', index);
				$(target).datagrid('fixRowHeight', index);
				var tr = $(target).closest('div.datagrid-row-detail').closest('tr').prev();
				if (tr.length){
					var index = parseInt(tr.attr('datagrid-row-index'));
					var dg = tr.closest('div.datagrid-view').children('table');
					setHeight(dg[0], index);
				}
			}
		});
	},
	getParentGrid: function(jq){
		var detail = jq.closest('div.datagrid-row-detail');
		if (detail.length){
			return detail.closest('.datagrid-view').children('.datagrid-f');
		} else {
			return null;
		}
	},
	getParentRowIndex: function(jq){
		var detail = jq.closest('div.datagrid-row-detail');
		if (detail.length){
			var tr = detail.closest('tr').prev();
			return parseInt(tr.attr('datagrid-row-index'));
		} else {
			return -1;
		}
	}
});


/**
 * 声明可展开表格对象
 * @param {Object} options
 * @param {Object} param
 * @memberOf {TypeName} 
 * @return {TypeName} 
 */
$.fn.expandGridSinosoft=function(options,param){
	if (typeof options == 'string'){
			var method = $.fn.expandGridSinosoft.methods[options];
			if (method){
				return method(this, param);
			}else{
				console.log("没有此方法")
			}
	}
	options = options || {};
    return this.each(function () {
       	//给这个对象挂载一个checkedBox的对象，
        var state = $.data(this, 'expandGridSinosoft');
        if (state) {
            $.extend(state.options, options);
            expandGridSinosoftCreate(this);
        }
        else {
        	$.data(this, 'expandGridSinosoft', {
					options: $.extend({}, $.fn.expandGridSinosoft.defaults, $.fn.expandGridSinosoft.parseOptions(this), options)
				});
            expandGridSinosoftCreate(this)
        }
    })		
};

/**
 * 读取参数
 * @param {Object} target
 * @return {TypeName} 
 */
$.fn.expandGridSinosoft.parseOptions=function(target){
		if(typeof $(target).attr("par")=="undefined"){
		return new Object()
	}else{
		var t ="{"+$(target).attr("par")+"}"
		return eval("("+t+")");
	}	
}
function expandGridSinosoftCreate(tager){
	var object =$.extend(true,{},$.fn.expandGridSinosoft.defaults,$.data(tager, 'expandGridSinosoft').options);
	object.body.toolbar=object.toolbar;
	object.pagebar.pageSize=object.body.pageSize;
	object.pagebar.pageList=object.body.pageList;
	$(tager).datagrid(object.body);
	$(tager).datagrid("getPager").pagination(object.pagebar)
}
$.fn.expandGridSinosoft.defaults={
		toolbar:[],
		body:{
			remoteSort:false,
			nowrap:false,
			fit:true,
			fitColumns:true,
			view: detailview,
			rownumbers:true,
			singleSelect:true,
			pagination:true,
		    loadMsg:"加载中......",
			url:'',
			detailFormatter: function(index,row){
								return "<div style=\"\"><table id=\""+row.itemid+index+"\" style=\"width:100%\">    </table></div>";
			},
			method:'post',
			onExpandRow:function(index,row){
				alert("请重写onExpandRow方法 参数(index,row)，index索引 row 当前行数据")
			}
		},
		pagebar:{
			pageSize:5,
			pageList:[5,10,20,30,50],
			showPageList:true,
			beforePageText:"第",
			afterPageText:"页,共{pages}页",
			displayMsg:"当前记录 {from}-{to} 总共 {total} 项"
		}
}
/**
 * 这里所有expandGridSinosoft的方法
 * @param {Object} jq
 * @return {TypeName} 
 */
$.fn.expandGridSinosoft.methods={
	options:function(jq){//获取当前组件的参数
			return $.data(jq[0], 'datagridSinosoft').options;
	},
	gridoptions:function(jq){//获取grid的参数
			return $(jq[0]).datagrid("options");
	},
	pageoptions:function(jq){//获取page工具條的参数
			return $(jq[0]).datagrid("getPager").pagination("options");;
	},
	getPager:function(jq){//获取分页提示条的对象
		return $(jq[0]).datagrid("getPager");
	},
	getPanel:function(jq){//返回面板对象。
		return $(jq[0]).datagrid("getPanel");
	},
	getColumnFields:function(jq,param){//返回列字段。如果设置了frozen属性为true，将返回固定列的字段名。
		return $(jq[0]).datagrid("getColumnFields",param);
	},
	getColumnOption:function(jq,param){//返回指定列属性。
		return $(jq[0]).datagrid("getColumnOption",param);
	},
	resize:function(jq,param){//做调整和布局。
		return $(jq[0]).datagrid("resize",param);
	},
	load:function(jq,param){//加载和显示第一页的所有行。如果指定了'param'，它将取代'queryParams'属性。通常可以通过传递一些参数执行一次查询，通过调用这个方法从服务器加载新数据。
		return $(jq[0]).datagrid("load",param);
	},
	
	reload:function(jq,param){//重载行。等同于'load'方法，但是它将保持在当前页。
		return $(jq[0]).datagrid("reload",param);
	},
	reloadFooter:function(jq,param){//重载页脚行。
		return $(jq[0]).datagrid("reloadFooter",param);
	},
	loading:function(jq,param){//隐藏载入状态。
		return $(jq[0]).datagrid("loading");
	},
	loaded:function(jq,param){//使列自动展开/收缩到合适的DataGrid宽度。
		return $(jq[0]).datagrid("loaded");
	},
	fitColumns:function(jq,field){//固定列大小。如果'field'参数未配置，所有列大小将都是固定的
		return $(jq[0]).datagrid("fitColumns",field);
	},
	fixColumnSize:function(jq,index){//固定指定列高度。如果'index'参数未配置，所有行高度都是固定的。
		return $(jq[0]).datagrid("fixColumnSize",index);
	},
	fixRowHeight:function(jq,index){//固定指定列高度。如果'index'参数未配置，所有行高度都是固定的。
		return $(jq[0]).datagrid("fixRowHeight",index);
	},
	freezeRow:function(jq,param){//冻结指定行，当DataGrid表格向下滚动的时候始终保持被冻结的行显示在顶部。
		return $(jq[0]).datagrid("freezeRow",param);
	},
	autoSizeColumn:function(jq,param){//自动调整列宽度以适应内容。
		return $(jq[0]).datagrid("autoSizeColumn",param);
	},
	loadData:function(jq,param){//加载本地数据，旧的行将被移除。
		return $(jq[0]).datagrid("loadData",param);
	},
	getData:function(jq){//返回加载完毕后的数据。
		return $(jq[0]).datagrid("getData");
	},
	getRows:function(jq){//返回当前页的所有行。
		return $(jq[0]).datagrid("getRows");
	},
	getFooterRows:function(jq){//返回页脚行。
		return $(jq[0]).datagrid("getFooterRows");
	},
	getindex:function(jq,row){//返回指定行的索引号，该行的参数可以是一行记录或一个ID字段值。
		return $(jq[0]).datagrid("getindex",row);
	},
	getChecked:function(jq){//在复选框呗选中的时候返回所有行
		return $(jq[0]).datagrid("getChecked");
	},
	getSelected:function(jq){//返回第一个被选中的行或如果没有选中的行则返回null。
		return $(jq[0]).datagrid("getSelected");
	},
	getSelections:function(jq){//返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
		return $(jq[0]).datagrid("getSelections");
	},
	clearSelections:function(jq){//清除所有选择的行。
		return $(jq[0]).datagrid("clearSelections");
	},
	clearChecked:function(jq){//清除所有勾选的行
		return $(jq[0]).datagrid("clearChecked");
	},
	scrollTo:function(jq,index){//滚动到指定的行。
		return $(jq[0]).datagrid("scrollTo",index);
	},
	highlightRow:function(jq,index){//高亮一行
		return $(jq[0]).datagrid("highlightRow",index);
	},
	selectAll:function(jq){//选择当前页中所有的行。
		return $(jq[0]).datagrid("selectAll");
	},
	unselectAll:function(jq){//取消选择所有当前页中所有的行。
		return $(jq[0]).datagrid("unselectAll");
	},
	selectRow:function(jq,index){//选择一行，行索引从0开始。
		return $(jq[0]).datagrid("selectRow",index);
	},
	selectRecord:function(jq,idValue){//通过ID值参数选择一行。
		return $(jq[0]).datagrid("selectRecord",idValue);
	},
	unselectRow:function(jq,index){//取消选择一行。
		return $(jq[0]).datagrid("unselectRow",index);
	},
	checkAll:function(jq){//勾选当前页中的所有行
		return $(jq[0]).datagrid("checkAll");
	},
	uncheckAll:function(jq){//取消勾选当前页中的所有行
		return $(jq[0]).datagrid("uncheckAll");
	},
	checkRow:function(jq,index){//勾选一行，行索引从0开始
		return $(jq[0]).datagrid("checkRow",index);
	},
	uncheckRow:function(jq,index){//取消勾选一行，行索引从0开始。
		return $(jq[0]).datagrid("uncheckRow",index);
	},
	beginEdit:function(jq,index){//开始编辑行。
		return $(jq[0]).datagrid("beginEdit",index);
	},
	endEdit:function(jq,index){//结束编辑行。
		return $(jq[0]).datagrid("endEdit");
	},
	cancelEdit:function(jq,index){//取消编辑行。
		return $(jq[0]).datagrid("cancelEdit",index);
	},
	getEditors:function(jq,index){//获取指定行的编辑器。每个编辑器都有以下属性：actions：编辑器可以执行的动作，同编辑器定义。target：目标编辑器的jQuery对象。field：字段名称。type：编辑器类型，比如：'text','combobox','datebox'等。
		return $(jq[0]).datagrid("getEditors",index);
	},
	getEditor:function(jq,options){//获取指定编辑器，options包含2个属性：index：行索引。field：字段名称。 
		return $(jq[0]).datagrid("getEditor",options);
	},
	refreshRow:function(jq,index){//刷新行。
		return $(jq[0]).datagrid("refreshRow",index);
	},
	validateRow:function(jq,index){//验证指定的行，当验证有效的时候返回true。
		return $(jq[0]).datagrid("validateRow",index);
	},
	updateRow:function(jq,param){//更新指定行，参数包含下列属性：index：执行更新操作的行索引。row：更新行的新数据。
		return $(jq[0]).datagrid("updateRow",param);
	},
	appendRow:function(jq,row){//追加一个新行。新行将被添加到最后的位置。 
		return $(jq[0]).datagrid("appendRow",row);
	},
	insertRow:function(jq,param){//插入一个新行，参数包括一下属性：index：要插入的行索引，如果该索引值未定义，则追加新行。row：行数据。
		return $(jq[0]).datagrid("insertRow",param);
	},
	deleteRow:function(jq,index){//删除行。
		return $(jq[0]).datagrid("deleteRow",index);
	},
	getChanges:function(jq,type){//从上一次的提交获取改变的所有行。类型参数指明用哪些类型改变的行，可以使用的值有：inserted,deleted,updated等。当类型参数未配置的时候返回所有改变的行
		return $(jq[0]).datagrid("getChanges",type);
	},
	acceptChanges:function(jq){//提交所有从加载或者上一次调用acceptChanges函数后更改的数据。
		return $(jq[0]).datagrid("acceptChanges");
	},
	
	rejectChanges:function(jq){//回滚所有从创建或者上一次调用acceptChanges函数后更改的数据。
		return $(jq[0]).datagrid("rejectChanges");
	},
	mergeCells:function(jq,options){//合并单元格，options包含以下属性：index：行索引。field：字段名称。rowspan：合并的行数。colspan：合并的列数
		return $(jq[0]).datagrid("mergeCells",options);
	},
	showColumn:function(jq,field){//显示指定的列。
		return $(jq[0]).datagrid("showColumn",field);
	},
	hideColumn:function(jq,field){//隐藏指定的列。
		return $(jq[0]).datagrid("hideColumn",field);
	},
	sort:function(jq,param){//排序datagrid表格
		return $(jq[0]).datagrid("sort",param);
	},
	loading:function(jq){//提醒分页控件正在加载中。
		return $(jq[0]).datagrid("getPager").pagination("loading");
	},
	loaded:function(jq){//提醒分页控件加载完成。
		return$(jq[0]).datagrid("getPager").pagination("loaded");
	},
	refresh:function(jq,options){//刷新并显示分页栏信息
		return $(jq[0]).datagrid("getPager").pagination("refresh",options);
	},
	select:function(jq,page){//选择一个新页面，页面索引从1开始。
		return $(jq[0]).datagrid("getPager").pagination("select",page);
	},
	fixDetailRowHeight:function(jq,index){//修复明细行高度
			return $(jq[0]).datagrid("fixDetailRowHeight",index);
	},
	getExpander:function(jq,index){//修复明细行高度
		return $(jq[0]).datagrid("getExpander",index);
	},
	getRowDetail:function(jq,index){//修复明细行高度
		return $(jq[0]).datagrid("getRowDetail",index);
	},
	expandRow:function(jq,index){//修复明细行高度
		return $(jq[0]).datagrid("expandRow",index);
	},
	collapseRow:function(jq,index){//修复明细行高度
		return $(jq[0]).datagrid("collapseRow",index);
	}
	
	
}


$.extend($.fn.datagrid.defaults, {
	groupHeight: 25,
	expanderWidth: 30,
	groupStyler: function(value,rows){return ''}
});

var groupview = $.extend({}, $.fn.datagrid.defaults.view, {
	render: function(target, container, frozen){
		var table = [];
		var groups = this.groups;
		for(var i=0; i<groups.length; i++){
			table.push(this.renderGroup.call(this, target, i, groups[i], frozen));
		}
		$(container).html(table.join(''));
	},
	
	renderGroup: function(target, groupIndex, group, frozen){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var fields = $(target).datagrid('getColumnFields', frozen);
		var hasFrozen = opts.frozenColumns && opts.frozenColumns.length;

		if (frozen){
			if (!(opts.rownumbers || hasFrozen)){
				return '';
			}
		}
		
		var table = [];

		var css = opts.groupStyler.call(target, group.value, group.rows);
		var cs = parseCss(css, 'datagrid-group');
		table.push('<div group-index=' + groupIndex + ' ' + cs + '>');
		if ((frozen && (opts.rownumbers || opts.frozenColumns.length)) ||
				(!frozen && !(opts.rownumbers || opts.frozenColumns.length))){
			table.push('<span class="datagrid-group-expander">');
			table.push('<span class="datagrid-row-expander datagrid-row-collapse">&nbsp;</span>');
			table.push('</span>');
		}
		if ((frozen && hasFrozen) || (!frozen)){
			table.push('<span class="datagrid-group-title">');
			table.push(opts.groupFormatter.call(target, group.value, group.rows));
			table.push('</span>');
		}
		table.push('</div>');
		
		table.push('<table class="datagrid-btable" cellspacing="0" cellpadding="0" border="0"><tbody>');
		var index = group.startIndex;
		for(var j=0; j<group.rows.length; j++) {
			var css = opts.rowStyler ? opts.rowStyler.call(target, index, group.rows[j]) : '';
			var classValue = '';
			var styleValue = '';
			if (typeof css == 'string'){
				styleValue = css;
			} else if (css){
				classValue = css['class'] || '';
				styleValue = css['style'] || '';
			}
			
			var cls = 'class="datagrid-row ' + (index % 2 && opts.striped ? 'datagrid-row-alt ' : ' ') + classValue + '"';
			var style = styleValue ? 'style="' + styleValue + '"' : '';
			var rowId = state.rowIdPrefix + '-' + (frozen?1:2) + '-' + index;
			table.push('<tr id="' + rowId + '" datagrid-row-index="' + index + '" ' + cls + ' ' + style + '>');
			table.push(this.renderRow.call(this, target, fields, frozen, index, group.rows[j]));
			table.push('</tr>');
			index++;
		}
		table.push('</tbody></table>');
		return table.join('');

		function parseCss(css, cls){
			var classValue = '';
			var styleValue = '';
			if (typeof css == 'string'){
				styleValue = css;
			} else if (css){
				classValue = css['class'] || '';
				styleValue = css['style'] || '';
			}
			return 'class="' + cls + (classValue ? ' '+classValue : '') + '" ' +
					'style="' + styleValue + '"';
		}
	},
	
	bindEvents: function(target){
		var state = $.data(target, 'datagrid');
		var dc = state.dc;
		var body = dc.body1.add(dc.body2);
		var clickHandler = ($.data(body[0],'events')||$._data(body[0],'events')).click[0].handler;
		body.unbind('click').bind('click', function(e){
			var tt = $(e.target);
			var expander = tt.closest('span.datagrid-row-expander');
			if (expander.length){
				var gindex = expander.closest('div.datagrid-group').attr('group-index');
				if (expander.hasClass('datagrid-row-collapse')){
					$(target).datagrid('collapseGroup', gindex);
				} else {
					$(target).datagrid('expandGroup', gindex);
				}
			} else {
				clickHandler(e);
			}
			e.stopPropagation();
		});
	},
	
	onBeforeRender: function(target, rows){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		
		initCss();
		
		var groups = [];
		for(var i=0; i<rows.length; i++){
			var row = rows[i];
			var group = getGroup(row[opts.groupField]);
			if (!group){
				group = {
					value: row[opts.groupField],
					rows: [row]
				};
				groups.push(group);
			} else {
				group.rows.push(row);
			}
		}
		
		var index = 0;
		var newRows = [];
		for(var i=0; i<groups.length; i++){
			var group = groups[i];
			group.startIndex = index;
			index += group.rows.length;
			newRows = newRows.concat(group.rows);
		}
		
		state.data.rows = newRows;
		this.groups = groups;
		
		var that = this;
		setTimeout(function(){
			that.bindEvents(target);
		},0);
		
		function getGroup(value){
			for(var i=0; i<groups.length; i++){
				var group = groups[i];
				if (group.value == value){
					return group;
				}
			}
			return null;
		}
		function initCss(){
			if (!$('#datagrid-group-style').length){
				$('head').append(
					'<style id="datagrid-group-style">' +
					'.datagrid-group{height:'+opts.groupHeight+'px;overflow:hidden;font-weight:bold;border-bottom:1px solid #ccc;white-space:nowrap;word-break:normal;}' +
					'.datagrid-group-title,.datagrid-group-expander{display:inline-block;vertical-align:bottom;height:100%;line-height:'+opts.groupHeight+'px;padding:0 4px;}' +
					'.datagrid-group-title{position:relative;}' +
					'.datagrid-group-expander{width:'+opts.expanderWidth+'px;text-align:center;padding:0}' +
					'.datagrid-row-expander{margin:'+Math.floor((opts.groupHeight-16)/2)+'px 0;display:inline-block;width:16px;height:16px;cursor:pointer}' +
					'</style>'
				);
			}
		}
	},
	onAfterRender: function(target){
		$.fn.datagrid.defaults.view.onAfterRender.call(this, target);

		var view = this;
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		if (!state.onResizeColumn){
			state.onResizeColumn = opts.onResizeColumn;
		}
		if (!state.onResize){
			state.onResize = opts.onResize;
		}
		opts.onResizeColumn = function(field, width){
			view.resizeGroup(target);
			state.onResizeColumn.call(target, field, width);
		}
		opts.onResize = function(width, height){
			view.resizeGroup(target);		
			state.onResize.call($(target).datagrid('getPanel')[0], width, height);
		}
		view.resizeGroup(target);
	}
});

$.extend($.fn.datagrid.methods, {
	groups:function(jq){
		return jq.datagrid('options').view.groups;
	},
    expandGroup:function(jq, groupIndex){
        return jq.each(function(){
            var view = $.data(this, 'datagrid').dc.view;
            var group = view.find(groupIndex!=undefined ? 'div.datagrid-group[group-index="'+groupIndex+'"]' : 'div.datagrid-group');
            var expander = group.find('span.datagrid-row-expander');
            if (expander.hasClass('datagrid-row-expand')){
                expander.removeClass('datagrid-row-expand').addClass('datagrid-row-collapse');
                group.next('table').show();
            }
            $(this).datagrid('fixRowHeight');
        });
    },
    collapseGroup:function(jq, groupIndex){
        return jq.each(function(){
            var view = $.data(this, 'datagrid').dc.view;
            var group = view.find(groupIndex!=undefined ? 'div.datagrid-group[group-index="'+groupIndex+'"]' : 'div.datagrid-group');
            var expander = group.find('span.datagrid-row-expander');
            if (expander.hasClass('datagrid-row-collapse')){
                expander.removeClass('datagrid-row-collapse').addClass('datagrid-row-expand');
                group.next('table').hide();
            }
            $(this).datagrid('fixRowHeight');
        });
    },
    scrollToGroup: function(jq, groupIndex){
    	return jq.each(function(){
			var state = $.data(this, 'datagrid');
			var dc = state.dc;
			var grow = dc.body2.children('div.datagrid-group[group-index="'+groupIndex+'"]');
			if (grow.length){
				var groupHeight = grow.outerHeight();
				var headerHeight = dc.view2.children('div.datagrid-header')._outerHeight();
				var frozenHeight = dc.body2.outerHeight(true) - dc.body2.outerHeight();
				var top = grow.position().top - headerHeight - frozenHeight;
				if (top < 0){
					dc.body2.scrollTop(dc.body2.scrollTop() + top);
				} else if (top + groupHeight > dc.body2.height() - 18){
					dc.body2.scrollTop(dc.body2.scrollTop() + top + groupHeight - dc.body2.height() + 18);
				}
			}
    	});
    }
});

$.extend(groupview, {
	refreshGroupTitle: function(target, groupIndex){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var dc = state.dc;
		var group = this.groups[groupIndex];
		var span = dc.body1.add(dc.body2).children('div.datagrid-group[group-index=' + groupIndex + ']').find('span.datagrid-group-title');
		span.html(opts.groupFormatter.call(target, group.value, group.rows));
	},
	resizeGroup: function(target, groupIndex){
		var state = $.data(target, 'datagrid');
		var dc = state.dc;
		var ht = dc.header2.find('table');
		var fr = ht.find('tr.datagrid-filter-row').hide();
		var ww = ht.width();
		if (groupIndex == undefined){
			var groupHeader = dc.body2.children('div.datagrid-group');
		} else {
			var groupHeader = dc.body2.children('div.datagrid-group[group-index=' + groupIndex + ']');
		}
		groupHeader._outerWidth(ww);
		var opts = state.options;
		if (opts.frozenColumns && opts.frozenColumns.length){
			var width = dc.view1.width() - opts.expanderWidth;
			var isRtl = dc.view1.css('direction').toLowerCase()=='rtl';
			groupHeader.find('.datagrid-group-title').css(isRtl?'right':'left', -width+'px');
		}
		fr.show();
	},

	insertRow: function(target, index, row){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var dc = state.dc;
		var group = null;
		var groupIndex;
		
		if (!state.data.rows.length){
			$(target).datagrid('loadData', [row]);
			return;
		}
		
		for(var i=0; i<this.groups.length; i++){
			if (this.groups[i].value == row[opts.groupField]){
				group = this.groups[i];
				groupIndex = i;
				break;
			}
		}
		if (group){
			if (index == undefined || index == null){
				index = state.data.rows.length;
			}
			if (index < group.startIndex){
				index = group.startIndex;
			} else if (index > group.startIndex + group.rows.length){
				index = group.startIndex + group.rows.length;
			}
			$.fn.datagrid.defaults.view.insertRow.call(this, target, index, row);
			
			if (index >= group.startIndex + group.rows.length){
				_moveTr(index, true);
				_moveTr(index, false);
			}
			group.rows.splice(index - group.startIndex, 0, row);
		} else {
			group = {
				value: row[opts.groupField],
				rows: [row],
				startIndex: state.data.rows.length
			}
			groupIndex = this.groups.length;
			dc.body1.append(this.renderGroup.call(this, target, groupIndex, group, true));
			dc.body2.append(this.renderGroup.call(this, target, groupIndex, group, false));
			this.groups.push(group);
			state.data.rows.push(row);
		}
		
		this.refreshGroupTitle(target, groupIndex);
		this.resizeGroup(target);
		
		function _moveTr(index,frozen){
			var serno = frozen?1:2;
			var prevTr = opts.finder.getTr(target, index-1, 'body', serno);
			var tr = opts.finder.getTr(target, index, 'body', serno);
			tr.insertAfter(prevTr);
		}
	},
	
	updateRow: function(target, index, row){
		var opts = $.data(target, 'datagrid').options;
		$.fn.datagrid.defaults.view.updateRow.call(this, target, index, row);
		var tb = opts.finder.getTr(target, index, 'body', 2).closest('table.datagrid-btable');
		var groupIndex = parseInt(tb.prev().attr('group-index'));
		this.refreshGroupTitle(target, groupIndex);
	},
	
	deleteRow: function(target, index){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var dc = state.dc;
		var body = dc.body1.add(dc.body2);
		
		var tb = opts.finder.getTr(target, index, 'body', 2).closest('table.datagrid-btable');
		var groupIndex = parseInt(tb.prev().attr('group-index'));
		
		$.fn.datagrid.defaults.view.deleteRow.call(this, target, index);
		
		var group = this.groups[groupIndex];
		if (group.rows.length > 1){
			group.rows.splice(index-group.startIndex, 1);
			this.refreshGroupTitle(target, groupIndex);
		} else {
			body.children('div.datagrid-group[group-index='+groupIndex+']').remove();
			for(var i=groupIndex+1; i<this.groups.length; i++){
				body.children('div.datagrid-group[group-index='+i+']').attr('group-index', i-1);
			}
			this.groups.splice(groupIndex, 1);
		}
		
		var index = 0;
		for(var i=0; i<this.groups.length; i++){
			var group = this.groups[i];
			group.startIndex = index;
			index += group.rows.length;
		}
	}
});

/**
 * 声明可分组表格对象
 * @param {Object} options
 * @param {Object} param
 * @memberOf {TypeName} 
 * @return {TypeName} 
 */
$.fn.groupGridSinosft=function(options,param){
	if (typeof options == 'string'){
			var method = $.fn.groupGridSinosft.methods[options];
			if (method){
				return method(this, param);
			}else{
				console.log("没有此方法")
			}
	}
	options = options || {};
    return this.each(function () {
       	//给这个对象挂载一个checkedBox的对象，
        var state = $.data(this, 'groupGridSinosft');
        if (state) {
            $.extend(state.options, options);
            groupGridSinosftCreate(this);
        }
        else {
        	$.data(this, 'groupGridSinosft', {
					options: $.extend({}, $.fn.groupGridSinosft.defaults, $.fn.groupGridSinosft.parseOptions(this), options)
				});
            groupGridSinosftCreate(this)
        }
    })		
};

/**
 * 读取参数
 * @param {Object} target
 * @return {TypeName} 
 */
$.fn.groupGridSinosft.parseOptions=function(target){
	if(typeof $(target).attr("par")=="undefined"){
		return new Object()
	}else{
		var t ="{"+$(target).attr("par")+"}"
		return eval("("+t+")");
	}	
}
function groupGridSinosftCreate(tager){
	var object =$.extend(true,{},$.fn.groupGridSinosft.defaults,$.data(tager, 'groupGridSinosft').options);
	object.body.toolbar=object.toolbar;
	object.pagebar.pageSize=object.body.pageSize;
	object.pagebar.pageList=object.body.pageList;
	$(tager).datagrid(object.body);
	$(tager).datagrid("getPager").pagination(object.pagebar)
}
$.fn.groupGridSinosft.defaults={
		toolbar:[],
		body:{
			remoteSort:false,
			nowrap:false,
			fit:true,
			fitColumns:true,
			view: groupview,
			groupFormatter:function(value, rows){
				return value + ' - ' + rows.length + ' 个  重写groupFormatter 参数 (value, rows)  ';
			},
			rownumbers:true,
			singleSelect:true,
			pagination:true,
		    loadMsg:"加载中......",
			url:'',
			method:'post'
		
			
		},
		pagebar:{
			pageSize:5,
			pageList:[5,10,20,30,50],
			showPageList:true,
			beforePageText:"第",
			afterPageText:"页,共{pages}页",
			displayMsg:"当前记录 {from}-{to} 总共 {total} 项"
		}
}
/**
 * 这里所有datagridSinosoft的方法
 * @param {Object} jq
 * @return {TypeName} 
 */
$.fn.groupGridSinosft.methods={
	options:function(jq){//获取当前组件的参数
			return $.data(jq[0], 'datagridSinosoft').options;
	},
	gridoptions:function(jq){//获取grid的参数
			return $(jq[0]).datagrid("options");
	},
	pageoptions:function(jq){//获取page工具條的参数
			return $(jq[0]).datagrid("getPager").pagination("options");;
	},
	getPager:function(jq){//获取分页提示条的对象
		return $(jq[0]).datagrid("getPager");
	},
	getPanel:function(jq){//返回面板对象。
		return $(jq[0]).datagrid("getPanel");
	},
	getColumnFields:function(jq,param){//返回列字段。如果设置了frozen属性为true，将返回固定列的字段名。
		return $(jq[0]).datagrid("getColumnFields",param);
	},
	getColumnOption:function(jq,param){//返回指定列属性。
		return $(jq[0]).datagrid("getColumnOption",param);
	},
	resize:function(jq,param){//做调整和布局。
		return $(jq[0]).datagrid("resize",param);
	},
	load:function(jq,param){//加载和显示第一页的所有行。如果指定了'param'，它将取代'queryParams'属性。通常可以通过传递一些参数执行一次查询，通过调用这个方法从服务器加载新数据。
		return $(jq[0]).datagrid("load",param);
	},
	
	reload:function(jq,param){//重载行。等同于'load'方法，但是它将保持在当前页。
		return $(jq[0]).datagrid("reload",param);
	},
	reloadFooter:function(jq,param){//重载页脚行。
		return $(jq[0]).datagrid("reloadFooter",param);
	},
	loading:function(jq,param){//隐藏载入状态。
		return $(jq[0]).datagrid("loading");
	},
	loaded:function(jq,param){//使列自动展开/收缩到合适的DataGrid宽度。
		return $(jq[0]).datagrid("loaded");
	},
	fitColumns:function(jq,field){//固定列大小。如果'field'参数未配置，所有列大小将都是固定的
		return $(jq[0]).datagrid("fitColumns",field);
	},
	fixColumnSize:function(jq,index){//固定指定列高度。如果'index'参数未配置，所有行高度都是固定的。
		return $(jq[0]).datagrid("fixColumnSize",index);
	},
	fixRowHeight:function(jq,index){//固定指定列高度。如果'index'参数未配置，所有行高度都是固定的。
		return $(jq[0]).datagrid("fixRowHeight",index);
	},
	freezeRow:function(jq,param){//冻结指定行，当DataGrid表格向下滚动的时候始终保持被冻结的行显示在顶部。
		return $(jq[0]).datagrid("freezeRow",param);
	},
	autoSizeColumn:function(jq,param){//自动调整列宽度以适应内容。
		return $(jq[0]).datagrid("autoSizeColumn",param);
	},
	loadData:function(jq,param){//加载本地数据，旧的行将被移除。
		return $(jq[0]).datagrid("loadData",param);
	},
	getData:function(jq){//返回加载完毕后的数据。
		return $(jq[0]).datagrid("getData");
	},
	getRows:function(jq){//返回当前页的所有行。
		return $(jq[0]).datagrid("getRows");
	},
	getFooterRows:function(jq){//返回页脚行。
		return $(jq[0]).datagrid("getFooterRows");
	},
	getindex:function(jq,row){//返回指定行的索引号，该行的参数可以是一行记录或一个ID字段值。
		return $(jq[0]).datagrid("getindex",row);
	},
	getChecked:function(jq){//在复选框呗选中的时候返回所有行
		return $(jq[0]).datagrid("getChecked");
	},
	getSelected:function(jq){//返回第一个被选中的行或如果没有选中的行则返回null。
		return $(jq[0]).datagrid("getSelected");
	},
	getSelections:function(jq){//返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
		return $(jq[0]).datagrid("getSelections");
	},
	clearSelections:function(jq){//清除所有选择的行。
		return $(jq[0]).datagrid("clearSelections");
	},
	clearChecked:function(jq){//清除所有勾选的行
		return $(jq[0]).datagrid("clearChecked");
	},
	scrollTo:function(jq,index){//滚动到指定的行。
		return $(jq[0]).datagrid("scrollTo",index);
	},
	highlightRow:function(jq,index){//高亮一行
		return $(jq[0]).datagrid("highlightRow",index);
	},
	selectAll:function(jq){//选择当前页中所有的行。
		return $(jq[0]).datagrid("selectAll");
	},
	unselectAll:function(jq){//取消选择所有当前页中所有的行。
		return $(jq[0]).datagrid("unselectAll");
	},
	selectRow:function(jq,index){//选择一行，行索引从0开始。
		return $(jq[0]).datagrid("selectRow",index);
	},
	selectRecord:function(jq,idValue){//通过ID值参数选择一行。
		return $(jq[0]).datagrid("selectRecord",idValue);
	},
	unselectRow:function(jq,index){//取消选择一行。
		return $(jq[0]).datagrid("unselectRow",index);
	},
	checkAll:function(jq){//勾选当前页中的所有行
		return $(jq[0]).datagrid("checkAll");
	},
	uncheckAll:function(jq){//取消勾选当前页中的所有行
		return $(jq[0]).datagrid("uncheckAll");
	},
	checkRow:function(jq,index){//勾选一行，行索引从0开始
		return $(jq[0]).datagrid("checkRow",index);
	},
	uncheckRow:function(jq,index){//取消勾选一行，行索引从0开始。
		return $(jq[0]).datagrid("uncheckRow",index);
	},
	beginEdit:function(jq,index){//开始编辑行。
		return $(jq[0]).datagrid("beginEdit",index);
	},
	endEdit:function(jq,index){//结束编辑行。
		return $(jq[0]).datagrid("endEdit");
	},
	cancelEdit:function(jq,index){//取消编辑行。
		return $(jq[0]).datagrid("cancelEdit",index);
	},
	getEditors:function(jq,index){//获取指定行的编辑器。每个编辑器都有以下属性：actions：编辑器可以执行的动作，同编辑器定义。target：目标编辑器的jQuery对象。field：字段名称。type：编辑器类型，比如：'text','combobox','datebox'等。
		return $(jq[0]).datagrid("getEditors",index);
	},
	getEditor:function(jq,options){//获取指定编辑器，options包含2个属性：index：行索引。field：字段名称。 
		return $(jq[0]).datagrid("getEditor",options);
	},
	refreshRow:function(jq,index){//刷新行。
		return $(jq[0]).datagrid("refreshRow",index);
	},
	validateRow:function(jq,index){//验证指定的行，当验证有效的时候返回true。
		return $(jq[0]).datagrid("validateRow",index);
	},
	updateRow:function(jq,param){//更新指定行，参数包含下列属性：index：执行更新操作的行索引。row：更新行的新数据。
		return $(jq[0]).datagrid("updateRow",param);
	},
	appendRow:function(jq,row){//追加一个新行。新行将被添加到最后的位置。 
		return $(jq[0]).datagrid("appendRow",row);
	},
	insertRow:function(jq,param){//插入一个新行，参数包括一下属性：index：要插入的行索引，如果该索引值未定义，则追加新行。row：行数据。
		return $(jq[0]).datagrid("insertRow",param);
	},
	deleteRow:function(jq,index){//删除行。
		return $(jq[0]).datagrid("deleteRow",index);
	},
	getChanges:function(jq,type){//从上一次的提交获取改变的所有行。类型参数指明用哪些类型改变的行，可以使用的值有：inserted,deleted,updated等。当类型参数未配置的时候返回所有改变的行
		return $(jq[0]).datagrid("getChanges",type);
	},
	acceptChanges:function(jq){//提交所有从加载或者上一次调用acceptChanges函数后更改的数据。
		return $(jq[0]).datagrid("acceptChanges");
	},
	
	rejectChanges:function(jq){//回滚所有从创建或者上一次调用acceptChanges函数后更改的数据。
		return $(jq[0]).datagrid("rejectChanges");
	},
	mergeCells:function(jq,options){//合并单元格，options包含以下属性：index：行索引。field：字段名称。rowspan：合并的行数。colspan：合并的列数
		return $(jq[0]).datagrid("mergeCells",options);
	},
	showColumn:function(jq,field){//显示指定的列。
		return $(jq[0]).datagrid("showColumn",field);
	},
	hideColumn:function(jq,field){//隐藏指定的列。
		return $(jq[0]).datagrid("hideColumn",field);
	},
	sort:function(jq,param){//排序datagrid表格
		return $(jq[0]).datagrid("sort",param);
	},
	loading:function(jq){//提醒分页控件正在加载中。
		return $(jq[0]).datagrid("getPager").pagination("loading");
	},
	loaded:function(jq){//提醒分页控件加载完成。
		return$(jq[0]).datagrid("getPager").pagination("loaded");
	},
	refresh:function(jq,options){//刷新并显示分页栏信息
		return $(jq[0]).datagrid("getPager").pagination("refresh",options);
	},
	select:function(jq,page){//选择一个新页面，页面索引从1开始。
		return $(jq[0]).datagrid("getPager").pagination("select",page);
	},
	expandGroup:function(jq,index){
		return $(jq[0]).datagrid("expandGroup",index);
	},
	collapseGroup:function(jq,index){
		return $(jq[0]).datagrid("collapseGroup",index);
	},
	collapseGroupAll:function(jq){
		return $(jq[0]).datagrid("collapseGroup");
	},
	expandGroupAll:function(jq){
		return $(jq[0]).datagrid("expandGroup");
	}
	
}

$.fn.systemCodeCombobox=function(options,param){
if (typeof options == 'string'){
	var method = $.fn.systemCodeCombobox.methods[options];
	if (method){
		return method(this, param);
	}else{
		console.log("没有此方法")
	}
}
var opt={
	valueField:'code',
	textField:'name',
	panelHeight:"auto",
	panelMaxHeight:"200",
	url:'/system/system-authority-info!getSystemAuthorityCodeList.action',
	queryParams:{
		codeType:'XFFS'
	}
}
var str="{"+this.attr("par")+"}";
var onv={};
if(str!="{undefined}"){
   onv=eval("("+str+")");
}
options=$.extend(true,{},opt,options,onv);
return this.each(function () {
		$(this).combobox(options)
	})		
};
$.fn.systemCodeCombobox.methods=$.fn.combobox.methods;