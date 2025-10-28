# USE CASE: World Population Reporting System
## CHARACTERISTIC INFORMATION
### Goal in Context
As a Report Analyst, I want the system to generate all 32 predefined population reports across countries, cities, capital cities, demographics, and languages so that I can conduct comprehensive global demographic analysis.

### Scope
World Population System.

### Level
Primary task.

### Preconditions
Database connection established. Country, city, and countrylanguage tables contain valid population and geographic data.

### Success End Condition
All 32 reports are generated with accurate data, proper sorting, and correct calculations.

### Failed End Condition
Any report fails to generate, contains incorrect data, or database operations fail.

### Primary Actor
Report Analyst.

### Trigger
Scheduled execution of the complete reporting system.

### MAIN SUCCESS SCENARIO
1. System automatically executes the country reports module (Reports 1-6):
    - Generates world, continent (Asia), and region (Eastern Asia) country reports
    - Produces top N populated country reports (world: 10, Asia: 5, Eastern Asia: 5)
    - Outputs all country reports sorted by population descending

2. System automatically executes the city reports module (Reports 7-16):
    - Generates world, continent (Asia), region (Eastern Asia), country (India), and district (Maharashtra) city reports
    - Produces top N populated city reports (world: 10, Asia: 5, Eastern Asia: 5, Japan: 5, São Paulo: 5)
    - Outputs all city reports sorted by population descending

3. System automatically executes the capital city reports module (Reports 17-22):
    - Generates world, continent (Asia), and region (Eastern Asia) capital city reports
    - Produces top N populated capital city reports (world: 10, Asia: 5, Eastern Asia: 5)
    - Outputs all capital city reports sorted by population descending

4. System automatically executes the population reports module (Reports 23-31):
    - Generates population distribution reports for continents, regions, and countries
    - Calculates urban vs rural populations and percentages for all distribution reports
    - Produces total population summaries for world, Asia, Eastern Asia, Brazil, São Paulo district, and São Paulo city
    - Outputs formatted tables for distribution reports and single figures for summaries

5. System automatically executes the language reports module (Report 32):
    - Calculates speaker counts for Chinese, English, Hindi, Spanish, and Arabic languages
    - Computes percentage of world population for each language
    - Sorts results by number of speakers descending
    - Outputs formatted language report

### EXTENSIONS
2a. Database query returns no data for any geographic area:
1. System outputs "No data available" for that specific report
2. Continues processing remaining reports

3a. Invalid capital city ID found in country table:
1. System excludes that country from capital city reports
2. Logs data integrity warning
3. Continues processing other countries

4a. Population calculation returns invalid data:
1. System excludes problematic entries from calculations
2. Logs data inconsistency warning
3. Continues processing valid data

5a. Language has no entries in countrylanguage table:
1. System sets speaker count to zero for that language
2. Continues processing other languages

### SUB-VARIATIONS
None.

## SCHEDULE
**DUE DATE**: Release 1.0