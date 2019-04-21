package net.k2o_info.hatenaview.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.k2o_info.hatenaview.R
import net.k2o_info.hatenaview.databinding.FragmentArticleListBinding
import net.k2o_info.hatenaview.model.repository.HatenaRepository
import net.k2o_info.hatenaview.view.adapter.ArticleRecyclerAdapter
import net.k2o_info.hatenaview.viewmodel.fragment.ArticleListViewModel
import timber.log.Timber

class ArticleListFragment : Fragment() {

    /**
     * フラグメント生成時に呼ばれる
     *
     * @param context コンテキスト
     */
    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    /**
     * フラグメントのビューを描画
     *
     * @param inflater レイアウトの拡張用
     * @param container 親ビューのUI
     * @param savedInstanceState 保存している状態
     * @return フラグメントを描画したビュー
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentArticleListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_list, container, false)
        val recyclerView: RecyclerView = binding.recyclerView
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        val recyclerAdapter = ArticleRecyclerAdapter(context!!)
        recyclerView.adapter = recyclerAdapter

        // ViewModelの設定
        val viewModel = ViewModelProviders
            .of(this, ArticleListViewModel.ArticleListFactory(activity!!.application, HatenaRepository, "all"))
            .get(ArticleListViewModel::class.java)

        // リフレッシュ処理
        val swipeRefreshLayout: SwipeRefreshLayout = binding.swipeContainer
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshList()
        }

        viewModel.subscribeList(this).observe(this, Observer {
            if (it != null) {
                Timber.d(it.toString())
                recyclerAdapter.updateItems(it)
            }
            swipeRefreshLayout.isRefreshing = false
        })

        return binding.root
    }

    /**
     * ActivityのonCreateが呼ばれた直後に呼ばれる
     *
     * @param savedInstanceState 保存している状態
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}