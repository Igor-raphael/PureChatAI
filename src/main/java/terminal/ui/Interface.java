package terminal.ui;

public class Interface {
	
	public void showBanner() {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                       PureChatAI v1.0                        ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║ Modelo......: Gemini 2.5 Flash                               ║");
        System.out.println("║ Comandos....: sair                                           ║");
        System.out.println("║                                                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }
	
	public void userMessage() {
		TerminalUtils.separator();
		
		System.out.println("👤 Você");
		
		System.out.println("> ");
		
	}
	
	public void IAMessage(String resposta) {
		
		System.out.println("\n🤖 Assistente\n");

        System.out.println(resposta);

        System.out.println();
		
	}

}
