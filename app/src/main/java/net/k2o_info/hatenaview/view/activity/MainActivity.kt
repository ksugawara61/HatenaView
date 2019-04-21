package net.k2o_info.hatenaview.view.activity

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import net.k2o_info.hatenaview.databinding.ActivityMainBinding
import net.k2o_info.hatenaview.R
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import net.k2o_info.hatenaview.view.adapter.ArticleListPagerAdapter
import net.k2o_info.hatenaview.viewmodel.dto.CategoryDto
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
        val categoryList = ArrayList<CategoryDto>(
            Arrays.asList(
                CategoryDto("総合", "all"),
                CategoryDto("一般", "general"),
                CategoryDto("世の中", "social"),
                CategoryDto("政治と経済", "economics"),
                CategoryDto("暮らし", "life"),
                CategoryDto("学び", "knowledge"),
                CategoryDto("テクノロジー", "it"),
                CategoryDto("おもしろ", "fun"),
                CategoryDto("エンタメ", "entertainment"),
                CategoryDto("アニメとゲーム", "game")
            )
        )
        val fragmentManager = supportFragmentManager
        viewPager.adapter = ArticleListPagerAdapter(fragmentManager, categoryList)
    }

}
