package com.sinosoft.xf.dataAnalysis.web;

import java.io.IOException;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

@SuppressWarnings("serial")
@Namespace("/xfmap")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { 
	@Result(name = CrudActionSupport.RELOAD, location = "xf-map-analysis.action", type = "redirect"),
})
public class XfMapAnalysisAction extends ActionSupport{
	/**
	 * 信访件分析地图
	 * @throws IOException
	 */
	public void XFMap() throws IOException{
		String json = "[[{name:\"金山区\"},{name:\"徐汇区\",value:50}],[{name:\"崇明区\"},{name:\"长宁区\",value:10}],[{name:\"静安区\"},{name:\"嘉定区\",value:95}],[{name:\"浦东新区\"},{name:\"嘉定区\",value:95}],[{name:\"崇明区\"},{name:\"普陀区\",value:20}],[{name:\"崇明区\"},{name:\"虹口区\",value:20}],[{name:\"金山区\"},{name:\"闵行区\",value:40}],[{name:\"崇明区\"},{name:\"松江区\",value:80}],[{name:\"金山区\"},{name:\"青浦区\",value:90}],[{name:\"金山区\"},{name:\"松江区\",value:60}],[{name:\"金山区\"},{name:\"奉贤区\",value:30}]]";
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 问题类别分析
	 * @throws IOException
	 */
	public void DataAnalysis() throws IOException{
		String json = "[{\"category\" : \"主体责任落实不力\",\"省部级\" : 160,\"厅部级\" : 290,\"县处级\" : 340}, {\"category\" : \"违规干预市场经济活动\",\"省部级\" : 250,\"厅部级\" : 150,\"县处级\" : 270}, {\"category\" : \"泄露扩散窃取私存党的秘密\",\"省部级\" : 140,\"厅部级\" : 210,\"县处级\" : 240},{\"category\" : \"违反考试录取工作规定\",\"省部级\" : 230,\"厅部级\" : 180,\"县处级\" : 310}, {\"category\" : \"其他违反工作记录行为\",\"省部级\" : 80,\"厅部级\" : 170,\"县处级\" : 280}]";
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 类别前5个月
	 * @throws IOException
	 */
	public void monthTop5() throws IOException{
		String json = "[{\"month\": \"1月\",\"top1\": \"违反政治纪律500\",\"top2\": \"违反组织纪律400\",\"top3\": \"违反廉洁纪律300\",\"top4\": \"违反群众纪律200\",\"top5\": \"违反工作纪律100\"},{\"month\": \"2月\",\"top1\": \"违反政治纪律500\",\"top2\": \"违反组织纪律400\",\"top3\": \"违反廉洁纪律300\",\"top4\": \"违反群众纪律200\",\"top5\": \"违反工作纪律100\"},{\"month\": \"3月\",\"top1\": \"违反政治纪律500\",\"top2\": \"违反组织纪律400\",\"top3\": \"违反廉洁纪律300\",\"top4\": \"违反群众纪律200\",\"top5\": \"违反工作纪律100\"},{\"month\": \"4月\",\"top1\": \"违反政治纪律500\",\"top2\": \"违反组织纪律400\",\"top3\": \"违反廉洁纪律300\",\"top4\": \"违反群众纪律200\",\"top5\": \"违反工作纪律100\"},{\"month\": \"5月\",\"top1\": \"违反政治纪律500\",\"top2\": \"违反组织纪律400\",\"top3\": \"违反廉洁纪律300\",\"top4\": \"违反群众纪律200\",\"top5\": \"违反工作纪律100\"}]";
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 问题类别前五位
	 * @throws IOException
	 */
	public void xfDataAnalysis() throws IOException{
		String json = "[{\"category\":\"公开发表危害党的言论\",\"数量\" : 160}, {\"category\" : \"在党内搞团团伙伙\",\"数量\" : 250}, {\"category\" : \"对抗组织审查\",\"数量\" : 360},{\"category\" : \"组织参加迷信活动\",\"数量\" : 140}, {\"category\" : \"妨碍党和国家方针政策实施\",\"数量\" : 230}]";
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 增量前五位
	 * @throws IOException
	 */
	public void ZLTop5() throws IOException{
		String json = "[{\"category\": \"公开发表危害党的言论\",\"数量\": 360,\"code\": \"012101\"},{\"category\": \"在党内搞团团伙伙\",\"数量\": 310,\"code\": \"012103\"},{\"category\": \"对抗组织审查\",\"数量\": 260,\"code\": \"012105\"},{\"category\": \"组织参加迷信活动\",\"数量\": 190,\"code\": \"012106\"},{\"category\": \"妨碍党和国家方针政策实施\",\"数量\": 130,\"code\": \"012104\"}]";
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 减量前五位
	 * @throws IOException
	 */
	public void JLTop5() throws IOException{
		String json = "[{\"category\": \"公开发表危害党的言论\",\"数量\": 230,\"code\": \"012101\"},{\"category\": \"在党内搞团团伙伙\",\"数量\": 190,\"code\": \"012103\"},{\"category\": \"对抗组织审查\",\"数量\": 160,\"code\": \"012105\"},{\"category\": \"组织参加迷信活动\",\"数量\": 120,\"code\": \"012106\"},{\"category\": \"妨碍党和国家方针政策实施\",\"数量\": 100,\"code\": \"012104\"}]";
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 2017年趋势分析
	 * @throws IOException
	 */
	public void QSOld() throws IOException{
		String json = "[{month: \"1月\", num: 375},{month: \"2月\", num: 200},{month: \"3月\", num: 160},{month: \"4月\", num: 250},{month: \"5月\", num: 140},{month: \"6月\", num: 230},{month: \"7月\", num: 170},{month: \"8月\", num: 204},{month: \"9月\", num: 80},{month: \"10月\", num: 150},{month: \"11月\", num: 210},{month: \"12月\", num: 150}]";
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 同比环比
	 * @throws IOException
	 */
	public void TBHB() throws IOException{
		String json = "[{\"month\":\"1月\", \"num\":0.2},{\"month\":\"2月\", \"num\":0.1},{\"month\":\"3月\", \"num\":0.3},{\"month\":\"4月\", \"num\":0.5},{\"month\":\"5月\", \"num\":0.8},{\"month\":\"6月\", \"num\":0.4},{\"month\":\"7月\", \"num\":0.6},{\"month\":\"8月\", \"num\":0.9},{\"month\":\"9月\", \"num\":0.2},{\"month\":\"10月\", \"num\":0.4},{\"month\":\"11月\", \"num\":0.7},{\"month\":\"12月\", \"num\":0.6}]";
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 同比环比
	 * @throws IOException
	 */
	public void TBHB1() throws IOException{
		String json = "[{\"month\":\"1月\", \"num\":0.3},{\"month\":\"2月\", \"num\":0.2},{\"month\":\"3月\", \"num\":0.6},{\"month\":\"4月\", \"num\":0.8},{\"month\":\"5月\", \"num\":0.5},{\"month\":\"6月\", \"num\":0.4},{\"month\":\"7月\", \"num\":0.1},{\"month\":\"8月\", \"num\":0.4},{\"month\":\"9月\", \"num\":0.7},{\"month\":\"10月\", \"num\":0.5},{\"month\":\"11月\", \"num\":0.4},{\"month\":\"12月\", \"num\":0.9}]";
		Struts2Utils.getResponse().getWriter().print(json);
	}
}
