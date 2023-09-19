package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;

/*
 * DONE
 * 
 * 
 */

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    MessageService messageService;
    AccountService accountService;

    public SocialMediaController() {
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/messages", this::messageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("/accounts/{account_id}/messages", this::getUserMessages);
        app.post("/register", this::accountHandler);
        app.post("/login", this::loginHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * 
     * @param context The Javalin Context object manages information about both the
     *                HTTP request and response.
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
/*
    * Message Handlers
    */
    private void messageHandler(Context ctx) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        
        if (addedMessage != null) {
            ctx.json(mapper.writeValueAsString(addedMessage));
        } else
            ctx.status(400);

    }

    private void getAllMessagesHandler(Context ctx) {
        ArrayList<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private String getMessageById(Context ctx) throws URISyntaxException, IOException {
        int id = Integer.parseInt(Objects.requireNonNull((ctx.pathParam("message_id"))));
        Message foundMessage = messageService.retrieveMessage(id);
        
        if (foundMessage != null) {
            ctx.status(200).json(foundMessage);
        }
        return null;
    }

    private void deleteMessageById(Context ctx) throws URISyntaxException, IOException {
        int id = Integer.parseInt(Objects.requireNonNull((ctx.pathParam("message_id"))));
        Message messageToBeDeleted = messageService.retrieveMessage(id);
        
        if (messageToBeDeleted != null) {
            ctx.status(200).json(messageToBeDeleted);
            messageService.deleteMessage(id);
        } else
            ctx.status(200).json("");

    }

    private void updateMessage(Context ctx) throws URISyntaxException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int id = Integer.parseInt(Objects.requireNonNull((ctx.pathParam("message_id"))));
        Message messageToBeUpdated = messageService.retrieveMessage(id);
        Message updatedMessage = (messageService.updateMessage(messageToBeUpdated, message));
        
        if (updatedMessage != null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(updatedMessage));
        } else
            ctx.status(400);

    }

    private void getUserMessages(Context ctx) throws URISyntaxException, IOException {
        int id = Integer.parseInt(Objects.requireNonNull((ctx.pathParam("account_id"))));
        ArrayList<Message> foundMessages = new ArrayList<>();

        if (messageService.retrieveMessageByID(id) != null) {
            for (Message message : messageService.retrieveMessageByID(id)) {
                if (message.posted_by == id) {
                    foundMessages.add(message);
                }
            }
        }
        ctx.status(200).json(foundMessages);
    }
     /*
      *  Account Handlers
      */
    private void accountHandler(Context ctx) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);

        if (addedAccount != null) {
            ctx.status(200).json(mapper.writeValueAsString(addedAccount));
        } else
            ctx.status(400);

    }

    private void loginHandler(Context ctx) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account foundAccount = accountService.getAccount(account);
        
        if (foundAccount.username != null) {
            ctx.status(200).json(foundAccount);
        } else
            ctx.status(401);

    }
    
}