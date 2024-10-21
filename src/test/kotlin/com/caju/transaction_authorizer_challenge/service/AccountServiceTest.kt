import com.caju.transaction_authorizer_challenge.mapper.toEntity
import com.caju.transaction_authorizer_challenge.model.dto.AccountDTO
import com.caju.transaction_authorizer_challenge.model.enums.CategoryEnum
import com.caju.transaction_authorizer_challenge.model.enums.FallbackEnum
import com.caju.transaction_authorizer_challenge.repository.AccountRepository
import com.caju.transaction_authorizer_challenge.service.AccountService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class AccountServiceTest {

    @Mock
    private lateinit var accountRepository: AccountRepository

    @InjectMocks
    private lateinit var accountService: AccountService

    private lateinit var accountDTO: AccountDTO

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        accountDTO = AccountDTO(
            id = 1L,
            foodAmount = 100.0,
            mealAmount = 50.0,
            cashAmount = 200.0,
            name = "Account Test",
            documentNumber = "12345678901"
        )
    }

    @Test
    fun `save should save account and return the saved DTO`() {
        // Arrange
        `when`(accountRepository.save(any())).thenReturn(accountDTO.toEntity())

        // Act
        val result = accountService.save(accountDTO)

        // Assert
        assertNotNull(result)
        assertEquals(100.0, result.foodAmount)
        verify(accountRepository, times(1)).save(any())
    }

    @Test
    fun `findAll should return a list of accounts`() {
        // Arrange
        `when`(accountRepository.findAll()).thenReturn(listOf(accountDTO.toEntity()))

        // Act
        val result = accountService.findAll()

        // Assert
        assertEquals(1, result.size)
        verify(accountRepository, times(1)).findAll()
    }

    @Test
    fun `findById should return an account by ID`() {
        // Arrange
        `when`(accountRepository.findById(1L)).thenReturn(Optional.of(accountDTO.toEntity()))

        // Act
        val result = accountService.findById(1L)

        // Assert
        assertNotNull(result)
        assertEquals(100.0, result.foodAmount)
        verify(accountRepository, times(1)).findById(1L)
    }

    @Test
    fun `debit should update the balance and approve the transaction if sufficient funds`() {
        // Arrange
        `when`(accountRepository.save(any())).thenReturn(accountDTO.toEntity())

        // Act
        val result = accountService.debit(accountDTO, CategoryEnum.FOOD, 50.0)

        // Assert
        assertEquals(FallbackEnum.APPROVED, result)
        assertEquals(50.0, accountDTO.foodAmount)
    }

    @Test
    fun `debit should update the cash balance if category balance is insufficient but cash is available`() {
        // Arrange
        `when`(accountRepository.save(any())).thenReturn(accountDTO.toEntity())

        // Act
        val result = accountService.debit(accountDTO, CategoryEnum.MEAL, 100.0)

        // Assert
        assertEquals(FallbackEnum.APPROVED, result)
        assertEquals(200.0 - 100.0, accountDTO.cashAmount)
        verify(accountRepository, times(1)).save(any())
    }

    @Test
    fun `debit should reject the transaction if insufficient balance in both category and cash`() {
        // Act
        val result = accountService.debit(accountDTO, CategoryEnum.MEAL, 500.0)

        // Assert
        assertEquals(FallbackEnum.REJECTED, result)
        verify(accountRepository, never()).save(any())
    }

    @Test
    fun `hasSufficientBalance should return true if category balance is sufficient`() {
        // Act
        val result = accountService.hasSufficientBalance(CategoryEnum.FOOD, 50.0, accountDTO)

        // Assert
        assertTrue(result)
    }

    @Test
    fun `hasSufficientBalance should return false if category balance is insufficient`() {
        // Act
        val result = accountService.hasSufficientBalance(CategoryEnum.MEAL, 100.0, accountDTO)

        // Assert
        assertFalse(result)
    }
}
