#define _WINSOCK_DEPRECATED_NO_WARNINGS
#include "Winsock2.h"
#include <iostream>
#include <string>
#include <time.h>
#pragma comment(lib, "WS2_32.lib")

using namespace std;

string  GetErrorMsgText(int code)    // cформировать текст ошибки 
{
    string msgText;
    switch (code)                      // проверка кода возврата  
    {
    case WSAEINTR:          msgText = "WSAEINTR";         break;
    case WSAEACCES:         msgText = "WSAEACCES";        break;
    case WSAEFAULT:         msgText = "WSAEFAULT";        break;
    case WSAEINVAL:         msgText = "WSAEINVAL";        break;
    case WSAEMFILE:         msgText = "WSAEMFILE";        break;
    case WSAEWOULDBLOCK:    msgText = "WSAEWOULDBLOCK";        break;
    case WSAEINPROGRESS:         msgText = "WSAEINPROGRESS";        break;
    case WSAEALREADY:         msgText = "WSAEALREADY";        break;
    case WSAENOTSOCK:         msgText = "WSAENOTSOCK";        break;
    case WSAEDESTADDRREQ:         msgText = "WSAEDESTADDRREQ";        break;
    case WSAEMSGSIZE:         msgText = "WSAEMSGSIZE";        break;
    case WSAEPROTOTYPE:         msgText = "WSAEPROTOTYPE";        break;
    case WSAENOPROTOOPT:         msgText = "WSAENOPROTOOPT";        break;
    case WSAEPROTONOSUPPORT:         msgText = "WSAEPROTONOSUPPORT";        break;
    case WSAESOCKTNOSUPPORT:         msgText = "WSAESOCKTNOSUPPORT";        break;
    case WSAEOPNOTSUPP:         msgText = "WSAEOPNOTSUPP";        break;
    case WSAEPFNOSUPPORT:         msgText = "WSAEPFNOSUPPORT";        break;
    case WSAEAFNOSUPPORT:         msgText = "WSAEAFNOSUPPORT";        break;
    case WSAEADDRINUSE:         msgText = "WSAEADDRINUSE";        break;
    case WSAEADDRNOTAVAIL:         msgText = "WSAEADDRNOTAVAIL";        break;
    case WSAENETDOWN:         msgText = "WSAENETDOWN";        break;
    case WSAENETUNREACH:         msgText = "WSAENETUNREACH";        break;
    case WSAENETRESET:         msgText = "WSAENETRESET";        break;
    case WSAECONNABORTED:         msgText = "WSAECONNABORTED";        break;
    case WSAECONNRESET:         msgText = "WSAECONNRESET";        break;
    case WSAENOBUFS:         msgText = "WSAENOBUFS";        break;
    case WSAEISCONN:         msgText = "WSAEISCONN";        break;
    case WSAENOTCONN:         msgText = "WSAENOTCONN";        break;
    case WSAESHUTDOWN:         msgText = "WSAESHUTDOWN";        break;
    case WSAETIMEDOUT:         msgText = "WSAETIMEDOUT";        break;
    case WSAECONNREFUSED:         msgText = "WSAECONNREFUSED";        break;
    case WSAEHOSTDOWN:         msgText = "WSAEHOSTDOWN";        break;
    case WSAEHOSTUNREACH:         msgText = "WSAEHOSTUNREACH";        break;
    case WSAEPROCLIM:         msgText = "WSAEPROCLIM";        break;
    case WSASYSNOTREADY:         msgText = "WSASYSNOTREADY";        break;
    case WSAVERNOTSUPPORTED:         msgText = "WSAVERNOTSUPPORTED";        break;
    case WSANOTINITIALISED:         msgText = "WSANOTINITIALISED";        break;
    case WSAEDISCON:         msgText = "WSAEDISCON";        break;
    case WSATYPE_NOT_FOUND:         msgText = "WSATYPE_NOT_FOUND";        break;
    case WSAHOST_NOT_FOUND:         msgText = "WSAHOST_NOT_FOUND";        break;
    case WSATRY_AGAIN:         msgText = "WSATRY_AGAIN";        break;
    case WSANO_RECOVERY:         msgText = "WSANO_RECOVERY";        break;
    case WSANO_DATA:         msgText = "WSANO_DATA";        break;
    case WSA_INVALID_HANDLE:         msgText = "WSA_INVALID_HANDLE";        break;
    case WSA_INVALID_PARAMETER:         msgText = "WSA_INVALID_PARAMETER";        break;
    case WSA_IO_INCOMPLETE:         msgText = "WSA_IO_INCOMPLETE";        break;
    case WSA_IO_PENDING:         msgText = "WSA_IO_PENDING";        break;
    case WSA_NOT_ENOUGH_MEMORY:         msgText = "WSA_NOT_ENOUGH_MEMORY";        break;
    case WSA_OPERATION_ABORTED:         msgText = "WSA_OPERATION_ABORTED";        break;
    case WSAEINVALIDPROCTABLE:         msgText = "WSAINVALIDPROCTABLE";        break;
    case WSAEINVALIDPROVIDER:         msgText = "WSAINVALIDPROVIDER";        break;
    case WSAEPROVIDERFAILEDINIT:         msgText = "WSAPROVIDERFAILEDINIT";        break;
    case WSASYSCALLFAILURE: msgText = "WSASYSCALLFAILURE"; break;
    default:                msgText = "***ERROR***";      break;
    };
    return msgText;
};
string  SetErrorMsgText(string msgText, int code)
{
    return  msgText + GetErrorMsgText(code);
};

int main()
{
    WSADATA wsaData;
    clock_t start, stop;
    try
    {
        if (WSAStartup(MAKEWORD(2, 0), &wsaData) != 0)
            throw  SetErrorMsgText("Startup:", WSAGetLastError());
        SOCKET  cC;                          // серверный сокет
        if ((cC = socket(AF_INET, SOCK_DGRAM, NULL)) == INVALID_SOCKET)
            throw  SetErrorMsgText("socket:", WSAGetLastError());

        //...........................................................

        SOCKADDR_IN serv; // параметры сокета сервера
        serv.sin_family = AF_INET; // используется ip-адресация
        serv.sin_port = htons(2000); // порт 2000
        serv.sin_addr.s_addr = inet_addr("192.168.56.1"); // адрес сервера
        //char obuf[50] = "client: I here"; //буфер вывода
        //int lobuf = 0; //количество отправленных


        char hello[20] = "Hello from Client ",
            result[50],
            number[5],
            ibuf[50];
        int count = 0,
            i = 0,
            libuf = 0,
            lobuf = 0;                    //количество отправленных байь 
        cout << "Enter number of messages to send: ";
        cin >> count;
        int lc = sizeof(serv);
        start = clock();
        while (i < count) {
            strcpy_s(result, hello);
            sprintf_s(number, "%d", i);
            strcat_s(result, number);
            if ((lobuf = sendto(cC, result, strlen(result) + 1, NULL, (sockaddr*)&serv, sizeof(serv))) == SOCKET_ERROR)
                throw SetErrorMsgText("recv:", WSAGetLastError());
            //if ((libuf = recvfrom(cC, ibuf, sizeof(ibuf), NULL, (sockaddr*)&serv, &lc)) == SOCKET_ERROR)
            //    throw SetErrorMsgText("recv:", WSAGetLastError());
            //else
      
            i++;
            cout << ibuf << endl;
            memset(result, 0, sizeof result);
            memset(number, 0, sizeof number);
        }
        stop = clock();
        printf("Time required %f seconds", float(stop - start) / CLOCKS_PER_SEC);

        char end[2] = "";
        if ((lobuf = send(cC, end, strlen(end) + 1, NULL)) == SOCKET_ERROR)
            throw  SetErrorMsgText("send:", WSAGetLastError());

        //...........................................................
        if (closesocket(cC) == SOCKET_ERROR)
            throw  SetErrorMsgText("closesocket:", WSAGetLastError());
        if (WSACleanup() == SOCKET_ERROR)
            throw  SetErrorMsgText("Cleanup:", WSAGetLastError());

    }
    catch (string errorMsgText)
    {
        cout << endl << errorMsgText;
    }
    system("pause");
    return 0;
}
