package dio.budgeting;

import dio.budgeting.application.BudgetingAssistantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatClientController {
    private final BudgetingAssistantService budgetingAssistantService;

    public ChatClientController(BudgetingAssistantService budgetingAssistantService) {
        this.budgetingAssistantService = budgetingAssistantService;
    }

    @GetMapping("/chat")
    String chat(String prompt) {
        return budgetingAssistantService.chat(prompt);
    }
}
