package net.k2o_info.hatenaview.view.binding

import android.databinding.BindingAdapter
import android.widget.TextView
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * TextView用BindingAdapter
 *
 * @author katsuya
 * @since 1.0.0
 */
object TextViewBindingAdapter {

    private const val MINUTE_SECONDS = 60
    private const val HOUR_SECONDS   = 3600
    private const val DAY_SECONDS    = 86400

    /**
     * Date型の値を文字列に変換
     *
     * @param view 描画するビュー
     * @param date 配信日時
     */
    @JvmStatic
    @BindingAdapter(value = ["publishedAt"])
    fun dateToPublishedAt(view: TextView, date: Date) {
        val now = System.currentTimeMillis()
        val diffMilliSec = now - date.time
        val diffSec = TimeUnit.MILLISECONDS.toSeconds(diffMilliSec)  // ミリ秒を秒に変換

        view.text =  when {
            diffSec < HOUR_SECONDS -> {
                (diffSec / MINUTE_SECONDS).toString() + "分前"
            }
            diffSec < DAY_SECONDS -> {
                (diffSec / HOUR_SECONDS).toString() + "時間前"
            }
            else -> {
                (diffSec / DAY_SECONDS).toString() + "日前"
            }
        }
    }

}