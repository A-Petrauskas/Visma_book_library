package Visma.book.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookServices {
    private final List<Client> clients = new ArrayList<Client>();
    private final List<Book> allBooks = new ArrayList<Book>();
    private final List<Book> availableBooks = new ArrayList<Book>();
    private final List<Book> takenBooks = new ArrayList<Book>();


    public List<Client> getClients() {
        return clients;
    }

    public List<Book> getTakenBooks() {
        return takenBooks;
    }

    public List<Book> getAvailableBooks() {
        return availableBooks;
    }

    public List<Book> getAllBooks() {
        return allBooks;
    }

    public List<Book> getFilterAuthor(String param) {
        return allBooks.stream().filter(Book -> Book.getAuthor().equals(param)).collect(Collectors.toList());
    }

    public List<Book> getFilterCategory(String param) {
        return allBooks.stream().filter(Book -> Book.getCategory().equals(param)).collect(Collectors.toList());
    }

    public List<Book> getFilterLanguage(String param) {
        return allBooks.stream().filter(Book -> Book.getLanguage().equals(param)).collect(Collectors.toList());
    }

    public List<Book> getFilterISBN(String param) {
        return allBooks.stream().filter(Book -> Book.getIsbn().equals(param)).collect(Collectors.toList());
    }

    public List<Book> getFilterName(String param) {
        return allBooks.stream().filter(Book -> Book.getName().equals(param)).collect(Collectors.toList());
    }

    public List<Book> getFiltered(String filter, String param) {
        switch (filter) {
            case "author":
                return getFilterAuthor(param);

            case "category":
                return getFilterCategory(param);

            case "language":
                return getFilterLanguage(param);

            case "isbn":
                return getFilterISBN(param);

            case "name":
                return getFilterName(param);

            case "taken":
                return takenBooks;

            case "available":
                return availableBooks;

            default:
                return Collections.emptyList();
        }
    }

    public void addNewBook(String name, String author, String category, String language, String pubDate, String isbn, String guid) {
        Book newBook = new Book(name, author, category, language, pubDate, isbn, guid);
        allBooks.add(newBook);
        availableBooks.add(newBook);
    }

    public Book getBookGuid(String guid) {

        for (int i = 0; i < allBooks.size(); i++)
            if (allBooks.get(i).getGuid().equals(guid)) {
                return allBooks.get(i);
            }

        return null;
    }

    public void deleteBook(String guid) {
        boolean bookSeen = false;

        for (int i = 0; i < availableBooks.size(); i++)
            if (availableBooks.get(i).getGuid().equals(guid)) {
                availableBooks.remove(i);
                bookSeen = true;
            }

        for (int i = 0; i < takenBooks.size(); i++)
            if (takenBooks.get(i).getGuid().equals(guid)) {
                takenBooks.remove(i);
                bookSeen = true;
            }

        if (bookSeen)
            for (int i = 0; i < allBooks.size(); i++)
                if (allBooks.get(i).getGuid().equals(guid))
                    allBooks.remove(i);
    }

    public void takeBook(String name, String guid, int period) {
        if (period <= 2) {
            boolean bookIsFree = true;

            for (Book takenBook : takenBooks)
                if (takenBook.getGuid().equals(guid)) {
                    bookIsFree = false;
                    break;
                }

            if (bookIsFree) {
                Client client = null;

                for (Client value : clients)
                    if (value.getFullName().equals(name)) {
                        client = value;
                        break;
                    }

                if (client != null) {
                    if (client.getTakenBooks().size() < 3) {
                        client.getTakenBooks().add(guid);
                        client.getBookRentingPeriod().put(guid, period);

                        bookIsTaken(guid);
                    }

                } else {
                    Client newClient = new Client(name);
                    newClient.getTakenBooks().add(guid);
                    newClient.getBookRentingPeriod().put(guid, period);
                    clients.add(newClient);

                    bookIsTaken(guid);
                }

            }
        }
    }

    private void bookIsTaken(String guid) {
        for (int i = 0; i < availableBooks.size(); i++)
            if (availableBooks.get(i).getGuid().equals(guid)) {
                Book book = availableBooks.get(i);
                availableBooks.remove(i);

                takenBooks.add(book);
                break;
            }
    }
}
