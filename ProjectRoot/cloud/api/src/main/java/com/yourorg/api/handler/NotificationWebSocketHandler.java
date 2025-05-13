package com.yourorg.api.handler;

import com.yourorg.api.util.CompressionUtil;
import com.yourorg.api.util.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NotificationWebSocketHandler extends TextWebSocketHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationWebSocketHandler.class);
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    
    private final EncryptionUtil encryptionUtil;
    private final CompressionUtil compressionUtil;
    
    @Autowired
    public NotificationWebSocketHandler(EncryptionUtil encryptionUtil, CompressionUtil compressionUtil) {
        this.encryptionUtil = encryptionUtil;
        this.compressionUtil = compressionUtil;
    }
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.info("WebSocket connection established: {}", session.getId());
        sessions.put(session.getId(), session);
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.debug("Received message: {}", payload);
        
        // If the client sends an encrypted and compressed message, we would decrypt and decompress it here
        // For example:
        // String decompressedMessage = compressionUtil.decompress(payload);
        // String decryptedMessage = encryptionUtil.decrypt(decompressedMessage);
        
        // Echo the message back for now (could implement command processing here)
        sendMessage(session, payload);
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.info("WebSocket connection closed: {}, status: {}", session.getId(), status);
        sessions.remove(session.getId());
    }
    
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.error("WebSocket transport error: {}", exception.getMessage(), exception);
        sessions.remove(session.getId());
    }
    
    /**
     * Sends a secure (encrypted and compressed) message to a specific session
     */
    public void sendMessage(WebSocketSession session, String message) throws IOException {
        if (session != null && session.isOpen()) {
            // Encrypt and compress the message before sending
            String encryptedMessage = encryptionUtil.encrypt(message);
            String compressedMessage = compressionUtil.compress(encryptedMessage);
            
            session.sendMessage(new TextMessage(compressedMessage));
            logger.debug("Sent secure message to session: {}", session.getId());
        }
    }
    
    /**
     * Broadcasts a secure message to all connected sessions
     */
    public void broadcastMessage(String message) {
        String encryptedMessage = encryptionUtil.encrypt(message);
        String compressedMessage = compressionUtil.compress(encryptedMessage);
        
        sessions.values().stream()
                .filter(WebSocketSession::isOpen)
                .forEach(session -> {
                    try {
                        session.sendMessage(new TextMessage(compressedMessage));
                        logger.debug("Broadcast secure message to session: {}", session.getId());
                    } catch (IOException e) {
                        logger.error("Error sending message to session {}: {}", session.getId(), e.getMessage(), e);
                    }
                });
    }
} 