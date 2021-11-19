package src;

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.event.*;

public class ManageUser extends JFrame implements ActionListener{
    private ArrayList<User> list;
    private String lastUser;

    private JFrame frame;
    private JButton submitButton;
    private JTextField userName, userAge, userHeight, userWeight;


    public ManageUser() {
        list = new ArrayList<User>();
    }

    public void addUser(User user) {
        list.add(user);
    }

    public void removeUser(User user) {
        list.remove(user);
    }

    public ArrayList<User> getList() {
        return list;
    }

    public User getUser(String name){
        for(User user : list){
            if(user.getName().equals(name)){
                return user;
            }
        }
        return null;
    }  

    /**
     * Serializes the user object to a file in data folder.
     * @return true if successful, false otherwise
     */
    public boolean saveUser() {
        System.out.println("Saving users to file...");
        //serialize user to file
        try{
            //Check/Create if directory exists
            File dir = new File("data");
            dir.mkdirs();
            //Create file
            File userFile = new File("src/data/", "users.ser"); 
            if (userFile.createNewFile()) {
                System.out.println("User File created: " + userFile.getName());
            } else {
                System.out.println("User File already exists. Trying to Save User now");
            }
        } catch (IOException e) {
            System.out.println("An error occurred creating a user file");
            e.printStackTrace();
            return false;
        }

        try{
            //Write user information to file
            FileOutputStream fos = new FileOutputStream("src/data/users.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.list);
            oos.close();
            fos.close();
            System.out.println("Saved users to file");
            return true;
        }catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deserializes the user object to a file in data folder.
     * @return true if successful, false otherwise
     */
    public boolean loadUser() {
        //deserialize user from file
        System.out.println("Loading users from file...");
        try{
            //load user list from file
            FileInputStream fis = new FileInputStream("src/data/users.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            //TODO: cast fix
            this.list = (ArrayList<User>) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("Loaded users from file");
            return true;
        }catch(IOException e){
            System.out.println("Error reading file \"src/data/users.ser\" (IOException)");
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            System.out.println("Error reading file \"src/data/users.ser\" (ClassNotFoundException)");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Serializes the last user name(string) to a file in data folder.
     * @return true if successful, false otherwise
     */
    public boolean saveLastUser(String name) {
        System.out.println("Saving last user to file...");
        //serialize user to file
        try{
            //
            FileOutputStream fos = new FileOutputStream("src/data/lastUser.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(name);
            oos.close();
            fos.close();
            System.out.println("Saved last user to file");
            return true;
        }catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deserializes the last user name(string) to a file in data folder.
     * @return User if successful, null otherwise
     */
    public String loadLastUser() {
        System.out.println("Loading last user from file...");
        //deserialize user from file
        try{
            FileInputStream fis = new FileInputStream("src/data/lastUser.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            lastUser = (String) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("Loaded last users from file");
            return lastUser;
        }catch(IOException e){
            //e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Displays UI for editing user information.
     */
    public void createUserUI(){

        frame = new JFrame("Create User");
        frame.setTitle("Create User");
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c = setGrid(c, 2, 0);
        frame.add(new JLabel("Welcome to our Workout Tracker Program"), c);
        c = setGrid(c, 2, 1);
        frame.add(new JLabel("Please enter your information below"), c);
        
        //Add padding
        setGrid(c, 1, 2, 40);
        frame.add(new JLabel(""), c);

        //User Name        
        c = setGrid(c, 1, 5);
        frame.add(new JLabel("Name: "), c);
        userName = new JTextField (20);
        userName.addActionListener(this);
        c = setGrid(c, 2, 5);
        frame.add(userName, c);

        //Age
        c = setGrid(c, 1, 6);
        frame.add(new JLabel("Age: "), c);
        userAge = new JTextField (5);
        userAge.addActionListener(this);
        c = setGrid(c, 2, 6);
        frame.add(userAge, c);

        //Height
        c = setGrid(c, 1, 7);
        frame.add(new JLabel("Height: "), c);
        userHeight = new JTextField (5);
        userHeight.addActionListener(this);
        c = setGrid(c, 2, 7);
        frame.add(userHeight, c);
        c = setGrid(c, 3, 7);
        frame.add(new JLabel("inches"), c);

        //Weight
        c = setGrid(c, 1, 8);
        frame.add(new JLabel("Weight: "), c);
        userWeight = new JTextField (5);
        userWeight.addActionListener(this);
        c = setGrid(c, 2, 8);
        frame.add(userWeight, c);
        c = setGrid(c, 3, 8);
        frame.add(new JLabel("kg"), c);

        //Add padding
        setGrid(c, 1, 10, 20);
        frame.add(new JLabel(""), c);

        //Submit Button
        submitButton = new JButton ("Submit");
        submitButton.addActionListener(this);
        c = setGrid(c, 2, 11);
        frame.add(submitButton, c);

        frame.setVisible(true);
    }

    private GridBagConstraints setGrid(GridBagConstraints c, int gridX, int gridY, int ipady){
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = ipady;
        c.gridx = gridX;
        c.gridy = gridY;
        return c;
    }

    private GridBagConstraints setGrid(GridBagConstraints c, int gridX, int gridY){
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.gridx = gridX;
        c.gridy = gridY;
        return c;
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == submitButton){
            String name = userName.getText();
            int age = Integer.parseInt(userAge.getText());
            double height = Double.parseDouble(userHeight.getText());
            double weight = Double.parseDouble(userWeight.getText());
            User user = new User(name, age, height, weight);
            addUser(user);
            saveUser();
            saveLastUser(name);
            frame.dispose();
        }
    }

    public ArrayList<String> getNames(){
        ArrayList<String> names = new ArrayList<String>();
        for(User user : this.getList()){
            names.add((user.getName()));
        }
        return names;
    }

}
