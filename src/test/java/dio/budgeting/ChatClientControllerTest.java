package dio.budgeting;

import dio.budgeting.application.BudgetingAssistantService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ChatClientControllerTest {

    @Test
    void shouldReturnFinancialAssistantResponse(){
        BudgetingAssistantService service = mock(BudgetingAssistantService.class);

        String prompt  = "Como posso economizar dinheiro?";
        String expectedResponse = "Crie um orçamento e acompanhe seus gastos.";

        when(service.chat(prompt)).thenReturn(expectedResponse);

        ChatClientController controller = new ChatClientController(service);

        String response  = controller.chat(prompt);

        assertEquals(expectedResponse, response);
        verify(service).chat(prompt);
    }
}
