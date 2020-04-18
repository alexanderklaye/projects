#include <iostream>

//#include <DrawLibrary.hpp>
#include "DrawLibrary.hpp"
//#include <goBoard.hpp>
//#include <paintBoard.hpp>

void StartGame()
{
	bool exit = false;
	char uPick;


	while (!exit) 
	{
		std::cout << "Play [G]omoku, [P]aint, or E[X]it?";
		cin >> uPick;
		if (uPick == 'g' || uPick == 'G')
		{
			GoBoard go;
		}
		else if (uPick == 'p' || uPick == 'P') 
		{
			PaintBoard paint;
		}
		else if (uPick == 'x' || uPick == 'x') 
		{
			exit = true;
			break;
		}
		else
		{
			cout << "Bad entry; try again!\n";
		}
	}

	
}