// ClientB.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#define _WINSOCK_DEPRECATED_NO_WARNINGS
#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <string>
#include <WS2tcpip.h>
#include <time.h>
#include "Winsock2.h"
#pragma comment(lib, "WS2_32.lib")
using namespace std;


string serverName = "Hello";

string GetErrorMsgText(int code) // cформировать текст ошибки
{
	string msgText;
	switch (code) // проверка кода возврата
	{
	case WSAEINTR: msgText = "WSAEINTR"; break;
	case WSAEACCES: msgText = "WSAEACCES"; break;
		//..........коды WSAGetLastError ..........................
	case WSASYSCALLFAILURE: msgText = "WSASYSCALLFAILURE"; break;
	default: msgText = "***ERROR***"; break;
	};
	return msgText;
};
string SetErrorMsgText(string msgText, int code)
{
	cout << code;
	return msgText + GetErrorMsgText(code);
};
string charToString(char mes[], int size) {
	string finalMes;
	for (int i = 0; i < size; i++) {
		finalMes += mes[i];
	}
	return finalMes;
}
bool GetServer(SOCKET clientSocket, sockaddr* from, int size) {
	int sendCount;
	int getCount;
	char getMes[50];
	
	int len = sizeof(sockaddr_in);
	

	if ((sendCount = sendto(clientSocket, serverName.c_str(), serverName.size(), NULL, from, size)) == SOCKET_ERROR)
		throw SetErrorMsgText("sendto:", WSAGetLastError());

	if ((getCount = recvfrom(clientSocket, getMes, sizeof(getMes), NULL, from, &len)) == SOCKET_ERROR)
		throw SetErrorMsgText("recvfrom:", WSAGetLastError());

	string mesFromServer = charToString(getMes, getCount);
	cout << mesFromServer<< endl;
	if (mesFromServer == serverName) {
		return true;
	}
	return false;
}



int main()
{

	SOCKET clientSocket;
	WSADATA wsaData;
	try
	{
		if (WSAStartup(MAKEWORD(2, 0), &wsaData) != 0)
			throw SetErrorMsgText("Startup:", WSAGetLastError());

		if ((clientSocket = socket(AF_INET, SOCK_DGRAM, NULL)) == INVALID_SOCKET)
			throw SetErrorMsgText("socket:", WSAGetLastError());


		int optval = 1;
		if (setsockopt(clientSocket, SOL_SOCKET, SO_BROADCAST,(char*)&optval, sizeof(int)) == SOCKET_ERROR)
			throw SetErrorMsgText("opt:", WSAGetLastError());

		SOCKADDR_IN clientSocketParam; 
		int clientSocketSize = sizeof(SOCKADDR_IN);
		clientSocketParam.sin_family = AF_INET;
		clientSocketParam.sin_port = htons(2000); 
		
		clientSocketParam.sin_addr.s_addr = inet_addr("192.168.56.255");

			if (GetServer(clientSocket, (sockaddr*)&clientSocketParam, clientSocketSize)) {
				cout << "SERVER ip - " << inet_ntoa(clientSocketParam.sin_addr) << endl;
				cout << "SERVER port - " << htons(clientSocketParam.sin_port) << endl;
			}
		


		if (closesocket(clientSocket) == SOCKET_ERROR)
			throw SetErrorMsgText("closesocket", WSAGetLastError());

		if (WSACleanup() == SOCKET_ERROR)
			throw SetErrorMsgText("WSACleanup", WSAGetLastError());

	}
	catch (string msg) {
		cout << "\n" << msg;
	}
	system("pause");
}







