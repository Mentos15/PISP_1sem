// ServerNP.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#define _WINSOCK_DEPRECATED_NO_WARNINGS
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
	try
	{
		
		while (1)
		{
			
			if ((sHandle = CreateNamedPipe(
				TEXT("\\\\.\\pipe\\Tube"),
				PIPE_ACCESS_DUPLEX, //дуплексный канал
				PIPE_TYPE_MESSAGE | PIPE_WAIT | PIPE_ACCEPT_REMOTE_CLIENTS, // сообщения|синхронный
				PIPE_UNLIMITED_INSTANCES, NULL, NULL, //limit , isize,osize
				INFINITE, NULL)) == INVALID_HANDLE_VALUE)
				throw SetErrorMsgText("create:", GetLastError());
		

			if (!ConnectNamedPipe(sHandle, NULL)) // ожидать клиента
				throw SetErrorMsgText("connect:", GetLastError());

			char buffer[128];
			DWORD numBytesRead;

			if (
				!ReadFile(
					sHandle,
					buffer, 
					sizeof(buffer), 
					&numBytesRead, 
					NULL) 
				)throw SetErrorMsgText("ReadFile:", GetLastError());


			buffer[numBytesRead] = '\0'; 
			cout << buffer << endl;

			char sendMes[] = "Halo form server";
			DWORD sendByteCount;

			if (
				!WriteFile(
					sHandle, 
					sendMes,
					strlen(sendMes) , 
					&sendByteCount, 
					NULL) 
				)throw SetErrorMsgText("WriteFile:", GetLastError());
		}
		//..................................................................

		DisconnectNamedPipe(sHandle);
		CloseHandle(sHandle);
	}

	catch (string s) {
		cout << endl << s;
	}
	system("pause");
}

