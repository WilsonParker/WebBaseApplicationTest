package com.dev.hare.daisowebviewtest

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.dev.hare.apputilitymodule.util.VersionChecker
import com.dev.hare.webbasetemplatemodule.util.CommonUtil
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleBasicTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        // assertEquals("com.dev.hare.daisowebviewtest", appContext.packageName)

        println(VersionChecker.getMarketVersion(appContext.packageName))
        println(VersionChecker.getMarketVersionFast(appContext.packageName))
        println(VersionChecker.getVersionInfo(appContext))
        println(CommonUtil.getDeviceUUID(appContext))
    }
}
