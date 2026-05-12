
# Quantity Measurement App

A Spring Boot REST API that performs arithmetic, comparison, and conversion operations on physical quantities like length, weight, temperature, and volume.

## Features
- Add, subtract, multiply, divide quantities
- Compare two quantities
- Convert between units
- View history of all operations
- Filter history by type or operation

## Supported Units
- **Length** — Kilometer, Meter, Centimeter, Millimeter, Mile, Yard, Feet
- **Weight** — Kilogram, Gram, Tonne, Pound, Ounce
- **Volume** — Liter, Milliliter, Gallon, Cubic Meter
- **Temperature** — Celsius, Fahrenheit, Kelvin
  
## POST
- `/api/add` — Add two quantities
- `/api/add-with-target-unit` — Add two quantities in target unit
- `/api/subtract` — Subtract two quantities
- `/api/subtract-with-target-unit` — Subtract two quantities in target unit
- `/api/multiply` — Multiply two quantities
- `/api/divide` — Divide two quantities
- `/api/compare` — Compare two quantities
- `/api/convert` — Convert a quantity to target unit

## GET
- `/api/history` — Get all operations
- `/api/history/type/{type}` — Filter by type
- `/api/history/operation/{operation}` — Filter by operation
- `/api/history/errored` — Get all failed operations
- `/api/count/{operation}` — Count by operation
