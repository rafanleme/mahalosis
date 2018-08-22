package mahalosis.utils;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class FacesUtils {
	
	public static void setMensagem(Severity severity, String summary, String detail){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(severity, summary, detail));
	}
	
	public static void setMensagem(String summary, String detail){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(summary, detail));
	}
	
}
