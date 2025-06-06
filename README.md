# CreTech
A collection of diverse projects. This repository showcases various development skills and approaches to building different types of applications.


# PROJECT 1:- 
## Chat application
## Overview

This is a real-time chat application website built with the goal of providing seamless communication. It offers users the ability to connect and chat in various ways, including joining public chatrooms and engaging in direct one-on-one conversations. The application prioritizes user security and provides a secure environment for online interaction through robust authentication features.

## 🔗 **Live Demo:** https://commonchat.netlify.app/

## ✨ Key Features

* **User Authentication:** Secure login and signup functionality to protect user accounts.
* **Chatroom Creation:** Users can create their own public chatrooms for group discussions on specific topics.
* **Direct Messaging:** Engage in private, one-on-one conversations with other registered users.
* **Real-time Communication:** Messages are delivered instantly, providing a fluid and responsive chat experience.

## 🛠️ Technologies Used

    * **Frontend:** React, JavaScript, HTML, CSS,java 
    * **Backend:** Node.js, Express.js
    * **Real-time Communication:** Socket.IO
    * **Authentication:** JSON Web Tokens (JWT)
    * **Deployment:** Netlify

## 🚀 Future Enhancements

This project is continuously being developed. Some planned future features include:

* **User Profiles:** Allow users to customize their profiles with avatars and information.
* **Media Sharing:** Enable users to share images, videos, and other files within chats.
* **Search Functionality:** Implement search within chatrooms and direct messages.
* **Admin Panel:** Provide administrative tools for managing users and chatrooms.
* **Notifications:** Implement desktop and in-app notifications for new messages.

## Contributing

Contributions to this project are welcome! If you have any ideas, bug reports, or feature requests, please feel free to open an issue or submit a pull request. Please follow standard Git contribution guidelines.

  # PROJECT 2:-
## To-Do list website:
A simple web application to manage your tasks effectively. You can add tasks, set deadlines, and mark them as complete to stay organized.

## 🔗 Hosted here:- https://todo-taskmgmt.netlify.app/
## ✨ Key Features

* **Add Tasks:** Easily add new tasks to your to-do list.
* **Set Deadlines:** Assign due dates to your tasks to keep track of important deadlines.
* **Mark as Complete:** Check off tasks as you finish them, providing a clear overview of your progress.
* **View Active Tasks:** See a list of all your pending tasks.
* **View Completed Tasks:** Review your accomplishments by viewing the list of completed tasks.
* **Responsive Design:** The application adapts to different screen sizes, making it usable on desktops, tablets, and mobile devices.
* **Local Storage:** Your tasks are saved directly in your browser's local storage, so your data persists even after you close the browser.

## 🛠️ Technologies Used

* HTML
* CSS
* JavaScript
* React
* java

## 🧪 Setup

To run this application locally, simply open the `index.html` file in your web browser. No additional setup or server is required.

## 📌 How to Use

1.  **Adding a Task:** Enter your task description in the input field and click the "Add Task" button. You can optionally set a deadline by selecting a date from the calendar.
2.  **Viewing Tasks:** Your active tasks will be displayed in a list. Tasks with deadlines will show the due date.
3.  **Marking as Complete:** Click the checkbox next to a task to mark it as complete. The task will then move to the "Completed Tasks" section.
4.  **Viewing Completed Tasks:** Click on the "Completed Tasks" tab to see a list of all the tasks you have finished.
5.  **Deleting Tasks:** You can delete tasks from both the "Active Tasks" and "Completed Tasks" sections using the delete button next to each task.

## Contributing

Contributions are welcome! If you have any ideas for improvements or find any bugs, please feel free to open an issue or submit a pull request.

## Acknowledgements

* This project was inspired by the need for a simple and effective personal task management tool.

## 🚀 Future Enhancements

* **Task Prioritization:** Allow users to set priority levels for their tasks.
* **Reminders:** Implement notifications for upcoming deadlines.
* **Task Editing:** Enable users to edit existing tasks.
* **Data Synchronization:** Explore options for syncing tasks across multiple devices.

   # PROJECT 3:-
## Java Banking System

A simple banking system built with Java and MySQL that demonstrates core banking operations including account management, transactions, and customer management.

## 🔗 Hosted here:- https://bankingsystemwebsite-securebank.netlify.app/

## 🔧 Setup Instructions

### Prerequisites

1. Java JDK 11 or higher
2. MySQL Server (5.7+)
3. Maven
4. VS Code with the following extensions:
   - Extension Pack for Java
   - Database Client extension

### Database Setup

1. Open MySQL and run the script located in `database/banking_system_schema.sql` to create the database and tables.

   ```bash
   mysql -u root -p < database/banking_system_schema.sql
   ```

2. Update the database connection settings in `src/main/java/com/banking/dao/DatabaseConnection.java` with your MySQL credentials.

### Project Setup in VS Code

1. Open VS Code
2. Click on "File" > "Open Folder" and select the project folder
3. VS Code should automatically recognize the Maven project
4. Right-click on `pom.xml` and select "Maven: Update Project" to download all dependencies

### Running the Application

1. Right-click on the `BankingConsoleApp.java` file in VS Code
2. Select "Run Java" or use the play button that appears above the `main` method
3. The console application will start in the integrated terminal

## ✨ Key Features

### 1. Customer Management
- Register new customers
- View customer details
- Update customer information

### 2. Account Management
- Create different types of accounts (Checking, Savings, Credit)
- View account details and balances
- Close accounts

### 3. Transaction Processing
- Deposit funds
- Withdraw funds
- Transfer between accounts
- View transaction history

## Class Overview

### Model Classes

1. **Customer.java**: Represents a bank customer with personal information.
2. **BankAccount.java**: Represents a bank account with balance and operations.
3. **Transaction.java**: Represents a financial transaction with type, amount, and timestamp.

### Data Access Objects (DAO)

1. **CustomerDAO.java**: Handles database operations for customer data.
2. **BankAccountDAO.java**: Handles database operations for account data.
3. **TransactionDAO.java**: Handles database operations for transaction data.
4. **DatabaseConnection.java**: Manages database connections.

### Service Layer

1. **BankingService.java**: Provides high-level banking operations by coordinating between model and DAO classes.

### User Interface

1. **BankingConsoleApp.java**: Console-based user interface for the banking system.

## 🧪 Testing

To run the tests, use the following Maven command:

```bash
mvn test
```

## Future Enhancements

 # PROJECT 4:-
## Java Tic Tac Toe Game

A simple Tic Tac Toe game implemented in Java using Swing for the GUI.

## 🎮 Key Features

- Interactive 3x3 game board
- Two-player gameplay (X and O)
- Win detection for horizontal, vertical, and diagonal matches
- Draw detection when the board is full
- Game state tracking and turn management
- Option to restart the game

## ▶️ How to Run

1. Ensure you have Java Development Kit (JDK) installed
2. Compile the Java files:
   ```
   javac src/*.java -d bin
   ```
3. Run the application:
   ```
   java -cp bin TicTacToeMain
   ```

## 🧾 Game Rules

1. The game is played on a 3x3 grid
2. Players take turns placing their symbol (X or O) on an empty cell
3. The first player to get 3 of their symbols in a row (horizontally, vertically, or diagonally) wins
4. If all cells are filled and no player has won, the game ends in a draw

## 📁 Project Structure

- `TicTacToeMain.java`: Entry point of the application
- `TicTacToeFrame.java`: Main frame that sets up the UI components
- `GamePanel.java`: Panel that renders the game board and handles mouse input
- `GameModel.java`: Model class that represents the game state and logic
- `Player.java`: Class representing a player (X or O)
  
- Web-based user interface using Spring Boot
- Authentication and authorization
- Interest calculation for savings accounts
- Credit card and loan features
- Scheduled payments and transfers

  ## 👤 Author

* Aryan Kashyap
* aryankashyap7899@gmail.com
* [My Github Profile](https://github.com/Void604)
