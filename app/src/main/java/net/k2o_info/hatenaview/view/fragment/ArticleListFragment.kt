package net.k2o_info.hatenaview.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.k2o_info.hatenaview.BR
import net.k2o_info.hatenaview.Constant
import net.k2o_info.hatenaview.R
import net.k2o_info.hatenaview.databinding.FragmentArticleListBinding
import net.k2o_info.hatenaview.model.repository.HatenaRepository
import net.k2o_info.hatenaview.view.adapter.ArticleRecyclerAdapter
import net.k2o_info.hatenaview.viewmodel.dto.ArticleDto
import net.k2o_info.hatenaview.viewmodel.fragment.ArticleListViewModel
import timber.log.Timber

/**
 * 記事リストフラグメント
 *
 * @author katsuya
 * @since 1.0.0
 */
class ArticleListFragment : Fragment(), ArticleRecyclerAdapter.ArticleRecyclerListener {

    companion object {

        /**
         * インスタンスの生成
         *
         * @param category カテゴリ
         * @return フラグメント
         */
        fun newInstance(category: String): ArticleListFragment {
            val articleListFragment = ArticleListFragment()
            val bundle = Bundle()
            bundle.putString(Constant.KEY_CATEGORY_QUERY, category)
            articleListFragment.arguments = bundle
            return articleListFragment
        }
    }

    private lateinit var binding: FragmentArticleListBinding
    private lateinit var recyclerAdapter: ArticleRecyclerAdapter
    private lateinit var category: String

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_list, container, false)
        val recyclerView: RecyclerView = binding.recyclerView
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        recyclerAdapter = ArticleRecyclerAdapter(context!!, this)
        recyclerView.adapter = recyclerAdapter

        // カテゴリを取得
        val bundle = arguments
        if (bundle != null) {
            category = bundle.getString(Constant.KEY_CATEGORY_QUERY) ?: "all"
        } else {
            category = "all"
        }

        return binding.root
    }

    /**
     * ActivityのonCreateが呼ばれた直後に呼ばれる
     *
     * @param savedInstanceState 保存している状態
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // ViewModelの設定
        val viewModel = ViewModelProviders
            .of(this, ArticleListViewModel.ArticleListFactory(activity!!.application, HatenaRepository(), category))
            .get(ArticleListViewModel::class.java)

        // リフレッシュ処理
        val swipeRefreshLayout: SwipeRefreshLayout = binding.swipeContainer
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshList()
        }

        viewModel.subscribeStatus().observe(this, Observer {
            if (it != null) {
                binding.setVariable(BR.status, it)
            }
            swipeRefreshLayout.isRefreshing = false
        })

        viewModel.subscribeList(this).observe(this, Observer {
            if (it != null) {
                recyclerAdapter.updateItems(it)
            }
        })
    }

    /**
     * 要素のクリック時に呼ばれる
     *
     * @param view ビュー
     * @param article クリックした記事
     * @param position 記事の位置
     */
    override fun onClickedListener(view: View, article: ArticleDto, position: Int) {
        val url = article.link
        val tabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setToolbarColor(ContextCompat.getColor(view.context, R.color.colorPrimary))
            .build()
        tabsIntent.launchUrl(view.context, Uri.parse(url))
    }

}