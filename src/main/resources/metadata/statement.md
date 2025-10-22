---
title: monobank.statement
version: 2025-10-07
source: https://api.monobank.ua/personal/statement
---

# Statement (Transaction)

**Returns:** array of `Statement` objects representing transaction records for a specified account and time period.

## Conventions
- **Money**: BigDecimal in **major units** (e.g., `100.50` UAH).
- **Currency codes**: ISO-4217 **numeric** (e.g., `980` for UAH, `840` for USD, `978` for EUR).
- **Timestamps**: converted to `LocalDateTime` for readability.
- **Amount signs**: negative values = debits/spending, positive values = credits/income.

## Statement Object

- `date` *(LocalDateTime)* — transaction timestamp, converted from Unix epoch seconds (UTC) to local date-time.
- `category` *(int)* — Merchant Category Code (MCC) per ISO 18245, normalized by bank.
- `currencyCode` *(int)* — ISO-4217 numeric code for the transaction currency.
- `amount` *(BigDecimal)* — transaction amount in major currency units (negative for debits, positive for credits).
- `balance` *(BigDecimal)* — account balance **after** this transaction in major currency units.
- `cashback` *(BigDecimal?)* — cashback amount earned in major currency units (null if no cashback).
- `merchant` *(String?)* — merchant or organization display name.
- `notes` *(String?)* — optional user/merchant comment or additional details.

## Understanding Transaction Data

### Amounts
- **Negative amounts**: money spent (debits)
- **Positive amounts**: money received (credits)
- **Balance**: running total after each transaction
- **Cashback**: rewards earned (null if none)

### Category Codes (MCC)
MCC codes classify merchant types according to ISO 18245. Common examples:
- `5411` — Grocery stores
- `5814` — Fast food restaurants
- `4121` — Taxi services
- `5812` — Restaurants

The bank normalizes these codes for consistency.

### Timestamps
All transaction dates are converted to local time zone for easier analysis and display.

## Common ISO-4217 Numeric Currency Codes
- `980` — UAH (Ukrainian Hryvnia)
- `840` — USD (US Dollar)
- `978` — EUR (Euro)
