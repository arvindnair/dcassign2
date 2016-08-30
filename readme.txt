Steps to run the Assignment Program:
1.Extract the contents of NairA2.zip onto a location using Winrar or 7zip. 
2.There are 3 cases to run in each separate folder(Refer documentation/Report).

1.Log into Machine sl253-rrpc01.cs.iupui.edu - 10.234.140.27:
1.1.Go into the extracted folder using cd NairA2 command on pegasus. 
1.2.On pegasus, using cd A2Case1 to go into the directory of Case 1.
1.3.Type cd Server to go into the Server folder in A2Case1.
1.4.Type chmod +x servermakefile.sh to give permission.
1.5.Type ./servermakefile.sh to run the script file.
1.6.If any error is thrown then in a1case1 directory: 
  1.6.1 Type javac mServer1.java and press enter.
  1.6.2 Type java mServer1 and press enter.
1.7.The server will be running on machine 10.234.140.27.

2.Log into Machine sl253-rrpc02.cs.iupui.edu - 10.234.140.28
2.1.Go into the extracted folder using cd NairA2 command on pegasus. 
2.2.On pegasus, using cd A2Case1 to go into the directory of Case 1.
2.3.Type cd Client to go into the Client folder in A2Case1.
2.4.Type chmod +x clientmakefile.sh to give permission.
2.5.Type ./clientmakefile.sh to run the script file.
2.6.If any error is thrown then in a1case1 directory: 
  2.6.1 Type javac mClient1.java and press enter.
  2.6.2 Type java mClient1 and press enter.
2.7.The server will be on machine 10.234.140.28.

3.Both the programs must be running side by side and the server must run first.
4.On the Client type the credentials for authentication as HelloArvindserver.
5.Then enter the file name as F1.txt which is the file stored in the server folder.
6.The file transfer operation will be successfully completed.
7.In the client folder we can see a F1Copy.txt file generated and its contents match that with the F1.txt file on the server.
8.For the other two cases do similarly.

To change machines for doublecheck:
1.The client can be run on all except the machine on which the server is running.
2.The server can be run on any other machine but in the mClien1.java file in the Socket Sock=new Socket("10.234.140.27",10444); change the address to the one
on which the server is running.
3.For port numbers if changed keep the port number same for client and server and use preferably a number higher than 10000(I have used 10444).
4.In the mServer1.java you can change the retrycountervary variable which will change the number of retries by the server to send the file to the client.
5.As class files are already made be sure to type javac and java commands and not makefile command if the source code is changed.

Note:
1.For Case 2, the retries sometimes works only till 3 or 4 inspite of setting to 5. But when we run again it works till 5.
2.For Case 3, the file F1.txt does not exist in Server folder of A2Case3 so it will give an error message of file not existing and close the connection.
So, you can copy paste F1.txt from any other cases(1 or 2) into the server folder of A2Case3 and then run the program and it will transfer the file.
3.I have already done the .class compilation because the makefile immediately executes even though it has the code for compilation.

Contents of the Folder NairA2:
1.Upon unzipping or extracting NairA2 folder we can see the 3 case folders namely A2Case1, A2Case2 and A2Case3;the Sampleoutputs and SampleOutputsforcases folders 
and the DC Assignment Report Arvind Nair.docx and DC Assignment Report Arvind Nair.pdf which is the project report in word and pdf formats and finally the readme.txt.
2.The 3 case folders have client and server folders which contain the source codes and themakefiles respectively for both.
3.The Sampleoutputs folder contains the following outputs:
3.1 output1.txt: It contains the case where the user types the wrong credentials.
3.2 output2.txt: It contains the case where the specified file does not exist.
3.3 output3.txt: It contains the case where the file transfer is successful.
3.4 output4.txt: It contains the case where the file transfer was retried till the limit.
4.The SampleOutputsforcases folder contains the outputs for the 3 Cases(Refer to Report).
