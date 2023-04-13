/*
Isaac Slais
4-12-23
Game Project
*/

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.event.*;
import javafx.animation.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


public class GameProject extends Application
{
   BorderPane bp=new BorderPane();
   FlowPane center =new FlowPane();
   StackPane top = new StackPane();
   
   // variables for throughout
   int ballsLeft=15;
   int possibleMoves=2;
   
   // game condition labels
   Label l1 = new Label("Balls Left: "+ballsLeft+" Possible Moves: "+possibleMoves);
   Label winLabel = new Label("YOU WIN");
   Label loseLabel = new Label("YOU LOSE");
   
   ArrayList<GamePane> GamePaneList = new ArrayList<GamePane>();
   public void start(Stage stage)
   {
      //setting up the main border pane
      center.setBackground(new Background(new BackgroundFill(Color.PAPAYAWHIP, 
      CornerRadii.EMPTY, Insets.EMPTY)));  
      center.setPrefSize(300,300);     
      FlowPane right = new FlowPane();
      right.setBackground(new Background(new BackgroundFill(Color.LAVENDER,CornerRadii.EMPTY, Insets.EMPTY)));  
      right.setPrefSize(49,50);
      FlowPane left = new FlowPane();
      left.setBackground(new Background(new BackgroundFill(Color.LAVENDER,CornerRadii.EMPTY, Insets.EMPTY)));  
      left.setPrefSize(49,50);
      top.getChildren().add(l1);
      top.setBackground(new Background(new BackgroundFill(Color.LAVENDER,CornerRadii.EMPTY, Insets.EMPTY)));  
      top.setPrefSize(50,50);
      FlowPane bottom = new FlowPane();
      bottom.setBackground(new Background(new BackgroundFill(Color.LAVENDER,CornerRadii.EMPTY, Insets.EMPTY)));  
      bottom.setPrefSize(50,50);

      //adding each element of the borderpane
      bp.setCenter(center);
      bp.setRight(right);
      bp.setLeft(left);
      bp.setTop(top); 
      bp.setBottom(bottom);  
      
      //idk if i still need this and im too lazy to check rn :)  
      center.setHgap(.001);
      center.setVgap(0);
   
   //loop
      for(int i=0; i<16; i++)//making gamepanes
   {
      GamePaneList.add(new GamePane(i));
      GamePaneList.get(i).draw();
      center.getChildren().add(GamePaneList.get(i).paint());
   }

   GamePaneList.get(8).jumped();
   GamePaneList.get(8).newButtons();
     
        
    //set scene
      Scene scene = new Scene(bp, 600, 600);
      stage.setTitle("BALL GAME");
      stage.setScene(scene);
      stage.show();  

   }

  //GameGridPane Class 
  class GamePane  extends GridPane
  {
      Canvas canvas=new Canvas(80, 80);
      GridPane gp= new GridPane();
      GraphicsContext gc = canvas.getGraphicsContext2D();
      int paneNum=0;
      
      Button bTop=new Button("T");
      Button bBottom=new Button("B");
      Button bRight=new Button("R");
      Button bLeft=new Button("L");

      public GamePane(int num)
      {
          gp.setVgap(0);
          gp.setHgap(0);
          paneNum=num;
      }
      //draw method
      public void draw()
      {
        gp.setPrefSize(100, 100);
        
        bTop.setPrefSize(80, 20);
        bBottom.setPrefSize(80, 20);
        bRight.setPrefSize(20, 80);
        bLeft.setPrefSize(20, 80);
       //adding buttons to pane
        gp.add(canvas, 1, 1);
        gp.add(bTop, 1, 0);
        gp.add(bBottom, 1, 2);
        gp.add(bLeft, 0, 1);
        gp.add(bRight, 2, 1);

        gc.fillOval(2,2, 75, 75);
        
       //set actions to buttons 
       bTop.setOnAction(new ButtonListener(paneNum, 'T'));
       bBottom.setOnAction(new ButtonListener(paneNum, 'B'));
       bRight.setOnAction(new ButtonListener(paneNum, 'R'));
       bLeft.setOnAction(new ButtonListener(paneNum, 'L'));
       
       // setting all the buttons to start invisible
       bLeft.setVisible(false);
       bTop.setVisible(false);
       bRight.setVisible(false);
       bBottom.setVisible(false);
       
      }
   
      public void setButtonVis(char b, boolean vis) // takes in which button and if it should be true=visible or false=invisible
      {
        
          if(b=='T')
          {
             bTop.setVisible(vis);
          }
           if(b=='B')
          {
             bBottom.setVisible(vis);
          }
           if(b=='L')
          {
             bLeft.setVisible(vis);
          }
           if(b=='R')
          {
             bRight.setVisible(vis);
          }
             
      }
      
      public void jumped()
      {
          gp.setVisible(false);
      }    
          
      public void newButtons()
      {     
         for(int i=0; i<=15; i++)
         {
         paneNum=i;
             // right
             if(paneNum%4==0||(paneNum-1)%4==0)
             {
                if(GamePaneList.get(paneNum+1).getVisStat()&&(!GamePaneList.get(paneNum+2).getVisStat()))
                  {
                     GamePaneList.get(paneNum).setButtonVis('L', true);
                  } 
             }
             //left
             else if(paneNum%2==0||(paneNum-1)%2==0)
             {
                if(GamePaneList.get(paneNum-1).getVisStat()&&(!GamePaneList.get(paneNum-2).getVisStat()))
                  {
                     GamePaneList.get(paneNum).setButtonVis('R', true);
                  } 
             }
          
             // if less then 7 then it can jump up
             if(paneNum<=7)
             {
              if(GamePaneList.get(paneNum+4).getVisStat()&&(!GamePaneList.get(paneNum+8).getVisStat()))
               {
               GamePaneList.get(paneNum).setButtonVis('T', true); 
               }
             }
             // if more then 8 then it can jump down
             if(paneNum>=8)
             {
               if(GamePaneList.get(paneNum-4).getVisStat()&&(!GamePaneList.get(paneNum-8).getVisStat()))
               {
               GamePaneList.get(paneNum).setButtonVis('B', true); 
               }
             }
         
         }// end of loop

      }

      public void setNext(int currentPane)
      {
          gp.setVisible(true);         
      }
      
      public boolean getVisStat()
      {
         return gp.isVisible();
      }
      
      public int getButtonsLeft()
      {
         int count=0;
         if(gp.isVisible())
         {
            if(bTop.isVisible())
               count++;
            if(bBottom.isVisible())
               count++;
            if(bLeft.isVisible())
               count++;
            if(bRight.isVisible())
               count++;
         }
         return count;
   }

      public GridPane paint()
      {
         return gp;
      }

   }
   
   //button listner
   public class ButtonListener implements EventHandler<ActionEvent>  
   {
      int paneNum=0;//which pane
      char  butttonLoc=' ';
      public ButtonListener(int numIn, char butttonLocIn )
      {
        paneNum=numIn;
        butttonLoc=butttonLocIn;
      }
      
      public void handle( ActionEvent e) 
      {
      
         for(int i=0; i<16; i++)//resetting all buttons. if one doesnt show up here is where the issue is likey at
         {
            GamePaneList.get(i).setButtonVis('T', false);
            GamePaneList.get(i).setButtonVis('B', false);
            GamePaneList.get(i).setButtonVis('L', false);
            GamePaneList.get(i).setButtonVis('R', false);
         }

      // if for each of what button was pressed
         if (butttonLoc=='B')
         {
            GamePaneList.get(paneNum).jumped();  // get rid of og circle jumping
            GamePaneList.get(paneNum-4).jumped(); // get rid of jumped circle
            GamePaneList.get(paneNum-8).setNext(0); //method to add circle in new spot
            GamePaneList.get(paneNum).newButtons(); //method to set buttons where needed
//                                                       VVV same throughout VVV
         }
         if (butttonLoc=='T')
         {
            GamePaneList.get(paneNum).jumped();
            GamePaneList.get(paneNum+4).jumped();
            GamePaneList.get(paneNum+8).setNext(0);
            GamePaneList.get(paneNum).newButtons();
         }
         
         if (butttonLoc=='R')
         {
            GamePaneList.get(paneNum).jumped();
            GamePaneList.get(paneNum-1).jumped();
            GamePaneList.get(paneNum-2).setNext(0);
            GamePaneList.get(paneNum).newButtons();
         }
         if (butttonLoc=='L')
         {
            GamePaneList.get(paneNum).jumped();
            GamePaneList.get(paneNum+1).jumped();
            GamePaneList.get(paneNum+2).setNext(0);
            GamePaneList.get(paneNum).newButtons();
         }
      
         //loop to check if all the cirgles have disaapeared
         int totalGone=0;
         int buttonsOn=0;
         for(int i=0; i<GamePaneList.size(); i++)
         {
            if(!GamePaneList.get(i).getVisStat())//if all are invisible
            {
               totalGone++;
            }
            buttonsOn+=GamePaneList.get(i).getButtonsLeft();//count how many buttons there are
         }
        int f1=16-totalGone;
        int  f2=buttonsOn;
        Label l2 = new Label("Balls Left: "+f1+" Possible Moves: "+f2+"");
        top.getChildren().clear();
        top.getChildren().add(l2);
         // if there are no buttons/ you loose
        if(buttonsOn==0)
        {
            top.getChildren().clear();
            top.getChildren().add(loseLabel);
        }
        //if all but one circle is left
        if(totalGone>=15)
        {
            top.getChildren().clear();
            top.getChildren().add(winLabel);
        }     
      }
   }
 }