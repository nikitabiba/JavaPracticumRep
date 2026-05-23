package fitprogwork.controller;

import fitprogwork.dto.ExtractRequest;
import fitprogwork.dto.ExtractResponse;
import fitprogwork.service.DataProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data")
public class DataExtractorController {

    private final DataProcessingService processingService;

    public DataExtractorController(DataProcessingService processingService) {
        this.processingService = processingService;
    }

    @PostMapping("/extract")
    public ResponseEntity<ExtractResponse> extract(@RequestBody ExtractRequest request) {
        try {
            String value = processingService.extract(request.getType(), request.getData(), request.getPath());
            return ResponseEntity.ok(ExtractResponse.ok(value));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExtractResponse.error(e.getMessage()));
        }
    }
}