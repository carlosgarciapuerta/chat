package es.kgp.chat.server.controller;

import es.kgp.chat.server.controller.dto.SendMessageRequest;
import es.kgp.chat.server.controller.dto.StartChatRequest;
import es.kgp.chat.server.controller.dto.StartChatResponse;
import es.kgp.chat.server.model.Chat;
import es.kgp.chat.server.model.ChatMessage;
import es.kgp.chat.server.model.Session;
import es.kgp.chat.server.model.User;
import es.kgp.chat.server.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by kgp on 26/01/2014.
 */
@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public StartChatResponse startChat(@RequestBody StartChatRequest request, Session session) throws Exception {
        Chat chat = chatService.startChat(session.getUser().getId(), request.getFriendsNickname());
        StartChatResponse startChatResponse = new StartChatResponse();
        startChatResponse.setId(chat.getId());
        startChatResponse.setName(chat.getName());
        startChatResponse.setCreationDate(chat.getCreationDate());
        return startChatResponse;
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    @ResponseBody
    public Date sendMessage(@RequestBody SendMessageRequest request, Session session) throws Exception {
        ChatMessage chatMessage = chatService.sendMessage(session.getUser().getId(), request.getChatId(), request.getText());
        return chatMessage.getSent();
    }

    @RequestMapping(value = "/messages/${chatId}/${page}", method = RequestMethod.GET)
    @ResponseBody
    public Page<ChatMessage> findMessages(@PathVariable Long chatId, @PathVariable int page, Session session) throws Exception {
        Page<ChatMessage> messages = chatService.findMessages(chatId, page, session.getUser().getId());
        return messages;
    }





}
