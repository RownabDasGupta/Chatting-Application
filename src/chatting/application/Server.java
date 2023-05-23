package chatting.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class Server implements ActionListener {
    JPanel chatArea;
    JTextField typeArea;
    static Box vertical = Box.createVerticalBox();
    static JFrame jf = new JFrame();
    static DataOutputStream dout;
    
    public Server() {
        jf.setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(7,94,84));
        panel.setBounds(0, 0, 450, 70);
        panel.setLayout(null);
        jf.add(panel);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/back button.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel backButton = new JLabel(i3);
        backButton.setBounds(8, 20, 25, 25);
        panel.add(backButton);
        
        
        backButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
                
            }
        });
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/gaitonde with gun.png"));
        Image i5 = i4.getImage().getScaledInstance(55, 55, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profileGaitondeWithGun = new JLabel(i6);
        profileGaitondeWithGun.setBounds(42, 8, 55, 55);
        panel.add(profileGaitondeWithGun);
        
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video call.png"));
        Image i8 = i7.getImage().getScaledInstance(22, 25, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel videoCall = new JLabel(i9);
        videoCall.setBounds(325, 22, 22, 25);
        panel.add(videoCall);
        
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/voice call.png"));
        Image i11 = i10.getImage().getScaledInstance(23, 27, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel voiceCall = new JLabel(i12);
        voiceCall.setBounds(362, 22, 23, 27);
        panel.add(voiceCall);
        
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/three dots.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 24, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel threeDots = new JLabel(i15);
        threeDots.setBounds(400, 22, 10, 24);
        panel.add(threeDots);
        
        
        JLabel name = new JLabel("Gaitonde");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN SERIF", Font.BOLD, 18));
        panel.add(name);
        
        JLabel status = new JLabel("Active Now");
        status.setBounds(116, 39, 80, 10);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN SERIF", Font.BOLD, 10));
        panel.add(status);
        
        
        chatArea = new JPanel();
        chatArea.setBounds(4, 73, 432, 550);
        jf.add(chatArea);
        
        typeArea = new JTextField();
        typeArea.setBounds(5, 625, 325, 40);
        typeArea.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        jf.add(typeArea);
        
        JButton send = new JButton("Send");
        send.setBounds(332, 625, 104, 40);
        send.setBackground(new Color(15, 135, 47));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN SERIF", Font.BOLD, 18));
        jf.add(send);
        
        
        jf.setSize(440, 670);
        jf.setLocation(200, 30);
        jf.setUndecorated(true);
        jf.getContentPane().setBackground(Color.WHITE);
        
        
        
        
        
        jf.setVisible(true);
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String output = typeArea.getText();

            JPanel p2 = formatLabel(output);

            chatArea.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            chatArea.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(output);
            typeArea.setText("");

            jf.repaint();
            jf.invalidate();
            jf.validate();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    public static JPanel formatLabel(String output) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output2 = new JLabel("<html><p style=\"width: 150px\">" + output + "</p></html>");
        output2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output2.setBackground(new Color(37, 211, 107));
        output2.setOpaque(true);
        output2.setBorder(new EmptyBorder(15, 15, 15, 50));
        
        panel.add(output2);
        
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(calendar.getTime() ));
        
        panel.add(time);
        
        return panel;
    }
    
    
    public static void main(String args[]) {
        new Server();
        
        try {
            ServerSocket serverSocket = new ServerSocket(6001);
            
            while(true) {
                Socket socket = serverSocket.accept();
                DataInputStream din = new DataInputStream(socket.getInputStream());
                dout = new DataOutputStream(socket.getOutputStream());
                
                while(true) {
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    jf.validate();
                      
                }
            }
            
        }
        catch(Exception e) {
            e.printStackTrace();                 
        }
    }

}
