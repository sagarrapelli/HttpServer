## cs457-557-f19-pa1-sagarrapelli

#Steps to run the server program
1. Enter command 'make' to compile.
2. Enter command 'java Server' to run the program

#Implementation details

I have implemented the program in Java using Java Socket programming. 
For reading/writing to and from the sockets I have refered from

"https://docs.oracle.com/javase/tutorial/networking/sockets/index.html"

A Server socket is created on an available port and it starts accepting client requests. When the server gets a new request from a client it creates a new thread to serve that client request and then it closes the connection. The server sends an error message 404 when it does not have the resource required.


#Sample input/output on remote.cs machines.

Output when server is started(remote04)
Server started at remote04.cs.binghamton.edu:34071

1. If server has requested file

Input at client(remote03)

wget http://remote04.cs.binghamton.edu:34071/test.html

Output at client:-

Resolving remote04.cs.binghamton.edu (remote04.cs.binghamton.edu)... 128.226.114.204
Connecting to remote04.cs.binghamton.edu (remote04.cs.binghamton.edu)|128.226.114.204|:34071... connected.
HTTP request sent, awaiting response... 200 OK
Length: 102 [text/html]
Saving to: ‘test.html’

test.html           100%[===================>]     102  --.-KB/s    in 0s

2019-09-24 19:21:57 (16.3 MB/s) - ‘test.html’ saved [102/102]

Output at server(remote04):-

/test.html|128.226.114.203|60830|1


2. If server doesn't have requested file

Input at client(remote03)
wget http://remote04.cs.binghamton.edu:34071/pdf-samphle.pdf

Output at client

Resolving remote04.cs.binghamton.edu (remote04.cs.binghamton.edu)... 128.226.114.204
Connecting to remote04.cs.binghamton.edu (remote04.cs.binghamton.edu)|128.226.114.204|:34071... connected.
HTTP request sent, awaiting response... 404 Not Found
2019-09-24 19:40:36 ERROR 404: Not Found.
