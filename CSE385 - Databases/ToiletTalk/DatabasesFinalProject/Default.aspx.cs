using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using System.Web.Configuration;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace DatabasesFinalProject
{
    public partial class _Default : Page
    {
        public int results;

        public String userName;
        public int userId;
        
        public void Page_Load(object sender, EventArgs e)
        {
            userName = System.Web.HttpContext.Current.User.Identity.Name;
            String password = ""; //This can be removed from the database
            Boolean newUser = true;
            try
            {
                userName = userName.Split('@')[0];
                SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
                con.Open();
                SqlCommand cmd = new SqlCommand("SELECT userName FROM users", con);
                cmd.ExecuteNonQuery();

                using (SqlDataReader dr = cmd.ExecuteReader())
                {
                    while (dr.Read())
                    {
                        String user = dr["userName"].ToString();
                        if (user.Equals(userName)) newUser = false;
                    }
                }

                //If they are a new user, add them to the database.
                if (newUser)
                {
                    SqlCommand cmd2 = new SqlCommand("INSERT INTO users values(@userName, @pw)", con);
                    cmd2.Parameters.AddWithValue("userName", userName);
                    cmd2.Parameters.AddWithValue("pw", password);
                    cmd2.ExecuteNonQuery();
                }

                //Grab the users userId
                SqlCommand cmd3 = new SqlCommand("SELECT userId FROM users WHERE (userName = @userName)", con);
                cmd3.Parameters.AddWithValue("userName", userName);
                cmd3.ExecuteNonQuery();
                using (SqlDataReader dr = cmd3.ExecuteReader())
                {
                    dr.Read();
                    userId = Convert.ToInt32(dr["userId"]);
                }


            }
            catch(Exception ee){
            }
        }
        protected void GridView1_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if ((string)e.CommandName != "Sort")
            {
                int rowIndex = Convert.ToInt32(e.CommandArgument);
                GridViewRow row = GridView.Rows[rowIndex];
                Session.Add("userName", userName); // Saves the username to the current session
                Session.Add("userId", userId); // Saves the userId to the current session
                //This saves the restroomId to the user's current session
                Console.Write(GridView.DataKeys[rowIndex]["buildingName"]);
                Session.Add("restroomId", GridView.DataKeys[rowIndex]["restroomId"]);
                Session.Add("buildingName", GridView.DataKeys[rowIndex]["buildingName"]);
                Session.Add("floorNumber", GridView.DataKeys[rowIndex]["floorNumber"]);
                Session.Add("roomNumber", GridView.DataKeys[rowIndex]["roomNumber"]);
                Session.Add("roomType", GridView.DataKeys[rowIndex]["roomType"]);
                Response.Redirect("~/restroomPage.aspx");
            }
        }

        protected void ViewComments_Click(object sender, EventArgs e)
        {

        }
        protected void SubmitButton_Click(object sender, EventArgs e)
        {
            SqlDataSource1.SelectParameters.Clear();
            string searchStr = txtSearch.Text;
            searchStr = searchStr.Replace("'", "''");
            SqlDataSource1.SelectParameters.Add("staff", Staff.Checked.ToString());
            SqlDataSource1.SelectParameters.Add("male", Male.Checked.ToString());
            SqlDataSource1.SelectParameters.Add("female", Female.Checked.ToString());
            SqlDataSource1.SelectParameters.Add("unisex", Any.Checked.ToString());
            SqlDataSource1.SelectParameters.Add("searchStr", searchStr);
            SqlDataSource1.SelectCommand = "spGetRestroomsByType @staff, @male, @female, @unisex,@searchStr";
            SqlDataSource1.DataBind();
            results = GridView.PageCount;
            //GridView.DataBind();

        }

        protected void Male_Check(object sender, EventArgs e)
        {

        }

        protected void Female_Check(object sender, EventArgs e)
        {

        }

        protected void Any_Check(object sender, EventArgs e)
        {

        }

        protected void Staff_Check(object sender, EventArgs e)
        {

        }
    }
}