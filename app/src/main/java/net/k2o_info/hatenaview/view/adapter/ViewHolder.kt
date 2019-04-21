package net.k2o_info.hatenaview.view.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * ビューホルダー
 *
 * @author katsuya
 * @since 1.0.0
 */
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var view: View = itemView
    private var binding: ViewDataBinding = DataBindingUtil.bind(itemView)!!

    fun getView(): View {
        return view
    }

    fun getBinding(): ViewDataBinding {
        return binding
    }
}
