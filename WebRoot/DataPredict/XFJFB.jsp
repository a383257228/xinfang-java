<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title></title>
    <script src="<%=basePath%>/publicresource/js/sinosoftUI.js"></script>
    <script src="<%=basePath%>/publicresource/js/g2.js"></script>
  </head>
  <body>
    <div id="c1"></div>
    <script>
      var data = [{country:'上海市',cost:96},{country:'徐汇区',cost:121},{country:'长宁区',cost:100},{country:'普陀区',cost:111},
                  {country:'虹口区',cost:102},{country:'杨浦区',cost:124},{country:'黄浦区',cost:123},{country:'静安区',cost:111},
                  {country:'奉贤区',cost:123},{country:'松江区',cost:109},{country:'崇明区',cost:115},{country:'金山区',cost:99},
                  {country:'浦东新区',cost:91},{country:'卢湾区',cost:87},{country:'闵行区',cost:125},{country:'嘉定区',cost:130},
                  {country:'国资委系统',cost:109},{country:'松江区',cost:123},{country:'南汇区',cost:91},{country:'质监局系统',cost:83},
                  {country:'金融系统',cost:101},{country:'宣传系统',cost:116},{country:'司法系统',cost:111},{country:'东航系统',cost:107},
                  {country:'社会系统',cost:101},{country:'科技系统',cost:116},{country:'政法系统',cost:111},{country:'建设和交通系统',cost:107},
                  {country:'经济信息系统',cost:91},{country:'世博系统',cost:87},{country:'规土局系统',cost:125},{country:'民防办系统',cost:130},
                  {country:'合作系统',cost:109},{country:'高院系统',cost:123},{country:'统计系统',cost:91},{country:'中国商飞系统',cost:83},
                  {country:'检察系统',cost:101},{country:'教育和卫生系统',cost:116},{country:'人力社保系统',cost:111},{country:'政府外办系统',cost:107},
                  {country:'宝钢系统',cost:101},{country:'工商系统',cost:116},{country:'旅游局系统',cost:111},{country:'市口岸办系统',cost:107}
                  ];
      var chart = new G2.Chart({
	        id: 'c1',
	        forceFit: true,
	        height: 600,
	        plotCfg: {
	          margin: [90, 430, 120, 0]
	        }
      });
      chart.source(data, {
        'cost': {
          min: 0
        }
      });
      chart.coord('polar');
      chart.axis('cost', {
        labels: null
      });
      chart.axis('country', {
        gridAlign: 'middle',
        labelOffset: 18,
        labels: {
          label: {
            textAlign: 'center'// 设置坐标轴 label 的文本对齐方向
          }
        }
      });
      chart.legend('country', {
        itemWrap: true // 图例换行，将该参数设置为 true, 默认为 false，不换行。
      });
      chart.interval().position('country*cost').color('country','RGB(0,0,255)-RGB(220,20,60)')
        .label('cost',{offset: -25,label: {textAlign: 'center', fontWeight: 'bold'}}).style({
        lineWidth: 1,
        stroke: '#fff'
      });
      chart.render();
    </script>
  </body>
</html>
