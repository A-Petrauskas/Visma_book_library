package Visma.book.library;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    BookServices bookServices = new BookServices();

    @GetMapping("/list")
    public List<Book> getAllBooks() {
        return bookServices.getAllBooks();
    }


    @GetMapping("/list/{filter}/{param}")
    public List<Book> getFilteredBooks(@PathVariable("filter") String filter, @PathVariable("param") String param) {
        return bookServices.getFiltered(filter, param);
    }


    @GetMapping("/list/{filter}")
    public List<Book> getTakenOrAvail(@PathVariable("filter") String filter) {
        return bookServices.getFiltered(filter, "");
    }


    @RequestMapping("/add")
    public void addBook(@RequestParam String name,
                        @RequestParam String author,
                        @RequestParam String category,
                        @RequestParam String language,
                        @RequestParam String pubDate,
                        @RequestParam String isbn,
                        @RequestParam String guid) {

        bookServices.addNewBook(name, author, category, language, pubDate, isbn, guid);
    }


    @GetMapping("/getBook/{guid}")
    public ResponseEntity<Book> getBook(@PathVariable("guid") String guid) {
        Book book = bookServices.getBookGuid(guid);
        if (book == null)
            return new ResponseEntity<Book>(book, HttpStatus.NOT_FOUND);
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }


    @GetMapping("/delete/{guid}")
    public void deleteBook(@PathVariable("guid") String guid) {
        bookServices.deleteBook(guid);
    }

    @RequestMapping("/take")
    public void takeBook(@RequestParam String name,
                         @RequestParam String guid,
                         @RequestParam int period) {

        bookServices.takeBook(name, guid, period);
    }
}