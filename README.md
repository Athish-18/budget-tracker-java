# 🏠 Home Budget Tracker

A Java Swing-based GUI application that helps users track their monthly income, expenses, and calculate taxes and remaining budget. The app includes categories for expenses, handles tax calculation as per Indian income tax slabs, and provides a user-friendly interface for managing financial data.

---

## ✨ Features

- Create a new user profile
- Add multiple sources of income
- Add expenses under predefined categories
- Automatically calculates monthly tax based on income
- Calculates and displays remaining monthly budget
- Intuitive and clean GUI built using Java Swing

---

## 🧰 Technologies Used

- **Java SE**
- **Swing GUI Toolkit**
- **OOP principles** (Encapsulation, Interface Implementation)
- **Modular Design** with clear separation between model, view, and logic

---

## 📂 Project Structure

budget-tracker-java/
├── src/
│ └── App.java # Main application with GUI and logic
├── .gitignore # Ignoring .class and build files
├── README.md # Project documentation
└── workspace.code-workspace # VS Code workspace (optional)

## 🧑‍💻 How to Run

### Prerequisites

- Java JDK 8 or above
- VS Code or any Java IDE (like IntelliJ IDEA or Eclipse)

### Steps

1. Clone this repository:
   ```bash
   git clone https://github.com/Athish-18/budget-tracker-java.git
   cd budget-tracker-java
Compile the application:

bash
Copy
Edit
javac src/App.java
Run the application:

bash
Copy
Edit
java -cp src App
Use the GUI to:

Create a user

Add incomes and expenses

Click "Calculate Budget" to view budget after tax

📊 Tax Calculation Logic
Follows simplified Indian tax slabs:

₹0–2.5L: No tax

₹2.5L–5L: 5%

₹5L–10L: 20%

₹10L and above: 30%

The app calculates annual tax and divides it into monthly tax deductions.

📌 Future Enhancements (Suggestions)
Allow custom expense categories

Save/load user profiles to a file

Export monthly budget reports

Add pie charts using JavaFX for visualization

👨‍💻 Author
C Athish Kumar
Student | Java Developer | Passionate about building real-world software projects

📄 License
This project is open-source and free to use for educational and non-commercial purposes.

yaml
Copy
Edit

---

Let me know once it's pasted — then you can:

```bash
git add README.md
git rebase --continue
And finally:

bash
Copy
Edit
git push -u origin main
