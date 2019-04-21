package net.k2o_info.hatenaview.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import net.k2o_info.hatenaview.model.entity.CategoryEntity

/**
 * カテゴリエンティティ用Dao
 *
 * @author katsuya
 * @since 1.0.0
 */
@Dao
interface CategoryDao {

    /**
     * カテゴリの一括取得
     *
     * @return カテゴリリストのLiveData
     */
    @Query("SELECT * FROM CategoryEntity")
    fun findAll(): LiveData<List<CategoryEntity>>

    /**
     * カテゴリの一括登録
     *
     * @param categoryEntityList カテゴリリスト
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categoryEntityList: List<CategoryEntity>)
}
