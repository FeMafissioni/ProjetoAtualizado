package br.pro.mateus.authnotify.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import br.pro.mateus.authnotify.R
import br.pro.mateus.authnotify.datastore.UserPreferencesRepository
import br.pro.mateus.authnotify.emergency.EmergencyFragment
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class DefaultMessageService : FirebaseMessagingService() {

    private lateinit var userPreferencesRepository: UserPreferencesRepository

    /***
     * Evento disparado automaticamente
     * quando o FCM envia uma mensagem. Lembrando que este serviço
     * "DefaultMessageService" está registrado no Manifesto como um serviço.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val msgData = remoteMessage.data
        val msg = msgData["Emergency"]
        showEmergencyNotification(msg!!, msgData)
    }
    override fun onNewToken(token: String) {
        Log.d("DefaultMessageService", "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    /**
     * Atualizar o FCM token caso tenha mudado.
     * Sem implementação para este exemplo.
     */
    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d("DefaultMessageService", "sendRegistrationTokenToServer($token)")
        // Guardar apenas no DataStore. Vamos manipular isso sempre no Login ou criação de conta.
        userPreferencesRepository = UserPreferencesRepository.getInstance(this)
        userPreferencesRepository.updateFcmToken(token!!)

    }

    /***
     * Este método cria uma Intent
     * para a activity Main, vinculada a notificação.
     * ou seja, quando acontecer a notificação, se o usuário clicar,
     * abrirá a activity Main.
     * Trabalhar isso para que dependendo da mensagem,
     * você poderá abrir uma ou outra activity
     * ou enviar um parametro na Intent para tratar qual fragment abrir.(desafio para vc fazer)
     */
    private fun showEmergencyNotification(messageBody: String, messageData: Map<String, String>) {
        val intent = Intent(this, EmergencyFragment::class.java)
        intent.action = "actionstring" + System.currentTimeMillis()
        intent.putExtra("nome", messageData["nome"])
        intent.putExtra("telefone", messageData["telefone"])
        intent.putExtra("fotos", messageData["fotos"])
        intent.putExtra("status", messageData["status"])
        intent.putExtra("id", messageData["id"])
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_IMMUTABLE)
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_check)
            .setContentTitle(getString(R.string.fcm_default_title_message))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Since android Oreo notification channel is needed.
        val channel = NotificationChannel(channelId,
            "Channel human readable title",
            NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}
