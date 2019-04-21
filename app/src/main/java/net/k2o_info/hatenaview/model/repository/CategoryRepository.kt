package net.k2o_info.hatenaview.model.repository

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Room
import android.content.Context
import android.os.AsyncTask
import net.k2o_info.hatenaview.Constant
import net.k2o_info.hatenaview.model.AppDatabase
import net.k2o_info.hatenaview.model.entity.CategoryEntity
import java.util.*

/**
 * カテゴリ用リポジトリ
 *
 * @author katsuya
 * @since 1.0.0
 */
class CategoryRepository(private val database: AppDatabase) {

    /**
     * カテゴリリストを取得
     *
     * @return カテゴリリストのLiveData
     */
    fun getCategoryList(): LiveData<List<CategoryEntity>> {
        return database.categoryDao().findAll()
    }

    /**
     * カテゴリリストを追加
     *
     * @param categoryEntityList 追加するカテゴリリスト
     */
    fun insertCategoryList(categoryEntityList: List<CategoryEntity>) {
        InsertAllAsyncTask(database).execute(categoryEntityList)
    }

    private class InsertAllAsyncTask internal constructor(private val database: AppDatabase) :
        AsyncTask<List<CategoryEntity>, Void, Void>() {

        override fun doInBackground(vararg params: List<CategoryEntity>): Void? {
            database.categoryDao().insertAll(params[0])
            return null
        }
    }


}
