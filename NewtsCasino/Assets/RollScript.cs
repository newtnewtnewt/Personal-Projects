using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using System;



public class RollScript : MonoBehaviour {
    public Text countText;
    public Text die1;
    public Text die2;
    public Text point;
    public Text textForRollNumber;
 

    int pointVal = 0;
    System.Random rand = new System.Random();
   

    public void RollIt()
    {
        
     
        int die1val = rand.Next(1, 7);
        int die2val = rand.Next(1, 7);

        String balance = countText.text;
        int realBal = int.Parse(balance);
        String rollNumberString = textForRollNumber.text;
        int rollNumber = int.Parse(rollNumberString);


        if (realBal - 5 < 0)
        {
        
            countText.text = "You went bust!";
            Environment.Exit(1);
        }

       
        die1.text = "" + die1val;
        die2.text = "" + die2val;
        if (rollNumber == 0)
        {
            realBal = realBal - 5;
            if (die1val + die2val == 7 || die1val + die2val == 11)
            {
                realBal = realBal + 15;
                countText.text ="" + realBal;
                rollNumber = 0;
            }
            else
            {
                pointVal = die1val + die2val;
                point.text  = "" + (pointVal);
                rollNumber++;
            }
        }
        else
        {
            if (die1val + die2val == 7 || die1val + die2val == 11)
            {
                rollNumber = 0;
                point.text = "";
                
            }
            else if (die1val + die2val == int.Parse(point.text)) {
                realBal = realBal + 10;
                countText.text = "" + realBal;
                rollNumber = 0;
            }
            
            else{
                    rollNumber = rollNumber + 1;

                }

            }
        textForRollNumber.text = "" + rollNumber;
        countText.text = "" + realBal;




    }



}
    

