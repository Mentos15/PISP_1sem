// ServerMS.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#define _WINSOCK_DEPRECATED_NO_WARNINGS
#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <string>
#include <time.h>
#include <WS2tcpip.h>
#include <WS2tcpip.h>
#include "Winsock2.h"
#include "Windows.h"

#pragma comment(lib, "WS2_32.lib")




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
int main()
{

	HANDLE sHandle; // дескриптор канала
	WSADATA wsaData;

	int waitTime = 1000 * 60 * 3;//0xFFFFFFFF
	try
	{
		if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0)
			throw SetErrorMsgText("Startup:", WSAGetLastError());

		//cout << "Server gonna wait " << waitTime / 1000 << " s until it thows an exception" << endl;

		if ((sHandle = CreateMailslot(L"\\\\.\\mailslot\\myslot",
			1500,
			INFINITE, // ждать вечно
			NULL)) == INVALID_HANDLE_VALUE)
			throw SetErrorMsgText("CreateMailslot", WSAGetLastError());

		while (1) {
			char buf[300];
			DWORD readCount;
			if (!ReadFile(sHandle,
				buf, // буфер
				sizeof(buf), // размер буфера
				&readCount, // прочитано
				NULL))
				throw SetErrorMsgText("ReadFile", WSAGetLastError());


			buf[readCount] = '\0';
			cout << buf << endl;
		}
		if (WSACleanup() == SOCKET_ERROR)
			throw SetErrorMsgText("WSACleanup", WSAGetLastError());
	}

	catch (string s) {
		cout << endl << s;
	}
	system("pause");
}


