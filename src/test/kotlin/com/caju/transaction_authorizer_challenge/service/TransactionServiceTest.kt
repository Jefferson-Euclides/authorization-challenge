import com.caju.transaction_authorizer_challenge.model.dto.TransactionDTO
import com.caju.transaction_authorizer_challenge.model.enums.CategoryEnum
import com.caju.transaction_authorizer_challenge.model.enums.FallbackEnum
import com.caju.transaction_authorizer_challenge.model.dto.AccountDTO
import com.caju.transaction_authorizer_challenge.model.entity.TransactionEntity
import com.caju.transaction_authorizer_challenge.repository.TransactionRepository
import com.caju.transaction_authorizer_challenge.service.AccountService
import com.caju.transaction_authorizer_challenge.service.MerchantService
import com.caju.transaction_authorizer_challenge.service.TransactionService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class TransactionServiceTest {

    @Mock
    private lateinit var accountService: AccountService

    @Mock
    private lateinit var merchantService: MerchantService

    @Mock
    private lateinit var transactionRepository: TransactionRepository

    @InjectMocks
    private lateinit var transactionService: TransactionService

    private lateinit var transactionDTO: TransactionDTO
    private lateinit var accountDTO: AccountDTO

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        // Configuração inicial de dados fictícios
        transactionDTO = TransactionDTO(
            account = "1",
            merchant = "Merchant Test",
            mcc = "5411",
            totalAmount = 100.0
        )

        accountDTO = AccountDTO(
            id = 1L,
            foodAmount = 200.0,
            mealAmount = 100.0,
            cashAmount = 300.0,
            documentNumber = "12345678901",
            name = "Account Test"
        )
    }

    @Test
    fun `authorize should approve transaction when balance is sufficient`() {
        // Arrange
        `when`(accountService.findById(1L)).thenReturn(accountDTO)
        `when`(merchantService.findByName("Merchant Test")).thenReturn(null)
        `when`(transactionRepository.save(any())).thenReturn(mock(TransactionEntity::class.java))
        `when`(accountService.debit(accountDTO, CategoryEnum.FOOD, 100.0)).thenReturn(FallbackEnum.APPROVED)

        // Act
        val result = transactionService.authorize(transactionDTO)

        // Assert
        assertEquals(FallbackEnum.APPROVED.fallback, result)
        verify(transactionRepository, times(1)).save(any())
        verify(accountService, times(1)).debit(accountDTO, CategoryEnum.FOOD, 100.0)
    }

    @Test
    fun `authorize should reject transaction when balance is insufficient`() {
        // Arrange
        `when`(accountService.findById(1L)).thenReturn(accountDTO)
        `when`(merchantService.findByName("Merchant Test")).thenReturn(null)
        `when`(transactionRepository.save(any())).thenReturn(mock(TransactionEntity::class.java))
        `when`(accountService.debit(accountDTO, CategoryEnum.FOOD, 100.0)).thenReturn(FallbackEnum.REJECTED)

        // Act
        val result = transactionService.authorize(transactionDTO)

        // Assert
        assertEquals(FallbackEnum.REJECTED.fallback, result)
        verify(transactionRepository, times(1)).save(any())
        verify(accountService, times(1)).debit(accountDTO, CategoryEnum.FOOD, 100.0)
    }

    @Test
    fun `authorize should fallback to cash category when merchant has no category and food amount is insufficient`() {
        // Arrange
        val transaction = TransactionDTO(
            account = "1",
            merchant = "Merchant Test",
            mcc = "5411",
            totalAmount = 500.0
        )

        val account = AccountDTO(
            id = 1L,
            foodAmount = 100.0,
            mealAmount = 100.0,
            cashAmount = 500.0,
            documentNumber = "12345678901",
            name = "Account Test"
        )

        `when`(accountService.findById(1L)).thenReturn(account)
        `when`(merchantService.findByName("Merchant Test")).thenReturn(null)
        `when`(transactionRepository.save(any())).thenReturn(mock(TransactionEntity::class.java))
        `when`(accountService.debit(account, CategoryEnum.FOOD, 500.0)).thenReturn(FallbackEnum.APPROVED)

        // Act
        val result = transactionService.authorize(transaction)

        // Assert
        assertEquals(FallbackEnum.APPROVED.fallback, result)
        verify(transactionRepository, times(1)).save(any())
        verify(accountService, times(1)).debit(account, CategoryEnum.FOOD, 500.0)
    }

    @Test
    fun `authorize should return error when an exception occurs`() {
        // Arrange
        `when`(accountService.findById(1L)).thenThrow(RuntimeException("Database error"))

        // Act
        val result = transactionService.authorize(transactionDTO)

        // Assert
        assertEquals(FallbackEnum.ERROR.fallback, result)
        verify(transactionRepository, never()).save(any())
    }
}
