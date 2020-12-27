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
    public partial class TrackingPreferences : ContentPage
    {
        //  We maintain a master set of 20 body parts that we may want to track
        String[] masterSet = {"N/A", "Left Arm", "Right Arm", "Left Leg", "Right Leg", "Head",
                        "Left Hand", "Right Hand", "Left Foot", "Right Foot", "Left Kidney",
                        "Right Kidney", "Left Wrist", "Right Wrist", "Pancreas", "Lower Back",
                        "Upper Back", "Neck", "Chest", "Lower Stomach", "Upper Stomach" };
        //  We maintain 5 copies for each of the areas
        String[] partsOptions1;
        String[] partsOptions2;
        String[] partsOptions3;
        String[] partsOptions4;
        String[] partsOptions5;
        List<String[]> allOptions = new List<string[]>();
        

        public TrackingPreferences()
        {
            InitializeComponent();
            //  Instantiate the fields
            partsOptions1 = new String[masterSet.Length];
            partsOptions2 = new String[masterSet.Length];
            partsOptions3 = new String[masterSet.Length];
            partsOptions4 = new String[masterSet.Length];
            partsOptions5 = new String[masterSet.Length];
            //  Make 5 copies of the master set
            Array.Copy(masterSet, partsOptions1, masterSet.Length);
            Array.Copy(partsOptions1, partsOptions2, partsOptions1.Length);
            Array.Copy(partsOptions1, partsOptions3, partsOptions1.Length);
            Array.Copy(partsOptions1, partsOptions4, partsOptions1.Length);
            Array.Copy(partsOptions1, partsOptions5, partsOptions1.Length);
            //  Mount the arrays to the pickers 
            partTrackerOne.ItemsSource   = partsOptions1;
            partTrackerTwo.ItemsSource   = partsOptions2;
            partTrackerThree.ItemsSource = partsOptions3;
            partTrackerFour.ItemsSource  = partsOptions4;
            partTrackerFive.ItemsSource  = partsOptions5;
            //  Throw all arrays in a List to make checks
            allOptions.Add(partsOptions1); allOptions.Add(partsOptions2);
            allOptions.Add(partsOptions3);
            allOptions.Add(partsOptions4); allOptions.Add(partsOptions5);
            //   Create our initial Preferences set
            partTrackerOne.SelectedIndex = getIndexOfPart(Preferences.Get("trackedPart1", "N/A"));
            partTrackerTwo.SelectedIndex = getIndexOfPart(Preferences.Get("trackedPart2", "N/A"));
            partTrackerThree.SelectedIndex = getIndexOfPart(Preferences.Get("trackedPart3", "N/A"));
            partTrackerFour.SelectedIndex = getIndexOfPart(Preferences.Get("trackedPart4", "N/A"));
            partTrackerFive.SelectedIndex = getIndexOfPart(Preferences.Get("trackedPart5", "N/A"));
        }
        //  This helper gives us the index of a part given that we know it's name
        public int getIndexOfPart(String partName)
        {
            if(partName == "N/A")
            {
                return 0;
            }
            //  A HashSet would have been more efficient, but this is for a small set N = 20
            for(int i = 0; i < masterSet.Length; i++)
            {
                if(masterSet[i] == partName)
                {
                    return i;
                }
            }
            return -1;

        }
        //  This method enables us to react to the user changing their preferneces, by adjusting based on a picker
        //  And the 5 arrays
        public void arrayChange(ref String[] changeArray, String trackedPart, ref Picker changePicker)
        {
           //   If the entry is labeled as Already Picked, We don't want to enable the user to pick it
           if ((string)changePicker.SelectedItem == "Already Picked")
           {
               changePicker.SelectedIndex = getIndexOfPart(Preferences.Get(trackedPart, "N/A"));
           }
           else
           {
               //  Else, we need to put the previous entry back into all the other 4 arrays
               String putEntryBack = Preferences.Get(trackedPart, "N/A");
               //  Grab the index
               int indexBack = getIndexOfPart(putEntryBack);
                //  If it's N/A we don't need to put anything back
                if (indexBack != 0)
                {
                    for (int i = 0; i < allOptions.Count; i++)
                    {
                        //  Set all those arrays back to having that option
                        if (allOptions[i] != changeArray)
                        {
                            allOptions[i][indexBack] = putEntryBack;

                        }

                    }
                }
                //  Put this entry back in the original array
                changeArray[indexBack] = putEntryBack;
                //  Set our new pick in Preference
                Preferences.Set(trackedPart, (string)changePicker.SelectedItem);
                //  Remove the selected entry from all the other arrays
               int indexRemoved = getIndexOfPart((string)changePicker.SelectedItem);
               if (indexRemoved != 0) {
                    for (int i = 0; i < allOptions.Count; i++)
                    {
                        //  Remove the entry from the other arrays
                        if (allOptions[i] != changeArray)
                        {
                            allOptions[i][indexRemoved] = "Already Picked";

                        }

                    }
                }
             
            }

        }

        private void PartTracker_SelectedIndexChanged(object sender, EventArgs e)
        {
            //  A helper for our picker selection that determines which tracked part we are changing
            Picker changePick = (Picker)sender;
            if (changePick == partTrackerOne)
            {
                arrayChange(ref partsOptions1, "trackedPart1", ref changePick);
            }
            else if (changePick == partTrackerTwo)
            {
                arrayChange(ref partsOptions2, "trackedPart2", ref changePick);
            }
            else if (changePick == partTrackerThree)
            {
                arrayChange(ref partsOptions3, "trackedPart3", ref changePick);
            }
            else if (changePick == partTrackerFour)
            {
                arrayChange(ref partsOptions4, "trackedPart4", ref changePick);
            }
            else if (changePick == partTrackerFive)
            {
                arrayChange(ref partsOptions5, "trackedPart5", ref changePick);
            }
        }
    }
}

/**
 *  This was a solution that was implemented, deemed to be user unfriendly, and left out
 *  It exists here as reference for a CheckBox solution to the above problem
 * 
 * 
 */
        

//        CheckBox[] allBoxes = new CheckBox[20];
//        int totalNumberChecked = Preferences.Get("totalNumberChecked", 0);
//        Color defaultBoxColor = new CheckBox().Color;

//        public TrackingPreferences()
//        { 
//            InitializeComponent();
//            Debug.WriteLine("Got here!");
//            allBoxes = new CheckBox[bodyParts.Length];
//            ScrollView sv = new ScrollView { };
//            StackLayout overallPage = new StackLayout { };
//            Label titlePlacard = new Label { Text = "Select Up to 5" };
//            overallPage.Children.Add(titlePlacard);

//            for (int i = 0; i < bodyParts.Length; i++)
//            {
//                StackLayout checkCluster = new StackLayout { Orientation = StackOrientation.Horizontal };
//                Label checkBoxLabel = new Label { Text = bodyParts[i], FontSize = 30 };
//                CheckBox checkBox = new CheckBox
//                {
//                    IsChecked = Preferences.Get(bodyParts[i], false),
//                    IsEnabled = (Preferences.Get(bodyParts[i], false) || totalNumberChecked < 5)
//                };
//                if (!checkBox.IsChecked && totalNumberChecked == 5)
//                {
//                    checkBox.Color = Color.Gray;
//                }
//                checkBox.CheckedChanged += PartSelected;
//                allBoxes[i] = checkBox;
//                checkCluster.Children.Add(checkBoxLabel);
//                checkCluster.Children.Add(checkBox);
//                overallPage.Children.Add(checkCluster);
//            }
//            sv.Content = overallPage;

//            this.Content = sv;
//        }

//        public void PartSelected(object sender, CheckedChangedEventArgs e)
//        {
//            CheckBox cb = (CheckBox) sender;
//            for(int i = 0; i < allBoxes.Length; i++)
//            {
//                if (allBoxes[i] == cb)
//                {
//                    if(!(Preferences.Get(bodyParts[i], false)))
//                    {
//                        Preferences.Set(bodyParts[i], true);
//                        totalNumberChecked++;
//                        Preferences.Set("selectedPart" + totalNumberChecked, bodyParts[i]);
//                        Debug.WriteLine("Clicked " + totalNumberChecked);
//                        Preferences.Set("totalNumberChecked", totalNumberChecked);
//                        if (totalNumberChecked == 5)
//                        {
//                            for(int j = 0; j < allBoxes.Length; j++)
//                            {
//                                if (!allBoxes[j].IsChecked)
//                                {
//                                    allBoxes[j].IsEnabled = false;
//                                    allBoxes[j].Color = Color.Gray;
//                                }
//                            }
                            
//                        }
//                    }
//                    else
//                    {
//                        Preferences.Set(bodyParts[i], false);
//                        Preferences.Set("selectedPart" + totalNumberChecked, "N/A");
//                        totalNumberChecked--;
//                        Debug.WriteLine("DeClicked " + totalNumberChecked);
//                        Preferences.Set("totalNumberChecked", totalNumberChecked);
//                        if (totalNumberChecked == 4)
//                        {
//                            for (int j = 0; j < allBoxes.Length; j++)
//                            {
//                                allBoxes[j].IsEnabled = true;
//                                allBoxes[j].Color = defaultBoxColor;
//                            }

//                        }
//                    }
//                }
//            }

//        }
//    }
//}