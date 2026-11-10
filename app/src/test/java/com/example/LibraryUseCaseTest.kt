package com.example

import com.example.data.local.BookEntity
import com.example.data.local.LoanEntity
import com.example.viewmodel.AppRole
import com.example.viewmodel.AuthenticatedUser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LibraryUseCaseTest {

    @Test
    fun testAvailabilityLogic() {
        val availableBook = BookEntity(
            id = 1,
            qrCode = "BOOK-978-01001",
            title = "Conception et Architecture Logicielle",
            author = "Alain Dupont",
            isbn = "978-2100100011",
            category = "Informatique",
            publishYear = 2022,
            totalCopies = 1,
            availableCopies = 1,
            description = "Description"
        )

        assertTrue(availableBook.availableCopies > 0)

        val borrowedBook = availableBook.copy(availableCopies = 0)
        assertEquals(0, borrowedBook.availableCopies)
    }

    @Test
    fun testOverdueCalculation() {
        val now = System.currentTimeMillis()
        val dayMillis = 86400000L

        val overdueLoan = LoanEntity(
            id = 1,
            bookId = 2,
            bookTitle = "Anthologie Poétique Contemporaine",
            bookQrCode = "BOOK-978-02002",
            studentId = 1,
            studentName = "Sophie Martin",
            studentEmail = "sophie.martin@usmba.ac.ma",
            studentCardQr = "STUDENT-1",
            loanDate = now - (20 * dayMillis),
            dueDate = now - (5 * dayMillis),
            returnDate = null,
            isReturned = false
        )

        val isOverdue = !overdueLoan.isReturned && overdueLoan.dueDate < now
        assertTrue(isOverdue)

        val overdueDays = ((now - overdueLoan.dueDate) / dayMillis).toInt()
        assertEquals(5, overdueDays)
    }

    @Test
    fun testAuthenticationRoleMapping() {
        val gerantUser = AuthenticatedUser(
            email = "gerant@usmba.ac.ma",
            role = AppRole.GERANT,
            displayName = "Gérant de Bibliothèque USMBA"
        )
        assertEquals(AppRole.GERANT, gerantUser.role)

        val studentUser = AuthenticatedUser(
            email = "sophie.martin@usmba.ac.ma",
            role = AppRole.UTILISATEUR,
            displayName = "Sophie Martin"
        )
        assertEquals(AppRole.UTILISATEUR, studentUser.role)
        assertTrue(studentUser.email.endsWith("@usmba.ac.ma"))
    }
}
