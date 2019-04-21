package net.k2o_info.hatenaview.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import net.k2o_info.hatenaview.view.fragment.ArticleListFragment
import timber.log.Timber



class ArticleListPagerAdapter(fragmentManager: FragmentManager, private val categoryList: List<String>)
    : FragmentStatePagerAdapter(fragmentManager) {

    /**
     * ページのタイトルを取得
     *
     * @param position ページ番号
     * @return タイトル
     */
    override fun getPageTitle(position: Int): CharSequence? {
        Timber.d("getPageTitle")
        return categoryList[position]
    }

    /**
     * ページ総数の取得
     *
     * @return ページ総数
     */
    override fun getCount(): Int {
        Timber.d("getCount")
        return categoryList.size
    }

    /**
     * ページのフラグメントを取得
     *
     * @param position ページ番号
     * @return ページのフラグメント
     */
    override fun getItem(position: Int): Fragment {
        Timber.d("getItem")
        return ArticleListFragment()
    }


}