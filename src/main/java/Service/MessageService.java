package Service;

import DAO.MessageDAO;
import Model.Message;
import java.util.ArrayList;

/*
 * DONE
 * 
 */
public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public ArrayList<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message addMessage(Message message) {
        Message newMessage = new Message();

        if (message.message_text != "" && message.message_text.length() < 255) {
            newMessage = MessageDAO.insertMessage(message);
        } else
            return null;

        return newMessage;
    }

    public Message retrieveMessage(int id) {
        ArrayList<Message> messages = new ArrayList<>();
        messages = messageDAO.getAllMessages();
        Message foundMessage = new Message();

        for (Message message : messages) {
            if (message.message_id == id) {
                foundMessage = messageDAO.getMessageById(id);
            } else
                return null;
        }
        return foundMessage;
    }

    public Message deleteMessage(int id) {
        ArrayList<Message> messages = new ArrayList<>();
        messages = messageDAO.getAllMessages();
        Message deletedMessage = new Message();

        for (Message message : messages) {
            if (message.message_id == id) {
                deletedMessage = messageDAO.deleteMessageById(id);
            } else
                return null;
        }
        return deletedMessage;
    }

    public Message updateMessage(Message message, Message newMessage) {
        ArrayList<Message> messages = new ArrayList<>();
        messages = messageDAO.getAllMessages();

        if (messages.contains(message) && newMessage.message_text != "" && newMessage.message_text.length() < 255) {
            return (MessageDAO.updateMessageById(message, newMessage));
        } else
            return null;
    }

    public ArrayList<Message> retrieveMessageByID(int id) {
        ArrayList<Message> messages = new ArrayList<>();
        messages = messageDAO.getAllMessages();
        ArrayList<Message> name = new ArrayList<>();

        for (Message message : messages) {
            if (message.posted_by == id) {
                name.addAll(messageDAO.getMessageByUserId(message.posted_by));
            } else
                return null;
        }
        return name;
    }
}