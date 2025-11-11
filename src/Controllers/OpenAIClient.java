package Controllers;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

public class OpenAIClient {
    
    private  final String API_KEY = "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";

    
    private  final String ENDPOINT = "https://openrouter.ai/api/v1/chat/completions";

    public  String askDeepSeek(String message) throws IOException {
        OkHttpClient client = new OkHttpClient();

        
        JSONObject json = new JSONObject();
        json.put("model", "deepseek/deepseek-chat-v3.1:free");
        json.put("messages", new JSONArray()
                .put(new JSONObject().put("role", "user").put("content", message))
        );

        
        JSONObject extraHeaders = new JSONObject();
        extraHeaders.put("HTTP-Referer", "https://your-site-url.com");
        extraHeaders.put("X-Title", "Your App Name");
        json.put("extra_headers", extraHeaders);

        
        RequestBody body = RequestBody.create(
                json.toString(),
                MediaType.parse("application/json")
        );

        
        Request request = new Request.Builder()
                .url(ENDPOINT)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            System.out.println("Phản hồi từ API:\n" + responseBody);

            if (!response.isSuccessful()) {
                throw new IOException("Yêu cầu thất bại: " + response.code() + " - " + response.message());
            }

            JSONObject responseJson = new JSONObject(responseBody);
            if (!responseJson.has("choices")) {
                throw new RuntimeException("Phản hồi không có 'choices': " + responseBody);
            }

            return responseJson
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
        }
    }

    
    
}
