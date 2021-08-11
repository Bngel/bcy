package com.bngel.bcy.web

import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONObject
import java.lang.Exception
import java.net.URI

class JWebSocketClient(serverUri: URI): WebSocketClient(serverUri, Draft_6455()) {
    val CHANNEL_ID = 0x00

    override fun onOpen(handshakedata: ServerHandshake?) {
    }

    override fun onMessage(message: String?) {
        val json = JSONObject(message?:"")
        val msg = json.get("msg")
        when (msg) {

        }
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
    }

    override fun onError(ex: Exception?) {
        ex?.printStackTrace()
    }

}