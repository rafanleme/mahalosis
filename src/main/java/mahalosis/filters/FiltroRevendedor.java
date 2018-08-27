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

@WebFilter("/restrito/revendedor/*")
public class FiltroRevendedor implements Filter{
	
	
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("entrou no filtro revendedor");
		
		Boolean logado = (Boolean) ((HttpServletRequest)request).getSession().getAttribute("logado");
		String perfil = (String) ((HttpServletRequest)request).getSession().getAttribute("perfil");
		String contextPath = ((HttpServletRequest)request).getContextPath();
		if(logado == null){
			System.out.println("não esta logado");
			((HttpServletRequest)request).getSession().setAttribute("mensagem", "Você não está logado!");
            ((HttpServletResponse)response).sendRedirect(contextPath + "/restrito/index.xhtml");
		}if(!perfil.equals("2")){
			((HttpServletRequest)request).getSession().removeAttribute("logado");
			((HttpServletResponse)response).sendRedirect(contextPath + "/restrito/index.xhtml");
			request.setAttribute("mensagem", "Você não tem acesso a esta área");
		}
		
		chain.doFilter(request, response);
	}
	
	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
