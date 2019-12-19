using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(DatabasesFinalProject.Startup))]
namespace DatabasesFinalProject
{
    public partial class Startup {
        public void Configuration(IAppBuilder app) {
            ConfigureAuth(app);
        }
    }
}
