package com.sinosoft.xf.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.sinosoft.xf.query.common.JubaoException;

public class ExceptionInterceptor implements Interceptor {

	public void init() {

	}

	public String intercept(ActionInvocation actioninvocation) {

		String result = null; // Action的返回值
		String errorMsg = "出现错误信息，请查看日志！";
		// 把自定义错误信息
					HttpServletRequest request = (HttpServletRequest) actioninvocation
							.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
		try {
			// 运行被拦截的Action,期间如果发生异常会被catch住
			result = actioninvocation.invoke();
//			System.out.println("===================");
			return result;
		} catch (JubaoException e) {
			errorMsg = "非法的参数";
			request.setAttribute("errorMsg", errorMsg);
			e.printStackTrace();
			return "invalidRequest";
		} catch (RuntimeException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", errorMsg);
			return "error";
		} catch (Exception e) {
			/**
			 * 处理异常
			 */
			// String errorMsg = "出现错误信息，请查看日志！";
			// 通过instanceof判断到底是什么异常类型
			if (e instanceof RuntimeException) {
				// 未知的运行时异常
				RuntimeException re = (RuntimeException) e;
				// re.printStackTrace();
				errorMsg = re.getMessage().trim();
			}
			// 把自定义错误信息
//			HttpServletRequest request = (HttpServletRequest) actioninvocation
//					.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
			/**
			 * 发送错误消息到页面
			 */
			request.setAttribute("errorMsg", errorMsg);

			System.out.println("==================");
			/**
			 * log4j记录日志
			 */
			// Logger log =
			// LogFactory.getLog(actioninvocation.getAction().getClass());
			// log.error(errorMsg, e);
			return "errorMsg";
		}// ...end of catch
	}

	public void destroy() {

	}
}
