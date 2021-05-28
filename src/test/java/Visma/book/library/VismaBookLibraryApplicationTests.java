package Visma.book.library;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VismaBookLibraryApplicationTests {
    static BookServices bookServices = new BookServices();

    static Book book1 = new Book("Head First Design Patterns", "Eric Freeman",
            "Software Design", "English", "2020", "9781492078005", "1");
    static Book book2 = new Book("Prezidentas", "Andrius Tapinas", "Politics",
            "Lithuanian", "2020", "9786090136423", "2");

    @BeforeAll
    static void setUp() {
        bookServices.getAllBooks().add(book1);
        bookServices.getAvailableBooks().add(book1);
        bookServices.getAllBooks().add(book2);
        bookServices.getAvailableBooks().add(book2);
    }

    @Test
    void listingTest() {
        List<Book> testingList = new ArrayList<>();
        testingList.add(book1);
        testingList.add(book2);

        assertEquals(testingList, bookServices.getAllBooks());
    }

    @Test
    void listingWithAuthor() {
        List<Book> testingList = new ArrayList<>();
        testingList.add(book1);

        assertEquals(testingList, bookServices.getFiltered("author", "Eric Freeman"));
    }

    @Test
    void listingWithCategory() {
        List<Book> testingList = new ArrayList<>();
        testingList.add(book2);

        assertEquals(testingList, bookServices.getFiltered("category", "Politics"));
    }

    @Test
    void listingWithLanguage() {
        List<Book> testingList = new ArrayList<>();
        testingList.add(book2);

        assertEquals(testingList, bookServices.getFiltered("language", "Lithuanian"));
    }

    @Test
    void listingWithISBN() {
        List<Book> testingList = new ArrayList<>();
        testingList.add(book1);

        assertEquals(testingList, bookServices.getFiltered("isbn", "9781492078005"));
    }

    @Test
    void listingWithName() {
        List<Book> testingList = new ArrayList<>();
        testingList.add(book1);

        assertEquals(testingList, bookServices.getFiltered("name", "Head First Design Patterns"));
    }

    @Test
    void listingAvailable() {
        List<Book> testingList = new ArrayList<>();
        testingList.add(book1);
        testingList.add(book2);

        assertEquals(testingList, bookServices.getFiltered("available", ""));
    }

    @Test
    void listingTaken() {
        List<Book> testingList = new ArrayList<>();

        assertEquals(testingList, bookServices.getFiltered("taken", ""));
    }

    @Test
    void addingBooks() {
        List<Book> emptyList = new ArrayList<>();
        List<Book> testingList = new ArrayList<>();
        BookServices bookServices2 = new BookServices();

        Book book3 = new Book("Head", "Eric",
                "Software", "Russian", "2020", "9755495558005", "3");

        testingList.add(book3);

        bookServices2.addNewBook("Head", "Eric",
                "Software", "Russian", "2020", "9755495558005", "3");

        assertEquals(testingList.size(), bookServices2.getAllBooks().size());
        assertEquals(testingList.size(), bookServices2.getFiltered("available", "").size());
        assertEquals(emptyList, bookServices2.getFiltered("taken", ""));
    }

    @Test
    void getBookWhenBookExists() {
        assertEquals(book1, bookServices.getBookGuid("1"));
        assertEquals(book2, bookServices.getBookGuid("2"));
    }


    @Test
    void deleteBookCorrectGUID() {
        BookServices bookServices2 = new BookServices();
        bookServices2.getAllBooks().add(book1);
        bookServices2.getTakenBooks().add(book2);
        bookServices2.deleteBook("1");
        assertEquals(1, bookServices2.getAllBooks().size());

        bookServices2.deleteBook("2");
        assertEquals(0, bookServices2.getTakenBooks().size());
    }

    @Test
    void deleteBookINCorrectGUID() {
        BookServices bookServices2 = new BookServices();
        bookServices2.getAllBooks().add(book1);

        bookServices2.deleteBook("0");
        assertEquals(1, bookServices2.getAllBooks().size());
    }

    @Test
    void takeBook() {
        BookServices bookServices2 = new BookServices();
        bookServices2.getAllBooks().add(book1);
        bookServices2.getAvailableBooks().add(book1);
        bookServices2.getAllBooks().add(book2);
        bookServices2.getAvailableBooks().add(book2);

        bookServices2.takeBook("Audrius P","1", 2);
        assertEquals("Audrius P", bookServices2.getClients().get(0).getFullName());
        assertEquals(1, bookServices2.getClients().get(0).getTakenBooks().size());
        assertEquals(1, bookServices2.getClients().get(0).getBookRentingPeriod().size());
        assertEquals(2, bookServices2.getClients().get(0).getBookRentingPeriod().get("1"));
        assertEquals(1, bookServices2.getTakenBooks().size());
        assertEquals(1, bookServices2.getAvailableBooks().size());
    }

    @Test
    void takeBookForTooLong() {
        BookServices bookServices2 = new BookServices();
        bookServices2.getAllBooks().add(book1);
        bookServices2.getAvailableBooks().add(book1);

        bookServices2.takeBook("Audrius P","1", 3);
        assertEquals(0, bookServices2.getTakenBooks().size());
        assertEquals(1, bookServices2.getAvailableBooks().size());
    }

    @Test
    void takeBookOver3() {
        BookServices bookServices2 = new BookServices();
        Book book3 = new Book("Head", "Eric",
                "Software", "Russian", "2020", "9755495558005", "3");
        Book book4 = new Book("Heads", "Erics",
                "Softwares", "Russisan", "2020", "9755495558005", "4");

        bookServices2.getAvailableBooks().add(book1);
        bookServices2.getAvailableBooks().add(book2);
        bookServices2.getAvailableBooks().add(book3);
        bookServices2.getAvailableBooks().add(book4);

        bookServices2.takeBook("Audrius P","1", 1);
        bookServices2.takeBook("Audrius P","2", 1);
        bookServices2.takeBook("Audrius P","3", 1);
        bookServices2.takeBook("Audrius P","4", 1);


        assertEquals(3, bookServices2.getTakenBooks().size());
        assertEquals(1, bookServices2.getAvailableBooks().size());
        assertEquals(3, bookServices2.getClients().get(0).getTakenBooks().size());
        assertEquals(3, bookServices2.getClients().get(0).getBookRentingPeriod().size());
    }
}