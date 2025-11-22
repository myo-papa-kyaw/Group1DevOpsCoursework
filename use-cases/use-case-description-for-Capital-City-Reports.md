# USE CASE 3: Produce Capital City Population Reports
## CHARACTERISTIC INFORMATION
### Goal in Context
As a *Report Analyst*, I want the system to generate pre-defined capital city population reports so that I can compare administrative center sizes across different geographic levels.

### Scope
World Population System.

### Level
Primary task.

### Preconditions
Database connection established. `city` and `countries` tables properly linked with valid capital city IDs.

### Success End Condition
All capital city reports are generated with correct capital city identification and sorting.

### Failed End Condition
Reports include non-capital cities or fail to identify proper capital cities.

### Primary Actor
Report Analyst.

### Trigger
Scheduled execution of capital city reports.

### MAIN SUCCESS SCENARIO
1. System automatically executes capital city reports module.
2. For "All Capital Cities in the World" report:
    - Joins `city` and `countries` tables on `city.ID = country.Capital`
    - Sorts by city population descending
    - Outputs formatted report
3. For "All Capital Cities in Asia" report:
    - Joins `city` and `countries` tables on `city.ID = country.Capital`
    - Filters where continent = 'Asia'
    - Sorts by city population descending
    - Outputs formatted report
4. For "All Capital Cities in Eastern Asia" report:
    - Joins `city` and `countries` tables on `city.ID = country.Capital`
    - Filters where region = 'Eastern Asia'
    - Sorts by city population descending
    - Outputs formatted report
5. For "Top 10 Populated Capital Cities in the World" report:
    - Joins `city` and `countries` tables on `city.ID = country.Capital`
    - Sorts by city population descending
    - Limits to first 10 records
    - Outputs formatted report
6. For "Top 5 Populated Capital Cities in Asia" report:
    - Joins `city` and `countries` tables on `city.ID = country.Capital`
    - Filters where continent = 'Asia'
    - Sorts by city population descending
    - Limits to first 5 records
    - Outputs formatted report
7. For "Top 5 Populated Capital Cities in Eastern Asia" report:
    - Joins `city` and `countries` tables on `city.ID = country.Capital`
    - Filters where region = 'Eastern Asia'
    - Sorts by city population descending
    - Limits to first 5 records
    - Outputs formatted report

### EXTENSIONS
2a. Invalid capital city ID found in country table:
1. No data is shown
2. Logs data integrity warning
3. Continues processing other countries

### SUB-VARIATIONS
None.

## SCHEDULE
**DUE DATE**: Release 1.0