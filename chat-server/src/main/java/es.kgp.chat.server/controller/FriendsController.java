package es.kgp.chat.server.controller;

import es.kgp.chat.server.controller.dto.FriendshipRequest;
import es.kgp.chat.server.model.Session;
import es.kgp.chat.server.model.User;
import es.kgp.chat.server.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by kgp on 19/01/2014.
 */
@Controller
public class FriendsController {

    @Autowired
    private FriendsService friendsService;

    @RequestMapping(value = "/friends", method = RequestMethod.POST)
    @ResponseBody
    public List<User> findAllFriends(Session session) throws Exception {
        return friendsService.findAllFriends(session.getUser().getId());
    }

    @RequestMapping(value = "/askForFriendship", method = RequestMethod.POST)
    @ResponseBody
    public void askForFriendship(@RequestBody FriendshipRequest friendshipRequest, Session session){
        //friendsService.askForFriendship(session.getUser(), friendshipRequest.getFriendNickName());
    }

}
