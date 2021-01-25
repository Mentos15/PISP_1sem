// ServerB.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//
#define _WINSOCK_DEPRECATED_NO_WARNINGS
#include <iostream>
#include <string>
#include <WS2tcpip.h>
#include <time.h>
#include "Winsock2.h"
#pragma comment(lib, "WS2_32.lib")



using namespace std;

bool stop = false;
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
	cout << code << endl;
	return msgText + GetErrorMsgText(code);
};

string charToString(char mes[], int size) {
	string finalMes;
	for (int i = 0; i < size; i++) {
		finalMes += mes[i];
	}
	return finalMes;
}

bool GetRequestFromClient(SOCKET serverSocket, sockaddr* from, int  size) {
	int getByteCount;
	char getMes[500];
	

	if ((getByteCount = recvfrom(serverSocket, getMes, sizeof(getMes), NULL, from, (&size))) == SOCKET_ERROR)
		throw SetErrorMsgText("recvfrom", WSAGetLastError());

	string mesFromClient = charToString(getMes, getByteCount);
	cout << getByteCount << endl;
	if (mesFromClient == serverName) {
		return true;
	}
	return false;
}

bool PutAnswerToClient(SOCKET serverSocket, sockaddr* to, int size) {
	int sendByte;

	if ((sendByte = sendto(serverSocket, serverName.c_str(), serverName.size(), NULL, to, size)) == SOCKET_ERROR)
		throw SetErrorMsgText("sendto", WSAGetLastError());

	return true;
}

int checkAnotherServ() {
	int count = 0;
	SOCKET serverSocket = NULL; // дескриптор сокета
	WSADATA wsaData;
	try
	{

		if (WSAStartup(MAKEWORD(2, 0), &wsaData) != 0)
			throw SetErrorMsgText("Startup:", WSAGetLastError());

		if ((serverSocket = socket(AF_INET, SOCK_DGRAM, NULL)) == INVALID_SOCKET)
			throw SetErrorMsgText("socket:", WSAGetLastError());

		int optval = 1;
		if (setsockopt(serverSocket, SOL_SOCKET, SO_RCVTIMEO, (char*)&optval, sizeof(int)) == SOCKET_ERROR)
			throw SetErrorMsgText("opt:", WSAGetLastError());

		SOCKADDR_IN serverSocketParam;
		sockaddr* addr;
		serverSocketParam.sin_family = AF_INET;
		serverSocketParam.sin_port = htons(2000);
		for (int i = 1; i < 255; i++) {
			string s = "192.168.56." + to_string(i);
			
			serverSocketParam.sin_addr.s_addr = inet_addr(s.c_str());

			int lc = sizeof(SOCKADDR_IN);
			if (PutAnswerToClient(serverSocket, (sockaddr*)&serverSocketParam, lc)) {

				int getByteCount;
				char getMes[500];
				int getMesSize = 50;
				//cout << i << endl;
				if ((getByteCount = recvfrom(serverSocket, getMes, getMesSize, NULL, (sockaddr*)&serverSocketParam, &lc)) == SOCKET_ERROR) {
					//continue;
				}
				else {

					string mesFromClient = charToString(getMes, getByteCount);

					if (mesFromClient == serverName) {
						cout << "Antoher server ip - " << inet_ntoa(serverSocketParam.sin_addr) << endl;
						cout << "Antoher server  port - " << htons(serverSocketParam.sin_port) << endl;
						count++;
						break;
					}
				}	
			}
		}
		if (closesocket(serverSocket) == SOCKET_ERROR)
			throw SetErrorMsgText("closesocket", WSAGetLastError());

		if (WSACleanup() == SOCKET_ERROR)
			throw SetErrorMsgText("WSACleanup", WSAGetLastError());
	}
	catch (string errorMsgText)
	{
		cout << endl << errorMsgText;
	}
	return count;
}


int main()
{
	cout <<"Server count "<<checkAnotherServ()<<endl;
	SOCKET serverSocket = NULL; // дескриптор сокета
	WSADATA wsaData;
	try
	{

		if (WSAStartup(MAKEWORD(2, 0), &wsaData) != 0)
			throw SetErrorMsgText("Startup:", WSAGetLastError());

		if ((serverSocket = socket(AF_INET, SOCK_DGRAM, NULL)) == INVALID_SOCKET)
			throw SetErrorMsgText("socket:", WSAGetLastError());

		SOCKADDR_IN serverSocketParam;
		sockaddr* addr;
		serverSocketParam.sin_family = AF_INET;
		serverSocketParam.sin_port = htons(2000);
		serverSocketParam.sin_addr.s_addr = INADDR_ANY;

		if (bind(serverSocket, (LPSOCKADDR)&serverSocketParam, sizeof(serverSocketParam)) == SOCKET_ERROR)
			throw SetErrorMsgText("bind:", WSAGetLastError());

		int lc = sizeof(SOCKADDR_IN);

		while (1) {
			if (1) {

				if (GetRequestFromClient(serverSocket, (sockaddr*)&serverSocketParam, lc)) {
					cout << "Client ip - " << inet_ntoa(serverSocketParam.sin_addr) << endl;
					cout << "Client port - " << htons(serverSocketParam.sin_port) << endl;
					PutAnswerToClient(serverSocket, (sockaddr*)&serverSocketParam, sizeof(serverSocketParam));
					in_addr ip;
					ip.s_addr = inet_addr("127.0.0.1");
					
					cout <<"Host name " <<gethostbyaddr((char*)&ip.s_addr, sizeof(ip.s_addr), AF_INET)->h_name << endl;
					ip.s_addr = inet_addr(inet_ntoa(serverSocketParam.sin_addr));
					cout << "Client host name " << gethostbyaddr((char*)&ip.s_addr, sizeof(ip.s_addr), AF_INET)->h_name << endl;

				}

			}
		}
		if (closesocket(serverSocket) == SOCKET_ERROR)
			throw SetErrorMsgText("closesocket", WSAGetLastError());

		if (WSACleanup() == SOCKET_ERROR)
			throw SetErrorMsgText("WSACleanup", WSAGetLastError());
	}
	catch (string errorMsgText)
	{
		cout << endl << errorMsgText;
	}
	system("pause");
}
