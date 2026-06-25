package services;


import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class GeminiService {

		private String apiKey = System.getenv("GEMINI-API-KEY");
	
		public String gerarConteudo(String prompt) {
		
			Client client = Client.builder().apiKey(apiKey).build();
			
			GenerateContentResponse response = client.models.generateContent(
					"gemini-3.1-flash-lite", 
					prompt, 
					null);
			
			return response.text();
		}
		
	}
	

