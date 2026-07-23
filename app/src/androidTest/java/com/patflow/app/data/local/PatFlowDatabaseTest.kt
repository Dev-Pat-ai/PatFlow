package com.patflow.app.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.patflow.app.data.local.dao.BillDao
import com.patflow.app.data.local.dao.CategoryDao
import com.patflow.app.data.local.database.PatFlowDatabase
import com.patflow.app.data.local.entity.BillCategoryEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PatFlowDatabaseTest {

    private lateinit var db: PatFlowDatabase
    private lateinit var categoryDao: CategoryDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PatFlowDatabase::class.java).build()
        categoryDao = db.categoryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadCategory() = runBlocking {
        val category = BillCategoryEntity(
            name = "Test Category",
            iconKey = "test",
            colorHex = "#000000"
        )
        categoryDao.insert(category)
        val allCategories = categoryDao.getAll().first()
        assertEquals(allCategories[0].name, category.name)
    }
}
