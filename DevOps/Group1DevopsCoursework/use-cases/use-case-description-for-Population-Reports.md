# USE CASE 5: Produce Total Population Summary Reports
## CHARACTERISTIC INFORMATION
### Goal in Context
As a *Report Analyst*, I want the system to generate total population summary reports for specific geographic entities so that I have definitive population figures for reporting and planning purposes.

### Scope
World Population System.

### Level
Primary task.

### Preconditions
Database connection established. Relevant tables contain valid population data.

### Success End Condition
All population summary reports are generated with correct single-figure population totals.

### Failed End Condition
Population figures are incorrect or entities cannot be found.

### Primary Actor
Report Analyst.

### Trigger
Scheduled execution of summary reports.

### MAIN SUCCESS SCENARIO
1. System automatically executes the population reports module.
2. For "Population by continent" report:
    - System retrieves continent names, total populations, city populations and percentages
    - Calculates non-city populations and percentages
    - Outputs formatted table

3. For "Population by region" report:
    - System retrieves region names, total populations, city populations and percentages
    - Calculates non-city populations and percentages
    - Outputs formatted table

4. For "Population by country" report:
    - System retrieves country names, total populations, city populations and percentages
    - Calculates non-city populations and percentages
    - Outputs formatted table

5. For "Total population of the world" report:
    - System calculates sum of population from all countries
    - Outputs single population figure

6. For "Population of Asia" report:
    - System calculates sum of population from countries where continent = 'Asia'
    - Outputs single population figure

7. For "Population of Eastern Asia" report:
    - System calculates sum of population from countries where region = 'Eastern Asia'
    - Outputs single population figure

8. For "Population of Brazil" report:
    - System retrieves population from country where name = 'Brazil'
    - Outputs single population figure

9. For "Population of São Paulo district" report:
    - System calculates sum of population from cities where district = 'São Paulo'
    - Outputs single population figure

10. For "Population of São Paulo city" report:
    - System retrieves population from city where name = 'São Paulo'
    - Outputs single population figure

### EXTENSIONS
2a. Population data is NULL for any entity:
1. System outputs "Population data not available" for that entity
2. Continues processing other entities

### SUB-VARIATIONS
None.

## SCHEDULE
**DUE DATE**: Release 1.0