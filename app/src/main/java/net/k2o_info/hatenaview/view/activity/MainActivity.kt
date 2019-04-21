package net.k2o_info.hatenaview.view.activity

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import net.k2o_info.hatenaview.databinding.ActivityMainBinding
import net.k2o_info.hatenaview.R
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import net.k2o_info.hatenaview.view.adapter.ArticleListPagerAdapter
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
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // ViewPagerの設定
        val viewPager: ViewPager = binding.viewPager

        // Tab Layoutの設定
        val tabLayout: TabLayout = binding.tabLayout
        tabLayout.setupWithViewPager(viewPager)

        // TODO: タイトルリストの取得処理を制御
        val categoryList = ArrayList<String>(Arrays.asList("all", "general", "social", "economics", "life", "knowledge", "it", "fun", "entertainment", "game"))
        val fragmentManager = supportFragmentManager
        viewPager.adapter = ArticleListPagerAdapter(fragmentManager, categoryList)
    }

}
