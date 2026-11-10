package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val qrCode: String,
    val title: String,
    val author: String,
    val isbn: String,
    val category: String,
    val publishYear: Int,
    val totalCopies: Int = 1,
    val availableCopies: Int = 1,
    val description: String,
    val shelfLocation: String = "Rayon A1"
)

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val studentCardQr: String,
    val studentNumber: String,
    val fullName: String,
    val email: String,
    val department: String,
    val phone: String
)

@Entity(tableName = "loans")
data class LoanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bookId: Long,
    val bookTitle: String,
    val bookQrCode: String,
    val studentId: Long,
    val studentName: String,
    val studentEmail: String,
    val studentCardQr: String,
    val loanDate: Long,
    val dueDate: Long,
    val returnDate: Long? = null,
    val isReturned: Boolean = false,
    val lastEmailReminderSent: Long? = null
)

@Entity(tableName = "email_logs")
data class EmailNotificationLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val loanId: Long,
    val recipientEmail: String,
    val recipientName: String,
    val bookTitle: String,
    val subject: String,
    val messageBody: String,
    val sentTimestamp: Long,
    val status: String = "Envoyé"
)

@Entity(tableName = "app_settings")
data class AppSettingEntity(
    @PrimaryKey
    val key: String,
    val value: String
)
