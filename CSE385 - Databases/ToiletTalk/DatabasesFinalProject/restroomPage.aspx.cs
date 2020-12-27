using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Web.Configuration;
using System.Diagnostics;
using System.Data;

namespace DatabasesFinalProject
{
    public partial class restroomPage : System.Web.UI.Page
    {
        public string userName;
        public int userId;

        public int restroomId;
        public String buildingName;
        public String roomType;
        public int floorNumber;
        public String roomNumber;

        public int currentSinks;
        public int currentStalls;
        public int currentUrinals;

        public double currentReview;
        public double sinksReview;
        public double stallsReview;
        public double urinalsReview;

        int totalStallEntries;
        int totalUrinalEntries;
        int totalSinkEntries;
        public int totalEntries;

        public Boolean urinals;
        public Boolean enoughData;

        protected void Page_Load(object sender, EventArgs e)
        {
            try
            {
                userName = Session["userName"].ToString();
                userId = (int)Session["userId"];
                restroomId = (int)Session["restroomId"];
                buildingName = Session["buildingName"].ToString();
                floorNumber = (int)Session["floorNumber"];
                roomNumber = Session["roomNumber"].ToString();
                roomType = Session["roomType"].ToString();

                if (roomType.ToLower().Contains("wo")) urinals = false;
                else urinals = true;

                getValues(); //Runs the commands to get all the values for the page.
                calculateReviewScores();
                if(urinals)
                {
                    if (totalEntries >= 15) enoughData = true;
                    else enoughData = false;
                }
                else
                {
                    if (totalEntries >= 10) enoughData = true;
                    else enoughData = false;
                }
            }
            catch (Exception eee) {
                Page.Response.Redirect("~/Default.aspx", true);
            }
        }

        private void calculateReviewScores()
        {
            SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
            con.Open();
            SqlCommand cmd = new SqlCommand("SELECT stalls, urinals, sinks FROM ratings WHERE restroomId = @restroomId", con);
            cmd.Parameters.AddWithValue("restroomId", restroomId);
            cmd.ExecuteNonQuery();

            stallsReview = 0;
            urinalsReview = 0;
            sinksReview = 0;

            int totalStall = 0;
            int totalUrinal = 0;
            int totalSink = 0;

            using (SqlDataReader dr = cmd.ExecuteReader())
            {
                while(dr.Read())
                {
                    int stalls = -1;
                    int urinals = -1;
                    int sinks = -1;

                    try
                    {
                        stalls = Convert.ToInt32(dr["stalls"]);
                    }
                    catch (Exception ee) { }

                    try
                    {
                        urinals = Convert.ToInt32(dr["urinals"]);
                    }
                    catch (Exception ee) { }

                    try
                    {
                        sinks = Convert.ToInt32(dr["sinks"]);
                    }
                    catch (Exception ee) { }



                    if(stalls != -1)
                    {
                        stallsReview += stalls;
                        totalStall++;
                    }

                    if(urinals != -1)
                    {
                        urinalsReview += urinals;
                        totalUrinal++;
                    }

                    if(sinks != -1)
                    {
                        sinksReview += sinks;
                        totalSink++;
                    }
                    
                }
                if (totalStall > 0) stallsReview = ((double) stallsReview / totalStall);
                else stallsReview = 0;

                if (totalUrinal > 0) urinalsReview = ((double) urinalsReview / totalUrinal);
                else urinalsReview = 0;

                if (totalSink > 0) sinksReview = ((double) sinksReview / totalSink);
                else sinksReview = 0;

                if (stallsReview != 0 && sinksReview != 0 && urinalsReview != 0) currentReview = ((double) stallsReview + (double) sinksReview + (double) urinalsReview) / 3;
                else currentReview = 0;

                currentReview = Math.Round(currentReview, 2);
            }
        }

        private void getValues()
        {
            SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
            con.Open();
            SqlCommand cmd = new SqlCommand("SELECT stalls, urinals, sinks FROM floorPlans WHERE restroomId = @restroomId", con);
            cmd.Parameters.AddWithValue("restroomId", restroomId);
            cmd.ExecuteNonQuery();

            using (SqlDataReader dr = cmd.ExecuteReader())
            {
                totalStallEntries = 0;
                totalUrinalEntries = 0;
                totalSinkEntries = 0;

                List<int> stalls = new List<int>();
                List<int> urinals = new List<int>();
                List<int> sinks = new List<int>();

                while (dr.Read())
                {
                    int stallValue = Convert.ToInt32(dr["stalls"]);
                    int urinalValue = Convert.ToInt32(dr["urinals"]);
                    int sinkValue = Convert.ToInt32(dr["sinks"]);

                    if (stallValue > 0)
                    {
                        totalStallEntries++;
                        stalls.Add(stallValue);
                    }

                    if (urinalValue > 0)
                    {
                        totalUrinalEntries++;
                        urinals.Add(urinalValue);
                    }

                    if (sinkValue > 0)
                    {
                        totalSinkEntries++;
                        sinks.Add(sinkValue);
                    }
                }

                stalls.Sort();
                urinals.Sort();
                sinks.Sort();

                if (totalStallEntries < 5) currentStalls = -1;
                else currentStalls = getMode(stalls);

                if (totalUrinalEntries < 5) currentUrinals = -1;
                else currentUrinals = getMode(urinals);

                if (totalSinkEntries < 5) currentSinks = -1;
                else currentSinks = getMode(sinks);

                totalEntries = totalStallEntries + totalUrinalEntries + totalSinkEntries;
            }

        }

        private int getMode(List<int> list)
        {
            list.Sort((x, y) => x.CompareTo(y));
            int numTimes = 0;
            int currentNum = 0;
            int best = -2;
            int bestTimes = -2;
            int prev = -2;
            for (int i = 0; i < list.Count; i++)
            {
                currentNum = list.ElementAt(i);
                if(currentNum != prev || i == list.Count)
                {
                    if (i == list.Count && currentNum == prev)
                    {
                        numTimes++;
                    }
                    else if(currentNum != prev)
                    {
                        numTimes = 1;
                    }
                }
                if(currentNum == prev)
                {
                    numTimes++;
                }
                if (numTimes >= bestTimes)
                {
                    bestTimes = numTimes;
                    best = currentNum;
                }
                prev = currentNum;
            }
            return best;
        }


//        private void sort(List<int> list)
//        {
//            Boolean sorted = false;
//            list.Sort();
//            return; 
//            while(sorted == false)
//            {
//                sorted = true;
//                for(int i = 0; i < list.Count - 1; i++)
//                {
//                  if(list.ElementAt(i) > list.ElementAt(i + 1))
//                    {
//                        int temp = list.ElementAt(i);
//                        list.Remove(list.ElementAt(i));
//                        sorted = false;
//                        list.Add(temp);
//;                   }  
//                }
//            }
//            for (int i = 0; i < list.Count; i++)
//            {
//                Debug.Print(list.ElementAt(i) + " ");
//            }

//        }

        /*
         * This stores a user's comment in the table with the press of a submit button,
         * accounting for several cases including a rating with no comment, and a comment with no rating
         * 
         * */
        protected void Submit_Review_Click(object sender, EventArgs e)
        {
            SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
            con.Open();
            SqlCommand cmd = new SqlCommand("INSERT INTO ratings values(@rating, @stalls, @urinals, @sinks, @ratingComment, @restroomId, @userId)", con);
            cmd.Parameters.AddWithValue("rating", "1"); //This column can be removed from the database if we'd like.

            if (stallRatingDrop.SelectedIndex != 0) cmd.Parameters.AddWithValue("stalls", Convert.ToInt32(stallRatingDrop.SelectedValue));
            else cmd.Parameters.AddWithValue("stalls", -1);

            if (urinalRatingDrop.SelectedIndex != 0) cmd.Parameters.AddWithValue("urinals", Convert.ToInt32(urinalRatingDrop.SelectedValue));
            else cmd.Parameters.AddWithValue("urinals", -1);

            if (sinkRatingDrop.SelectedIndex != 0) cmd.Parameters.AddWithValue("sinks", Convert.ToInt32(sinkRatingDrop.SelectedValue));
            else cmd.Parameters.AddWithValue("sinks", -1);

            if (txtComment.Text != "") cmd.Parameters.AddWithValue("ratingComment", txtComment.Text);
            else cmd.Parameters.AddWithValue("ratingComment", "No Comment");

            cmd.Parameters.AddWithValue("restroomId", restroomId);
            cmd.Parameters.AddWithValue("userId", userId);

            cmd.ExecuteNonQuery();
            Page.Response.Redirect(Page.Request.Url.ToString(), true);
        }

        protected void Submit_Updates_Click(object sender, EventArgs e)
        {
            SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
            con.Open();
            SqlCommand cmd = new SqlCommand("INSERT INTO floorPlans values(@stalls, @urinals, @sinks, @floorPlanLink, @restroomId)", con);

            if(enoughData)
            {
                if (txtStallsE.Text != "") cmd.Parameters.AddWithValue("stalls", Convert.ToInt32(txtStallsE.Text));
                else cmd.Parameters.AddWithValue("stalls", -1);

                if (txtUrinalsE.Text != "") cmd.Parameters.AddWithValue("urinals", Convert.ToInt32(txtUrinalsE.Text));
                else cmd.Parameters.AddWithValue("urinals", -1);

                if (txtSinksE.Text != "") cmd.Parameters.AddWithValue("sinks", Convert.ToInt32(txtSinksE.Text));
                else cmd.Parameters.AddWithValue("sinks", -1);
            }
            else
            {
                if (txtStalls.Text != "") cmd.Parameters.AddWithValue("stalls", Convert.ToInt32(txtStalls.Text));
                else cmd.Parameters.AddWithValue("stalls", -1);

                if (txtUrinals.Text != "") cmd.Parameters.AddWithValue("urinals", Convert.ToInt32(txtUrinals.Text));
                else cmd.Parameters.AddWithValue("urinals", -1);

                if (txtSinks.Text != "") cmd.Parameters.AddWithValue("sinks", Convert.ToInt32(txtSinks.Text));
                else cmd.Parameters.AddWithValue("sinks", -1);
            }

            cmd.Parameters.AddWithValue("floorPlanLink", "null"); //This column can be removed from the database if we'd like.

            cmd.Parameters.AddWithValue("restroomId", restroomId);

            cmd.ExecuteNonQuery();
            Page.Response.Redirect(Page.Request.Url.ToString(), true);
        }
    }
}