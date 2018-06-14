package UserTakingTest;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Class to control User Taking Test GUI
 *
 * @author Liz Goltz, Bobby Puckett
 */

public class GUI {
    private JPanel rootPanel;
    private PicturePanel item1Image;
    private PicturePanel item2Image;
    private int winItem;
    private int position = 0;
    private ButtonGroup radioGroup;
    private JRadioButton item1RadioButton;
    private JRadioButton item2RadioButton;
    private JRadioButton tieRadioButton;
    private JButton nextButton;
    private JButton previousButton;
    private JLabel itemCompareLabel;
    private JProgressBar positionProgressBar;
    private JLabel positionLabel;
    private static String mItem1 = "";
    private static String mItem2 = "";
    private static int mWin = 0;

    private static int mCollectionID = 3;
    private static int mUserID = 1;
    private static int mSessionID = 0;
    private static Test mTest;
    private static ArrayList<ItemPair> mTestQuestions;

    public GUI() {
        mTest = new Test(mUserID, mCollectionID, 3);
        mTestQuestions = mTest.makeRandomTestQuestions();
        rootPanel.setPreferredSize(new Dimension(500, 300));

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton button = (JRadioButton) e.getSource();
                System.out.println("Got click on " + button.getText());
                winItem = getSelectedButton(radioGroup);
                System.out.println(winItem);
                mTestQuestions.get(position).setWinItem(winItem);
                nextQuestion();
            }
        };

        radioGroup = new ButtonGroup();
        radioGroup.add(item1RadioButton);
        radioGroup.add(tieRadioButton);
        radioGroup.add(item2RadioButton);

        displayQuestion();

        item1RadioButton.addActionListener(listener);
        item2RadioButton.addActionListener(listener);
        tieRadioButton.addActionListener(listener);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { nextQuestion(); }
        });

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                winItem = getSelectedButton(radioGroup);
                System.out.println(winItem);
                mTestQuestions.get(position).setWinItem(winItem);
                previousQuestion();
            }
        });
    }

    private void displayQuestion() {
        ItemPair currentQuestion = mTestQuestions.get(position);
        item1Image.setImage(currentQuestion.getItem1().getImage());
        item2Image.setImage(currentQuestion.getItem2().getImage());
        //      radioGroup.clearSelection();
        item1RadioButton.setText(currentQuestion.getItem1().getName());
        item2RadioButton.setText(currentQuestion.getItem2().getName());
        tieRadioButton.setText("tie");

        if (currentQuestion.getWinItem() != 3) {
            if (currentQuestion.getWinItem() == 1) {
                item1RadioButton.setSelected(true);
            }
            else if (currentQuestion.getWinItem() == 2) {
                item2RadioButton.setSelected(true);
            }
            else {
                tieRadioButton.setSelected(true);
            }
        }
        else {
            radioGroup.clearSelection();
        }
    }

    private void nextQuestion() {
        position = position + 1;
        if (position >= mTestQuestions.size()) {
            mTest.saveTestAnswers(mTestQuestions);
            // You're done, exit the GUI.
            Main.finished();
        } else {
            displayQuestion();
            updateProgress();
        }
    }

    /**
     * (Bobby Puckett)
     * displays the previous set of questions
     */
    private void previousQuestion() {
        position = position - 1;
        if (position < 0) {
            position = 0;
            updateProgress();
        } else {
            displayQuestion();
            updateProgress();
        }
    }

    /**
     * (Bobby Puckett)
     * updates the progress bar and progress label
     */
    private void updateProgress() {
        float pos = position;
        float questions = mTestQuestions.size() - 1;
        int progressPercentage = (int)(100 * (pos / (questions)));
        positionLabel.setText("Progress: " + String.valueOf(progressPercentage) + "%");
        positionProgressBar.setValue(progressPercentage);
    }

    /**
     * @param buttonGroup
     * @return integer win code for winItem
     */
    public int getSelectedButton(ButtonGroup buttonGroup) {
        //iterate through radio buttons to see which one is selected
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            //if statements to set winItem code 0=tie 1=item1RadioButton 2=item2RadioButton
            if (item1RadioButton.isSelected()) {
                winItem = 1;
            } else if (item2RadioButton.isSelected()) {
                winItem = 2;
            } else if (tieRadioButton.isSelected()) {
                winItem = 0;
            }
        }
        return winItem;
    }

    public PicturePanel getItem1Image()
    {
        return item1Image;
    }

    public void setItem1(PicturePanel item1)
    {
        this.item1Image = item1;
    }

    public PicturePanel getItem2()
    {
        return item2Image;
    }

    public void setItem2(PicturePanel item2)
    {
        this.item2Image = item2;
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
