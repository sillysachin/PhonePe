package me.saket.phonepesaket.data;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * This is the first class that gets created when the app starts
 * and lives through-out the life of the app.
 */
public class PhonePeApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupLocalDataRepository();
    }

    private void setupLocalDataRepository() {
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder(this)
                .name(DataRepository.DATABASE_NAME)
                .schemaVersion(DataRepository.DATABASE_VERSION)
                .setModules(Realm.getDefaultModule())
                .build());
    }

}