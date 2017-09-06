import javax.swing.*;
public class Main {

    //creating my main method for the game
    public static void main(String[] args) {

        //this is the external GUI screen for the game
        JFrame obj = new JFrame();

        //Create a gamePlay object
        Game_Play gamePlay = new Game_Play();

        //This is setting the properties of the screen
        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Breakout Ball");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay); //added to JFrame object

    }
}