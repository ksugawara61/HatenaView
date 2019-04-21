package net.k2o_info.hatenaview.view.binding

import android.content.Intent
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import com.google.android.flexbox.FlexboxLayout
import net.k2o_info.hatenaview.BR
import net.k2o_info.hatenaview.Constant
import net.k2o_info.hatenaview.R
import net.k2o_info.hatenaview.databinding.TagItemBinding
import net.k2o_info.hatenaview.view.activity.MainActivity
import net.k2o_info.hatenaview.view.activity.TagArticleListActivity
import timber.log.Timber

/**
 * FlexboxLayout用カスタムバインディング
 *
 * @author katsuya
 * @since 1.0.0
 */
object FlexboxLayoutBindingAdapter {

    /**
     * タグリストをFlexboxに追加
     *
     * @param view ビュー
     * @param tagList タグリスト
     */
    @JvmStatic
    @BindingAdapter(value = ["tagList"])
    fun addTagList(view: FlexboxLayout, tagList: List<String>?) {
        val context = view.context
        view.removeAllViews()  // RecyclerViewの再利用の影響でタグが増殖するため要素を初期化する
        if (tagList == null) {
            return
        }
        val inflater: LayoutInflater = LayoutInflater.from(view.context)
        for (tag in tagList) {
            val tagViewBinding = DataBindingUtil.inflate<TagItemBinding>(inflater, R.layout.tag_item, null, false)

            // タップイベントを設定
            tagViewBinding.tag.setOnClickListener {
                Timber.d("tap: ${tag}")
                val intent = Intent(context, TagArticleListActivity::class.java)
                intent.putExtra(Constant.KEY_TAG_QUERY, tag)
                context.startActivity(intent)
            }

            // タグをビューに追加
            tagViewBinding.setVariable(BR.tag, tag)
            view.addView(tagViewBinding.root)
        }
    }

}
