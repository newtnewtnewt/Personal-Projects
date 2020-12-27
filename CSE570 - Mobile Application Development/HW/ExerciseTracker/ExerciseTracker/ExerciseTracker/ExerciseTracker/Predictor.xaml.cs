using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace ExerciseTracker
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class Predictor : ContentPage
    {
        //  A series of lists for the initial binding model used for initial load
        public List<int> hoursList;
        public List<int> minutesList;
        public List<int> secondsList;
        public IList<int> Hours { get; private set; }
        public IList<int> Minutes { get; private set; }
        public IList<int> Seconds { get; private set; }
        public Predictor()
        {
            //  Fills our picker with desired elements
            FillPickerElements();
            //  Ensures our mostly obsolete binding loop saves all the correct values, 
            //  If we ever needed to modify ranges, we could do this easily
            Hours = new List<int>();
            Minutes = new List<int>();
            Seconds = new List<int>();
            foreach (int i in hoursList)
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
            //  Draw the component on the UI 
            InitializeComponent();
            //  Set our binding here when we initialize
            BindingContext = this;
            //  Set all initial time values to 0 
            predictHour.SelectedIndex = 0;
            predictMinutes.SelectedIndex = 0;
            predictSeconds.SelectedIndex = 0;
        }
        public void FillPickerElements()
        {
            //  Same as in AgeGrade
            hoursList = new List<int>();
            minutesList = new List<int>();
            secondsList = new List<int>();
            for (int i = 0; i < 99; i++)
            {
                if (i > 59)
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

        //  On any changes, gives us our expected race time
        private void PredictHour_SelectedIndexChanged(object sender, EventArgs e)
        {
            //  If we have blanks, we don't bother with computations
            if(DesiredRace.Text != "" && PreviousRace.Text != "")
            {
                double distancePrevious = 0.0;
                double distanceDesired = 0.0;
                //  Try to parse user inputs as doubles
                if (Double.TryParse(DesiredRace.Text, out distancePrevious) && Double.TryParse(PreviousRace.Text, out distanceDesired))
                {
                    //  Helper method to resolve Miles/Kilometers mismatches
                    EvaluateUnits(ref distancePrevious, ref distanceDesired);
                    //  Generate a TimeSpan based on input
                    TimeSpan previousRace = new TimeSpan(predictHour.SelectedIndex, predictMinutes.SelectedIndex, predictSeconds.SelectedIndex);
                    //  This helper uses a formula from the specified web page in order to calculate future run time based on previous
                    double predictedSeconds = PredictFutureTime(previousRace.TotalSeconds, distanceDesired, distancePrevious);
                    // If our predicted is negative, the user entered some garbaled input
                    if (predictedSeconds == -1)
                    {
                        outputHour.Text = "N/A";
                        outputMinutes.Text = "N/A";
                        outputSeconds.Text = "N/A";

                    }
                    //  Otherwise we can present the values we got from the predicted # of seconds
                    else
                    {
                        TimeSpan predictedRace = TimeSpan.FromSeconds(predictedSeconds);
                        outputHour.Text = "" + predictedRace.Hours;
                        outputMinutes.Text = "" + predictedRace.Minutes;
                        outputSeconds.Text = "" + predictedRace.Seconds;
                    }
                }
                else
                {
                    //  Garbled input 
                    outputHour.Text = "N/A";
                    outputMinutes.Text = "N/A";
                    outputSeconds.Text = "N/A";

                }
            }
            else
            {
                //  Garbled input 
                outputHour.Text = "N/A";
                outputMinutes.Text = "N/A";
                outputSeconds.Text = "N/A";
            }
        }
        //  This formula was grabbed from the specified site to predict a future race time
        //  Given an existant race time
        private double PredictFutureTime(double t1, double d1, double d2)
        {
            if(d1 == 0) { return -1; }
            return t1 * Math.Pow((d2 / d1), 1.06);
        }
        //  Evaluates our units based on which combination we have, and converts if necessary
        private void EvaluateUnits(ref double distancePrevious, ref double distanceDesired)
        {
            //  If they are not the same unit, convert both to miles
            if (PreviousRaceUnit.SelectedIndex != DesiredRaceUnit.SelectedIndex)
            {
                if(PreviousRaceUnit.SelectedIndex == 0)
                {
                    distancePrevious = distancePrevious * 0.621371;
                }
                else
                {
                    distanceDesired = distanceDesired * 0.621371;
                }
            }


        }
    }
}