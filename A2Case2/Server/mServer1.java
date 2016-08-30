//Author Arvind Nair

/*Importing the required packages*/
import java.net.*;
import java.io.*;
import java.util.*;

public class mServer1 {//start of class containing main method

	public static void main(String[] args)throws Exception {//start of main method
		
                System.out.println("Server Running and Accepting Connections!");
		ServerSocket Server=new ServerSocket(10444);//Create Server type socket listening on specified port
		Socket Sock=Server.accept();//accept the incoming connection

		/*Authentication part*/
		PrintStream PS=new PrintStream(Sock.getOutputStream());
		PS.println("Enter the Credentials to access server: ");//sends authentication request to client
		InputStreamReader IR=new InputStreamReader(Sock.getInputStream());
		BufferedReader br=new BufferedReader(IR);
		String clientMessage=br.readLine();
		String msgAuth="HelloArvindserver";
		
		if(clientMessage.equalsIgnoreCase(msgAuth)){//checks if received message is equal to stored message
			PS.println("Autentication Approved!");
			String clientReqMessage=br.readLine();
			String clientReqMessageVer="Request for file";
			if(clientReqMessage.equalsIgnoreCase(clientReqMessageVer)){//checks if client requests for file
				PS.println("Enter the file you want to send: ");
				String filenamepaths=br.readLine();//Takes the file name from client
				
				File f=new File(filenamepaths);
				if(f.exists()){//checks if the file exists on the server side
					PS.println("File is present Be Ready to receive it!");
					
					boolean serverflag=true;//server flag is set
					int retrycounter=0;//retry counter is set to zero
					int retrycountervary=5;//Number of times retry is allowed can be varied here
					
					while(serverflag==true){//while loop begins
						retrycounter++;
						
						/*Read file contents into byte array called buf*/
					FileInputStream fin=new FileInputStream(f);
					byte[] buf=new byte[1024];
					fin.read(buf, 0, 1024);
					
					/*Calculate the CRC32 value of the message*/
					 java.util.zip.CRC32 x=new java.util.zip.CRC32();
			            x.update(buf);
			            long y=x.getValue();
			        
			            String strLong = Long.toString(y);
				
			            int key =5;//Specify the key here
			            
			            /*Encrypting the contents of file here by adding the key to each element of array*/
				       for(int i=0;i<buf.length;i++){
				    	   buf[i]=(byte) (buf[i]+key);
				       }
				       
	
				    
				       /*write the file contents to output using sockets*/
					OutputStream os = Sock.getOutputStream();
			         BufferedOutputStream out = new BufferedOutputStream(os, buf.length);
			            out.write(buf, 0, buf.length);
			            out.flush();
			         
			           
			            PS.println(strLong);//send CRC32 value
				    
				        PS.println(key);//send key
			            
			            
			    		String clientMessage1=br.readLine();//read client message for retry
			    
			    	
			    		/*If file transfer is successful send message Ok and close the socket*/
				       if(clientMessage1.equalsIgnoreCase("Success")){
				    		PS.println("Ok");
				    	   serverflag=false;
				    	   Sock.close();
				       }
				       
				       /*If file transfer is unsuccessful then retry if within the retry limit*/
				       else if(clientMessage1.equalsIgnoreCase("Resend")){
				          if(retrycounter>retrycountervary){
				    	   PS.println("Retry Attempts are done. Closing connection....");
				    	   serverflag=false;
				    	   Sock.close();
				    	   } 
				          else{
				        	  PS.println("Retry attempt "+retrycounter);
				          }
				       }
					}//while loop ends
				}//if loop check file exists ends 
				
				else{//if file does not exist print message and close connection
					PS.println("The specified file does not exist.Closing Connection....");
					Sock.close();
				}
			}
			else{//if another request other than file transfer print message and close connection
				PS.println("Invalid Request. Closing Connection...");
				Sock.close();	
			}
		}
		else{//if authentication fails due to wrong credentials display message and close connection
			PS.println("Autentication Failed. Closing Connection...");
			Sock.close();
		}
		
		
	}//end of main method

}//end of class
