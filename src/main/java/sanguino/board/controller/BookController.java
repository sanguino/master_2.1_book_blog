package sanguino.board.controller;

import org.springframework.web.bind.annotation.PathVariable;
import sanguino.board.model.Book;
import sanguino.board.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping("/")
	public String listBooks(Model model) {
		model.addAttribute("books", bookService.findAll());
		return "index";
	}

	@GetMapping("/book/new")
	public String newBookForm() {
		return "new_book";
	}
	
	@PostMapping("/book/new")
	public String newBook(Model model, Book book) {
		bookService.save(book);
		return "saved_book";
	}

	@GetMapping("/book/{id}")
	public String showPost(Model model, @PathVariable long id) {

		Book book = bookService.findById(id);

		model.addAttribute("book", book);

		return "show_book";
	}
}