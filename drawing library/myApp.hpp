/*	
 * myApp.hpp
 * author Alex Laye
 * date 2019-01-25
 * Header file for static library/console project
*/

#pragma once
#include <iostream>
#include <string>
#include <regex>
#include <cmath>
#include <vector>

void checkNumber(int num);

class Object {
	Object(Object const&) = delete;
	Object& operator=(Object const&) = delete;
protected:
	Object() { std::cout << "Object::ctor" << std::endl; }
	virtual ~Object() { std::cout << "Object::dtor" << std::endl; }
public:
	void argBuilder(int argc, char* argv[], int id = 0) {
		std::string tempS;
		std::vector<unsigned> myVec;
		int temp = 0;
		for (int i = 1; i < argc; ++i)
		{
			tempS += argv[i];
			if (isdigit(argv[i][0]))
			{
				myVec.push_back(std::stoi(argv[i]));
				//std::cout << argv[i] << " added to vector.\n";
			}
			else {
				std::cout << argv[i] << " is not a digit.\n";
				std::cout << *argv[i] << " is not a digit.\n";
				std::cout << &argv[i] << " is not a digit.\n";
			}
		}
		for (unsigned n = 0; n < myVec.size(); ++n)
		{
			checkNumber(myVec[n]);
		}
	}
};

class Singleton :public Object {
public:
	static Object& instance() {
		static std::unique_ptr<Singleton> _instance(new Singleton);
		return *_instance;
	}


	
};

void SBuilder(int argc, char*argv[])
{
	Singleton::instance().argBuilder(argc, argv);


}

// generate sum of numbers for status
int getSum(int num)
{
	int sum = 0;
	for (int i = 1; i <= sqrt(num); i++)
	{
		if (num % i == 0)
		{
			if (num / i == i)
				sum += i;
			else
			{
				sum += i;
				sum += (num / i);
			}
		}
	}
	sum -= num;
	return sum;
}
// call to check sum for perfect
bool checkPerfect(int num) {
	return (getSum(num) == num);
}
// call to check sum for abundant
bool checkAbundant(int num)
{
	return (getSum(num) > num);
}
// call to check sum for deficient
bool checkDeficient(int num) {
	return (getSum(num) < num);
}
// call to check the number for its status (PAD)
void checkNumber(int num)
{
	bool perfect, abundant, deficient;
	perfect = checkPerfect(num);
	abundant = checkAbundant(num);
	deficient = checkDeficient(num);

	if (perfect == true || abundant == true || deficient == true)
	{
		std::cout << num << " is ";

		if (perfect == true)
			std::cout << "Perfect.";
		if (abundant == true)
			std::cout << "Abundant.";
		if (deficient == true)
			std::cout << "Deficient.";

		if (perfect == false && abundant == false && deficient == false)
			std::cout << "not Perfect, Abundant, or Deficient.";

		std::cout << "\n";

	}
}