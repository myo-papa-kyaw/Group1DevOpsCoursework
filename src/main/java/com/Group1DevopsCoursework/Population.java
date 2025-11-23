package com.Group1DevopsCoursework;

/**
 * Represents aggregated population statistics for a continent, region, or country.
 * Each Population object stores:
 * - The name of the area (continent, region, or country)
 * - The total population
 * - The population living in cities
 * - The population living outside cities
 * - The percentage of people living in cities
 * - The percentage of people not living in cities
 * This class is used in population aggregation queries.
 */
public class Population {

    /** The name of the continent, region, or country. */
    public String name;

    /** The total population of the area. */
    public long totalPopulation;

    /** The total population living in cities. */
    public long cityPopulation;

    /** The total population not living in cities. */
    public long nonCityPopulation;

    /** The percentage of the population living in cities. */
    public double cityPercentage;

    /** The percentage of the population not living in cities. */
    public double nonCityPercentage;
}
