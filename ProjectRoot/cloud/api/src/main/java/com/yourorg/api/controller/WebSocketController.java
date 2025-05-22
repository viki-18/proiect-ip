package com.yourorg.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebSocketController {

    /**
     * Serves the secure WebSocket test page
     * 
     * @return The name of the HTML template to render
     */
    @GetMapping("/secure-ws")
    public String secureWebSocketPage() {
        return "redirect:/secure-websocket.html";
    }
    
    /**
     * Redirects to the WebSocket test page
     * 
     * @return Redirect to the secure WebSocket page
     */
    @GetMapping("/ws-test")
    public String wsTestRedirect() {
        return "redirect:/secure-ws";
    }
} 