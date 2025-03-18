package com.amancode.blogappserver.Services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.amancode.blogappserver.Payloads.PostDTO;
import com.amancode.blogappserver.Services.PostService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OllamaService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String OLLAMA_API_URL = "http://localhost:11434/api/generate";
    
    @Autowired
    private PostService postService; // Fetch blog categories and posts

    public Map<String, String> checkAIContent(String text) {
        try {
            // Construct request payload
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "mistral");
            requestBody.put("prompt",
                    "Analyze the following text and determine if it is AI-generated or human-written. "
                            + "Provide a JSON response with percentage confidence:\n"
                            + "{ \"AI_generated\": \"x%\", \"Human\": \"y%\" } "
                            + "Ensure the total is 100%.\n\n"
                            + "Text: \"" + text + "\"");
            requestBody.put("stream", false); // Disable streaming for structured response

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create HTTP entity
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // Make API call
            ResponseEntity<String> response = restTemplate.exchange(OLLAMA_API_URL, HttpMethod.POST, entity,
                    String.class);

            // Debugging: Log raw response
            System.out.println("Raw Ollama Response: " + response.getBody());

            // Parse AI response
            return parseAIResponse(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", "Failed to process AI detection request.");
        }
    }

    private Map<String, String> parseAIResponse(String rawResponse) {
        try {
            JsonNode jsonNode = objectMapper.readTree(rawResponse);
            Map<String, String> result = new HashMap<>();

            // Extract confidence values
            if (jsonNode.has("response")) {
                String aiAnalysis = jsonNode.get("response").asText();

                // Basic parsing (you may need a more robust JSON parser)
                if (aiAnalysis.contains("AI_generated")) {
                    String aiPercentage = aiAnalysis.split("AI_generated\": \"")[1].split("%")[0];
                    String humanPercentage = aiAnalysis.split("Human\": \"")[1].split("%")[0];

                    result.put("AI_generated", aiPercentage + "%");
                    result.put("Human", humanPercentage + "%");
                } else {
                    result.put("error", "Unexpected response format.");
                }
            } else {
                result.put("error", "Invalid JSON structure.");
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", "Failed to parse AI response.");
        }
    }



  public String chatWithAI(String userInput, Integer userId) {
        // Fetch all posts by user
        List<PostDTO> userPosts = postService.getPostsByUser(userId);

        if (userInput.toLowerCase().contains("suggest blog topics")) {
            return generateBlogSuggestions(userPosts);
        } else {
            return handleGeneralChat(userInput);
        }
    }

    private String generateBlogSuggestions(List<PostDTO> userPosts) {
        if (userPosts.isEmpty()) {
            return "You haven't written any posts yet. Start writing and I'll suggest topics!";
        }

        // Count posts per category
        Map<String, Long> categoryCount = userPosts.stream()
                .collect(Collectors.groupingBy(post -> post.getCategory().getCategoryTitle(), Collectors.counting()));

        // Get top 3 categories
        List<String> topCategories = categoryCount.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue())) // Sort by highest count
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Request AI for topic ideas
        String prompt = "Suggest 3 unique blog topics for each of the following categories: "
                + String.join(", ", topCategories) + ".";

        return fetchAIResponse(prompt);
    }

    private String handleGeneralChat(String userMessage) {
        return fetchAIResponse(userMessage);
    }

    private String fetchAIResponse(String prompt) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "mistral");
            requestBody.put("prompt", prompt);
            requestBody.put("stream", false);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(OLLAMA_API_URL, HttpMethod.POST, entity, String.class);

            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return jsonNode.has("response") ? jsonNode.get("response").asText() : "Couldn't fetch AI response.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error in processing AI request.";
        }
    }
   
}
