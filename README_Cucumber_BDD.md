# Cucumber BDD Refactoring

This document describes the refactoring of the Selenium TestNG project to use Cucumber BDD (Behavior-Driven Development) framework.

## Project Structure

### New Directory Structure
```
src/
├── test/
│   ├── java/
│   │   ├── Runners/
│   │   │   └── TestRunner.java          # Cucumber test runner
│   │   └── StepDefinitions/
│   │       ├── Hooks.java                # Setup and teardown hooks
│   │       ├── LoginSteps.java           # Login step definitions
│   │       ├── RegisterSteps.java        # Registration step definitions
│   │       └── LogoutSteps.java          # Logout step definitions
│   └── resources/
│       └── features/
│           ├── login.feature             # Login scenarios
│           ├── register.feature          # Registration scenarios
│           └── logout.feature            # Logout scenarios
```

### Existing Structure (Preserved)
```
PageObjects/
├── Railway/
│   ├── GeneralPage.java                  # Base page object
│   ├── HomePage.java                     # Home page object
│   ├── LoginPage.java                    # Login page object
│   ├── RegisterPage.java                 # Registration page object
│   └── FAQPage.java                      # FAQ page object

Common/
├── Common/
│   └── Utilities.java                    # Utility methods
└── Constant/
    └── Constant.java                     # Test constants

TestCases/                                # Original TestNG tests (preserved)
├── BaseTest.java
├── LoginTest.java
├── CreateAccountTest.java
└── LogoutTest.java
```

## Key Changes

### 1. Maven Dependencies
Added Cucumber BDD dependencies to `pom.xml`:
- `cucumber-java` - Core Cucumber functionality
- `cucumber-testng` - TestNG integration
- `cucumber-core` - Core Cucumber features
- `cucumber-datatable` - Data table support
- `cucumber-picocontainer` - Dependency injection

### 2. Feature Files
Created Gherkin feature files that describe test scenarios in business-readable format:

**login.feature** - Contains 5 login scenarios:
- Successful login with valid credentials
- Failed login with blank username
- Failed login with invalid password
- Multiple failed login attempts
- Failed login with inactive account

**register.feature** - Contains 3 registration scenarios:
- Failed registration with existing email
- Successful registration with valid data
- Failed registration with mismatched passwords

**logout.feature** - Contains 1 logout scenario:
- Successful logout redirects to home page

### 3. Step Definitions
Created step definition classes that implement the Gherkin steps:

**LoginSteps.java** - Implements all login-related steps
**RegisterSteps.java** - Implements all registration-related steps
**LogoutSteps.java** - Implements all logout-related steps

### 4. Hooks
**Hooks.java** - Manages test setup and teardown:
- `@Before` - Initializes WebDriver and HomePage
- `@After` - Closes WebDriver

### 5. Test Runner
**TestRunner.java** - Configures Cucumber execution:
- Specifies feature files location
- Specifies step definitions package
- Configures reporting plugins
- Enables parallel execution

## Running Tests

### Using TestNG
Run the `TestRunner.java` class as a TestNG test to execute all Cucumber scenarios.

### Using Maven
```bash
mvn test
```

### Using IDE
Right-click on `TestRunner.java` and select "Run as TestNG Test".

## Reports
Cucumber generates reports in the `target/cucumber-reports/` directory:
- HTML report: `cucumber-pretty/index.html`
- JSON report: `cucumber.json`
- JUnit XML report: `cucumber.xml`

## Benefits of BDD Approach

1. **Business Readable**: Tests are written in natural language that stakeholders can understand
2. **Collaboration**: Encourages collaboration between developers, testers, and business analysts
3. **Documentation**: Feature files serve as living documentation
4. **Maintainability**: Clear separation between test logic and implementation
5. **Reusability**: Step definitions can be reused across multiple scenarios

## Migration Notes

- Original TestNG tests are preserved in the `TestCases/` directory for reference
- Page Objects remain unchanged and are reused by Cucumber step definitions
- All existing functionality is maintained while adding BDD capabilities
- Constants and utilities are shared between both testing approaches

## Next Steps

1. Run the tests to verify the implementation
2. Review and enhance the feature files if needed
3. Add more scenarios as required
4. Consider adding data-driven testing using Cucumber data tables
5. Integrate with CI/CD pipeline for automated execution
