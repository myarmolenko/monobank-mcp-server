---
title: monobank.bank-currency
version: 2025-09-28
source: https://api.monobank.ua/bank/currency
---

# Bank Currency

**Returns:** an array of `Currency` objects describing current exchange rates.

## Conventions

-   **Currency codes**: ISO-4217 **numeric** codes (e.g., `980` for UAH, `840` for USD, `978` for EUR).
-   **Rates**: BigDecimal values for precise financial calculations.
-   **Timestamps**: converted to `LocalDateTime` for readability.
-   Strings are UTF-8.

## Currency Object

-   `baseCurrency` *(int)* --- ISO-4217 numeric code of the base currency (the currency you're converting FROM).
-   `quoteCurrency` *(int)* --- ISO-4217 numeric code of the quote currency (the currency you're converting TO, e.g., `980` for UAH).
-   `date` *(LocalDateTime)* --- timestamp of the rate update, converted from UNIX epoch seconds to local date-time.
-   `rateBuy` *(BigDecimal?)* --- buy rate (nullable, may be absent if unavailable).
-   `rateSell` *(BigDecimal?)* --- sell rate (nullable, may be absent if unavailable).
-   `rateCross` *(BigDecimal?)* --- cross rate (nullable, used when no direct buy/sell pair is provided).

## Common ISO-4217 Numeric Currency Codes

- `980` --- UAH (Ukrainian Hryvnia)
- `840` --- USD (US Dollar)
- `978` --- EUR (Euro)
