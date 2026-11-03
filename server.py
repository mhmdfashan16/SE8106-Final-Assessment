import socket

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind(('127.0.0.1', 5001))
server_socket.listen(1)
print("Server started on port 5001")

conn, addr = server_socket.accept()
print("Client connected")

while True:
    # Receive from client
    data = conn.recv(1024).decode()
    print(f"Client: {data}")
    
    if data.lower() == "exit":
        conn.close()
        break
    

    # Send reply
    message = input("Server: ")
    conn.send(message.encode())

    

conn.close()
server_socket.close()