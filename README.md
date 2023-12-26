# Social Media Application
- [Svenska](README_SE.md)
- [Polski](README_PL.md)

## Overview
The Social Media Application is a platform showcasing my proficiency in Java development, Spring framework, and microservices architecture. It provides users with the ability to connect, share posts, engage with content, and communicate seamlessly with the built in messeging function. The application encompasses features such as adding friends, posting text and image posts, commenting on posts, answering to other comments, and liking content to influence its popularity.

## Key Features
- **Post Creation:** Users can create and share text or image-based posts with their network.

- **Commenting and Answering:** Users can engage in conversations by commenting on posts or responding to other comments.

- **Liking System:** The application includes a liking system for both posts and comments, influencing the popularity of content.

- **Friendship Management:** Users can connect with others by sending and accepting friend requests, expanding their social network.

- **Messaging Functionality:** Real-time messaging functionality enables users to communicate with each other through text and image messages.

- **Popularity Metrics:** The popularity of posts and comments is determined by the number of likes and comments, impacting their visibility.

- **User Registration and Authentication:** Robust user registration and authentication mechanisms ensure data security and access control. The application employs JWT (JSON Web Tokens) for secure user authentication.

- **Unit Testing with JUnit and Mockito:** Each microservice undergoes comprehensive unit testing with JUnit and Mockito to ensure functionality and identify and address potential issues.

## Technologies Used
- **Java:** The primary programming language.

- **Spring Framework:** Leveraging the Spring ecosystem, including Spring Boot, Spring Data JPA, and Spring Security, to build scalable microservices.

- **Microservices Architecture:** Employing microservices architecture for modular and scalable development, allowing for the efficient management of complex features.

- **JUnit and Mockito:** Utilizing JUnit and Mockito for thorough unit testing to ensure the reliability of microservices.

- **PostgreSQL:** Managing data, including posts and user information, using PostgreSQL as the database management system.

- **RabbitMQ:** Facilitating real-time communication between microservices, particularly for handling events and messages.

- **Resilience4J:** Ensuring resilience and robustness in microservices communication by incorporating Resilience4J as a circuit breaker.

## Microservices

### Api Gateway
Responsible for routing and providing guidance to various microservices.

### Security-Service
Manages authentication and authorization, issuing JWTs for secure communication.

### User-Service
Handles user data, including contact information and friendship management. Implements Event-Driven Architecture for efficient data updates.

### Post-Service
Manages the creation, liking, and commenting on posts, influencing their popularity metrics.

### Comment-Service
Handles the commenting and answering functionalities, contributing to the engagement within the community.

### Like-Service
Manages the liking system, influencing the popularity of both posts and comments.

### Messaging-Service
Enables real-time messaging functionality, allowing users to communicate through text and image messages.

### Friend-Service
Manages friend-related functionalities, including friend requests and connections.

The application is designed to provide a rich and interactive social experience, allowing users to connect, share, and engage within a vibrant community.