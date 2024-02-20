import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

class Task {
    private String title;
    private boolean completed;

    public Task(String title) {
        this.title = title;
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return title;
    }
}

public class TaskManagementGUI extends JFrame {
    private List<Task> tasks;
    private DefaultListModel<Task> listModel;
    private JList<Task> taskList;
    private JTextField taskField;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;

    public TaskManagementGUI() {
        tasks = new ArrayList<>();
        listModel = new DefaultListModel<>();

        setTitle("Task Management Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initComponents();

        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Task Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        taskField = new JTextField();
        taskField.setFont(new Font("Arial", Font.PLAIN, 16));

        addButton = new JButton("Add Task");
        addButton.setFont(new Font("Arial", Font.BOLD, 16));

        editButton = new JButton("Edit Task");
        editButton.setFont(new Font("Arial", Font.BOLD, 16));
        editButton.setEnabled(false);

        deleteButton = new JButton("Delete Task");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 16));
        deleteButton.setEnabled(false);

        taskList = new JList<>(listModel);
        taskList.setFont(new Font("Arial", Font.PLAIN, 16));
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int selectedIndex = taskList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        enableEditAndDeleteButtons();
                    }
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editTask();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTask();
            }
        });

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(taskField, BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        listPanel.add(new JScrollPane(taskList), BorderLayout.CENTER);
        listPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);
    }

    private void addTask() {
        String title = taskField.getText().trim();
        if (!title.isEmpty()) {
            Task task = new Task(title);
            tasks.add(task);
            listModel.addElement(task);
            taskField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a task.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            Task selectedTask = taskList.getSelectedValue();
            String newTitle = JOptionPane.showInputDialog(this, "Enter a new task title:", selectedTask.getTitle());
            if (newTitle != null && !newTitle.isEmpty()) {
                selectedTask.setTitle(newTitle);
                listModel.setElementAt(selectedTask, selectedIndex);
                taskList.clearSelection();
                disableEditAndDeleteButtons();
            }
        }
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the selected task?", "Delete Task", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                Task selectedTask = taskList.getSelectedValue();
                tasks.remove(selectedTask);
                listModel.removeElement(selectedTask);
                taskList.clearSelection();
                disableEditAndDeleteButtons();
            }
        }
    }

    private void enableEditAndDeleteButtons() {
        editButton.setEnabled(true);
        deleteButton.setEnabled(true);
    }

    private void disableEditAndDeleteButtons() {
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new TaskManagementGUI());
    }
}