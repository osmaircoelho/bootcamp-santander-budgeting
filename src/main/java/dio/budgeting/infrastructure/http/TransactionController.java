package dio.budgeting.infrastructure.http;

import dio.budgeting.application.ListTransactionByCategoryUseCase;
import dio.budgeting.application.PersistTransactionUseCase;
import dio.budgeting.domain.Category;
import dio.budgeting.infrastructure.http.request.TransactionRequest;
import dio.budgeting.infrastructure.http.response.TransactionResponse;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final PersistTransactionUseCase persistTransactionUseCase;
    private final ListTransactionByCategoryUseCase listTransactionByCategoryUseCase;
    private final TranscriptionModel transcriptionModel;

    public TransactionController(
            PersistTransactionUseCase persistTransactionUseCase,
            ListTransactionByCategoryUseCase listTransactionByCategoryUseCase,
            TranscriptionModel transcriptionModel
    ) {
        this.persistTransactionUseCase = persistTransactionUseCase;
        this.listTransactionByCategoryUseCase = listTransactionByCategoryUseCase;
        this.transcriptionModel = transcriptionModel;
    }

    @PostMapping
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public TransactionResponse createTransaction(@RequestBody TransactionRequest request) {
        var transaction = persistTransactionUseCase.execute(request.toInput());
        return TransactionResponse.from(transaction);
    }

    @GetMapping("/{category}")
    public List<TransactionResponse> readTransaction(@PathVariable Category category) {
        return listTransactionByCategoryUseCase.execute(category)
                .stream().map(TransactionResponse::from).toList();
    }

    @PostMapping(value = "/ai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String transcribe(@RequestParam("file") MultipartFile file) {
        var resource = file.getResource();
        return transcriptionModel.transcribe(resource);
    }


}
