# PureChatAI

PureChatAI é um projeto experimental desenvolvido em Java com o objetivo de estudar integração com modelos de Inteligência Artificial através de APIs externas.

Esta versão representa uma Prova de Conceito (PoC), construída com um servidor HTTP simples em Java e integração com a API do Google Gemini.

O projeto servirá como base para futuras versões com arquitetura mais robusta e uma interface moderna desenvolvida em React.

## Status do Projeto

🚧 Em desenvolvimento

A versão atual é uma prova de conceito focada em validar a comunicação entre uma aplicação Java e um modelo de IA generativa.

---

## Tecnologias Utilizadas

### Backend

* Java 21
* Java HttpServer
* Google Gemini API
* Maven

### Futuro Frontend

* React
* TypeScript
* HTML5
* CSS3

---

## Configuração

### 1. Obtenha uma API Key do Google Gemini

Acesse:

https://aistudio.google.com/

Crie uma API Key para utilização do projeto.

---

### 2. Configure sua API Key

Localize a classe responsável pela integração com o Gemini:

```java
GeminiService.java
```

Substitua a chave de exemplo pela sua chave pessoal:

```java
private final String apiKey = "SUA_API_KEY";
```

ou, preferencialmente, utilize variáveis de ambiente:

```java
private final String apiKey = System.getenv("GEMINI_API_KEY");
```

Windows:

```bash
setx GEMINI_API_KEY "SUA_API_KEY"
```

Linux:

```bash
export GEMINI_API_KEY="SUA_API_KEY"
```

Após configurar a variável de ambiente, reinicie sua IDE ou terminal.

---

## Executando o Projeto

Clone o repositório:

```bash
git clone https://github.com/seu-usuario/PureChatAI.git
```

Entre na pasta:

```bash
cd PureChatAI
```

Execute a aplicação:

```bash
mvn clean install
mvn exec:java
```

O servidor será iniciado na porta:

```text
http://localhost:8080
```

---

## Rotas Disponíveis

### Gerar Resposta da IA

**GET**

```http
/ai?prompt=SuaPergunta
```

Exemplo:

```text
http://localhost:8080/ai?prompt=Explique%20o%20que%20é%20JWT
```

Resposta:

```text
JWT (JSON Web Token) é um padrão utilizado para autenticação...
```

---

## Exemplo de Uso

Pergunta:

```text
http://localhost:8080/ai?prompt=O%20que%20é%20Java%3F
```

Resposta:

```text
Java é uma linguagem de programação orientada a objetos...
```

---

## Roadmap

### Backend

* [x] Integração com Gemini API
* [x] Servidor HTTP em Java puro
* [x] Endpoint de geração de conteúdo
* [ ] Requisições POST
* [ ] Processamento automático de JSON
* [ ] Tratamento global de exceções
* [ ] Histórico de conversas
* [ ] Persistência de dados

### Frontend

* [ ] Interface React
* [ ] Chat em tempo real
* [ ] Histórico de mensagens
* [ ] Design responsivo
* [ ] Tema escuro
* [ ] Integração completa com o backend

---

## Objetivo do Projeto

O objetivo desta primeira versão é estudar:

* Desenvolvimento Backend com Java
* Integração com APIs de Inteligência Artificial
* Comunicação HTTP
* Arquitetura de Software
* Desenvolvimento Fullstack

A próxima versão utilizará esta prova de conceito como base para a construção de uma plataforma completa de conversação com IA, contando com um backend mais robusto em Java e uma interface moderna desenvolvida em React.

