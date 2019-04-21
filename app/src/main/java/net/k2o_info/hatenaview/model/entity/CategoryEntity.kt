package net.k2o_info.hatenaview.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 * カテゴリエンティティ
 *
 * @author katsuya
 * @since 1.0.0
 */
@Entity(
    indices = [ Index(value = ["categoryId"]) ]
)
data class CategoryEntity constructor(
    @PrimaryKey(autoGenerate = true) var categoryId: Int,
    var categoryName: String,
    var categoryQuery: String
)
