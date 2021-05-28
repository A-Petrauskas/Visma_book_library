package Visma.book.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Client {
    private final String fullName;
    private final List<String> takenBooks = new ArrayList<String>();
    private final HashMap<String, Integer> bookRentingPeriod = new HashMap<String, Integer>(); // In months

    public Client(String fullName) {
        this.fullName = fullName;
    }

    public List<String> getTakenBooks() {
        return takenBooks;
    }

    public String getFullName() {
        return fullName;
    }

    public HashMap<String, Integer> getBookRentingPeriod() {
        return bookRentingPeriod;
    }
}
