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

public class ImageCompare {
    private JPanel rootPanel;
    private PicturePanel item1;
    private PicturePanel item2;
    private int winItem;
    private int position = 0;
    private ButtonGroup radioGroup;
    private JRadioButton item1RadioButton;
    private JRadioButton item2RadioButton;
    private JRadioButton tieRadioButton;
    private PicturePanel item1Image;
    private PicturePanel tieImage;
    private PicturePanel item2Image;
    private JLabel item1Label;
    private JLabel tieLabel;
    private JLabel Item2Label;
    private JButton nextButton;
    private JButton backButton;

    private static String mItem1 = "";
    private static String mItem2 = "";
    private static int mWin = 0;

    private static int mCollectionID = 1;
    //   private static String mTestName = "";
    private static int mUserID = 1;
    private static int mSessionID = 0;

    private static Test mTest;
    private static ArrayList<ItemPair> mTestQuestions;




    public ImageCompare() {
        mTest = new Test(mUserID, mCollectionID);
        mTestQuestions = mTest.makeRandomTestQuestions();
        rootPanel.setPreferredSize(new Dimension(300, 200));
        displayQuestion();
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

        item1RadioButton.addActionListener(listener);
        item2RadioButton.addActionListener(listener);
        tieRadioButton.addActionListener(listener);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextQuestion();
            }
        });
    }
    /* BufferedImage buttonImage1 = ImageIO.read(new File("buttonIconPath"));
     buttonImage1 = new JButton(new ImageIcon(buttonImage1));

             ImageIcon icon = ...;

     JLabel button = new PicturePanel();

     button.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
      ... handle the click ...
         }
     });

     public static void showImage(String itemName) {
         mFrame.getContentPane().removeAll();
         mFrame.getContentPane().add(new EditImageForm(itemName,mFrame).getRootPanel());
         mFrame.pack();
         mFrame.setLocationRelativeTo(null);
         mFrame.setVisible(true);
         }
 */
    private void displayQuestion() {
        ItemPair currentQuestion = mTestQuestions.get(position);
        item1Image.setImage(null);
        tieImage.setImage(null);
//        item2Image.setImage();
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

    public PicturePanel getItem1()
    {
        return item1;
    }

    public void setItem1(PicturePanel item1)
    {
        this.item1 = item1;
    }

    public PicturePanel getItem2()
    {
        return item2;
    }

    public void setItem2(PicturePanel item2)
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
