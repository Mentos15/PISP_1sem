// ClientNP.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
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
	HANDLE cHandle; // дескриптор канала
	WSADATA wsaData;
	try
	{
		if (WSAStartup(MAKEWORD(2, 0), &wsaData) != 0)
			throw SetErrorMsgText("Startup:", WSAGetLastError());
		if ((cHandle = CreateFile(
			TEXT("\\\\192.168.56.1\\pipe\\Tube"),
			GENERIC_READ | GENERIC_WRITE,
			FILE_SHARE_READ | FILE_SHARE_WRITE,
			NULL, OPEN_ALWAYS, 
			NULL,
			NULL)) == INVALID_HANDLE_VALUE)
			throw SetErrorMsgText("createfile:", GetLastError());

		char sendMes[] = "Halo form client";
		DWORD sendByteCount;
		
		if(
			!WriteFile(
			cHandle, 
			sendMes, 
			strlen(sendMes) * sizeof(wchar_t), 
			&sendByteCount, 
			NULL) 
		)throw SetErrorMsgText("WriteFile:", GetLastError());

		char readMes[128];
		DWORD readByteCount;

		if (
			!ReadFile(
				cHandle,
				readMes, 
				sizeof(readMes),
				&readByteCount, 
				NULL) 
			)throw SetErrorMsgText("ReadFile:", GetLastError());


		readMes[readByteCount] = '\0';
		cout << readMes << endl;

		//..................................................................

	
		CloseHandle(cHandle);

		if (WSACleanup() == SOCKET_ERROR)
			throw SetErrorMsgText("WSACleanup", WSAGetLastError());
	}

	catch (string s) {
		cout << endl << s;
   }
	system("pause");
}


