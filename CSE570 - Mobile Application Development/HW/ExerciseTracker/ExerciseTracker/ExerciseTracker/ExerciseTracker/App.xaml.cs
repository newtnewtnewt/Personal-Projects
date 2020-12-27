using System;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using ExerciseTracker;
using Xamarin.Essentials;


namespace ExerciseTracker
{
    public partial class App : Application
    {
        public App()
        {
            InitializeComponent();

            MainPage = new MainPage();
        }

        protected override void OnStart()
        {
            // Handle when your app starts
        }

        protected override void OnSleep()
        {
        }

        protected override void OnResume()
        {
        }
    }
}
