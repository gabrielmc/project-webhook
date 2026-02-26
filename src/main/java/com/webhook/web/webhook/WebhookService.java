package com.webhook.web.webhook;

@Service
@Slf4j
public class WebhookService {

    private final WebClient webClient;

    @Value("${webhook.url}")
    private String webhookUrl;

    public WebhookService(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public void send(String event, Object payload) {
        Map<String, Object> body = Map.of(
            "event", event,
            "timestamp", LocalDateTime.now().toString(),
            "data", payload
        );

        webClient.post()
            .uri(webhookUrl)
            .bodyValue(body)
            .retrieve()
            .bodyToMono(String.class)
            .doOnSuccess(res -> log.info("Webhook enviado: {}", event))
            .doOnError(err -> log.error("Erro no webhook: {}", err.getMessage()))
            .subscribe(); // async, não bloqueia
    }
}