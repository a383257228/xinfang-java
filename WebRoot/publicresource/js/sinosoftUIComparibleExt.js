$.fn.datagrid.defaults.loadFilter=function(data){
//	data.rows=data.result;
//	data.total=data.totalCount;
	return data;
}
/*
 * $.fn.tree.defaults.loadFilter=function(data){
	$(data).each(function(){
		if(this.leaf){
			this.state="open";
		}else if(typeof this.leaf=="undefined"){
			this.state="closed";
		};
		if(this.iconCls){
			this.iconCls="icon-man"
		}
	})
	return data;
}*/
