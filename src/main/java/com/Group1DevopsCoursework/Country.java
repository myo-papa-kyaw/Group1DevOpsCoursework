package com.Group1DevopsCoursework;

/**
 * Represents a country in the world database.
 * Each Country object stores:
 * - The country's ISO code
 * - The name of the country
 * - The continent it belongs to
 * - The region within that continent
 * - The total population
 * - The capital city
 * This class is used in all country-level reporting and filtering operations.
 */
public class Country {

    /** The ISO code of the country (e.g., "GBR", "USA"). */
    public String code;

    /** The official name of the country. */
    public String name;

    /** The continent on which the country is located. */
    public String continent;

    /** The regional classification of the country. */
    public String region;

    /** The total population of the country. */
    public int population;

    /** The capital city of the country (stored as a name or city identifier). */
    public String capital;
}
