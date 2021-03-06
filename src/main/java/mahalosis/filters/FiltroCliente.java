package mahalosis.filters;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;

import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.sun.net.httpserver.Filter.Chain;


import mahalosis.controller.UsuarioMBean;

@WebFilter("/restrito/cliente/*")
public class FiltroCliente implements Filter{
	
	
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
				
		Boolean logado = (Boolean) ((HttpServletRequest)request).getSession().getAttribute("logado");
		String perfil = (String) ((HttpServletRequest)request).getSession().getAttribute("perfil");
		if(logado == null){
			String contextPath = ((HttpServletRequest)request).getContextPath();
			System.out.println(contextPath);
			((HttpServletRequest)request).getSession().setAttribute("mensagem", "Voc� n�o est� logado!");
            ((HttpServletResponse)response).sendRedirect(contextPath + "/restrito/index.xhtml");
		}if(!perfil.equals("3")){
			request.setAttribute("mensagem", "Voc� n�o tem acesso a esta �rea");
		}
		
		chain.doFilter(request, response);
	}
	
	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
