package com.kukuhsain.simpletour.host.model.local;

import com.kukuhsain.simpletour.host.model.pojo.Destination;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by kukuh on 04/11/16.
 */

public class RealmHelper {
    private static RealmHelper INSTANCE;

    private RealmHelper() {}

    public static RealmHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RealmHelper();
        }
        return INSTANCE;
    }

    public void addDestination(Destination destination) {
        List<Destination> destinations = getAllDestinations();
        boolean isExisted = false;
        for (Destination destination1 : destinations) {
            if (destination.getDestinationId() == destination1.getDestinationId()) {
                isExisted = true;
            }
        }
        if (!isExisted) {
            Realm.getDefaultInstance().executeTransaction(realm -> {
                realm.copyToRealm(destination);
            });
        }
    }

    public void addDestinations(List<Destination> destinations) {
        List<Destination> realmDestinations = getAllDestinations();
        for (Destination destination : destinations) {
            boolean isExisted = false;
            for (Destination realmDestination : realmDestinations) {
                if (realmDestination.getDestinationId() == destination.getDestinationId()) {
                    isExisted = true;
                }
            }
            if (!isExisted) {
                Realm.getDefaultInstance().executeTransaction(realm -> {
                    realm.copyToRealm(destination);
                });
            }
        }
    }

    public List<Destination> getAllDestinations() {
        RealmResults<Destination> iterableDestinations = Realm.getDefaultInstance()
                .where(Destination.class).findAll();
        return Realm.getDefaultInstance().copyFromRealm(iterableDestinations);
    }
}
