package com.sinosoft;

import java.io.PrintStream;

import com.p6spy.engine.logging.appender.P6Logger;

public class SqlLog implements  P6Logger  {
	protected PrintStream qlog;
	private String lastEntry;  
	 public SqlLog()
	  {
	    this.qlog = System.out;
	  }
	public void setLastEntry(String lastEntry) {
		this.lastEntry = lastEntry;
	}
	@Override
	public String getLastEntry() {
		// TODO Auto-generated method stub
		 return lastEntry;  
	}
	@Override
	public void logException(Exception e) {
		// TODO Auto-generated method stub
		e.printStackTrace();
	}
	@Override
	public void logSQL(int connectionId, String now, long elapsed, String category, String prepared, String sql) {
		// TODO Auto-generated method stub
		//当前时间，耗时，sql
		StringBuffer logEntry = new StringBuffer();
		logEntry.append("sql执行时刻：").append(now).append(" | 耗时： ").append(elapsed).append("ms | ")
			.append(category).append(" | connectionId:").append(connectionId)
			.append("\npreSQL：").append(prepared).append("\n实际执行SQL语句：").append(sql).append(";\n****************************");
//		 String logEntry = "sql执行时刻：" + now + " | 耗时： " + elapsed + "ms | " + category + " | connectionId:" + connectionId + "\npreSQL："+ prepared + "\n实际执行SQL语句：" + sql +";" + "\n*****************************************";
		 if(sql!=null){
			 //不输出查询结果，定时任务语句和日志sql
			 if("resultset".equals(category)||sql.contains("QRTZ_TRIGGERS")||sql.contains("operation_log")){
				 return ;
			 }
			 logText(logEntry.toString());  
		 }
	}
	@Override
	public void logText(String text) {
		// TODO Auto-generated method stub
		this.qlog.println(text);
		setLastEntry(text);
	}
}
