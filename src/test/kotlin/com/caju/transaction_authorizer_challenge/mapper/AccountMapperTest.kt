import com.caju.transaction_authorizer_challenge.model.dto.AccountDTO
import com.caju.transaction_authorizer_challenge.model.entity.AccountEntity
import com.caju.transaction_authorizer_challenge.mapper.toDTO
import com.caju.transaction_authorizer_challenge.mapper.toEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AccountMapperTest {

    @Test
    fun `should map AccountDTO to AccountEntity correctly`() {
        // Arrange
        val accountDTO = AccountDTO(
            id = 1L,
            name = "Test Account",
            documentNumber = "123456789",
            foodAmount = 100.0,
            mealAmount = 50.0,
            cashAmount = 200.0
        )

        // Act
        val accountEntity = accountDTO.toEntity()

        // Assert
        assertEquals(accountDTO.id, accountEntity.id)
        assertEquals(accountDTO.name, accountEntity.name)
        assertEquals(accountDTO.documentNumber, accountEntity.documentNumber)
        assertEquals(accountDTO.foodAmount, accountEntity.foodAmount)
        assertEquals(accountDTO.mealAmount, accountEntity.mealAmount)
        assertEquals(accountDTO.cashAmount, accountEntity.cashAmount)
    }

    @Test
    fun `should map AccountEntity to AccountDTO correctly`() {
        // Arrange
        val accountEntity = AccountEntity(
            id = 1L,
            name = "Test Account",
            documentNumber = "123456789",
            foodAmount = 100.0,
            mealAmount = 50.0,
            cashAmount = 200.0
        )

        // Act
        val accountDTO = accountEntity.toDTO()

        // Assert
        assertEquals(accountEntity.id, accountDTO.id)
        assertEquals(accountEntity.name, accountDTO.name)
        assertEquals(accountEntity.documentNumber, accountDTO.documentNumber)
        assertEquals(accountEntity.foodAmount, accountDTO.foodAmount)
        assertEquals(accountEntity.mealAmount, accountDTO.mealAmount)
        assertEquals(accountEntity.cashAmount, accountDTO.cashAmount)
    }

    @Test
    fun `should handle empty values correctly in AccountDTO to AccountEntity mapping`() {
        // Arrange
        val accountDTO = AccountDTO(
            id = 1L,
            name = "",
            documentNumber = "",
            foodAmount = 0.0,
            mealAmount = 0.0,
            cashAmount = 0.0
        )

        // Act
        val accountEntity = accountDTO.toEntity()

        // Assert
        assertEquals("", accountEntity.name)
        assertEquals("", accountEntity.documentNumber)
        assertEquals(0.0, accountEntity.foodAmount)
        assertEquals(0.0, accountEntity.mealAmount)
        assertEquals(0.0, accountEntity.cashAmount)
    }

    @Test
    fun `should handle empty values correctly in AccountEntity to AccountDTO mapping`() {
        // Arrange
        val accountEntity = AccountEntity(
            id = 1L,
            name = "",
            documentNumber = "",
            foodAmount = 0.0,
            mealAmount = 0.0,
            cashAmount = 0.0
        )

        // Act
        val accountDTO = accountEntity.toDTO()

        // Assert
        assertEquals("", accountDTO.name)
        assertEquals("", accountDTO.documentNumber)
        assertEquals(0.0, accountDTO.foodAmount)
        assertEquals(0.0, accountDTO.foodAmount)
        assertEquals(0.0, accountDTO.mealAmount)
        assertEquals(0.0, accountDTO.cashAmount)
    }
}
