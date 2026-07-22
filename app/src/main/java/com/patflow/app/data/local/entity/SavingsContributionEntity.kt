package com.patflow.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

/** `savings_contribution` — a logged contribution toward a savings goal (Architecture §8.5 / FR-13.2). */
@Entity(
    tableName = "savings_contribution",
    foreignKeys = [
        ForeignKey(
            entity = SavingsGoalEntity::class,
            parentColumns = ["id"],
            childColumns = ["savings_goal_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["savings_goal_id"])],
)
data class SavingsContributionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "savings_goal_id")
    val savingsGoalId: Long,

    val amount: Double,

    @ColumnInfo(name = "contribution_date")
    val contributionDate: LocalDate,

    val note: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime,
)
