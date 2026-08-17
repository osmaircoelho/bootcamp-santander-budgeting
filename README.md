# Budgeting - Assistente Financeiro com Spring AI

## Sobre o projeto

O **Budgeting** é uma API desenvolvida com Spring Boot e Spring AI para explorar o uso de Inteligência Artificial em um contexto de organização financeira.

A aplicação combina gerenciamento de transações financeiras com recursos de IA, permitindo interações por texto e áudio, transcrição de fala, geração de respostas e conversão de texto em áudio.

O projeto foi desenvolvido como parte do desafio da trilha de Spring Boot da DIO e posteriormente evoluído com funcionalidades próprias.

## Objetivo

O objetivo do projeto é aplicar os principais recursos do Spring AI em uma API financeira, integrando modelos da OpenAI com uma aplicação Spring Boot.

A aplicação permite explorar conceitos como:

- Chat com modelos de linguagem;
- System Prompts;
- Tool Calling;
- Transcrição de áudio;
- Text-to-Speech;
- Persistência de transações;
- Integração com banco de dados MySQL;
- Testes automatizados utilizando JUnit e Mockito.

## Funcionalidades

A API possui funcionalidades para:

- Registrar transações financeiras;
- Consultar transações por categoria;
- Interagir com um modelo de linguagem;
- Transcrever arquivos de áudio para texto;
- Converter respostas de texto em áudio;
- Utilizar Tool Calling para executar operações relacionadas às transações;
- Utilizar um assistente especializado em organização financeira.

## Evolução implementada

Como evolução do projeto base, foi criado um assistente financeiro especializado utilizando o `ChatClient` do Spring AI.

Antes da alteração, o endpoint de chat utilizava diretamente o `ChatClient` e aceitava perguntas de qualquer contexto.

A evolução realizada incluiu:

- Criação do `BudgetingAssistantService`;
- Separação da lógica de IA da camada HTTP;
- Definição de um System Prompt focado em finanças pessoais;
- Restrição do assistente para perguntas relacionadas ao domínio financeiro;
- Criação de testes automatizados para o Controller e o Service.

O novo fluxo do endpoint de chat passou a ser:

```text
Cliente
   ↓
ChatClientController
   ↓
BudgetingAssistantService
   ↓
System Prompt + User Prompt
   ↓
ChatClient
   ↓
OpenAI
   ↓
Resposta
```

### Exemplo de comportamento

Pergunta relacionada ao domínio financeiro:

```text
Como posso economizar dinheiro?
```

O assistente responde com orientações relacionadas à organização financeira.

Para uma pergunta fora desse domínio:

```text
Quem foi Albert Einstein?
```

o assistente mantém o contexto definido pelo System Prompt e informa que seu foco é auxiliar com organização financeira.

## Arquitetura

O projeto separa as responsabilidades entre diferentes partes da aplicação.

```text
Cliente
   ↓
Controllers
   ↓
Application / Use Cases
   ↓
Domain
   ↓
Infrastructure
   ↓
MySQL / OpenAI
```

### Principais componentes

**Controllers**

Responsáveis por receber as requisições HTTP e encaminhar as operações para os serviços ou casos de uso correspondentes.

**Application**

Contém os casos de uso da aplicação e serviços como o `BudgetingAssistantService`.

**Domain**

Representa os conceitos relacionados às transações financeiras, como categorias, identificadores e contratos de persistência.

**Infrastructure**

Contém as implementações relacionadas à persistência e à exposição HTTP da aplicação.

**Spring AI**

Responsável pela integração com os modelos de Inteligência Artificial utilizados pela aplicação.

## Fluxo com áudio e Tool Calling

Além do endpoint de chat, o projeto possui um fluxo que combina diferentes recursos do Spring AI.

```text
Arquivo de áudio
      ↓
TranscriptionModel
      ↓
Texto
      ↓
ChatClient
      ↓
Tool Calling
      ↓
Casos de uso
      ↓
Resposta da IA
      ↓
TextToSpeechModel
      ↓
Arquivo MP3
```

Nesse fluxo, o áudio enviado pelo usuário é convertido em texto.

A mensagem é processada pelo modelo de linguagem, que pode utilizar as ferramentas disponibilizadas pela aplicação para consultar ou registrar transações.

A resposta final também pode ser convertida novamente para áudio.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring AI
- Spring Web
- Spring Data JPA
- OpenAI
- MySQL
- Docker
- Docker Compose
- Gradle
- JUnit 5
- Mockito

## Pré-requisitos

Para executar o projeto localmente, é necessário possuir:

- Java 21;
- Docker;
- Docker Compose;
- Git;
- Uma chave de API da OpenAI.

O projeto utiliza o **Gradle Wrapper**, portanto não é necessário instalar o Gradle separadamente.

## Clonando o projeto

Clone o repositório:

```bash
git clone https://github.com/osmaircoelho/bootcamp-santander-budgeting.git
```

Entre no diretório:

```bash
cd bootcamp-santander-budgeting
```

## Configuração da OpenAI

A aplicação utiliza a variável de ambiente:

```text
OPENAI_API_KEY
```

para acessar os modelos da OpenAI.

### Linux / WSL

```bash
export OPENAI_API_KEY="sua-chave-aqui"
```

Para verificar:

```bash
echo $OPENAI_API_KEY
```

### Windows PowerShell

```powershell
$env:OPENAI_API_KEY="sua-chave-aqui"
```

A aplicação utiliza essa variável através da configuração:

```properties
spring.ai.openai.api-key=${OPENAI_API_KEY}
```

> A chave da OpenAI não deve ser adicionada diretamente ao código-fonte nem enviada para o repositório Git.

## Banco de dados

O projeto utiliza **MySQL** para persistência das transações financeiras.

O arquivo `compose.yml` disponível na raiz do projeto configura o banco utilizado pela aplicação.

Para iniciar o container:

```bash
docker compose up -d
```

O MySQL é disponibilizado localmente através da porta:

```text
3307
```

A aplicação utiliza:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/transaction
spring.datasource.username=app
spring.datasource.password=app
```

Para verificar os containers:

```bash
docker compose ps
```

Para acompanhar os logs:

```bash
docker compose logs
```

Para encerrar:

```bash
docker compose down
```

O volume configurado no Docker Compose mantém os dados do banco mesmo após a interrupção do container.

## Executando a aplicação

Primeiro, certifique-se de que:

1. O Docker está em execução;
2. O container MySQL está disponível;
3. A variável `OPENAI_API_KEY` foi configurada.

### IntelliJ IDEA

Abra o projeto no IntelliJ IDEA e execute:

```text
BudgetingApplication
```

através do botão **Run**.

### Terminal

Também é possível executar utilizando o Gradle Wrapper:

```bash
./gradlew bootRun
```

No Windows:

```powershell
.\gradlew.bat bootRun
```

Após a inicialização, a aplicação estará disponível em:

```text
http://localhost:8080
```

## Endpoints

### Assistente financeiro

```http
GET /api/chat?prompt={pergunta}
```

Exemplo:

```http
GET http://localhost:8080/api/chat?prompt=Como posso economizar dinheiro?
```

Também é possível utilizar a URL codificada:

```http
GET http://localhost:8080/api/chat?prompt=Como+posso+economizar+dinheiro%3F
```

### Registrar transação

```http
POST /transactions
```

Esse endpoint registra uma nova transação financeira utilizando o caso de uso responsável pela persistência.

### Consultar transações por categoria

```http
GET /transactions/{category}
```

O endpoint permite consultar as transações pertencentes a uma determinada categoria.

### Processamento financeiro por áudio

```http
POST /transactions/ai
```

O endpoint recebe um arquivo através de `multipart/form-data`.

O fluxo executado é:

```text
Áudio
 ↓
Transcrição
 ↓
ChatClient
 ↓
Tool Calling
 ↓
Operação financeira
 ↓
Resposta
 ↓
Text-to-Speech
 ↓
MP3
```

A resposta é retornada como:

```text
audio/mp3
```

## System Prompt

A evolução do assistente utiliza um System Prompt para determinar seu comportamento.

O objetivo é fazer com que o modelo atue como um assistente especializado em organização financeira pessoal.

O prompt define que o assistente deve:

- Auxiliar na compreensão dos gastos;
- Auxiliar na organização do orçamento;
- Incentivar hábitos financeiros mais saudáveis;
- Responder de maneira clara e prática;
- Manter o foco em assuntos relacionados a finanças pessoais.

Dessa forma, o comportamento do modelo deixa de ser completamente genérico e passa a respeitar o contexto da aplicação.

## Testes

Foram adicionados testes automatizados para a evolução do assistente financeiro.

### ChatClientControllerTest

Valida a comunicação entre:

```text
ChatClientController
        ↓
BudgetingAssistantService
```

O `BudgetingAssistantService` é substituído por um mock durante o teste.

Isso permite verificar se o Controller:

- Envia corretamente o prompt recebido;
- Delega a operação para o Service;
- Retorna a resposta produzida pelo Service.

### BudgetingAssistantServiceTest

Valida o fluxo:

```text
BudgetingAssistantService
        ↓
ChatClient
        ↓
Resposta
```

O `ChatClient` é mockado para que os testes não dependam de chamadas reais à API da OpenAI.

Isso torna os testes:

- Mais rápidos;
- Reproduzíveis;
- Independentes da internet;
- Independentes de custos da API;
- Independentes de respostas variáveis do modelo.

### Executando os testes

No IntelliJ IDEA, os testes podem ser executados individualmente através do botão **Run** ao lado da classe ou método.

Também podem ser executados pelo terminal:

```bash
./gradlew test
```

No Windows:

```powershell
.\gradlew.bat test
```

Um resultado bem-sucedido deve terminar com:

```text
BUILD SUCCESSFUL
```

## Estrutura principal

Uma visão simplificada da organização do projeto:

```text
src
├── main
│   ├── java
│   │   └── dio
│   │       └── budgeting
│   │           ├── application
│   │           │   └── BudgetingAssistantService.java
│   │           ├── domain
│   │           ├── infrastructure
│   │           ├── BudgetingApplication.java
│   │           └── ChatClientController.java
│   │
│   └── resources
│       ├── prompts
│       │   └── system-message.st
│       └── application.properties
│
└── test
    └── java
        └── dio
            └── budgeting
                ├── application
                │   └── BudgetingAssistantServiceTest.java
                └── ChatClientControllerTest.java
```

## O que foi aprendido

Durante o desenvolvimento e evolução do projeto foram explorados conceitos como:

- Integração do Spring Boot com modelos de linguagem;
- Utilização do `ChatClient`;
- Diferença entre User Prompt e System Prompt;
- Especialização do comportamento de um assistente através de prompts;
- Tool Calling;
- Transcrição de áudio;
- Geração de áudio com Text-to-Speech;
- Persistência de dados com Spring Data JPA e MySQL;
- Separação de responsabilidades entre Controller e Service;
- Injeção de dependências;
- Testes unitários utilizando JUnit e Mockito;
- Mock de dependências externas;
- Docker para execução do banco de dados.

## Possíveis evoluções

Algumas melhorias que podem ser implementadas futuramente:

- Adicionar validações mais completas antes de registrar transações;
- Criar novos tipos de consultas financeiras;
- Expandir as ferramentas disponíveis para Tool Calling;
- Criar relatórios de gastos por período;
- Adicionar análise de gastos por categoria;
- Criar recomendações baseadas nas transações armazenadas;
- Melhorar o tratamento de erros da API;
- Expandir a cobertura de testes;
- Documentar a API utilizando OpenAPI/Swagger.

## Projeto base

Este projeto foi desenvolvido a partir do conteúdo da trilha **Spring Boot Learning Track** da DIO.

O projeto base apresenta diferentes recursos do Spring AI e serviu como ponto de partida para a implementação e evolução do assistente financeiro.

### Repositório da trilha

```text
digitalinnovationone/dio-spring-boot-learning-track
```

### Módulo utilizado

```text
05-spring-ai
```

A partir dessa base, o projeto foi estudado, executado e posteriormente evoluído com uma nova camada de serviço, especialização do assistente financeiro e testes automatizados.