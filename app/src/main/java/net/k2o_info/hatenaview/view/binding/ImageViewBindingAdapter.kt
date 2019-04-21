package net.k2o_info.hatenaview.view.binding

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * イメージビュー用カスタムバインディング
 *
 * @author katsuya
 * @since 1.0.0
 */
object ImageViewBindingAdapter {

    /**
     * URLから画像を読み込み
     *
     * @param view ビュー
     * @param url 画像のURL
     * @param error エラー時の画像
     */
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "error"])
    fun loadImage(view: ImageView, url: String?, error: Drawable) {
        val context = view.context
        if (url != null && !url.isEmpty()) {
            Picasso.with(context).load(url).placeholder(error).error(error).into(view)
        } else {
            view.setImageDrawable(error)
        }
    }
}