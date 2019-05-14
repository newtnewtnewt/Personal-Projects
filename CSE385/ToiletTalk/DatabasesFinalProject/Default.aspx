<%@ Page Title="Home Page" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="Default.aspx.cs" Inherits="DatabasesFinalProject._Default" %>

<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">

    <div class="main">

        <asp:TextBox ID="txtSearch" runat="server" Rows="1" Columns="20" Text="" class="search" placeholder="Search Buildings Here" style="font-size:25px;"/> 
        <div class="checkboxes">
            <asp:Button ID="SubmitSearch" class ="searchButton"  runat="server"   Text="Submit"  OnClick="SubmitButton_Click" />
            <h4>Male: </h4>
            <asp:CheckBox class="box" ID="Male" runat="server" oncheckedchanged="Male_Check" autopostback="false" />
            <h4>Female:</h4>
            <asp:CheckBox class="box" ID="Female" runat="server" oncheckedchanged="Female_Check" autopostback="false"/>
            <h4>Unisex:</h4>
            <asp:CheckBox class="box" ID="Any" runat="server" oncheckedchanged="Any_Check" autopostback="false"/>
            <h4>Staff:</h4>
            <asp:CheckBox class="box" ID="Staff" runat="server" oncheckedchanged="Staff_Check" autopostback="false"/>
        </div>

        <h3 class="header"> Total Results: <%= GridView.Rows.Count %> </h3>
        <hr>

        <div>

    <asp:GridView ID="GridView" runat="server" AutoGenerateColumns="False" DataKeyNames="restroomId,buildingName,floorNumber,roomType,roomNumber" DataSourceID="SqlDataSource1" OnRowCommand ="GridView1_RowCommand" AllowSorting ="true" >
        <Columns>
        <%-- Our Gridview accounts for Name, Description, Organization, Platform, Versions, Rating, and Comments.Th
             All data and headers are centered, and space is provided appropriately for the content of each column.
            --%>
         <asp:BoundField DataField="restroomId"  HeaderStyle-CssClass="text-center"  ItemStyle-HorizontalAlign="Center" HeaderText="Restroom ID" ItemStyle-Width="10%"> </asp:BoundField>
         <asp:BoundField DataField="buildingName" HeaderText="Building Name" HeaderStyle-CssClass="text-center" ItemStyle-HorizontalAlign="Center" ItemStyle-Width="30%"></asp:BoundField>
         <asp:BoundField DataField="floorNumber"  HeaderStyle-CssClass="text-center"  ItemStyle-HorizontalAlign="Center" HeaderText="Floor Number" ItemStyle-Width="10%"></asp:BoundField>
         <asp:BoundField DataField="roomType"  HeaderStyle-CssClass="text-center"  ItemStyle-HorizontalAlign="Center" HeaderText="Gender" ItemStyle-Width="10%"> </asp:BoundField>
         <asp:BoundField DataField="roomNumber" HeaderStyle-CssClass="text-center"  ItemStyle-HorizontalAlign="Center" HeaderText="Room Number" ItemStyle-Width="10%"></asp:BoundField>
         <asp:TemplateField HeaderText="Select Restroom"  HeaderStyle-CssClass="text-center"  ItemStyle-HorizontalAlign="Center" ItemStyle-Width="30%">
                 <ItemTemplate>
                      <asp:LinkButton ID="ViewCommentsButton" CssClass = "Button"  runat="server"   Text="Go To Page"  OnClick="ViewComments_Click" CommandArgument= "<%# Container.DataItemIndex %>" CommandName ="Remove"  />
                 </ItemTemplate>

                <ItemStyle HorizontalAlign="Center" Width="20%"></ItemStyle>
                </asp:TemplateField>
       </Columns>
    </asp:GridView>
        </div>

            <%--this provides the items in the GridView from the SQL database --%>
    <asp:SqlDataSource ID="SqlDataSource1" runat="server" ConnectionString="<%$ ConnectionStrings:ConnectionString %>" SelectCommand="EXEC spGetRestroomsByType @staff, @male, @female, @unisex, @searchStr">
        <SelectParameters>
            <asp:ControlParameter ControlID="Staff" PropertyName="Checked" Name="staff" Type="byte" DefaultValue="0" />
            <asp:ControlParameter ControlID="Male" PropertyName="Checked" Name="male" Type="byte" DefaultValue="0" />
            <asp:ControlParameter ControlID="Female" PropertyName="Checked" Name="female" Type="byte" DefaultValue="0" />
            <asp:ControlParameter ControlID="Any" PropertyName="Checked" Name="unisex" Type="byte" DefaultValue="0" />
            <asp:ControlParameter ControlID="txtSearch" PropertyName="Text" Name="searchStr" Type="String" DefaultValue="" />
        </SelectParameters>
    </asp:SqlDataSource>
    </div>
    
</asp:Content>

