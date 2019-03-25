/*   Emmanuel idehen
       Java Programming  
       02/23/2018

Program:  This programs makes use of input validation, security,
          graphical user interface


***Design (Pseudo/Algorithm)***

Create functions: UserLogin, createprofile, duplicate and the main function
These functions have various purposes
UserLogin gives the user a platform to login if his username and password already exists
createprofile allows user register
The username and password are encrypted and decrypted accordingly
The duplicate function checks if the username already exists

*******************************

*/             

import java.io.*;
import java.util.*;
import java.lang.StringBuilder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class UserInterface
{
      private static JFrame f;   //Used to create interface
      private static JPanel mainP, panel;	
      private JButton createP, UserLoginB;
      private static JLabel l;
      private static JTextField field, userid, password; 
      static int mod=0, shift=0, ascii, result;  //Moding the username
      static long phoneNumber=0; 
      static String name=null, userName=null, passWord=null, sex=null, uni=null, line=null, check=null,
                    dash=null, temp=null;   //Creation of user profile
      static StringBuilder sb = new StringBuilder(); //Spacing 

   //Main Frame
   public UserInterface()  //Graphic user interface
   {
         f = new JFrame("Menu");
         f.setVisible(true);
         f.setSize(200, 150);
         f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
         mainP = new JPanel();
         mainP.setBackground(Color.BLUE);
         
         createP = new JButton("Create Profile");
         createP.addActionListener(new ActionListener()
         {
               public void actionPerformed(ActionEvent e){create();}
         });

         UserLoginB = new JButton("Login");
         UserLoginB.addActionListener(new ActionListener()
         {
               public void actionPerformed(ActionEvent e){UserLogin();}
         });
        
         l = new JLabel("Select an option.");
      
         mainP.add(l);
         mainP.add(createP);  //This button links to the Creation of a user profile
         mainP.add(UserLoginB);   //This button links to UserLogin with username and password
         f.add(mainP);
   }  

   
   public static void main(String[] args)
   {
         new UserInterface();  //Call the interface function
   }
   
   //Write data to file
    public static void WriteToFile()
    {
         File outFile = new File("accounts.txt");   //Write to file accounts.txt
      
         try
         {
               if(!outFile.exists())   //Check if the file exists
               {
                     outFile.createNewFile();
                     JOptionPane.showMessageDialog(null, "File was created.", "Information", JOptionPane.INFORMATION_MESSAGE);
               }
         
               FileWriter fw = new FileWriter(outFile, true);   //Write into file
               BufferedWriter bw = new BufferedWriter(fw);  	//Use buffered WriteToFiler and printer
               PrintWriter pw = new PrintWriter(bw);
         
               pw.println("-----");   //Write into file
               pw.println(mod);
               pw.println(shift);
               pw.println(name);
               pw.println(sex);
               pw.println(phoneNumber);
               pw.println(uni);
               pw.println(passWord);
               pw.println(userName);         
               pw.close();
               
               mod =0;     //Assign zero to each value of the variables being written into the file 
               shift =0;
               name = null;
               sex =null;
               phoneNumber=0;
               uni =null;
               passWord=null;
               userName=null;
               
               JOptionPane.showMessageDialog(null, "Your profile was created.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
         }  
      
         catch (IOException e)    //Catch an exception
         {JOptionPane.showMessageDialog(null, "File not found.", "error", JOptionPane.ERROR_MESSAGE);}
    }
   
   //Create Profile Panel
   public static void create()    //Create profile
   {
         userid = new JTextField();
         password = new JTextField();
         JTextField yourName = new JTextField();
         JTextField phone = new JTextField();
         JTextField university = new JTextField();
         panel = new JPanel(new GridLayout(6,2));   //Size of the JPanel using grid
         panel.add( new JLabel( "Name:") );   //Displays name
         panel.add( yourName );		      //Displays real name inputted
         panel.add( new JLabel( "Userid:") );
         panel.add( userid );
         panel.add( new JLabel( "Password:") );
         panel.add( password );
         panel.add( new JLabel( "Phone number:") );
         panel.add( phone );
         panel.add( new JLabel( "University:") );
         panel.add( university );
         JRadioButton male = new JRadioButton("Male"); 
         JRadioButton female = new JRadioButton("Female");
         ButtonGroup group = new ButtonGroup();
         panel.add(male);
         panel.add(female);
         group.add(male);
         group.add(female);
         
         result = JOptionPane.showConfirmDialog(null, panel, "Choose", JOptionPane.YES_NO_OPTION);    
         name = yourName.getText();
         userName = userid.getText();
        
         while((userName.length() < 8 || !Character.isAlphabetic(userName.charAt(0))) && result != JOptionPane.NO_OPTION)    //Username validation
         {
               JOptionPane.showMessageDialog(null, "Username must at least be 8 characters and can only start with an alphabet.", "Information", JOptionPane.INFORMATION_MESSAGE);
               result = JOptionPane.showConfirmDialog(null, panel, "Choose", JOptionPane.YES_NO_OPTION);
               userName = userid.getText();     
         }
      
         if(result != JOptionPane.NO_OPTION)
         {  
               File outFile = new File("accounts.txt");
               if(outFile.exists())
               {duplicate();}
         }
        
         sb.setLength(0);
      
         Random rnum = new Random();
         mod = rnum.nextInt(14)+2;
         
         for(int n=0; n<userName.length(); n++)     //Mod username
         {
               char character = userName.charAt(n);
               ascii = (int) character;
            
               ascii = ascii - mod;
           
               character = (char) ascii;
         
               sb.append(character);
         }
 
         userName = sb.toString();   //Adds spaces 

         passWord = password.getText();  //Gets text

         while((passWord.length() < 7 || Character.isDigit(passWord.charAt(0))) && result != JOptionPane.NO_OPTION)
         {
               JOptionPane.showMessageDialog(null, "Password must at least be 7 characters and can not start with a number.", "Information", JOptionPane.INFORMATION_MESSAGE);
               result = JOptionPane.showConfirmDialog(null, panel, "Choose", JOptionPane.YES_NO_OPTION);
               passWord = password.getText();
         }
         
         Random num2 = new Random();   //Generate random number 
         shift = num2.nextInt(14)+4;    //Shift the password first
      
         sb.setLength(0); //Set length with string builder at zero
         
         for(int n=0; n<passWord.length(); n++)   //Encrypt every character of the password using Ceaser cipher
         {
               char character = passWord.charAt(n);
               ascii = (int) character;
         
               ascii = ascii - shift;
         
               character = (char) ascii;
         
               sb.append(character);
         }
      
         passWord = sb.toString();
      
         if(male.isSelected())   //Validate gender inputted
               sex = "Male";        
       
         else if(female.isSelected())
               sex = "Female";
       else
       {
            while((!male.isSelected() && !female.isSelected()) && result != JOptionPane.NO_OPTION)
            {
                  JOptionPane.showMessageDialog(panel, "A gender was not selected.", "Inane error",JOptionPane.ERROR_MESSAGE); 
                  result = JOptionPane.showConfirmDialog(null, panel, "Choose", JOptionPane.YES_NO_OPTION);
                              
                  if(male.isSelected())
                        sex = "Male";        
   
                  else if(female.isSelected())
                        sex = "Female";
             }
        }
           
        temp = phone.getText();   //Gets phone number
      
         if(result != JOptionPane.NO_OPTION)
         {       
               try 
               {phoneNumber = Long.parseLong(temp);}
               
               catch(NumberFormatException e) 
               {JOptionPane.showMessageDialog(null, "Invalid entry.", "error", JOptionPane.ERROR_MESSAGE);}
         } 

         if(result != JOptionPane.NO_OPTION) 
         {
               phoneNumber = Long.parseLong(temp);
               
               while(Long.toString(phoneNumber).length() != 10 && result != JOptionPane.NO_OPTION)
               {
                     result = JOptionPane.showConfirmDialog(null, panel, "Choose", JOptionPane.YES_NO_OPTION);
                     temp = phone.getText();   
                     
                     try {phoneNumber = Long.parseLong(temp);}
                     
                     catch(NumberFormatException e) 
                     {JOptionPane.showMessageDialog(null, "Invalid entry.", "error", JOptionPane.ERROR_MESSAGE);}  
                }
          } 
   
          uni = university.getText();
   
          if(result != JOptionPane.NO_OPTION)
               WriteToFile();
    }
    
    

    //Login Panel
    public static void UserLogin()    //Login function verifies if username previously exists in the file accounts.txt
    {
         String userName2, passWord2;  //Username and password as stored in the data file
         boolean validI = false;   //Boolean variable to validate
         userid = new JTextField();  //Space in which you input your username or user ID
         JTextField password = new JTextField();   //Space in whuch you input your password
         panel = new JPanel(new GridLayout(4,4));   
         panel.add( new JLabel( "Username:") );
         panel.add( userid );
         panel.add( new JLabel( "Password:") );
         panel.add( password );

         result = JOptionPane.showConfirmDialog(null, panel, "Login", JOptionPane.YES_NO_OPTION);
         userName2 = userid.getText();
         passWord2 = password.getText();
    
         try
         {  
               File file = new File("accounts.txt");

               FileReader fr = new FileReader(file);      //Reads from file
               BufferedReader br = new BufferedReader(fr);
            
               while(((line = br.readLine()) != null) && ((!userName2.equals(userName)) || (!passWord2.equals(passWord))))
               {
                     if(dash == null)
                           dash = line;
                           
                     else if(mod == 0)
                           mod = Integer.parseInt(line.trim());
                           
                     else if(shift == 0)
                           shift = Integer.parseInt(line.trim());
            
                     else if(name == null)
                           name = line;
             
                     else if(sex == null)
                           sex = line;
             
                     else if(phoneNumber == 0)
                           phoneNumber = Long.parseLong(line.trim());
             
                     else if(uni==null)
                           uni = line;
             
                     else if(passWord == null)
                     {
                           passWord = line;
                           decodePassW();
                     }
             
                     else if(userName == null)
                     { 
                           userName = line;
                           decodeUserN();
                   
                           if(userName2.equals(userName) && passWord2.equals(passWord))
                           {
                                 JOptionPane.showMessageDialog(null,"                Name:    " + name + "\n       Username:    " 
                                                      +userName+ "\n        Password:    " +passWord+
                                                      "\n              Gender:   " +sex+
                                                      "\nPhone Number:   " +phoneNumber+
                                                      "\n          University:   " +uni, "Results", JOptionPane.PLAIN_MESSAGE);                        
                                 validI = true;
                                 dash = null;
                                 name = null; 
                                 userName = null;
                                 passWord = null;
                                 sex = null;
                                 phoneNumber = 0;
                                 uni = null;
                                 mod = 0;
                                 shift = 0;
                           }
                            
                           else
                           {
                                 dash = null;
                                 name = null; 
                                 userName = null;
                                 passWord = null;
                                 sex = null;
                                 phoneNumber = 0;
                                 uni = null;
                                 mod = 0;
                                 shift = 0;
                            }
                      }
               
                           
               }
               
               if(validI == false && result != JOptionPane.NO_OPTION)
                     JOptionPane.showMessageDialog(null, "Your profile could not be found or doesn't exist.", "error", JOptionPane.ERROR_MESSAGE);
         }
         
               
      
         catch (IOException e)
         {JOptionPane.showMessageDialog(null, "File not found", "error", JOptionPane.ERROR_MESSAGE);}
            
         dash = null;
         name = null; 
         userName = null;
         passWord = null;
         sex = null;
         phoneNumber = 0;
         uni = null;
         mod = 0;
         shift = 0;
    }
    
    //Decipher Password  
    public static void decodePassW()
    {
         sb.setLength(0);
                
         for(int n=0; n<passWord.length(); n++)
         {
               char character = passWord.charAt(n);
               ascii = (int) character;
         
               ascii = ascii + shift;
        
               character = (char) ascii;
         
               sb.append(character);
          }
      
          passWord = sb.toString();
     }
     
     //Deciphers the username
     public static void decodeUserN()
     {
         sb.setLength(0);    
                
         for(int n=0; n<userName.length(); n++)
         {
               char character = userName.charAt(n);
               ascii = (int) character;
         
               ascii = ascii + mod;
         
               character = (char) ascii;
         
               sb.append(character);
          }
          userName = sb.toString();
     }

     //Checks to see if a username already exist in the file
     public static void duplicate()
     {
         String name2 = null;
         try
         {  
               File file = new File("accounts.txt");

               FileReader fr = new FileReader(file);
               BufferedReader br = new BufferedReader(fr);
         
               while((line = br.readLine()) != null)
               {
                     if(dash == null)
                           dash = line;
                 
                     else if(mod == 0)
                           mod = Integer.parseInt(line.trim());
              
                     else if(shift == 0)
                           shift = Integer.parseInt(line.trim());
             
                     else if(name2 == null)
                           name2 = line;
              
                     else if(sex == null)
                           sex = line;
             
                     else if(phoneNumber == 0)
                           phoneNumber = Long.parseLong(line.trim());
             
                     else if(uni==null)
                           uni = line;
             
                     else if(passWord == null)
                           passWord = line;
             
                     else if(check == null)
                     { 
                           sb.setLength(0);    
                           check = line;
               
                           for(int n=0; n<check.length(); n++)
                           {
                                 char character = check.charAt(n);
                                 ascii = (int) character;
         
                                 ascii = ascii + mod;
         
                                 character = (char) ascii;
         
                                 sb.append(character);
                           }
                           check = sb.toString();  
             
                           if(userName.equals(check))
                           {
                                 JOptionPane.showMessageDialog(null, "The username entered already exists", "error", JOptionPane.ERROR_MESSAGE);
                                 while((userName.equals(check)|| userName.length() < 8 || !Character.isAlphabetic(userName.charAt(0))) && result != JOptionPane.NO_OPTION)
                                 {
                                       result = JOptionPane.showConfirmDialog(null, panel, "Choose", JOptionPane.YES_NO_OPTION);
                                       userName = userid.getText();
                        
                                 } 
                            }
               
                            else
                            {
                                 dash = null; name2 = null; check = null;
                                 passWord = null;
                                 sex = null;
                                 phoneNumber = 0;
                                 uni = null;
                                 mod = 0;
                                 shift = 0;
                             }  
                     }
               }
         }
      
         catch (IOException e)
         {JOptionPane.showMessageDialog(null, "File not found.", "error", JOptionPane.ERROR_MESSAGE);}
       }
}
