# CreTech 🚀

[![Made with Love](https://img.shields.io/badge/Made%20with-❤️-red.svg)](https://github.com/Void604)
[![GitHub Stars](https://img.shields.io/github/stars/Void604/CreTech?style=social)](https://github.com/Void604/CreTech/stargazers)
[![GitHub Forks](https://img.shields.io/github/forks/Void604/CreTech?style=social)](https://github.com/Void604/CreTech/network/members)

> A collection of diverse projects showcasing various development skills and approaches to building different types of applications.

---

## 🛠️ Technologies Used

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![Node.js](https://img.shields.io/badge/Node.js-43853D?style=for-the-badge&logo=node.js&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![Socket.io](https://img.shields.io/badge/Socket.io-black?style=for-the-badge&logo=socket.io&badgeColor=010101)

---

## 📋 Table of Contents

- [Projects Overview](#projects-overview)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Contributing](#contributing)
- [Author](#author)
- [License](#license)

---

## 🎯 Projects Overview

### 1. 💬 Chat Application

[![Netlify Status](https://api.netlify.com/api/v1/badges/your-site-id/deploy-status)](https://app.netlify.com/sites/commonchat/deploys)
[![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)](https://reactjs.org/)
[![Node.js](https://img.shields.io/badge/Node.js-43853D?style=for-the-badge&logo=node.js&logoColor=white)](https://nodejs.org/)
[![Socket.io](https://img.shields.io/badge/Socket.io-black?style=for-the-badge&logo=socket.io&badgeColor=010101)](https://socket.io/)

A real-time chat application built with modern web technologies, offering seamless communication through public chatrooms and private messaging.

**🔗 Live Demo:** [https://commonchat.netlify.app/](https://commonchat.netlify.app/)

#### ✨ Key Features

- 🔐 **Secure Authentication** - JWT-based login and signup
- 🏠 **Chatroom Creation** - Create public chatrooms for group discussions
- 📱 **Direct Messaging** - Private one-on-one conversations
- ⚡ **Real-time Communication** - Instant message delivery with Socket.IO
- 🎨 **Responsive Design** - Works seamlessly across all devices

#### 🛠️ Tech Stack

- **Frontend:** React, JavaScript, HTML, CSS, Java
- **Backend:** Node.js, Express.js
- **Real-time:** Socket.IO
- **Authentication:** JSON Web Tokens (JWT)
- **Deployment:** Netlify

#### 🚀 Future Enhancements

- [ ] User Profiles with avatars
- [ ] Media sharing capabilities
- [ ] Advanced search functionality
- [ ] Admin panel for management
- [ ] Push notifications

---

### 2. ✅ To-Do List Website

[![Netlify Status](https://api.netlify.com/api/v1/badges/your-todo-site-id/deploy-status)](https://app.netlify.com/sites/todo-taskmgmt/deploys)
[![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)](https://reactjs.org/)
[![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)](https://developer.mozilla.org/en-US/docs/Web/JavaScript)

A simple yet effective task management application to help you stay organized and productive.

**🔗 Live Demo:** [https://todo-taskmgmt.netlify.app/](https://todo-taskmgmt.netlify.app/)

#### ✨ Key Features

- ➕ **Add Tasks** - Easily create new tasks
- 📅 **Set Deadlines** - Assign due dates to track priorities
- ✅ **Mark Complete** - Visual progress tracking
- 👀 **View Management** - Separate views for active and completed tasks
- 📱 **Responsive Design** - Mobile-friendly interface
- 💾 **Local Storage** - Data persistence without server dependency

#### 🛠️ Tech Stack

- **Frontend:** HTML, CSS, JavaScript, React, Java
- **Storage:** Browser Local Storage

#### 📌 How to Use

1. **Adding Tasks:** Enter description and optional deadline
2. **Managing Tasks:** Check off completed items
3. **Viewing Progress:** Switch between active and completed views
4. **Organizing:** Delete tasks as needed

#### 🚀 Future Enhancements

- [ ] Task prioritization system
- [ ] Deadline reminders
- [ ] Task editing capabilities
- [ ] Cross-device synchronization

---

### 3. 🏦 Java Banking System

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)](https://maven.apache.org/)

A comprehensive banking system demonstrating enterprise-level Java development with database integration.

**🔗 Live Demo:** [https://bankingsystemwebsite-securebank.netlify.app/](https://bankingsystemwebsite-securebank.netlify.app/)

#### ✨ Key Features

##### 👥 Customer Management
- Register new customers
- View and update customer details
- Comprehensive customer profiles

##### 💰 Account Management
- Multiple account types (Checking, Savings, Credit)
- Account balance tracking
- Account lifecycle management

##### 💸 Transaction Processing
- Secure deposit and withdrawal operations
- Inter-account transfers
- Detailed transaction history

#### 🛠️ Tech Stack

- **Backend:** Java JDK 11+, Maven
- **Database:** MySQL 5.7+
- **IDE:** VS Code with Java extensions
- **Architecture:** DAO Pattern, Service Layer

#### 🔧 Setup Instructions

##### Prerequisites
```bash
# Required software
- Java JDK 11 or higher
- MySQL Server (5.7+)
- Maven
- VS Code with Java Extension Pack
```

##### Database Setup
```sql
mysql -u root -p < database/banking_system_schema.sql
```

##### Running the Application
1. Open project in VS Code
2. Update database credentials in `DatabaseConnection.java`
3. Run `BankingConsoleApp.java`

#### 📁 Project Architecture

```
src/
├── model/
│   ├── Customer.java
│   ├── BankAccount.java
│   └── Transaction.java
├── dao/
│   ├── CustomerDAO.java
│   ├── BankAccountDAO.java
│   ├── TransactionDAO.java
│   └── DatabaseConnection.java
├── service/
│   └── BankingService.java
└── ui/
    └── BankingConsoleApp.java
```

#### 🧪 Testing
```bash
mvn test
```

---

### 4. 🎮 Java Tic Tac Toe Game

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Swing](https://img.shields.io/badge/Swing-GUI-orange?style=for-the-badge)](https://docs.oracle.com/javase/tutorial/uiswing/)

A classic Tic Tac Toe game implementation showcasing Java Swing GUI development and game logic.

#### 🎮 Key Features

- 🎯 **Interactive Gameplay** - Click-to-play 3×3 grid
- 👥 **Two-Player Mode** - Alternating X and O turns
- 🏆 **Win Detection** - Horizontal, vertical, and diagonal wins
- 🤝 **Draw Detection** - Smart game-end detection
- 🔄 **Game Reset** - Restart functionality
- 🎨 **Clean UI** - Intuitive Swing interface

#### ▶️ How to Run

```bash
# Compile
javac src/*.java -d bin

# Run
java -cp bin TicTacToeMain
```

#### 🧾 Game Rules

1. Players alternate placing X and O on a 3×3 grid
2. First to get 3 symbols in a row wins
3. Game ends in draw if board fills without winner

#### 📁 Project Structure

```
src/
├── TicTacToeMain.java      # Application entry point
├── TicTacToeFrame.java     # Main UI frame
├── GamePanel.java          # Game board rendering
├── GameModel.java          # Game logic and state
└── Player.java             # Player representation
```

#### 🚀 Future Enhancements

- [ ] Web-based UI with Spring Boot
- [ ] User authentication system
- [ ] Interest calculation for savings
- [ ] Credit and loan features
- [ ] Automated payment scheduling

---



## 🚀 Getting Started

### Prerequisites

- Java JDK 11+
- Node.js 14+
- MySQL 5.7+
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Void604/CreTech.git
   cd CreTech
   ```

2. **Choose your project**
   ```bash
   # For Chat Application
   cd chat-app
   npm install
   npm start
   
   # For Banking System
   cd banking-system
   mvn clean install
   
   # For Tic Tac Toe
   cd tic-tac-toe
   javac src/*.java -d bin
   java -cp bin TicTacToeMain
   ```

---

## 🤝 Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

### How to Contribute

1. **Fork the Project**
2. **Create your Feature Branch** (`git checkout -b feature/AmazingFeature`)
3. **Commit your Changes** (`git commit -m 'Add some AmazingFeature'`)
4. **Push to the Branch** (`git push origin feature/AmazingFeature`)
5. **Open a Pull Request**

### Contribution Guidelines

- Follow the existing code style
- Add tests for new features
- Update documentation as needed
- Ensure all tests pass before submitting

---

  ## 👤 Author

* Aryan Kashyap
* aryankashyap7899@gmail.com
* [My Github Profile](https://github.com/Void604)


---

## 🙏 Acknowledgements

- [React](https://reactjs.org/) - Frontend framework
- [Node.js](https://nodejs.org/) - Backend runtime
- [Socket.IO](https://socket.io/) - Real-time communication
- [MySQL](https://www.mysql.com/) - Database management
- [Netlify](https://www.netlify.com/) - Deployment platform
- [Java](https://www.oracle.com/java/) - Programming language
- [Maven](https://maven.apache.org/) - Build automation


<div align="center">

### ⭐ Don't forget to star this repository if you found it helpful!

**Made with ❤️ by [Aryan Kashyap](https://github.com/Void604)**

</div>