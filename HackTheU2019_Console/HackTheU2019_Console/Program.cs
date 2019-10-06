
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using WindowsInput;

namespace HackTheU2019_Console
{
    class Program
    {
        // Incoming data from the client.  
        public static string data = null;
        static  InputSimulator sim = new InputSimulator();

        public static void StartListening()
        {
            byte[] buffer = new byte[1]; // Only need to store the header

            // Establish the local endpoint for the socket.  
            // Dns.GetHostName returns the name of the   
            // host running the application.  
            IPHostEntry ipHostInfo = Dns.GetHostEntry(Dns.GetHostName());
            //172.20.17.129
            IPAddress ipAddress = new IPAddress(new byte[4] { 172, 20, 17, 129 });
            IPEndPoint localEndPoint = new IPEndPoint(ipAddress, 11000);

            // Create a TCP/IP socket.  
            Socket listener = new Socket(ipAddress.AddressFamily,
                SocketType.Stream, ProtocolType.Tcp);

            // Bind the socket to the local endpoint and   
            // listen for incoming connections.  
            try
            {
                listener.Bind(localEndPoint);
                listener.Listen(10);

                // Start listening for connections.  
                while (true)
                {
                    Console.WriteLine("Waiting for a connection...");
                    // Program is suspended while waiting for an incoming connection.  
                    Socket handler = listener.Accept();
                    Console.WriteLine("Connection accepted");
                    data = null;

                    // An incoming connection needs to be processed.  
                    while (true)
                    {
                        handler.Receive(buffer);
                        ProcessMessage(buffer[0], handler);
                    }
                }

            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }

        }

        public static void ProcessMessage(byte header, Socket handler)
        {
            if (header == 0)
            {
                // Left click
                sim.Mouse.LeftButtonClick();
            }
            else if(header == 2)
            {
                // Right click
                sim.Mouse.RightButtonClick();
            }
            else if(header == 3)
            {

                // Mouse move
                byte[] buffer = new byte[4];
                handler.Receive(buffer);
                Console.WriteLine(bufferToString(buffer));
                int dx = byteArrayToInt(buffer);
                handler.Receive(buffer);
                Console.WriteLine(bufferToString(buffer));
                int dy = byteArrayToInt(buffer);

                Console.WriteLine("dx: " + dx);
                Console.WriteLine("dy: " + dy);
                sim.Mouse.MoveMouseBy(dx, dy);
            }
        }

        private static int byteArrayToInt(byte[] buffer)
        {
            int twosCompliment = buffer[0] >> 7;

            if(twosCompliment == 1)
            {
                for(int i = 0; i < buffer.Length; i++)
                {
                    buffer[i] ^= 255;
                }
            }

            int result = 0;
            for (int i = 0; i < buffer.Length; i++)
            {
                result += buffer[i] * (int)(Math.Pow(2, buffer.Length - 1 - i));
            }
            if(twosCompliment == 1)
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
                buffer[i] = (byte) (value >> (8 * (buffer.Length - 1 - i)));
            }
            return buffer;
        }

        private static string bufferToString(byte[] buffer)
        {
            string builder = "[";
            foreach (byte spot in buffer)
            {
                builder+=spot+", ";
            }
            builder+=']';
            return builder;

        }

        public static int Main(String[] args)
        {
            StartListening();
            return 0;
        }
    }
}
