# Delta Processor Grammar Syntax Guide

## Overview

Delta Processor is a domain-specific query language designed for processing and manipulating file data. It supports operations like selecting, filtering, updating, and deleting data from files with support for nested JSON structures and various data types.

## Grammar Structure

### Core Components

#### 1. **Statements**

The query language supports six main statement types:

- `SELECT` - Query and retrieve data
- `SET` - Update/modify existing data
- `REMOVE` - Remove specific fields or targets
- `DELETE` - Delete records based on conditions
- `FILTER` - Filter data based on conditions
- `INSERT` - Add new data records

### 2. **Syntax Elements**

#### Identifiers

- **Pattern**: `[a-zA-Z_][a-zA-Z0-9_]*`
- **Description**: Variable names, column names, and field identifiers
- **Examples**: `name`, `user_age`, `_internal`, `field1`

#### Numbers

- **Pattern**: `[0-9]+ ('.' [0-9]+)?`
- **Description**: Integer or decimal numbers
- **Examples**: `42`, `3.14`, `0`, `100.99`

#### Strings

- **Pattern**: `"..."` with escape sequence support
- **Description**: Text values enclosed in double quotes
- **Examples**: `"hello"`, `"user@example.com"`, `"escaped\"quote"`

#### JSON Values

- **Objects**: `{"key1": value1, "key2": value2}`
- **Arrays**: `[value1, value2, value3]`
- **Boolean**: `true`, `false`
- **Null**: `null`

---

## Detailed Statement Reference

### SELECT Statement

**Syntax**:

```
SELECT columnList [WHERE condition];
```

**Components**:

- `SELECT` - Keyword to retrieve data
- `columnList` - Comma-separated column names or `*` for all columns
- `WHERE` - Optional condition clause
- `;` - Optional statement terminator

**Examples**:

```
SELECT * ;
```

Retrieve all columns from the data.

```
SELECT name, email, age ;
```

Retrieve specific columns.

```
SELECT user_name WHERE age > 18 ;
```

Retrieve user_name where age is greater than 18.

```
select email, country where status == "active" ;
```

Case-insensitive: retrieve email and country for active users.

---

### SET Statement

**Syntax**:

```
SET assignment [, assignment]* ;
```

**Components**:

- `SET` - Keyword to modify data
- `assignment` - `target = expression` (can chain multiple with commas)
- Target can be an identifier or a path expression
- Expression can include functions, values, or operations

**Examples**:

```
SET name = "John Doe" ;
```

Set the name field to "John Doe".

```
SET age = 30, status = "active" ;
```

Update multiple fields in one statement.

```
SET email = UPPER(email) ;
```

Apply the UPPER function to normalize email to uppercase.

```
SET count = count + 1 ;
```

Increment a numeric value using arithmetic operation.

```
SET user_data = {"name": "Jane", "role": "admin"} ;
```

Set a field to a JSON object value.

```
SET path(user.address.city) = "New York" ;
```

Update a nested field using path expression.

---

### REMOVE Statement

**Syntax**:

```
REMOVE target [, target]* ;
```

**Components**:

- `REMOVE` - Keyword to delete specific fields/targets
- `target` - Identifier or path expression (can chain multiple with commas)

**Examples**:

```
REMOVE sensitive_data ;
```

Remove a specific field.

```
REMOVE password, token, api_key ;
```

Remove multiple fields at once.

```
REMOVE path(user.temporary_id) ;
```

Remove a nested field from the data structure.

```
REMOVE deprecated_field ;
```

Clean up outdated data.

---

### DELETE Statement

**Syntax**:

```
DELETE WHERE condition ;
```

**Components**:

- `DELETE` - Keyword to remove records matching a condition
- `WHERE` - Required condition clause
- `condition` - Comparison expression

**Examples**:

```
DELETE WHERE status == "inactive" ;
```

Delete all records with inactive status.

```
DELETE WHERE age < 18 ;
```

Delete records for users under 18 years old.

```
DELETE WHERE created_date < "2020-01-01" ;
```

Delete old records before a specific date.

```
DELETE WHERE type == "temp" AND duration > 30 ;
```

Delete temporary records exceeding 30 days duration.

---

### FILTER Statement

**Syntax**:

```
FILTER WHERE condition ;
```

**Components**:

- `FILTER` - Keyword to filter/subset data
- `WHERE` - Required condition clause
- Returns data matching the condition

**Examples**:

```
FILTER WHERE country == "USA" ;
```

Keep only records from the USA.

```
FILTER WHERE price > 100 AND category == "premium" ;
```

Filter premium products over $100.

```
FILTER WHERE status == "pending" OR status == "in_progress" ;
```

Filter for pending or in-progress items.

---

### INSERT Statement

**Syntax**:

```
INSERT [(columnList)] VALUES (valueList) ;
```

**Components**:

- `INSERT` - Keyword to add new data
- `columnList` - Optional list of column names (if omitted, assumes all columns)
- `VALUES` - Keyword before values
- `valueList` - Comma-separated values matching column count

**Examples**:

```
INSERT VALUES ("John", "john@example.com", 28) ;
```

Insert a complete row of values.

```
INSERT (name, email) VALUES ("Jane", "jane@example.com") ;
```

Insert partial row with specific columns.

```
INSERT (id, status, data) VALUES (1, "active", {"role": "user"}) ;
```

Insert with JSON object value.

```
INSERT (username, age, verified) VALUES ("alice", 25, true) ;
```

Insert with boolean value.

---

## Functions

### String Functions

#### UPPER

Converts a string to uppercase.

**Syntax**: `UPPER(argument)`

**Examples**:

```
SELECT UPPER(name) ;
```

```
SET normalized_email = UPPER(email) ;
```

#### LOWER

Converts a string to lowercase.

**Syntax**: `LOWER(argument)`

**Examples**:

```
SELECT LOWER(category) ;
```

```
SET lowercase_name = LOWER(user_name) ;
```

#### LENGTH

Returns the length of a string or array.

**Syntax**: `LENGTH(argument)`

**Examples**:

```
SELECT name WHERE LENGTH(password) >= 8 ;
```

```
SET name_length = LENGTH(full_name) ;
```

---

## Path Expressions

Access nested data using dot notation or array indices.

**Syntax**: `PATH(identifier[.identifier | [NUMBER]]*)`

**Examples**:

```
SELECT PATH(user.profile.email) ;
```

Access nested email field.

```
SET PATH(data.items[0].name) = "Updated" ;
```

Update the name of the first item in an array.

```
REMOVE PATH(config.deprecated.legacy) ;
```

Remove deeply nested field.

```
SELECT * WHERE PATH(user.settings.notifications) == true ;
```

Query based on nested boolean value.

---

## Operators

### Comparison Operators

- `==` - Equality
- `!=` - Not equal
- `>` - Greater than
- `<` - Less than
- `>=` - Greater than or equal
- `<=` - Less than or equal

### Logical Operators

- `AND` - Logical AND (case-insensitive: `and`)
- `OR` - Logical OR (case-insensitive: `or`)

### Arithmetic Operators

- `+` - Addition
- `-` - Subtraction
- `*` - Multiplication
- `/` - Division

**Examples**:

```
SELECT * WHERE age >= 21 AND status == "verified" ;
```

```
SET total = price * quantity + tax ;
```

```
DELETE WHERE score < 50 OR status == "failed" ;
```

---

## Complete Query Examples

### Example 1: User Management Query

```
SELECT name, email, age WHERE country == "USA" AND age >= 18 ;
```

Retrieve active adult users from the USA.

### Example 2: Data Normalization

```
SET email = LOWER(email), status = "active", last_updated = "2024-01-15" ;
```

Normalize and update user data.

### Example 3: Nested Data Update

```
SET path(user.profile.full_name) = UPPER(path(user.profile.first_name)) ;
```

Update nested field using function.

### Example 4: Multi-field Cleanup

```
REMOVE temporary_id, debug_info, api_token ;
```

Remove sensitive or temporary fields.

### Example 5: Complex Filtering

```
FILTER WHERE LENGTH(description) > 10 AND category == "featured" AND price >= 99.99 ;
```

Filter featured products with detailed descriptions.

### Example 6: Bulk Data Insertion

```
INSERT (id, username, role, active) VALUES (1, "admin_user", "administrator", true) ;
```

Add a new administrative user.

### Example 7: Conditional Deletion

```
DELETE WHERE created_date < "2023-01-01" AND status == "archived" ;
```

Remove old archived records.

### Example 8: Advanced Selection with Nesting

```
SELECT name, email, path(settings.theme) WHERE path(user.verified) == true ;
```

Select specific fields including nested properties for verified users.

---

## Data Types

| Type            | Examples                     | Description                           |
| --------------- | ---------------------------- | ------------------------------------- |
| **String**      | `"hello"`, `"user@test.com"` | Text values enclosed in double quotes |
| **Number**      | `42`, `3.14`, `0`            | Integer or decimal values             |
| **Boolean**     | `true`, `false`              | Boolean literals                      |
| **Null**        | `null`                       | Null/empty value                      |
| **JSON Object** | `{"key": "value"}`           | Key-value pairs                       |
| **JSON Array**  | `[1, 2, 3]`                  | Ordered list of values                |

---

## Case Sensitivity

- **Keywords**: Case-insensitive (`SELECT`, `select`, `Select` are all valid)
- **Identifiers**: Case-sensitive (`name` and `Name` are different)
- **Operators**: Case-insensitive (`AND`, `and` are both valid)

---

## Optional Elements

- **Semicolon (`;`)**: Optional statement terminator
- **Column names in SELECT**: Can use `*` to select all columns
- **WHERE clause in SELECT**: Optional; omit to select without filtering
- **Column list in INSERT**: Optional; if omitted, all columns are assumed

---

## Common Patterns

### Pattern 1: Select with Multiple Conditions

```
SELECT email, name WHERE age > 18 AND country == "UK" AND status == "active" ;
```

### Pattern 2: Update Multiple Fields Conditionally

```
SET status = "processed", updated_at = "2024-01-15", count = count + 1 ;
```

### Pattern 3: Nested Path Operations

```
SELECT path(user.contact.phone) WHERE path(user.contact.verified) == true ;
```

### Pattern 4: String Function Chaining in Expressions

```
SELECT LOWER(email) WHERE LENGTH(name) > 3 ;
```

### Pattern 5: Arithmetic in Updates

```
SET final_price = base_price * quantity + (tax * 0.05) ;
```

---

## Error Handling Considerations

- All statements should conform to the defined grammar rules
- Identifiers must follow naming conventions
- String values must be enclosed in double quotes
- JSON objects and arrays must use valid JSON syntax
- Path expressions must reference valid nested structures
- Operators must have valid operands on both sides

---

## Summary

Delta Processor provides a powerful, SQL-like syntax for querying and manipulating file-based data with support for:

- Complex nested data structures via path expressions
- String manipulation functions (UPPER, LOWER, LENGTH)
- Logical and arithmetic operations
- Bulk data operations (INSERT, DELETE, FILTER)
- Field-level updates and removals

Use this guide to construct queries that efficiently process and transform your data according to your specific requirements.

\* **Note: Nested path not yet implemented**
