package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ChatUI extends JFrame {
    private final ChatService chatService;
    private final JTextArea chatArea;
    private final JTextField inputField;

    private final JButton sendButton;
    private final List<String> conversationHistory;

    public ChatUI() {
        chatService = new ChatService();
        conversationHistory = new ArrayList<>();
        inputField = new JTextField();
        sendButton = new JButton("Send");
        chatArea = new JTextArea();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("OpenAI Chat Interface");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> {
            String userMessage = inputField.getText();
            if(!userMessage.isEmpty()){
                sendMessage(userMessage);
                inputField.setText("");
            }
        });
        inputField.addActionListener(e -> {
            String userMessage = inputField.getText();
            if(!userMessage.isEmpty()){
                sendMessage(userMessage);
                inputField.setText("");
            }
        });
    }

    private void sendMessage(String userMessage) {
        conversationHistory.add("You: " + userMessage);
        chatArea.append("You: " + userMessage + "\n");

        String aiResponse = chatService.callOpenAi(conversationHistory, userMessage);

        conversationHistory.add("OpenAI: " + aiResponse);
        chatArea.append("OpenAI: " + aiResponse + "\n");

        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatUI chatUI = new ChatUI();
            chatUI.setVisible(true);
        });
    }
}
