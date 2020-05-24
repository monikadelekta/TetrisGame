import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Monika Delekta
 */

public class CE203_2016_Ex2 extends JFrame {
    //create initial variables, textBox will be the user input box, arrList will store the words, the buttons
    // will allow user interaction and mainOut will show the user some text while interacting
    JTextField textBox;
    ArrayList<String> arrList = new ArrayList<String>();
    JButton addToList, dispAllSpec, searchList, remFirstOcc, removeAllOcc, clearList;
    JLabel mainOut;

    public static void main(String[] args) {
        //creates frame, sets the desired size, makes it visible and allows it to be closed
        // when the user selects the red cross
        CE203_2016_Ex2 screen = new CE203_2016_Ex2();
        screen.setTitle("Tetris");
        screen.setSize(950, 400);
        screen.setVisible(true);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public CE203_2016_Ex2() {
        //create all the panels and add the text output panel to the centre, buttons
        // to the top and text box to the bottom
        JPanel outputT = new JPanel();
        JPanel buttons = new JPanel();
        JPanel inputT = new JPanel();
        add(outputT, BorderLayout.CENTER);
        add(buttons, BorderLayout.NORTH);
        add(inputT, BorderLayout.SOUTH);

        //set text box size to 30 so user can see what they have typed, add text box to south panel
        //create JLabel and add it to the centre panel then add the text for the user to view
        textBox = new JTextField("", 30);
        inputT.add(textBox);
        mainOut = new JLabel("");
        outputT.add(mainOut);
        mainOut.setText("Tetris");

        //create all buttons then add an actionListener to them, the actionlisteners give this and a string
        //the string is used for the cases in the switch statement to refer to a specific button
        addToList = new JButton("Add Word To List");
        addToList.addActionListener(new bHandler(this, "Add"));
        dispAllSpec = new JButton("Display List For Specific Letter");
        dispAllSpec.addActionListener(new bHandler(this, "DispAll"));
        searchList = new JButton("Search List");
        searchList.addActionListener(new bHandler(this, "Search"));
        remFirstOcc = new JButton("Remove First Occurrence");
        remFirstOcc.addActionListener(new bHandler(this, "RemFirstOcc"));
        removeAllOcc = new JButton("Remove All Occurrences");
        removeAllOcc.addActionListener(new bHandler(this, "RemAllOcc"));
        clearList = new JButton("Clear List");
        clearList.addActionListener(new bHandler(this, "Clear"));

        //add buttons to the north panel
        buttons.add(addToList);
        buttons.add(dispAllSpec);
        buttons.add(searchList);
        buttons.add(remFirstOcc);
        buttons.add(removeAllOcc);
        buttons.add(clearList);
    }

    class bHandler implements ActionListener {
        //refer to main frame with theApp
        //buttonOp so buttons can be called and used
        CE203_2016_Ex2 theApp;
        String buttonOp;

        bHandler(CE203_2016_Ex2 app, String buttons) {
            theApp = app;
            buttonOp = buttons;

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                //get text from textBox and trim any whitespace, create a regex string variable to match it to, 'exp'
                //mat variable matches exp to the wordInput variable, patt refers to the pattern class
                String wordInput = textBox.getText().trim();
                String exp = "^([a-zA-Z]+)(-[a-zA-Z]+)*$"; //
                Pattern patt = Pattern.compile(exp);
                Matcher mat = patt.matcher(wordInput);

                switch (buttonOp) {
                    case "Add":
                        //check if word entered matches and if it does add it to the arrayList, once added
                        //mainOut will give a message telling the user it was added, the textbox is then cleared
                        //to add a new word
                        if (mat.matches()) {
                            arrList.add(wordInput);
                            mainOut.setText("The following word was added to the list:   " + wordInput);
                            textBox.setText("");
                            break;

                        } else { //if text doesnt match the regex the user will be shown an error message
                            mainOut.setText("The input was invalid. To add a word please enter only a word and no numbers.");
                            textBox.setText("");
                        }

                    case "DispAll":
                        //create new array to add matching words to, create new regex variable letExp which
                        // will only allow a single letter
                        ArrayList<String> fakeArr = new ArrayList<String>();
                        String letExp = "^[a-zA-Z]$";

                        if (wordInput.matches(letExp)) {
                            //if the letter input is accepted by regex the for loop iterates through the arrList
                            //with all those values it will then follow the if statement where the letter is changed to lowercase
                            //then words in arrList are changed to lowercase too. Matching words afre added to the new array
                            for (int i = 0; i < arrList.size(); i++) {
                                if (arrList.get(i).toLowerCase().startsWith(wordInput.toLowerCase())) {
                                    fakeArr.add(arrList.get(i));
                                }
                            }
                            //no matches gives an error message
                            if (fakeArr.size() == 0) {
                                mainOut.setText("There were no matches for this word, please try another.");
                                textBox.setText("");

                            } else {
                                String matches = "";
                                //matches will then be output into the text field by iterating through the new array
                                for (int b = 0; b < fakeArr.size(); b++) {
                                    matches += fakeArr.get(b) + " ";
                                }
                                mainOut.setText("These match:    " + matches);
                                textBox.setText("");
                            }
                            break;

                        } else { //if text doesnt match the regex the user will be shown an error message
                            mainOut.setText("The input was invalid. To display all words starting with a specific letter, please enter a single letter only.");
                            textBox.setText("");
                        }
                        break;

                    case "RemFirstOcc":
                        if (mat.matches()) {
                            //if input matches regex the arrList will be checked if it contains the input word
                            //if it does it will remove its first occurrence from the array
                            if (arrList.contains(wordInput)) {
                                arrList.remove(wordInput);
                                mainOut.setText("The first occurrence of  " + wordInput + "  was removed.");
                                textBox.setText("");
                            } else {
                                //error message if word was not found
                                mainOut.setText("The word you wish to remove was not found in the list. Please try another");
                                textBox.setText("");

                            }
                        } else { //if text doesnt match the regex the user will be shown an error message
                            mainOut.setText("The input was invalid. To remove the first occurrence of a specific word please enter a single word and no numbers.");
                            textBox.setText("");
                        }
                        break;

                    case "RemAllOcc":
                        if (mat.matches()) {
                            //if word matches regex arrList is checked to contain the word input, a for loop then
                            //iterates through the arrList which then passes to an if statement to get all words that equal the input word
                            //these are then removed until the word no longer exists in the array  with the remove function
                            if (arrList.contains(wordInput)) {

                                for (int i = 0; i < arrList.size(); i++) {

                                    if (arrList.get(i).equals(wordInput)) {
                                        arrList.remove(i--);
                                        mainOut.setText("All occurrences of " + wordInput + " were removed from the list");
                                        textBox.setText("");
                                    }
                                }
                            } else {
                                //error if word isn't found
                                mainOut.setText("The word you wish to remove was not found in the list. Please try another.");
                                textBox.setText("");
                            }
                        } else { //if text doesnt match the regex the user will be shown an error message
                            mainOut.setText("The input was invalid. To remove all occurrences of a word please enter a word and no numbers.");
                            textBox.setText("");
                        }
                        break;

                    case "Clear":
                        //if the array contains 1 or more values it will be cleared
                        if (arrList.size() > 0) {
                            arrList.clear();
                            mainOut.setText("The list was successfully cleared!");
                            textBox.setText("");

                        } else {
                            //if the list is empty an error message appears
                            mainOut.setText("The list is already empty!");
                            textBox.setText("");
                        }
                        break;

                    case "Search":
                        if (mat.matches()) {
                            //if input matches regex the arrList is checked to contain the word, for loop iterates through the array
                            //this then passes to an if statement to find all words that equal the input word if found the counter variable increases by
                            //one each time
                            if (arrList.contains(wordInput)) {
                                int counter = 0;
                                for (int i = 0; i < arrList.size(); i++) {
                                    if (arrList.get(i).equals(wordInput)) {
                                        counter++;
                                    }
                                    mainOut.setText("The number of occurrences of  " + wordInput + "  found, was :  " + counter);
                                    textBox.setText("");
                                }
                            } else {
                                //error if word isn't found
                                mainOut.setText("The word you are searching for could not be found in the list, please try another!");
                                textBox.setText("");
                            }

                        } else {
                            //if text box is empty the arrSize variable will get the current size of the array and this is then used to tell the user the current size
                            if (textBox.getText().equals("") ) {
                                int arrSize = arrList.size();
                                mainOut.setText("There was nothing entered into the textbox below, but the current size of the list is  " + arrSize);

                            } else { //if text doesnt match the regex the user will be shown an error message
                                mainOut.setText("The input was invalid. To find a word or letter please enter only the specific word/letter and no numbers.");
                                textBox.setText("");
                            }
                        }
                        break;
                }

            } catch (Exception unknown) {
                mainOut.setText("An error occurred. Please try again!");

            }

        }
    }

}
