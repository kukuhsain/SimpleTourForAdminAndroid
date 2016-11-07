package com.kukuhsain.simpletour.host.model.remote;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import timber.log.Timber;

/**
 * Created by kukuh on 07/11/16.
 */

public class FireService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Timber.d("Message from FCM received...");
        Timber.d(remoteMessage.getMessageId());
        Timber.d(remoteMessage.getMessageType());
        Timber.d(remoteMessage.getFrom());
        Timber.d(remoteMessage.getTo());
        Timber.d(remoteMessage.getNotification().getTitle());
        Timber.d(remoteMessage.getNotification().getBody());
        Timber.d(remoteMessage.getNotification().getIcon());
    }
}
