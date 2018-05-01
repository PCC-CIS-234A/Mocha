import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

/**
 * Class to control Story3 GUI
 * @author Liz Goltz
 * @version
 */

public class GUI
{
    private JPanel rootPanel;

    private String item1;
    private String item2;
    private int winItem;
    private ButtonGroup radioGroup;
    private JRadioButton item1RadioButton;
    private JRadioButton item2RadioButton;
    private JRadioButton tieRadioButton;
    private JButton nextButton;
    private JLabel itemCompareLabel;

    public GUI(String item1, String item2)
    {
        this.item1 = item1;
        this.item2 = item2;
        winItem = 0;
        item1RadioButton.setText(item1);
        item2RadioButton.setText(item2);
        tieRadioButton.setText("tie");

        rootPanel.setPreferredSize(new Dimension(300, 200));

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {
                JRadioButton button = (JRadioButton) e.getSource();
                System.out.println("Got click on " + button.getText());
            }
        };

        item1RadioButton.addActionListener(listener);
        item2RadioButton.addActionListener(listener);
        tieRadioButton.addActionListener(listener);

        nextButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                winItem = getSelectedButton(radioGroup);
                System.out.println(winItem);
            }
        });
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

    public JPanel getRootPanel()
    {
        return rootPanel;
    }

}
