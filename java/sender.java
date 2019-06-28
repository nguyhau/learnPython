import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class sender {
 
    public static final String GROUP_ADDRESS = "224.4.0.1";
    public static final int PORT = 50420;
    private static int index;
    
        
    public static void main(String[] args) throws InterruptedException {
        DatagramSocket socket = null;
        try {
            // Get the address that we are going to connect to.
            InetAddress address = InetAddress.getByName(GROUP_ADDRESS);
 
            // Create a new Multicast socket
            socket = new DatagramSocket();
            //socket.setInterface(InetAddress.getByName("10.116.41.145"));
            DatagramPacket outPacket = null;
            long counter = 0;
            index = 0;
            int num_pkg = 0;
            while (index < 31) {
                String msg = "Sent message No. " + counter;
                String FileName = "Bunny\\chunk_dash";
                counter++;
                //outPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, address, PORT);
                //outPacket = new DatagramPacket(bytes, count, address, PORT);
                //socket.send(outPacket);
                
                //System.out.println("Server sent packet with msg: " + msg);
                
                if (index == 0) {
                    FileName = FileName + "init.mp4";
                } else {
                    FileName = FileName + index + ".m4s";
                }
                if (index < 31) {
                    File file = new File(FileName);
                    // Get the size of the file
                    //long length = file.length();
                    byte[] bytes = new byte[4096];
                    InputStream in = new FileInputStream(file);           
                    System.out.println("Send file " + FileName);
                    int count;
                    while ((count = in.read(bytes)) > 0) {
                        num_pkg++;
                        //ByteBuffer buf = ByteBuffer.wrap(bytes, 0, count);
                        //mSession.getBasicRemote().sendBinary(buf);
                        //
                        //outPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, address, PORT);
                        outPacket = new DatagramPacket(bytes, count, address, PORT);
                        socket.send(outPacket);
                        System.out.println("Send " + num_pkg + " package to Proxy Server ");
                        Thread.sleep(10); // Sleep 1 second before sending the next message
                    }           
                    in.close();
                    //break;
                }
                index++;
                //Thread.sleep(1000); // Sleep 1 second before sending the next message
                //break;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}