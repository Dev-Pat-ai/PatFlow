package com.patflow.app.data.mapper

import com.patflow.app.data.local.entity.BillCategoryEntity
import com.patflow.app.domain.model.Category
import org.junit.Assert.assertEquals
import org.junit.Test

class BillMapperTest {

    @Test
    fun `map BillCategoryEntity to Category domain model`() {
        val entity = BillCategoryEntity(
            id = 1,
            name = "Electricity",
            iconKey = "bolt",
            colorHex = "#FF0000",
            isCustom = false
        )
        
        val domain = entity.toDomain()
        
        assertEquals(entity.id, domain.id)
        assertEquals(entity.name, domain.name)
        assertEquals(entity.iconKey, domain.iconKey)
        assertEquals(entity.colorHex, domain.colorHex)
    }

    @Test
    fun `map Category domain model to BillCategoryEntity`() {
        val domain = Category(
            id = 1,
            name = "Electricity",
            iconKey = "bolt",
            colorHex = "#FF0000",
            isCustom = false
        )
        
        val entity = domain.toEntity()
        
        assertEquals(domain.id, entity.id)
        assertEquals(domain.name, entity.name)
        assertEquals(domain.iconKey, entity.iconKey)
        assertEquals(domain.colorHex, entity.colorHex)
    }
}
