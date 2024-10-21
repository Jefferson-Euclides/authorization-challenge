import com.caju.transaction_authorizer_challenge.exception.MerchantNotFoundException
import com.caju.transaction_authorizer_challenge.model.dto.MerchantDTO
import com.caju.transaction_authorizer_challenge.model.entity.MerchantEntity
import com.caju.transaction_authorizer_challenge.model.enums.CategoryEnum
import com.caju.transaction_authorizer_challenge.repository.MerchantRepository
import com.caju.transaction_authorizer_challenge.service.MerchantService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class MerchantServiceTest {

    @Mock
    private lateinit var merchantRepository: MerchantRepository

    @InjectMocks
    private lateinit var merchantService: MerchantService

    private lateinit var merchantDTO: MerchantDTO
    private lateinit var merchantEntity: MerchantEntity

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        merchantDTO = MerchantDTO(
            id = 1L,
            name = "Merchant Test",
            category = CategoryEnum.FOOD
        )

        merchantEntity = MerchantEntity(
            id = 1L,
            name = "Merchant Test",
            category = CategoryEnum.FOOD
        )
    }

    @Test
    fun `save should save merchant and return the saved DTO`() {
        // Arrange
        `when`(merchantRepository.save(any())).thenReturn(merchantEntity)

        // Act
        val result = merchantService.save(merchantDTO)

        // Assert
        assertNotNull(result)
        assertEquals(merchantDTO.name, result.name)
        verify(merchantRepository, times(1)).save(any())
    }

    @Test
    fun `findByName should return merchant DTO when found`() {
        // Arrange
        `when`(merchantRepository.findByName("Merchant Test")).thenReturn(merchantEntity)

        // Act
        val result = merchantService.findByName("Merchant Test")

        // Assert
        assertNotNull(result)
        assertEquals("Merchant Test", result?.name)
        verify(merchantRepository, times(1)).findByName("Merchant Test")
    }

    @Test
    fun `findByName should return null when merchant not found`() {
        // Arrange
        `when`(merchantRepository.findByName("Non-existent Merchant")).thenReturn(null)

        // Act
        val result = merchantService.findByName("Non-existent Merchant")

        // Assert
        assertNull(result)
        verify(merchantRepository, times(1)).findByName("Non-existent Merchant")
    }

    @Test
    fun `findById should return merchant DTO when found`() {
        // Arrange
        `when`(merchantRepository.findById(1L)).thenReturn(Optional.of(merchantEntity))

        // Act
        val result = merchantService.findById(1L)

        // Assert
        assertNotNull(result)
        assertEquals(merchantDTO.name, result?.name)
        verify(merchantRepository, times(1)).findById(1L)
    }

    @Test
    fun `findById should throw MerchantNotFoundException when merchant is not found`() {
        // Arrange
        val nonExistentId = 1L
        `when`(merchantRepository.findById(nonExistentId)).thenReturn(Optional.empty())

        // Act & Assert
        assertThrows<MerchantNotFoundException> {
            merchantService.findById(nonExistentId)
        }
    }
}
