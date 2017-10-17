package com.sinosoft.constants;

public class UserConstants {
	/**
	 * 码表类型
	 * GWLX->公文类型,YWWLFFL->业务外来访分类,WTXZ->问题性质,XFLB->信访类别,BLFS->办理方式,JJDX->纪检监察对象,
	 * XFFS->信访方式,SSCD->属实程度,DBLY->督办理由,JDFS->信访监督方式,DBFS->督办方式,XFLY->信访来源,JIBE->行政级别,
	 * ZZMM->政治面貌,FYRY->职业,JJCD->紧急程度,DWXZ->单位性质,DJCF->党纪处分,ZJCF->政纪处分,FYXB->性别,MMDJ->秘密等级,
	 * YESNO->是否,HAVEORNOT->有无,PSLB->批示类别,BLFSNEW->办理方式,GJZ->关键字,COND->查询条件,MATCH->匹配条件,
	 * MMSORTCODE->多媒体类型,CSLX->超时类型,NATION->民族,DBSD->图表对比时段周期,FREAK->异常行为代码,ZW->被反映人职务,
	 * BJFS->办结方式,PRTSTAT->打印状态,YQZT->延期状态,HTLX->回退类型,CHCL->初核处理结果,NETFLAG->网络举报件状态,SMBZ->署名标志,
	 * CLQK->处理情况,XFZT->当前状态,XFZTJS->当前接收状态,QUERYFLAG->查询口径,IMPORTANTFLAG->统计口径,NPC->人大代表,
	 * LEADERDISCIPLINE->一把手违纪,CPPCCMEMBER->政协委员,ENTERPRISE->企业性质,DEALTYPEBL->本委办理方式,DZGDGW->电子归档公文,
	 * YJCS->预警超时状态,YJLX->预警类型,TJBB->统计报表,CLJG->处理结果,XUELI->学历,DWJB->单位级别,ZJLX->证件类型,CHHE->初步核实 ,
	 * ZZZT->在职状态
	 */
	public enum SysCodeType{
		GWLX("GWLX"),YWWLFFL("YWWLFFL"),WTXZ("WTXZ"),XFLB("XFLB"),BLFS("BLFS"),JJDX("JJDX"),
		XFFS("XFFS"),SSCD("SSCD"),DBLY("DBLY"),JDFS("JDFS"),DBFS("DBFS"),XFLY("XFLY"),JIBE("JIBE"),
		ZZMM("ZZMM"),FYRY("FYRY"),JJCD("JJCD"),DWXZ("DWXZ"),DJCF("DJCF"),ZJCF("ZJCF"),FYXB("FYXB"),
		MMDJ("MMDJ"),YESNO("YESNO"),HAVEORNOT("HAVEORNOT"),PSLB("PSLB"),BLFSNEW("BLFSNEW"),GJZ("GJZ"),
		COND("COND"),MATCH("MATCH"),MMSORTCODE("MMSORTCODE"),CSLX("CSLX"),NATION("NATION"),DBSD("DBSD"),
		FREAK("FREAK"),ZW("ZW"),BJFS("BJFS"),PRTSTAT("PRTSTAT"),YQZT("YQZT"),HTLX("HTLX"),CHCL("CHCL"),
		NETFLAG("NETFLAG"),SMBZ("SMBZ"),CLQK("CLQK"),XFZT("XFZT"),XFZTJS("XFZTJS"),QUERYFLAG("QUERYFLAG"),
		IMPORTANTFLAG("IMPORTANTFLAG"),NPC("NPC"),LEADERDISCIPLINE("LEADERDISCIPLINE"),CPPCCMEMBER("CPPCCMEMBER"),
		ENTERPRISE("ENTERPRISE"),DEALTYPEBL("DEALTYPEBL"),DZGDGW("DZGDGW"),YJCS("YJCS"),YJLX("YJLX"),TJBB("TJBB"),
		CLJG("CLJG"),XUELI("XUELI"),DWJB("DWJB"),ZJLX("ZJLX"),CHHE("CHHE"),ZZZT("ZZZT");
		private String v;
		SysCodeType(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * DeptName 部门名称
	 */
	public enum DeptName{
		AGS("案管室"),
		XFS("信访室"),
		BGT("办公厅");
		private String v;
		DeptName(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * LeaderFlag 标志
	 */
	public enum LeaderFlag{
		SAFE_AUDITOR("8"),//安全审计员
		SAFE_ADMIN("7"),//安全管理员
		SYSTEM_ADMIN("6"),//系统管理员
		LEADER_MAIN("5"),//委领导
		BUSINESS_ADMIN("4"),//业务管理员
		GENERAL("3"),//办信员
		SECRETARY("2"),//秘书
		LEADER("1");//领导
		private String v;
		LeaderFlag(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	
	/**
	 * 
	 * @author lijun
	 *1初核，2谈话函询，3转办，4了结，5暂存,6留存
	 */
	public enum SecretaryFlag{
		CHHE("1"),
		THHX("2"),
		TB("3"),
		LIAOJIE("4"),
		ZC("5"),
		LC("6");
		private String v;
		SecretaryFlag(String value){
			this.v = value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	
	/**
	 * 角色权限
	 */
	public enum RoleStr{
		SYSTEMADMIN("systemadmin"),//系统管理员
		SAFEADMIN("safeadmin"),//安全管理员
		SAFEAUDITOR("safeauditor"),//安全审计员
		ADMIN("admin"),//admin用户
		GENERAL("general"),//办信员
		SECRETARY("secretary"),//秘书
		LEADER("leader"),//领导
		BXS("bxs"),//办信室
		AGS("ags"),//案管室
		XFS("xfs"),//信访室
//		BGT("bgt"),//办公厅
		LEADER_MAIN("leadermain"),//委领导  系统权限加选择的部门权限
		BUSINESS_ADMIN("businessadmin"),//业务管理员 什么都不加
		BXS_GENERAL("bxsgeneral"),//办信室  办信员 系统权限加自己办理的信 Petition_Circulation_State_Info.default_Dealer_Code
		BXS_SECRETARY("bxssecretary"),//办信室 秘书  系统权限加本部门 Petition_Basic_Info.curr_Dept_Code
		BXS_LEADER("bxsleader"),//领导 系统权限加本部门 Petition_Basic_Info.curr_Dept_Code
		AGS_GENERAL("agsgeneral"),//案管室 办信员 系统权限加督办人是自己 Petition_Circulation_State_Info.default_Supervise_Code
		AGS_SECRETARY("agssecretary"),//案管室 秘书 系统权限加his_dept_code有案管室
		AGS_LEADER("agsleader"),//案管室  领导 系统权限加his_dept_code有案管室
		XFS_GENERAL("xfsgeneral"),//信访室 办信员 系统权限加自己办理的信 Petition_Circulation_State_Info.default_Dealer_Code
		XFS_SECRETARY("xfssecretary"),//信访室 秘书  系统权限
		XFS_LEADER("xfsleader");//信访室 领导 系统权限
		private String v;
		RoleStr(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * 市管单位树深度，0根节点，1部门，2系统，3单位
	 */
	public enum UnitDeep{
		ROOT("0"),DEPT("1"),SYS("2"),UNIT("3");
		private String v;
		UnitDeep(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * 归档电子文档
	String codes="1,2,3,4,5,6,7,8,9,10";
 *  String name="原信,重要信访拟办单,办结报告原件,办理方案呈批表,直接核实呈批表,信访谈话呈批表,函询单,通知书,交办函,结案呈批表"
	1,2,3为必须上传的
	 */
	public enum ScanType{
		YX("1"),ZYXFNBD("2"),BJBGYH("3")
		,BLFACPB("4"),ZJCBCPB("5"),XFTHCPB("6"),HXD("7"),TZS("8"),JBH("9"),JACPB("10");
		private String v;
		ScanType(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * 工作流状态：
	 */
	public enum ActivityNo{
		ACCEPT("0000000100"),ZB("0000000205"),ZBBW("0000000206"),JBYJG("0000000207"),JBYQK("0000000208"),
		QXBC("0000000212"),ZC("0000000210"),BLFA("0000000102"),GD("0000000303"),BLFF("0000000107"),DBSH("0000000307"),
		NBPS("0000000101"),BLPS("0000000103"),SLFF("0000000104"),SLQS("0000000105"),
		LJLR("0000000301"),LJPS("0000000302"),FKPS("0000000305"),ZCHFPS("0000000902"),FFPS("0000000905"),DBPS("0000000908"),
		SJZTHZC("0000000214"),SJZTHLC("0000000215"),SJZTHLJ("0000000216"),
		SJZTHZCPS("0000000903"),SJZTHLCPS("0000000904"),SJZTHLJPS("0000000906"), YGD("9");
		private String v;
		ActivityNo(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * 签收类型
	 * ONE("1")  受理签收
	 * TWO("2")  代理签收
	 */
	public enum AcceptSignFlag{
		ONE("1"),TWO("2");
		private String v;
		AcceptSignFlag(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * 办理方案：
	 *  1、XFJBQH:交办群函
	 *  2、XFJBXH:交办信函
	 * @author oba
	 */
	public enum DocTypeCode{
		XFJBQH("XFJBQH"),XFJBXH("XFJBXH"),XFZBD("XFZBD");
		private String v;
		DocTypeCode(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * 办理方案：
	 * 	0101：直接核实 ZJCB
	 * 	01024：函询 HX
	 * 	01021：信访谈话 XFTH
	 *  01022：信访通知 XFTZ
	 *  01023：民主生活会 MZHY
	 *  0302：转办 ZB
	 *  0301：转办本委 ZBBW
	 *  0201：交办要结果 JBYJG
	 *  0202：交办要情况 JBYQK
	 *  05：来信来访摘报 LXLFZB
	 *  04：暂存 ZC
	 *  //后加
	 *  06：SJZTHZC 书记专题会-暂存
	 *  07：SJZTHLC 书记专题会-留存
	 *  08：SJZTHLJ 书记专题会-了结
	 *  09：LC 留存
	 *  01025：CBHS  初步核实
	 * @author oba
	 */
	public enum DealType{
		ZJCB("0101"),XFTH("01021"),XFTZ("01022"),MZHY("01023"),HX("01024")
		,ZB("0302"),ZBBW("0301"),JBYJG("0201")
		,JBYQK("0202"),LXLFZB("05"),ZC("04")
		,SJZTHZC("06"),SJZTHLC("07"),SJZTHLJ("08"),LJ("09"),CBHS("01025");
		private String v;
		DealType(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * 信访来源 
	 * 01、自收 0101、本机关受理 0102、机关领导交办 
	 * 02、上级转来    0201、上级转办 0202、上级交办   
	 * 03、其他部门转来 0301、其他部门交办 0302、其他部门转办 
	 * @author Administrator
	 */
	public enum PetitionSourceCode{
		ZS("01"),BJGSL("0101"),JGLDJB("0102"),SJZL("02"),SJZB("0201")
		,SJJB("0202"),QTBMZL("03"),QTBMJB("0301"),QTBMZB("0302");
		private String v;
		PetitionSourceCode(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * 信访类别 1、检举控告 2、申诉 3、批评建议 4、业务范围外
	 * @author Administrator
	 */
	public enum PetitionClassCode{
		Accuse("1"),Appeal("2"),Criticize("3"),OutBusiness("4");
		private String v;
		PetitionClassCode(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}

	/**
	 * 2'基本属实','',
	3'部分属实','',
	4'不实','','All
	1-'属实'
	5未认定
	 */
	public enum RealityCode{
		ONE("1"),TWO("2"),THREE("3"),FOUR("4"),FIVE("5");
		private String v;
		RealityCode(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * 信访方式 1、来信 2来访 3电话举报 4网络举报
	 * @author Administrator
	 */
	public enum PetitionTypeCode{
		LetterAccept("1"),VisitAccept("2"),TelAccept("3"),NetAccept("4");
		private String v;
		PetitionTypeCode(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * 网络举报件接收状态 0未接收，1垃圾件,2已接收
	 * @author Administrator
	 */
	public enum NetReceiveFlag{
		NotReceiving("0"),Rubbish("1"),Received("2");
		private String v;
		NetReceiveFlag(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * 导出状态 0未导出，1已导出
	 * @author Administrator
	 */
	public enum NetReplyFlag{
		NotReply("0"),Reply("1");
		private String v;
		NetReplyFlag(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * 反映人及其被反映人老访户标志，老访户 1是0否
	 * @author Administrator
	 */
	public enum ConstantAppealFlag{
		no("0"),yes("1");
		private String v;
		ConstantAppealFlag(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * 是否有效  0 有效  1 无效
	 * @author Administrator
	 */
	public enum ValidFlag{
		Valid("0"),Invalid("1");
		private String v;
		ValidFlag(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * @author Administrator 性别（Secret保密、Man男、Woman女）
	 */
	public enum Gender {
		Secret("0"), Man("1"), Woman("2");

		private String v;

		Gender(String value) {
			v = value;
		}

		@Override
		public String toString() {
			return v;
		}
	}
	
	/**
	 * 转发类型（trans转办、assign交办、assignreply交办回复、assignreport交办上报、back回退,recovery回收）
	 * @author sunzhe 
	 */
	public enum TransType {
		transOrAssign("1"),trans("2"), assign("3"),recovery("4"),assignreply("5"),assignreport("6"), back("8");

		private String v;

		TransType(String value) {
			v = value;
		}

		@Override
		public String toString() {
			return v;
		}
	}
	
	/**
	 * 行政级别 
	 * 省部级：0200
	 * 副部级：0202
	 * 地厅级：0300
	 * 副厅级：0302
	 * 县处级：0400
	 * 副处级：0402
	 * 乡科级：0500
	 * 副科级：0502
	 * 一般干部：0600
	 * 其他军队人员：0901
	 * 其他企业人员：0903
	 * 其他农村人员：0904
	 * 其他人员：0905
	 * @author jk 
	 */
	public enum JIBE{
		SBJ("0200"),FBJ("0202"), DTJ("0300"),FTJ("0302"),XCJ("0400"), FCJ("0402"),
		XKJ("0500"),FKJ("0502"), YBGB("0600"),QTJDRY("0901"),QTQYRY("0903"),QTNCRY("0904"), 
		QTRY("0905");
		private String v;

		JIBE(String value) {
			v = value;
		}

		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * 预警类型（YuJ预警、ChaoS超时、ZhongDCS中度超时、YanZCS严重超时）
	 * @author Administrator
	 *
	 */
	public enum AlarmType{
		YuJ("1"),ChaoS("2"),ZhongDCS("3"),YanZCS("4");
		
		private String v;

		AlarmType(String value) {
			v = value;
		}

		@Override
		public String toString() {
			return v;
		}
		
	}
	/**
	 * 是否申请过延期 
	 * 0否，1同意申请延期，2正在申请中，3申请没同意
	 *
	 */
	public enum PostponeFlag{
		No("0"),Yes("1"),Ing("2"),Fail("3");
		private String v;
		PostponeFlag(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}

	/**
	 * 流转状态
	 * 0受理(待呈批)，1批示(待录入批示)，3移交(待移交)，9办结
	 *
	 */
	public enum ActivityFlag{
		Accept("0"),Instruct("1"),Transfer("3"),End("9");
		private String v;
		ActivityFlag(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * 批示状态 0:待批示，1：已批示
	 * 
	 */
	public enum InstructFlag{
		No("0"),Yes("1");
		private String v;
		InstructFlag(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * 了解标识
	 * 0:否  1:是
	 */
	public enum EndFlag{
		No("0"),Yes("1");
		private String v;
		EndFlag(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	
	/**
	 * 受理处理方式
	 * 0:保存  1:提交批示 2：办结
	 */
	public enum AcceptHandleType{
		Save("0"),Instruct("1"),End("2");
		private String v;
		AcceptHandleType(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * 受理处理方式
	 * 0:保存  1:提交批示 2：办结
	 */
	public enum InstructHandleType{
		InstructEnd("0"), Fallback("1");
		private String v;
		InstructHandleType(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	/**
	 * 模块类型
	 * 0:听取专题汇报  1:列席会议 2：组织谈话 3:抽查核实 4:走访调研 5:召开座谈 6:重要问题了解 
	 */
	public enum UnderstandFlag{
		HearReports("0"), AttendMeeting("1"),IndividualConversation("2"),
		SpotCheckVerify("3"),VisitingResearch("4"),ConvokeTalk("5"),ImportantIssues("6");
		private String v;
		UnderstandFlag(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}
	
}
