package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ExercisesUI extends JPanel implements ActionListener{
    private initUI parentUI;
    private ManageExercise exercisesManager;

    private JComboBox<String> exersices, types;
    private JLabel selectExersice, selectType;
    private JPanel typesPanel, listPanel, detailsPanel;
    private static final long serialVersionUID = 1L; // VERSION NUMBER, needed for serialization

    public ExercisesUI(initUI parentUI, ManageExercise exercisesManager) {
        this.parentUI = parentUI;
        this.exercisesManager = exercisesManager;

        setLayout(new BorderLayout());
        //Creates the drop down menu for the Types

        //Create new panel to format layout
        typesPanel = new JPanel();
        //Create new panel to format layout
        listPanel = new JPanel();
        //Create new panel to format layout
        detailsPanel = new JPanel();
        showTypes();
    }

    /**
     * if the user clicks on a type, it will show the exercises for that type
     * if the user clicks on an exercise, it will show the details for that exercise
     */
    public void actionPerformed(ActionEvent e) {
        System.out.print("ExerciseUI: ");

        //If user selected a type
        if (e.getSource() == types) {
            System.out.println("Type Selected: " + types.getSelectedItem());
            remove(detailsPanel);
            remove(listPanel);
            updateFrame();
            
            String s = (String) types.getSelectedItem();
            showExercises(s);
        }

        //If user selected a exercise from a type
        else if (e.getSource() == exersices) {
            remove(detailsPanel);
            updateFrame();

            System.out.println("Exercise Selected: " + exersices.getSelectedItem());
            String s = (String) exersices.getSelectedItem();
            //Display exercise details
            showExerciseDetails(s);
        }

        else{
            System.out.println("Not found Element: " + e.getSource() + " \n" + e.getActionCommand());
        }
        
    }

    /**
     * refreshes display to update GUI
     */
    public void updateFrame(){
        parentUI.updateDisplay();
    }

    /**
     * displays a drop down menu of the types of exercises
     */
    public void showTypes(){
        typesPanel.removeAll();

        //Create Label for Type of Exercise
        selectType = new JLabel("Please Select a Type of Exersice:  ");
        typesPanel.add(selectType, BorderLayout.NORTH);
        
        //Create Drop box
        types = new JComboBox<String>(exercisesManager.getTypeList().toArray(new String[0]));
        types.addActionListener(this);
        typesPanel.add(types, BorderLayout.NORTH);

        //Add panel to the ExercisesUI Panel
        add(typesPanel, BorderLayout.NORTH);
        updateFrame();

    }

    /**
     * displays a drop down menu of the exercises for a type
     * @param type the type of exercise to display
     */
    public void showExercises(String type){
        listPanel.removeAll();

        //Create Label for Exercise
        selectExersice = new JLabel("Please Select a Exersice:  ");
        listPanel.add(selectExersice, BorderLayout.CENTER);

        //Create Drop box
        exersices = new JComboBox<String>(exercisesManager.getExersicesFromType(type).toArray(new String[0]));
        exersices.addActionListener(this);
        listPanel.add(exersices);

        //Add panel to the ExercisesUI Panel
        add(listPanel, BorderLayout.CENTER);
        updateFrame();
    }

    public void showExerciseDetails(String exercise){
        detailsPanel.removeAll();

        // find exercise from set of exercises
        Exercise e = exercisesManager.getExerciseByName(exercise);

        JLabel img = new JLabel(new ImageIcon(e.getScaledImage()));
        detailsPanel.add(img, BorderLayout.CENTER);

        detailsPanel.add(new JLabel(String.format("Selected Exercise: %s", e.getName())));

        //Add panel to the ExercisesUI Panel
        add(detailsPanel, BorderLayout.SOUTH);
        updateFrame();
    }
}
