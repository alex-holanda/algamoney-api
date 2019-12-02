package com.algaworks.algamoney.api.cors;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algamoney.api.config.property.AlgamoneyApiProperty;

//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter { // implements Filter {

	@Autowired
	private AlgamoneyApiProperty algamoneyProperty;
	
//	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		resp.setHeader("Access-Control-Allow-Origin", algamoneyProperty.getOrigemPermitida());
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		
		if ("OPTIONS".equals(req.getMethod()) && algamoneyProperty.getOrigemPermitida().equals(req.getHeader("Origin"))) {
			
			resp.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
			resp.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			resp.setHeader("Access-Control-Max-Age", "3600");
			
			resp.setStatus(HttpServletResponse.SC_OK);
			
		} else {
			chain.doFilter(request, response);
		}
		
	}
	
//	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

//	@Override
	public void destroy() {
		
	}

}
