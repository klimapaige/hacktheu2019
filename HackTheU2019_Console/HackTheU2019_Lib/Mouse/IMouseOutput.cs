using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HackTheU2019_Lib.Mouse
{
    public interface IMouseOutput
    {
        void Click();

        void MoveRelative(double dx, double dy);
        void MoveAbsolute(double x, double y);
    }
}
