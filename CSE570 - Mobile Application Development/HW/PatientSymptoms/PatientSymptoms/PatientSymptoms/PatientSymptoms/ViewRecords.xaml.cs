using SQLite;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Essentials;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace PatientSymptoms
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class ViewRecords : ContentPage
    {
        //  The page that will be pushed when we are ready to update or edit
        EditPage ep;
        //  The name of our Database
        private string DBName = "Pain.db";
        //  What allows us to connect to our database
        public SQLiteConnection conn;
        public void OpenConnection()
        {
            //   Navigates us to the correct database file
            string libFolder = FileSystem.AppDataDirectory;
            string fname = System.IO.Path.Combine(libFolder, DBName);
            conn = new SQLiteConnection(fname);
            conn.CreateTable<Pain>();
            //  Grabs the desired fields we want to present in the ListView
            var allRecords = from painCase in conn.Table<Pain>()
                             select painCase.Date + " " + painCase.Time + " Record: " + painCase.Id;
            //  Set the source to what it needs to be 
            SelectionMenu.ItemsSource = allRecords;
        }

        public ViewRecords()
        {
            InitializeComponent();
            OpenConnection();
        }

        private void SelectionMenu_ItemTapped(object sender, ItemTappedEventArgs e)
        { 
            ListView currentLV = (ListView)sender;
            //  Push an instance of an Edit Page on with the ID of the item in the Database to be used later
            ep = new EditPage( Int32.Parse(((string)currentLV.SelectedItem).Split(' ')[4]));
            Navigation.PushAsync(ep, true);
            
        }
        protected override void OnAppearing()
        {
            OpenConnection();

        }
    }
}