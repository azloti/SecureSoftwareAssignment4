package com.adrian.assignment4

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "secure_notes")
data class SecureNote(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false, length = 1000)
    var encryptedContent: String,

    @Column(nullable = false)
    val createdBy: String,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column
    var lastModifiedAt: LocalDateTime? = null
) 