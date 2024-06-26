package com.jpa.test;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

		@Autowired
	private BookService bookservice;
	//get all books
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getBooks()
	{
		
			List<Book> list=bookservice.getAllBooks();
			if(list.size()<=0) 
			{
			
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();						
				
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(list);
		}
	//get single book
	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBook(@PathVariable("id")int id) {
		Book book= bookservice.getBookById(id);
		if(book==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();			
		}
		return ResponseEntity.of(Optional.of(book));
	}
	//new book handler
	@PostMapping("/books")
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
	 Book b=null;
	 
	try {
		 b=this.bookservice.addBook(book);
		 System.out.println(book);
		 return ResponseEntity.of(Optional.of(b));
	}catch(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();			
	}
	
	}
	//delete book 
	@DeleteMapping("/books/{bookId}")
	public ResponseEntity<?>deleteBook(@PathVariable("bookId") int bookId){ 

	try {
		this.bookservice.deleteBook(bookId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}catch(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
	
	//update book handler
	@PutMapping("/books/{bookId}")
	public Book updateBook(@RequestBody Book book,@PathVariable("bookId")int bookId) {
		
		this.bookservice.updateBook(book,bookId);
		return book;
		
	}
}
