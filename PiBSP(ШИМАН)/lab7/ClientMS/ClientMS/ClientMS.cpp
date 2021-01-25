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

void writeToDesk(HANDLE сHandle) {
	char send[] = "Halo from client";
	DWORD sendByte;

	if ((сHandle = CreateFile(L"\\\\.\\mailslot\\myslot",
		GENERIC_WRITE, // будем писать в ящик
		FILE_SHARE_READ, // разрешаем одновременно читать
		NULL,
		OPEN_ALWAYS, // ящик уже есть
		NULL, NULL)) == INVALID_HANDLE_VALUE)
		throw SetErrorMsgText("CreateFile", WSAGetLastError());

	if (!WriteFile(сHandle,
		send, // буфер
		sizeof(send), // размер буфера
		&sendByte, // записано
		NULL))
		throw SetErrorMsgText("WriteFile", WSAGetLastError());

}
void writeToComp(HANDLE сHandle) {
	char send[] = "Halo from client";
	DWORD sendByte;

	if ((сHandle = CreateFile(L"\\\\DESKTOP-FU09168\\mailslot\\myslot",
		GENERIC_WRITE, // будем писать в ящик
		FILE_SHARE_READ, // разрешаем одновременно читать
		NULL,
		OPEN_ALWAYS, // ящик уже есть
		NULL, NULL)) == INVALID_HANDLE_VALUE)
		throw SetErrorMsgText("CreateFile", WSAGetLastError());

	if (!WriteFile(сHandle,
		send, // буфер
		sizeof(send), // размер буфера
		&sendByte, // записано
		NULL))
		throw SetErrorMsgText("WriteFile", WSAGetLastError());

}
int main()
{

	HANDLE сHandle = NULL; // дескриптор канала
	WSADATA wsaData;
	
	try
	{
		if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0)
			throw SetErrorMsgText("Startup:", WSAGetLastError());
	
			writeToComp(&сHandle);
			
		    writeToDesk(&сHandle);
			cout << "Data was sended to DESKTOP\n";
	
		
		CloseHandle(сHandle);

		if (WSACleanup() == SOCKET_ERROR)
			throw SetErrorMsgText("WSACleanup", WSAGetLastError());
	}

	catch (string s) {
		cout << endl << s;
	}
	system("pause");
}


