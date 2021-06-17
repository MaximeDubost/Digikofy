package fr.maximedubost.digikofyapp.session

import android.content.Context
import android.content.SharedPreferences
import fr.maximedubost.digikofyapp.models.LoginResponseModel

class DigikofySession {
    companion object {
        private const val SESSION = "session"
        private const val EMAIL = "email"
        private const val EXPIRES_IN = "expiresIn"
        private const val ID_TOKEN = "idToken"
        private const val LOCAL_ID = "localId"
        private const val REFRESH_TOKEN = "refreshToken"
        private const val REGISTERED = "registered"
        private const val IS_LOGGED_IN = "isLoggedIn"

        /**
         * Create new session
         * @param context context
         * @param loginResponseModel login response data object
         */
        fun create(context: Context, loginResponseModel: LoginResponseModel) {
            context
                .getSharedPreferences(SESSION, 0)
                .edit()
                .putString(EMAIL, loginResponseModel.email)
                .putString(EXPIRES_IN, loginResponseModel.expiresIn)
                .putString(ID_TOKEN, loginResponseModel.idToken)
                .putString(LOCAL_ID, loginResponseModel.localId)
                .putString(REFRESH_TOKEN, loginResponseModel.refreshToken)
                .putBoolean(REGISTERED, loginResponseModel.registered)
                .putBoolean(IS_LOGGED_IN, true)
                .apply()
        }

        /**
         * Get session to map
         * @param context context
         * @return session to map
         */
        fun toMap(context: Context): HashMap<String, Any?> =
            hashMapOf(
                EMAIL to context
                    .getSharedPreferences(SESSION, 0)
                    .getString(EMAIL, null),
                EXPIRES_IN to context
                    .getSharedPreferences(SESSION, 0)
                    .getString(EXPIRES_IN, null),
                ID_TOKEN to context
                    .getSharedPreferences(SESSION, 0)
                    .getString(ID_TOKEN, null),
                LOCAL_ID to context
                    .getSharedPreferences(SESSION, 0)
                    .getString(LOCAL_ID, null),
                REFRESH_TOKEN to context
                    .getSharedPreferences(SESSION, 0)
                    .getString(REFRESH_TOKEN, null),
                REGISTERED to context
                    .getSharedPreferences(SESSION, 0)
                    .getBoolean(REGISTERED, false)
            )

        /**
         * Clear session
         * @param context context
         */
        fun clear(context: Context) {
            context
                .getSharedPreferences(SESSION, 0)
                .edit()
                .clear()
                .apply()
        }

        /**
         * Allows to know if session is logged in
         * @param context context
         * @return true if session is logged in, false otherwise
         */
        fun isLoggedIn(context: Context): Boolean =
            context
                .getSharedPreferences(SESSION, 0)
                .getBoolean(IS_LOGGED_IN, false)


        /**
         * Allows to know if session exists
         * @param context context
         * @return true if session exists, false otherwise
         */
        fun exists(context: Context): Boolean =
            context
                .getSharedPreferences(SESSION, 0)
                .getString(EMAIL, null) != null &&
            context
                .getSharedPreferences(SESSION, 0)
                .getString(EXPIRES_IN, null) != null &&
            context
                .getSharedPreferences(SESSION, 0)
                .getString(ID_TOKEN, null) != null &&
            context
                .getSharedPreferences(SESSION, 0)
                .getString(LOCAL_ID, null) != null &&
            context
                .getSharedPreferences(SESSION, 0)
                .getString(REFRESH_TOKEN, null) != null &&
            context
                .getSharedPreferences(SESSION, 0)
                .getBoolean(REGISTERED, false)

        /**
         * Get email
         * @param context context
         * @return email
         */
        fun getEmail(context: Context): String? =
            context
                .getSharedPreferences(SESSION, 0)
                .getString(EMAIL, null)

        /**
         * Get idToken
         * @param context context
         * @return idToken
         */
        fun getIdToken(context: Context): String? =
            context
                .getSharedPreferences(SESSION, 0)
                .getString(ID_TOKEN, null)

        /**
         * Set idToken
         * @param context context
         * @param idToken idToken
         */
        fun setIdToken(context: Context, idToken: String) {
            context
                .getSharedPreferences(SESSION, 0)
                .edit()
                .putString(REFRESH_TOKEN, idToken)
                .apply()
        }

        /**
         * Get refreshToken
         * @param context context
         * @return refreshToken
         */
        fun getRefreshToken(context: Context): String? =
            context
                .getSharedPreferences(SESSION, 0)
                .getString(REFRESH_TOKEN, null)

        /**
         * Set refreshToken
         * @param context context
         * @param refreshToken refreshToken
         */
        fun setRefreshToken(context: Context, refreshToken: String) {
            context
                .getSharedPreferences(SESSION, 0)
                .edit()
                .putString(REFRESH_TOKEN, refreshToken)
                .apply()
        }
    }
}