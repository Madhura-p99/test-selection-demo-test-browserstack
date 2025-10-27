# 🎯 Smart Test Selection Demo - BrowserStack TestNG Framework

A comprehensive TestNG-based automation framework with **Smart Test Selection** capabilities, demonstrating how AI-powered test selection can optimize your test execution by running only relevant tests based on code changes.

## 📋 Table of Contents

- [What is Smart Test Selection?](#-what-is-smart-test-selection)

- [Prerequisites](#-prerequisites)

- [Quick Start](#-quick-start)

- [Test Execution Examples](#-test-execution-examples)

- [Smart Test Selection Configuration](#-smart-test-selection-configuration)

- [How Smart Test Selection Works?](#-how-smart-test-selection-works)
  
- [How to Use the Demo Code Repo for Smart Test Selection?](#-how-to-use-the-demo-code-repo-for-smart-test-selection)

- [BrowserStack Integration](#-browserstack-integration)

---

## 🤖 What is Smart Test Selection?

**Smart Test Selection** is an AI-powered feature that analyzes your code changes and intelligently selects only the relevant tests to run, significantly reducing test execution time while maintaining confidence in your releases.

### How It Works:

1.  **Code Analysis**: Analyzes changes between base and feature branches.
2.  **Impact Detection**: Identifies which application modules are affected by the changes.
3.  **Test Selection**: Automatically selects only relevant tests that cover the changed code.
4.  **Execution Modes**:
    -  `relevantFirst`: Runs relevant tests first, then remaining tests.
    -  `relevantOnly`: Runs only relevant tests.


### Benefits
- ⚡ **Faster Feedback** — Reduce test execution time
- 💰 **Cost Optimization** — Lower cloud testing costs
- 🎯 **Focused Testing** — Run tests that matter for your changes
- 🚀 **Faster CI/CD** — Accelerate your deployment pipeline


---

## 📦 Prerequisites

### Required Software

-  **Java 11 or higher**: [Download Java](https://www.oracle.com/java/technologies/downloads/)

-  **Maven 3.6 or higher**: [Download Maven](https://maven.apache.org/download.cgi)

-  **Google Chrome**: [Download Chrome](https://www.google.com/chrome/)

-  **Git**: [Download Git](https://git-scm.com/downloads)

### BrowserStack Account

- Sign up at [BrowserStack](https://www.browserstack.com/) for cloud testing

---

## 🚀 Quick Start

### Step 1: Clone and Setup Demo Application
```
# Clone the demo application repository
git clone https://github.com/browserstack/test-selection-demo-app-browserstack.git
cd test-selection-demo-app-browserstack

# Install all dependencies (root, client, and server)
npm run install:all

# Start the application (runs on port 3000)
npm start
```

The application will start:
-  **Frontend**: http://localhost:3000
-  **Backend API**: http://localhost:5001

Keep this running in a separate terminal.

### Step 2: Clone and Setup Test Repository

```
# Clone this test repository
git clone https://github.com/browserstack/test-selection-demo-test-browserstack.git
cd test-selection-demo-test-browserstack

# Build the project (downloads dependencies)
mvn clean install
```

### Step 3: Run Your First Tests

```
# Run smoke tests locally (fastest way to verify setup)
mvn clean test -P smoke-tests

# Run all tests locally
mvn test
```
---

## 🎯 Test Execution Examples

```
# Quick smoke tests (5-10 minutes)
mvn clean test -P smoke-tests

# Critical tests for release validation (5-7 minutes)
mvn clean test -P critical-tests

# UI-focused tests (8-12 minutes)
mvn clean test -P ui-tests

# Management features (all user, product, task, order tests)
mvn clean test -P management-tests

# Full regression suite (20-30 minutes)
mvn clean test -P regression-tests

# Performance tests
mvn clean test -P performance-tests
```

## ⚙️ Smart Test Selection Configuration

### Two Ways to Enable Smart Test Selection

BrowserStack offers **two methods** to enable Smart Test Selection in your test automation workflows:

1. **Repository Cloning:**  
   BrowserStack clones your application source code locally (from a git repo or local path) to analyze code changes and select relevant tests.  
   [Learn more](https://www.browserstack.com/docs/automate/selenium/smart-test-selection?fw-lang=java#Repo_cloning)

2. **GitHub App Integration:**  
   BrowserStack's GitHub App connects directly to your GitHub repository for seamless code analysis and test selection—no manual cloning required.  
   [Learn more](https://www.browserstack.com/docs/automate/selenium/smart-test-selection?fw-lang=java#GitHub_app)

---

> **Demo Note:**  
> For this project, **repository cloning** is used, since the demo application code must be cloned locally for test execution.

---

### BrowserStack Configuration: `browserstack.yml`

Update your `browserstack.yml` as follows to enable Smart Test Selection using repo cloning:

```yaml
browserstackLocal: true # for local testing
testOrchestrationOptions:
  runSmartSelection:
    enabled: true
    source:
      - '<path_to_demo_code_locally_cloned>'
    mode: 'relevantFirst' # or 'relevantOnly'
```

- Replace `<path_to_demo_code_locally_cloned>` with the actual local path of the cloned demo application repository.

**Execution Modes:**
- `relevantFirst` (Recommended): Runs relevant tests first, then remaining tests
- `relevantOnly`: Runs only relevant tests

---

## ⏳ How Smart Test Selection Works?

- **Project Name and Build Name:**  
  The `projectName` and `buildName` in your configuration must be static and consistent across builds. This ensures that all executions are grouped together for model training and predictions.

- **Training Data Collection:**  
  Data collection and model training occurs when a build contains at least one test failure. The system needs to observe failing and passing tests to learn code-to-test relationships.

- **Initial Training:**  
  The first 12-20 builds (with at least one failure) are used for initial data collection and model training. Once enough builds have run and the accuracy threshold is met, Smart Test Selection will start making predictions from the next build onwards.

---

## 🕹️ How to Use the Demo Code Repo for Smart Test Selection?

1. **Use an Existing PR or Create a New One:**  
   To simulate code changes, you can **use any existing pull request (PR)** or **create a new PR** in `test-selection-demo-app-browserstack` with updates or bugs.

2. **Check Out the Feature Branch Locally:**  
   Clone the demo application repository and check out the branch containing your changes (feature branch).

3. **Run Tests with Smart Test Selection:**  
   When you execute tests (for example, from the SDK), Smart Test Selection will compare the code on your checked-out branch with the origin branch (usually `main`).  
   It will analyze PR changes, train the model, and predict which tests to run based on the impacted code.

---

## 🌐 BrowserStack Integration

### Setup BrowserStack
1. Sign up at [BrowserStack](https://www.browserstack.com/)
2. Get your credentials from [Account Settings](https://www.browserstack.com/accounts/settings)
3. Update \`browserstack.yml\`:
```
userName: YOUR_USERNAME
accessKey: YOUR_ACCESS_KEY
```

---

## 🚀 Quick Reference
| Need | Command |

|------|---------|

| **Quick validation** | \`mvn clean test -P smoke-tests\` |

| **Complete testing** | \`mvn clean test -P regression-tests\` |

---

## 📚 Further Reading

- [Smart Test Selection Docs (Java Example)](https://www.browserstack.com/docs/automate/selenium/smart-test-selection?fw-lang=java)


---

**For more information, visit [BrowserStack Documentation](https://www.browserstack.com/docs).**
