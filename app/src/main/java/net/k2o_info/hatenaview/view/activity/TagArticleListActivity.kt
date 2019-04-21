package net.k2o_info.hatenaview.view.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import net.k2o_info.hatenaview.R
import android.arch.lifecycle.Observer
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import net.k2o_info.hatenaview.BR
import net.k2o_info.hatenaview.databinding.ActivityTagArticleListBinding
import net.k2o_info.hatenaview.model.repository.HatenaRepository
import net.k2o_info.hatenaview.view.adapter.ArticleRecyclerAdapter
import net.k2o_info.hatenaview.viewmodel.dto.ArticleDto
import net.k2o_info.hatenaview.viewmodel.fragment.ArticleListViewModel

/**
 * タグ別記事リストアクティビティ
 *
 * @author katsuya
 * @since 1.0.0
 */
class TagArticleListActivity : AppCompatActivity(), ArticleRecyclerAdapter.ArticleRecyclerListener {

    /**
     * アクティビティ生成時に呼ばれる
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTagArticleListBinding = DataBindingUtil.setContentView(this, R.layout.activity_tag_article_list)
        val recyclerView: RecyclerView = binding.recyclerView
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        val recyclerAdapter = ArticleRecyclerAdapter(this, this)
        recyclerView.adapter = recyclerAdapter

        // ViewModelの設定
        val viewModel = ViewModelProviders
            .of(this, ArticleListViewModel.ArticleListFactory(this.application, HatenaRepository(), "all"))
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
