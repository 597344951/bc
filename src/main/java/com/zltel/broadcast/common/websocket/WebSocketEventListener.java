package com.zltel.broadcast.common.websocket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

/**
 * WebSocketEventListener class
 *
 * @author Touss
 * @date 2018/5/18
 */
@Component
public class WebSocketEventListener {
    private static final Log log = LogFactory.getLog(WebSocketEventListener.class);

    @EventListener
    public void handleWebSocketConnectedListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

    }

    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {

    }

    @EventListener
    public void handleWebSocketUnsubscribeListener(SessionUnsubscribeEvent event) {

    }

}
