@file:Suppress("unused")

package com.eaglesakura.armyknife.android.extensions

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * await Firebase auth.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/army-knife
 */
suspend fun FirebaseAuth.awaitLogin(): FirebaseUser = withContext(Dispatchers.Main) {
    while (currentUser == null) {
        delay(1)
    }
    currentUser!!
}

/**
 * await Firebase auth.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/army-knife
 */
suspend fun FirebaseAuth.awaitLogout() = withContext(Dispatchers.Main) {
    while (currentUser != null) {
        delay(1)
    }
}

/**
 * Syntax sugar to Sign-In with Google Account.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/army-knife
 */
fun FirebaseAuth.signIn(account: GoogleSignInAccount) =
    signInWithCredential(GoogleAuthProvider.getCredential(account.idToken, null))
