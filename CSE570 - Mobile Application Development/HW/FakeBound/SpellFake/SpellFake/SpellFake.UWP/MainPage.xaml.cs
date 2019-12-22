namespace SpellFake.UWP
{
    public sealed partial class MainPage
    {
        public MainPage()
        {
            this.InitializeComponent();
            LoadApplication(new SpellFake.App());
        }
    }
}
