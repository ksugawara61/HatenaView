package net.k2o_info.hatenaview.view.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import net.k2o_info.hatenaview.databinding.ActivityMainBinding
import net.k2o_info.hatenaview.R
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.arch.lifecycle.Observer
import net.k2o_info.hatenaview.MainApplication
import net.k2o_info.hatenaview.model.repository.CategoryRepository
import net.k2o_info.hatenaview.view.adapter.ArticleListPagerAdapter
import net.k2o_info.hatenaview.viewmodel.activity.MainViewModel
import timber.log.Timber
import android.support.v4.view.ViewPager.OnPageChangeListener




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

        val fragmentManager = supportFragmentManager

        // ViewModelの設定
        val viewModel = ViewModelProviders
            .of(this, MainViewModel.MainFactory(this.application, CategoryRepository(MainApplication.database)))
            .get(MainViewModel::class.java)

        viewModel.subscribeList(this).observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                viewPager.adapter = ArticleListPagerAdapter(fragmentManager, it)
                viewPager.currentItem = viewModel.getSelectedTabPosition()
            }
        })

        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                Timber.d("onPageSelected: ${position}")
                viewModel.setSelectedTabPosition(position)
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                Timber.d("onPageScrolled")
            }

            override fun onPageScrollStateChanged(state: Int) {
                Timber.d("onPageScrollStateChanged")
            }
        })
    }

}
