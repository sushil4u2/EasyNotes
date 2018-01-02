package com.example.easynotes.controller;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.repository.NoteRepository;
import com.example.easynotes.model.Note;

@RestController
@RequestMapping("/easynotes")
public class NoteController {
	
	@Autowired
	NoteRepository noteRepository;
	
	@GetMapping("/getallnotes")
	public List<Note> getAllNotes(){
		return noteRepository.findAll();
	}
	
	@PostMapping("/create")
	public Note createNote(@Valid @RequestBody Note note) {
		return noteRepository.save(note);
	}
	
	@GetMapping("/getnote/{id}")
	public ResponseEntity<Note> getOneNote(@PathVariable Long id){
		Note note = noteRepository.findOne(id);
		if(note != null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(note);
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<Note> updateNote(@PathVariable(value="id") Long noteId, @Valid @RequestBody Note noteDetails){
		Note note = noteRepository.findOne(noteId);
		if(note != null) {
			note.setTitle(noteDetails.getTitle());
			note.setContent(noteDetails.getContent());
			Note updatedNote = noteRepository.save(note);
			return ResponseEntity.ok(updatedNote);
		}
			return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Note> deleteNode(@PathVariable(value = "id") Long noteId){
		Note note = noteRepository.findOne(noteId);
		if(note != null) {
			noteRepository.delete(note);
			return ResponseEntity.ok().build();
		}
			return ResponseEntity.notFound().build();
	}
}
