import com.caju.transaction_authorizer_challenge.mapper.toDTO
import com.caju.transaction_authorizer_challenge.mapper.toEntity
import com.caju.transaction_authorizer_challenge.model.dto.MerchantDTO
import com.caju.transaction_authorizer_challenge.model.entity.MerchantEntity
import com.caju.transaction_authorizer_challenge.model.enums.CategoryEnum
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MerchantMapperTest {

    @Test
    fun `should map MerchantDTO to MerchantEntity correctly`() {
        // Arrange
        val merchantDTO = MerchantDTO(
            id = 1L,
            name = "Merchant Test",
            category = CategoryEnum.FOOD
        )

        // Act
        val merchantEntity = merchantDTO.toEntity()

        // Assert
        assertEquals(merchantDTO.name, merchantEntity.name)
        assertEquals(merchantDTO.category, merchantEntity.category)
    }

    @Test
    fun `should map MerchantEntity to MerchantDTO correctly`() {
        // Arrange
        val merchantEntity = MerchantEntity(
            id = 1L,
            name = "Merchant Test",
            category = CategoryEnum.FOOD
        )

        // Act
        val merchantDTO = merchantEntity.toDTO()

        // Assert
        assertEquals(merchantEntity.id, merchantDTO.id)
        assertEquals(merchantEntity.name, merchantDTO.name)
        assertEquals(merchantEntity.category, merchantDTO.category)
    }
}
