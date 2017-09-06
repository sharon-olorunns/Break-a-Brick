import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game_Play extends JPanel implements KeyListener, ActionListener{

    //prevent the game playing by itself
    private boolean play = false;

    //start score is 0
    private int score = 0;

    private int totalBricks = 32;
    private Timer timer;
    private int delay = 6;  //speed of the ball (small value = faster speed of the ball)

    //player starting XPos
    private int playerX = 310;

    //ball start positions
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballYdir = -2;
    private int ballXdir = -1;

    private Map_Generator map;

    //Game_play Constructor
    public Game_Play(){
        map = new Map_Generator(4,8);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);

        //start timer to start game
        timer.start();
    }

    //draws the different characters and objects in the game
    public void paint(Graphics g){
        //background
        g.setColor(Color.BLACK);
        g.fillRect(1,1,692,592);

        map.draw((Graphics2D)g);

        //boarders
        g.setColor(Color.black);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        //the paddle
        g.setColor(Color.blue);
        g.fillRect(playerX, 550,100,8);

        //show the score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD ,25));
        g.drawString("" + score , 590 ,30);

        //the ball
        g.setColor(Color.green);
        g.fillOval(ballposX,ballposY,20,20);

        if(totalBricks <= 0){
            play = false;
            g.setColor(Color.red);
            g.setFont(new Font("comic sans ms", Font.BOLD ,30));
            g.drawString("You won " , 250 ,300);

            g.setFont(new Font("comic sans ms", Font.BOLD ,20));
            g.drawString("Press enter to restart", 230 ,350);

        }

        if(ballposY > 570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("comic sans ms", Font.BOLD ,30));
            g.drawString("Game Over" , 250 ,300);

            g.setFont(new Font("comic sans ms", Font.BOLD ,20));
            g.drawString("Press enter to restart", 230 ,350);
        }

        g.dispose();
    }

    /*  KeyListeners that detect if I have pressed the right arrow key or
        the left arrow key

        To detect the ball and paddle collision we can use a rectangle
        around the ball
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        //move the ball
        if(play){

            ballposX += ballXdir;
            ballposY += ballYdir;

            //detect if the ball is touching the boarders

            //left-boarder
            if(ballposX < 0){
                ballXdir = -ballXdir;  //changes direction of the ball
            }

            //top-boarder
            if(ballposY < 0){
                ballYdir = -ballYdir;  //changes direction of the ball
            }

            //right-boarder
            if(ballposX > 670){
                ballXdir = -ballXdir;  //changes direction of the ball
            }

            //Ball and paddle collision
            if(new Rectangle(ballposX,ballposY,20,20).intersects (new Rectangle(playerX,550,100,8))){
                ballYdir = -ballYdir;
            }

            A: for(int i = 0; i< map.map.length; i++){
                for(int j = 0; j < map.map[0].length; j++){
                    if(map.map[i][j] > 0){
                        int brickX =j * map.brickWidth + 80;
                        int brickY = i* map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle (brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX,ballposY, 20,20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score += 5;

                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            }else{
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }

                    }
                }
            }
        }
        //updates the paint method when a key is pressed
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        //if the right arrow key is pressed
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (playerX < 10) {
                    playerX = 10;
                } else {
                    moveLeft();
                }
            }

        //place all the original values in the restart code block
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                playerX = 310;

                //ball start positions
                ballposX = 120;
                ballposY = 350;
                ballYdir = -2; //balls direction
                ballXdir = -1;
                score = 0;
                totalBricks = 32;

                map =new Map_Generator(4,8);
                repaint();
            }
        }
    }

    //Paddle movement functions
    public void moveRight(){
            play = true;
            playerX +=20;
    }

    public void moveLeft(){
        play = true;
        playerX -=20;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

}