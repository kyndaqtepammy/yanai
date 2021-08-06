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
    private String bookAudio;

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookDescr() {
        return bookDescr;
    }

    public void setBookDescr(String bookDescr) {
        this.bookDescr = bookDescr;
    }

    public String getBookCoverUrl() {
        return bookCoverUrl;
    }

    public void setBookCoverUrl(String bookCoverUrl) {
        this.bookCoverUrl = bookCoverUrl;
    }

    public String getBookPdfUrl() {
        return bookPdfUrl;
    }

    public void setBookPdfUrl(String bookPdfUrl) {
        this.bookPdfUrl = bookPdfUrl;
    }

    public String getBookDateAdded() {
        return bookDateAdded;
    }

    public void setBookDateAdded(String bookDateAdded) {
        this.bookDateAdded = bookDateAdded;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookAudio() {
        return bookAudio;
    }

    public void setBookAudio(String bookAudio) {
        this.bookAudio = bookAudio;
    }

    public ModelBooks(String bookID, String bookTitle, String bookAuthor, String bookDescr, String bookCoverUrl, String bookPdfUrl, String bookDateAdded, String bookPrice, String bookAudio) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookDescr = bookDescr;
        this.bookCoverUrl = bookCoverUrl;
        this.bookPdfUrl = bookPdfUrl;
        this.bookDateAdded = bookDateAdded;
        this.bookPrice = bookPrice;
        this.bookAudio = bookAudio;

    }
}
