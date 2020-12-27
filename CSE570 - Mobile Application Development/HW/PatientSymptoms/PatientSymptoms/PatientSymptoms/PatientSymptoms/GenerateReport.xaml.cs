using SQLite;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Essentials;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace PatientSymptoms
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class GenerateReport : ContentPage
    {
        //  The name of our Pain Database
        private string DBName = "Pain.db";
        //  Our SQL connection
        public SQLiteConnection conn;
        public void OpenConnection()
        {
            //  Create the correct path to our DB file
            string libFolder = FileSystem.AppDataDirectory;
            string fname = System.IO.Path.Combine(libFolder, DBName);
            conn = new SQLiteConnection(fname);
            //  Create the Table if it doesn't exist
            conn.CreateTable<Pain>();
            //  Grab the string representations of all our records
            var allRecords = from painCase in conn.Table<Pain>()
                             select painCase.ToString();
            //  Erase previous display
            RecordText.Text = "";
            //  Append all records onto the same page
            foreach (var record in allRecords)
            {
                RecordText.Text += record + "\n";
            }
        }
        public GenerateReport()
        {
            InitializeComponent();
            OpenConnection();
        }
    }
}