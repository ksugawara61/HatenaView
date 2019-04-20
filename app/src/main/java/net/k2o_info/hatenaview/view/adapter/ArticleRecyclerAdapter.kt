package net.k2o_info.hatenaview.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import net.k2o_info.hatenaview.BR
import net.k2o_info.hatenaview.Constant
import net.k2o_info.hatenaview.R
import net.k2o_info.hatenaview.viewmodel.dto.ArticleDto

class ArticleRecyclerAdapter(context: Context) : RecyclerView.Adapter<ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var list: List<ArticleDto> = emptyList()

    /**
     * 要素のViewタイプを取得
     *
     * @param position 要素番号
     * @return Viewタイプ
     */
    override fun getItemViewType(position: Int): Int {
        return when(position) {
            // 記事Topのみ目立つように表示
            0 -> Constant.VIEW_TYPE_TOP

            else -> Constant.VIEW_TYPE_DEFAULT
        }
    }

    /**
     * 新規ViewHolderが渡されたときに呼ばれる
     *
     * @param viewGroup ビューの要素
     * @param viewType ビューの種類
     * @return 新しいViewHolder
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType) {
            Constant.VIEW_TYPE_TOP -> ViewHolder(inflater.inflate(R.layout.adapter_top_article, viewGroup, false))

            Constant.VIEW_TYPE_DEFAULT -> ViewHolder(inflater.inflate(R.layout.adapter_article, viewGroup, false))

            else -> ViewHolder(inflater.inflate(R.layout.adapter_article, viewGroup, false))
        }

    }

    /**
     * 要素が表示されたときに呼ばれる
     *
     * @param viewHolder ビューホルダー
     * @param position 要素番号
     */
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = list.getOrNull(position)

        if (item != null) {
            // 変数の格納
            viewHolder.getBinding().setVariable(BR.article, item)

            // タップ時の処理
            viewHolder.getView().setOnClickListener {
                //listener.onClickedListener(it, item, position)
            }
        }
    }

    /**
     * 要素数を返却
     *
     * @return 要素数
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * 要素のアップデート
     */
    fun updateItems(list: List<ArticleDto>) {
        this.list = list
        notifyDataSetChanged()
    }

}