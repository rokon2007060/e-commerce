# E-commerce App

A comprehensive Android-based e-commerce application built with Java, featuring multi-role support for customers, sellers, and administrators, along with integrated mobile payment solutions.

## Project Overview

This is a full-featured e-commerce mobile application that supports three distinct user roles:
- Customers: Browse and purchase products
- Sellers: Manage and sell products
- Admins: Oversee platform operations

## Key Features

- Multi-role authentication and authorization (Customer, Seller, Admin)
- Product catalog with detailed product information and filtering
- Shopping cart management and checkout process
- Multiple payment gateway integration (Bkash, Nagad, Rocket, SSLCommerz)
- Firebase integration for authentication and data management
- Image handling with Glide and Picasso libraries
- Product caching for optimized performance
- Responsive UI with Material Design components

## Technology Stack

- Language: Java 8
- Build System: Gradle (Kotlin DSL)
- Android SDK: Target API 34, Min API 24
- Backend Services: Firebase (Authentication, Firestore, Realtime Database, Storage)
- Image Loading: Glide 4.16.0, Picasso 2.8
- Testing: JUnit 4, Robolectric, Mockito, Espresso
- Material Design: Material Components 1.12.0
- Payment Gateways: Bkash, Nagad, Rocket, SSLCommerz

## Project Structure

```
E-commerce-App/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/e_commerce_app/
│   │   │   │   ├── Activities (MainActivity, LoginActivity, RegistrationActivity, etc.)
│   │   │   │   ├── Adapters (CartAdapter, ProductAdapter, AdminProductAdapter, etc.)
│   │   │   │   ├── Models (Product, Cart, CartItem)
│   │   │   │   ├── Payment Services (BkashPayment, NagadPayment, RocketPayment)
│   │   │   │   ├── Utilities (ProductCache, ShopFacade, Singleton)
│   │   │   │   └── EcommerceApp.java
│   │   │   ├── res/ (Resources: layouts, drawables, values)
│   │   │   └── AndroidManifest.xml
│   │   ├── test/ (Unit tests)
│   │   └── androidTest/ (Instrumentation tests)
│   ├── build.gradle.kts
│   ├── google-services.json
│   └── libs/ (Third-party libraries including SSLCommerz)
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradle/ (Gradle wrapper)
├── gradlew / gradlew.bat
└── .gitignore
```

## Core Components

### Activities
- SplashActivity: Application splash screen
- MainActivity: Main entry point
- LoginActivity: User authentication
- RegistrationActivity: New user registration
- ForgotPasswordActivity: Password recovery
- CustomerActivity: Customer product browsing interface
- SellerActivity: Seller product management
- AdminActivity: Admin dashboard
- CartActivity: Shopping cart interface
- AddActivity: Product addition interface

### Payment Integration
The application supports multiple payment gateways commonly used in South Asia:
- PaymentMethod: Abstract payment interface
- BkashPayment: Bkash mobile money integration
- NagadPayment: Nagad digital payment integration
- RocketPayment: Rocket payment system integration
- SSLCommerz: Credit/debit card payment processing
- PaymentFactory: Factory pattern for payment processing
- PaymentCallback: Payment callback handler

Payment Request Models:
- BkashPaymentRequest
- NagadPaymentRequest
- RocketPaymentRequest

### Data Models
- Product: Product entity with details
- Cart: Shopping cart model
- CartItem: Individual cart items

### Utilities
- ProductCache: Caching mechanism for products
- ShopFacade: Facade pattern for shop operations
- Singleton: Singleton pattern implementation for shared resources

## Branches Overview

The project includes multiple development branches for feature isolation:

- main: Primary branch with stable code
- admin: Admin feature development and enhancements
- customer: Customer feature development and improvements
- seller: Seller feature development and seller-specific tools
- test: Testing and experimental features

## Dependencies

Key dependencies include:
- AndroidX libraries (AppCompat 1.3.1, ConstraintLayout 2.1.0, SwipeRefreshLayout 1.1.0)
- Firebase libraries:
  - Authentication 23.0.0
  - Firestore 25.0.0
  - Realtime Database
  - Cloud Storage
- Image loading libraries (Glide 4.16.0, Picasso 2.8)
- JSON serialization (Gson 2.10.1)
- Testing frameworks:
  - JUnit 4.13.2
  - Robolectric 4.7.3
  - Mockito 4.0.0
  - Espresso 3.4.0
- Material Design components 1.12.0

## Build Configuration

- Target SDK: 34
- Minimum SDK: 24
- Java Version: 1.8
- Gradle JVM args: -Xmx4608m -Dfile.encoding=UTF-8
- AndroidX enabled: true
- Non-transitive R class enabled: true

## Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Configure your Firebase project and update google-services.json
4. Set up payment gateway credentials (Bkash, Nagad, Rocket, SSLCommerz)
5. Build and run the application on an Android device or emulator (API 24 or higher)

## Development Notes

- The application uses modern Android development practices with AndroidX
- Firebase Firestore serves as the primary backend database for product and user data
- Payment processing supports multiple mobile payment providers and card-based payments
- The project implements design patterns (Factory, Singleton, Facade) for maintainability and scalability
- Product caching improves performance for frequently accessed product data
- Comprehensive test coverage with unit tests and instrumentation tests

## Repository Statistics

- Created: April 16, 2024
- Last Updated: May 26, 2024
- Primary Language: Java (100%)
- Visibility: Public
- Open Issues: 1
