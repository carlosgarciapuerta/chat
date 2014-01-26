package es.kgp.chat.server.repository;

import es.kgp.chat.server.config.ApplicationConfig;
import es.kgp.chat.server.config.DataSourceTestConfig;
import es.kgp.chat.server.model.Chat;
import es.kgp.chat.server.model.ChatMessage;
import es.kgp.chat.server.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by kgp on 26/01/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, DataSourceTestConfig.class})
@Transactional
public class ChatMessageRepositoryTest {

    private static final String TEST_CHAT = "testChat";
    private static final int NUMBER_OF_MESSAGES = 20;
    private static final int PAGE_SIZE = 10;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Chat chat;

    private List<ChatMessage> messages;

    private User user1;

    private User user2;

    @Before
    public void setup(){
        user1 = new User();
        user1.setNickname("nickname");
        user1.setPassword("password");

        user2 = new User();
        user2.setNickname("nickname2");
        user2.setPassword("password");

        entityManager.persist(user1);
        entityManager.persist(user2);

        chat = new Chat();
        chat.setName(TEST_CHAT);
        chat.setCreationDate(new Date());

        entityManager.persist(chat);

        messages = new ArrayList<>(NUMBER_OF_MESSAGES);
        for (int i = 0; i < NUMBER_OF_MESSAGES; i++) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setChat(chat);
            chatMessage.setText(TEST_CHAT + " message number 1");
            chatMessage.setSent(new Date());
            if (i % 2 == 0){
                chatMessage.setUser(user1);
            } else {
                chatMessage.setUser(user2);
            }
            messages.add(chatMessageRepository.save(chatMessage));
        }
    }

    @Test
    public void should_return_2_pages_of_messages(){
        Pageable pageable1 = new PageRequest(0, PAGE_SIZE);
        Page<ChatMessage> page1 = chatMessageRepository.findMessagesByChatId(chat.getId(), pageable1);
        Pageable pageable2 = new PageRequest(pageable1.getPageNumber() + 1, pageable1.getPageSize());
        Page<ChatMessage> page2 = chatMessageRepository.findMessagesByChatId(chat.getId(), pageable2);

        assertEquals(messages.size(), page1.getTotalElements());
        assertEquals(messages.size(), page2.getTotalElements());

        assertEquals(messages.size() / 2, page1.getNumberOfElements());
        assertEquals(messages.size() / 2, page2.getNumberOfElements());

        assertTrue(page1.hasNextPage());
        assertFalse(page2.hasNextPage());
        assertTrue(page1.isFirstPage());
        assertTrue(page2.isLastPage());
        assertEquals(NUMBER_OF_MESSAGES / PAGE_SIZE, page1.getTotalPages());
        assertEquals(NUMBER_OF_MESSAGES / PAGE_SIZE, page2.getTotalPages());

        assertEquals(messages.get(messages.size() - 1), page1.getContent().get(0));
        assertEquals(messages.get(0), page2.getContent().get(page2.getSize() - 1));

    }

}
