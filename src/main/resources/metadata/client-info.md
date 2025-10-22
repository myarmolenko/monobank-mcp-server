---
title: monobank.client-info
version: 2025-09-28
source: https://api.monobank.ua/personal/client-info
---

# Client Info (Personal)

**Returns:** one `ClientInfo` object describing the authorized client, their accounts and jars.

## Conventions
- **Money**: BigDecimal in **major units** (e.g., `100.50` UAH).
- **Currency codes**: ISO-4217 **numeric** (e.g., `980` for UAH, `840` for USD, `978` for EUR).
- **Timestamps**: converted to `LocalDateTime` for readability.
- Strings are UTF-8; names may be Ukrainian.

## ClientInfo Object
- `name` *(String)* — client's full name.
- `accounts` *(List\<Account\>)* — list of personal accounts/cards.
- `jars` *(List\<Jar\>)* — list of savings jars.

## Account Object
- `id` *(String)* — internal account identifier.
- `type` *(String)* — account type (e.g., `black`, `white`, `fop`).
- `currencyCode` *(int)* — ISO-4217 numeric currency code.
- `balance` *(BigDecimal)* — current balance in major currency units.
- `creditLimit` *(BigDecimal)* — credit limit in major currency units.
- `cashbackType` *(String?)* — cashback program type (e.g., `UAH`, may be null).
- `iban` *(String)* — account IBAN.
- `maskedPan` *(List\<String\>)* — masked card numbers (e.g., `["537541******1234"]`).

## Jar Object
- `title` *(String)* — jar name/purpose.
- `description` *(String)* — jar description.
- `currencyCode` *(int)* — ISO-4217 numeric currency code.
- `balance` *(BigDecimal)* — current saved amount in major currency units.
- `goal` *(BigDecimal)* — target savings goal in major currency units.
- `progressPercentage` *(int)* — progress toward goal (0-100+).

## Common ISO-4217 Numeric Currency Codes
- `980` — UAH (Ukrainian Hryvnia)
- `840` — USD (US Dollar)
- `978` — EUR (Euro)
