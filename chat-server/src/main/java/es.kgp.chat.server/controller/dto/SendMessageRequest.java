package es.kgp.chat.server.controller.dto;

/**
 * Created by kgp on 26/01/2014.
 */
public class SendMessageRequest {

    private Long chatId;

    private String text;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
