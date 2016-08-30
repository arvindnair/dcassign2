//Author Arvind Nair

/*Importing the required packages*/
import java.net.*;
import java.io.*;
import java.util.*;


public class mClient1 {//start of class containing main method

	public static void main(String[] args)throws Exception {//start of main method
		
		Socket Sock=new Socket("10.234.140.27",10444);//Creating a socket to access the server
		
		/*Server Authentication*/
		PrintStream PS=new PrintStream(Sock.getOutputStream());
		
		InputStreamReader IR=new InputStreamReader(Sock.getInputStream());
		BufferedReader br=new BufferedReader(IR);
		String serverMessageCred=br.readLine();
		System.out.println(serverMessageCred);//Display server's message for Authentication
		
		BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
		String serverCredentials=br1.readLine();
		PS.println(serverCredentials);//Sending Client's Credentials
		
		/*Reading response from server*/
		String serverMessage=br.readLine();
		System.out.println(serverMessage);
		String serverMessageVer="Autentication Approved!";
		
		/*After client is approved for further activities*/
		if(serverMessage.equalsIgnoreCase(serverMessageVer)){//Authentication approved if open
			
			/*Client sends request for file transfer*/
			PS.println("Request for file");//sends message to server
			String serverMessage1=br.readLine();//reads message from server
			System.out.println(serverMessage1);//Shows message from server onto standard console of client
			
			String filenamepath=br1.readLine();//Takes file name as input from user
			PS.println(filenamepath);//Sends file name to server
			String serverMessage3=br.readLine();
			System.out.println(serverMessage3);
			String serverFilever="File is present Be Ready to receive it!";
			if(serverMessage3.equalsIgnoreCase(serverFilever)){//if file present loop open
				boolean clientflag=true;//setting boolean flag for client to control while loop
				while(clientflag==true){//start while loop for client
				
					byte[] b = new byte[1024];//specifying byte array of 1024 fixed size to receive message contents
				    
				    String f="F1Copy.txt";//change transferred filename here
				    FileOutputStream inFile = new FileOutputStream(f);//Creates a new file instance
				    InputStream is = Sock.getInputStream();//takes the input stream from the socket
				    BufferedInputStream in2 = new BufferedInputStream(is, 1024);
				    in2.read(b, 0, 1024);//reads into the byte array b from the input stream 
				    
				    /*Receives the checksum from the server*/
				    InputStreamReader IR1=new InputStreamReader(Sock.getInputStream());
				    BufferedReader br2=new BufferedReader(IR1);
				    String longver=br2.readLine();
		            
				    /*Receives the key value from the server*/
		            int key=Integer.parseInt(br2.readLine());
				
		           Long yverify=Long.parseLong(longver);//convert checksum to long its original format
				   
		           /*Decryption by client*/
		           for(int i=0;i<b.length;i++){
				    	if(b[i]==0){//skip all zero values in byte array
				    		break;
				    	}
				    	   b[i]=(byte) (b[i]-key);//subtract the key
				       }
				   
		           /*Write each element of byte array into file at client side*/
		           for(int i=0;i<b.length;i++){
				    	if(b[i]==0){
				    		break;
				    	}
				    	inFile.write(b[i]);
				    }
				    inFile.close();
				   
				    /*Create instance of file at client side to read its contents*/
				    File f1=new File(f);
				    FileInputStream fin=new FileInputStream(f1);
					byte[] buf=new byte[1024];
					fin.read(buf, 0, 1024);//read file into byte array called buf.
					
					/*calculate CRC32 of the file contents*/
					java.util.zip.CRC32 x=new java.util.zip.CRC32();
			            x.update(buf);
			            long y=x.getValue();
                                    y=0;
			            
			           /*if checksum is equal send message of success*/
			            if(y==yverify){
			            PS.println("Success");
			            InputStream is1 = Sock.getInputStream(); 
			            InputStreamReader IR2=new InputStreamReader(is1);
					    BufferedReader br3=new BufferedReader(IR2);
					    String retryver=br3.readLine();
					    System.out.println("File transfer is successful!");
			            clientflag=false;//setting client flag to false
			            }
			            else{
			            	PS.println("Resend");
				            InputStream is1 = Sock.getInputStream(); 
				            InputStreamReader IR2=new InputStreamReader(is1);
						    BufferedReader br3=new BufferedReader(IR2);
						    String retryver=br3.readLine();
						    System.out.println("Retry server msg "+retryver);
						    if(retryver.contains("Closing")){//if server message contains closing
						    	clientflag=false;//setting client flag to false
						    }
			            }
				    }//end while loop for client
			 } //if file present loop closed 
			}//if authentication approved loop closed
		
		
	}//end of main method

}//end of class
