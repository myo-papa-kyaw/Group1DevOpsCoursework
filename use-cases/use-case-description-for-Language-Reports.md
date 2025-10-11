# USE CASE 6: Produce Language Speakers Report
## CHARACTERISTIC INFORMATION
### Goal in Context
As a *Report Analyst*, I want the system to generate a report on the number of speakers and global percentage for specific, pre-defined languages so that the organization can understand the global distribution of major languages.

### Scope
World Population System.

### Level
Primary task.

### Preconditions
Database connection is established. The `country`, `countrylanguage` tables contain valid data, and the `Percentage` fields is shown.

### Success End Condition
A report is generated showing the specified languages sorted by number of speakers, including the count and the percentage of the world population for each.

### Failed End Condition
The calculations are incorrect, the sorting is wrong, percentages are inaccurate, or the report fails.

### Primary Actor
Report Analyst.

### Trigger
Scheduled system execution of the language report.

### MAIN SUCCESS SCENARIO
1. System automatically executes the language report module.
2. Gets world population total.
3. For each language (Chinese, English, Hindi, Spanish, Arabic):
    1. Calculates total speakers from database
    2. Calculates world percentage
4. Outputs formatted report with all language data.

### EXTENSIONS
2a. World population zero: abort report.
3a. Language data missing: set count to zero.

### SUB-VARIATIONS
None.

## SCHEDULE
**DUE DATE**: Release 1.0