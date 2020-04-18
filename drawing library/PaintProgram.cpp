/*
 * GoProgram.cpp
 * author Alex Laye
 * date 2019-03-10
 * CPP file  that uses the DrawLibrary. this is for the paintiing program.
*/
//#include <DrawLibrary.hpp>
#include "DrawLibrary.hpp"
int main()
{

#ifdef _DEBUG
	// Enable CRT memory leak checking.
	int dbgFlags = _CrtSetDbgFlag(_CRTDBG_REPORT_FLAG);
	dbgFlags |= _CRTDBG_CHECK_ALWAYS_DF;
	dbgFlags |= _CRTDBG_DELAY_FREE_MEM_DF;
	dbgFlags |= _CRTDBG_LEAK_CHECK_DF;
	_CrtSetDbgFlag(dbgFlags);
#endif

	PaintBoard paint;
}