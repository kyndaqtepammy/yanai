package com.pamsillah.yanai.models;

public class ModelFlashcards {
    String flashCardImage;
    String flashCardsTitle;
    String flashCardId;
    String flashCardEnglishAudio;
    String flashCardNdebeleAudio;
    String flashCardShonaAudio;
    String flashCardEnglishWord;
    String flashCardNdebeleWord;
    String flashCardShonaWord;
    String flashCardCategory;
    String dateAdded;

    public ModelFlashcards(String flashCardImage, String flashCardsTitle, String flashCardId, String flashCardEnglishAudio, String flashCardNdebeleAudio, String flashCardShonaAudio, String flashCardEnglishWord, String flashCardNdebeleWord,
                           String flashCardShonaWord, String flashCardCategory, String dateAdded) {
        this.flashCardImage = flashCardImage;
        this.flashCardsTitle = flashCardsTitle;
        this.flashCardId = flashCardId;
        this.flashCardEnglishAudio = flashCardEnglishAudio;
        this.flashCardNdebeleAudio = flashCardNdebeleAudio;
        this.flashCardShonaAudio = flashCardShonaAudio;
        this.flashCardEnglishWord = flashCardEnglishWord;
    this.flashCardNdebeleWord = flashCardNdebeleWord;
    this.flashCardShonaWord = flashCardShonaWord;
        this.flashCardCategory = flashCardCategory;
        this.dateAdded = dateAdded;
    }

    public String getFlashCardImage() {
        return flashCardImage;
    }

    public void setFlashCardImage(String flashCardImage) {
        this.flashCardImage = flashCardImage;
    }

    public String getFlashCardsTitle() {
        return flashCardsTitle;
    }

    public void setFlashCardsTitle(String flashCardsTitle) {
        this.flashCardsTitle = flashCardsTitle;
    }

    public String getFlashCardId() {
        return flashCardId;
    }

    public void setFlashCardId(String flashCardId) {
        this.flashCardId = flashCardId;
    }

    public String getFlashCardEnglishAudio() {
        return flashCardEnglishAudio;
    }

    public void setFlashCardEnglishAudio(String flashCardEnglishAudio) {
        this.flashCardEnglishAudio = flashCardEnglishAudio;
    }

    public String getFlashCardNdebeleAudio() {
        return flashCardNdebeleAudio;
    }

    public void setFlashCardNdebeleAudio(String flashCardNdebeleAudio) {
        this.flashCardNdebeleAudio = flashCardNdebeleAudio;
    }

    public String getFlashCardShonaAudio() {
        return flashCardShonaAudio;
    }

    public void setFlashCardShonaAudio(String flashCardShonaAudio) {
        this.flashCardShonaAudio = flashCardShonaAudio;
    }
    public String getFlashCardEnglishWord() {
        return flashCardEnglishWord;
    }

    public void setFlashCardEnglishWord(String flashCardEnglishWord) {
        this.flashCardEnglishWord = flashCardEnglishWord;
    }

    public String getFlashCardNdebeleWord() {
        return flashCardNdebeleWord;
    }

    public void setFlashCardNdebeleWord(String flashCardNdebeleWord) {
        this.flashCardNdebeleWord = flashCardNdebeleWord;
    }

    public String getFlashCardShonaWord() {
        return flashCardShonaWord;
    }

    public void setFlashCardShonaWord(String flashCardShonaWord) {
        this.flashCardShonaWord = flashCardShonaWord;
    }
    public String getFlashCardCategory() {
        return flashCardCategory;
    }

    public void setFlashCardCategory(String flashCardCategory) {
        this.flashCardCategory = flashCardCategory;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
