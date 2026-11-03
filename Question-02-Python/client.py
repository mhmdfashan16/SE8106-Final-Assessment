import socket

client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect(('127.0.0.1', 5001))
print("Connected to server")

while True:
    # Send to server
    message = input("Client: ")
    client_socket.send(message.encode())
    
    if message.lower() == "exit":
        client_socket.close()
        break

    # Receive reply
    data = client_socket.recv(1024).decode()
    print(f"Server: {data}")

    

client_socket.close()