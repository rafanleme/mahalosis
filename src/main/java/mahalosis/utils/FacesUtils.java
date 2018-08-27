package mahalosis.utils;
import java.text.Normalizer;
import java.util.regex.Pattern;

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
	
	public static String removerAcentos(String str) {
	    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
	    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	    return pattern.matcher(nfdNormalizedString).replaceAll("");
	}
}
