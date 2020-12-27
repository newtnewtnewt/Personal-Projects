using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace Calculator
{
    // Learn more about making custom code visible in the Xamarin.Forms previewer
    // by visiting https://aka.ms/xamarinforms-previewer
    [DesignTimeVisible(false)]
    public partial class MainPage : ContentPage
    {
        double memoryLocation = 0;
        bool equalsPressed = false;
        public MainPage()
        {
            //  We want all our initial states to be such that the user can't come up with crackpot input right off the bat
            InitializeComponent();
            disableBlues();
            disableYellows();
            disableTwo();
            minusButton.IsEnabled = true;
        }

        private void Yellow_Clicked(object sender, EventArgs e)
        {
            //  We use HTML decode to compare HTML ASCII correctly, this does the sqrt function
            Button buttonCheck = (Button) sender;
            if (buttonCheck.Text == System.Net.WebUtility.HtmlDecode("&#8730;"))
            {
                Input.Text = "" + round(Math.Sqrt(double.Parse(Input.Text)));
            }
            //  Insert an exponential into the input feed
            else if (buttonCheck.Text == "x^y")
            {
                if (Input.Text.Length + 1 > 20) { return; }
                equalsPressed = false;
                Input.Text = Input.Text += " ^ ";
                disableBlues();
                disableYellows();
                disableTwo();
                
            }
            //  Square the existing text
            else if(buttonCheck.Text == "x^2")
            {
                Input.Text = "" + round(Math.Pow(double.Parse(Input.Text), 2));
            }
            //  Take asin of the existing text
            else if(buttonCheck.Text == "asin")
            {
                Input.Text = "" + round(Math.Asin(double.Parse(Input.Text)));
            }
            //  Take the acos of the existing text
            else if(buttonCheck.Text == "acos")
            {
                Input.Text = "" + round(Math.Acos(double.Parse(Input.Text)));
            }
            //  Take the atan of the existing text
            else if(buttonCheck.Text == "atan")
            {
                Input.Text = "" + round(Math.Atan(double.Parse(Input.Text)));
            }
            //  Put the eixsting input text as the exponent to 10
            else if(buttonCheck.Text == "10^x")
            {
                Input.Text = "" + round(Math.Pow(10, double.Parse(Input.Text)));
            }
            //  Take the natural log of the existing text
            else if(buttonCheck.Text == "ln")
            {
                Input.Text = "" + round(Math.Log(double.Parse(Input.Text)));
            }
            //  Take the log base 10 of the existing text
            else if(buttonCheck.Text == "log")
            {
                Input.Text = "" + round(Math.Log10(double.Parse(Input.Text)));
            }
            //  Take the sin of the existing text
            else if(buttonCheck.Text == "sin")
            {
                Input.Text = "" + round(Math.Sin(double.Parse(Input.Text)));
            }
            //  Take the cos of the existing text
            else if(buttonCheck.Text == "cos")
            {
                Input.Text = "" + round(Math.Cos(double.Parse(Input.Text)));
            }
            //  Take the tan of the existing text
            else if(buttonCheck.Text == "tan")
            {
                Input.Text = "" + round(Math.Tan(double.Parse(Input.Text)));
            }
            //  Take 1/x of the existing text
            else if(buttonCheck.Text == "1/x")
            {
                Input.Text = "" + round(1 / (double.Parse(Input.Text)));
            }
            //  Insert Pi into the existing text
            else if(buttonCheck.Text == System.Net.WebUtility.HtmlDecode("&#x3C0;"))
            {
                if (Input.Text.Length + 1 > 20) { return; }
                piButton.IsEnabled = false;
                enableBlues();
                expButton.IsEnabled = true;
                if (!operatorsPresent(Input.Text))
                {
                    enableYellows();
                    plusMinusButton.IsEnabled = true;
                    expButton.IsEnabled = true;
                }
                Input.Text = " " + round(Math.PI);
            }
            //  Factorial the existing text
            else if(buttonCheck.Text == "x!")
            {
                if(Input.Text != "Infinity" &&  Input.Text != "NaN" && !Input.Text.Contains("."))
                {
                    Input.Text = "" + factorial(Int32.Parse(Input.Text));
                }
                
            }

        }
        private void Grey_Clicked(object sender, EventArgs e)
        {
            Button buttonCheck = (Button)sender;
            //  Certain adjustments we have to make based on the insertion of a digit
            if (equalsPressed && (buttonCheck.Text != "exp" && buttonCheck.Text != System.Net.WebUtility.HtmlDecode("&#177;")) ) {
                Input.Text = ""; clear(); equalsPressed = !equalsPressed;
            }
            if (isDigit(buttonCheck.Text)) {
                if (Input.Text.Length + 1 > 20) { return; }
                //  Get our operators back 
                enableBlues();
                //  Throw our equals button up
                equalsButton.IsEnabled = true;
                //  Enable the e button
                expButton.IsEnabled = true;
                //  Throw our yellows, e button, and plusMinus button up if there aren't operators
                if (!operatorsPresent(Input.Text)){
                    enableYellows(); plusMinusButton.IsEnabled = true;
                }
            }
            if (buttonCheck.Text == ".") {
                //  Prevent wacky decimal play
                if (Input.Text.Length + 1 > 20) { return; }
                disableBlues(); disableYellows(); disableTwo();
                decimalButton.IsEnabled = false;
                equalsButton.IsEnabled = false;
                piButton.IsEnabled = false;
                minusButton.IsEnabled = false;
            }
            
            //  Extra rules for retracting by one space
            if (buttonCheck.Text == "DEL")
            {   
                //  Deleting everything if any of the fields is met
                if (Input.Text == "NaN" || Input.Text == "Infinity" || Input.Text  == "Error") { Input.Text = ""; clear(); }
                else
                {
                    //  If there is something there
                    if (Input.Text.Length > 0)
                    {
                        //  How to process deleting decimals or operators
                        if (Input.Text.Length > 1 && operatorsPresent("" + Input.Text[Input.Text.Length - 2]) || Input.Text[Input.Text.Length - 1] == '.')
                        {
                            if (Input.Text[Input.Text.Length - 1] == '.') {
                                Input.Text = Input.Text.Substring(0, Input.Text.Length - 1);
                                decimalButton.IsEnabled = true;
                            }
                            else { Input.Text = Input.Text.Substring(0, Input.Text.Length - 3); }
                            enableBlues();
                        }
                        //  Deleting regular digits
                        else {
                            Input.Text = Input.Text.Substring(0, Input.Text.Length - 1);
                        }
                        //  Re-Enable all the stuff when we empty the calculator
                        if(Input.Text.Length == 0) { clear(); }
                        //  Get rid of any extra negatives
                        else if(Input.Text.Length == 1 && Input.Text == "-")
                        {
                            Input.Text = ""; clear();
                        }
                    }
                    //  Throw our yellows up when operators are gone
                    if (!operatorsPresent(Input.Text) && Input.Text.Length != 0){
                        enableYellows();
                        plusMinusButton.IsEnabled = true;
                        expButton.IsEnabled = true;
                    }
                   
                }
            }
            //  Clear the input
            else if (buttonCheck.Text == "C") { clear(); } 
            //  Add into memory
            else if (buttonCheck.Text == "M+") { memoryLocation += round(double.Parse(Input.Text)); }
            //  Subtract from memory
            else if (buttonCheck.Text == "M-")
            {
                memoryLocation -= round(double.Parse(Input.Text));
            }
            //  Grab numbers from memory
            else if (buttonCheck.Text == "MR") { Input.Text += memoryLocation; }
            if (Input.Text.Length + 1 > 20) { return; }
            //  Save into memory
            else if (buttonCheck.Text == "MS") { memoryLocation = double.Parse(Input.Text); }
            //  Clear memory 
            else if (buttonCheck.Text == "MC") { memoryLocation = 0; }
            //  Add 0-9 to the input
            else if (buttonCheck.Text == "0") { Input.Text += "0"; }
            else if (buttonCheck.Text == "1") { Input.Text += "1"; }
            else if (buttonCheck.Text == "2") { Input.Text += "2"; }
            else if (buttonCheck.Text == "3") { Input.Text += "3"; }
            else if (buttonCheck.Text == "4") { Input.Text += "4"; }
            else if (buttonCheck.Text == "5") { Input.Text += "5"; }
            else if (buttonCheck.Text == "6") { Input.Text += "6"; }
            else if (buttonCheck.Text == "7") { Input.Text += "7"; }
            else if (buttonCheck.Text == "8") { Input.Text += "8"; }
            else if (buttonCheck.Text == "9") { Input.Text += "9"; }
            //  Add in the e operator
            else if (buttonCheck.Text == "exp") { Input.Text += " e "; disableBlues(); disableYellows(); equalsPressed = false; }
            //  Throw a decimal in
            else if (buttonCheck.Text == ".") { Input.Text += "."; }

            //  Flip from negative to positive or positive or negative
            else if (buttonCheck.Text == System.Net.WebUtility.HtmlDecode("&#177;"))
            {
                equalsPressed = false;
                if (Input.Text[0] == '-')
                {
                    Input.Text = Input.Text.Substring(1);
                }
                else if (Input.Text.Length > 0)
                {
                    Input.Text = "-" + Input.Text;
                }
            }
            //  Calculate based on the input
            else if (buttonCheck.Text == "=")
            {
                if (operatorsPresent(Input.Text))
                {
                    equalsPressed = true;
                    submit();
                }
            }
            //  Throw the yellows back up if all operators are gone
            if (!operatorsPresent(Input.Text) && Input.Text.Length != 0)
            {
                enableYellows(); 
            }
        }
        private void Blue_Clicked(object sender, EventArgs e)
        {
            equalsPressed = false;
            if(Input.Text == "NaN" || Input.Text == "Infinity") { Input.Text = ""; clear(); return; }
            if (Input.Text.Length + 1 > 20) { return; }
            //  Disable all operators in the presence of an operator acceptance
            Button buttonCheck = (Button)sender;
            piButton.IsEnabled = true;
            decimalButton.IsEnabled = true;
            equalsButton.IsEnabled = false;
            disableTwo();
            disableBlues();
            disableYellows();
            //  Add in desired blue operator
            if(buttonCheck.Text == "x") { Input.Text += " * "; }
            else if(buttonCheck.Text == "+") { Input.Text += " + "; }
            else if (buttonCheck.Text == "-") {
                if (Input.Text.Length != 0 && Char.IsDigit(Input.Text[Input.Text.Length - 1]))
                {
                    Input.Text += " - ";
                }
                else
                {
                    Input.Text += "-";
                }
                minusButton.IsEnabled = false;
            }
              
            else if (buttonCheck.Text == System.Net.WebUtility.HtmlDecode("&#xF7;")) { Input.Text += " / "; }
            
        }
        private double round(double needsRounded)
        {
            //  Round according to specified digits
            return Math.Round(needsRounded, Rounding.SelectedIndex, MidpointRounding.AwayFromZero);
        }
        // Calculates a factorial from an input
        private int factorial(int needsFactorialed)
        {
            int total = 1;
            for(int i = 1; i <= needsFactorialed; i++)
            {
                total *= i;
            }
            return total;
        }
        //  Disable all blue buttons and blue related functionality
        private void disableBlues()
        {
            plusButton.IsEnabled = false;
            xButton.IsEnabled = false;
            divideButton.IsEnabled = false;
            powerButton.IsEnabled = false;
        }
        //  Enable all blue buttons and blue related functionality
        private void enableBlues()
        {
            plusButton.IsEnabled = true;
            xButton.IsEnabled = true;
            divideButton.IsEnabled = true;
            powerButton.IsEnabled = true;
            minusButton.IsEnabled = true;
        }
        //  Disable all yellow buttons and yellow related functionality
        private void disableYellows()
        {
            sqrtButton.IsEnabled = false;
            squaredButton.IsEnabled = false;
            asinButton.IsEnabled = false;
            acosButton.IsEnabled = false;
            atanButton.IsEnabled = false;
            log10Button.IsEnabled = false;
            naturalLogButton.IsEnabled = false;
            power10Button.IsEnabled = false;
            sinButton.IsEnabled = false;
            tanButton.IsEnabled = false;
            oneOverXButton.IsEnabled = false;
            factorialButton.IsEnabled = false;
            mPlusButton.IsEnabled = false;
            mMinusButton.IsEnabled = false;
            mSaveButton.IsEnabled = false;
            plusMinusButton.IsEnabled = false;
            cosButton.IsEnabled = false;
        }
        //  Enable all Yellow buttons and Yellow related functionality
        private void enableYellows()
        {
            sqrtButton.IsEnabled = true;
            squaredButton.IsEnabled = true;
            asinButton.IsEnabled = true;
            acosButton.IsEnabled = true;
            atanButton.IsEnabled = true;
            log10Button.IsEnabled = true;
            naturalLogButton.IsEnabled = true;
            power10Button.IsEnabled = true;
            sinButton.IsEnabled = true;
            tanButton.IsEnabled = true;
            oneOverXButton.IsEnabled = true;
            factorialButton.IsEnabled = true;
            mPlusButton.IsEnabled = true;
            mMinusButton.IsEnabled = true;
            mSaveButton.IsEnabled = true;
            plusMinusButton.IsEnabled = true;
            cosButton.IsEnabled = true;
        }
        //  Disable the two special buttons 
        public void disableTwo()
        {
            plusMinusButton.IsEnabled = false;
            expButton.IsEnabled = false;
        }
        //  Determines if an operator is in the given string
        private bool operatorsPresent(string input)
        {
            if (input.Contains("*") ||
               input.Contains("e") ||
               input.Contains("+") ||
               input.Contains("^") ||
               input.Contains("/") || 
               input.Contains("."))
            {
                return true;
            }
            for (int i = 0; i < Input.Text.Length - 1; i++)
            {
                if (Input.Text[i] == '-' && !Char.IsDigit(Input.Text[i + 1]))
                {
                    return true;
                }
            }

                    return false;
        }
        //  Determines if the following string is a digit
        private bool isDigit(string input)
        {
            String digitOps = "0123456789MR";
            if (digitOps.Contains(input)) { return true; }
            return false;
        }
        //  Clear the inputs and set the buttons back as they were supposed to be
        private void clear()
        {
            Input.Text = "";
            disableBlues();
            disableYellows();
            disableTwo();
            piButton.IsEnabled = true;
            decimalButton.IsEnabled = true;
            plusMinusButton.IsEnabled = false;
            minusButton.IsEnabled = true;            
        }
        //  Submit what is currently in the input feed, and clear the buttons back to what they need to be
        private void submit()
        {
            double resultVal = evaluate(Input.Text);
            Input.Text = "" + resultVal;
            enableYellows();
            enableBlues();
        }
        //  Evaluate the precedence based on the character of input
        private int precedenceEval(char precedenceInput)
        {
            switch (precedenceInput)
            {
                case 'm':
                case '+':
                    return 0;


                case '*':
                case '/':
                case '%':
                    return 1;

                case '^':
                    return 2;
                case 'e':
                    return 3;
                default:
                    return -1;
            }

        }
        //  Evaluate based on inputString
       
        public double evaluate(string infix)
        {
           
            //  Create two stacks, one for operators and one for operands
            Stack<char> operatorStack = new Stack<char>();
            Stack<String> valueStack = new Stack<String>();
            for(int i = 0; i < infix.Length -1; i++)
            {
                if(infix[i] == '-' && !Char.IsDigit(infix[i + 1]))
                {
                    infix = infix.Substring(0, i) + 'm' + infix.Substring(i + 1, infix.Length - (i + 1));
                }
            }

            // Run through the whole expression 
            for (int i = 0; i < infix.Length; i++)
            {
                //  Grab a char
                char nextChar = infix[i];
                    
                //  Switch based on char
                switch (nextChar)
                {
                    //  For all operand data
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                    case '0':
                    case '.':
                    case '-':
                        String variable = "";
                        //  Create a single operand by appending until we hit a space
                        while (i != infix.Length && (Char.IsDigit(infix[i]) || infix[i] == '.' || infix[i] == '-'))
                        {
                            variable = variable + infix[i];
                            i++;

                        }
                        //  Append the whole value to the valueStack
                        valueStack.Push(variable);
                        break;

                    //  In the case of the highest-precedence item 
                    case 'e':
                        operatorStack.Push(nextChar);
                        break;
                    //  For all the lesser precedence items
                    case '+':
                    case 'm':
                    case '*':
                    case '/':
                    case '%':
                    case '^':
                        //  Evaluate what we need to pop off based on Precedence,only push if precedance is higher than the other
                        while (operatorStack.Count != 0 && precedenceEval(nextChar) <= precedenceEval(operatorStack.Peek()))
                        {
                            char topOperator = operatorStack.Pop(); //  Grab the desired operand
                            double operandTwo = Double.Parse(valueStack.Pop()); //  Pop off one value
                            double operandOne = Double.Parse(valueStack.Pop());  //  Pop off the other value
                            double result = 0; //  Our resultant value
                            //  Evaluate based on operator 
                            if (topOperator == '+')
                            {
                                result = operandOne + operandTwo;
                            }
                            else if (topOperator == 'm')
                            {
                                result = operandOne - operandTwo;
                            }
                            else if (topOperator == '*')
                            {
                                result = operandOne * operandTwo;
                            }
                            else if (topOperator == '/')
                            {
                                result = operandOne / operandTwo;
                            }
                            else if (topOperator == '%')
                            {
                                result = operandOne % operandTwo;
                            }
                            else if (topOperator == '^')
                            {

                                result = Math.Pow(operandOne, operandTwo);
                            }
                            else if (topOperator == 'e')
                            {
                                result = operandOne * (Math.Pow(10, operandTwo));
                            }
                            //  Chuck on the Value based on the result of the operation
                            valueStack.Push("" + result);
                        }
                        //  Throw our operator on the stack
                        operatorStack.Push(nextChar);
                        break;

                    default: break;
                }
            }
            //  When we finish running through the whole expression, clean out the last operations
            while (operatorStack.Count != 0)
            {
                //  Identical to before
                char topOperator = operatorStack.Pop();
                double operandTwo = Double.Parse(valueStack.Pop());
                double operandOne = Double.Parse(valueStack.Pop());
                double result = 0;
                if (topOperator == '+')
                {
                    result = operandOne + operandTwo;
                }
                else if (topOperator == 'm')
                {
                    result = operandOne - operandTwo;
                }
                else if (topOperator == '*')
                {
                    result = operandOne * operandTwo;
                }
                else if (topOperator == '/')
                {
                    result = operandOne / operandTwo;
                }
                else if (topOperator == '%')
                {
                    result = operandOne % operandTwo;
                }
                else if (topOperator == '^')
                {
                    result = Math.Pow(operandOne, operandTwo);
                }
                else if (topOperator == 'e')
                {
                    result = operandOne * (Math.Pow(10, operandTwo));
                }
                valueStack.Push("" + result);

            }
            // Round our final output
            return round(Double.Parse(valueStack.Peek()));

        }

    }
}
