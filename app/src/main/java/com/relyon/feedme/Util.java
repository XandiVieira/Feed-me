package com.relyon.feedme;

import com.google.firebase.auth.FirebaseUser;
import com.relyon.feedme.model.User;

public class Util {

    public static User user;
    public static FirebaseUser fbUser;

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
}
