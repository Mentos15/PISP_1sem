// ClientS.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
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
string hostName = "DESKTOP-FU09168";

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
}

string SetErrorMsgText(string msgText, int code)
{
	cout << code;
	return msgText + GetErrorMsgText(code);
}

string charToString(char mes[], int size) {
	string finalMes;
	for (int i = 0; i < size; i++) {
		finalMes += mes[i];
	}
	return finalMes;
}

bool GetServerByName(SOCKET clientSocket) 
{

	 hostent* he;
	 in_addr** addr_list;

		if ((he = gethostbyname(hostName.c_str())) == NULL) {
			throw SetErrorMsgText("gethostbyname", WSAGetLastError());
		}
			addr_list = (struct in_addr**)he->h_addr_list;
			for (int i = 0; addr_list[i] != NULL; i++) {
				

				cout << inet_ntoa(*addr_list[i])<<endl;

				sockaddr_in clientSocketParam;

				clientSocketParam.sin_family = AF_INET;
				clientSocketParam.sin_port = htons(2000);
				clientSocketParam.sin_addr.s_addr = inet_addr(inet_ntoa(*addr_list[i]));


				int sendCount;
				int getCount;
				char getMes[50];

				int len = sizeof(clientSocketParam);


				if ((sendCount = sendto(clientSocket, serverName.c_str(), serverName.size(), NULL, (sockaddr*)&clientSocketParam, sizeof(clientSocketParam))) == SOCKET_ERROR)
					throw SetErrorMsgText("sendto:", WSAGetLastError());

				if ((getCount = recvfrom(clientSocket, getMes, sizeof(getMes), NULL, (sockaddr*)&clientSocketParam, &len)) == SOCKET_ERROR)
					throw SetErrorMsgText("recvfrom:", WSAGetLastError());

				string mesFromServer = charToString(getMes, getCount);

				if (mesFromServer == serverName) {
					cout <<"Host ip "<<inet_ntoa(*addr_list[i]) << endl;
					return true;
				}
			}
	return false;


	
}


int main()
{
	WSADATA data;
	SOCKET clientSocket;
	try {
		if (WSAStartup(MAKEWORD(2, 0), &data) != 0)
			throw SetErrorMsgText("WSAStartup", WSAGetLastError());

		if((clientSocket = socket(AF_INET , SOCK_DGRAM , NULL))==INVALID_SOCKET)
			throw SetErrorMsgText("socket", WSAGetLastError());

		

		if (GetServerByName(clientSocket)){
			cout<<"Server was found"<<endl;
				while (1) {}
		}
		
		
		if(closesocket(clientSocket)==SOCKET_ERROR)
			throw SetErrorMsgText("closesocket", WSAGetLastError());

		if(WSACleanup()==SOCKET_ERROR)
			throw SetErrorMsgText("WSAStartup", WSAGetLastError());

	}
	catch (string msg) {
		cout << endl << msg;
	}

}


