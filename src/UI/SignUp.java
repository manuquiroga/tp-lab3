package UI;
import Handlers.JSONHandler;
import Handlers.SendEmail;
import Exceptions.IncorrectEmailFormatException;
import Exceptions.NameTooShortException;
import Exceptions.WeakPasswordException;
import Handlers.DataValidation;
import Users.PhysicalActivity;
import Users.User;
import Users.UserTest;
import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.TimerTask;

import static Handlers.DataValidation.checkData;

public class SignUp extends JFrame implements ActionListener{

    private static final String QUESTION_MARK_ICON_PATH = "src/UI/Resources/question.png";
    private static final String LOGO_ICON_PATH = "src/UI/Resources/weightlifter.png";
    private static final String NAME_LIMITS_INFO = "The name must have between 6 and 20 characters";
    private static final String PASSWORD_LIMITS_INFO = "The password must have at least 8 characters, a number and a capital letter";
    private static final String ACTIVITY_INFO = "You should put physical activity aside.\n NONE is if you work from home or if you are a student.\n MODERATE is for example a job where you lift boxes.\n ACTIVE is for example an active job as a cyclist postman";


    public SignUp() {
        //age(datepicker), sex,

        JFrame frame=new JFrame("Nutribros");//creating instance of JFrame
        frame.getContentPane().setBackground(new Color(41, 42, 54));
        ImageIcon logo = new ImageIcon(LOGO_ICON_PATH); frame.setIconImage(logo.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Name label and field
        JLabel NameLabel = new JLabel("Full Name:");
        NameLabel.setForeground(Color.WHITE);

        JTextField NameField = new JTextField();
        NameLabel.setBounds(35, 40, 80, 30);
        NameField.setBounds(115, 40, 250, 30);

        JLabel NameQuestionLabel = CreateIconLabel(QUESTION_MARK_ICON_PATH, NAME_LIMITS_INFO , 370, 40, 30, 30);
        frame.add(NameLabel); frame.add(NameField); frame.add(NameQuestionLabel);

        //Email label and field
        JLabel EmailLabel = new JLabel("Email:");
        EmailLabel.setForeground(Color.WHITE);

        JTextField EmailField = new JTextField();
        EmailLabel.setBounds(35, 90, 80, 30);
        EmailField.setBounds(115, 90, 250, 30);
        frame.add(EmailLabel); frame.add(EmailField);

        //Password label and field
        JLabel PasswordLabel = new JLabel("Password:");
        PasswordLabel.setForeground(Color.WHITE);

        JPasswordField PasswordField = new JPasswordField();
        PasswordLabel.setBounds(35, 140, 80, 30);
        PasswordField.setBounds(115, 140, 250, 30);

        JLabel PasswordQuestionLabel = CreateIconLabel(QUESTION_MARK_ICON_PATH, PASSWORD_LIMITS_INFO,370, 140, 30, 30);
        frame.add(PasswordLabel); frame.add(PasswordField); frame.add(PasswordQuestionLabel);

        //Weight and height label and field
        JLabel WeightLabel = new JLabel("Weight:");
        WeightLabel.setForeground(Color.WHITE);

        JTextField WeightField = new JTextField();
        WeightLabel.setBounds(35, 190, 80, 30);
        WeightField.setBounds(115, 190, 225, 30);

        JLabel WeightLabelKG = new JLabel("kg");
        WeightLabelKG.setForeground(Color.WHITE);
        WeightLabelKG.setBounds(345, 190, 20, 30);
        frame.add(WeightLabel); frame.add(WeightField); frame.add(WeightLabelKG);

        JLabel HeightLabel = new JLabel("Height:");
        HeightLabel.setForeground(Color.WHITE);

        JTextField HeightField = new JTextField();
        HeightLabel.setBounds(35, 240, 80, 30);
        HeightField.setBounds(115, 240, 225, 30);

        JLabel HeightLabelCM = new JLabel("cm");
        HeightLabelCM.setForeground(Color.WHITE);
        HeightLabelCM.setBounds(345, 240, 20, 30);
        frame.add(HeightLabel); frame.add(HeightField); frame.add(HeightLabelCM);

        //Desired Weight label and field
        JLabel DesiredWeightLabel = new JLabel("Desired Weight:");
        DesiredWeightLabel.setForeground(Color.WHITE);

        JTextField DesiredWeightField = new JTextField();
        DesiredWeightLabel.setBounds(35, 290, 80, 30);
        DesiredWeightField.setBounds(115, 290, 225, 30);

        JLabel DesiredWeightLabelKG = new JLabel("kg");
        DesiredWeightLabelKG.setForeground(Color.WHITE);
        DesiredWeightLabelKG.setBounds(345, 290, 20, 30);
        frame.add(DesiredWeightLabel); frame.add(DesiredWeightField); frame.add(DesiredWeightLabelKG);

        //Physical activity Combo box and label
        JLabel ActivityLabel = new JLabel("Activity:");
        ActivityLabel.setForeground(Color.WHITE);

        String[] Activities = {PhysicalActivity.NONE.toString(), PhysicalActivity.MODERATE.toString(), PhysicalActivity.ACTIVE.toString()};
        JComboBox ActivityCombo = new JComboBox(Activities);
        ActivityLabel.setBounds(35, 340, 80, 30);
        ActivityCombo.setBounds(115, 340, 250, 30);

        JLabel ActivityQuestionLabel = CreateIconLabel(QUESTION_MARK_ICON_PATH, ACTIVITY_INFO,370, 340, 30, 30);
        frame.add(ActivityLabel); frame.add(ActivityCombo); frame.add(ActivityQuestionLabel);

        //TODO:
        //create calendar to save age variable
        //set userData values

        //TODO:
        JLabel DateLabel = new JLabel("Birth Date:");



        JButton signButton=new JButton("Submit");//creating instance of JButton
        signButton.setBounds(135,440,150, 40);//x axis, y axis, width, height

        signButton.addActionListener(e -> {

            String name = NameField.getText();
            String email = (EmailField.getText());
            String password = Arrays.toString((PasswordField.getPassword()));
            //TODO: when userData is set, uncomment the try below this comment line
            /*int id=0;
             try{
                id=JSONHandler.countItemsInUserJSON()+1;
            } catch (FileNotFoundException ex)
            {
                System.err.println(ex.getMessage());
            }*/

            try{
                DataValidation.checkData(name, email, password);
                UserTest user = new UserTest(name, email, password);
                //User userAdd = new User(name, password, email, id, userData);
                //JSONHandler.userToFile(userAdd);
                System.out.println(user);

                String subject = "Welcome to Nutribros";
                String body = SendEmail.welcomeText(name);
                SendEmail.send(email, subject, body);

                //TODO: (ponele) forgot password

            } catch (IncorrectEmailFormatException ex) {
                System.err.println("Email error: " + ex.getMessage());
            } catch (NameTooShortException ex) {
                System.err.println("Name error: " + ex.getMessage());
            } catch (WeakPasswordException ex) {
                System.err.println("Password error: " + ex.getMessage());
            }

        });

        frame.add(signButton);//adding button in JFrame
        frame.setResizable(false);
        frame.setSize(420,560);
        frame.setLayout(null);//using no layout managers
        frame.setVisible(true);//making the frame visible
    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public JLabel CreateIconLabel(String iconPath, String labelText, int x, int y, int w, int h){
        ImageIcon Icon = new ImageIcon(iconPath);
        Image scaledImage = Icon.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel IconLabel = new JLabel(scaledIcon);
        IconLabel.setBounds(x, y, w, h);

        IconLabel.addMouseListener(new MouseAdapter() {
            private JDialog dialog;

            @Override
            public void mouseEntered(MouseEvent e) {
                dialog = new JDialog();
                dialog.setUndecorated(true);
                dialog.setLayout(new BorderLayout());
                dialog.add(new JLabel(labelText), BorderLayout.CENTER);
                dialog.pack();
                dialog.setLocationRelativeTo(IconLabel);
                dialog.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (dialog != null) {
                    dialog.dispose();
                    dialog = null;
                }
            }
        });

        return IconLabel;
    }
}

