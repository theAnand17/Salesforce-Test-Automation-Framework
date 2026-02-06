# Salesforce Test Automation Framework

> **A comprehensive QA automation framework for Salesforce testing**  
> Built with: **Java 19** | **Selenium 4.27** | **Cucumber 7.20** | **TestNG 7.10** | **Allure 2.29**

[![Java](https://img.shields.io/badge/Java-19-orange.svg)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium-4.27.0-green.svg)](https://www.selenium.dev/)
[![Cucumber](https://img.shields.io/badge/Cucumber-7.20.1-brightgreen.svg)](https://cucumber.io/)
[![TestNG](https://img.shields.io/badge/TestNG-7.10.2-red.svg)](https://testng.org/)
[![Allure](https://img.shields.io/badge/Allure-2.29.1-yellow.svg)](https://docs.qameta.io/allure/)

---

## 📋 Table of Contents

- [Features](#-features)
- [Quick Start](#-quick-start)
- [Configuration](#-configuration)
- [Running Tests](#-running-tests)
- [Project Structure](#-project-structure)
- [Example Tests](#-example-tests)
- [Custom Object Example](#-custom-object-example-bobo-enrollment)
- [Reporting](#-reporting)
- [Documentation](#-documentation)
- [Contributing](#-contributing)

---

## ✨ Features

### Core Capabilities
- ✅ **Hybrid Testing**: Selenium WebDriver + Salesforce REST API
- ✅ **BDD Framework**: Cucumber with Gherkin syntax
- ✅ **Data-Driven Testing**: Scenario Outlines with Examples
- ✅ **Page Object Model**: Clean separation of concerns
- ✅ **Component-Based Architecture**: Reusable UI components
- ✅ **Dynamic Element Discovery**: JavaScript-based element location for custom fields
- ✅ **Encrypted Credentials**: Jasypt encryption for sensitive data
- ✅ **Multi-Browser Support**: Chrome, Edge, Firefox
- ✅ **Headless Execution**: CI/CD ready
- ✅ **Parallel Execution**: TestNG parallel test execution
- ✅ **Rich Reporting**: Allure reports with screenshots
- ✅ **Logging**: Log4j2 with JSON and console logging

### Salesforce-Specific Features
- 🔹 Salesforce Lightning UI automation
- 🔹 REST API integration for data setup/validation
- 🔹 Custom object and field handling
- 🔹 Account, Contact, and custom object CRUD operations
- 🔹 OAuth authentication support

---

## 🚀 Quick Start

### Prerequisites

- **Java 19** or higher ([Download](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.6+** ([Download](https://maven.apache.org/download.cgi))
- **Salesforce Developer Account** ([Sign up](https://developer.salesforce.com/signup))

### Installation

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd salesforce-test-automation-framework
   ```

2. **Install dependencies**
   ```bash
   mvn clean install -DskipTests
   ```

3. **Configure your environment** (see [Configuration](#-configuration))

4. **Run example tests**
   ```bash
   mvn test -Dcucumber.filter.tags="@Examples"
   ```

---

## ⚙️ Configuration

### Step 1: Create Configuration Files

The framework uses two configuration files that are **NOT** tracked in Git for security:

#### 1. `environment.yaml`

Copy the template and fill in your values:
```bash
cp environment.yaml.example environment.yaml
```

**Required fields:**
- `Salesforce`: Your Salesforce instance URL
- `TrialUser_Username`: Your Salesforce username
- `TrialUser_Password`: **Encrypted** password (see encryption instructions below)
- `TestAccount_ID`: A valid Salesforce Account ID for testing

#### 2. `config.properties`

Copy the template and fill in your values:
```bash
cp config.properties.example config.properties
```

**Required fields:**
- `client_id`: Salesforce Connected App Consumer Key
- `client_secret`: Salesforce Connected App Consumer Secret
- `username`: Your Salesforce username
- `password`: **Encrypted** password

### Step 2: Encrypt Your Passwords

**Why encrypt?** Even though config files are in `.gitignore`, encryption adds an extra security layer.

**How to encrypt:**
1. Go to [Jasypt Online Tool](https://www.devglan.com/online-tools/jasypt-online-encryption-decryption)
2. Select algorithm: `PBEWithMD5AndDES`
3. Enter your password and a secret key
4. Click "Encrypt"
5. Copy the encrypted value to your config files

**⚠️ Important:** Store your encryption key securely (e.g., environment variable, password manager)

### Step 3: Create a Salesforce Connected App

1. Log in to Salesforce
2. Go to **Setup** > **Apps** > **App Manager**
3. Click **New Connected App**
4. Fill in:
   - **Connected App Name**: "Test Automation"
   - **API Name**: Auto-generated
   - **Contact Email**: Your email
5. Enable **OAuth Settings**:
   - **Callback URL**: `https://login.salesforce.com/services/oauth2/callback`
   - **Selected OAuth Scopes**: 
     - Full access (full)
     - Perform requests on your behalf at any time (refresh_token, offline_access)
6. Save and copy the **Consumer Key** (client_id) and **Consumer Secret** (client_secret)

---

## 🧪 Running Tests

### Maven Command (Full)
```bash
mvn clean test -Denvironment=QA -DskipTests=false -Denv=Test allure:report -f pom.xml
```

**Parameters:**
- `-Denvironment=QA` - Specifies which environment config to use from `environment.yaml`
- `-DskipTests=false` - Ensures tests are executed
- `-Denv=Test` - Environment parameter for test execution
- `allure:report` - Generates Allure report after tests
- `-f pom.xml` - Specifies the POM file

### Run All Tests
```bash
mvn clean test
```

### Run Specific Tags
```bash
# Run only example tests (no Salesforce credentials needed)
mvn test -Dcucumber.filter.tags="@Examples"

# Run Salesforce tests
mvn test -Dcucumber.filter.tags="@Salesforce"

# Run BoBo Enrollment tests (requires custom object setup)
mvn test -Dcucumber.filter.tags="@BoBo"
```

### Run Specific Feature File
```bash
mvn test -Dcucumber.features="src/test/resources/Features/Examples.feature"
```

### Headless Mode
Set in `config.properties`:
```properties
headlessMode=true
```

### Generate Allure Report
```bash
mvn allure:serve
```

---

## 📁 Project Structure

```
salesforce-test-automation-framework/
├── src/
│   ├── main/java/base/              # Core framework utilities
│   │   ├── Environment.java         # Environment config loader
│   │   ├── LaunchBrowser.java       # WebDriver management
│   │   ├── PasswordDecrypt.java     # Jasypt decryption
│   │   └── ...
│   └── test/java/Test101/
│       ├── Components/              # Reusable UI components
│       │   ├── SFDC/               # Salesforce components
│       │   ├── BoBo/               # BoBo custom object components
│       │   └── Common.java         # Shared utilities
│       ├── Pages/                   # Page Object Model classes
│       ├── StepDefs/               # Cucumber step definitions
│       └── TestRunner.java         # TestNG runner
├── Features/                        # Cucumber feature files
│   ├── Examples.feature            # Public website examples
│   ├── SalesforceTest.feature      # Salesforce test scenarios
│   └── BoBo Enrollment.feature     # Custom object example
├── environment.yaml.example         # Environment config template
├── config.properties.example        # API config template
├── pom.xml                         # Maven dependencies
└── README.md                       # This file
```

---

## 📚 Example Tests

The framework includes example tests that work **without Salesforce credentials**:

### Flipkart Search Example
**File:** `Features/Examples.feature`

```gherkin
@FlipkartSearchResult @Examples
Scenario Outline: Search for products on Flipkart and select from suggestions
  Given User launch "<Website>" in "<Browser>"
  When Search for "<SearchText>"
  Then Select "<Suggestion>" from the suggestion list
  And Validate search result is displayed and save screenshot
  And Close the browser
```

**Purpose:** 
- Learn the framework structure
- Verify your setup works
- Test without Salesforce access

**Run it:**
```bash
mvn test -Dcucumber.filter.tags="@Examples"
```

---

## 🎯 Custom Object Example: BoBo Enrollment

This framework includes a **real-world example** of testing a custom Salesforce object with custom fields.

### What is BoBo Enrollment?

BoBo Enrollment is a custom Salesforce object created to demonstrate:
- ✅ Custom object creation and validation
- ✅ Custom field handling (text, picklist, date, etc.)
- ✅ Complex business logic testing
- ✅ JavaScript-based dynamic element discovery
- ✅ API + UI hybrid testing

### Features Demonstrated

**Files:**
- `Features/BoBo Enrollment.feature` - Test scenarios
- `src/test/java/Test101/Components/BoBo/` - Component classes
- `src/test/java/Test101/Components/SFDC/AccountDataFactory.java` - Data factory pattern

**Capabilities:**
- Account creation via API
- BoBo enrollment creation via UI
- Custom field population
- Tax exemption handling
- Cancellation workflows

### Running BoBo Tests

**⚠️ Prerequisites:**
You need to create the BoBo Enrollment custom object in your Salesforce org with the required custom fields.

**Custom Object Schema:**

#### BoBo Account Program (Custom Object)
**API Name:** `BoBo_Account_Program__c`

**Custom Fields:**

| Field Label | API Name | Type | Required | Description |
|------------|----------|------|----------|-------------|
| Account | `Account__c` | Lookup(Account) | Yes | Related Account |
| BoBo URL | `BoBo_URL__c` | URL | Yes | BoBo website URL |
| BoBo Address | `BoBo_Address__c` | Text Area | No | Physical address |
| Sub Program | `Sub_Program__c` | Picklist | Yes | Program type (e.g., Bingo) |
| Contact | `Contact__c` | Lookup(Contact) | Yes | Primary contact |
| Enrollment Status | `Enrollment_Status__c` | Picklist | Yes | Status (Enrolled, Out for Approval, Cancelled) |
| Cancellation Reason | `Cancellation_Reason__c` | Picklist | No | Reason for cancellation |
| Cancellation Notes | `Cancellation_Notes__c` | Text Area(Long) | No | Additional cancellation details |
| Cancelled Date | `Cancelled_Date__c` | Date | No | Date of cancellation |

#### Account Custom Fields
**Added to Standard Account Object:**

| Field Label | API Name | Type | Description |
|------------|----------|------|-------------|
| BoBo Customer | `BoBo_Customer__c` | Checkbox | Indicates if account is enrolled in BoBo |
| BoBo Tax Exempt | `BoBo_Tax_Exempt__c` | Checkbox | Tax exemption status |

**Run tests:**
```bash
mvn test -Dcucumber.filter.tags="@BoBo"

# Or with @CustomObject tag
mvn test -Dcucumber.filter.tags="@CustomObject"
```

**Test Scenarios Included:**
- ✅ BoBo Enrollment with approval workflow
- ✅ BoBo Cancellation with approval workflow
- ✅ Enrollment validation (account without contact)
- ✅ Duplicate enrollment prevention
- ✅ Tax exemption handling

### Using as a Template

**Don't have BoBo object?** Use these files as a template for your own custom objects:
1. Copy the component structure
2. Update field names and locators
3. Modify step definitions
4. Create your own feature files

---

## 📊 Reporting

### Allure Reports

**Generate and view:**
```bash
mvn allure:serve
```

**Features:**
- ✅ Test execution timeline
- ✅ Screenshots on failure
- ✅ Step-by-step execution details
- ✅ Environment information
- ✅ Historical trends

### Logs

**Location:** `target/logs/`

**Types:**
- `console.log` - Console output
- `json.log` - Structured JSON logs

---

## 📖 Documentation

### Detailed Documentation

See [`FRAMEWORK_DOCUMENTATION.md`](FRAMEWORK_DOCUMENTATION.md) for:
- Complete architecture overview
- Design patterns explained
- Component details
- API integration guide
- Custom field handling
- Troubleshooting

### Key Concepts

**Page Object Model (POM)**
- Pages contain locators
- Components contain actions
- Step definitions orchestrate tests

**Data Factory Pattern**
- `AccountDataFactory` creates test data via API
- Reduces test execution time
- Ensures clean test data

**Dynamic Element Discovery**
- JavaScript-based element location
- Handles Salesforce Lightning's dynamic IDs
- Works with custom fields

---

## 🤝 Contributing

Contributions are welcome! Please:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style
- Follow existing patterns
- Add Javadoc comments for public methods
- Write meaningful commit messages
- Include tests for new features

---

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 🙏 Acknowledgments

- Selenium WebDriver team
- Cucumber BDD framework
- Salesforce Developer Community
- TestNG and Allure teams

---

## 📧 Contact

For questions or support, please open an issue on GitHub.

---

**Happy Testing! 🚀**