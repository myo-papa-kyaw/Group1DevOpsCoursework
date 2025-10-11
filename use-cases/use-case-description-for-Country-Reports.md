# USE CASE 1: Produce Country Population Reports
## CHARACTERISTIC INFORMATION
### Goal in Context
As a *Report Analyst*, I want the system to automatically generate pre-defined country population reports organized by population size across different geographic scopes so that I can analyze national population distributions without manual configuration.

### Scope
World Population System.

### Level
Primary task.

### Preconditions
Database connection is established. The `country` table contains valid code, name, continent, region, population and capital data.

### Success End Condition
All pre-defined country population reports are automatically generated with correct sorting and geographic filtering as per hardcoded parameters.

### Failed End Condition
Any report fails to generate, contains incorrect data, or is improperly sorted.

### Primary Actor
Report Analyst.

### Trigger
Scheduled execution of country reports.

### MAIN SUCCESS SCENARIO
1. System automatically executes the population summary reports module.

2. For "Total population of the world" report:

System calculates sum of population from all countries in country table

Outputs single population figure

For "Population of Asia" report:

System calculates sum of population from country table where continent = 'Asia'

Outputs single population figure

For "Population of Eastern Asia" report:

System calculates sum of population from country table where region = 'Eastern Asia'

Outputs single population figure

For "Population of Brazil" report:

System retrieves population from country table where name = 'Brazil'

Outputs single population figure

For "Population of São Paulo district" report:

System calculates sum of population from city table where district = 'São Paulo'

Outputs single population figure

For "Population of São Paulo city" report:

System retrieves population from city table where name = 'São Paulo'

Outputs single population figure

### EXTENSIONS
2a. Database connection fails during any report generation:
1. System displays an error message and aborts the report generation.
2. Use case ends in failure.

### SUB-VARIATIONS
None.

## SCHEDULE
**DUE DATE**: Release 1.0