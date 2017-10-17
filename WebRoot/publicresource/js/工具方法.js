新增的方法:
setdisabled //按钮设置禁止使用和启动使用
$.f("a","button").setdisabled(true);
isdisabled //判断按钮的状态
var a=$.f("a","button").isdisabled();   //true是禁用的状态，灰色


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
$.f("a","button").setdisabledEvent(true,function(){
	
	//点击会发生地事件
	
})


getDept  //获取的当前节点的深度
用法:dept=$.f("ul","yearroundtree").tree("getDept", node.target);
//sinosoftUI  $.SUI 命名空间
用法:$.sinosoftUI("xxx.xxx.xx") //或 $.SUI(xxx.xxx.xx)
log // 控制台输出 不兼容ie9
用法:$.log("控制台输出")
findName 	//根据name 查询dom对象的jquery形式  参数1 标签类型  参数2 属性值 参数3 属性名默认name
用法:  $.findName("div","value","attrname").findName("div","value","attrname");
f  //findidFlag 根据Flag 查询dom对象的jquery形式 参数1 标签类型  参数2 属性值 参数3 属性名默认idFlag
用法:	$.f("div","value","attrname").f("div","value","attrname");
copyObj //克隆一个对象
用法:	$.copyObj({a:"xxx",b:"xxxad"})
getLabelText  //找到一个dom节点下的选中单选框的显示值 	参数1  单选框的group
用法:
	$.f("div","window").getLabelText("groupname")
setChecked  //让一个单选框选中 	参数1  单选框的group 参数1 单选框的值
用法:
	$.f("div","window").setChecked("groupname","value")	
getLabelVal  //找到一个dom节点下的选中单选框的隐藏值 	参数1  单选框的group
用法:
	$.f("div","window").getLabelVal("groupname");
setRadioBoxNull // 所有的单选框都置为不选状态  参数1  单选框的group 可以不传参数
	$.f("div","window").setRadioBoxNull("groupname");$.f("div","window").setRadioBoxNull();
findtabopt// 或许当前选中的tab的参数值
用法:
	$.findtabopt();
findtabtoPanel //获取当前选中tab的panel dom节点
用法:
	$.findtabtoPanel();
