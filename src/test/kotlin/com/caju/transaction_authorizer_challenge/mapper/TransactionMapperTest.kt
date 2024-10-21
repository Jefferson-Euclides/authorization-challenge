import com.caju.transaction_authorizer_challenge.mapper.toEntity
import com.caju.transaction_authorizer_challenge.model.dto.TransactionDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TransactionMapperTest {

    @Test
    fun `should map TransactionDTO to TransactionEntity correctly`() {
        // Arrange
        val transactionDTO = TransactionDTO(
            account = "12345",
            totalAmount = 100.50,
            mcc = "5411",
            merchant = "Supermarket"
        )

        // Act
        val transactionEntity = transactionDTO.toEntity()

        // Assert
        assertEquals(transactionDTO.account, transactionEntity.accountId)
        assertEquals(transactionDTO.totalAmount, transactionEntity.amount)
        assertEquals(transactionDTO.mcc, transactionEntity.mcc)
        assertEquals(transactionDTO.merchant, transactionEntity.merchant)
    }
}
