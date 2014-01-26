package es.kgp.chat.server.controller;

import es.kgp.chat.server.controller.dto.FriendshipRequest;
import es.kgp.chat.server.model.Session;
import es.kgp.chat.server.model.User;
import es.kgp.chat.server.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by kgp on 19/01/2014.
 */
@Controller
@RequestMapping("/friend")
public class FriendsController {

    @Autowired
    private FriendsService friendsService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public List<User> findAllFriends(Session session) throws Exception {
        return friendsService.findAllFriends(session.getUser().getId());
    }

    @RequestMapping(value = "/request", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendFriendRequest(@RequestBody FriendshipRequest friendshipRequest, Session session){
        friendsService.sendFriendRequest(session.getUser().getId(), friendshipRequest.getFriendNickName());
    }

    @RequestMapping(value = "/request", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void acceptFriendRequest(@RequestBody FriendshipRequest friendshipRequest, Session session){
        friendsService.acceptFriendRequest(session.getUser().getId(), friendshipRequest.getFriendNickName());
    }

    @RequestMapping(value = "/request", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelFriendRequest(@RequestBody FriendshipRequest friendshipRequest, Session session){
        friendsService.cancelFriendRequest(session.getUser().getId(), friendshipRequest.getFriendNickName());
    }

    @RequestMapping(value = "/requests", method = RequestMethod.GET)
    public void findPendingFriendRequests(Session session){
        friendsService.findAllFriendRequests(session.getUser().getId());
    }




}
