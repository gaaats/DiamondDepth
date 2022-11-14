package com.ngelgames.herocantar

import android.app.Application
import com.my.tracker.MyTracker
import com.onesignal.OneSignal
import com.orhanobut.hawk.Hawk
import java.util.*

class GoodGoodClass : Application() {

    companion object {
        const val SDK_KEY = "47134284043887555922"

        const val jsoupCheck = "1v4b"
        const val ONESIGNAL_APP_ID = "09aecc3e-8f44-4a61-983d-919d2224ca7e"

        val linkFilterPart1 = "http://diamond"
        val linkFilterPart2 = "depth.xyz/go.php?to=1&"
        var MYID: String? = "myID"
        var INSTID: String? = "instID"

    }

    override fun onCreate() {
        super.onCreate()

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        Hawk.init(this).build()

        val settings = getSharedPreferences("PREFS_NAME", 0)

        val trackerParams = MyTracker.getTrackerParams()
        val trackerConfig = MyTracker.getTrackerConfig()
        val instID = MyTracker.getInstanceId(this)
        trackerConfig.isTrackingLaunchEnabled = true

        if (settings.getBoolean("my_first_time", true)) {
            val IDIN = UUID.randomUUID().toString()
            trackerParams.setCustomUserId(IDIN)
            Hawk.put(MYID, IDIN)
            Hawk.put(INSTID, instID)
            settings.edit().putBoolean("my_first_time", false).apply()
        } else {
            val IDIN = Hawk.get(MYID, "null")
            trackerParams.setCustomUserId(IDIN)
        }
        MyTracker.initTracker(SDK_KEY, this)
    }
}


