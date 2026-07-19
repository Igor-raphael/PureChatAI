package services;

import java.util.List;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.FileData;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.google.genai.types.UploadFileConfig;

public class GeminiService {
		
		private String apiKey = System.getenv("GEMINI-API-KEY");
		Client client = Client.builder().apiKey(apiKey).build();
	
		public String gerarConteudo(String prompt) {
		
			GenerateContentResponse response = client.models.generateContent(
					"gemini-3.1-flash-lite", 
					prompt, 
					null);
			
			return response.text();
		}
		
		private String getMimeType(java.io.File file) {
			
			String name = file.getName().toLowerCase();
			
			if (name.endsWith(".png"))  return "image/png";
		    if (name.endsWith(".jpg"))  return "image/jpeg";
		    if (name.endsWith(".jpeg")) return "image/jpeg";
		    if (name.endsWith(".gif"))  return "image/gif";
		    if (name.endsWith(".webp")) return "image/webp";
		    return "image/jpeg";
		}
		
		public String uploadArquivo(java.io.File file, String prompt) {
			
			String mimeType = getMimeType(file);
			
			com.google.genai.types.File upload = client.files.upload(file, UploadFileConfig.builder().mimeType(mimeType).build());
			
			Content content = Content.builder().role("user").parts(List.of(
					Part.builder().fileData(FileData.builder()
													.fileUri(upload.uri().get())
													.mimeType(mimeType)
													.build()).build(),
													
										Part.builder().text(prompt).build()
					)).build();
			
			GenerateContentResponse response = client.models.generateContent(
					"gemini-3.1-flash-lite",
					List.of(content), 
					null);
			
			return response.text();
		}
		
		
	}
	

