package com.adrian.assignment4

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller

@Controller
@RestController
@RequestMapping("/api/notes")
class SecureNoteController(private val noteRepository: SecureNoteRepository) {

    @GetMapping
    fun getNotes(@AuthenticationPrincipal user: UserDetails): ResponseEntity<List<SecureNote>> {
        val notes = noteRepository.findByCreatedBy(user.username)
        return ResponseEntity.ok(notes)
    }

    @GetMapping("/{id}")
    fun getNote(@PathVariable id: Long, @AuthenticationPrincipal user: UserDetails): ResponseEntity<SecureNote> {
        val note = noteRepository.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()
            
        // Only allow access to own notes
        if (note.createdBy != user.username) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }
        
        return ResponseEntity.ok(note)
    }

    @PostMapping
    fun createNote(
        @RequestBody noteRequest: NoteRequest,
        @AuthenticationPrincipal user: UserDetails
    ): ResponseEntity<SecureNote> {
        val note = SecureNote(
            title = noteRequest.title,
            encryptedContent = noteRequest.content, // TODO: Add encryption
            createdBy = user.username
        )
        val savedNote = noteRepository.save(note)
        return ResponseEntity.ok(savedNote)
    }

    @PutMapping("/{id}")
    fun updateNote(
        @PathVariable id: Long,
        @RequestBody noteRequest: NoteRequest,
        @AuthenticationPrincipal user: UserDetails
    ): ResponseEntity<SecureNote> {
        val existingNote = noteRepository.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()

        // Only allow updating own notes
        if (existingNote.createdBy != user.username) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }

        existingNote.title = noteRequest.title
        existingNote.encryptedContent = noteRequest.content // TODO: Add encryption
        existingNote.lastModifiedAt = LocalDateTime.now()

        val updatedNote = noteRepository.save(existingNote)
        return ResponseEntity.ok(updatedNote)
    }

    @DeleteMapping("/{id}")
    fun deleteNote(
        @PathVariable id: Long,
        @AuthenticationPrincipal user: UserDetails
    ): ResponseEntity<Unit> {
        val note = noteRepository.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()

        // Only allow deleting own notes
        if (note.createdBy != user.username) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }

        noteRepository.delete(note)
        return ResponseEntity.ok().build()
    }
}

data class NoteRequest(
    val title: String,
    val content: String
) 