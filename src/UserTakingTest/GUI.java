package UserTakingTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Class to control User Taking Test GUI
 * @author Liz Goltz
 * @version
 */

public class GUI
{
    private JPanel rootPanel;

    private String item1;
    private String item2;
    private int winItem;
    private int position = 0;
    private ButtonGroup radioGroup;
    private JRadioButton item1RadioButton;
    private JRadioButton item2RadioButton;
    private JRadioButton tieRadioButton;
    private JButton nextButton;
    private JLabel itemCompareLabel;

    private static String mItem1 = "";
    private static String mItem2 = "";
    private static int mWin = 0;

    private static int mCollectionID = 1;
 //   private static String mTestName = "";
    private static int mUserID = 1;
    private static int mSessionID = 0;

    private static Test mTest;
    private static ArrayList<ItemPair> mTestQuestions;

    public GUI() {
        mTest = new Test(mUserID, mCollectionID);
        mTestQuestions = mTest.makeTestQuestions();

        rootPanel.setPreferredSize(new Dimension(300, 200));
        displayQuestion();
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton button = (JRadioButton) e.getSource();
                System.out.println("Got click on " + button.getText());
            }
        };

        item1RadioButton.addActionListener(listener);
        item2RadioButton.addActionListener(listener);
        tieRadioButton.addActionListener(listener);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                winItem = getSelectedButton(radioGroup);
                System.out.println(winItem);
                mTestQuestions.get(position).setWinItem(winItem);
                nextQuestion();
            }
        });
    }


    private void displayQuestion() {
        ItemPair currentQuestion = mTestQuestions.get(position);
        radioGroup.clearSelection();
        item1RadioButton.setText(currentQuestion.getItem1().getName());
        item2RadioButton.setText(currentQuestion.getItem2().getName());
        tieRadioButton.setText("tie");

    }

    private void nextQuestion() {
        position = position + 1;
        if (position >= mTestQuestions.size()) {
            mTest.saveTestAnswers(mTestQuestions);
            // You're done, exit the GUI.
            Main.finished();
        } else {
            displayQuestion();
        }
    }

    /**
     * @param buttonGroup
     * @return integer win code for winItem
     */
    public int getSelectedButton(ButtonGroup buttonGroup)
    {
        //iterate through radio buttons to see which one is selected
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); )
        {
            AbstractButton button = buttons.nextElement();
            //if statements to set winItem code 0=tie 1=item1RadioButton 2=item2RadioButton
            if (item1RadioButton.isSelected()) {
                winItem = 1;
            } else if (item2RadioButton.isSelected()) {
                winItem = 2;
            } else if (tieRadioButton.isSelected()) {
                winItem = 0;
            }
            //else catch an error here?
        }
        return winItem;
    }

    public String getItem1()
    {
        return item1;
    }

    public void setItem1(String item1)
    {
        this.item1 = item1;
    }

    public String getItem2()
    {
        return item2;
    }

    public void setItem2(String item2)
    {
        this.item2 = item2;
    }

    public int getWinItem()
    {
        return winItem;
    }

    public void setWinItem(int winItem)
    {
        this.winItem = winItem;
    }

    public ArrayList<ItemPair> getTestQuestions() {
        return mTestQuestions;
    }

    public void setTestQuestions(ArrayList<ItemPair> testQuestions) {
        mTestQuestions = testQuestions;
    }

    public JPanel getRootPanel()
    {
        return rootPanel;
    }

}
