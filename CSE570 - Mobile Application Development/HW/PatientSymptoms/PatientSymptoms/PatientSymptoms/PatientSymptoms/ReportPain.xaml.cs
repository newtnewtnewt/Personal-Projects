using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Essentials;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using SQLite;
using System.Diagnostics;
using System.Net.Http;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace PatientSymptoms
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    //  This is how we build the SCHEMA for our PainReports table
    [Table("PainReports")]
    public class Pain
    {
        [PrimaryKey, AutoIncrement, Column("id")]
        public int Id { get; set; }
        [MaxLength(250), Column("Date")]
        public String Date { get; set; }

        [MaxLength(250), Column("Time")]
        public String Time { get; set; }

        [MaxLength(250), Column("PainArea")]
        public String PainArea { get; set; }

        [MaxLength(2), Column("PainRating")]
        public int PainRating { get; set; }

        [MaxLength(10), Column("Temperature")]
        public String Temperature { get; set; }

        [MaxLength(10), Column("BarometricPressure")]
        public String BarometricPressure { get; set; }
        public override string ToString()
        {
            return string.Format("==============================\n" +
                                 "Pain Recorded On {0} at {1}\n" +
                                 "Area: {2} at a rating of {3} out of 10\n" + 
                                 "Temperature Outside: {4}\n" +
                                 "Barometric pressure: {5}\n" + 
                                 "==============================", 
                                 Date, Time, PainArea, PainRating, Temperature, BarometricPressure);
        }
    }

    public partial class ReportPain : ContentPage
    {
        //   The connection to our database
        SQLiteConnection conn;
        //   A list that will store what values we currently care about
        List<String> allTrackedParts = new List<String>();
        //  The endpoint for our REST server that we can ping
        public static string OpenWeatherMapEndpoint = "https://api.openweathermap.org/data/2.5/weather";
        //  The API Key that gives us access to be able to query
        public static string OpenWeatherMapAPIKey = "4b458973fac9a990ffc580b76ddb410e";
        //  Default values for the Pressure and Temperature Values
        public string barometricPressure = "Unavailable";
        public string temperature = "Unavailable";
        
        public ReportPain()
        {
            InitializeComponent();
            //  Build the connection to our table
            String libFolder = FileSystem.AppDataDirectory;
            String fname = System.IO.Path.Combine(libFolder, "Pain.db");
            conn = new SQLiteConnection(fname);
            conn.CreateTable<Pain>();

            //  Set our default index to prevent errors
            PainSelection.SelectedIndex = 0;
            //  Append all values set in Preferences
            for(int i = 1; i < 6; i++)
            {
                String part = Preferences.Get("trackedPart" + i, "N/A");
                if(part != "N/A")
                {
                    allTrackedParts.Add(part);
                }
            }
            //  Rebuild the picker
            ConditionList.ItemsSource = allTrackedParts;
            //  Set the first as the default
            ConditionList.SelectedIndex = 0;
        }

        
        public string CreateWeatherQuery(string cityName)
        {
            //   Build a full REST query
            string requestUri = OpenWeatherMapEndpoint;
            requestUri += $"?q={cityName}";
            requestUri += "&units=Imperial";  // Freedom units
            requestUri += $"&APPID={OpenWeatherMapAPIKey}";
            return requestUri;
        }
        public async Task<String> GetWeatherQueryResult()
        {
            //  Spin up our client
            HttpClient client = new HttpClient();
            string result = null;
            //  As long as our input isn't bull, we try it
            if (!string.IsNullOrWhiteSpace(LocationEntry.Text))
            {
                //  Clean it
                String cleanLocation = LocationEntry.Text.Trim();
                //  Prevent intentionally broken querying
                cleanLocation.Replace(" ", "+");
                //   Generate our full query
                string query = CreateWeatherQuery(LocationEntry.Text);
                //  Have a go
                try
                {
                    var response = await client.GetAsync(query);
                    if (response.IsSuccessStatusCode)
                    {
                        //  If it works, we want the temperature value and pressure value
                        result = await response.Content.ReadAsStringAsync();
                        dynamic d = JObject.Parse(result);
                        if (d.cod == 200)
                        {
                            barometricPressure = d.main.pressure + " hPA";
                            temperature = d.main.temp + " F";
                        }
                    }
                    else
                    {
                        //  Otherwise we can just say that it's unavailable
                        barometricPressure = "Unavailable";
                        temperature = "Unavailable";
                    }
                }
                catch (Exception ex)
                {
                    //   If the connection gets severed, set the data to unavailable
                    barometricPressure = "Unavailable";
                    temperature = "Unavailable";
                }
            }
            return result;
        }

        private async void Button_Clicked(object sender, EventArgs e)
        {
            //  Our submission requires grabbing Temp and Pressure if we can
            string submission = await GetWeatherQueryResult();
            //  Create an object based on the information we have
            Pain addPain = new Pain
            {
                Date = DateTime.Now.ToString("MM-dd-yyyy"),
                Time = DateTime.Now.ToShortTimeString(),
                PainArea = (String)ConditionList.SelectedItem,
                PainRating = Int32.Parse((String)PainSelection.SelectedItem),
                Temperature = temperature,
                BarometricPressure = barometricPressure
            };
            //  Insert that sucker into the Database
            conn.Insert(addPain);
            await Navigation.PopAsync();
        }

    }
    
}