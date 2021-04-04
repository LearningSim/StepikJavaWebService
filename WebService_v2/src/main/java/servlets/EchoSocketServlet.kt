package servlets

import echoSocket.EchoSocket
import org.eclipse.jetty.websocket.servlet.WebSocketServlet
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory
import java.util.concurrent.TimeUnit
import javax.servlet.annotation.WebServlet

@WebServlet
class EchoSocketServlet : WebSocketServlet() {
    override fun configure(factory: WebSocketServletFactory) {
        factory.policy.idleTimeout = TimeUnit.MINUTES.toMillis(10)
        factory.setCreator { _, _ -> EchoSocket() }
    }
}