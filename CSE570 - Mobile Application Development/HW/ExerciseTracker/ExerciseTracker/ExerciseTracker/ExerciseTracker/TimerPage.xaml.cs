using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Essentials;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace ExerciseTracker
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class TimerPage : ContentPage
    {
        public TimerPage()
        {
            InitializeComponent();
            //  If the timer is running, make sure it kicks back on when we resume
            if(Preferences.Get("timerWorking", false))
            {
                StartTimer_Clicked(null, null);
            }
            else
            {
                //  Reset all our values
                stopTimer.IsEnabled = false;
                Preferences.Set("numOfSeconds", 0);
                timerTime.TextColor = Color.FromHex("#2f723e");
            }
            
        }
        //  Trigers when we hit the start button
        private void StartTimer_Clicked(object sender, EventArgs e)
        {
            //  Give us the option to stop
            stopTimer.IsEnabled = true;
            startTimer.IsEnabled = false;
            //  Save a val that let's us know the timer is on 
            Preferences.Set("timerWorking", true);
            //  Display a color change
            timerTime.TextColor = Color.Black;
            
            //  Our start timer uses a lambda to update number of seconds, and checks if the timer is still running

            Device.StartTimer(new TimeSpan(0, 0, 1), () => {
                //  Kill the timer if it's our preference has been disabled
                if(Preferences.Get("timerWorking", true)  == false)
                {
                    return false;
                }
                //  Grab the current number of seconds we are on
                int numOfSeconds = Preferences.Get("numOfSeconds", 0);
                //  Increment
                numOfSeconds++;
                //  Set our updated number of seconds
                Preferences.Set("numOfSeconds", numOfSeconds);
                //  Update the displayed time
                timerTime.Text = evaluateTime(numOfSeconds).ToString();
                //  Check if we are done or not
                return Preferences.Get("timerWorking", true);
            });
        }
        //  Could have done this with TimeSpan natively, but baked a custom method
        //  because I wasn't aware of native functionality
        private TimeSpan evaluateTime(int numOfSeconds)
        {
            int seconds = numOfSeconds % 60;
            int minutes = numOfSeconds / 60;
            int hours = 0;
            if (minutes > 59)
            {
                hours = minutes / 60;
                minutes = (hours * 60) - minutes;
            }
            TimeSpan ts = new TimeSpan(hours, minutes, seconds);
            return ts;

        }
        //  If we stop the timer, we want the time to stop running
        private void StopTimer_Clicked(object sender, EventArgs e)
        {
            //  Change the color back
            timerTime.TextColor = Color.FromHex("#2f723e");
            //  Disable the stop button, enable the start button
            stopTimer.IsEnabled = false;
            startTimer.IsEnabled = true;
            //  Disable the timer 
            Preferences.Set("timerWorking", false);


        }
       
    }
}