package fitprogwork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fitprogwork.dto.ExtractRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DataExtractorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ExtractRequest request(String type, String data, String path) {
        ExtractRequest r = new ExtractRequest();
        r.setType(type);
        r.setData(data);
        r.setPath(path);
        return r;
    }

    @Test
    void jsonRequest_returnsValue() throws Exception {
        ExtractRequest req = request("json", "{ \"user\": { \"name\": \"Alex\" } }", "user/name");
        mockMvc.perform(post("/api/data/extract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value("Alex"));
    }

    @Test
    void xmlRequest_returnsValue() throws Exception {
        ExtractRequest req = request("xml", "<person><name>Alex</name></person>", "name");
        mockMvc.perform(post("/api/data/extract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value("Alex"));
    }

    @Test
    void yamlRequest_returnsValue() throws Exception {
        ExtractRequest req = request("yaml", "name: Alex", "name");
        mockMvc.perform(post("/api/data/extract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value("Alex"));
    }

    @Test
    void unknownType_returnsError() throws Exception {
        ExtractRequest req = request("csv", "a,b,c", "a");
        mockMvc.perform(post("/api/data/extract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void wrongPath_returnsError() throws Exception {
        ExtractRequest req = request("json", "{ \"user\": { \"name\": \"Alex\" } }", "user/nonexistent");
        mockMvc.perform(post("/api/data/extract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }
}