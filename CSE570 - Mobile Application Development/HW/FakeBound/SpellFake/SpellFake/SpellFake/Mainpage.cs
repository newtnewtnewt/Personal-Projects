using System;
using System.Collections.Generic;
using System.Text;
using Xamarin.Forms;
using System.Collections;
using WordScramblers_CSE570J;
using System.Diagnostics;
using System.Threading.Tasks;
/* Name: Noah Dunn
 * Professor: Dr. Zmuda
 * Date: 9/24/2019
 * Class: CSE 570J
 * Project: SpellFake
 * 
 * Project Log:
 * -Insofar as I can tell, this project runs with no errors.
 * Run this project on UWP for maximum results
 */

namespace SpellFake
{
    /* Our main screen we care about processing*/
    class MainPage : ContentPage
    {
        //  An instance of our dictionary
        EnglishWords eg;

        int countedSeconds;
        bool finished = false;
        //  The label for the user's input using the keypad
        Label text = new Label
        {
            Text = "",
            FontSize = 25,
            FontAttributes = FontAttributes.Bold,
            HorizontalOptions = LayoutOptions.Center
        };
        //  A label to keep track of the current time
        Label timer = new Label
        {
            Text = "2:00",
            FontSize = 20,
            FontAttributes = FontAttributes.Italic,
            HorizontalOptions = LayoutOptions.Start
        };
        //  A label to keep track of the words already guessed
        Label wordList = new Label
        {
            Text = "",
            FontSize = 15,
            HorizontalOptions = LayoutOptions.Start,
            VerticalOptions = LayoutOptions.Start,
            TextColor = Color.Aquamarine
        };
        //  A label to keep track of the number of words already guessed + processed
        Label numOfFinds = new Label
        {
            Text = "",
            FontSize = 20,
            HorizontalOptions = LayoutOptions.End

        };
        //  Create an array of buttons, our submit button, our back button, and our ArrayList to keep track of pressed buttons
        Button submitButton;
        Button backButton;
        Button[] buttonArray;
        
        Stack buttonsPressed = new Stack();
        List<string> allWords = new List<string>();
        HashSet<string> allWordsHashed = new HashSet<string>();
        HashSet<string> wordsFoundHashed = new HashSet<string>();
        Button resetButton;
        Grid grid;
        public MainPage()
        {
            //  Begin asynchronous loading
            eg = new EnglishWords("words.txt");
            //  Six buttons for our character set
            buttonArray = new Button[6];
            //  Grab what letters we are gonna use for this game
            String letters = GenerateLetterSet();
            resetButton = new Button { Text = "Reset", BackgroundColor = Color.LightYellow, IsEnabled = false, IsVisible = false }; resetButton.Clicked += OnClicked;
            GenerateButtons(letters);
            
            //  Variable for storing the number of possible combinations for a given charset
            int numOfCombinations = 0;
            for(int i = 1; i < letters.Length + 1; i++)
            {
                //  Generate every possible wordset going from 1 - 6 characters 
                List<String> words = eg.AvailableWords(letters, i);

                for(int j = 0; j < words.Count; j++) {
                    numOfCombinations++;
                    allWords.Add(words[j]);
                    allWordsHashed.Add(words[j]);
                }
                
            }
            //  Create our starter text
            numOfFinds.Text = ("0/" + numOfCombinations);

            //  Construct a grid for mounting our labels and options
            grid = new Grid
            {
                VerticalOptions = LayoutOptions.FillAndExpand,
                Margin = new Thickness(10),
                RowDefinitions =
                {
                    //  Give the button rows equal amounts of space, with less room for the timer, current word, and word counter
                        new RowDefinition { Height = new GridLength(0.7, GridUnitType.Star) },
                        new RowDefinition { Height = new GridLength(1, GridUnitType.Star) },
                        new RowDefinition { Height = new GridLength(1, GridUnitType.Star) },
                        new RowDefinition { Height = new GridLength(1, GridUnitType.Star) },
                        new RowDefinition { Height = new GridLength(1, GridUnitType.Star) }
                 },
                ColumnDefinitions =
                {
                    //  Split the columns in thirds
                       new ColumnDefinition { Width = new GridLength(33.3, GridUnitType.Star) },
                       new ColumnDefinition { Width = new GridLength(33.3, GridUnitType.Star) },
                       new ColumnDefinition { Width = new GridLength(33.3, GridUnitType.Star) }
                }
            };
            //  Add all our non-button elements in appropriate positions
            grid.Children.Add(timer, 0, 1, 0, 1 );
            grid.Children.Add(text, 1, 2, 0, 1);
            grid.Children.Add(numOfFinds, 2, 3, 0, 1);
            grid.Children.Add(wordList, 0, 3, 1, 2);
            grid.Children.Add(resetButton, 0, 3, 4, 6);
            //  Add all our button elements in their corresponding grid element
            for (int i = 0; i < buttonArray.Length; i++)
            {
                grid.Children.Add(buttonArray[i], i % 3, 2 +(int)(i / 3) + 1);
            }
            //  Manually add the back button and submit button
            backButton = new Button { Text = "Backspace", BackgroundColor = Color.LightYellow }; backButton.Clicked += OnClicked;
            submitButton = new Button { Text = "Submit", BackgroundColor = Color.LightYellow}; submitButton.Clicked += OnClicked;
            grid.Children.Add(backButton, 0, 2, 5, 6);
            grid.Children.Add(submitButton, 2, 3, 5, 6);
            

            //  Clock is set for 2 minutes or 120 seconds
            countedSeconds = 120;
            //  Activate the timer as a disposable object
            _ = FakeTimer();
            //  Use a Stack Layout to combine my grid element and title placard
            StackLayout sl = new StackLayout();
            Label titleLabel = new Label {
                Text = "Spell-Round",
                FontSize = 30,
                HorizontalTextAlignment = Xamarin.Forms.TextAlignment.Center,
                //  Use Custom Lobster font from Google
                FontFamily = Device.RuntimePlatform == Device.UWP ? "Assets/Fonts/Lobster-Regular.ttf#Lobster" : null
            };
            //  Build the scene
            sl.Children.Add(titleLabel);
            sl.Children.Add(grid);
            this.Content = sl;
            this.Content.BackgroundColor = Color.Coral;

        }
        /* This "fake timer" gets away by using Task.Delay at 1second intervals asynchronously */
        public async Task FakeTimer()
        {
            //  Loop until we beat it or run out of time
            while (countedSeconds != 0 && !finished) 
            {
                //  Activates this sucker Asynch
                await Task.Delay(1000);
                countedSeconds--;
                //  Checks if we we need to add a 0 to the timer of values < 10
                if (countedSeconds % 60 < 10)
                {
                    timer.Text = "" + (countedSeconds / 60) + ":0" + (countedSeconds % 60);
                }
                else
                {
                    timer.Text = "" + (countedSeconds / 60) + ":" + (countedSeconds % 60);
                }
                //  Make the impending doom more threatening with the color red
                if(countedSeconds <= 10)
                {
                    timer.TextColor = Color.Red;
                }
                //  Stop and clear if the person loses
                if (countedSeconds == 0)
                {
                    finished = true;
                    //  Disable all buttons, change the text, and make the colors gray
                    for (int i = 0; i < buttonArray.Length; i++)
                    {
                        buttonArray[i].IsEnabled = false;
                        buttonArray[i].IsVisible = false;
                        buttonArray[i].TextColor = Color.Gray;
                    }
                    backButton.IsEnabled = false; backButton.TextColor = Color.Gray;
                    submitButton.IsEnabled = false; submitButton.TextColor = Color.Gray;
                    text.Text = "GAME OVER";
                    AppendMissingWords();
                    text.TextColor = Color.Red;
                    ChangeToReset();
                }
            }
        }
      
        //  Adds two vowels guarenteed to every letter sets to insure we have more than 0 words
        public String GenerateLetterSet()
        {
            String[] vowels = { "A", "E", "I", "O", "U" };
            String vowel1 = vowels[new Random().Next(5)];
            String vowel2 = vowels[new Random().Next(5)];
            String totalString = "";
            totalString += vowel1 + vowel2;
            //  Give us a random capital letter between 'A' and 'Z'
            for (int i = 0; i < 4; i++)
            {
               totalString += (char)((int)'A' + new Random().Next(26));
            }
            return totalString;


        }

        public void AppendMissingWords()
        {
            wordList.Text += " Missed Words:";
            foreach(var x in allWords)
            {
                if (!wordsFoundHashed.Contains(x))
                {
                    wordList.Text += " " + x;
                }
            }
        }
            //  This generates all of our buttons with letter labels and gives them a listener
            public void GenerateButtons(String letters) { 
                for (int i = 0; i<buttonArray.Length; i++)
                { 
                    buttonArray[i] = new Button { Text = "" + letters[i], BackgroundColor = Color.LightGoldenrodYellow
                };
                buttonArray[i].Clicked += OnClicked; //  The eventListener for any of the available button clicks
            }
            }

        //  Actually resetting the game
        private void Reset()
        {
            resetButton.IsVisible = false;
            resetButton.IsEnabled = false;
            foreach (var button in buttonArray)
            {
                button.IsVisible = true;
                button.IsEnabled = true;
            }
            submitButton.IsVisible = true;
            submitButton.IsEnabled = true;
            backButton.IsVisible = true;
            backButton.IsEnabled = true;

            String letters = GenerateLetterSet();
            for(int i = 0; i < letters.Length; i++)
            {
                buttonArray[i].Text = letters.Substring(i, 1);
                buttonArray[i].IsEnabled = true;
                buttonArray[i].IsVisible = true;
                buttonArray[i].TextColor = Color.Black;
            }

            timer.TextColor = Color.Black;
            timer.Text = "2:00";
            finished = false;
            countedSeconds = 120;
            _ = FakeTimer();
            wordList.Text = "";

            allWords.Clear();
            allWordsHashed.Clear();
            wordsFoundHashed.Clear();


            int numOfCombinations = 0;
            for (int i = 1; i < letters.Length + 1; i++)
            {
                //  Generate every possible wordset going from 1 - 6 characters 
                List<String> words = eg.AvailableWords(letters, i);

                for (int j = 0; j < words.Count; j++)
                {
                    ++numOfCombinations;
                    allWords.Add(words[j]);
                    allWordsHashed.Add(words[j]);
                }

            }
            numOfFinds.Text = ("0/" + numOfCombinations);
            text.Text = "";
            text.TextColor = Color.Black;
        }
        //  This method displays a big reset button on the screen
        private void ChangeToReset()
        {
            
            foreach(var button in buttonArray)
            {
                button.IsVisible = false;
                button.IsEnabled = false;
            }
            submitButton.IsVisible = false;
            submitButton.IsEnabled = false;
            backButton.IsVisible = false;
            backButton.IsVisible = false;
            resetButton.IsVisible = true;
            resetButton.IsEnabled = true;
        }
        private void OnClicked(object sender, EventArgs e)
        {
            //  Check the button is a letter
            Button sent = (Button)sender;
            if (sent.Text.Length == 1 && sent.TextColor != Color.Gray) {
                //  Add to the total shown text
                text.Text += sent.Text;
                //  Disable the button
                sent.TextColor = Color.Gray;
                sent.IsEnabled = false;
                //  Add it to our impromptu stack
                buttonsPressed.Push(sent);
            }
            //  Check if the button is a backspace hit 
            else if(sent.Text == "Backspace" && text.Text.Length != 0)
            {
                //  Remove the button from our 'Stack' and get rid of the last letter
                Button buttonRemoved = (Button)buttonsPressed.Pop();
                text.Text = text.Text.Substring(0, text.Text.Length - 1);
                //  Re-enable the button
                buttonRemoved.TextColor = Color.Black;
                buttonRemoved.IsEnabled = true;   
            }
            // Check if the button is a Submit hit
            else if(sent.Text == "Submit")
            {
                //  Add the word if its legal and we haven't used it yet. Increment our word counter                
                if (eg.IsLegal(text.Text) && allWordsHashed.Contains(text.Text) && !wordsFoundHashed.Contains(text.Text)) 
                {
                    numOfFinds.Text = (Int32.Parse(numOfFinds.Text.Substring(0,numOfFinds.Text.IndexOf('/'))) + 1) + numOfFinds.Text.Substring(numOfFinds.Text.IndexOf('/'));
                    wordList.Text += " " + text.Text;
                    wordsFoundHashed.Add(text.Text);
                }
                //  Clear the submit box
                text.Text = "";
                //  Unpress all the buttons
                while(buttonsPressed.Count != 0)
                {
                    Button buttonRemoved = (Button)buttonsPressed.Pop();
                    buttonRemoved.TextColor = Color.Black;
                    buttonRemoved.IsEnabled = true;
                }
                //  Execute special conditions when if we win the game
                if (numOfFinds.Text.Substring(0, numOfFinds.Text.IndexOf('/')) == numOfFinds.Text.Substring(numOfFinds.Text.IndexOf('/') + 1)){
                    for (int i = 0; i < buttonArray.Length; i++)
                    {
                        buttonArray[i].IsEnabled = false;
                        buttonArray[i].TextColor = Color.Gray;
                    }
                    backButton.IsEnabled = false; backButton.TextColor = Color.Gray;
                    submitButton.IsEnabled = false; submitButton.TextColor = Color.Gray;
                    text.Text = "YOU WIN";
                    text.TextColor = Color.Green;
                    finished = true;
                    ChangeToReset();

                }



            }
            else if(sent.Text == "Reset"){
                Reset();
            }

        }
    }
}
