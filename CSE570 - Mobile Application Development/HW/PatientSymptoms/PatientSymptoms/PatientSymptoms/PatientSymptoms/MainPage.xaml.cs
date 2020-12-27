using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace PatientSymptoms
{
    // Learn more about making custom code visible in the Xamarin.Forms previewer
    // by visiting https://aka.ms/xamarinforms-previewer
    [DesignTimeVisible(false)]
    public partial class MainPage : ContentPage
    {
        ReportPain rpPage;
        ViewRecords vrPage;
        GenerateReport grPage;
        TrackingPreferences tpPage;
        

        public MainPage()
        {
            InitializeComponent();
            NavigationPage.SetBackButtonTitle(this, "Home");
            //  Give us some room for the title bar
            this.Padding = new Thickness(0, 21, 0, 0);
        }

        private async void ReportPainB_Clicked(object sender, EventArgs e)
        {
            //  If someone clicks the Report Pain Button, we take them to the right page
            rpPage = new ReportPain();
            await Navigation.PushAsync(rpPage, true);
        }

        private async void ViewRecordsB_Clicked(object sender, EventArgs e)
        {
            //  If someone clicks the View Records Button, we take them to the right page
            vrPage = new ViewRecords();
            await Navigation.PushAsync(vrPage, true);
        }

        private async void GenerateReportB_Clicked(object sender, EventArgs e)
        {
            //  If someone clicks the Generate Report Button, we take them to the right page
            grPage = new GenerateReport();
            await Navigation.PushAsync(grPage, true);
        }

        private async void PartTrackerB_Clicked(object sender, EventArgs e)
        {
            //  If someone clicks the Preferences button, we take them to the right page
            tpPage = new TrackingPreferences();
            await Navigation.PushAsync(tpPage, true);
        }
    }
}
