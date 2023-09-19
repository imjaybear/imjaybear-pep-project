package DAO;

import Util.ConnectionUtil;
import Model.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/*
 * DONE
 * 
 */
public class MessageDAO {
    public ArrayList<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        ArrayList<Message> messages = new ArrayList<>();
        try {

            String sql = "SELECT * from message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public static Message getMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        Message result = new Message();
        try {

            String sql = "SELECT * from message WHERE message_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message.message_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                result = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            int generatedkey = 0;

            String sql = "INSERT INTO message (message_text, posted_by) VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, message.message_text);
            preparedStatement.setInt(2, message.posted_by);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {

                generatedkey = rs.getInt(1);
                message.setMessage_id(generatedkey);
            }
            return message;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message getMessageById(int id) {
        Connection connection = ConnectionUtil.getConnection();
        Message result = new Message();
        String sql = "SELECT * FROM message WHERE message_id=?";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
            }
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageById(int id) {
        Connection connection = ConnectionUtil.getConnection();
        Message result = new Message();
        String sql = "DELETE FROM message WHERE message_id=?";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
            }
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Message updateMessageById(Message message, Message newMessage) {
        Message messages = new Message();
        messages.setMessage_id(message.message_id);
        messages.setPosted_by(message.posted_by);
        messages.setMessage_text(newMessage.message_text);
        messages.setTime_posted_epoch(message.time_posted_epoch);
        return messages;
    }

    public ArrayList<Message> getMessageByUserId(int id) {
        Connection connection = ConnectionUtil.getConnection();
        ArrayList<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM message WHERE message_id=?";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                messages.add(new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")));
            }
            return messages;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
}