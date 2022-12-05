package com.relyon.feedme;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.relyon.feedme.model.User;

public class Util {

    private static User user;
    private static FirebaseUser fbUser;
    private static FirebaseFirestore db;

    public Util() {
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Util.user = user;
    }

    public static FirebaseUser getFbUser() {
        return fbUser;
    }

    public static void setFbUser(FirebaseUser fbUser) {
        Util.fbUser = fbUser;
    }

    public static FirebaseFirestore getDb() {
        return db;
    }

    public static void setDb(FirebaseFirestore db) {
        Util.db = db;
    }
}
