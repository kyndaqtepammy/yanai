package com.pamsillah.yanai.models;


public class ModelBooks {
    private String bookID;
    private String bookTitle;
    private String bookAuthor;
    private String bookDescr;
    private String bookCoverUrl;
    private String bookPdfUrl;
    private String bookDateAdded;
    private String bookPrice;

    public ModelBooks(String bookID, String bookTitle, String bookAuthor, String bookDescr, String bookCoverUrl, String bookPdfUrl, String bookDateAdded, String bookPrice) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookDescr = bookDescr;
        this.bookCoverUrl = bookCoverUrl;
        this.bookPdfUrl = bookPdfUrl;
        this.bookDateAdded = bookDateAdded;
        this.bookPrice = bookPrice;
    }

    public String getBookID() {
        return bookID;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookDescr() {
        return bookDescr;
    }

    public String getBookCoverUrl() {
        return bookCoverUrl;
    }

    public String getBookPdfUrl() {
        return bookPdfUrl;
    }

    public String getBookDateAdded() {
        return bookDateAdded;
    }

    public String getBookPrice() {
        return bookPrice;
    }
}
