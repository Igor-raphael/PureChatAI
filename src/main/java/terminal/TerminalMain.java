package terminal;

import java.util.Scanner;

import services.GeminiService;

public class TerminalMain {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		GeminiService service = new GeminiService();
		
		while(true) {
			
			System.out.println("User: ");
			String prompt = sc.next();
			
			if(prompt.equalsIgnoreCase("sair")) {
				break;
			}
			
			String result = service.gerarConteudo(prompt);
			
			System.out.println("IA: " + result);
			
		}
		
		sc.close();
		
	}
}
