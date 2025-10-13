# USE CASE 2: Produce City Population Reports
## CHARACTERISTIC INFORMATION
### Goal in Context
As a *Report Analyst*, I want the system to generate pre-defined city population reports across various geographic hierarchies so that I can analyze urban settlement patterns.

### Scope
World Population System.

### Level
Primary task.

### Preconditions
Database connection is established. The `city` and `country` tables contain valid population and geographic data.

### Success End Condition
All pre-defined city population reports are generated with correct geographic filtering and sorting.

### Failed End Condition
Any city report fails to generate or contains data from incorrect geographic areas.

### Primary Actor
Report Analyst.

### Trigger
Scheduled system execution of city reports.

### MAIN SUCCESS SCENARIO
1. System automatically executes the city reports' module.
2. For "All Cities in the World" report:
    - Retrieves all cities and populations from joining `city` and `countries` tables on `city.CountryCode = country.Code`
    - Sorts by population descending
    - Outputs formatted report
3. For "All Cities in Asia" report:
    - Joins `city` and `country` tables on `city.CountryCode = country.Code`
    - Filters where continent = 'Asia'
    - Sorts by population descending
    - Outputs formatted report
4. For "All Cities in Eastern Asia" report:
    - Joins `city` and `country` tables on `city.CountryCode = country.Code`
    - Filters where region = 'Eastern Asia'
    - Sorts by population descending
    - Outputs formatted report
5. For "All Cities in India" report:
    - Joins `city` and `country` tables on `city.CountryCode = country.Code`
    - Filters where country = 'India'
    - Sorts by population descending
    - Outputs formatted report
6. For "All cities in Maharashtra district" report:
    - Joins `city` and `country` tables on `city.CountryCode = country.Code`
    - Filters `city` table where district = 'Maharashtra'
    - Sorts by population descending
    - Outputs formatted report
7. For "Top 10 populated cities in the world" report:
    - Joins `city` and `country` tables on `city.CountryCode = country.Code`
    - Retrieves all cities
    - Sorts by population descending
    - Limits to first 10 records
    - Outputs formatted report
8. For "Top 5 populated cities in Asia" report:
    - Joins `city` and `country` tables on `city.CountryCode = country.Code`
    - Filters where continent = 'Asia'
    - Sorts by population descending
    - Limits to first 5 records
    - Outputs formatted report
9. For "Top 5 Cities in Eastern Asia" report:
    - Joins `city` and `country` tables on `city.CountryCode = country.Code`
    - Filters where continent = 'Eastern Asia'
    - Sorts by population descending
    - Limits to first 5 records
    - Outputs formatted report
10. For "Top 5 Cities in Japan" report:
    - Joins `city` and `country` tables on `city.CountryCode = country.Code`
    - Filters where country = 'Japan'
    - Sorts by population descending
    - Limits to first 5 records
    - Outputs formatted report
11. For "Top 5 Cities in São Paulo district" report:
    - Joins `city` and `country` tables on `city.CountryCode = country.Code`
    - Filters `city` table where district = 'São Paulo'
    - Sorts by population descending
    - Limits to first 5 records
    - Outputs formatted report

### EXTENSIONS
2a. Database join fails for continent/region reports:
1. System logs error and skips affected reports
2. Continues with remaining city reports

### SUB-VARIATIONS
None.

## SCHEDULE
**DUE DATE**: Release 1.0