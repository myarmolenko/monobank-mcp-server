<div align="center">
    <picture>
        <source media="(prefers-color-scheme: dark)" srcset="./docs/img/banner-light.svg">
        <source media="(prefers-color-scheme: light)" srcset="./docs/img/banner-dark.svg">
        <img alt="Monobank MCP Banner" src="./docs/img/banner-light.svg" width="60%">
    </picture>
</div>
<hr>
<div align="center" style="line-height: 1;">
    <a href="https://github.com/modelcontextprotocol/servers" target="_blank" style="margin: 2px;">
        <img alt="MCP Server" src="https://badge.mcpx.dev?type=server" style="display: inline-block; vertical-align: middle;"/>
    </a>
    <a href="https://github.com/yarmmak/monobank-mcp-server/releases" target="_blank" style="margin: 2px;">
        <img alt="GitHub release" src="https://img.shields.io/github/v/release/yarmmak/monobank-mcp-server" style="display: inline-block; vertical-align: middle;"/>
    </a>
    <a href="https://github.com/yarmmak/monobank-mcp-server/blob/main/LICENSE" target="_blank" style="margin: 2px;">
         <img alt="MIT License" src="https://img.shields.io/github/license/yarmmak/monobank-mcp-server" style="display: inline-block; vertical-align: middle;"/>
   </a>
</div>

## About

A [Model Context Protocol (MCP)](https://modelcontextprotocol.io/docs/getting-started/intro) server that brings your Monobank 
account data directly into AI conversations. Access account balances, transaction history, and currency rates seamlessly within 
Claude Desktop and other MCP-compatible applications through [Monobank's official open API](https://api.monobank.ua/docs/index.html).

## Features

- **Real-time Currency Rates:** Access current exchange rates from Monobank
- **Client Information:** View your profile, accounts list, and associated jars
- **Transaction History:** Retrieve statements for any period (up to 31 days)
- **Account Overview:** Get a comprehensive summary of your banking activity
- **Smart Prompts:** Pre-built templates for common banking queries
- **Selectable Resources:** Choose specific data sources to give AI context about your account

## Prerequisites

To use this MCP server, you need:
- A Monobank account
- API access token (obtain from [Monobank API Portal](https://api.monobank.ua))
- Claude Desktop or another MCP-compatible client
- (For Docker method) Docker installed on your system

## Getting Started

<details open>
    <summary style="font-size: 1.2em; font-weight: bold;">
        Using Docker (Recommended)
    </summary>
<br>

>  **Note**: Requires [Docker](https://www.docker.com/get-started) to be installed on your system.

1. **Pull the Docker image:**

   ```bash
    docker pull makyarm/monobank-mcp-server:latest
    ```

2. **Configure your MCP client (e.g., Claude Desktop):**
 
    On MacOS: `~/Library/Application Support/Claude/claude_desktop_config.json`  
    On Windows: `%APPDATA%/Claude/claude_desktop_config.json`

    ```json
    {
      "mcpServers": {
        "monobank": {
          "command": "docker",
          "args": [
            "run", "--rm", "-i",
            "-e", "MONOBANK_API_TOKEN=your_api_token",
            "makyarm/monobank-mcp-server:latest"
          ]
        }
      }
    }
    ```
    
    Replace `your_api_token` with your actual Monobank API token.

3. **Restart Claude Desktop to load the new configuration.**

</details>

<details>
    <summary style="font-size: 1.2em; font-weight: bold;">
        Manual Installation
    </summary>

1. **Clone and build the project:**
    ```bash
    git clone https://github.com/yourusername/monobank-mcp-server.git
    cd monobank-mcp-server
    mvn clean package
    ```

    The JAR file will be created in the `/target` directory.

2. **Configure your MCP client (e.g., Claude Desktop):**
    
    On MacOS: `~/Library/Application Support/Claude/claude_desktop_config.json`  
    On Windows: `%APPDATA%/Claude/claude_desktop_config.json`
    
    ```json
    {
      "mcpServers": {
        "monobank": {
          "command": "java",
          "args": [
            "-jar",
            "/absolute/path/to/target/monobank-mcp-server.jar"
          ],
          "env": {
            "MONOBANK_API_TOKEN": "your_api_token"
          }
        }
      }
    }
    ```

    Replace `/absolute/path/to/target/monobank-mcp-server.jar` with the actual path to your JAR file and `your_api_token` with your Monobank API token.

3. **Restart Claude Desktop to load the new configuration.**

</details>

## Available Tools

Once configured, the MCP server provides the following tools, resources and prompts that can be used in your AI conversations:

<details open>
    <summary style="font-size: 1.2em; font-weight: bold;">
        🏦 Monobank Tools
    </summary>

Core tools for accessing Monobank banking services:

- **`retrieve_all_currencies`** - Get current exchange rates for all currencies from Monobank
- **`retrieve_client_information`** - Retrieve your Monobank account information, balance, and card details
- **`retrieve_statements`** - Fetch transaction history for a specified account and time period (up to 31 days)

</details>
<details>
    <summary style="font-size: 1.2em; font-weight: bold;">
        🛠️ Utility Tools
    </summary>

Helper tools to enhance functionality:

- **`resolve_time_to_epoch`** - Parse natural-language date/time expressions (like "two weeks ago" or "December 1, 2025") and convert them to Unix epoch timestamps for use with statement retrieval

</details>
<details>
    <summary style="font-size: 1.2em; font-weight: bold;">
        📚 Resources
    </summary>

Structured resources that can be attached to conversations to give Claude context about your account:

- **`monobank://bank/currency-rates`** - Real-time exchange rates for all currency pairs (cached by bank ~5 min)
- **`monobank://personal/client-info`** - Your complete account profile, balances, and card information
- **`monobank://personal/statements`** - Recent transaction history from your primary account

*Resources are automatically available when needed, or you can explicitly reference them in your queries.*

</details>
<details>
    <summary style="font-size: 1.2em; font-weight: bold;">
        💬 Prompts
    </summary>

Pre-configured prompt templates for common use cases:

- **`Account Overview`** - Generates a comprehensive overview of your Monobank account, including balances and recent activity
- **`Interpret Monobank Statements`** - Provides rules for analyzing and reasoning about transaction statements
- **`Interpret Monobank Currency Record`** - Guidelines for understanding currency exchange rate data
- **`Interpret Monobank Client Information`** - Instructions for interpreting account, jar, and client profile data

*Prompts help Claude better understand and analyze your Monobank data with domain-specific context.*

</details>

## Example Usage

**Check your spending this month:**
```
"Show me my transactions from the beginning of this month"
→ Automatically converts time expressions and fetches your statements
```

**Monitor exchange rates:**
```
"What's the current USD to UAH exchange rate?"
→ Retrieves live exchange rates from Monobank
```

**Generate a financial summary:**
```
"Summarize my spending for the last two weeks"
→ Analyzes your recent transactions and provides insights
```

## Important Notes

> **⚠️ Security**: Keep your API token private and never share it publicly. It provides access to your personal banking data.

> **🔒 Read-Only**: This server provides read-only access to your Monobank data. It cannot perform transactions or modify your account.

> **⏱️ Rate Limits**: Monobank API has rate limits. Avoid making excessive requests in short periods. 

## Disclaimer

This project is not affiliated with Monobank. It was created independently to facilitate integration of Monobank's banking services with MCP-compatible applications.

---

<div align="center">
    Made with ❤️ for the Monobank community
</div>
