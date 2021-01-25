// Server.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#include <iostream>
#include <string>
#include <WS2tcpip.h>
#include <time.h>
#include "Winsock2.h"
#pragma comment(lib, "WS2_32.lib")
#define _WINSOCK_DEPRECATED_NO_WARNINGS
using namespace std;

string GetErrorMsgText(int code) 
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
	return msgText + GetErrorMsgText(code);
};

int main()
{
	SOCKET sS; // дескриптор сокета
	WSADATA wsaData;
	try
	{
		if (WSAStartup(MAKEWORD(2, 0), &wsaData) != 0)		
			throw SetErrorMsgText("Startup:", WSAGetLastError());

		if ((sS = socket(AF_INET, SOCK_STREAM, NULL)) == INVALID_SOCKET)
			throw SetErrorMsgText("socket:", WSAGetLastError());

		SOCKADDR_IN serv; // параметры сокета sS
		serv.sin_family = AF_INET; // используется IP-адресация
		serv.sin_port = htons(2000); // порт 2000
		serv.sin_addr.s_addr = INADDR_ANY; 
		


		if (bind(sS, (LPSOCKADDR)&serv, sizeof(serv)) == SOCKET_ERROR) // связывает сокет с параметрами
			throw SetErrorMsgText("bind:", WSAGetLastError());

		if (listen(sS, 10) == SOCKET_ERROR)
			throw SetErrorMsgText("listen:", WSAGetLastError());

		SOCKET cS; // сокет для обмена данными с клиентом
		SOCKADDR_IN clnt; // параметры сокета клиента
		memset(&clnt, 0, sizeof(clnt)); // обнулить память
		int lclnt = sizeof(clnt); // размер SOCKADDR_IN
		while (1) {
			if ((cS = accept(sS, (sockaddr*)&clnt, &lclnt)) == INVALID_SOCKET) 
				throw SetErrorMsgText("accept:", WSAGetLastError());
			else {

				if (1) {
					char ibuf[50];
					int libuf = 0, //количество принятых байт
						lobuf = 0; //количество отправленных байт
					while (1) {

						if ((libuf = recv(cS, ibuf, sizeof(ibuf), NULL)) == SOCKET_ERROR)
							throw SetErrorMsgText("recv:", WSAGetLastError());
						if (libuf == 3) {
							char end[3] = { ibuf[0],ibuf[1],ibuf[2] };
							if (strcmp(end, "end")) {
								if ((lobuf = send(cS, "end", strlen("end"), NULL)) == SOCKET_ERROR)
									throw SetErrorMsgText("send:", WSAGetLastError());
								break;
							}

						}
						else {
							ibuf[libuf] = '\0';
							cout << "get count = " << libuf << " get message: " << ibuf << endl;
							if ((lobuf = send(cS, "", strlen("") + 1, NULL)) == SOCKET_ERROR)
								throw SetErrorMsgText("send:", WSAGetLastError());
						}

					}
				}

				clock_t start, stop;
				if (1) {
					char ibuf[50];
					int libuf = 0, //количество принятых байт
						lobuf = 0; //количество отправленных байт

					start = clock();
					while (1) {

						if ((libuf = recv(cS, ibuf, sizeof(ibuf), NULL)) == SOCKET_ERROR)
							throw SetErrorMsgText("recv:", WSAGetLastError());
						if (libuf == 3) {
							char end[3] = { ibuf[0],ibuf[1],ibuf[2] };
							if (strcmp(end, "end")) {
								if ((lobuf = send(cS, "end", strlen("end"), NULL)) == SOCKET_ERROR)
									throw SetErrorMsgText("send:", WSAGetLastError());
								break;
							}

						}
						else {
							ibuf[libuf] = '\0';
							cout << "get count = " << libuf << " get message: " << ibuf << endl;
							if ((lobuf = send(cS, ibuf, strlen(ibuf), NULL)) == SOCKET_ERROR)
								throw SetErrorMsgText("send:", WSAGetLastError());
						}

					}
					stop = clock();
				}


				cout << -(start - stop) << endl;

				//...........................................................
			}
		}
		if (closesocket(cS) == SOCKET_ERROR)
			throw SetErrorMsgText("closesocket:", WSAGetLastError());

		if (closesocket(sS) == SOCKET_ERROR)
			throw SetErrorMsgText("closesocket:", WSAGetLastError());

		if (WSACleanup() == SOCKET_ERROR)
			throw SetErrorMsgText("Cleanup:", WSAGetLastError());
	}
	catch (string errorMsgText)
	{
		cout << endl << errorMsgText;
	}
}


