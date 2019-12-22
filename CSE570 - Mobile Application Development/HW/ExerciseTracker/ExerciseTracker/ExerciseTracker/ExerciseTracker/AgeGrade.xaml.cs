using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Essentials;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace ExerciseTracker
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class AgeGrade : ContentPage
    {
        //  A 2D matrix to store men and women's records
        TimeSpan[,] mensScores;
        TimeSpan[,] womensScores;
        //  A list of all our hour, minute, second option
        public List<int> hoursList;
        public List<int> minutesList;
        public List<int> secondsList;
        //  For use in our initial bindings
        public IList<int> Hours { get; private set; }
        public IList<int> Minutes { get; private set; }
        public IList<int> Seconds { get; private set; }
        //  Returns for our initial binding, which becomes obsolete later. This was done
        //  Primarily to test the limits of the binding functionality
        public string GroupCalculation
        {
            get
            {
                return UpdateAge();
            }
            set
            {

            }
        }
        public string GradeCalculation
        {
            get
            {
                return UpdateAgeGrade();  
            }
            set
            {

            }
        }
        //  When we come back to this page, we want to update the information
        protected override void OnAppearing()
        {
            UpdateAge();
            UpdateAgeGrade();

        }
        //  Update the currently displayed Age Group
        public string UpdateAge()
        {
            //  Grab the Age 
            int age = GetAge();

            if (age < 0)
            {
                //  If our age is negative, display N/A
                AgeGroup.Text = "N/A";
                return "N/A";
            }
            else
            {
                //  This gives us the age bracket, multiplying an index by 5 and adding to the base of 40
                int ageBracket = (AgeBracket(GetAge()) - 1) * 5 + 40;
                //  If Get Age returns an index of 0, we will get an ageBracket of 35
                //  This means the user is under 40, which is as far as we care about
                if(ageBracket == 35)
                {
                    //  The person is younger than 40
                    AgeGroup.Text = "< 40";
                }
                else
                {
                    //  Aside from the weird case, return our Age Bracket
                    AgeGroup.Text = "" + ageBracket;
                }
               
                return "" + ageBracket;
            }

        }
        public string UpdateAgeGrade()
        {
            //  If nothing has been set yet, don't crash the sucker
            if (raceHour.SelectedItem == null || raceMinutes.SelectedItem == null || raceSeconds.SelectedItem == null)
            {
                AgeGra.Text = "N/A";
                return "N/A";
            }
            //  If we have some invalid values, display N/A
            if (GetAge() < 0 || raceHour.SelectedItem.ToString() == "0" && raceMinutes.SelectedItem.ToString() == "0" && raceSeconds.SelectedItem.ToString() == "0")
            {
                AgeGra.Text = "N/A";
                return "N/A";
            }
            else
            {
                //  We save some values so we can perform the necessary calculations  
                int ageBracket = AgeBracket(GetAge());
                int enteredHour;
                int enteredMinute;
                int enteredSeconds;
                //  If we are fresh-booting, set the values to 0
                if (raceHour.SelectedItem.ToString() == null || (string)raceMinutes.SelectedItem.ToString() == null || (string)raceSeconds.SelectedItem.ToString() == null)
                {
                    enteredHour = 0;
                    enteredMinute = 0;
                    enteredSeconds = 0;
                }
                else
                {
                    //  Otherwise, grab what is present and convert them to whole ints
                    enteredHour = Int32.Parse(raceHour.SelectedItem.ToString());
                    enteredMinute = Int32.Parse(raceMinutes.SelectedItem.ToString());
                    enteredSeconds = Int32.Parse(raceSeconds.SelectedItem.ToString());
                }
                //  Create a TimeSpan object based on their inputs
                TimeSpan enteredTime = new TimeSpan(enteredHour, enteredMinute, enteredSeconds);
                //  Grab the total amount of time in the form of seconds
                double enteredTotalSeconds = enteredTime.TotalSeconds;
                //  If the person is a male, use the male data
                if (Preferences.Get("Gender", "Male") == "Male")
                {
                    //  Grab the world record from the mensScores array
                    TimeSpan worldRecord = mensScores[raceType.SelectedIndex, ageBracket];
                    //  If the worldRecord is not a valid time, or is n/a, display n/a
                    if (worldRecord.Ticks == -1)
                    {
                        AgeGra.Text = "N/A";
                        return "N/A";
                    }
                    //  get the totalseconds of the world record
                    double worldRecordSeconds = worldRecord.TotalSeconds;
                    //  Round it to 2 decimals
                    double ageGrade = Math.Round(100 * (worldRecordSeconds / enteredTotalSeconds), 2);
                    //  Set the text to the correct value
                    AgeGra.Text = "" + ageGrade;
                    return "" + ageGrade;
                }
                else
                {
                    //  Grab the women's record for a given age Bracket
                    TimeSpan worldRecord = womensScores[raceType.SelectedIndex, ageBracket];
                    //  If it's an N/A, display N/A
                    if (worldRecord.Ticks == -1)
                    {
                        AgeGra.Text = "N/A";
                        return "N/A";
                    }
                    //  Grab the totalWorldRecordSeconds
                    double worldRecordSeconds = worldRecord.TotalSeconds;
                    //  Round the age grade to two decimals
                    double ageGrade = Math.Round(100 * (worldRecordSeconds / enteredTotalSeconds), 2);
                    //  Change the age grade label
                    AgeGra.Text = "" + ageGrade;
                    return "" + ageGrade;
                }

            }

        }
        public AgeGrade()
        {
            // Fill our picker with the correct  HOURS/MINUTES/SECONDS
            FillPickerElements();
            //  Store all the world record data
            StoreRunningData();
            //  Create our Lists for Hours, Minutes, and Seconds for use in the binding
            Hours = new List<int>();
            Minutes = new List<int>();
            Seconds = new List<int>();
            foreach(int i in hoursList)
            {
                Hours.Add(i);
            }
            foreach (int i in minutesList)
            {
                Minutes.Add(i);
            }
            foreach (int i in secondsList)
            {
                Seconds.Add(i);
            }
            //  Draw the UI component
            InitializeComponent();
            //  Set our initial grade equal to our Age Grade calculation
            AgeGra.Text = GradeCalculation;
            //  Establish this as the binding context so we can easily bind our picker
            BindingContext = this;
            //  Set all our selected indexes to 0
            raceHour.SelectedIndex = 0;
            raceMinutes.SelectedIndex = 0;
            raceSeconds.SelectedIndex = 0;
        }
        
        //  Load the Picker Elements
        public void FillPickerElements()
        {
            //  Generates hours   0 - 99
            //  Generates minutes 0 - 59
            //  Generates seconds 0 - 59
            hoursList   = new List<int>();
            minutesList = new List<int>();
            secondsList = new List<int>();
            for(int i = 0; i < 99; i++)
            {
                if(i > 59)
                {
                    hoursList.Add(i);
                }
                else
                {
                    hoursList.Add(i);
                    minutesList.Add(i);
                    secondsList.Add(i);
                }
            }

        }
        //  Grab the Age given the currently selected Birth Date
        public int GetAge()
        {
            //  Grab the birthday from preferences
            DateTime birthday = Preferences.Get("DOB", DateTime.UtcNow);
            //  Grab the current time
            DateTime now = DateTime.UtcNow;
            //  Calculate the age
            var age = now.Year - birthday.Year;
            //  We cannot forget to account for leap years, and later days in a month
            if (birthday.Date > now.AddYears(-age))
            {
                age--;
            }
            return age;

        }
        //  This method reads in the data from mens.txt and womens.txt 
        public void StoreRunningData()
        {
            //  Constructs our read stream using the proper means
            var assembly = IntrospectionExtensions.GetTypeInfo(typeof(MainPage)).Assembly;
            Stream stream = assembly.GetManifestResourceStream("ExerciseTracker.mens.txt");
            //  Our two arrays for storing records
            mensScores = new TimeSpan[5, 15];
            womensScores = new TimeSpan[5, 15];
            //  Read from the stream
            using (StreamReader readIn = new StreamReader(stream))
            {
                //  Grab row, and stash a storage variable
                int row = 0;
                string data = "";
                readIn.ReadLine();
                //  While we aren't at the end
                while (!readIn.EndOfStream)
                {
                    //  Grab the whole line 
                    data = readIn.ReadLine();
                    //  Use a helper method to stash the whole row
                    TimesProcessing(ref mensScores, row, data);
                    //  Increment the row
                    row++;
                }
                readIn.Close();
            }
            //  Open the second 
            Stream stream2 = assembly.GetManifestResourceStream("ExerciseTracker.womens.txt");
            //  Read the stream in for the women's text file
            using (StreamReader readIn = new StreamReader(stream2))
            {
                //  Saves row and data 
                int row = 0;
                string data = "";
                //  Read in the first line of garbage headers
                readIn.ReadLine();
                while (!readIn.EndOfStream)
                {
                    //  Read each line, use the helper to load a given row
                    data = readIn.ReadLine();
                    TimesProcessing(ref womensScores, row, data);
                    row++;
                }
                readIn.Close();
            }
        }
        public void TimesProcessing(ref TimeSpan[,] scores, int row, string data)
        {
            //  Strips a given string into column values
            String[] timesArray = data.Split(',');
            for(int i = 0; i < timesArray.Length - 1; i++)
            {
                //  inserts into the row at the correct positions
                scores[row, i] = TimeParser(timesArray[i + 1]);
            }
        }

        //  This was used before I realized that TimeSpan had a parse
        TimeSpan TimeParser(String inputTime)
        {
            //  This lets us know in the case of n/a world records that a given world record is n/a
            if (inputTime == "n/a") return new TimeSpan(-1);
            int colonCount = 0;
            int dotCount = 0;
            //  Checks if we have minutes,seconds,milliseconds or hours,minutes or hours,minutes, seconds
            foreach(char ch in inputTime)
            {
                if (ch == ':') colonCount++;
                else if (ch == '.') dotCount++;
            }
            // In the case of hours:minutes:seconds
            if(colonCount == 2)
            {     
                String[] fullTime = inputTime.Split(':');
                int hours = Int32.Parse(fullTime[0]);
                int minutes = Int32.Parse(fullTime[1]);
                int seconds = Int32.Parse(fullTime[2]);
                return new TimeSpan(hours, minutes, seconds);
            }
            else
            {
                // Use for minutes:seconds:millseconds format
                if(dotCount > 0)
                {

                    String[] fullTime = inputTime.Split((new[] { ':', '.' }));
                    int minutes = Int32.Parse(fullTime[0]);
                    int seconds = Int32.Parse(fullTime[1]);
                    int milliseconds = Int32.Parse(fullTime[2]);
                    return new TimeSpan(0, 0, minutes, seconds, milliseconds);
                }
                //  Use for minutes:seconds
                else
                {
                    String[] fullTime = inputTime.Split(':');
                    int minutes = Int32.Parse(fullTime[0]);
                    int seconds = Int32.Parse(fullTime[1]);
                    return new TimeSpan(0, minutes, seconds);
                }
            }
            
        }
        //  Age Bracket gives us the index that maps to a given world record given the brackets
        public int AgeBracket(int age)
        {
            if(age < 40)
            {
                return 0;
            }
            else if(age >= 40 && age < 45)
            {
                return 1;
            }
            else if (age >= 45 && age < 50)
            {
                return 2;
            }
            else if (age >= 50 && age < 55)
            {
                return 3;
            }
            else if (age >= 55 && age < 60)
            {
                return 4;
            }
            else if (age >= 60 && age < 65)
            {
                return 5;
            }
            else if (age >= 65 && age < 70)
            {
                return 6;
            }
            else if (age >= 70 && age < 75)
            {
                return 7;
            }
            else if (age >= 75 && age < 80)
            {
                return 8;
            }
            else if (age >= 80 && age < 85)
            {
                return 9;
            }
            else if (age >= 85 && age < 90)
            {
                return 10;
            }
            else if (age >= 90 && age < 95)
            {
                return 11;
            }
            else if (age >= 95 && age < 100)
            {
                return 12;
            }
            else // Age greater than or equal to 100
            {
                return 13;
            }
        }
        //  If we update a control, update Age and Age Grade
        private void RaceType_SelectedIndexChanged(object sender, EventArgs e)
        {
            UpdateAge();
            UpdateAgeGrade();
        }
    }
}