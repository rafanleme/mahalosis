
public class Teste {
	
	public static void main(String[] args) {
		
		float var = 17.75F;
		
		float inteiro = (int) var;
		
		float decimal = var - (int) var;
		
		System.err.println((int) inteiro + ":" + (int) (decimal * 60));
	}

}