package UDP;

import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 20251107L;
    private String id;
    private String title;
    private String author;
    private String isbn;
    private String publishDate;

    public Book(String id, String title, String author, String isbn, String publishDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishDate = publishDate;
    }

    // getter & setter
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public String getPublishDate() { return publishDate; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setPublishDate(String publishDate) { this.publishDate = publishDate; }

    @Override
    public String toString() {
        return id + " | " + title + " | " + author + " | " + isbn + " | " + publishDate;
    }
}
