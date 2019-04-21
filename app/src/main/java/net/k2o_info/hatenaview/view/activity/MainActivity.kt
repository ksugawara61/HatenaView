package net.k2o_info.hatenaview.view.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.arch.lifecycle.Observer
import android.support.v4.widget.SwipeRefreshLayout
import net.k2o_info.hatenaview.BR
import net.k2o_info.hatenaview.databinding.ActivityMainBinding
import net.k2o_info.hatenaview.R
import net.k2o_info.hatenaview.model.repository.HatenaRepository
import net.k2o_info.hatenaview.view.adapter.ArticleRecyclerAdapter
import net.k2o_info.hatenaview.viewmodel.ArticleListViewModel
import timber.log.Timber

/**
 * メインアクティビティ
 *
 * @author katsuya
 * @since 1.0.0
 */
class MainActivity : AppCompatActivity() {

    /**
     * アクティビティ生成時に呼ばれる
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val recyclerView: RecyclerView = binding.recyclerView
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        val recyclerAdapter = ArticleRecyclerAdapter(this)
        recyclerView.adapter = recyclerAdapter

        // ViewModelの設定
        val viewModel = ViewModelProviders
            .of(this, ArticleListViewModel.ArticleListFactory(this.application, this, HatenaRepository, "all"))
            .get(ArticleListViewModel::class.java)

        // リフレッシュ処理
        val swipeRefreshLayout: SwipeRefreshLayout = binding.swipeContainer
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshList()
        }

        viewModel.getList().observe(this, Observer {
            if (it != null) {
                Timber.d(it.toString())
                recyclerAdapter.updateItems(it)
            }
            swipeRefreshLayout.isRefreshing = false
        })

    }


}
