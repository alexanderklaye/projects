/*
 * DrawLibrary.hpp
 * author Alex Laye, some code provided by Garth Santor
 * date 2019-03-10
 * Header file for the library; contains bulk of the class object code. Bad pactice.
*/
#include <Windows.h>
#include <algorithm>
#include <iomanip>
#include <iostream>
#include <sstream>
#include <string>
#include <vector>
using namespace std;

//====================//
// Error Descriptions //
//====================//
string ErrorDescription(DWORD dwMessageID) {
	char* msg;
	auto c = FormatMessageA(
		/* flags */			FORMAT_MESSAGE_ALLOCATE_BUFFER | FORMAT_MESSAGE_FROM_SYSTEM | FORMAT_MESSAGE_IGNORE_INSERTS | FORMAT_MESSAGE_MAX_WIDTH_MASK,
		/* source*/			NULL,
		/* message ID */	dwMessageID,
		/* language */		MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT),
		/* buffer */		(LPSTR)&msg,
		/* size */			0,
		/* args */			NULL
	);

	string strMsg = (c == 0)
		? "unknown"
		: msg;
	LocalFree(msg);
	return strMsg;
}
//=======================//
// Error exception class //
//=======================//
#pragma region XError

/* Console error exception class. */
class XError {
public:
	using id_type = decltype(GetLastError());
	using file_type = char const *;
	using string_type = std::string;
private:
	id_type code_;
	int	line_;
	file_type file_;
public:
	XError(int line, file_type file) : code_(GetLastError()), line_(line), file_(file) {}
	auto code() const -> id_type { return code_; }
	auto line() const -> int { return line_; }
	auto file() const -> file_type { return file_; }

	string_type msg() const {
		ostringstream oss;
		oss << "Error: " << code() << "\n";
		oss << ErrorDescription(code()) << "\n";
		oss << "In: " << file() << "\n";
		oss << "Line: " << line() << "\n";
		return oss.str();
	}
};

/* Console error exception throw helper macro. */
#define THROW_IF_CONSOLE_ERROR(res) if(!res) throw XError(__LINE__,__FILE__)
#define THROW_CONSOLE_ERROR() throw XError(__LINE__,__FILE__)

#pragma endregion


class Board
{
protected:
	//================//
   // Console System //
  //================//
	WORD const FOREGROUND_BLACK = 0;
	WORD const FOREGROUND_WHITE = FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE;
	WORD const BACKGROUND_BLACK = 0;
	WORD const BACKGROUND_WHITE = BACKGROUND_RED | BACKGROUND_GREEN | BACKGROUND_BLUE;

	WORD BACKGROUND_COL = BACKGROUND_RED;

	HANDLE hConsoleInput, hConsoleOutput;
	CONSOLE_SCREEN_BUFFER_INFO	originalCSBI;
	CONSOLE_CURSOR_INFO			originalCCI;
	vector<CHAR_INFO>			originalBuffer;
	COORD						originalBufferCoord;
	DWORD						originalConsoleMode;
	WORD						currentConsoleWidth = 0;
	DWORD charsWritten;
	COORD cursorHomeCoord;

	//
	DWORD oldConsoleMode;

	HANDLE _hConsole;
	HANDLE _hConsoleIn;
	SMALL_RECT _rectWindow;

	int _screenHeight;
	int _screenWidth;
	int _mPosX;
	int _mPosY;
	//std::wstring _appName;
	LPCSTR _appName;

	bool done = false;
	bool mToggle = false;

	bool applicationQuitting = false;
	DWORD terminationEventIdx = -1;

	bool editControlHasFocus = false;
	string editControlString;
	string::size_type editControlCursorPos = 0;
	decltype(editControlCursorPos) editControlAperture = 0;

	bool buttonState = false;
	bool exitToggle = false;

	short m_keyNewState[256] = { 0 };
	short m_keyOldState[256] = { 0 };


public:
	//===============//
   // CTOR and DTOR //
  //===============//
	Board()
	{
		_hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
		_hConsoleIn = GetStdHandle(STD_INPUT_HANDLE);
		_mPosX = 0;
		_mPosY = 0;
		_screenWidth = 40;
		_screenHeight = 40;
		_appName = "AppName";
		ConsoleSetup();
		GamePlay();



	}
	~Board() { cout << "Exiting\n"; }

	void ConsoleSetup()
	{
		//set screen width/height
		/*_screenWidth = 40;
		_screenHeight = 40;*/
		// set size of screen buffer
		_rectWindow = { 0,0,1,1 };
		THROW_IF_CONSOLE_ERROR(SetConsoleWindowInfo(_hConsole, TRUE, &_rectWindow));
		//set screen buffer size
		COORD coord = { (short)_screenWidth, (short)_screenHeight };
		THROW_IF_CONSOLE_ERROR(SetConsoleScreenBufferSize(_hConsole, coord));
		THROW_IF_CONSOLE_ERROR(SetConsoleActiveScreenBuffer(_hConsole));
		// get buffer iinfo, check sizes allowed.
		CONSOLE_SCREEN_BUFFER_INFO csbi;
		THROW_IF_CONSOLE_ERROR(GetConsoleScreenBufferInfo(_hConsole, &csbi));
		// set window size
		_rectWindow = { 0,0,(short)_screenWidth - 1,(short)_screenHeight - 1 };
		THROW_IF_CONSOLE_ERROR(SetConsoleWindowInfo(_hConsole, TRUE, &_rectWindow));
		// flags for mouse input
		THROW_IF_CONSOLE_ERROR(SetConsoleMode(_hConsoleIn, ENABLE_EXTENDED_FLAGS | ENABLE_WINDOW_INPUT | ENABLE_MOUSE_INPUT));

	}

	//================//
   // Event Handlers //
  //================//
#pragma region EventHandlers

// Control Event Handler
	BOOL CtrlHandler(DWORD ctrlType) {
		if (ctrlType <= CTRL_CLOSE_EVENT) {
			terminationEventIdx = ctrlType;
			applicationQuitting = true;
			return TRUE;
		}

		return FALSE;
	}
	virtual void ProcessKeyEvent(KEY_EVENT_RECORD const& ker) {}


#pragma endregion
	//================//
   // Mouse Handlers //
  //================//
	virtual void MouseEventPainter(MOUSE_EVENT_RECORD const& mer) {
		auto bufferLoc = mer.dwMousePosition;
		cursorHomeCoord = { bufferLoc.X, bufferLoc.Y };

		switch (mer.dwEventFlags) {
		case 0:	// button pressed or released
		{
			auto mask = mer.dwButtonState;
			if (mask&FROM_LEFT_1ST_BUTTON_PRESSED)
			{
				THROW_IF_CONSOLE_ERROR(FillConsoleOutputAttribute(hConsoleOutput, BACKGROUND_COL, 1, cursorHomeCoord, &charsWritten));
				//THROW_IF_CONSOLE_ERROR(FillConsoleOutputCharacterA(hConsoleOutput, 'x', 1, cursorHomeCoord, &charsWritten));
				//pTurnCount(bufferLoc.X, bufferLoc.Y);

			}
			if (mask&RIGHTMOST_BUTTON_PRESSED)
			{
				if (BACKGROUND_COL == NULL)
				{
					BACKGROUND_COL = BACKGROUND_RED;
				}
				else if (BACKGROUND_COL == BACKGROUND_RED)
				{
					BACKGROUND_COL = BACKGROUND_BLUE;
				}
				else if (BACKGROUND_COL == BACKGROUND_BLUE)
				{
					BACKGROUND_COL = BACKGROUND_GREEN;
				}
				else if (BACKGROUND_COL == BACKGROUND_GREEN)
				{
					BACKGROUND_COL = BACKGROUND_RED;
				}

			}
		}break;

		case MOUSE_MOVED: {
			mToggle = !mToggle;

			auto mask = mer.dwButtonState;
			auto bufferLoc = mer.dwMousePosition;
			if (mToggle == true && mask&FROM_LEFT_1ST_BUTTON_PRESSED)
			{
				THROW_IF_CONSOLE_ERROR(FillConsoleOutputAttribute(hConsoleOutput, BACKGROUND_COL, 1, cursorHomeCoord, &charsWritten));
				//cout << "mt";
				//THROW_IF_CONSOLE_ERROR(FillConsoleOutputCharacterA(hConsoleOutput, 'x', 1, cursorHomeCoord, &charsWritten));
			}
		}break;


		default:
			break;
		}
	}



	virtual int GamePlay() { return 0; }
};


class GoBoard : public Board
{
public:
	GoBoard() {
		_hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
		_hConsoleIn = GetStdHandle(STD_INPUT_HANDLE);
		_mPosX = 0;
		_mPosY = 0;
		_screenHeight = 20;
		_screenWidth = 40;
		_appName = "Alex's GO App";
		ConsoleSetup();
		GamePlay();
	}
	//===========================//
   // Key Handlers - GO Version //
  //===========================//
	void ProcessKeyEvent(KEY_EVENT_RECORD const& ker) override {
		DWORD charsWritten;
		CONSOLE_SCREEN_BUFFER_INFO csbi;
		THROW_IF_CONSOLE_ERROR(GetConsoleScreenBufferInfo(hConsoleOutput, &csbi));
		DWORD consoleSize = csbi.dwSize.X * csbi.dwSize.Y;
		auto ks = ker.dwControlKeyState;
		WORD test1 = 17;
		//cout << ker.wVirtualKeyCode;
		while (ker.bKeyDown && ker.wVirtualKeyCode == test1)
			cout << "H";


		if (ker.uChar.AsciiChar == 'r' || ker.uChar.UnicodeChar == 'r') {
			THROW_IF_CONSOLE_ERROR(FillConsoleOutputAttribute(hConsoleOutput, BACKGROUND_WHITE, consoleSize, { 0,0 }, &charsWritten));
			BACKGROUND_COL = BACKGROUND_RED;
		}
		if (ks&LEFT_CTRL_PRESSED || ks & RIGHT_CTRL_PRESSED && ker.uChar.AsciiChar == 'c')
			cout << "ctrl+c\n";


	}

	//=============================//
   // Mouse Handlers - GO Version //
  //=============================//
	void MouseEventPainter(MOUSE_EVENT_RECORD const& mer) override {
		auto bufferLoc = mer.dwMousePosition;
		cursorHomeCoord = { bufferLoc.X, bufferLoc.Y };

		switch (mer.dwEventFlags) {
		case 0:	// button pressed or released
		{
			auto mask = mer.dwButtonState;
			if (mask&FROM_LEFT_1ST_BUTTON_PRESSED)
			{
				if (BACKGROUND_COL == NULL)
				{
					BACKGROUND_COL = BACKGROUND_RED;
				}
				else if (BACKGROUND_COL == BACKGROUND_RED)
				{
					BACKGROUND_COL = BACKGROUND_BLACK;
				}

				THROW_IF_CONSOLE_ERROR(FillConsoleOutputAttribute(hConsoleOutput, BACKGROUND_COL, 1, cursorHomeCoord, &charsWritten));


			}
		}break;
		default:
			break;
		}
	}
	//========================//
   // Gameplaye - GO Version //
  //========================//
	int GamePlay() override {
		vector<char> originalTitle(64 * 1024);
		originalTitle.resize(size_t(GetConsoleTitleA(originalTitle.data(), (DWORD)originalTitle.size())) + 1);
		originalTitle.shrink_to_fit();
		SetConsoleTitleA(_appName);

		cout << "Press 'r' to reset the board, CTRL+C to exit.";

		hConsoleInput = GetStdHandle(STD_INPUT_HANDLE);
		hConsoleOutput = GetStdHandle(STD_OUTPUT_HANDLE);

		auto hStdin = GetStdHandle(STD_INPUT_HANDLE);
		auto hStdout = GetStdHandle(STD_OUTPUT_HANDLE);
		DWORD charsWritten;
		CONSOLE_SCREEN_BUFFER_INFO csbi;
		THROW_IF_CONSOLE_ERROR(GetConsoleScreenBufferInfo(hConsoleOutput, &csbi));

		DWORD consoleSize = csbi.dwSize.X * csbi.dwSize.Y;

		THROW_IF_CONSOLE_ERROR(FillConsoleOutputAttribute(hConsoleOutput, BACKGROUND_WHITE, consoleSize, { 0,0 }, &charsWritten));

		vector<INPUT_RECORD> inBuffer(128);
		while (!done) {
			DWORD numEvents;
			if (!ReadConsoleInput(hStdin, inBuffer.data(), (DWORD)inBuffer.size(), &numEvents)) {
				cerr << "Failed to read console input\n";
				break;
			}

			for (size_t iEvent = 0; iEvent < numEvents; ++iEvent) {
				switch (inBuffer[iEvent].EventType) {
				case MOUSE_EVENT:
					MouseEventPainter(inBuffer[iEvent].Event.MouseEvent);
					break;
				case KEY_EVENT:
					ProcessKeyEvent(inBuffer[iEvent].Event.KeyEvent);
					break;

				}
			}

		}
		return 0;
	}
};

class PaintBoard : public Board
{
public:
	PaintBoard() {
		_hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
		_hConsoleIn = GetStdHandle(STD_INPUT_HANDLE);
		_mPosX = 0;
		_mPosY = 0;
		_appName = "Alex's Paint App";
		ConsoleSetup();
		GamePlay();
	}


	void ProcessKeyEvent(KEY_EVENT_RECORD const& ker) override {
		DWORD charsWritten;
		CONSOLE_SCREEN_BUFFER_INFO csbi;
		THROW_IF_CONSOLE_ERROR(GetConsoleScreenBufferInfo(hConsoleOutput, &csbi));
		DWORD consoleSize = csbi.dwSize.X * csbi.dwSize.Y;
		auto ks = ker.dwControlKeyState;

		if (ker.uChar.AsciiChar == 'c' || ker.uChar.UnicodeChar == 'c') {
			THROW_IF_CONSOLE_ERROR(FillConsoleOutputAttribute(hConsoleOutput, BACKGROUND_WHITE, consoleSize, { 0,0 }, &charsWritten));
			BACKGROUND_COL = BACKGROUND_RED;
		}
		/*if (RIGHT_CTRL_PRESSED && ker.uChar.AsciiChar == 'c' || ker.uChar.UnicodeChar == 'c')
		{
			cout << "Ctrl-C pressed\n" << endl;
			done = true;
		}*/
	}

	int GamePlay() override {
		vector<char> originalTitle(64 * 1024);
		originalTitle.resize(size_t(GetConsoleTitleA(originalTitle.data(), (DWORD)originalTitle.size())) + 1);
		originalTitle.shrink_to_fit();
		SetConsoleTitleA(_appName);

		cout << "Press 'c' to clear the screen, CTRL+C to exit.";

		hConsoleInput = GetStdHandle(STD_INPUT_HANDLE);
		hConsoleOutput = GetStdHandle(STD_OUTPUT_HANDLE);

		auto hStdin = GetStdHandle(STD_INPUT_HANDLE);
		auto hStdout = GetStdHandle(STD_OUTPUT_HANDLE);
		DWORD charsWritten;
		CONSOLE_SCREEN_BUFFER_INFO csbi;
		THROW_IF_CONSOLE_ERROR(GetConsoleScreenBufferInfo(hConsoleOutput, &csbi));

		DWORD consoleSize = csbi.dwSize.X * csbi.dwSize.Y;

		THROW_IF_CONSOLE_ERROR(FillConsoleOutputAttribute(hConsoleOutput, BACKGROUND_WHITE, consoleSize, { 0,0 }, &charsWritten));

		vector<INPUT_RECORD> inBuffer(128);
		while (!done) {
			DWORD numEvents;
			if (!ReadConsoleInput(hStdin, inBuffer.data(), (DWORD)inBuffer.size(), &numEvents)) {
				cerr << "Failed to read console input\n";
				break;
			}

			for (size_t iEvent = 0; iEvent < numEvents; ++iEvent) {
				switch (inBuffer[iEvent].EventType) {
				case MOUSE_EVENT:
					//MouseEventGO(inBuffer[iEvent].Event.MouseEvent);
					MouseEventPainter(inBuffer[iEvent].Event.MouseEvent);
					break;
				case KEY_EVENT:
					ProcessKeyEvent(inBuffer[iEvent].Event.KeyEvent);
					break;

				}
			}
		}
		return 0;
	}

};
