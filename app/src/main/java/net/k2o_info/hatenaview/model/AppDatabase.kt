package net.k2o_info.hatenaview.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import net.k2o_info.hatenaview.model.dao.CategoryDao
import net.k2o_info.hatenaview.model.entity.CategoryEntity

/**
 * DBクラス
 *
 * @author katsuya
 * @since 1.0.0
 */
@Database(entities = [ CategoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}
