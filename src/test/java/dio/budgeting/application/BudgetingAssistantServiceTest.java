package dio.budgeting.application;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BudgetingAssistantServiceTest {

    @Test
    void shouldReturnResponseFromChatClient() {
        ChatClient chatClient = mock(ChatClient.class);

        ChatClient.ChatClientRequestSpec requestSpec =
                mock(ChatClient.ChatClientRequestSpec.class);

        ChatClient.CallResponseSpec callResponseSpec =
                mock(ChatClient.CallResponseSpec.class);

        String prompt = "Como posso economizar dinheiro?";
        String expectedResponse = "Organize seus gastos e crie um orçamento mensal.";

        when(chatClient.prompt()).thenReturn(requestSpec);
        when(requestSpec.system(anyString())).thenReturn(requestSpec);
        when(requestSpec.user(prompt)).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(callResponseSpec);
        when(callResponseSpec.content()).thenReturn(expectedResponse);

        BudgetingAssistantService service = new BudgetingAssistantService(chatClient);

        String response  = service.chat(prompt);

        assertEquals(expectedResponse, response);

    }
}
