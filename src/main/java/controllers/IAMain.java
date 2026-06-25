package controllers;

import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpServer;

import services.GeminiService;

public class IAMain {
	
	public static void main(String[] args) throws Exception {
	
		GeminiService service = new GeminiService();
		
		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
		
		server.createContext("/ai", exchange -> {
			
			try {
				
				String query = exchange.getRequestURI().getQuery();
				String prompt = query != null ? URLDecoder.decode(query.replace("prompt=",""), StandardCharsets.UTF_8) : "Olá";
				
				String result = service.gerarConteudo(prompt);
				System.out.println(result);
				
				byte[] responseBytes = result.getBytes(StandardCharsets.UTF_8);
				
				exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=utf_8");
				
				exchange.sendResponseHeaders(200, responseBytes.length);
				
				try (var os = exchange.getResponseBody()) {
		            os.write(responseBytes);
		        }
				
			} catch (Exception e) {
				
				String error = "Erro: " + e.getMessage();
				byte[] responseBytes = error.getBytes(StandardCharsets.UTF_8);
			
				exchange.getResponseHeaders()
                .add("Content-Type", "text/plain; charset=utf-8");

				exchange.sendResponseHeaders(500, responseBytes.length);

				try (var os = exchange.getResponseBody()) {
					os.write(responseBytes);
				}
				
			} finally {
				exchange.close();
			}
			
		});
		
		server.start();
		
		System.out.println("Servidor rodando em http://localhost:8080/ai?prompt=teste");
	}

}
