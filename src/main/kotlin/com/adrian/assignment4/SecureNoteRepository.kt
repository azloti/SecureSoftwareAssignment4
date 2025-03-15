package com.adrian.assignment4

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SecureNoteRepository : JpaRepository<SecureNote, Long> {
    fun findByCreatedBy(username: String): List<SecureNote>
    fun findByTitleContainingIgnoreCase(title: String): List<SecureNote>
} 