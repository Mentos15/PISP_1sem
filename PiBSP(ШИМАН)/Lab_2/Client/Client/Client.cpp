// Client.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#include <iostream>
#include <string>
#include <WS2tcpip.h>
#include <time.h>
#include "Winsock2.h"
#pragma comment(lib, "WS2_32.lib")
#define _WINSOCK_DEPRECATED_NO_WARNINGS
using namespace std;



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
		//serv.sin_addr.s_addr = inet_addr("127.0.0.1");
		inet_pton(AF_INET, "192.168.56.1", &serv.sin_addr);
		//172.16.13.129
		//192.168.43.121



		if (connect(sS, (LPSOCKADDR)&serv, sizeof(SOCKADDR_IN)) == SOCKET_ERROR)
			throw SetErrorMsgText("connect:", WSAGetLastError());
		int libuf = 0, //количество принятых байт
			lobuf = 0; //количество отправленных байь
		char ibuf[50]; //буфер ввода
		if (1) {
			int count = 1000;
			int mes = 0;
			while (1) {
				if (mes == count) {
					if ((lobuf = send(sS, "end", strlen("end"), NULL)) == SOCKET_ERROR)
						throw SetErrorMsgText("send:", WSAGetLastError());

					cout << "send count = " << lobuf << endl;

					if ((libuf = recv(sS, ibuf, sizeof(ibuf), NULL)) == SOCKET_ERROR)
						throw SetErrorMsgText("recv:", WSAGetLastError());
					ibuf[libuf] = '\0';
					cout << "last mes = " << ibuf << endl;
					break;
				}
				char obuf1[50] = "Hello "; //буфер вывода
				char buff[20];
				_itoa_s(mes, buff, 10);
				strcat_s(obuf1, buff);

				if ((lobuf = send(sS, obuf1, strlen(obuf1), NULL)) == SOCKET_ERROR)
					throw SetErrorMsgText("send:", WSAGetLastError());
				cout << "send count = " << lobuf << endl;

				if ((libuf = recv(sS, ibuf, sizeof(ibuf), NULL)) == SOCKET_ERROR)
					throw SetErrorMsgText("recv:", WSAGetLastError());

				mes++;

				//	cout << "get count = " << libuf<<" get message: "<< ibuf << endl;
					//...........................................................
			}

		}
		clock_t start, stop;
		if (1) {
			int mes = 0;
			int count = 200;
			cout << "Write\n";

			char sendMes[2] = { (char)2 };
			start = clock();
			while (1) {
				if (count == mes) {
					if ((lobuf = send(sS, "end", strlen("end"), NULL)) == SOCKET_ERROR)
						throw SetErrorMsgText("send:", WSAGetLastError());

					if ((libuf = recv(sS, ibuf, sizeof(ibuf), NULL)) == SOCKET_ERROR)
						throw SetErrorMsgText("recv:", WSAGetLastError());
					ibuf[libuf] = '\0';
					cout << "last mes = " << ibuf << endl;

					break;
				}
				//char obuf1[50] = "hello from client "; //буфер вывода
				//char buff[20];
			//	strcat(obuf1, itoa(mes, buff, 10));

				if ((lobuf = send(sS, sendMes, strlen(sendMes), NULL)) == SOCKET_ERROR)
					throw SetErrorMsgText("send:", WSAGetLastError());

				cout << "send count = " << lobuf << endl;


				if ((libuf = recv(sS, ibuf, sizeof(ibuf), NULL)) == SOCKET_ERROR)
					throw SetErrorMsgText("recv:", WSAGetLastError());
				ibuf[0]++;
				sendMes[0] = ibuf[0];
				mes++;

			}
			stop = clock();
			cout << (start - stop) << endl;
		}
		if (closesocket(sS) == SOCKET_ERROR)
			throw SetErrorMsgText("closesocket:", WSAGetLastError());

		if (WSACleanup() == SOCKET_ERROR)
			throw SetErrorMsgText("Cleanup:", WSAGetLastError());
	}
	catch (string errorMsgText)
	{
		cout << endl << errorMsgText;
	}
	system("pause");
}

