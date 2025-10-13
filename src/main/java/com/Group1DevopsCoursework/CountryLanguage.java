package com.Group1DevopsCoursework;

public class CountryLanguage {
    private String countryCode;
    private String language;
    private boolean isOfficial;
    private double percentage;

    public CountryLanguage(String countryCode, String language, boolean isOfficial, double percentage) {
        this.countryCode = countryCode;
        this.language = language;
        this.isOfficial = isOfficial;
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %b | %.1f%%", countryCode, language, isOfficial, percentage);
    }

    // Getters and setters...

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isOfficial() {
        return isOfficial;
    }

    public void setOfficial(boolean official) {
        isOfficial = official;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
