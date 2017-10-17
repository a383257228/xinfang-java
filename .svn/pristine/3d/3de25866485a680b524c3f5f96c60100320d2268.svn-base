package com.sinosoft.authorization.module.system.login.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.authentication.DefaultGatewayResolverImpl;
import org.jasig.cas.client.authentication.GatewayResolver;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

//import com.sinosoft.authorization.util.tools.BiUser;

//Referenced classes of package org.jasig.cas.client.authentication:
//         DefaultGatewayResolverImpl, GatewayResolver

public class CasFilter extends AbstractCasFilter {

    public CasFilter() {
        renew = false;
        gateway = false;
        gatewayStorage = new DefaultGatewayResolverImpl();
    }

    protected void initInternal(FilterConfig filterConfig) throws ServletException {
        if (!isIgnoreInitConfiguration()) {
            super.initInternal(filterConfig);
            setCasServerLoginUrl(getPropertyFromInitParams(filterConfig, "casServerLoginUrl", null));
            log.trace((new StringBuilder("Loaded CasServerLoginUrl parameter: ")).append(casServerLoginUrl).toString());
            setRenew(parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));
            log.trace((new StringBuilder("Loaded renew parameter: ")).append(renew).toString());
            setGateway(parseBoolean(getPropertyFromInitParams(filterConfig, "gateway", "false")));
            log.trace((new StringBuilder("Loaded gateway parameter: ")).append(gateway).toString());
            setClientPassedPathSet(getPropertyFromInitParams(filterConfig, "clientPassedPathSet", null));
            String gatewayStorageClass = getPropertyFromInitParams(filterConfig, "gatewayStorageClass", null);
            if (gatewayStorageClass != null)
                try {
                    gatewayStorage = (GatewayResolver) Class.forName(gatewayStorageClass).newInstance();
                }
                catch (Exception e) {
                    log.error(e, e);
                    throw new ServletException(e);
                }
        }
    }

    public void init() {
        super.init();
        CommonUtils.assertNotNull(casServerLoginUrl, "casServerLoginUrl cannot be null.");
    }

    public final void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        /**
         * 直接使用本系统地址时，通过过滤器重定向到平台登录地址首页
         * 
         * */
        String casFlag = request.getParameter("cas");
        String url = ((HttpServletRequest)servletRequest).getRequestURL().toString().trim();
//        System.out.println("----------------cas过滤地址------------------:"+url);
//        if(url.indexOf("/index") != -1 && casFlag == null){
//        	HttpSession sessionEp = request.getSession();
//        	if(sessionEp != null){
//        		sessionEp.getServletContext().setAttribute("isCas", "yes");
//        	}
//        	filterChain.doFilter(request, response);
//            return;
//        }
//        BiUser biUser = session == null?null:(BiUser)session.getAttribute("BISESSION");
//        if(biUser != null){
//            try{
//                Thread.sleep(800);
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//            filterChain.doFilter(request, response);
//            return;
//        }
        Assertion assertion = session == null ? null : (Assertion) session.getAttribute("_const_cas_assertion_");
//        System.out.println("assertion:"+assertion);
        if (assertion != null) {
            filterChain.doFilter(request, response);
            return;
        }
        String serviceUrl = constructServiceUrl(request, response);
        String ticket = CommonUtils.safeGetParameter(request, getArtifactParameterName());
        boolean wasGatewayed = gatewayStorage.hasGatewayedAlready(request, serviceUrl);
        if (CommonUtils.isNotBlank(ticket) || wasGatewayed) {
            filterChain.doFilter(request, response);
            return;
        }
        if (CommonUtils.isNotBlank(clientPassedPathSet)) {
            HttpServletRequest httpRequest = request;
            String requestPath = httpRequest.getRequestURI();
            PathMatcher matcher = new AntPathMatcher();
            String passedPaths[] = (String[]) null;
            passedPaths = clientPassedPathSet.split(",");
            if (passedPaths != null) {
                String as[];
                int j = (as = passedPaths).length;
                for (int i = 0; i < j; i++) {
                    String passedPath = as[i];
                    boolean flag = matcher.match(passedPath, requestPath);
                    if (flag) {
                        log.info((new StringBuilder("sso client request path '")).append(requestPath)
                                .append("'is matched,filter chain will be continued.").toString());
                        filterChain.doFilter(request, response);
                        return;
                    }
                }

            }
        }
        log.debug("no ticket and no assertion found");
        String modifiedServiceUrl;
        if (gateway) {
            log.debug("setting gateway attribute in session");
            modifiedServiceUrl = gatewayStorage.storeGatewayInformation(request, serviceUrl);
        }
        else {
            modifiedServiceUrl = serviceUrl;
        }
        if (log.isDebugEnabled())
            log.debug((new StringBuilder("Constructed service url: ")).append(modifiedServiceUrl).toString());
        String urlToRedirectTo = CommonUtils.constructRedirectUrl(casServerLoginUrl, getServiceParameterName(),
                modifiedServiceUrl, renew, gateway);
//        System.out.println("----------------cas过滤地址2------------------:" + casServerLoginUrl);
//        System.out.println("----------------cas过滤地址3------------------:"  +urlToRedirectTo);
        
        if(url.indexOf("/jubao") != -1 && url.indexOf("/petition") == -1 && casFlag == null){
        	urlToRedirectTo = casServerLoginUrl.substring(0,casServerLoginUrl.indexOf("cas"));
        }else{
        	
        }
//        System.out.println("----------------cas过滤地址4------------------:"  +urlToRedirectTo);
        if (log.isDebugEnabled())
            log.debug((new StringBuilder("redirecting to \"")).append(urlToRedirectTo).append("\"").toString());
        response.sendRedirect(urlToRedirectTo);
    }

    public final void setRenew(boolean renew) {
        this.renew = renew;
    }

    public final void setGateway(boolean gateway) {
        this.gateway = gateway;
    }

    public void setClientPassedPathSet(String clientPassedPathSet) {
        this.clientPassedPathSet = clientPassedPathSet;
    }

    public final void setCasServerLoginUrl(String casServerLoginUrl) {
        this.casServerLoginUrl = casServerLoginUrl;
    }

    public final void setGatewayStorage(GatewayResolver gatewayStorage) {
        this.gatewayStorage = gatewayStorage;
    }

    private String casServerLoginUrl;
    private String clientPassedPathSet;
    private boolean renew;
    private boolean gateway;
    private GatewayResolver gatewayStorage;
}

