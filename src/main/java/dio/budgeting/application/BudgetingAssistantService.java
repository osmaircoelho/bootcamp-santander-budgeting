package dio.budgeting.application;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class BudgetingAssistantService {
    private final ChatClient chatClient;

    public BudgetingAssistantService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String chat(String prompt) {
        return chatClient
                .prompt()
                .system("""
                        Você é um assistente especializado em organização financeira pessoal.
                        
                        Seu objetivo é ajudar o usuário a entender melhor seus gastos,
                        organizar seu orçamento e desenvolver hábitos financeiros mais saudáveis.
                        
                        Responda de forma clara, objetiva e prática.
                        
                        Quando a pergunta não estiver relacionada a finanças pessoais,
                        explique educadamente que seu objetivo é auxiliar com organização financeira.
                        """)
                .user(prompt)
                .call()
                .content();
    }
}
