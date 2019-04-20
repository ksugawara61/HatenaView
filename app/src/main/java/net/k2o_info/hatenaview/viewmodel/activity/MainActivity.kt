package net.k2o_info.hatenaview.viewmodel.activity

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import net.k2o_info.hatenaview.databinding.ActivityMainBinding
import net.k2o_info.hatenaview.R
import net.k2o_info.hatenaview.viewmodel.adapter.ArticleRecyclerAdapter
import java.util.*
import kotlin.collections.ArrayList

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

        // TODO: 暫定対処
        val itemList = ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5"))
        recyclerAdapter.updateItems(itemList)

        recyclerView.adapter = recyclerAdapter
    }


}
