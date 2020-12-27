using SQLite;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Essentials;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace PatientSymptoms
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class EditPage : ContentPage
    {
        //  All available parts as pre-chosen by me
        String[] masterSet = {"N/A", "Left Arm", "Right Arm", "Left Leg", "Right Leg", "Head",
                        "Left Hand", "Right Hand", "Left Foot", "Right Foot", "Left Kidney",
                        "Right Kidney", "Left Wrist", "Right Wrist", "Pancreas", "Lower Back",
                        "Upper Back", "Neck", "Chest", "Lower Stomach", "Upper Stomach" };
        //  The Database we are using to stash all this information
        private string DBName = "Pain.db";
        //  A value to determine which entry we are editing 
        int entryNum = 0;
        //  Instance Variables to be captured and edited later
        String dateVal = "";
        String timeVal = "";
        String painArea = "";
        int painRating = 0;
        String temperature = "";
        String barometricPressure = "";
        List<String> allTrackedParts = new List<String>();
        //  The connection we need to make to the SQLLite DB
        public SQLiteConnection conn;
        
        //  The workhorse to connect us to the DB and make edits
        public void OpenConnection(int x)
        {
            //  Grab the correct path location to get us to the Database file
            string libFolder = FileSystem.AppDataDirectory;
            string fname = System.IO.Path.Combine(libFolder, DBName);
            conn = new SQLiteConnection(fname);
            //  Create the Table if we somehow got here when we weren't supposed to
            conn.CreateTable<Pain>();
            
            //  A LINQ query that gets us just the entry we need that matches the one we selected
            var allRecords = from painCase in conn.Table<Pain>()
                             where painCase.Id == x
                             select painCase;
            //  Grab all currently saved values from the DB
            dateVal = allRecords.First().Date;
            timeVal = allRecords.First().Time;
            painArea = allRecords.First().PainArea;
            painRating = allRecords.First().PainRating;
            temperature = allRecords.First().Temperature;
            barometricPressure = allRecords.First().BarometricPressure;
            //  Set initial index for pain rating
            EditPainSelection.SelectedIndex = painRating;
            //  Fill our picker with all the body parts from the master set
            for (int i = 0; i < masterSet.Length; i++)
            {
                    allTrackedParts.Add(masterSet[i]);
            }
            //  Set our picker to use the master set
            EditConditionList.ItemsSource = allTrackedParts;
            //  Set our default pain area to the correct option
            EditConditionList.SelectedItem = painArea;

            //  Reformat the saved date string to set displayed date to the right value
            String[] datePieces = dateVal.Split('-');
            EditDate.Date = new DateTime(Int32.Parse(datePieces[2]), Int32.Parse(datePieces[0]), Int32.Parse(datePieces[1]));
            //  Same for the time
            String[] timePieces = timeVal.Split(new char[] { ':', ' ' });
            if(timePieces[2] == "PM" &&  (Int32.Parse(timePieces[0])) != 12) {
                timePieces[0] = "" + (Int32.Parse(timePieces[0]) + 12);
            };
            if(timePieces[2] == "AM" && (Int32.Parse(timePieces[0]) == 12)){
                timePieces[0] = "" + (Int32.Parse(timePieces[0]) - 12);
            }
            EditTime.Time = new TimeSpan(Int32.Parse(timePieces[0]), Int32.Parse(timePieces[1]), 0);
            EditTemp.Text = temperature;
            EditPressure.Text = barometricPressure;
            
            
        }
        public EditPage(int x)
        {
            //  Set our entry number, and load the DB with OpenConnection()
            InitializeComponent();
            entryNum = x;
            OpenConnection(x);
        }
        //  If we hit the Update Button, we want to update the record and save it in the DB
        private void Update_Clicked(object sender, EventArgs e)
        {
            //  A LINQ query to get us the right record from the Database
            var allRecords = from painCase in conn.Table<Pain>()
                             where painCase.Id == entryNum
                             select painCase;
            //  We change the record's values to match what the user has currently set them to
            var updatingEntry = allRecords.First();
            updatingEntry.PainArea = (String)EditConditionList.SelectedItem;
            updatingEntry.PainRating = Int32.Parse((string)EditPainSelection.SelectedItem);
            DateTime time = DateTime.Today.Add(EditTime.Time);
            updatingEntry.Time = time.ToShortTimeString();
            updatingEntry.Date = EditDate.Date.ToString("MM-dd-yyyy");
            updatingEntry.Temperature = EditTemp.Text;
            updatingEntry.BarometricPressure = EditPressure.Text;
            //  Update the entry
            conn.Update(updatingEntry);
        }

        private void Delete_Clicked(object sender, EventArgs e)
        {
            //  Find the entry we are on in the database
            var allRecords = from painCase in conn.Table<Pain>()
                             where painCase.Id == entryNum
                             select painCase;
            //  Delete that entry
            conn.Delete(allRecords.First());
            //  Back out
            Navigation.PopAsync();

        }
    }
}