package com.fitflow.user.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FitFlowMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "새 토큰 발급: $token")
        // TODO: 백엔드 API 준비되면 core-network의 Retrofit으로 이 토큰을 서버에 전송
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("FCM", "메시지 수신: ${message.notification?.title} / ${message.notification?.body}")
        // TODO: 실제 알림(Notification) 표시는 알림 채널 설정 후 다음 단계에서 추가
    }
}