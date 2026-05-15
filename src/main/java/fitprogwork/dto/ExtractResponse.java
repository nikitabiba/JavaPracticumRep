package fitprogwork.dto;

public class ExtractResponse {
    private String value;
    private String error;

    public static ExtractResponse ok(String value) {
        ExtractResponse r = new ExtractResponse();
        r.value = value;
        return r;
    }

    public static ExtractResponse error(String error) {
        ExtractResponse r = new ExtractResponse();
        r.error = error;
        return r;
    }

    public String getValue() { return value; }
    public String getError() { return error; }
}