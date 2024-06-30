package chatting.application;

//server socket class for server
//socket class for client

import javax.swing.*;//jframe
import javax.swing.border.EmptyBorder;
import java.awt.*;//color class
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//we will be using socket programming
public class client  implements ActionListener {

    JTextField text1;

    static JFrame t=new JFrame();
    static DataOutputStream dout;
   static  JPanel a1;
   static Box vertical=Box.createVerticalBox();//ek ke niche ayega
    client(){

       t. setLayout(null);
        //chatbox mei uppar ek alag se hi frame tha use hum bana sakte h pannel ke help se

        JPanel p1=new JPanel();//creating header//seprate frame.Frame ke uppar cheezo ko divide karna h
        p1.setBackground(new Color(7,94,84));
        //passing the coordinates
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);//iska layout null karna hoga because tabhi image uske uppar tabhi banega
        t.add(p1);

        //arrow
        //jaise hi aarow click frame close
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2=i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);

        JLabel back=new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });


        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5=i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);

        JLabel profile=new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);


        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8=i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);

        JLabel video=new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);


        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11=i10.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon(i11);

        JLabel phone=new JLabel(i12);
        phone.setBounds(360,20,35,30);
        p1.add(phone);



        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14=i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon(i14);

        JLabel more=new JLabel(i15);
        more.setBounds(420,20,10,25);
        p1.add(more);
        //frame ke uppar jo kuch bhi likhna h we do it using jlabel
        JLabel name=new JLabel("Priyanka");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);

        JLabel status=new JLabel("Active now");
        status.setBounds(110,35,100,18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(status);

        a1=new JPanel();
        a1.setBounds(5,75,440,570);
        t.add(a1);

        text1=new JTextField();
        text1.setBounds(5,655,310,40);
        text1.setFont(new Font("SAN_SERIF",Font.BOLD,16));
        t.add(text1);

        JButton send=new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF",Font.BOLD,16));
        t.add(send);


        t.setSize(450,700);
       t. setLocation(800,50);
      t.    setUndecorated(true);
        t.getContentPane().setBackground(Color.WHITE);
     t.   setVisible(true);//should be at last
    }

    public static void main(String[] args) {
        new client();//class ke run hote hi main method call hota h and
        //main method ke run hote hi constructor call hota h since humne object create
        //kiya h toh vo constructor ko call karega

        try {

            Socket s=new Socket("127.0.0.1",6001);//through this socket now i am connected to server
            //socket can be many but server socket can only be one

            //to recieve message
            DataInputStream din = new DataInputStream(s.getInputStream());
            //to send message
            dout = new DataOutputStream(s.getOutputStream());
            //action performance


            while (true) {

                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                //now yeh display karvana h
                JPanel panel=formatLabel(msg);

                JPanel left=new JPanel(new BorderLayout());
                left.add(panel,BorderLayout.LINE_START);
                vertical.add(left);


                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical,BorderLayout.PAGE_START);

                t.validate();//yeh static method tha thats why we need static method and
                //the vertical as static

            }


        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String out = text1.getText();

            //JLabel output=new JLabel(out);

            JPanel p2 = formatLabel(out);

            //p2.add(output);

            a1.setLayout(new BorderLayout());//top bottom left right centre

            JPanel right = new JPanel(new BorderLayout());//right mei through border layout line end
            right.add(p2, BorderLayout.LINE_END);//above two lines for right side display
            vertical.add(right);//for multiple messages
            vertical.add(Box.createVerticalStrut(15));//do vertical "hello" ke beech mei 15 ka space

            a1.add(vertical, BorderLayout.PAGE_START);//aligning the vertical

            //frame ko repaint karna hoga
            //inshort jaise hi send button click kare mera frame reload ho and
            //then message display ho jaye

            dout.writeUTF(out);

            text1.setText("");//jaise hi send dabaye it should be empty

            t.repaint();
            t.invalidate();
            t.validate();
        } catch(Exception ec) {
          ec.printStackTrace();
        }
    }
    public static JPanel formatLabel(String out){
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel output=new JLabel("<html><p style=\"width: 150px\">"+out+"</p></html>");
        output.setFont(new Font("Thoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        //adding padding
        output.setBorder(new EmptyBorder(15,15,15,50));

        //for time
        Calendar cal=Calendar.getInstance();  //calendar class is an abstract class
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");

        JLabel timo=new JLabel();
        timo.setText(sdf.format(cal.getTime()));
        //this is setting time dynamically

        panel.add(timo);
        panel.add(output);

        return panel;
    }

}
