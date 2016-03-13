package me.saket.phonepesaket.data;

import io.realm.Realm;

/**
 * Handles storing and retrieving app's data.
 * Should only be accessed through managers, like {@link TransactionManager}.
 */
public class DataRepository {

    private Realm mRealm;

    public DataRepository(Realm realm) {
        mRealm = realm;
    }

}