using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using Xamarin.Essentials;
using System.IO;
using System.Diagnostics;

namespace ExerciseTracker
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class LogPage : ContentPage
    {
        public LogPage()
        {
            //  Draw the gui
            InitializeComponent();
            if(Preferences.Get("neverRun", true))
            {
                //  Generate the file to avoid crashes
                string libFolder = FileSystem.AppDataDirectory;
                string fname = System.IO.Path.Combine(libFolder, "data.txt");
                // Create the streamreader
                StreamWriter tempOut = new StreamWriter(fname);
                tempOut.Close();
            }
            Preferences.Set("neverRun", false);
            double tMiles = 0;
            //  Load the log on boot, pass in true if it's our initialization run
            LoadLog(true, ref tMiles);

        }
        //  When someone clicks the button, we want to add a log entry
        private void LogAdd_Clicked(object sender, EventArgs e)
        {
           //  We are going to try to parse what the user entered as a double
           double milesEntered = 0.0; 
           if(Double.TryParse(milesEntry.Text, out milesEntered) && milesEntered >= 0)
           {
                //  Read in from the area where we are saving all of our log runs
                string libFolder = FileSystem.AppDataDirectory;
                string fname = System.IO.Path.Combine(libFolder, "data.txt");
                double tMiles = 0.0;

                // Save today's date 
                DateTime today = DateTime.Today;
                
               //  Generate the log entry as a string
                String logString = today.ToString("d") + " " + milesEntered + " miles " + "\n";
                //  Add to our running total
                tMiles += milesEntered;
                //  Grab the whole existant log and append the new entry to the top
                logString += LoadLog(false, ref tMiles);
                //   Write our log to the file
                StreamWriter writeOut = new StreamWriter(fname);
                //  Close
                writeOut.Write(logString);
                writeOut.Close();
                //  Set the total mile text equal to the current mile total 
                totalMiles.Text = tMiles + " Miles Run";
                //  Update the log text
                mileLog.Text = logString;

            }
        }
        //  Load log reads in from our file, and saves the text as needed
        public string LoadLog(bool init, ref double tMiles)
        {
            //  Open the stream to our data storage file
            string libFolder = FileSystem.AppDataDirectory;
            string fname = System.IO.Path.Combine(libFolder, "data.txt");
            String logString = "";
            // Create the streamreader
            StreamReader readIn = new StreamReader(fname);

            //  While we can still read in
            while (!readIn.EndOfStream)
            {
                //  Get our total number of miles at the same time we read in every line 
                //  from file
                String[] totalLine = readIn.ReadLine().Split(' ');
                String date = totalLine[0];
                double miles = Double.Parse(totalLine[1]);
                String word = totalLine[2];
                tMiles += miles;
                logString += date + " " + miles + " " + word + '\n';
            }
            readIn.Close();
            //  If this is our first run, reset our text
            if (init) { 
                totalMiles.Text = tMiles + " Miles Run";
                mileLog.Text = logString;
            }
            return logString;



        }
    }
}