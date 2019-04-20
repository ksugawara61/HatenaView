package net.k2o_info.hatenaview

import android.app.Application
import timber.log.Timber

/**
 * アプリケーションクラス
 *
 * @author katsuya
 * @since 1.0.0
 */
class MainApplication : Application() {

    /**
     * アプリケーション起動時に呼ばれるメソッド
     */
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            // デバック時は Timberでログ出力
            Timber.plant(Timber.DebugTree())
        }
    }

}