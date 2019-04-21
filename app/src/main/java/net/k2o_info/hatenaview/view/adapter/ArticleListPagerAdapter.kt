package net.k2o_info.hatenaview.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import net.k2o_info.hatenaview.view.fragment.ArticleListFragment
import net.k2o_info.hatenaview.viewmodel.dto.CategoryDto
import timber.log.Timber
import android.support.v4.app.FragmentStatePagerAdapter


/**
 * 記事リスト用ページャアダプタ
 *
 * @author katsuya
 * @since 1.0.0
 */
class ArticleListPagerAdapter(fragmentManager: FragmentManager, private val categoryList: List<CategoryDto>)
    : FragmentStatePagerAdapter(fragmentManager) {

    /**
     * ページのタイトルを取得
     *
     * @param position ページ番号
     * @return タイトル
     */
    override fun getPageTitle(position: Int): CharSequence? {
        Timber.d("getPageTitle")
        return categoryList[position].categoryName
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
        Timber.d("getItem: ${position}")
        return ArticleListFragment.newInstance(categoryList[position].categoryQuery)
    }

    /**
     * 要素のポジションの取得
     *
     * @param object オブジェクト
     * @return
     */
    override fun getItemPosition(any: Any): Int {
        return POSITION_NONE
    }


}