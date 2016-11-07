package com.kukuhsain.simpletour.host.model.remote;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import timber.log.Timber;

/**
 * Created by kukuh on 07/11/16.
 */

public class FireIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        Timber.d("firebase ID get token...");
        Timber.d(FirebaseInstanceId.getInstance().getId());
        Timber.d(FirebaseInstanceId.getInstance().getToken());
        Timber.d(""+FirebaseInstanceId.getInstance().getCreationTime());
    }
}
