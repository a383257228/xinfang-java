$.ajaxSetup({
    contentType:"application/x-www-form-urlencoded;charset=utf-8",
    complete:function(XMLHttpRequest,textStatus){
          //通过XMLHttpRequest取得响应头，sessionstatus           
          var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); 
          if(sessionstatus=="timeout"){
               //这里怎么处理在你，这里跳转的登录页面
               window.location.replace(XMLHttpRequest.getResponseHeader("loginPath"));
       }
    }
});