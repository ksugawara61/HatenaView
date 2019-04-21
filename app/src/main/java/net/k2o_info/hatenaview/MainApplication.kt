package net.k2o_info.hatenaview

import android.app.Application
import android.arch.persistence.room.Room
import net.k2o_info.hatenaview.model.AppDatabase
import timber.log.Timber

/**
 * アプリケーションクラス
 *
 * @author katsuya
 * @since 1.0.0
 */
class MainApplication : Application() {

    companion object {
        lateinit var database: AppDatabase
    }

    /**
     * アプリケーション起動時に呼ばれるメソッド
     */
    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, AppDatabase::class.java, Constant.APP_DABASE_NAME)
            .build()

        if (BuildConfig.DEBUG) {
            // デバック時は Timberでログ出力
            Timber.plant(Timber.DebugTree())
        }
    }

}