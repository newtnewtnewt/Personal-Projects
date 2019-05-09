using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Xml;

namespace WebApplication2
{
    public partial class _Default : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            lblData.Text = "";
            try
            {
                //XML Version
                XmlDocument doc1 = new XmlDocument();
                doc1.Load("https://ws.miamiOH.ed/person/" + TextBox1.Text);
                XmlNodeList doc = doc1.SelectNodes("personCollection/person");
                lblData.Text = "<h1>" + doc[0].SelectSingleNode("nameFirst").InnerText + "</h1>";

                //JSON Version
                using(var client = new WebClient())
                {
                    var json = client.DownloadString("https://ws.miamiOH.ed/person/" + TextBox1.Text);
                    dynamic dynJson = JsonConvert.DeserializeObject(json);
                    lblData.Text += "<h2>" + dynJson.personCollection[0].nameLast + "</h2>";

                }

            }
            catch (Exception ex) { }

        }
    }
}