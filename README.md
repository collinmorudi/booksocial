# Book Social

This project is currently under active development. I am working diligently to bring you a comprehensive and well-documented release.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Contributing](#contributing)
- [Issues](#issues)
- [License](#license)
- [Contributors](#contributors)
- [Acknowledgments](#acknowledgments)

## Overview
**Book Social Network**

Book Social is a full-stack application that enables users to manage their book collections and engage with a community of book enthusiasts. It offers features such as user registration, secure email validation, book management (including creation, updating, sharing, and archiving), book borrowing with checks for availability, book return functionality, and approval of book returns. The application ensures security using JWT tokens and adheres to best practices in REST API design. The backend is built with Spring Boot 3 and Spring Security 6, while the frontend is developed using Angular with Bootstrap for styling.

## Features

- **User Registration**: Users can register for a new account.
- **Email Validation**: Accounts are activated using secure email validation codes.
- **User Authentication**: Existing users can log in to their accounts securely.
- **Book Management**: Users can create, update, share, and archive their books.
- **Book Borrowing**: Implements necessary checks to determine if a book is borrowable.
- **Book Returning**: Users can return borrowed books.
- **Book Return Approval**: Functionality to approve book returns.

(Note: The features listed above are only a glimpse into what the final product will include. Expect many more improvements and additions!)

## Technologies Used

### Backend
- **Spring Boot 3**
- **Spring Security 6**
- **JWT Token Authentication**
- **Spring Data JPA**
- **JSR-303 and Spring Validation**
- **OpenAPI and Swagger UI Documentation**
- **Docker**
- **GitHub Actions**
- **Keycloak**

### Frontend
- **Angular** (Component-Based Architecture, Lazy Loading, Authentication Guard)
- **OpenAPI Generator for Angular**
- **Bootstrap**

(UI is still under development)

## Getting Started

To clone the repository and get the project running locally:
```bash
git clone https://github.com/collinmorudi/booksocial.git
cd booksocial
./gradlew build
```

(Note: The specific instructions to build and run the project may change as development progresses.)

## Contributing

We welcome contributions from anyone who is interested in improving this project. Please keep in mind that the current state of the project may change frequently, and significant modifications are being made regularly.

### How to Contribute

1. Fork the repository.
2. Create your feature branch: `git checkout -b feature/YourFeatureName`
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature/YourFeatureName`
5. Open a pull request.

## Issues

For any issues or bug reports, please use the [issue tracker](https://github.com/collinmorudi/booksocial/issues).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributors

- Collin Morudi

## Acknowledgments

Special thanks to the developers and maintainers of the technologies used in this project. Their hard work and dedication make projects like this possible.

---

Stay tuned for more updates and the official documentation release! Thank you for your interest and support.

---