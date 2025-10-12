# USE CASE 1: Produce Country Population Reports
## CHARACTERISTIC INFORMATION
### Goal in Context
As a Report Analyst, I want the system to generate country population reports organized by geographic scope and population ranking so that I can analyze national population distributions.

### Scope
World Population System.

### Level
Primary task.

### Preconditions
Database connection established. Country table contains valid population, continent, and region data.

### Success End Condition
All country population reports are generated with correct sorting and geographic filtering.

### Failed End Condition
Reports fail to generate, contain incorrect data, or are improperly sorted.

### Primary Actor
Report Analyst.

### Trigger
Scheduled execution of country reports.

### MAIN SUCCESS SCENARIO
1. System automatically executes the country reports' module.
2. For "All countries in the world" report:
    - System retrieves all countries and populations from country table
    - Sorts by population descending
    - Outputs formatted report
3. For "All countries in Asia" report:
    - System retrieves countries where continent = 'Asia'
    - Sorts by population descending
    - Outputs formatted report
4. For "All countries in Eastern Asia" report:
    - System retrieves countries where region = 'Eastern Asia'
    - Sorts by population descending
    - Outputs formatted report
5. For "Top 10 populated countries in the world" report:
    - System retrieves all countries
    - Sorts by population descending
    - Limits to first 10 records
    - Outputs formatted report
6. For "Top 5 populated countries in Asia" report:
    - System retrieves countries where continent = 'Asia'
    - Sorts by population descending
    - Limits to first 5 records
    - Outputs formatted report
7. For "Top 5 populated countries in Eastern Asia" report:
    - System retrieves countries where region = 'Eastern Asia'
    - Sorts by population descending
    - Limits to first 5 records
    - Outputs formatted report

### EXTENSIONS
2a. Database query returns no data:
1. System outputs "No country data available"
2. Continues processing other reports

### SUB-VARIATIONS
None.

## SCHEDULE
**DUE DATE**: Release 1.0