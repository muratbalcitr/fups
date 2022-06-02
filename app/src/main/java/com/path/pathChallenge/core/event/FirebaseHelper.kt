package com.path.pathChallenge.core.event

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

object FirebaseHelper {

    fun generateFirebaseEventParams(
        screenName: String? = null,
        characterId: String? = null,
        characterName: String? = null,

    ): Bundle {

        val bundle = Bundle()
        if (characterId != null)
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, characterId)
        if (screenName != null)
            bundle.putString("screen_name", screenName)
        if (characterName != null)
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, characterName)

        return bundle
    }
}
