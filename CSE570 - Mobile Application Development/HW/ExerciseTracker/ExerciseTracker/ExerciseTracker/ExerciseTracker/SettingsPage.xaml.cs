using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using Xamarin.Essentials;
using System.Diagnostics;

namespace ExerciseTracker
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class SettingsPage : ContentPage
    {
        public SettingsPage()
        {
            InitializeComponent();
            //  Set the gender to our gender preference, or male by default 
            GenderPicker.SelectedIndex = Preferences.Get("Gender", "Male") == "Male" ?  0 : 1;
            // Set the date to the preferred birthday, or today's date if not provided
            DateOfBirth.Date = Preferences.Get("DOB", DateTime.Today);
        }
        //  If we hit the settings button, Open a browser to miami's page
        private void Button_Clicked(object sender, EventArgs e)
        {
            Device.OpenUri(new Uri("http://www.miamiOH.edu"));
        }
        //  Change DOB according to picker selection
        private void DatePicker_DateSelected(object sender, DateChangedEventArgs e)
        {
            DatePicker datePicker = (DatePicker)sender;
            Preferences.Set("DOB", datePicker.Date);
        }
        //  Change Gender according to preference selection
        private void Picker_SelectedIndexChanged(object sender, EventArgs e)
        {
            Picker picker = (Picker)sender;
            Preferences.Set("Gender", (string)picker.SelectedItem);
            Debug.WriteLine(Preferences.Get("Gender", "Male"));
        }
    }
}