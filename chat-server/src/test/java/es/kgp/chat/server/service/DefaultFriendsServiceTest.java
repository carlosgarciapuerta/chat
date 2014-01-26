package es.kgp.chat.server.service;

import es.kgp.chat.server.model.User;
import es.kgp.chat.server.model.UserFriend;
import es.kgp.chat.server.repository.UserFriendRepository;
import es.kgp.chat.server.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by kgp on 26/01/2014.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultFriendsServiceTest {

    @InjectMocks
    private DefaultFriendsService defaultFriendsService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserFriendRepository userFriendRepository;

    @Mock
    private User friend;

    @Mock
    private User user;

    @Test
    public void testSendFriendRequest() throws Exception {
        when(userRepository.findByNickname(anyString())).thenReturn(friend);
        when(userRepository.findOne(anyLong())).thenReturn(user);
        UserFriend friendRequest = new UserFriend();
        friendRequest.setAccepted(false);
        friendRequest.setFriend(user);
        friendRequest.setFriendOf(friend);
        when(userFriendRepository.save(friendRequest)).thenReturn(friendRequest);

        Long userId = 1L;
        String aNickname = "aNickname";

        defaultFriendsService.sendFriendRequest(userId, aNickname);

        verify(userRepository).findByNickname(aNickname);
        verify(userRepository).findOne(userId);
        verify(userFriendRepository).save(friendRequest);
    }
}
