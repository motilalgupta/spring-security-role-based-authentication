
# Spring Security Role-Based Authentication

A Spring Boot application demonstrating role-based authentication and method-level authorization using Spring Security with USER and ADMIN roles.

## 🚀 Overview

Role-Based Access Control (RBAC) is a security mechanism that restricts access to application resources based on the roles assigned to users.

This project demonstrates how to implement role-based authentication and authorization using Spring Security.

The application uses:

- In-memory user authentication.
- BCrypt password encoding.
- HTTP Basic authentication.
- Method-level security using `@PreAuthorize`.
- USER and ADMIN roles.

## 🛠️ Tech Stack

- Java
- Spring Boot
- Spring Security
- Spring Security Method Security
- Maven
- REST API

## 🔐 User Configuration

This project uses `InMemoryUserDetailsManager` to configure two users.

| Username | Password | Role |
|---|---|---|
| admin | admin | ADMIN |
| user | user | USER |

### UserConfig.java

```java
@Configuration
public class UserConfig {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(
            PasswordEncoder passwordEncoder) {

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

### 🔍 Configuration Explained

- `InMemoryUserDetailsManager` stores user details in application memory.
- `User.withUsername()` creates a user.
- `.roles("ADMIN")` assigns the ADMIN role.
- `.roles("USER")` assigns the USER role.
- `BCryptPasswordEncoder` hashes passwords before storing them.

**Note:** Spring Security automatically adds the `ROLE_` prefix when using `.roles()`.

## ⚙️ Security Configuration

### WebSecurity.java

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/api/about").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
```

### 🔍 Configuration Explained

- `@EnableWebSecurity` enables Spring Security web security support.
- `@EnableMethodSecurity` enables method-level security annotations.
- `csrf.disable()` disables CSRF protection.
- `permitAll()` allows public access to selected endpoints.
- `authenticated()` requires authentication for all remaining endpoints.
- `httpBasic()` enables HTTP Basic authentication.

## 🎯 Method-Level Authorization

This project uses `@PreAuthorize` to restrict service methods based on user roles.

### AccountService.java

```java
@Service
public class AccountService {

    @PreAuthorize("hasRole('USER')")
    public String getBalance() {
        return "you balance is : 80000";
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String closeAccount() {
        return "Account closed!";
    }
}
```

### 🔍 Authorization Explained

**1. USER Role**

```java
@PreAuthorize("hasRole('USER')")
```

- Only users with the USER role can check their account balance.
- ADMIN users are not automatically granted access to this method.

**2. ADMIN Role**

```java
@PreAuthorize("hasRole('ADMIN')")
```

- Only users with the ADMIN role can close an account.
- Regular USER accounts cannot access this functionality.

## 📌 API Endpoints

| HTTP Method | Endpoint | Access |
|---|---|---|
| GET | `/` | Public |
| GET | `/api/about` | Public |
| GET | `/api/balance` | USER |
| GET | `/api/close` | ADMIN |

## 🧪 Testing the APIs

### 1. Public Endpoint

```bash
curl http://localhost:8080/api/about
```

Expected response:

```text
Please wait, we are having lunch
```

No authentication is required.

### 2. Check Account Balance (USER)

```bash
curl -u user:user http://localhost:8080/api/balance
```

Expected response:

```text
you balance is : 80000
```

### 3. Close Account (ADMIN)

```bash
curl -u admin:admin http://localhost:8080/api/close
```

Expected response:

```text
Account closed!
```

## 🔒 Authorization Test Cases

| User | Endpoint | Expected Result |
|---|---|---|
| USER | `/api/balance` | 200 OK |
| USER | `/api/close` | 403 Forbidden |
| ADMIN | `/api/balance` | 403 Forbidden |
| ADMIN | `/api/close` | 200 OK |
| Unauthenticated | `/api/balance` | 401 Unauthorized |
| Unauthenticated | `/api/close` | 401 Unauthorized |
| Unauthenticated | `/api/about` | 200 OK |

## 📚 Learning Objectives

- Understand authentication and authorization in Spring Security.
- Implement role-based access control (RBAC).
- Configure in-memory user authentication.
- Understand `UserDetails` and `InMemoryUserDetailsManager`.
- Configure `BCryptPasswordEncoder`.
- Enable method-level security using `@EnableMethodSecurity`.
- Implement role-based method authorization using `@PreAuthorize`.
- Understand the difference between HTTP 401 and HTTP 403.

## ▶️ Getting Started

### Prerequisites

- Java version compatible with your Spring Boot version.
- Maven.

### Clone the Repository

```bash
git clone https://github.com/<your-username>/spring-security-role-based-authentication.git

cd spring-security-role-based-authentication
```

### Run the Application

```bash
./mvnw spring-boot:run
```

For Windows:

```bash
mvnw.cmd spring-boot:run
```

If Maven Wrapper is unavailable:

```bash
mvn spring-boot:run
```

## ⚠️ Security Note

This project is intended for learning purposes.

- In-memory users are suitable for learning and testing.
- Never use hardcoded credentials in production.
- Use strong passwords and secure password storage.
- Use HTTPS to protect HTTP Basic credentials.
- Disabling CSRF requires careful evaluation of the authentication mechanism.
- Apply the principle of least privilege when assigning roles.

## 👨‍💻 Author

**Motilal Gupta**
