package echoSocket

import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import java.util.concurrent.TimeoutException

@WebSocket
class EchoSocket {
    private lateinit var session: Session

    @OnWebSocketConnect
    fun onConnect(session: Session) {
        this.session = session
    }

    @OnWebSocketMessage
    fun onMessage(data: String) {
        session.remote.sendString(data)
    }

    @OnWebSocketError
    fun onError(error: Throwable) {
        if (error.cause is TimeoutException) {
            session.remote.sendString("Соединение разорвано. Время простоя превышено")
        } else {
            session.remote.sendString("${error.message}")
        }
    }
}