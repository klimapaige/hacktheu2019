using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WindowsInput;

namespace HackTheU2019_Lib.Mouse
{
    public class WindowsMouse : IMouseOutput
    {
        InputSimulator input = new InputSimulator();
        public void Click()
        {
            input.Mouse.LeftButtonClick();
        }

        public void MoveAbsolute(double x, double y)
        {
            input.Mouse.MoveMouseTo(x, y);
        }

        public void MoveRelative(double dx, double dy)
        {
            input.Mouse.MoveMouseBy((int) dx, (int) dy);
        }
    }
}