package com.bngel.bcy.web

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bngel.bcy.R
import com.bngel.bcy.activity.MainActivity
import com.bngel.bcy.utils.ActivityCollector
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.enums.ReadyState
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONObject
import java.lang.Exception
import java.net.URI

class JWebSocketClient(serverUri: URI): WebSocketClient(serverUri, Draft_6455()) {
    val CHANNEL_ID = 0x00

    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.d("TestLog", "onOpen")
    }

    override fun onMessage(message: String?) {
        Log.d("TestLog", message?:"onMessage Error")
        val json = JSONObject(message?:"")
        val msg = json.get("msg")
        val data = JSONObject(json.get("data").toString())
        val title = data.get("title").toString()
        val content = data.get("content").toString()
        createNotification(ActivityCollector.curActivity!!, title, content, when (msg) {
            "talkInfo" -> 1
            "talkReceive" -> 2
            "systemInfo" -> 3
            "fansInfo" -> 4
            "askInfo" -> 5
            "likeInfo" -> 6
            "commentInfo" -> 7
            "atInfo" -> 8
            else -> 1
        })
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.d("TestLog", "onClose")
    }

    override fun onError(ex: Exception?) {
        ex?.printStackTrace()
        if (ConstantRepository.loginStatus) {
            WebRepository.createWebClient(InfoRepository.user!!.id)
            while (WebRepository.webClient.readyState != ReadyState.OPEN)
                Log.d("TestLog", "连接中")
        }
    }

    private fun createNotification(context: Context, title: String, content: String, type: Int) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel("notification01", "bcyPush", NotificationManager.IMPORTANCE_DEFAULT)
        // Create the TaskStackBuilder
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(when (type){
                1 -> Intent(context, MainActivity::class.java)
                2 -> Intent(context, MainActivity::class.java)
                3 -> Intent(context, MainActivity::class.java)
                4 -> Intent(context, MainActivity::class.java)
                5 -> Intent(context, MainActivity::class.java)
                6 -> Intent(context, MainActivity::class.java)
                7 -> Intent(context, MainActivity::class.java)
                8 -> Intent(context, MainActivity::class.java)
                else -> Intent(context, MainActivity::class.java)
            })
            // Get the PendingIntent containing the entire back stack
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        manager.createNotificationChannel(channel)
        val builder = NotificationCompat.Builder(context, "notification01")
            .setSmallIcon(R.drawable.rem)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(resultPendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(CHANNEL_ID, builder.build())
        }
    }

}