package Visma.book.library;

public class Book {
    private final String name;
    private final String author;
    private final String category;
    private final String language;
    private final String pubDate;
    private final String isbn;
    private final String guid;


    public Book (String name, String author, String category, String language, String pubDate, String isbn, String guid) {
        this.name = name;
        this.author = author;
        this.category = category;
        this.language = language;
        this.pubDate = pubDate;
        this.isbn = isbn;
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getGuid() {
        return guid;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getLanguage() {
        return language;
    }
}
