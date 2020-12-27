<%@ Page Language="C#" AutoEventWireup="true" MasterPageFile="~/Site.Master" CodeBehind="restroomPage.aspx.cs" Inherits="DatabasesFinalProject.restroomPage" %>

<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="main">
        <h1 class="center"><%= buildingName %></h1>
        <h2 class="center">Floor: <%= floorNumber %> Room: <%= roomNumber %> - <%= roomType %></h2>
        <hr />
        <%if (enoughData == true) {%>
        <div class="center">
            <div class="stats">
                <h3 class="padding">Number of Sinks: <%= currentSinks %></h3>
                <h3 class="padding">Number of Stalls: <%= currentStalls %></h3>
                <%if (urinals)
                { %>
                    <h3 class="padding"> Number of Urinals: <%= currentUrinals %></h3>
                <% } %>
            </div>
        </div>
        <div class="center">
            <h3>Help Us Improve Our Accuracy:</h3>
            <p>Report the number of utilities at this location to improve its accuracy!</p>
            <div class="stats">
                <h3 class="evenMargin">Sinks: <asp:TextBox ID="txtSinksE" class="shrinkBtn" runat="server"></asp:TextBox></h3>
                <h3 class="evenMargin">Stalls: <asp:TextBox ID="txtStallsE" class="shrinkBtn" runat="server"></asp:TextBox></h3>
                <%if (urinals) { %>
                <h3 class="evenMargin">Urinals: <asp:TextBox ID="txtUrinalsE" class="shrinkBtn" runat="server"></asp:TextBox></h3>
                <%} %>
            </div>
          </div>
        <%}
       else
        {%>
        <div class="center">
            <h3>Help Us Gather Data:</h3>
            <p>This site works best with help from you! Enter the number of utilities at this location!</p>
            <div class="stats">
                <h3>Sinks: <asp:TextBox ID="txtSinks" class="shrinkBtn" runat="server"></asp:TextBox></h3>
                <h3>Stalls: <asp:TextBox ID="txtStalls" class="shrinkBtn" runat="server"></asp:TextBox></h3>
                <%if (urinals)
                { %>
               <h3>Urinals: <asp:TextBox ID="txtUrinals" class="shrinkBtn" runat="server"></asp:TextBox></h3>
                <%} %>
            </div>
        </div>
        <%}%>
        <asp:Button ID="Button2" class = "roomButton restroomMod"  runat="server"   Text="Submit"  OnClick="Submit_Updates_Click" />
        <hr />
        
      <%if (currentReview != 0)
        {%>
        <div class="center">
            <h3>Overall Score: <%= currentReview %> / 5</h3>
            <div class="stats">
                <h4 class="gridMod">Sink Score: <%= sinksReview %></h4>
                <h4 class="gridMod">Stalls Score: <%= stallsReview %></h4>
                <%if (urinals) { %>
                <h4 class="gridMod">Urinals Score: <%= urinalsReview %></h4>
                <%} %>
            </div>
        </div>
      <%}
        else
        {%>
        <h3 class="center">Be The First To Review This Restroom!</h3>
      <%} %>
        <h3 style="margin-top: 10px;">Leave A Review:</h3>
  <asp:TextBox ID="txtComment" runat="server" Rows="5" Columns="20" TextMode="MultiLine" Width="100%" placeholder="Leave a Comment!" style="resize:none"/>  
        <div class="center">
            <div class="stats">
                <h3 class="maxWidth evenMargin">Sinks: <asp:DropDownList ID ="sinkRatingDrop" runat="server" >
                    <asp:ListItem Selected ="True" Value="1" >1</asp:ListItem>
                    <asp:ListItem Value="2" >2</asp:ListItem>
                    <asp:ListItem Value="3" >3</asp:ListItem>
                    <asp:ListItem Value="4" >4</asp:ListItem>
                    <asp:ListItem Value="5" >5</asp:ListItem>
                </asp:DropDownList>
                 / 5</h3>
                <h3 class="maxWidth evenMargin">Stalls: <asp:DropDownList ID ="stallRatingDrop" runat="server" >
                        <asp:ListItem Selected ="True" Value="1" >1</asp:ListItem>
                        <asp:ListItem Value="2" >2</asp:ListItem>
                        <asp:ListItem Value="3" >3</asp:ListItem>
                        <asp:ListItem Value="4" >4</asp:ListItem>
                        <asp:ListItem Value="5" >5</asp:ListItem>
                    </asp:DropDownList>
                 / 5</h3>
                <%if (urinals) { %>
                <h3 class="maxWidth evenMargin">Urinals: <asp:DropDownList ID ="urinalRatingDrop" runat="server" >
                        <asp:ListItem Selected ="True" Value="1" >1</asp:ListItem>
                        <asp:ListItem Value="2" >2</asp:ListItem>
                        <asp:ListItem Value="3" >3</asp:ListItem>
                        <asp:ListItem Value="4" >4</asp:ListItem>
                        <asp:ListItem Value="5" >5</asp:ListItem>
                    </asp:DropDownList>
                 / 5</h3>
           <%} %>
            </div>
        </div>

        <%if(urinals) {%>
          <asp:Button ID="Button1" class = "roomButton restroomMod"  runat="server"   Text="Submit"  OnClick="Submit_Review_Click" />
  <ul class ="commentGridview gridMod" >
    <asp:GridView ID="GridView" AutoGenerateColumns="False" DataSourceID="SqlDataSource1" runat="server" DataKeyNames="username, sinks, stalls, urinals, ratingComment" style="width:100%;">
        <Columns>
            <asp:BoundField DataField="username" HeaderText="Name" HeaderStyle-CssClass="text-center" ItemStyle-HorizontalAlign="Center" ItemStyle-Width="15%" SortExpression="username" ></asp:BoundField>
            <asp:BoundField DataField="sinks" HeaderText="Sink Ratings" HeaderStyle-CssClass="text-center" ItemStyle-HorizontalAlign="Center" ItemStyle-Width="15%" SortExpression="sinks" ></asp:BoundField>
            <asp:BoundField DataField="stalls" HeaderText="Stall Ratings" HeaderStyle-CssClass="text-center" ItemStyle-HorizontalAlign="Center" ItemStyle-Width="15%" SortExpression="stalls" ></asp:BoundField>
            <asp:BoundField DataField="urinals" HeaderText="Urinal Ratings" HeaderStyle-CssClass="text-center" ItemStyle-HorizontalAlign="Center" ItemStyle-Width="15%" SortExpression="urinals" ></asp:BoundField>
            <asp:BoundField DataField="ratingComment" HeaderText="Comment" HeaderStyle-CssClass="text-center" ItemStyle-HorizontalAlign="Center" ItemStyle-Width="15%" SortExpression="ratingComment" ></asp:BoundField>
        </Columns>
    </asp:GridView>
  </ul>

        <%} else {%>
          <asp:Button ID="Button3" class = "roomButton restroomMod"  runat="server"   Text="Submit"  OnClick="Submit_Review_Click" />
  <ul class ="commentGridview gridMod" >
    <asp:GridView ID="GridView1" AutoGenerateColumns="False" DataSourceID="SqlDataSource1" runat="server" DataKeyNames="username, sinks, stalls, urinals, ratingComment" style="width:100%;">
        <Columns>
            <asp:BoundField DataField="username" HeaderText="Name" HeaderStyle-CssClass="text-center" ItemStyle-HorizontalAlign="Center" ItemStyle-Width="15%" SortExpression="username" ></asp:BoundField>
            <asp:BoundField DataField="sinks" HeaderText="Sink Ratings" HeaderStyle-CssClass="text-center" ItemStyle-HorizontalAlign="Center" ItemStyle-Width="15%" SortExpression="sinks" ></asp:BoundField>
            <asp:BoundField DataField="stalls" HeaderText="Stall Ratings" HeaderStyle-CssClass="text-center" ItemStyle-HorizontalAlign="Center" ItemStyle-Width="15%" SortExpression="stalls" ></asp:BoundField>
            <asp:BoundField DataField="ratingComment" HeaderText="Comment" HeaderStyle-CssClass="text-center" ItemStyle-HorizontalAlign="Center" ItemStyle-Width="15%" SortExpression="ratingComment" ></asp:BoundField>
        </Columns>
    </asp:GridView>
  </ul>

        <%} %>

    <!-- this provides the items in the GridView from the SQL database -->

    <asp:SqlDataSource ID="SqlDataSource1" runat="server" ConnectionString="<%$ ConnectionStrings:ConnectionString %>" SelectCommand="EXEC spGetRatingAndComment @restroomId">
        <SelectParameters>
		    <asp:sessionparameter name="restroomId" sessionfield="restroomId" type="Int32" />
	    </SelectParameters>
    </asp:SqlDataSource>

        </div>

</asp:Content>