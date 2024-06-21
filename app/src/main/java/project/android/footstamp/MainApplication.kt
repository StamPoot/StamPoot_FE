package project.android.footstamp

import android.app.Application
import project.android.footstamp.BuildConfig
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
//        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}