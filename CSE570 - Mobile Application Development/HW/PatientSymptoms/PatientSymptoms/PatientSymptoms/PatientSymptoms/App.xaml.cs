using System;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace PatientSymptoms
{
    public partial class App : Application
    {
        public App()
        {
            //  This is the standard setup for a Navigation Page
            InitializeComponent();
            Page mainPage = new MainPage();
            //  Here is where our initial page will land
            NavigationPage navPage = new NavigationPage(mainPage);
            //  Bar at the top set to a nice shade of red
            navPage.BarBackgroundColor = Color.FromHex("#FC4A4A");
            //  The color of the arrow
            navPage.BarTextColor = Color.AntiqueWhite;
            MainPage = navPage;
        }

        protected override void OnStart()
        {
            // Handle when your app starts
        }

        protected override void OnSleep()
        {
            // Handle when your app sleeps
        }

        protected override void OnResume()
        {
            // Handle when your app resumes
        }
    }
}
