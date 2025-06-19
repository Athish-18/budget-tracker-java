import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

interface Trackable {
    String getDetails();
}

class Income implements Trackable {
    private String source;
    private double amount;

    public Income(String source, double amount) {
        this.source = source;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String getDetails() {
        return source + " - ₹" + amount;
    }
}

class ExpenseCategory implements Trackable {
    private String name;

    public ExpenseCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDetails() {
        return name;
    }
}

class TaxCalculator {
    public static double calculateMonthlyTax(double monthlyIncome) {
        double annualIncome = monthlyIncome * 12;
        double tax = 0;

        if (annualIncome <= 250000) {
            tax = 0;
        } else if (annualIncome <= 500000) {
            tax = (annualIncome - 250000) * 0.05;
        } else if (annualIncome <= 1000000) {
            tax = (250000 * 0.05) + (annualIncome - 500000) * 0.20;
        } else {
            tax = (250000 * 0.05) + (500000 * 0.20) + (annualIncome - 1000000) * 0.30;
        }

        return tax / 12; // Return monthly tax
    }
}

class Expense implements Trackable {
    private String description;
    private double amount;
    private ExpenseCategory category;
    private User user;

    public Expense(User user, String description, double amount, ExpenseCategory category) {
        this.user = user;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    @Override
    public String getDetails() {
        return description + " - ₹" + amount + " (" + category.getName() + ")";
    }
}

class User {
    private String name;
    private ArrayList<Income> incomes;
    private ArrayList<Expense> expenses;
    private ArrayList<ExpenseCategory> categories;

    public User(String name) {
        this.name = name;
        this.incomes = new ArrayList<>();
        this.expenses = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    public void addIncome(Income income) {
        incomes.add(income);
    }

    public double getTotalIncome() {
        double total = 0;
        for (Income income : incomes) {
            total += income.getAmount();
        }
        return total;
    }

    public void addExpense(String description, double amount, ExpenseCategory category) {
        Expense expense = new Expense(this, description, amount, category);
        expenses.add(expense);
    }

    public void addCategory(ExpenseCategory category) {
        categories.add(category);
    }

    public double getTotalExpenses() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    public double getRemainingBudget() {
        return getTotalIncome() - getTotalExpenses() - TaxCalculator.calculateMonthlyTax(getTotalIncome());
    }

    public String getName() {
        return name;
    }

    public ArrayList<ExpenseCategory> getCategories() {
        return categories;
    }
}

class BudgetTrackerUI extends JFrame {
    private JTextField userNameField;
    private JTextField incomeSourceField;
    private JTextField incomeAmountField;
    private JTextField expenseDescField;
    private JTextField expenseAmountField;
    private JComboBox<String> expenseTypeCombo;
    private JTextArea displayArea;
    private User user;

    public BudgetTrackerUI() {
        setupUI();
    }

    private void setupUI() {
        setTitle("Home Budget Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(500, 600);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // User information panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("User Name:"), gbc);

        userNameField = new JTextField();
        gbc.gridx = 1;
        add(userNameField, gbc);

        JButton enterButton = new JButton("Create User Profile");
        enterButton.addActionListener(e -> createUserProfile());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(enterButton, gbc);

        // Income input panel
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Income Source:"), gbc);

        incomeSourceField = new JTextField();
        gbc.gridx = 1;
        add(incomeSourceField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(new JLabel("Income Amount:"), gbc);

        incomeAmountField = new JTextField();
        gbc.gridx = 1;
        add(incomeAmountField, gbc);

        JButton addIncomeButton = new JButton("Add Income");
        addIncomeButton.addActionListener(e -> addIncome());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(addIncomeButton, gbc);

        // Expense input panel
        gbc.gridy = 5;
        gbc.gridx = 0;
        add(new JLabel("Expense Description:"), gbc);

        expenseDescField = new JTextField();
        gbc.gridx = 1;
        add(expenseDescField, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0;
        add(new JLabel("Expense Amount:"), gbc);

        expenseAmountField = new JTextField();
        gbc.gridx = 1;
        add(expenseAmountField, gbc);

        gbc.gridy = 7;
        gbc.gridx = 0;
        add(new JLabel("Expense Type:"), gbc);

        expenseTypeCombo = new JComboBox<>();
        gbc.gridx = 1;
        add(expenseTypeCombo, gbc);

        JButton addExpenseButton = new JButton("Add Expense");
        addExpenseButton.addActionListener(e -> addExpense());
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        add(addExpenseButton, gbc);

        // Display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setPreferredSize(new Dimension(450, 200));
        gbc.gridy = 9;
        add(scrollPane, gbc);

        JButton calculateBudgetButton = new JButton("Calculate Budget");
        calculateBudgetButton.addActionListener(e -> calculateBudget());
        gbc.gridy = 10;
        add(calculateBudgetButton, gbc);

        setLocationRelativeTo(null);
    }

    private void createUserProfile() {
        String name = userNameField.getText();
        user = new User(name);
        displayArea.setText("User profile created for: " + name + "\n");

        // Add default categories
        user.addCategory(new ExpenseCategory("Food"));
        user.addCategory(new ExpenseCategory("Utilities"));
        user.addCategory(new ExpenseCategory("Entertainment"));
        user.addCategory(new ExpenseCategory("Other"));

        // Populate combo box with categories
        expenseTypeCombo.removeAllItems();
        for (ExpenseCategory category : user.getCategories()) {
            expenseTypeCombo.addItem(category.getName());
        }

        userNameField.setText("");
    }

    private void addIncome() {
        String source = incomeSourceField.getText();
        String amountText = incomeAmountField.getText();

        try {
            double amount = Double.parseDouble(amountText);
            Income income = new Income(source, amount);
            user.addIncome(income);
            displayArea.append("Added income: " + income.getDetails() + "\n");
            incomeSourceField.setText("");
            incomeAmountField.setText("");
        } catch (NumberFormatException e) {
            displayArea.append("Error: Please enter a valid income amount.\n");
        }
    }

    private void addExpense() {
        if (user == null) {
            displayArea.append("Error: Please create a user profile first.\n");
            return;
        }

        String description = expenseDescField.getText();
        String amountText = expenseAmountField.getText();
        String categoryName = (String) expenseTypeCombo.getSelectedItem();

        try {
            double amount = Double.parseDouble(amountText);
            ExpenseCategory expenseCategory = null;
            for (ExpenseCategory category : user.getCategories()) {
                if (category.getName().equals(categoryName)) {
                    expenseCategory = category;
                    break;
                }
            }

            if (expenseCategory != null) {
                Expense expense = new Expense(user, description, amount, expenseCategory);
                user.addExpense(description, amount, expenseCategory);
                displayArea.append("Added expense: " + expense.getDetails() + "\n");
                expenseDescField.setText("");
                expenseAmountField.setText("");
            } else {
                displayArea.append("Error: Expense category not found.\n");
            }
        } catch (NumberFormatException e) {
            displayArea.append("Error: Please enter a valid expense amount.\n");
        }
    }

    private void calculateBudget() {
        if (user == null) {
            displayArea.append("Error: Please create a user profile first.\n");
            return;
        }

        double remainingBudget = user.getRemainingBudget();
        displayArea.append("Remaining budget after tax: ₹" + remainingBudget + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BudgetTrackerUI ui = new BudgetTrackerUI();
            ui.setVisible(true);
        });
    }
}
