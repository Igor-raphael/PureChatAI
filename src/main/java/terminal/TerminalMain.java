package terminal;

import java.util.Scanner;

import services.GeminiService;
import terminal.ui.Interface;

public class TerminalMain {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		GeminiService service = new GeminiService();
		
		Interface ui = new Interface();
		
		ui.showBanner();
		
		while(true) {
			
			ui.userMessage();
			String prompt = sc.next();
			
			if(prompt.equalsIgnoreCase("sair")) {
				break;
			}
			
			String result = service.gerarConteudo(prompt);
			
			ui.IAMessage(result);
			
		}
		
		sc.close();
		
	}
}
