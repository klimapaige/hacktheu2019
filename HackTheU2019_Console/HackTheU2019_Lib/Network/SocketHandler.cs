using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using WindowsInput;

namespace HackTheU2019_Lib.Network
{
    public class SocketHandler
    {

        public void StartListening(string ipAddress, int port = 21211)
        {
            byte[] buffer = new byte[1]; // Buffer for the header
            
            IPAddress ip = StringToIp(ipAddress);
            IPEndPoint localEndPoint = new IPEndPoint(ip, port);

            Socket listener = new Socket(ip.AddressFamily,
                SocketType.Stream, ProtocolType.Tcp);

            try
            {
                listener.Bind(localEndPoint);
                listener.Listen(1);

                while (true)
                {
                    Console.WriteLine("Waiting for a connection...");
                    // Program is suspended while waiting for an incoming connection.  
                    Socket handler = listener.Accept();
                    Console.WriteLine("Connection accepted");

                    // An incoming connection needs to be processed.  
                    while (true)
                    {
                        try { 
                            handler.Receive(buffer);
                            ProcessMessage(buffer[0], handler);
                        }
                        catch (Exception e)
                        {
                            Console.WriteLine(e.Message);
                            break;
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        private IPAddress StringToIp(string ipAddress)
        {
            byte[] ipArray = new byte[4];
            int index = 0;
            ipAddress.Split('.').ToList().ForEach(ipString => ipArray[index++] = byte.Parse(ipString));
            Console.WriteLine(bufferToString(ipArray));
            return new IPAddress(ipArray);
        }

        private static InputSimulator sim = new InputSimulator();

        public static void ProcessMessage(byte header, Socket handler)
        {
            if (header == 0)
            {
                sim.Mouse.LeftButtonClick();
            }
            else if(header == 1)
            {
                sim.Mouse.LeftButtonDown();
            }
            else if (header == 2)
            {
                sim.Mouse.LeftButtonUp();
            }
            else if (header == 3)
            {
                sim.Mouse.RightButtonClick();
            }
            else if (header == 4)
            {
                sim.Mouse.RightButtonDown();
            }
            else if (header == 5)
            {
                sim.Mouse.RightButtonUp();
            }
            else if (header == 6)
            {

                // Mouse move
                byte[] buffer = new byte[4];
                handler.Receive(buffer);
                //Console.WriteLine(bufferToString(buffer));
                int dx = byteArrayToInt(buffer);
                handler.Receive(buffer);
                //Console.WriteLine(bufferToString(buffer));
                int dy = byteArrayToInt(buffer);

                Console.WriteLine("dx: " + dx);
                Console.WriteLine("dy: " + dy);
                sim.Mouse.MoveMouseBy(dx, dy);
            }
        }

        private static int byteArrayToInt(byte[] buffer)
        {
            int twosCompliment = buffer[0] >> 7;

            if (twosCompliment == 1)
            {
                for (int i = 0; i < buffer.Length; i++)
                {
                    buffer[i] ^= 255;
                }
            }

            int result = 0;
            for (int i = 0; i < buffer.Length; i++)
            {
                result += buffer[i] * (int)(Math.Pow(2, buffer.Length - 1 - i));
            }
            if (twosCompliment == 1)
            {
                result += 1;
                result *= -1;
            }
            return result;
        }

        private static byte[] intToByteArray(int value)
        {
            byte[] buffer = new byte[4];
            for (int i = 0; i < buffer.Length; i++)
            {
                buffer[i] = (byte)(value >> (8 * (buffer.Length - 1 - i)));
            }
            return buffer;
        }

        private static string bufferToString(byte[] buffer)
        {
            string builder = "[";
            foreach (byte spot in buffer)
            {
                builder += spot + ", ";
            }
            builder += ']';
            return builder;

        }
    }
}
