package es.kgp.chat.server.controller;

import es.kgp.chat.server.model.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by kgp on 19/01/2014.
 */
@Controller
public class FriendsController {

    @RequestMapping(value = "/friends", method = RequestMethod.POST)
    @ResponseBody
    public String findAllFriends(Session session) throws Exception {
        System.out.println(session);
        return "sadasdas";
    }

}
