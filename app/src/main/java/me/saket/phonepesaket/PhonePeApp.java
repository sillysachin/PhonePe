package me.saket.phonepesaket;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.saket.phonepesaket.data.LocalDataRepository;

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
                .name(LocalDataRepository.DATABASE_NAME)
                .schemaVersion(LocalDataRepository.DATABASE_VERSION)
                .setModules(Realm.getDefaultModule())
                .build());
    }

}